package com.pansoft.moneymanager.api;

import com.alibaba.fastjson.JSON;
import com.pansoft.moneymanager.base.BaseController;
import com.pansoft.moneymanager.bean.MemberUserDaoBean;
import com.pansoft.moneymanager.bean.QMemberUserDaoBean;
import com.pansoft.moneymanager.bean.UserDaoBean;
import com.pansoft.moneymanager.dao.IMemberUserDao;
import com.pansoft.moneymanager.dao.IUserDao;
import com.pansoft.moneymanager.utils.Dump;
import com.pansoft.moneymanager.utils.MD5Utils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class UserController extends BaseController {

    private final IUserDao mUserDao;
    private final IMemberUserDao mMemberUserDao;
    private final JPAQueryFactory mJPAQueryFactory;

    @Autowired
    public UserController(IUserDao iUserDao, IMemberUserDao iMemberUserDao, JPAQueryFactory jpaQueryFactory) {
        mUserDao = iUserDao;
        mMemberUserDao = iMemberUserDao;
        mJPAQueryFactory = jpaQueryFactory;
    }

    @GetMapping("/api/base/check")
    public Dump checkApi() {
        return Dump.success(true);
    }

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/api/user/login")
    public Dump login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (username == null || password == null)
            return Dump.fail("用户名或密码不能为空");
        UserDaoBean userBean = mUserDao.findByUsername(username);
        if (userBean == null) {
            return Dump.fail("未找到该用户，请注册后登录");
        }
        password = MD5Utils.MD5(password);
        String dbPassword = userBean.getPassword();
        if (!password.equals(dbPassword)) {
            return Dump.fail("密码输入错误");
        }
        userBean.setLoginTime(sf.format(new Date()));
        mUserDao.flush();
        return Dump.success("登录成功", userBean.getOid());
    }

    /**
     * 注册接口
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/api/user/register")
    public Dump register(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        if (username == null || password == null)
            return Dump.fail("用户名或密码不能为空");
        UserDaoBean userBean = mUserDao.findByUsername(username);
        if (userBean != null) {
            return Dump.fail("对不起该用户名已注册，请更换用户名后重试");
        }
        UserDaoBean newUserBean = new UserDaoBean();
        newUserBean.setUsername(username);
        newUserBean.setPassword(MD5Utils.MD5(password));
        newUserBean.setCreateTime(sf.format(new Date()));
        mUserDao.saveAndFlush(newUserBean);
        return Dump.success("恭喜您注册成功", true);
    }

    /**
     * 添加该用户的指定的成员
     *
     * @param params 该成员信息的Json数据
     * @return 返回添加的状态
     */
    @PostMapping("/api/user/member/add")
    public Dump addMemberUser(@RequestBody String params) {
        MemberUserDaoBean userDaoBean = JSON.parseObject(params, MemberUserDaoBean.class);
        if (userDaoBean.isEmpty()) {
            return Dump.fail("请补充全信息后提交");
        }
        QMemberUserDaoBean qmu = QMemberUserDaoBean.memberUserDaoBean;
        List<MemberUserDaoBean> memberUserList = mJPAQueryFactory.selectFrom(qmu)
                .where(qmu.parentOid.eq(userDaoBean.getParentOid())
                        .and(qmu.userNo.eq(userDaoBean.getUserNo()))
                        .or(qmu.userName.eq(userDaoBean.getUserName()))
                        .or(qmu.userPhone.eq(userDaoBean.getUserPhone()))).fetch();
        if (!memberUserList.isEmpty()) {
            MemberUserDaoBean alreadyExistBean = memberUserList.get(0);
            String alreadyExistKey = "该用户已被添加";
            if (alreadyExistBean.getUserNo().equals(userDaoBean.getUserNo())) {
                alreadyExistKey = "该用户编号已存在";
            } else if (alreadyExistBean.getUserName().equals(userDaoBean.getUserName())) {
                alreadyExistKey = "该用户名称已存在";
            } else if (alreadyExistBean.getUserPhone().equals(userDaoBean.getUserPhone())) {
                alreadyExistKey = "该用户手机号已存在";
            }
            return Dump.fail(alreadyExistKey);
        }
        userDaoBean.setCreateTime(sf.format(new Date()));
        mMemberUserDao.saveAndFlush(userDaoBean);
        return Dump.success("恭喜您添加成功", true);
    }

    /**
     * 请求获取该用户下所有的成员
     *
     * @param parentOid 用户oid
     * @param page      页码（不传页面会返回所有数量）
     * @param pageNum   每页的数量（默认20条）
     * @return 指定页面中成员列表信息
     */
    @GetMapping("/api/user/member/list")
    public Dump getMemberUserList(@RequestParam("parentOid") String parentOid,
                                  @RequestParam(value = "page", defaultValue = "-1") long page,
                                  @RequestParam(name = "pageNum", defaultValue = "20") int pageNum) {
        QMemberUserDaoBean bean = QMemberUserDaoBean.memberUserDaoBean;
        List<MemberUserDaoBean> memberUserList;
        try {
            JPAQuery<MemberUserDaoBean> jpaQuery = mJPAQueryFactory
                    .selectFrom(bean)
                    .where(bean.parentOid.eq(parentOid))
                    .orderBy(bean.createTime.desc());
            if (page > 0) {
                memberUserList = jpaQuery.
                        offset(pageNum * (page - 1)).
                        limit(pageNum).fetch();
            } else {
                memberUserList = jpaQuery.fetch();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Dump.fail("执行异常");
        }
        return Dump.success("查询成功", memberUserList);
    }

}
