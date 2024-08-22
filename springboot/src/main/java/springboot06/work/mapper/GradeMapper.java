package springboot06.work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import springboot06.work.pojo.Grade;
import springboot06.work.pojo.Student;

import java.util.List;

@Repository
@Mapper
public interface GradeMapper {
    void addStudentGrade(Grade grade);
    List<Grade> getGrade(Grade grade);
    List<Grade> findAllGrade(Grade grade);
    void updateGrade(Grade grade);
    void deleteGrade(@Param("id") int id);
}
