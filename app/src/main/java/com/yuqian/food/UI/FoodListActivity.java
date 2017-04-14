package com.yuqian.food.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuqian.food.R;
import com.yuqian.food.SmartImageViewByUrl.SmartImageView;
import com.yuqian.food.model.Food;

import java.util.ArrayList;



public class FoodListActivity extends Activity implements AdapterView.OnItemClickListener{
    private ArrayList<Food> foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        Bundle bundle = this.getIntent().getExtras();
        foods=(ArrayList<Food>) bundle.getSerializable("foods");
        ListView listView= (ListView) findViewById(R.id.food_list);
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ViewHolder viewHolder=(ViewHolder)view.getTag();
        Intent intent=new Intent(FoodListActivity.this,FoodDetailActivity.class);
        intent.putExtra("food",foods.get(position));
        startActivity(intent);
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return foods.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            Food item = foods.get(position);
            if (convertView == null) {
                convertView = View.inflate(FoodListActivity.this, R.layout.food_list_item, null);
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
