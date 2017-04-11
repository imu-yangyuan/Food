package com.yuqian.food.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuqian.food.Listener.Listener;
import com.yuqian.food.model.UserData;
import com.yuqian.food.util.HttpRequest;

import org.apache.http.Header;

/**
 * Created by yangy on 2016/9/11.
 */
public class UserService {

    public void get(final Context context, String url, RequestParams params, final Listener listener){
        HttpRequest.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.i("user_json",new String(bytes));

               String string=new String(bytes);
                UserData userData=new Gson().fromJson(string,UserData.class);
                if(userData.getState()==1){
                    listener.onSuccess(userData);
                }else{
                    listener.onFailure(userData.getMsg());
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                listener.onFailure("网络错误！");
            }
        });
    }

    public void post(Context context, String url, RequestParams params, final Listener listener){
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String s=new String(bytes);
                Log.i("user_json",s);

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.onFailure("网络错误！");
            }
        });
    }

}
