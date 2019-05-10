
package com.qq.server.model;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author trhjyhf
 * @create 2019/5/5
 * @since 1.0.0
 */
import java.util.*;
public class ManageClientThread {
    //首先应该明确：只能有一份hashmap
    public static HashMap hm = new HashMap<String, SerConClientThread>();

    //向hm中添加一个客户端通讯线程
    public static void addClientThread(String uid, SerConClientThread ct) {
        hm.put(uid, ct);
    }

    public static SerConClientThread getClientThread(String uid) {

        return (SerConClientThread) hm.get(uid);
    }

    //返回当前在线的人的情况
    public static String getAllOnlineUserid() {
        //使用迭代器遍历hashmap的key值
        Iterator it = hm.keySet().iterator();
        String res = "";
        while (it.hasNext()) {
            res += it.next().toString() + " ";//打上空格，将来用空格拆分
        }
        return res;
    }
}