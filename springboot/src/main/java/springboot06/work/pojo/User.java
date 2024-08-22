package springboot06.work.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private Integer level;

    private Integer sid;

    private Integer tid;

    private String picture;


}
