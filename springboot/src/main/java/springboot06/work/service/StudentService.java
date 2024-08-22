package springboot06.work.service;

import com.alibaba.fastjson.JSONObject;
import springboot06.work.pojo.Grade;
import springboot06.work.pojo.Student;
import springboot06.work.pojo.User;
import springboot06.work.req.CreatClassReq;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StudentService {

    int selectId(String username);

    void updateStudentPassword(Student student);
    String selectPassword(String username);
    void updateStudentPicture(Student student);
//    List<Student> selectSchoolStudent(int page, int limit,String school);
    List<Student> selectSchoolStudent(String school);
    List<Student> selectClassStudent(String cname);
    public Map<String, Object> PageShow(int limit,int offset,String cname);
    int deleteStudent(List<String> id);
    int updateStudent(HashMap<String, Object> map);
    int addAllStudent(HashMap<String, Object> map);



//********
    Student selectById(String username);
    void addStudent(Student student);
    void addExpStudent(Student student);
    JSONObject getAllStudent(int page, int limit, Student student);
    JSONObject updateById(Student student);
    void deleteStu(String username);
    JSONObject getExpStudent(int page, int limit, Student student);
    JSONObject teacherUpdateById(Student student);
    void teacherdeleteStu(Student student);
    Student teacherselectStu(Student student);
    void deleteExpStu(String cname);
    List<String> selectCourse(String username);
    void addStudentGrade(Grade grade);
    JSONObject getGrade(Grade grade);
    void getStudent(Integer id, HttpServletResponse response);
    void exportExpStudent(Integer id,HttpServletResponse response,int tid,String cname);
}
