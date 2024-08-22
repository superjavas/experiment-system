package springboot06.work.service;

import com.alibaba.fastjson.JSONObject;
import springboot06.work.pojo.Grade;
import springboot06.work.pojo.Student;

public interface GradeService {
    JSONObject getGradeList(int page, int limit, Grade grade);
    JSONObject updateGrade(Grade grade);
    void deleteGrade(int id);
}
