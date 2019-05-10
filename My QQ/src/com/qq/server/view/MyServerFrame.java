/**
 * Author:   trhjyhf
 * Date:     2019/4/18 11:49
 * Description:这是服务器端的控制界面，可以完成启动服务器，关闭服务器
 * 可以管理和监控用户
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qq.server.view;

import com.qq.server.model.MyQqServer;

import javax.swing.*;
import  java.awt.*;
import java.awt.event.*;

/**
 * 〈一句话功能简述〉<br> 
 *
 */
public class MyServerFrame extends JFrame implements ActionListener{

    JPanel jp1;
    JButton jb1,jb2;

    public static void  main(String[] args){

        MyServerFrame mysf=new MyServerFrame();
    }

    public MyServerFrame(){

        jp1=new JPanel();
        jb1=new JButton("启动服务器");
        jb1.addActionListener(this);

        jb2=new JButton("关闭服务器");
        jp1.add(jb1);
        jp1.add(jb2);

        this.add(jp1);
        this.setSize(500,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jb1){
            //实现启动，new一个即可
            new MyQqServer();
        }
    }
}
