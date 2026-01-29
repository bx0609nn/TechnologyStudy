package com.bx.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.bx.annotation.AutoFill;
import com.bx.constant.FieldConstant;
import com.bx.constant.PageConstant;
import com.bx.entity.QTeacher;
import com.bx.entity.Teacher;
import com.bx.enumtype.OperationType;
import com.bx.repository.TeacherRepository;
import com.bx.service.TeacherService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/9 17:20
 * @description
 */
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private EntityManager entityManager;

    /**
     * @param teacher 教师
     * @return Long 教师ID
     * @description 添加教师
     */
    @Override
    @AutoFill(OperationType.ADD)
    public Long addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacher.getId();
    }

    /**
     * @param id 教师ID
     * @description 删除教师
     */
    @Override
    public void deleteTeacher(Long id) {
        if (id == null) {
            return;
        }
        teacherRepository.deleteById(id);
    }

    /**
     * @param map 查询条件
     * @return HashMap<String, Object> 教师列表
     * @description 分页查询教师列表
     */
    @Override
    public HashMap<String, Object> getTeacherList(HashMap<String, Object> map) {
        Integer pageNum = Optional.ofNullable((Integer) map.get("pageNum")).orElse(PageConstant.PAGENUM);
        Integer pageSize = Optional.ofNullable((Integer) map.get("pageSize")).orElse(PageConstant.PAGESIZE);
        QTeacher qTeacher = QTeacher.teacher;
        BooleanBuilder builder = new BooleanBuilder();

        HashMap<String, Object> condition = (HashMap) map.get("condition");
        if (CollUtil.isNotEmpty(condition)) {
            String name = (String) condition.get("name");
            Integer age = (Integer) condition.get("age");
            String gender = (String) condition.get("gender");
            String beginDate = (String) condition.get("beginDate");
            String endDate = (String) condition.get("endDate");
            if (StrUtil.isNotBlank(name)) {
                builder.and(qTeacher.name.eq(name));
            }
            if (age != null) {
                builder.and(qTeacher.age.eq(age));
            }
            if (StrUtil.isNotBlank(gender)) {
                builder.and(qTeacher.gender.eq(gender));
            }
            if (StrUtil.isNotBlank(beginDate) && StrUtil.isNotBlank(endDate)) {
                beginDate = beginDate.split("T")[0];
                endDate = endDate.split("T")[0] + FieldConstant.END_OF_DAY;
                builder.and(qTeacher.createTime.stringValue().between(beginDate, endDate));
            }
        }
        QueryResults<Teacher> page = new JPAQuery<Teacher>(entityManager)
                .from(qTeacher)
                .where(builder)
                .offset((pageNum - 1) * pageSize)
                .limit(pageSize)
                .fetchResults();

        long count = page.getTotal();
        long totalPages = (count + pageSize - 1) / pageSize;

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", page.getResults());
        result.put("totalPages", totalPages);
        result.put("recordCount", count);
        return result;
    }

    /**
     * @param id 教师ID
     * @return Teacher 教师
     * @description 根据ID查询教师
     */
    @Override
    public Teacher getTeacherById(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        return teacher;
    }

    /**
     * @param teacher 教师
     * @return Long 教师ID
     * @description 更新教师
     */
    @Override
    @AutoFill(OperationType.UPDATE)
    public Long updateTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
        return teacher.getId();
    }
}
