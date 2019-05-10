
package com.qq.client.tools;

import com.qq.client.view.QqChat;

import java.util.HashMap;

/**
 * 这是一个管理用户聊天界面的类
 */
public class ManageQqChat {//在点击那一瞬间将其加入

    private static HashMap hm=new HashMap<String, QqChat>();
    //这里的管理是引用，而不是创建一个新的
    //加入
    public static void addQqChat(String loginIdAnFriendId,QqChat qqChat)/*可以放在一起因为不可能重复*/{
        hm.put(loginIdAnFriendId,qqChat);
    }

    //取出
    public static QqChat getQqChat(String loginIdAnFriendId){
        return (QqChat)hm.get(loginIdAnFriendId);//是从hashmap取得
    }


}
