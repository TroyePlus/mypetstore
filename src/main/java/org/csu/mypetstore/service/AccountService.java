package org.csu.mypetstore.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.csu.mypetstore.persistence.LineItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public Account getAccount(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    public Account getAccount(String username, String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    public String getUsername(String phone){
        return accountMapper.getUserNameByPhone(phone);
    }

    @Transactional
    public void insertAccount(Account account) {
        String password = passwordEncoder.encode(account.getPassword());
        account.setPassword(password);
        accountMapper.insertAccount(account);
        //accountMapper.insertProfile(account);
        accountMapper.insertSignon(account);
    }

    @Transactional
    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
        //accountMapper.updateProfile(account);

        if (account.getPassword() != null && account.getPassword().length() > 0) {
            accountMapper.updateSignon(account);
        }
    }

    public PageInfo getList(Integer page,Integer limit,Account account){
        PageHelper.startPage(page, limit);
        List<Account> list = accountMapper.getList(account);
        PageInfo<Account> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public void updateAccountStatusByUserName(Account account){
        accountMapper.updateAccountStatusByUserName(account);
    }

    @Transactional
    public void deleteAccount(Account account){
        accountMapper.deletAccountByUserName(account);
        accountMapper.deletSignon(account);
    }


}
