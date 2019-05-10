
package com.qq.client.model;
import com.qq.common.*;
/**
 *相比之下，MyQqClientServer是更后台的，相当于一个和服务器交互的类；
 * 客户端不会和数据库联系，server更有数据库的意思
 * 只要是涉及到对用户的操作都走这个业务逻辑类
 */
//通过验证返回这个用户是真还是假决定能不能往下走
public class QqClientUser {

    public boolean checkUser(User u){//传进来一个User对象，在点击登录按钮后拿到
                                     //通过验证返回是真还是假，决定能不能往下走
        return  new QqClientConServer().sendLoginInfoToServer(u);
    }//这层是给chat这个界面服务的

}
