package springboot06.work.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class Teacher {
    private int id;
    @Excel(name="教职工号")
    private String username;
    @Excel(name="姓名")
    private String tname;
    private String picture;
    private int level;
    private String password;

}
