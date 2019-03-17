package com.xuecheng.framework.domain.course;

import com.xuecheng.framework.util.UUIdGenId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
//@Entity
@Table(name="teachplan")
//@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@NoArgsConstructor
public class Teachplan implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;

    //@GeneratedValue(generator = "jpa-uuid")
    @Id
    @Column(length = 32)
    @KeySql(genId = UUIdGenId.class)
    private String id;
    private String pname;
    private String parentid;
    private String grade;
    private String ptype;
    private String description;
    private String courseid;
    private String status;
    private Integer orderby;
    private Double timelength;
    private String trylearn;

    public Teachplan(String parentid, String courseid) {
        this.parentid = parentid;
        this.courseid = courseid;
    }
}
