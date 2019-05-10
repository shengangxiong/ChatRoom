/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: QqClientLogin
 * Author:   trhjyhf
 * Date:     2019/4/5 14:06
 * Description:qq客户端登录界面
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qq.client.view;

import com.qq.client.model.QqClientConServer;
import com.qq.client.model.QqClientUser;
import com.qq.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import com.qq.client.tools.*;

/**
 * 〈一句话功能简述〉<br>
 */
public class QqClientLogin extends JFrame implements ActionListener{

    //定义北部需要的组件
    JLabel jbl1;
    //定义中部需要的组件
        //中部有三个JPanel，有一个叫选项卡窗口组件
    JTabbedPane jtp;//选项卡窗口，可以切换

    JPanel jp2,jp3,jp4;//jp2放一个网格布局，里面有九个控件；jp34是另外两个，手机号码等
    //定义九个小控件
    JLabel jp2_jbl1,jp2_jbl2,jp2_jbl3,jp2_jbl4;//label4个
    JButton jp2_jb1;//button一个
    JTextField jp2_jtf;//文本框一个
    JPasswordField jp2_jpf;//密码框一个
    JCheckBox jp2_jcb1,jp2_jcb2;//勾选框，共九个了




    //定义南部需要的组件

    JPanel jp1;
    JButton jp1_jb1,jp1_jb2,jp1_jb3;//三个按钮

    public static void main(String[] args){
        QqClientLogin qqClientLogin=new QqClientLogin();

    }
    public QqClientLogin(){
        //处理北边
        jbl1=new JLabel(new ImageIcon("image/tou.gif"));

        //处理中部
        jp2=new JPanel(new GridLayout(3,3));//三行三列
        //先处理lable
        jp2_jbl1 = new JLabel("QQ号码",JLabel.CENTER);
        jp2_jbl2 = new JLabel("QQ密码",JLabel.CENTER);
        jp2_jbl3= new JLabel("忘记密码",JLabel.CENTER);
        jp2_jbl3.setForeground(Color.blue);//前景色变蓝色，即字体颜色
        jp2_jbl4 = new JLabel("申请密码保护",JLabel.CENTER);

        jp2_jb1=new JButton(new ImageIcon("image/clear.gif"));

        jp2_jtf=new JTextField();
        jp2_jpf=new JPasswordField();
        jp2_jcb1=new JCheckBox("隐身登录");
        jp2_jcb2=new JCheckBox("记住密码");

        //把控件按照顺序加入到jp2,按行从上到下
        jp2.add(jp2_jbl1);
        jp2.add(jp2_jtf);
        jp2.add(jp2_jb1);
        jp2.add(jp2_jbl2);
        jp2.add(jp2_jpf);
        jp2.add(jp2_jbl3);
        jp2.add(jp2_jcb1);
        jp2.add(jp2_jcb2);
        jp2.add(jp2_jbl4);
        //创建选项卡窗口
        jtp=new JTabbedPane();//务必记得创建！要不就是空指针了
        jtp.add("QQ号码",jp2);
        jp3=new JPanel();
        jtp.add("手机号码",jp3);
        jp4=new JPanel();
        jtp.add("电子邮件",jp4);



        //处理南部
        jp1=new JPanel();//这里默认就是流布局、居中的
        jp1_jb1=new JButton(new ImageIcon("image/denglu.gif"));//登陆图片，按钮为图
        ////响应用户点击登录
        jp1_jb1.addActionListener(this);
        jp1_jb2=new JButton(new ImageIcon("image/quxiao.gif"));
        jp1_jb3=new JButton(new ImageIcon("image/xiangdao.gif"));

        //把三个按钮放入到jp1
        jp1.add(jp1_jb1);
        jp1.add(jp1_jb2);
        jp1.add(jp1_jb3);


        this.add(jbl1,"North");
        this.add(jtp,"Center");
        //把jp1放在南面
        this.add(jp1,"South");
        this.setSize(350,240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.setLocation(450,250);
    }

    ////
    public void actionPerformed(ActionEvent arg0) {
        //如果用户点击登录
        if(arg0.getSource()==jp1_jb1){
            QqClientUser qqClientUser=new QqClientUser();
            User u=new User();
            u.setUserId(jp2_jtf.getText().trim());
            u.setPasswd(new String(jp2_jpf.getPassword()));//注意，密码与账号操作不同

            if(qqClientUser.checkUser(u)){
                try{

                    //把创建好友列表的语句提前，否则不能成功
                    //让它进入好友界面
                    QqFriendList qqList=new QqFriendList(u.getUserId());
                    ManageQqFriendList.addQqFriendList(u.getUserId(),qqList);

                    //发送一个要求返回在线好友的请求包；在登录后显示好友列表前让它发包
                    ObjectOutputStream oos=new ObjectOutputStream
                            (ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().getOutputStream());
                    //做一个Message
                    Message m=new Message();
                    m.setMesType(MessageType.message_get_onLineFriend);//请求包,如果在这到此为止的话服务器不知道要给你返回什么
                    //有可能有两个账号登录，两个账号好友不同，服务器无法判断要哪个
                    //指明我要的是这个qq号的好友情况
                    m.setSender(u.getUserId());
                    oos.writeObject(m);
                    //不要在这里等待接收：发过去之后属于这个qq号的线程已经在运作了，不要在这里等待返回包
                    //所有的包都走线程ClientConServerThread，这也意味着这个线程将来得到的包的种类很多，而非仅有message

                }catch (Exception e){
                    e.printStackTrace();
                }

                //同时关闭登录界面
                this.dispose();
            }else{
                //若不成功，跳出对话框
                JOptionPane.showMessageDialog(this,"用户名密码错误");
            }
        }
    }
}
