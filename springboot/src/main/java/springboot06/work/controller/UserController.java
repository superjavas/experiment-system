package springboot06.work.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springboot06.work.annotation.UserLoginToken;
import springboot06.work.pojo.*;
import springboot06.work.req.*;
import springboot06.work.service.*;
import springboot06.work.util.ResultJson;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService iuserService;
    @Autowired
    StudentService iStudentService;
    @Autowired
    TeacherService iTeacherService;
    @Autowired
    TokenService tokenService;

    public ResultJson list(){
        List<User> users = iuserService.queryUser();
        ResultJson r = new ResultJson();
        r.setData(users);
        return r;
    }
    /**
     * 登录
     * @Requestparam username
     * @Requestparam password
     * @return
     */
    @PostMapping("/login")
    public ResultJson login(@RequestBody LoginReq loginReq,HttpServletRequest request){
        String username = loginReq.getUsername();
        String password = loginReq.getPassword();
        String code = loginReq.getCode();
        String defaultKaptcha = (String) request.getSession().getAttribute("captcha");
        ResultJson r = new ResultJson();
        if (StringUtils.isEmpty(code) || !defaultKaptcha.equalsIgnoreCase(code)) {
            r.setMessage("验证码错误");
        }else{
            User user = iuserService.login(username,password);
            if(user==null){
                r.setSuccess(false);
                r.setMessage("用户名或密码错误");
            }else {
                String token = tokenService.getToken(user);
                System.out.print("level是"+user.getLevel());
                if(user.getLevel()==1){
                    Teacher teacher = iTeacherService.selectById(user.getUsername());
                    r.setData(teacher);
                }
                if(user.getLevel()==2){
                    Student student = iStudentService.selectById(user.getUsername());
                    //User user1 = iuserService.findUserById(user.getId());
                    r.setData(student);
                }
                if(user.getLevel()==3){
                    User user1 = iuserService.findUserById(user.getId());
                    r.setData(user1);
                }
                r.setCode(user.getId());
                r.setSuccess(true);
                r.setMessage("登录成功");
                r.setToken(token);
            }
        }
        return r;
    }
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        // 定义response输出类型为image/jpeg类型
        response.setContentType("image/jpeg");
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, mustrevalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        //-------------------生成验证码 begin --------------------------
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容：" + text);
        //将验证码放入session中
        request.getSession().setAttribute("captcha", text);
        //根据文本内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片，格式jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //-------------------生成验证码 end ----------------------------
    }
    @RequestMapping(value = "/setPicture",method = RequestMethod.POST)
    public JSONObject setPicture(@RequestBody PictureReq pictureReq){
        System.out.print(pictureReq);
        return iuserService.setPicture(pictureReq);
    }


    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    public JSONObject selectOldPwd(@RequestBody UpdatePwdReq updatePwdReq){
        JSONObject result=new JSONObject();
        int id = updatePwdReq.getId();
        User user = iuserService.findUserById(id);
        String oPassword = user.getPassword();
        String oldPassword = updatePwdReq.getOldPassword();
        String newPassword = updatePwdReq.getNewPassword();
        User user1 = new User();
        user1.setId(id);
        user1.setPassword(newPassword);
        System.out.print(updatePwdReq);
        System.out.print("数据库里的旧密码："+oPassword);
        System.out.print("输入的旧密码："+oldPassword);
        if(!oldPassword.equals(oPassword)){
            result.put("msg","旧密码错误");
        }else {
            result.put("msg","修改成功");
            iuserService.updatePassword(user1);
        }
        return result;
    }
    @GetMapping(value = "/exportStu", produces = "application/octet-stream")
    public void exportStu(HttpServletResponse response) {
        iStudentService.getStudent(null, response);
    }
    @RequestMapping(value = "/importStu", method = RequestMethod.POST)
    public RespBean importStu(@RequestPart("file") MultipartFile file) {
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
                user1=iuserService.findUserByUsername(studentMap.get("username"));
                if (user1==null){
                    iuserService.addUser(next1);
                    iStudentService.addStudent(next);
                    return RespBean.success("msg","添加成功");
                }else{
                    return RespBean.success("msg","用户已存在");
                }
            }
            file.getInputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.success("导入失败");
    }
    @GetMapping(value = "/exportTeacher", produces = "application/octet-stream")
    public void exportTeacher(HttpServletResponse response) {
        iTeacherService.getTeacher(null, response);
    }
    @RequestMapping(value = "/importTeacher", method = RequestMethod.POST)
    public RespBean importTeacher(@RequestPart("file") MultipartFile file) {
        ImportParams params = new ImportParams();
        JSONObject r = new JSONObject();
        params.setTitleRows(1);//表的标题行数，去掉前两行(第一行表标题，第二行列标题)，如果超出表格本身的标题行数，截取数据为null
        //params.setStartRows(0);//列标题和实际字段值的距离，可以设置从第几行开始截取数据，默认为0，就是列标题的下一行
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
            List<Teacher> studentList = ExcelImportUtil.importExcel(inputStream1, Teacher.class,params);
            List<User> userList = ExcelImportUtil.importExcel(inputStream2, User.class,params);
            Iterator<User> iterator1 = userList.iterator();
            Iterator<Teacher> iterator = studentList.iterator();

            while (iterator.hasNext()) {
                Teacher next = iterator.next();
                User next1=new User();
                next1.setUsername(next.getUsername());
                Map<String, String> studentMap = new HashMap<>();
                Map<String, String> userMap = new HashMap<>();
                studentMap.put("username", next.getUsername());
                studentMap.put("tname", next.getTname());
                userMap.put("username", next1.getUsername());
                userMap.put("tname", next.getTname());
                System.out.println(studentMap.get("username"));
                System.out.println(studentMap.get("tname"));
                System.out.println(userMap.get("username"));
                System.out.println(userMap.get("tname"));
                //各种学号 姓名 班级 性别 专业
                User user = new User();
                User user1=new User();
                user1=iuserService.findUserByUsername(studentMap.get("username"));
                if (user1==null){
                    iuserService.addTeacherUser(next1);
                    iTeacherService.addTeacher(next);
                    r.put("code",0);
                    r.put("msg","添加成功");
                }else{
                    r.put("msg","用户已存在");
                }
            }
            file.getInputStream().close();
            return RespBean.success("导入成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.success("导入失败");
    }
}
