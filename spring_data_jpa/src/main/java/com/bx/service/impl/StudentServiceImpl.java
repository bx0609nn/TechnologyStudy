package com.bx.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.bx.annotation.AutoFill;
import com.bx.constant.FieldConstant;
import com.bx.constant.PageConstant;
import com.bx.constant.StatusConstant;
import com.bx.entity.Account;
import com.bx.entity.QStudent;
import com.bx.entity.Student;
import com.bx.enumtype.OperationType;
import com.bx.repository.StudentRepository;
import com.bx.service.StudentService;
import com.bx.utils.NameUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * @param student 学生
     * @return Long 学生ID
     * @description 添加学生
     */
    @AutoFill(OperationType.ADD)
    public Long addStudent(Student student) {
        //如果添加的账户为空，则创建一个默认账户
        if (student.getAccount() == null) {
            Account account = new Account();
            account.setUsername(student.getName() + NameUtil.generateSuffix());
            account.setPassword(FieldConstant.PASSWORD);
            account.setRole(StatusConstant.ONE);
            student.setAccount(account);
            account.setCreateTime(new Date());
            account.setUpdateTime(new Date());
        }
        studentRepository.save(student);
        return student.getId();
    }

    /**
     * @param id 学生ID
     * @return void
     * @description 删除学生
     */
    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    /**
     * @param map 查询条件
     * @return HashMap<String, Object> 学生列表
     * @description 查询学生列表
     */
    @Override
    public HashMap<String, Object> getStudentList(HashMap<String, Object> map) {
        Integer pageNum = Optional.ofNullable((Integer) map.get("pageNum")).orElse(PageConstant.PAGENUM);
        Integer pageSize = Optional.ofNullable((Integer) map.get("pageSize")).orElse(PageConstant.PAGESIZE);
        QStudent qStudent = QStudent.student;
        BooleanBuilder builder = new BooleanBuilder();
        HashMap<String, Object> condition = (HashMap) map.get("condition");
        if (CollUtil.isNotEmpty(condition)) {
            Integer age = (Integer) condition.get("age");
            String gender = (String) condition.get("gender");
            String beginDate = (String) condition.get("beginDate");
            String endDate = (String) condition.get("endDate");

            if (age != null) {
                builder.and(qStudent.age.eq(age));
            }
            if (StrUtil.isNotBlank(gender)) {
                builder.and(qStudent.gender.eq(gender));
            }
            if (StrUtil.isNotBlank(beginDate) && StrUtil.isNotBlank(endDate)) {
                beginDate = beginDate.split("T")[0];
                endDate = endDate.split("T")[0] + FieldConstant.END_OF_DAY;
                builder.and(qStudent.createTime.stringValue().between(beginDate, endDate));
            }
        }

        QueryResults<Student> page = new JPAQuery<Student>(entityManager)
                .select(Projections.bean(Student.class, qStudent.id, qStudent.name, qStudent.age, qStudent.gender, qStudent.email, qStudent.createTime, qStudent.updateTime))
                .from(qStudent)
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
     * @param id 学生ID
     * @return Long 学生
     * @description 根据ID查询学生
     */
    @Override
    public Student getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return student;
    }

    /**
     * @param student 学生
     * @return Long 学生ID
     * @description 修改学生
     */
    @Override
    @AutoFill(OperationType.UPDATE)
    public Long updateStudent(Student student) {
        studentRepository.save(student);
        return student.getId();
    }


}