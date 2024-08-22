package springboot06.work.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06.work.mapper.GradeMapper;
import springboot06.work.pojo.Grade;
import springboot06.work.pojo.Student;
import springboot06.work.service.GradeService;

import java.util.List;
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    GradeMapper gradeMapper;
    @Override
    public JSONObject getGradeList(int page, int limit, Grade grade) {
        JSONObject result=new JSONObject();
        //接收数据
        try {
            PageHelper.startPage(page,limit);
            List<Grade> list = gradeMapper.findAllGrade(grade);
            PageInfo<Grade> pageInfo=new PageInfo<>(list);
            result.put("code","0"); //设置状态码
            result.put("msg","success"); //设置信息
            result.put("data",pageInfo.getList()); //获取数据集合
            result.put("count",pageInfo.getTotal()); //获取分页总数
        } catch (Exception e) {
            List<Grade> list = gradeMapper.findAllGrade(grade);
            result.put("code","500");
            result.put("msg","查询异常！");
            System.out.print("查找全部数据"+list);
        }
        return result;
    }
    @Override
    public JSONObject updateGrade(Grade grade) {
        JSONObject result=new JSONObject();
        try {
            gradeMapper.updateGrade(grade);
            result.put("code",0);
            result.put("msg","修改,主键id为"+grade.getId());
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","异常,修改失败！");
            e.printStackTrace();
        }
        return result;
    }
    public void deleteGrade(int  id) {
        /*使用JSON*/
        gradeMapper.deleteGrade(id);

    }
}
