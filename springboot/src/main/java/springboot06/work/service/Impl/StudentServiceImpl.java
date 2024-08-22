package springboot06.work.service.Impl;

//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06.work.mapper.GradeMapper;
import springboot06.work.mapper.StudentMapper;
import springboot06.work.mapper.UserMapper;
import springboot06.work.pojo.Grade;
import springboot06.work.pojo.Student;
import springboot06.work.pojo.User;
import springboot06.work.req.CreatClassReq;
import springboot06.work.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    GradeMapper gradeMapper;
    @Override
    public Map<String, Object> PageShow(int limit, int offset,String cname){
        Map<String, Object> map=new HashMap<>();
        List<Student> page=new ArrayList<>();
        List<Student> all=studentMapper.allStudent(cname);
        for (int i = offset; i < offset+limit; i++) {
            if (i<all.size()) {
                page.add(all.get(i));
            }
        }
        map.put("rows", page);
        map.put("total", all.size());
        return map;
    }
    @Override
    public int updateStudent(HashMap<String,Object> map){
        System.err.println(map);
        return studentMapper.updateStudent(map);
    }
    @Override
    public int addAllStudent(HashMap<String,Object> map){
        System.err.println(map);
        return studentMapper.addAllStudent(map);
    }



    @Override
    public int deleteStudent(List<String> id) {
        //创建一个map对象，map里面有个list集合
        HashMap<String, List<String>> map = new HashMap<>();
        map.put("id",id );
        return studentMapper.deleteStudent(map);
    }

    @Override
    public int selectId(String username) {
        return studentMapper.selectId(username);
    }



    @Override
    public void updateStudentPassword(Student student) {
        studentMapper.updateStudentPassword(student);
    }

    @Override
    public String selectPassword(String username) {
        return studentMapper.selectPassword(username);
    }

    @Override
    public void updateStudentPicture(Student student) {
        studentMapper.updateStudentPicture(student);
    }

    @Override
    public List<Student> selectSchoolStudent(String school) {
//        JSONObject result=new JSONObject();
//        //接收数据
//        try {
//            PageHelper.startPage(page,limit);
//            PageInfo<linkman> pageInfo=new PageInfo(goodsMapper.findAll());
//            result.put("code","0"); //设置状态码
//            result.put("msg","操作成功"); //设置信息
//            result.put("data",pageInfo.getList()); //获取数据集合
//            result.put("count",pageInfo.getTotal()); //获取分页总数
//        } catch (Exception e) {
//            result.put("code","500");
//            result.put("msg","查询异常！");
//        }
//        return result;

        return studentMapper.selectSchoolStudent(school);
    }
    @Override
    public List<Student> selectClassStudent(String cname) {
        return studentMapper.selectClassStudent(cname);
    }




    /*******/
    @Override
    public Student selectById(String username) {
        System.err.println(username);
        return studentMapper.selectById(username);
    }
    @Override
    public JSONObject getAllStudent(int page, int limit, Student student) {
        JSONObject result=new JSONObject();
        //接收数据
        try {
            PageHelper.startPage(page,limit);
            List<Student> list = studentMapper.findAll(student);
            PageInfo<Student> pageInfo=new PageInfo<>(list);
            result.put("code","0"); //设置状态码
            result.put("msg","success"); //设置信息
            result.put("data",pageInfo.getList()); //获取数据集合
            result.put("count",pageInfo.getTotal()); //获取分页总数
        } catch (Exception e) {
            List<Student> list = studentMapper.findAll(student);
            result.put("code","500");
            result.put("msg","查询异常！");
            System.out.print("查找全部数据"+list);
        }
        return result;
    }
    @Override
    public void addStudent(Student student) {
        studentMapper.addStudents(student);
    }
    @Override
    public JSONObject updateById(Student student) {
        JSONObject result=new JSONObject();
        try {
            studentMapper.updateById(student);
            result.put("code",0);
            result.put("msg","修改,主键id为"+student.getId());
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","异常,修改失败！");
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void deleteStu(String username) {
        /*使用JSON*/
       studentMapper.deleteStu(username);

    }
    public JSONObject getExpStudent(int page, int limit, Student student) {
        JSONObject result=new JSONObject();
        //接收数据
        try {
            PageHelper.startPage(page,limit);
            List<Student> list = studentMapper.findExpAll(student);
            PageInfo<Student> pageInfo=new PageInfo<>(list);
            result.put("code","0"); //设置状态码
            result.put("msg","success"); //设置信息
            result.put("data",pageInfo.getList()); //获取数据集合
            result.put("count",pageInfo.getTotal()); //获取分页总数
        } catch (Exception e) {
            List<Student> list = studentMapper.findExpAll(student);
            result.put("code","500");
            result.put("msg","查询异常！");
            System.out.print("查找全部数据"+list);
        }
        return result;
    }
    @Override
    public void addExpStudent(Student student) {
        studentMapper.addExpStudent(student);
    }
    @Override
    public JSONObject teacherUpdateById(Student student) {
        JSONObject result=new JSONObject();
        try {
            studentMapper.teacherUpdateById(student);
            result.put("code",0);
            result.put("msg","修改,主键id为"+student.getId());
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","异常,修改失败！");
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public void teacherdeleteStu(Student student) {
        /*使用JSON*/
        studentMapper.teacherdeleteStu(student);

    }
    @Override
    public void deleteExpStu(String cname){
        studentMapper.deleteExpStu(cname);
    }
    @Override
    public Student teacherselectStu(Student student) {
    return studentMapper.teacherselectStu(student);
    }
    @Override
    public List<String> selectCourse(String username) {
        return studentMapper.selectCourse(username);
    }
    @Override
    public void addStudentGrade(Grade grade) {
        gradeMapper.addStudentGrade(grade);
    }
    @Override
    public JSONObject getGrade(Grade grade) {
        JSONObject result=new JSONObject();
        //接收数据
        try {
            List<Grade> list = gradeMapper.getGrade(grade);
            PageInfo<Grade> pageInfo=new PageInfo<>(list);
            result.put("code","0"); //设置状态码
            result.put("msg","success"); //设置信息
            result.put("data",pageInfo.getList()); //获取数据集合
        } catch (Exception e) {
            List<Grade> list = gradeMapper.getGrade(grade);
            result.put("code","500");
            result.put("msg","查询异常！");
            System.out.print("查找全部数据"+list);
        }
        return result;
    }
    @Override
    public void getStudent(Integer id, HttpServletResponse response) {
        List<Student> studentsList = studentMapper.getStudentsList(id);
        // 导出员工信息基本设置
        // title：文件内容中的标题名，第一行 sheetName：文件中的表名 ExcelType:导出的表格文件名后缀， 1.HSSF 后缀为.xls，2.XSSF 为 .xlsx，
        // 2003版本的导出速度更快，并且用 2003 或者 2003 以上的office都能打开，2007版本的office只能向上兼容
        ExportParams exportParams = new ExportParams("学生信息表", "学生信息", ExcelType.HSSF);
        // 查询到的 list 导出的表格数据，此时还没有输出文件
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, Student.class, studentsList);

        BufferedOutputStream outputStream = null;
        try {
            // 以流的形式输出,防止文件乱码
            response.setContentType("application/octet-stream");
            // 防止下载出来的文件名中文乱码
             URLEncoder.encode("学生信息表.xls","UTF-8");//输出的文件名并且设置编码
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("学生信息表.xls", "UTF-8"));
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
    @Override
    public void exportExpStudent(Integer id,HttpServletResponse response,int tid,String cname) {
        List<Student> studentsList = studentMapper.exportExpStudent(id,tid,cname);
        // 导出员工信息基本设置
        // title：文件内容中的标题名，第一行 sheetName：文件中的表名 ExcelType:导出的表格文件名后缀， 1.HSSF 后缀为.xls，2.XSSF 为 .xlsx，
        // 2003版本的导出速度更快，并且用 2003 或者 2003 以上的office都能打开，2007版本的office只能向上兼容
        ExportParams exportParams = new ExportParams("学生信息表", "学生信息", ExcelType.HSSF);
        // 查询到的 list 导出的表格数据，此时还没有输出文件
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, Student.class, studentsList);

        BufferedOutputStream outputStream = null;
        try {
            // 以流的形式输出,防止文件乱码
            response.setContentType("application/octet-stream");
            // 防止下载出来的文件名中文乱码
            URLEncoder.encode("学生信息表.xls","UTF-8");//输出的文件名并且设置编码
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("学生信息表.xls", "UTF-8"));
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
