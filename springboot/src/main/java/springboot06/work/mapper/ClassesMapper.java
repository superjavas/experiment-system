package springboot06.work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import springboot06.work.pojo.Classes;

import java.util.List;

@Mapper
@Repository
public interface ClassesMapper {
    void creatClass(Classes classes);
    List<String> selectClass(@Param("tid") int tid);
    Classes selectByCname(@Param("cname") String cname,@Param("tid") int tid);
    Classes deleteCourse(@Param("cname") String cname,@Param("tid") int tid);
}
