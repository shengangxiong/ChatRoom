
package com.qq.client.view;

import com.qq.client.model.*;
import com.qq.client.tools.ManageClientConServerThread;
import com.qq.common.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 〈一句话功能简述〉<br> 
 *
 * @since 1.0.0//既然读取的任务已经交给线程了，qqCHat就没有必要做成线程了
 * */
public class QqChat extends JFrame implements ActionListener{

    JTextArea jta;
    JTextField jtf;
    JButton jb;
    JPanel jp;
    String ownerId;
    String friendId;
    public static void  main(String [] args){

        //QqChat qqChat=new QqChat("1");
    }
    public QqChat(String ownerId,String friend){
        this.ownerId=ownerId;
        this.friendId=friend;
        jta=new JTextArea();
        jtf=new JTextField(15);
        jb=new JButton("发送");
        jb.addActionListener(this);
        jp=new JPanel();
        jp.add(jtf);
        jp.add(jb);

        this.add(jta,"Center");
        this.add(jp,"South");
        this.setTitle(ownerId+" 正在和 "+friend+" 聊天");
        //下面这句话的作用是使聊天窗口左上角出现qq图标，然而在Mac似乎无法使用
        // this.setIconImage((new ImageIcon("image/qq.gif").getImage()));
        this.setSize(300,200);
        this.setVisible(true);


    }


    //写一个方法让它显示消息
    public void showMessage(Message m){
        String info=m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\r\n";
        this.jta.append(info);
    }

    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource()==jb){
            //如果用户点击了发送按钮
            Message m=new Message();
            m.setMesType(MessageType.message_comm_mes);
            m.setSender(this.ownerId);
            m.setGetter(this.friendId);
            m.setCon(jtf.getText());
            m.setSendTime(new java.util.Date().toString());
            //发送给服务器。
            try{
                ObjectOutputStream oos=new ObjectOutputStream
                        (ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
                //上面的大概就是我通过这个管理线程的类，取得了这个线程，再通过这个线程取得这个线程的socket
                //再通过这个socket取得输出流，最后实现发送
                oos.writeObject(m);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /*@Override
    public void run() {
        while(true){//要一直读取

            try {
                //读取[如果读不到就等待。]
                //这里就会出现问题：当1号打开两个聊天窗口时，它们就要争夺这一个socket
                //所以不能实现多对多：3给1发信1收不到，是因为1的socket被另一个占领了，报错：流并发异常
                ObjectInputStream ois = new ObjectInputStream(QqClientConServer.s.getInputStream());
                Message m=(Message)ois.readObject();

                //显示
                String info=m.getSender()+"对"+m.getGetter()+"说："+m.getCon()+"\r\n";
                this.jta.append(info);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }*/
}
