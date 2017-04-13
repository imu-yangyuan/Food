package com.yuqian.food.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.yuqian.food.Listener.Listener;
import com.yuqian.food.R;
import com.yuqian.food.model.Food;
import com.yuqian.food.model.FoodListData;
import com.yuqian.food.service.FoodService;

import java.util.ArrayList;

/**
 * Created by yangy on 2017/4/13.
 */

public class SelectRestaurantActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_restaurant);
        ImageView iv_restaurant_1=(ImageView) findViewById(R.id.restaurant_1);
        ImageView iv_restaurant_2=(ImageView) findViewById(R.id.restaurant_2);
        ImageView iv_restaurant_3=(ImageView) findViewById(R.id.restaurant_3);
        ImageView iv_restaurant_4=(ImageView) findViewById(R.id.restaurant_4);
        ImageView iv_restaurant_5=(ImageView) findViewById(R.id.restaurant_5);
        iv_restaurant_1.setOnClickListener(this);
        iv_restaurant_2.setOnClickListener(this);
        iv_restaurant_3.setOnClickListener(this);
        iv_restaurant_4.setOnClickListener(this);
        iv_restaurant_5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.restaurant_1:{
                showListFood(1);
                break;
            }
            case R.id.restaurant_2:{
                showListFood(2);
                break;
            }
            case R.id.restaurant_3:{
                showListFood(3);
                break;
            }
            case R.id.restaurant_4:{
                showListFood(4);
                break;
            }
            case R.id.restaurant_5:{
                showListFood(5);
                break;
            }
        }
    }
    private void showListFood(Integer restaurantNum){
        FoodService foodService=new FoodService();
        RequestParams params=new RequestParams();
        params.put("restaurantNum",restaurantNum);
        foodService.get(SelectRestaurantActivity.this, "getFoodList.php", params, new Listener() {
            @Override
            public void onSuccess(Object object) {

                Intent intent=new Intent(SelectRestaurantActivity.this,FoodListActivity.class);
                FoodListData foodListData=(FoodListData)object;
                ArrayList<Food> foods=foodListData.getFoods();
                intent.putExtra("foods",foods);
                startActivity(intent);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
