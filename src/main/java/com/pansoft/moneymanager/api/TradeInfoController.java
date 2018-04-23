package com.pansoft.moneymanager.api;

import com.alibaba.fastjson.JSON;
import com.pansoft.moneymanager.base.BaseController;
import com.pansoft.moneymanager.bean.QMemberUserDaoBean;
import com.pansoft.moneymanager.bean.QTradeDaoBean;
import com.pansoft.moneymanager.bean.TradeDaoBean;
import com.pansoft.moneymanager.bean.TradeItemBean;
import com.pansoft.moneymanager.dao.ITradeDao;
import com.pansoft.moneymanager.utils.Dump;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class TradeInfoController extends BaseController {

    private final ITradeDao mTradeDao;
    private final JPAQueryFactory mJPAQueryFactory;

    @Autowired
    public TradeInfoController(ITradeDao mTradeDao, JPAQueryFactory factory) {
        this.mTradeDao = mTradeDao;
        mJPAQueryFactory = factory;
    }

    @PostMapping("/api/trade/add")
    public Dump addTradeInfo(@RequestBody String params) {
        TradeDaoBean daoBean = JSON.parseObject(params, TradeDaoBean.class);
        daoBean.setCreateTime(sf.format(new Date()));
        mTradeDao.saveAndFlush(daoBean);
        return Dump.success("添加成功", true);
    }

    @GetMapping("/api/trade/list")
    public Dump tradeListInfoTest(@RequestParam("parentOid") String parentOid,
                                  @RequestParam("page") long page,
                                  @RequestParam("pageNum") int pageNum) {
        QTradeDaoBean qTradeDaoBean = QTradeDaoBean.tradeDaoBean;
        QMemberUserDaoBean qMemberUserDaoBean = QMemberUserDaoBean.memberUserDaoBean;
        Predicate predicate = qTradeDaoBean.userOid.eq(qMemberUserDaoBean.oid);
        List<TradeItemBean> itemBeanList = mJPAQueryFactory
                .select(Projections.fields(TradeItemBean.class, qTradeDaoBean.date
                        , qTradeDaoBean.consumeTag
                        , qTradeDaoBean.money
                        , qTradeDaoBean.oid
                        , qTradeDaoBean.remarks
                        , qTradeDaoBean.type
                        , qMemberUserDaoBean.userName))
                .from(qTradeDaoBean, qMemberUserDaoBean)
                .where(qTradeDaoBean.parentOid.eq(parentOid).and(predicate))
                .orderBy(qTradeDaoBean.date.desc())
                .offset(pageNum * (page - 1)).
                        limit(pageNum).fetch();
        return Dump.success("获取成功", itemBeanList);
    }

    @PostMapping("/api/trade/del")
    @Transactional
    public Dump delTradeInfo(@RequestParam("oid") String oid) {
        QTradeDaoBean qTradeDaoBean = QTradeDaoBean.tradeDaoBean;
        try {
            mJPAQueryFactory
                    .delete(qTradeDaoBean)
                    .where(qTradeDaoBean.oid.eq(oid))
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
            return Dump.fail("执行删除是失败");
        }
        return Dump.success("执行删除成功", true);
    }

    @PostMapping("/api/trade/analysis")
    public Dump getTradeData(@RequestParam("parentOid") String parentOid,
                             @RequestParam("type") int type,
                             @RequestParam("month") int month) {
        String[] monthArray = new String[month];
        Date dNow = new Date();   //当前时间
        Date dBefore = null;
        Calendar calendar = Calendar.getInstance(); //得到日历
        for (int i = 0; i < month; i++) {
            calendar.setTime(dNow);
            calendar.add(Calendar.MONTH, -i);
            String tempDate = sf.format(calendar.getTime());
            String monthStr = tempDate.substring(5, 7);
            monthArray[i] = monthStr;
        }
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -month + 1);  //设置为前3月
        dBefore = calendar.getTime();   //得到前3月的时间
        String defaultStartDate = sf.format(dBefore);    //格式化前3月的时间
        calendar.setTime(dNow);
        calendar.add(Calendar.MONTH, 1);
        dNow = calendar.getTime();
        String defaultEndDate = sf.format(dNow); //格式化当前时间搜索
        List<TradeDaoBean> beanList = mTradeDao.findByParentOidAndTypeAndDateBetween(
                parentOid
                , type
                , defaultStartDate.substring(0, 7) + "%"
                , defaultEndDate.substring(0, 7) + "%"
        );
        Map<String, String> data = new HashMap<>();
        for (TradeDaoBean daoBean : beanList) {
            String monthStr = daoBean.getDate().substring(5, 7);
            if (data.containsKey(monthStr)) {
                String money = data.get(monthStr);
                BigDecimal a = new BigDecimal(money);
                BigDecimal b = new BigDecimal(daoBean.getMoney());
                data.put(monthStr, a.add(b).toString());
            } else {
                data.put(monthStr, daoBean.getMoney());
            }
        }
        Map<String, String> resultMap = new LinkedHashMap<>();
        for (int i = monthArray.length - 1; i >= 0; i--) {
            String monthStr = monthArray[i];
            resultMap.put(monthStr, data.getOrDefault(monthStr, "0.00"));
        }
        System.out.println("返回的数据为：" + JSON.toJSONString(resultMap));
        return Dump.success("执行成功", resultMap);
    }
}

