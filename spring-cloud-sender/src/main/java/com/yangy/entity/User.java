package com.yangy.entity;

/**
 * 用户对象
 *
 * @author yangy
 * @email yangyang@lanqikeji.cn
 * @create 2018/7/14
 * @since 1.0.0
 */
public class User {

    private Long userId;
    private String userName;
    private Integer sex;
    private String phone;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}