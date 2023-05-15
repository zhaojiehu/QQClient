package com.QQServer.Server;

import java.io.Serializable;

/**
 * 信息类
 * 用于网路传输
 */

public class Massage implements Serializable,mesType {
    private static final long serialVersionUID = 1L;
    private String User;//用户
    private String Server;//服务者
    private String SentTime;//发送时间
    private String content;//发送内容
    private String mesType;//信息类型(可以在接口定义)
    private byte[] bytesFile;

    public byte[] getBytesFile() {
        return bytesFile;
    }

    public void setBytesFile(byte[] bytesFile) {
        this.bytesFile = bytesFile;
    }

    public Massage() {
    }

    public Massage(String user, String server, String sentTime,
                   String content, String mesType) {
        User = user;
        Server = server;
        SentTime = sentTime;
        this.content = content;
        this.mesType = mesType;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
    }

    public String getSentTime() {
        return SentTime;
    }

    public void setSentTime(String sentTime) {
        SentTime = sentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }
}
