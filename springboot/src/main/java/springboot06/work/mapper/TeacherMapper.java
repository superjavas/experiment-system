package springboot06.work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import springboot06.work.pojo.Student;
import springboot06.work.pojo.Teacher;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {

    int selectId(@Param("username") String username);

    void updateTeacherPassword(Teacher teacher);
    String selectPassword(@Param("username") String username);
    void updateTeacherPicture(Teacher teacher);
    List<Teacher> selectSchoolTeacher(String school);
    String selectSchool(int id);


    /*********************/
    Teacher selectById(String username);
    List<Teacher> findAllTeachers(Teacher teacher);
    void addTeacher(Teacher teacher);
    void updateTeacher(Teacher teacher);
    void deleteTeacher(@Param("username") String username);
    List<Teacher> getTeacherList(@Param("id") Integer id);
}
