package com.pansoft.moneymanager.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_trade")
@GenericGenerator(name = "alicomm-uuid", strategy = "uuid")
public class TradeDaoBean {
    @Id
    @Column(name = "t_oid")
    @GeneratedValue(generator = "alicomm-uuid")
    private String oid;
    @Column(name = "m_uoid")
    private String parentOid;
    @Column(name = "m_moid")
    private String userOid;//成员用户对oid
    @Column(name = "m_type", length = 1)
    private int type;//0是支出 1是收入
    @Column(name = "m_date")
    private String date;//时间
    @Column(name = "m_consume_tag")
    private String consumeTag;//消费的Tag
    @Column(name = "m_remarks")
    private String remarks;//备注信息
    @Column(name = "m_money")
    private String money;//金额
    @Column(name = "m_create_time")
    private String createTime;//创建时间

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getParentOid() {
        return parentOid;
    }

    public void setParentOid(String parentOid) {
        this.parentOid = parentOid;
    }

    public String getUserOid() {
        return userOid;
    }

    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConsumeTag() {
        return consumeTag;
    }

    public void setConsumeTag(String consumeTag) {
        this.consumeTag = consumeTag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
