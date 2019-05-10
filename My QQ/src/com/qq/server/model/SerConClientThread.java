
package com.qq.server.model;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.common.*;
import java.net.Socket;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 功能：是服务器和某个客户端的通讯线程
 * 〈〉
 */
public class SerConClientThread extends Thread{

    Socket s;

    public  SerConClientThread(Socket s){
        //把服务器与该客户端的连接赋给s；
        this.s=s;
    }

    //让该线程去通知其他用户
    public void notifyOther(String iam){//I am
        //得到所有在线的人的线程，，在这里所有线程都被爆存在ManageClientThread这个类里
        HashMap hm=ManageClientThread.hm;
        Iterator it=hm.keySet().iterator();

        while(it.hasNext()){
            Message m=new Message();
            m.setCon(iam);
            m.setMesType(MessageType.message_ret_onLineFriend);
            //取出在线人的id
            String onLineUserId=it.next().toString();
            try {
                ObjectOutputStream oos = new ObjectOutputStream
                        (ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
                m.setGetter(onLineUserId);
                oos.writeObject(m);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void run(){

        while(true){

            //这里该线程就可以接收客户端的信息
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m=(Message)ois.readObject();

                //System.out.println(m.getSender()+"给"+m.getGetter()+"说"+m.getCon());

                //对从客户端取得的消息进行类型判断然后做相应的处理
                if(m.getMesType().equals(MessageType.message_comm_mes)) {
                    //一会儿完成转发
                    //取得接收人的通信线程
                    SerConClientThread sc = ManageClientThread.getClientThread(m.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());//这个s是接收人和服务器的连接
                    oos.writeObject(m);
                }else if(m.getMesType().equals(MessageType.message_get_onLineFriend)){
                    System.out.println(m.getSender()+" 要他的好友");
                    //把在服务器的好友给该客户端返回
                    //怎么拿到在线的人？？hashmap是可以遍历的。交给管理者做
                    String res= ManageClientThread.getAllOnlineUserid();//返回来了
                    Message m2=new Message();//返回一个新的message，包含它需要的信息
                    m2.setMesType(MessageType.message_ret_onLineFriend);//设置类型，返回好友在线
                    m2.setCon(res);//把类放进去
                    m2.setGetter(m.getSender());//表明这个是给谁的，返回给谁的：拿到message的时候本身有一个sender
                    ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());//不能用上面的oos；那个是转发的，这个是回送给谁的
                    oos.writeObject(m2);
                }

            }catch(Exception e){
                e.printStackTrace();

            }
        }

    }
}
