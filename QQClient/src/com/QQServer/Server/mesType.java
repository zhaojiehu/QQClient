package com.QQServer.Server;

public interface mesType {
    String MESSAGE_LOCAL_SUCCESS = "登录成功";
    String MESSAGE_LOCAL_FALSE = "登录失败";
    String MESSAGE_CREATE_SUCCESS = "注册成功";
    String MESSAGE_CREATE_FALSE = "注册失败";
    String MESSAGE_LOCAL_DISCONNECT = "网络断开";
    String MESSAGE_GET_ONLINE_FRIEND="获取列表";
    String MESSAGE__ONLINE_FRIEND_LIST="用户列表";
    String MESSAGE_PRIVATE_WECHAT="私发信息";
    String MESSAGE_PRIVATE_SENT_FALSE="私送失败";
    String MESSAGE_PRIVATE_SENT_SUCCESS="私送成功";
    String MESSAGE_PUBLIC_WECHAT="群发信息";
    String MESSAGE_SEND_FILE="预发送文件";
    String MESSAGE_SET_FILE_NO="不接收文件";
    String MESSAGE_SET_FILE_YES="接收文件";
    String MESSAGE_REAL_SEND_FILE="发送文件";
    String MESSAGE_NEWS="新闻";
}
