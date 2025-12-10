package com.bx.service.impl;

import com.bx.repository.TeacherRepository;
import com.bx.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
