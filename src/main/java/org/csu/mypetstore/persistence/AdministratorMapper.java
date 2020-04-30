package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Administrator;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorMapper {
    Administrator getAdministratorByUsername(String username);

    Administrator getAdministratorUsernameAndPassword(Administrator administrator);

    void insertAdministrator(Administrator administrator);

    void insertSignon(Administrator administrator);

    void updateAdministrator(Administrator administrator);

    void updateSignon(Administrator administrator);
}
