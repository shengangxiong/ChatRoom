/**
 * FileName: QqFriendList
 * Author:   trhjyhf
 * Date:     2019/4/10 15:17
 * Description:我的好友列表，也包括陌生人黑名单
 * History:
 */
package com.qq.client.view;

import com.qq.client.tools.ManageQqChat;
import com.qq.client.view.*;
import com.qq.common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class QqFriendList extends JFrame implements ActionListener,MouseListener{

    //处理第一张卡片
    JPanel jphy1,jphy2,jphy3;//jphy即jp好友
    JButton jphy_jb1,jphy_jb2,jphy_jb3;
    JScrollPane jsp1;
    String owner;

    //处理第二张卡片（陌生人
    JPanel jpmsr1,jpmsr2,jpmsr3;//jphy即jp好友
    JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3;
    JScrollPane jsp2;
    JLabel []jbls;

    //把整个JFrame设置成cardlayout布局
    CardLayout cl;

    public static void main(String[] args){

        //QqFriendList qqFriendList=new QqFriendList();
    }
    //更新在线的好友情况（头像由灰色变成彩色或反之
    public void upateFriend(Message m){
        //message有个content，这里面就是它好友在线的情况
        String onLineFriend[]=m.getCon().split(" ");

        for(int i=0;i<onLineFriend.length;i++){
            jbls[Integer.parseInt(onLineFriend[i])-1].setEnabled(true);
        }

    }

    public QqFriendList(String ownerId){
        this.owner=ownerId;
        //处理第一张卡片（显示好友列表），最大的那个
        jphy_jb1=new JButton("我的好友");
        jphy_jb2=new JButton("陌生人");
        //对陌生人按钮监听
        jphy_jb2.addActionListener(this);
        jphy_jb3=new JButton("黑名单");

        jphy1=new JPanel(new BorderLayout());
        //假定有50好友（中间那个
        jphy2=new JPanel(new GridLayout(50,1,4,4));//44代表上下行间隔

        //给jphy2初始化50个好友
        jbls=new JLabel[50];

        for(int i=0;i<jbls.length;i++){
            jbls[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
            //默认除了自己都不在线
            jbls[i].setEnabled(false);
            if(jbls[i].getText().equals(ownerId)){
                jbls[i].setEnabled(true);
            }
            jbls[i].addMouseListener(this);
            //每创建一个jlable，就是一个好友，它有图片
            jphy2.add(jbls[i]);


        }

        //存放陌生人黑名单，2行一列
        jphy3=new JPanel(new GridLayout(2,1));
        //把两个按钮加入到jphy3
        jphy3.add(jphy_jb2);
        jphy3.add(jphy_jb3);

        jsp1=new JScrollPane(jphy2);

        //对jphy1初始化，jphy1是整个一大块，要放在jfream才能实现
        jphy1.add(jphy_jb1,"North");
        jphy1.add(jsp1,"Center");
        jphy1.add(jphy3,"South");



        //处理第二张卡片，，，，复制粘贴上面一大堆。。。
        jpmsr_jb1=new JButton("我的好友");
        jpmsr_jb1.addActionListener(this);

        jpmsr_jb2=new JButton("陌生人");
        jpmsr_jb3=new JButton("黑名单");

        jpmsr1=new JPanel(new BorderLayout());
        //假定有20陌生人
        jpmsr2=new JPanel(new GridLayout(20,1,4,4));//44代表上下行间隔

        //给jpmsr2初始化50个好友
        JLabel []jbls2=new JLabel[20];

        for(int i=0;i<jbls2.length;i++){
            jbls2[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
            //每创建一个jlable，就是一个好友，它有图片
            jpmsr2.add(jbls2[i]);

        }

        //存放陌生人黑名单，2行一列
        jpmsr3=new JPanel(new GridLayout(2,1));
        //把两个按钮加入到jphy3
        jpmsr3.add(jpmsr_jb1);
        jpmsr3.add(jpmsr_jb2);

        jsp2=new JScrollPane(jpmsr2);

        //对jphy1初始化，jphy1是整个一大块，要放在jfream才能实现
        jpmsr1.add(jpmsr3,"North");
        jpmsr1.add(jsp2,"Center");
        jpmsr1.add(jpmsr_jb3,"South");

        cl=new CardLayout();
        this.setLayout(cl);
        this.add(jphy1,"1");
        this.add(jpmsr1,"2");
        //在窗口显示自己的编号
        this.setTitle(ownerId);

        //this.add(jphy1,"Center");
        this.setSize(240,400);
        this.setVisible(true);
        this.setLocation(450,250);
    }


    public void actionPerformed(ActionEvent arg0){
        //如果点击了陌生人按钮，就显示第二张卡片
        if(arg0.getSource()==jphy_jb2){
            //cl.show(this,"2");会报错
            cl.show(this.getContentPane(),"2");
        }else if(arg0.getSource()==jpmsr_jb1)
            cl.show(this.getContentPane(),"1");
    }



    public void mouseClicked(MouseEvent arg0){
        //响应用户双击的事件并得到好友的编号；  得到编号才可以使用服务器近一步操作
        if(arg0.getClickCount()==2){//2指双击
            //得到该好友的编号
            String friendNo=((JLabel)arg0.getSource()).getText();
            //写好qqchat后注释掉这句System.out.print("你希望和"+friendNo+"聊天");//后台需要这个东西
            QqChat qqChat=new QqChat(this.owner,friendNo);
            /*Thread t=new Thread(qqChat);
            t.start();*///qqchat已经不是线程了，这个启动也没意义了

            //把聊天界面加入到管理类中
            ManageQqChat.addQqChat(this.owner+" "+friendNo,qqChat);

        }
    }
    public void mouseEntered(MouseEvent arg0){
        JLabel j1=(JLabel)arg0.getSource();
        j1.setForeground(Color.red);
    }
    public void mouseExited(MouseEvent arg0){
        JLabel j1=(JLabel)arg0.getSource();
        j1.setForeground(Color.black);
    }
    public void mouseReleased(MouseEvent arg0){

    }
    public void mousePressed(MouseEvent e) {

    }

}
