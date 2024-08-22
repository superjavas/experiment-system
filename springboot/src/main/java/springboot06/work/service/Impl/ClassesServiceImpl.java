package springboot06.work.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot06.work.mapper.ClassesMapper;
import springboot06.work.pojo.Classes;
import springboot06.work.service.ClassesService;

import java.util.List;

@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    ClassesMapper classesMapper;
    @Override
    public void creatClass(Classes classes) {
        classesMapper.creatClass(classes);
    }

    @Override
    public List<String> selectClass(int tid) {
        return classesMapper.selectClass(tid);
    }

    @Override
    public Classes selectByCname(String cname ,int tid) {
        return classesMapper.selectByCname(cname,tid);
    }
    @Override
    public Classes deleteCourse(String cname ,int tid) {
        return classesMapper.deleteCourse(cname,tid);
    }
}
