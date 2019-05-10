/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Message
 * Author:   trhjyhf
 * Date:     2019/4/18 17:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qq.common;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author trhjyhf
 * @create 2019/4/18
 * @since 1.0.0
 */
public class Message implements java.io.Serializable{


    private String mesType;

    private String sender;
    private String getter;
    private String con;
    private String sendTime;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

}
