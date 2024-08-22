package springboot06.work.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import springboot06.work.pojo.User;
import springboot06.work.req.PictureReq;

import java.util.List;

public interface UserService {
    List<User> queryUser();
    void addTid(User user);
    void addSid(User user);
    User login(String username,String password);
    void updateUserpassword(User user);
    void updateUser(User user);
    String selectPassword(int id);
    //***************
    void addUser(User user);
    void deleteUser(String username);
    JSONObject setPicture(PictureReq pictureReq);
    User findUserById(int id);
   void addTeacherUser(User user);
    User findUserByUsername(String username);
    void updatePassword(User user);
}
