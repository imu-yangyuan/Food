package com.yuqian.food.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.yuqian.food.Listener.Listener;
import com.yuqian.food.R;
import com.yuqian.food.model.CommonData;
import com.yuqian.food.service.Service;
import com.yuqian.food.util.ToastManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AddFoodActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private ArrayList<String> data_list;
    private ImageView image;
    private File foodImgFile = null;
    private int selectIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food1);
       Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //数据
        data_list = new ArrayList<String>();
        data_list.add("第二食堂");
        data_list.add("第四食堂");
        data_list.add("地下食堂");
        data_list.add("清真食堂");
        data_list.add("清真风味");

        //适配器
        ArrayAdapter arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(this);
        image= (ImageView) findViewById(R.id.add_food_image);
        showImage();

    }
    private void init(){
        TextView add_food_cancel=(TextView) findViewById(R.id.add_food_finish);
        add_food_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFoodActivity.this.finish();
            }
        });
        final EditText food_name=(EditText) findViewById(R.id.food_name);
        final EditText et_food_price=(EditText) findViewById(R.id.et_food_price);
        final EditText et_food_describe=(EditText) findViewById(R.id.et_food_describe);
        Button btn_add_food=(Button) findViewById(R.id.btn_add_food);
        btn_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food_name_str=food_name.getText().toString();
                String food_describe_str=et_food_describe.getText().toString();
                String food_price_str=et_food_price.getText().toString();

            }
        });

    }
    /**
     * 回调图片
     */
    private final String IMAGE_TYPE="image/*";
    private final int IMAGE_CODE=1;
    private String Path;
    private void showImage() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(IMAGE_TYPE);
                intent.putExtra("crop", "true");    // crop=true 有这句才能出来最后的裁剪页面.
                intent.putExtra("aspectX", 1);      // 这两项为裁剪框的比例.
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                //输出地址
                intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg")));
                intent.putExtra("outputFormat", "JPEG");//返回格式
                startActivityForResult(Intent.createChooser(intent, "选择图片"), IMAGE_CODE);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != AddFoodActivity.RESULT_OK) {
            Log.i("photopath","fail");
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE) {
            Log.i("photopath","success");
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg", options);
                Path=Environment.getExternalStorageDirectory().getPath()+"/loveu.jpg";

                if (bitmap == null){
                    Log.i("bitmap","null");
                    return;
                }

                image.setImageBitmap(bitmap);
                foodImgFile = new File(Path);

            }catch (Exception e){
                e.getLocalizedMessage();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectIndex=position;
        ToastManager.toast(this,selectIndex+"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void addFood(String foodName,
                         String foodDescribe,
                         String foodPrice){
        String url="addbook.action";
        Service service=new Service();
        RequestParams requestParams=new RequestParams();
        requestParams.put("foodName",foodName);
        requestParams.put("foodDescribe",foodDescribe);
        requestParams.put("foodPrice",foodPrice);
        requestParams.put("restaurantNum",data_list.get(selectIndex));
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
       /* Integer userId = preferences.getInt("userId",0);
        String accessKey=preferences.getString("accessKey","");
        Integer userType=preferences.getInt("type",0);
        String userName=preferences.getString("userName","");
        if(userType!=2){
            this.finish();
        }
        requestParams.put("userId",userId);
        requestParams.put("accessKey",accessKey);*/
        if(foodImgFile==null){
            ToastManager.toast(AddFoodActivity.this,"请选择菜品图片");
            return;
        }else{
            try {
                requestParams.put("foodPictureFile",foodImgFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ToastManager.toast(AddFoodActivity.this,"请选择菜品图片");
                return;
            }
            service.get(AddFoodActivity.this, url, requestParams, new Listener() {
                @Override
                public void onSuccess(Object object) {
                    String string=new String((byte[])object);
                    CommonData commonData=new Gson().fromJson(string,CommonData.class);
                    if(commonData.getState()==0){
                        ToastManager.toast(AddFoodActivity.this,commonData.getMsg());
                    }else{
                        ToastManager.toast(AddFoodActivity.this,commonData.getMsg());
                        try {
                            Thread.sleep(1000);
                            AddFoodActivity.this.finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(String msg) {

                }
            });
        }

    }
}
