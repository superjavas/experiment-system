package springboot06.work.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
@Data
public class Student {
    private int id;
    @Excel(name ="学号")
    private String username;
    @Excel(name ="姓名")
    private String sname;
    @Excel(name ="班级")
    private String classes;
    @Excel(name ="性别")
    private String gender;
    private String password;
    @Excel(name ="专业")
    private String major;
    private String picture;
    private int level;
    @Excel(name ="教师编码")
    private int tid;
    @Excel(name ="课程名称")
    private String cname;
    private int sid;

}
