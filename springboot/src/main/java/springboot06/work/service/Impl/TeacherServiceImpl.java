package springboot06.work.service.Impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06.work.mapper.TeacherMapper;
import springboot06.work.pojo.Student;
import springboot06.work.pojo.Teacher;
import springboot06.work.service.TeacherService;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;



    @Override
    public int selectId(String username) {
        return teacherMapper.selectId(username);
    }



    @Override
    public void updateTeacherPassword(Teacher teacher) {
        teacherMapper.updateTeacherPassword(teacher);
    }

    @Override
    public String selectPassword(String username) {
        return teacherMapper.selectPassword(username);
    }

    @Override
    public void updateTeacherPicture(Teacher teacher) {
        teacherMapper.updateTeacherPicture(teacher);
    }

    @Override
    public List<Teacher> selectSchoolTeacher(String school) {
        return teacherMapper.selectSchoolTeacher(school);
    }

    @Override
    public String selectSchool(int id) {
        return teacherMapper.selectSchool(id);
    }

    /****/
    @Override
    public Teacher selectById(String username) {
        return teacherMapper.selectById(username);
    }
    @Override
    public JSONObject getAllTeachers(int page, int limit, Teacher teacher) {
        JSONObject result=new JSONObject();
        //接收数据
        try {
            PageHelper.startPage(page,limit);
            List<Teacher> list = teacherMapper.findAllTeachers(teacher);
            PageInfo<Teacher> pageInfo=new PageInfo<>(list);
            result.put("code","0"); //设置状态码
            result.put("msg","success"); //设置信息
            result.put("data",pageInfo.getList()); //获取数据集合
            result.put("count",pageInfo.getTotal()); //获取分页总数
        } catch (Exception e) {
            List<Teacher> list = teacherMapper.findAllTeachers(teacher);
            result.put("code","500");
            result.put("msg","查询异常！");
            System.out.print("查找全部数据"+list);
        }
        return result;
    }
    @Override
    public void addTeacher(Teacher teacher) {
        teacherMapper.addTeacher(teacher);
    }
    @Override
    public JSONObject updateTeacher(Teacher teacher) {
        JSONObject result=new JSONObject();
        try {
            teacherMapper.updateTeacher(teacher);
            result.put("code",0);
            result.put("msg","修改,主键id为"+teacher.getId());
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","异常,修改失败！");
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void deleteTeacher(String username) {
        /*使用JSON*/
        teacherMapper.deleteTeacher(username);

    }
    @Override
    public void getTeacher(Integer id, HttpServletResponse response) {
        List<Teacher> teachersList = teacherMapper.getTeacherList(id);
        // 导出员工信息基本设置
        // title：文件内容中的标题名，第一行 sheetName：文件中的表名 ExcelType:导出的表格文件名后缀， 1.HSSF 后缀为.xls，2.XSSF 为 .xlsx，
        // 2003版本的导出速度更快，并且用 2003 或者 2003 以上的office都能打开，2007版本的office只能向上兼容
        ExportParams exportParams = new ExportParams("教师信息表", "教师信息", ExcelType.HSSF);
        // 查询到的 list 导出的表格数据，此时还没有输出文件
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, Teacher.class, teachersList);

        BufferedOutputStream outputStream = null;
        try {
            // 以流的形式输出,防止文件乱码
            response.setContentType("application/octet-stream");
            // 防止下载出来的文件名中文乱码
            URLEncoder.encode("教师信息表.xls","UTF-8");//输出的文件名并且设置编码
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("教师信息表.xls", "UTF-8"));
            // 拿到输出流
            outputStream = new BufferedOutputStream(response.getOutputStream());
            // 导出的表格数据，以流的形式输出，提供给浏览器下载
            sheets.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
