package springboot06.work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import springboot06.work.pojo.Student;
import springboot06.work.pojo.User;
import springboot06.work.req.PictureReq;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> queryUser();

    void addSid(User user);
    void addTid(User user);


    void deleteOutuser(@Param("oid") int oid);
    void updateUserPassword(User user);
    void updateUser(User user);
    String selectPassword(int id);

    //*********
    void addUser(User user);
    void deleteUser(String username);
    void setPicture(PictureReq picture);
    User login(@Param("username")String username,@Param("password")String password);
    void updatePassword(User user);
    User findUserById(@Param("id") int id);
    User findUserByUsername(@Param("username") String username);
    void addTeacherUser(User user);
}
