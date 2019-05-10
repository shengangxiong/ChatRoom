/**
 * 这是用户信息类
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qq.client.common;

public class User implements java.io.Serializable{//序列化可以让一个对象在网络上传输

    private  String  userId;
    private  String passwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

}
