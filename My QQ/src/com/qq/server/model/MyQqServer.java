/**

 * Description:这是qq服务器，它在监听，等待某个qq客户端来连接
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qq.server.model;
import com.qq.common.*;

import java.net.*;
import java.io.*;

public class MyQqServer {


    public MyQqServer(){

        try{

            //在9999监听
            System.out.println("我是服务器，在9999监听");
            ServerSocket ss=new ServerSocket(9999);
            //阻塞，等待连接
            while(true) {
                Socket s = ss.accept();

                //接受客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User u = (User) ois.readObject();
                System.out.println("服务器接收到用户id" + u.getUserId() + "密码" + u.getPasswd());
                Message m=new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                if (u.getPasswd().equals("123456")) {
                    //返回一个成功登录的信息包
                    m.setMesType("1");
                    oos.writeObject(m);
                    //这里就单开一个线程，让该线程与该客户端保持通讯
                    SerConClientThread scct=new SerConClientThread(s);
                    ManageClientThread.addClientThread(u.getUserId(),scct);
                    //启动与该客户端通信的线程
                    scct.start();

                    //并通知其他在线用户，把这个事情交给（上面与客户端保持通讯的线程自己去做最为合理
                    scct.notifyOther(u.getUserId());
                } else {
                    m.setMesType("2");
                    oos.writeObject(m);
                    //关闭连接
                    s.close();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally{

        }

    }

}
