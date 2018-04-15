package com.pansoft.moneymanager.dao;

import com.pansoft.moneymanager.bean.TradeDaoBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITradeDao extends JpaRepository<TradeDaoBean, String> {

    List<TradeDaoBean> findByParentOidAndTypeAndDateBetween(String parentOid, int type, String date, String date2);
}
