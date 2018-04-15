package com.pansoft.moneymanager.dao;

import com.pansoft.moneymanager.bean.MemberUserDaoBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMemberUserDao extends JpaRepository<MemberUserDaoBean, String> {

}
