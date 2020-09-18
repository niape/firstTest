package cn.smbms.dao.user;

import cn.smbms.pojo.User;

import java.sql.Connection;

public interface UserMapper {
    public User getLoginUser(String userCode);

}
