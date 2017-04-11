package com.yuqian.food.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuqian.food.Listener.Listener;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
/**
 * Created by yangy on 2016/9/11.
 */
public class HttpFileRequest {

    public static void FileUpload(Context context, String url, File file, String FileName,
                                  final Listener listener)throws FileNotFoundException {
        RequestParams params=new RequestParams();
        params.put(FileName,file);
        if (file.exists() && file.length() > 0) {
            // 上传文件
            HttpRequest.post(context,url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    listener.onSuccess(responseBody);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    listener.onFailure(String.valueOf(responseBody));
                }
            });
        } else {
            listener.onFailure((FileName+"不存在"));
        }
    }

    public static void FileDownload(String url,final Listener listener){

        AsyncHttpClient client = new AsyncHttpClient();
        // 指定文件类型
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg","video/mp4" };
        // 获取二进制数据如图片和其他文件
        client.get(url, new BinaryHttpResponseHandler(allowedContentTypes) {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                listener.onSuccess(binaryData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                listener.onFailure(new String(binaryData));
            }
        });

    }

}

