package com.kayosystem.honki.chapter08.lesson36;

public class Attendance {

    public String name;
    public String groupId;
    public String date;


    public String time;
    public String subject;


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


    public void setName(String name) {
        this.name = name;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }

}
