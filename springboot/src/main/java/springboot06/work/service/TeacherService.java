package springboot06.work.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import springboot06.work.pojo.Student;
import springboot06.work.pojo.Teacher;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface TeacherService {
    int selectId(String username);

    void updateTeacherPassword(Teacher teacher);
    String selectPassword(@Param("username") String username);
    void updateTeacherPicture(Teacher teacher);
    List<Teacher> selectSchoolTeacher(String school);
    String selectSchool(int id);



    /*******/
    Teacher selectById(String username);
    JSONObject getAllTeachers(int page, int limit, Teacher teacher);
    void addTeacher(Teacher teacher);
    JSONObject updateTeacher(Teacher teacher);
    void deleteTeacher(String username);
    void getTeacher(Integer id, HttpServletResponse response);
}
