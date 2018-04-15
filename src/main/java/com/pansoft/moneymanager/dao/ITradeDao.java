package com.pansoft.moneymanager.dao;

import com.pansoft.moneymanager.bean.TradeDaoBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITradeDao extends JpaRepository<TradeDaoBean, String> {

}
