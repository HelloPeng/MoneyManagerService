package com.pansoft.moneymanager.api;

import com.alibaba.fastjson.JSON;
import com.pansoft.moneymanager.base.BaseController;
import com.pansoft.moneymanager.bean.TradeDaoBean;
import com.pansoft.moneymanager.dao.ITradeDao;
import com.pansoft.moneymanager.utils.Dump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class TradeInfoController extends BaseController {

    private final ITradeDao mTradeDao;

    @Autowired
    public TradeInfoController(ITradeDao mTradeDao) {
        this.mTradeDao = mTradeDao;
    }

    @PostMapping("/api/trade/add")
    public Dump addTradeInfo(@RequestBody String params) {
        TradeDaoBean daoBean = JSON.parseObject(params, TradeDaoBean.class);
        daoBean.setCreateTime(sf.format(new Date()));
        mTradeDao.saveAndFlush(daoBean);
        return Dump.success("添加成功", true);
    }

}
