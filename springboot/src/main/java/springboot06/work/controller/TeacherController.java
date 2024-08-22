package springboot06.work.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot06.work.pojo.*;
import springboot06.work.req.CreatClassReq;
import springboot06.work.req.RespBean;
import springboot06.work.service.*;
import springboot06.work.util.ResultJson;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.InputStream;
import java.util.*;
@RequestMapping("/teacher")
@RestController
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    ClassesService classesService;
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    GradeService gradeService;

    /**
     * 老师创建实验课程
     * @Requestparam cname
     * @Requestparam tid
     * @return
     */
    @PostMapping("/creatCourse")
    public ResultJson creat(@RequestBody CreatClassReq creatClassReq){
        String cname = creatClassReq.getCname();
        int tid = creatClassReq.getTid();
        String ename=creatClassReq.getEname();
        ResultJson r = new ResultJson();
        Classes classes = new Classes();
        classes.setCname(cname);
        classes.setTid(tid);
        Classes classes1 = classesService.selectByCname(cname,tid);
        if(classes1==null) {
            classesService.creatClass(classes);
            r.setSuccess(true);
            r.setCode(100);
            r.setMessage("实验课程创建成功");
        }else {
            r.setSuccess(false);
            r.setMessage("实验课程已存在或已经被其他老师创建");
        }
        return r;
    }

    /**
     *老师查询已经创建的班级
     * @Requestparam tid
     * @return
     */
    @GetMapping("/selectCourse")
    public ResultJson selectClass(@RequestParam("tid") int tid){
        ResultJson r = new ResultJson();
        List<String> cname = classesService.selectClass(tid);
        if(cname.isEmpty()){
            r.setMessage("该老师暂时未创建课程");
            r.setSuccess(false);
        }else {
            r.setData(cname);
            r.setSuccess(true);
        }
        return r;
    }
    /**
     * 老师删除课程
     */
    @PostMapping("/deleteCourse")
    public ResultJson deleteCourse(@RequestBody CreatClassReq creatClassReq){
        int tid = creatClassReq.getTid();
        String cname=creatClassReq.getCname();
        ResultJson r = new ResultJson();
        Classes classes = new Classes();
        classes.setCname(cname);
        classes.setTid(tid);
            classesService.deleteCourse(cname,tid);
            studentService.deleteExpStu(cname);
            r.setSuccess(true);
            r.setCode(100);
            r.setMessage("实验课程删除成功");
        return r;
    }







    /**
     * 老师根据班级查询学生
     * @param
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JSONObject getStudentList(Integer currentPage, Integer size, Student student){
        System.out.print(student);
        return studentService.getExpStudent(currentPage,size,student);
        //return studentService.getAllStudent(currentPage,size,student);
    }
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public JSONObject addStudet(@RequestBody Student student){
        JSONObject r = new JSONObject();
        String username=student.getUsername();
        int tid=student.getTid();
        String cname=student.getCname();
        User user=userService.findUserByUsername(username);
        Student student1=studentService.teacherselectStu(student);
        if(user!=null){
            if(student1==null){
                studentService.addExpStudent(student);
                //userService.addUser(user);
                r.put("code",0);
                r.put("msg","学生添加成功");
            }else{
                r.put("code",0);
                r.put("msg","此学生用户在本课程中已经存在");
            }
        }else{
            r.put("code",0);
            r.put("msg","此用户不存在，请联系管理员");
        }
        return r;
    }
    @RequestMapping(value = "/updateStudent",method = RequestMethod.POST)
    public JSONObject updateById(@RequestBody Student student){
        return studentService.teacherUpdateById(student);
    }

    @RequestMapping(value = "/deleteStudent",method = RequestMethod.GET)
    public JSONObject deleteStudent(@RequestParam("username") String username,@RequestParam("tid") int tid,@RequestParam("cname") String cname){
        Student student=new Student();
        student.setUsername(username);
        student.setCname(cname);
        student.setTid(tid);
        JSONObject r = new JSONObject();
        try {
            studentService.teacherdeleteStu(student);
            //userService.deleteUser(username);
            r.put("code",0);
            r.put("msg","删除成功");
        } catch (Exception e) {
            r.put("code",500);
            r.put("msg","异常,删除失败！");
            e.printStackTrace();
        }
        return r;
    }
    @RequestMapping(value = "/gradeList",method = RequestMethod.GET)
    public JSONObject getGradeList(Integer currentPage, Integer size, Grade grade){
        return gradeService.getGradeList(currentPage,size,grade);
        //return studentService.getAllStudent(currentPage,size,student);
    }
    @RequestMapping(value = "/updateGrade",method = RequestMethod.POST)
    public JSONObject updateGrade(@RequestBody Grade grade){
        return gradeService.updateGrade(grade);
    }
    @RequestMapping(value = "/deleteGrade",method = RequestMethod.GET)
    public JSONObject deleteGrade(@RequestParam("id") int id){
        JSONObject r = new JSONObject();
        try {
            gradeService.deleteGrade(id);
            //userService.deleteUser(username);
            r.put("code",0);
            r.put("msg","删除成功");
        } catch (Exception e) {
            r.put("code",500);
            r.put("msg","异常,删除失败！");
            e.printStackTrace();
        }
        return r;
    }
    @RequestMapping(value = "/teacherList",method = RequestMethod.GET)
    public JSONObject getTeacherList(Integer currentPage, Integer size, Teacher teacher){
        return teacherService.getAllTeachers(currentPage,size,teacher);
    }
    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    public JSONObject addTeacher(@RequestBody Teacher teacher){
        User user = new User();
        JSONObject r = new JSONObject();
        user.setUsername(teacher.getUsername());
        user.setPassword(teacher.getPassword());
        user.setLevel(teacher.getLevel());
        User user1=new User();
        user1=userService.findUserByUsername(user.getUsername());
        try {
            if (user1==null){
                userService.addTeacherUser(user);
                teacherService.addTeacher(teacher);
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
    @RequestMapping(value = "/updateTeacher",method = RequestMethod.POST)
    public JSONObject updateTeacher(@RequestBody Teacher teacher){
        return teacherService.updateTeacher(teacher);
    }
    @RequestMapping(value = "/deleteTeacher",method = RequestMethod.GET)
    public JSONObject deleteTeacher(@RequestParam("username") String username){
        System.out.print(username);
        JSONObject r = new JSONObject();
        try {
            teacherService.deleteTeacher(username);
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
    @RequestMapping(value = "/importExpStu", method = RequestMethod.POST)
    public RespBean importStu(@RequestPart("file") MultipartFile file,Student student) {
        ImportParams params = new ImportParams();
        JSONObject r = new JSONObject();
        params.setTitleRows(1);//表的标题行数，去掉前两行(第一行表标题，第二行列标题)，如果超出表格本身的标题行数，截取数据为null
//        params.setStartRows(0);//列标题和实际字段值的距离，可以设置从第几行开始截取数据，默认为0，就是列标题的下一行
        try {
            InputStream inputStream1 = file.getInputStream();
            InputStream inputStream2 = file.getInputStream();
            //设置导入使用的流 实体类 实体类的具体字段(加了Excel注解的字段)
            //返回值就是一个对象集合，里面包含了所有从文件中获取的数据
            /* 需求：此时获取的对象信息，里面有很多字段用户添加的是名字，需要将名字找到对应的ID添加导入到数据库
             *  所以需要在添加数据库之前，把名字对应的ID准备完成，实际添加用对应ID添加
             *  这个sql只需要查询一次数据库，但是如果用户传入的某一个ID查找出来为空的，其他ID也找不到
             *  如果避免这种情况，可以写 5 条 sql 查找各自的ID
             * */
            List<Student> studentList = ExcelImportUtil.importExcel(inputStream1, Student.class,params);
            List<User> userList = ExcelImportUtil.importExcel(inputStream2, User.class,params);
            Iterator<User> iterator1 = userList.iterator();
            Iterator<Student> iterator = studentList.iterator();

            while (iterator.hasNext()) {
                Student next = iterator.next();
                next.setTid(student.getTid());
                next.setCname(student.getCname());
                User next1=new User();
                next1.setUsername(next.getUsername());
                System.out.print(next1);
                Map<String, String> studentMap = new HashMap<>();
                Map<String, String> userMap = new HashMap<>();
                studentMap.put("username", next.getUsername());
                studentMap.put("sname", next.getSname());
                studentMap.put("gender", next.getGender());
                studentMap.put("classes", next.getClasses());
                studentMap.put("major", next.getMajor());
                userMap.put("username", next1.getUsername());
                System.out.println(studentMap.get("username"));
                System.out.println(studentMap.get("sname"));
                System.out.println(studentMap.get("gender"));
                System.out.println(studentMap.get("classes"));
                System.out.println(studentMap.get("major"));
                //各种学号 姓名 班级 性别 专业
                User user = new User();
                User user1=new User();
                user1=userService.findUserByUsername(studentMap.get("username"));
                Student student1=studentService.teacherselectStu(next);
                if (user1!=null){
                    if(student1==null){
                        studentService.addExpStudent(next);
                        return RespBean.success("msg","添加成功");
                    }else{
                        return RespBean.success("msg","此学生用户已存在该课程中");
                    }
                }else{
                    return RespBean.success("msg","用户不存在，请联系管理员");
                }
            }
            file.getInputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.success("导入失败");
    }
    @GetMapping(value = "/exportExpStu", produces = "application/octet-stream")
    public void exportTeacher(HttpServletResponse response,@RequestParam("tid") int tid,@RequestParam("cname") String cname) {
        //int tid = Integer.parseInt(tid1);
        System.out.print(tid);
        studentService.exportExpStudent(null, response,tid,cname);
    }
//    **************
//    @PostMapping("/showStudent")
//    public Map<String, Object> getTable(@RequestParam(value="limit") Integer limit, @RequestParam(value="offset") Integer offset,@RequestParam(value = "cname") String cname){
//        return studentService.PageShow(limit,offset,cname);
//    }
//    //}
//    @PostMapping("/addAllStudent")
//    public int addAllStudent(@RequestBody HashMap<String, Object> map) {
//        return studentService.addAllStudent(map);
//    }
//    public ResultJson delete(@RequestParam("id") int id){
//        Student student = studentService.selectById(id);
//        ResultJson r = new ResultJson();
//        if(student != null){
//            studentService.deleteStudent(id);
//            r.setSuccess(true);
//            r.setMessage("成功");
//            return r;
//        }else {
//            r.setMessage("id错误");
//            r.setSuccess(false);
//            return r;
//        }
//    }
//@PostMapping(value = "addbatch")
//public ResultJson addBatch(@RequestParam String data){
//    System.out.println(data);
//    String strlist = data;
//    List<Student> array = JSON.parseArray(strlist,Student.class);
//
//    return studentService.insertBatch(array);
//}

}
