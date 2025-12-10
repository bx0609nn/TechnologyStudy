package com.bx.service.impl;

import com.bx.entity.QSubject;
import com.bx.entity.Subject;
import com.bx.repository.SubjectRepository;
import com.bx.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;



}