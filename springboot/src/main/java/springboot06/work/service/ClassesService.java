package springboot06.work.service;

import springboot06.work.pojo.Classes;

import java.util.List;

public interface ClassesService {
    void creatClass(Classes classes);
    List<String> selectClass(int tid);
    Classes selectByCname(String cname ,int tid);
    Classes deleteCourse(String cname ,int tid);
}
