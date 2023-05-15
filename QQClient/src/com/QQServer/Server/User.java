package com.QQServer.Server;

import java.io.Serializable;

/**
 * 用户类
 *
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ID;//用户ID;
    private String name;//用户姓名;
    private String password;//用户密码;

    public User(String ID, String name, String password) {
        this.ID = ID;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
