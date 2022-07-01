package com.example.letsgrow;

public class Post {

    String Name,Data,Userid;

    public Post(String name, String data, String userid) {
        Name = name;
        Data = data;
        Userid = userid;
    }

    public Post(){

    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
