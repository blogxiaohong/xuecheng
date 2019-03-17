package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.mapper.CourseBaseMapper;
import com.xuecheng.manage_course.mapper.CourseMapper;
import com.xuecheng.manage_course.mapper.CoursemarketMapper;
import com.xuecheng.manage_course.mapper.TeachplanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private CoursemarketMapper coursemarketMapper;

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private CmsPageClient cmsPageClient;

    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest) {

        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }

        PageHelper.startPage(page, size);
        Page<CourseInfo> courseInfos = courseMapper.selectAllWithPic();

        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setList(courseInfos.getResult());
        queryResult.setTotal(courseInfos.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    @Transactional
    public AddCourseResult addCoursebase(CourseBase courseBase) {
        courseBase.setId(null);
        courseBase.setStatus("202001");
        courseBaseMapper.insert(courseBase);
        return new AddCourseResult(CommonCode.SUCCESS, courseBase.getId());
    }

    public CourseBase getCoursebaseById(String courseId) {
        CourseBase courseBase = courseBaseMapper.selectByPrimaryKey(courseId);
        if (courseBase == null) {
            return null;
        }
        return courseBase;
    }

    @Transactional
    public ResponseResult updateCoursebase(String courseId, CourseBase courseBase) {

        CourseBase temp = courseBaseMapper.selectByPrimaryKey(courseId);
        if (temp == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //修改课程基本信息
        courseBaseMapper.updateByPrimaryKeySelective(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CourseView getCourseView(String id) {
        CourseView courseView = new CourseView();

        //查询课程基本信息
        CourseBase courseBase = courseBaseMapper.selectByPrimaryKey(id);
        if (courseBase != null) {
            courseView.setCourseBase(courseBase);
        }
        //查询课程营销信息
        CourseMarket courseMarket = coursemarketMapper.selectByPrimaryKey(id);
        if (courseMarket != null) {
            courseView.setCourseMarket(courseMarket);
        }
        //查询课程图片信息

        //查询课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        if (teachplanNode != null) {
            courseView.setTeachplanNode(teachplanNode);
        }
        return courseView;
    }

    /**
     * 课程预览
     * @param id 课程ID
     * @return
     */
    public CoursePublishResult preview(String id) {

        CmsPage cmsPage = this.createCmsPage(id);

        //远程请求 Cms 服务添加页面的接口
        CmsPageResult cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        if (!cmsPageResult.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }
        String pageId = cmsPageResult.getCmsPage().getPageId();

        String url = "http://www.xuecheng.com/cms/preview/" + pageId;

        return new CoursePublishResult(CommonCode.SUCCESS, url);
    }

    /**
     * 发布课程
     * @param id
     * @return
     */
    @Transactional
    public CoursePublishResult publish(String id) {

        CmsPage cmsPage = this.createCmsPage(id);

        //远程调用 Cms 服务一键发布页面接口
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postCmsPageQuick(cmsPage);
        if (!cmsPostPageResult.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }

        //更新课程状态
        int result = updateCourseStatus(id);
        if (result <= 0) {
            ExceptionCast.cast(CommonCode.FAIL);
        }

        String pageUrl = cmsPostPageResult.getPageUrl();

        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }

    /**
     * 更新课程状态
     * @param id
     */
    private int updateCourseStatus(String id) {
        CourseBase courseBase = courseBaseMapper.selectByPrimaryKey(id);
        courseBase.setStatus("202002");
        return courseBaseMapper.updateByPrimaryKeySelective(courseBase);
    }

    private CmsPage createCmsPage(String id) {
        //查询课程基本信息
        CourseBase courseBase = courseBaseMapper.selectByPrimaryKey(id);
        if (courseBase == null) {
            ExceptionCast.cast(CourseCode.COURSE_GET_NOTEXISTS);
        }
        CmsPage cmsPage = new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);//课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(id+".html");
        //页面别名
        cmsPage.setPageAliase(courseBase.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+id);
        return cmsPage;
    }
}
