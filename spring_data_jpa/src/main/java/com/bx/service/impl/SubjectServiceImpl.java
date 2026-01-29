package com.bx.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.bx.annotation.AutoFill;
import com.bx.constant.FieldConstant;
import com.bx.constant.PageConstant;
import com.bx.entity.QSubject;
import com.bx.entity.Subject;
import com.bx.enumtype.OperationType;
import com.bx.repository.SubjectRepository;
import com.bx.service.SubjectService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Optional;

@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private EntityManager entityManager;

    /**
     * @param subject 课程
     * @return Long 课程ID
     * @description 添加课程
     */
    @Override
    @AutoFill(OperationType.ADD)
    public Long addSubject(Subject subject) {
        subjectRepository.save(subject);
        return subject.getId();
    }

    /**
     * @param id 课程ID
     * @return void
     * @description 删除课程
     */
    @Override
    public void deleteSubject(Long id) {
        if (id == null) {
            return;
        }
        subjectRepository.deleteById(id);
    }

    /**
     * @param map 查询条件
     * @return HashMap<String, Object> 课程列表
     * @description 分页查询课程列表
     */
    @Override
    public HashMap<String, Object> getSubjectList(HashMap<String, Object> map) {
        Integer pageNum = Optional.ofNullable((Integer) map.get("pageNum")).orElse(PageConstant.PAGENUM);
        Integer pageSize = Optional.ofNullable((Integer) map.get("pageSize")).orElse(PageConstant.PAGESIZE);
        QSubject qSubject = QSubject.subject;
        BooleanBuilder builder = new BooleanBuilder();

        HashMap<String, Object> condition = (HashMap) map.get("condition");

        if (CollUtil.isNotEmpty(condition)) {
            String name = (String) condition.get("name");
            String beginDate = (String) condition.get("beginDate");
            String endDate = (String) condition.get("endDate");
            if (StrUtil.isNotBlank(name)) {
                builder.and(qSubject.name.eq(name));
            }
            if (StrUtil.isNotBlank(beginDate) && StrUtil.isNotBlank(endDate)) {
                beginDate = beginDate.split("T")[0];
                endDate = endDate.split("T")[0] + FieldConstant.END_OF_DAY;
                builder.and(qSubject.createTime.stringValue().between(beginDate, endDate));
            }
        }

        QueryResults<Subject> page = new JPAQuery<Subject>(entityManager)
                .from(qSubject)
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
     * @param id 课程ID
     * @return Subject 课程
     * @description 根据ID查询课程
     */
    @Override
    public Subject getSubjectById(Long id) {
        if (id == null) {
            return null;
        }
        Subject subject = subjectRepository.findById(id).orElse(null);
        return subject;
    }

    /**
     * @param subject 课程
     * @return Long 课程ID
     * @description 更新课程
     */
    @Override
    @AutoFill(OperationType.UPDATE)
    public Long updateSubject(Subject subject) {
        subjectRepository.save(subject);
        return subject.getId();
    }
}