package com.yuqian.food.model;

import java.util.ArrayList;

/**
 * Created by yangy on 2017/4/12.
 */

public class CommentListData {
    private String msg;
    private int state;
    private ArrayList<Comment> comments;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
