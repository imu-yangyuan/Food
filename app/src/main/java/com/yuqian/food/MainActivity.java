package com.yuqian.food;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuqian.food.Listener.Listener;
import com.yuqian.food.model.UserData;
import com.yuqian.food.model.UserModel;
import com.yuqian.food.service.UserService;
import com.yuqian.food.util.ToastManager;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginButton=(Button) findViewById(R.id.login_button);
        passwordEditText = (EditText) findViewById(R.id.login_password);
        usernameEditText=(EditText)findViewById(R.id.login_zhanghao) ;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login(){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (username.length() <= 0 || password.length() <= 0) {
            Toast.makeText(this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestParams params = new RequestParams();
        String url="public/login.php";
        params.put("userName", username);
        params.put("password", params);
        UserService userService=new UserService();
        userService.get(this, url, params, new Listener() {
            @Override
            public void onSuccess(Object object) {
                UserData userData=(UserData)object;
                ToastManager.toast(MainActivity.this,((UserData)object).getState()+"");
                UserModel userModel=userData.getUserModel();
                saveInfor(userModel);


            }

            @Override
            public void onFailure(String msg) {
                ToastManager.toast(MainActivity.this,msg);
            }
        });

    }
    private void saveInfor(UserModel userModel) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("accessKey", userModel.getAccessKey());
        editor.putInt("userId",userModel.getId());
        editor.putString("username",userModel.getUserName());
        if (!editor.commit()) {
            System.err.println("！！！写入失败！！！");
        }
    }
}
