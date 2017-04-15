package com.yuqian.food.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.yuqian.food.R;

/**
 * Created by yangy on 2017/4/14.
 */

public class WeclomeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weclome);
        handler.sendEmptyMessageDelayed(0,500);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            goHome();
            super.handleMessage(msg);
        }
    };
    public void goHome(){
        Intent intent = new Intent(WeclomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
