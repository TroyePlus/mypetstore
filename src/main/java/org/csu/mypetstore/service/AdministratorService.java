package org.csu.mypetstore.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Administrator;
import org.csu.mypetstore.persistence.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdministratorService {
    
    @Autowired
    private AdministratorMapper administratorMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Administrator getAdministrator(String username) {
        return administratorMapper.getAdministratorByUsername(username);
    }

    public Administrator getAdministrator(String username, String password) {
        Administrator administrator = new Administrator();
        administrator.setUsername(username);
        administrator.setPassword(password);
        return administratorMapper.getAdministratorUsernameAndPassword(administrator);
    }


    @Transactional
    public void insertAdministrator(Administrator administrator) {
        String password = passwordEncoder.encode(administrator.getPassword());
        administrator.setPassword(password);
        administratorMapper.insertAdministrator(administrator);
        administratorMapper.insertSignon(administrator);
    }

    @Transactional
    public void updateAdministrator(Administrator administrator) {
        administratorMapper.updateAdministrator(administrator);

        if (administrator.getPassword() != null && administrator.getPassword().length() > 0) {
            administratorMapper.updateSignon(administrator);
        }
    }

    public PageInfo getList(Integer page, Integer limit, Administrator administrator){
        PageHelper.startPage(page, limit);
        List<Administrator> list = administratorMapper.getList(administrator);
        PageInfo<Administrator> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public void updateAdministratorStatusByUserName(Administrator administrator){
        administratorMapper.updateAdministrator(administrator);
    }

    @Transactional
    public void deleteAdministrator(Administrator administrator){
        administratorMapper.deleteAdministratorByUserName(administrator);
    }

}
