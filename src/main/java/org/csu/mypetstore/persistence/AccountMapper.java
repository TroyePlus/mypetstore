package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {
    Account getAccountByUsername(String username);

    Account getAccountByUsernameAndPassword(Account account);

    void updateAccountStatusByUserName(Account account);

    String getUserNameByPhone(String phone);

    void insertAccount(Account account);

    void insertProfile(Account account);

    void insertSignon(Account account);

    void updateAccount(Account account);

    void updateProfile(Account account);

    void updateSignon(Account account);

    List<Account> getList(Account account);

    void deletAccountByUserName(Account account);

    void deletSignon(Account account);
}
