package com.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by xfcq on 2016/7/3.
 */
@Entity
public class User {
    private int userNo;

    @Id
    @Column(name = "UserNo", nullable = false)
    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    private String userName;

    @Basic
    @Column(name = "UserName", nullable = true, length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userPwd;

    @Basic
    @Column(name = "UserPwd", nullable = true, length = 20)
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }    private Integer userStatus;

    @Basic
    @Column(name = "UserStatus", nullable = true)
    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userNo != user.userNo) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (userPwd != null ? !userPwd.equals(user.userPwd) : user.userPwd != null) return false;
        if (userStatus != null ? !userStatus.equals(user.userStatus) : user.userStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userNo;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPwd != null ? userPwd.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        return result;
    }
}
