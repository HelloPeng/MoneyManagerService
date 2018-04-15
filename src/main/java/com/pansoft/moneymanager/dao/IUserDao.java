package com.pansoft.moneymanager.dao;

import com.pansoft.moneymanager.bean.UserDaoBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<UserDaoBean, String> {

    UserDaoBean findByUsername(String username);

}
