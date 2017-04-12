package com.yuqian.food.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuqian.food.R;
import com.yuqian.food.SmartImageViewByUrl.SmartImageView;
import com.yuqian.food.model.Comment;
import com.yuqian.food.model.Food;

import java.util.ArrayList;

public class FoodDetailActivity extends AppCompatActivity {
private ArrayList<Comment> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Bundle bundle = this.getIntent().getExtras();
        Food food=(Food) bundle.getSerializable("food");
        ((TextView)findViewById(R.id.food_list_item_foodname)).setText(food.getFoodName());
        ((TextView)findViewById(R.id.food_list_item_foodprice)).setText(food.getFoodPrice());
        ((TextView)findViewById(R.id.food_list_item_food_describe)).setText(food.getFoodDescribe());
        SmartImageView smartImageView=(SmartImageView) findViewById(R.id.food_list_item_food_img);
        smartImageView.setImageUrl(food.getFoodPhotoUrl());
        ListView listView=(ListView) findViewById(R.id.lv_food_item_comments);



    }
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return comments.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            Comment item = comments.get(position);
            if (convertView == null) {
                convertView = View.inflate(FoodDetailActivity.this, R.layout.food_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.smartImage = (SmartImageView) convertView.findViewById(R.id.food_list_item_food_img);
                viewHolder.tv_food_neme = (TextView) convertView.findViewById(R.id.food_list_item_foodname);
                viewHolder.tv_food_price = (TextView) convertView.findViewById(R.id.food_list_item_foodprice);
                convertView.setTag(viewHolder);
            } else {
                viewHolder =  (ViewHolder)convertView.getTag();
            }
            viewHolder.smartImage.setImageUrl(item.getFoodPhotoUrl());
            viewHolder.tv_food_neme.setText(item.getFoodName());
            viewHolder.tv_food_price.setText("价格： ￥ "+item.getFoodPrice());
            return convertView;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
    class ViewHolder{
        public SmartImageView smartImage;
        public TextView tv_food_neme;
        public  TextView tv_food_price;
    }


}
