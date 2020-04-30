package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Administrator;
import org.csu.mypetstore.persistence.AdministratorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
