package com.yuqian.food.model;

/**
 * Created by yangy on 2017/4/12.
 */

public class Comment {

    private int id;

    private String foodcomment;

    private int userid;

    private int foodid;

    private String username;

    private String userphotourl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodcomment() {
        return foodcomment;
    }

    public void setFoodcomment(String foodcomment) {
        this.foodcomment = foodcomment;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphotourl() {
        return userphotourl;
    }

    public void setUserphotourl(String userphotourl) {
        this.userphotourl = userphotourl;
    }
}

