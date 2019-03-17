package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2018/2/10.
 */
//@Entity
//@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
@Data
@ToString
@Table(name="course_market")
public class CourseMarket implements Serializable {
    private static final long serialVersionUID = -916357110051689486L;

//    @GeneratedValue(generator = "jpa-assigned")
//    @KeySql(genId = UUIdGenId.class)
    @Id
    @Column(length = 32)
    private String id;
    private String charge;
    private String valid;
    private String qq;
    private Float price;
    private Float price_old;
//    private Date expires;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;

}
