
package com.qq.client.tools;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;

import java.net.Socket;
import java.io.*;
import java.net.*;

/**
 * 这是客户端和服务器端保持通讯的线程
 * 只要有一个账号登录就给他一个线程，线程连接服务器
 */
public class ClientConServerThread extends Thread{

    private Socket s;
    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }


    //构造函数
    public ClientConServerThread(Socket s){
        this.s=s;
    }
    public void run(){
        while(true){
            //不停读取从服务器端发来的消息
            try{

                ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
                Message m=(Message)ois.readObject();
                /*System.out.println("读取到从服务器发来的消息"+m.getSender()+"给"+m.getGetter()+"内容"+
                        m.getCon());
                  //服务器不停读message，也就是你还以为它一定有getter 和sender。然而这未必。因为它未必是那种普通包
                  */

                if(m.getMesType().equals(MessageType.message_comm_mes)){

                    //把从服务器获得的消息显示到该显示的聊天界面
                    QqChat qqChat=ManageQqChat.getQqChat(m.getGetter()+" "+m.getSender());
                    //实现显示，最好的方法是在QqChat里建一个方法让它去显示
                    qqChat.showMessage(m);

                }else if(m.getMesType().equals(MessageType.message_ret_onLineFriend)){
                    System.out.println("客户端接收到"+m.getCon());
                    String con=m.getCon();
                    String friends[]=con.split(" ");//??????????????????????
                    String getter=m.getGetter();//服务器回送的时候就是getter，你刚才给服务器发的那个
                    System.out.println("getter="+getter);
                    //修改相应的好友列表
                    QqFriendList qqFriendList=ManageQqFriendList.getQqFriendList(getter);
                    //拿到好友列表后还要修改qqfriendlist，所以qqfiendlist应该再提供一个方法
                    //更新在线好友
                    if(qqFriendList!=null) {
                        qqFriendList.upateFriend(m);
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
