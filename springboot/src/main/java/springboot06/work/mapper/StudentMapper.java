package springboot06.work.mapper;


//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import springboot06.work.pojo.Student;
import springboot06.work.pojo.Teacher;
import springboot06.work.req.CreatClassReq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
@Mapper
public interface StudentMapper {

//    void insertBatch()
    int selectId(@Param("username") String username);

    void updateStudentPassword(Student student);
    String selectPassword(@Param("username") String username);
    void updateStudentPicture(Student student);
    List<Student> selectSchoolStudent(String school);
    List<Student> selectClassStudent(String cname);
    public List<Student> allStudent(String cname);
    int deleteStudent(Map<String, List<String>> maps);
    public int updateStudent(HashMap<String,Object> map);
    public int addAllStudent(HashMap<String, Object> map);


/****/
    Student selectById(String username);
    void addStudents(Student student);
    void addExpStudent(Student student);
    List<Student> findAll(Student students);
    void updateById(Student student);
    Student teacherselectStu(Student student);
    void deleteStu(@Param("username") String username);
    List<Student> findExpAll(Student student);
    void teacherUpdateById(Student student);
    void teacherdeleteStu(Student student);
    void deleteExpStu(String cname);
    List<String> selectCourse(@Param("username") String username);
    List<Student> getStudentsList(@Param("id") Integer id);
    List<Student> exportExpStudent(@Param("id") Integer id,@Param("tid") int tid,@Param("cname") String cname);
}
