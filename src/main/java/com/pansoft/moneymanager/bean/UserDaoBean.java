package com.pansoft.moneymanager.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "p_user")
@GenericGenerator(name = "alicomm-uuid", strategy = "uuid")
public class UserDaoBean {

    @Id
    @Column(name = "u_oid")
    @GeneratedValue(generator = "alicomm-uuid")
    private String oid;
    @Column(name = "u_createtime")
    private String createTime;
    @Column(name = "u_logintime")
    private String loginTime;
    @Column(name = "u_username")
    private String username;
    @Column(name = "u_password")
    private String password;



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

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
