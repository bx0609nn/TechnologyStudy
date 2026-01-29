package com.bx.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.bx.annotation.AutoFill;
import com.bx.constant.FieldConstant;
import com.bx.constant.PageConstant;
import com.bx.constant.StatusConstant;
import com.bx.entity.*;
import com.bx.enumtype.OperationType;
import com.bx.exception.BsException;
import com.bx.repository.AccountRepository;
import com.bx.repository.StudentRepository;
import com.bx.service.StudentService;
import com.bx.utils.NameUtil;
import com.bx.utils.RelationUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AccountRepository accountRepository;

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
        if (id == null) {
            return;
        }
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
     * @return Student 学生
     * @description 根据ID查询学生
     */
    @Override
    public Student getStudentById(Long id) {
        if (id == null) {
            return null;
        }
        Student student = studentRepository.findById(id).orElseThrow(() -> new BsException("该学生不存在，查询失败！"));
        Account account = student.getAccount();
        if (account != null) {
            account.setPassword(null);
        }
        return student;
    }

    /**
     * @param student 学生
     * @return Long 学生ID
     * @description 修改学生
     */
    @Override
    @Transactional
    @AutoFill(OperationType.UPDATE)
    public Long updateStudent(Student student) {
        Account account = student.getAccount();
        if (account == null) {
            throw new BsException("学生账户信息不能为空，修改失败！");
        }
        Student byStudent = studentRepository.findById(student.getId()).orElseThrow(() -> new BsException("该学生不存在，修改失败！"));
        Account byAccount = byStudent.getAccount();
        if (!byAccount.getId().equals(account.getId())) {
            throw new BsException("账户校验异常，修改失败！");
        }

        //校验关联数据
        RelationUtil.check()
                .relation(student.getSubjects(), byStudent.getSubjects(), Subject::getId, "课程")
                .relation(student.getTeachers(), byStudent.getTeachers(), Teacher::getId, "教师")
                .validate();

        account.setPassword(byAccount.getPassword());
        studentRepository.save(student);
        return student.getId();
    }

    /**
     * @param map 学生ID，账户ID，原密码，新密码
     * @return void
     * @description 修改账户密码
     */
    @Override
    @Transactional
    public void updateAccountPassword(HashMap<String, Object> map) {
        //获取参数
        Long id = Convert.toLong(map.get("id"));
        Long accountId = Convert.toLong(map.get("accountId"));
        String password = (String) map.get("password");
        String newPassword = (String) map.get("newPassword");
        //校验参数
        if (id == null || accountId == null || StrUtil.isBlank(password) || StrUtil.isBlank(newPassword)) {
            throw new BsException("参数不能为空，请检查输入的信息");
        }
        //校验新密码长度
        if (newPassword.length() < 6 || newPassword.length() > 24) {
            throw new BsException("新密码长度必须在6-24位之间");
        }
        //校验账户ID和原密码
        Student student = studentRepository.findById(id).orElseThrow(() -> new BsException("该学生不存在，修改失败！"));
        Account account = student.getAccount();
        if (!account.getId().equals(accountId)) {
            throw new BsException("账户校验异常，修改失败！");
        }
        if (!account.getPassword().equals(password)) {
            throw new BsException("原密码错误，修改失败！");
        }
        accountRepository.updatePasswordById(newPassword, accountId);
    }

}