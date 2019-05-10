
package com.qq.client.model;
//import com.qq.client.common.*;
import com.qq.client.tools.ClientConServerThread;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.common.*;

import java.util.*;
import java.net.*;
import java.io.*;


/**
 * 这是客户端连接服务器的后台
 * @author trhjyhf
 * @create 2019/4/18
 * @since 1.0.0
 */
public class QqClientConServer {
    public  Socket s;//不管有多少个用户登录，它们都共享一个链接

   //发送第一次请求;单写一份的原因是只要聊天发的都是message内容，有一个初始化的过程
    public boolean sendLoginInfoToServer(Object o){//这个oblect其实是一个user

        boolean b=false;
        try {
            System.out.println("kk");
            s=new Socket("127.0.0.1",9999);
            ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(o);//发出去，之后那边会返回结果，发信息要验证是否合法

            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());

            Message ms = null;
            try {

                ms =(Message)ois.readObject();//

           } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //这里就是验证登录的地方
            if(ms.getMesType().equals("1")){
                //就创建一个该qq和服务器端保持通讯连接的线程
                ClientConServerThread ccst=new ClientConServerThread(s);
                //启动该通讯线程
                ccst.start();//也就是说当一个人登录之后，他就启动了自己的线程，开始与服务器保持连接
                ManageClientConServerThread.addClientConServerThread
                        (((User)o).getUserId(),ccst);//登录者的QQ号；ccst是创建的线程
                b=true;
            }else{
                //关闭socket
                s.close();
            }



        } catch (IOException e) {
            e.printStackTrace();//异常一定打印
        }finally {

        }


        return b;

    }



   public void SendInfoToServer(Object o){

       try {
           Socket s=new Socket("127.0.0.1",9999);

       } catch (IOException e) {
           e.printStackTrace();//异常一定打印
       }finally {

       }

   }


}
