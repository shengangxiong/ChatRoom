
package com.qq.client.tools;

import java.util.*;
/**
 * 这是一个管理客户端和服务器保持通讯的线程类
 * @since 1.0.0
 */
public class ManageClientConServerThread {

    //这里可以做成静态的：哪怕一台机器登录很多的qq号，hashmap只有一个
    private static HashMap hm=new HashMap<String,ClientConServerThread>();//k存放登录的qq的号码

    //把创建好的ClientConServerThread放入到hm
    public static void addClientConServerThread(String qqId,ClientConServerThread ccst){
        hm.put(qqId,ccst);
    }

    //可以通过qqId取得该线程
    public static ClientConServerThread getClientConServerThread(String qqId){
        return (ClientConServerThread)hm.get(qqId);
    }

}
