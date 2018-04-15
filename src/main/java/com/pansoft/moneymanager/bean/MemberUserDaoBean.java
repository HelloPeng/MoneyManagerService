package com.pansoft.moneymanager.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "p_memberUser")
@GenericGenerator(name = "alicomm-uuid", strategy = "uuid")
public class MemberUserDaoBean {
    @Id
    @Column(name = "m_oid")
    @GeneratedValue(generator = "alicomm-uuid")
    private String oid;
    @Column(name = "m_createtime")
    private String createTime;
    @Column(name = "m_username")
    private String userName;
    @Column(name = "m_userNo")
    private String userNo;
    @Column(name = "m_userPhone")
    private String userPhone;
    @Column(name = "m_parentoid")
    private String parentOid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getParentOid() {
        return parentOid;
    }

    public void setParentOid(String parentOid) {
        this.parentOid = parentOid;
    }

    /**
     * 有一个参数为空，则不可创建
     *
     * @return
     */
    public boolean isEmpty() {
        return userName == null || userNo == null || userPhone == null || parentOid == null;
    }
}
