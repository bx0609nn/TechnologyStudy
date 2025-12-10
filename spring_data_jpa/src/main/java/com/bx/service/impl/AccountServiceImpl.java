package com.bx.service.impl;

import com.bx.entity.Account;
import com.bx.entity.QAccount;
import com.bx.repository.AccountRepository;
import com.bx.service.AccountService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private Long addAcount(Account account) {
        accountRepository.save(account);
        QAccount qAccount = QAccount.account;
        BooleanBuilder builder = new BooleanBuilder();


        return account.getId();


    }


}