package com.kayosystem.honki.chapter08.lesson36;

public class UserId {

    public String time ;
    public String subject = "";

    public UserId(){}

    public UserId(String time,String subject){
        this.time = time;
        this.subject = subject;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }

}
