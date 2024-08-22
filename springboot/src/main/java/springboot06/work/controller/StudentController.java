package springboot06.work.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot06.work.pojo.Grade;
import springboot06.work.pojo.User;
import springboot06.work.req.RespPageBean;
import springboot06.work.pojo.Student;
import springboot06.work.service.StudentService;
import springboot06.work.service.UserService;
import springboot06.work.util.ResultJson;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;


//    /**
//     * 查看学生个人信息
//     * @param sid
//     * @return
//     */
//    @GetMapping("/information")
//    public ResultJson getInformation(@RequestParam("sid") int sid){
//        ResultJson r = new ResultJson();
//        Student student = studentService.selectById(sid);
//        r.setData(student);
//        return r;
//    }
    /**
     *分页查询所有学生
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONObject getStudentList(Integer currentPage, Integer size, Student student){
        return studentService.getAllStudent(currentPage,size,student);
    }
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public JSONObject addStudet(@RequestBody Student student){
        User user = new User();
        JSONObject r = new JSONObject();
        user.setUsername(student.getUsername());
        user.setPassword(student.getPassword());
        user.setLevel(student.getLevel());
        User user1=new User();
        user1=userService.findUserByUsername(user.getUsername());
        try {
            if (user1==null){
                userService.addUser(user);
                studentService.addStudent(student);
                r.put("code",0);
                r.put("msg","添加成功");
            }else{
                r.put("msg","用户已存在");
            }

        } catch (Exception e) {
            r.put("code",500);
            r.put("msg","异常,添加失败！");
            e.printStackTrace();
        }
        return r;
    }
    @RequestMapping(value = "/updateStudent",method = RequestMethod.POST)
    public JSONObject updateById(@RequestBody Student student){
        return studentService.updateById(student);
    }
    @RequestMapping(value = "/deleteStudent",method = RequestMethod.GET)
    public JSONObject deleteStudent(@RequestParam("username") String username){
        System.out.print(username);
        JSONObject r = new JSONObject();
        try {
            studentService.deleteStu(username);
            userService.deleteUser(username);
            r.put("code",0);
            r.put("msg","删除成功");
        } catch (Exception e) {
            r.put("code",500);
            r.put("msg","异常,删除失败！");
            e.printStackTrace();
        }
        return r;
    }
    @GetMapping("/selectCourse")
    public ResultJson selectClass(@RequestParam("username") String username){
        ResultJson r = new ResultJson();
        List<String> sname = studentService.selectCourse(username);
        System.out.print(sname);
        if(sname.isEmpty()){
            r.setMessage("暂时未有老师将你加入实验");
            r.setSuccess(false);
        }else {
            r.setData(sname);
            r.setSuccess(true);
        }
        return r;
    }
    @RequestMapping(value = "/addGrade",method = RequestMethod.POST)
    public JSONObject addStudetGrade(@RequestBody Grade grade){
        System.out.print(grade);
        JSONObject r = new JSONObject();
        try {
            studentService.addStudentGrade(grade);
            //userService.addUser(user);
            r.put("code",0);
            r.put("msg","提交成功");
        } catch (Exception e) {
            r.put("code",500);
            r.put("msg","异常,提交失败！");
            e.printStackTrace();
        }
        return r;
    }
    @RequestMapping(value = "/getGrade",method = RequestMethod.GET)
    public JSONObject getGrade(Grade grade){
        System.out.print(grade);
        return studentService.getGrade(grade);
    }


}
