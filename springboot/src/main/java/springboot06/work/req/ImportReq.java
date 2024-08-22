package springboot06.work.req;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ImportReq {
    @Excel(name ="教师编码")
    private int tid;
    @Excel(name ="课程名称")
    private String cname;
}
