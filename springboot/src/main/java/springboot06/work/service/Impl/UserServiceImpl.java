package springboot06.work.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06.work.mapper.UserMapper;
import springboot06.work.pojo.User;
import springboot06.work.req.PictureReq;
import springboot06.work.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> queryUser() {
        List<User> users = userMapper.queryUser();
        return users;
    }
    @Override
    public void addTid(User user) {
        userMapper.addTid(user);
    }

    @Override
    public void addSid(User user) {
        userMapper.addSid(user);
    }




    @Override
    public User login(String username, String password) {

        return userMapper.login(username,password);
    }


    @Override
    public void updateUserpassword(User user) {
        userMapper.updateUserPassword(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public String selectPassword(int id) {
        return userMapper.selectPassword(id);
    }

    @Override
    public void updatePassword(User user) {
        userMapper.updatePassword(user);
    }


    //*************************
    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }
    public void addTeacherUser(User user) {
        userMapper.addTeacherUser(user);
    }
    public void deleteUser(String username){
        System.out.print("user用户名是："+username);
        userMapper.deleteUser(username);
    }
    public JSONObject setPicture(PictureReq pictureReq) {
        JSONObject result=new JSONObject();
        try {
            userMapper.setPicture(pictureReq);
            result.put("code",0);
            result.put("msg","上传成功");
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","异常,上传失败！");
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }
    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
