package com.yuqian.food.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.yuqian.food.Listener.Listener;
import com.yuqian.food.R;
import com.yuqian.food.SmartImageViewByUrl.SmartImageView;
import com.yuqian.food.model.Comment;
import com.yuqian.food.model.CommentListData;
import com.yuqian.food.model.Food;
import com.yuqian.food.service.Service;
import com.yuqian.food.util.ToastManager;
import com.yuqian.food.viewUtil.CircleSmartImageView;

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
        ((TextView)findViewById(R.id.food_list_item_foodprice)).setText("价格：￥"+food.getFoodPrice());
        ((TextView)findViewById(R.id.food_list_item_food_describe)).setText("描述："+food.getFoodDescribe());
        SmartImageView smartImageView=(SmartImageView) findViewById(R.id.food_list_item_food_img);
        smartImageView.setImageUrl(food.getFoodPhotoUrl());
        final ListView listView=(ListView) findViewById(R.id.lv_food_item_comments);
        Service service=new Service();
        RequestParams requestParams=new RequestParams();
        requestParams.put("foodId",food.getId()+"");
        service.get(this, "getCommentByFoodId.php", requestParams, new Listener() {
            @Override
            public void onSuccess(Object object) {
                String string=new String((byte[]) object);
                CommentListData commentListData=new Gson().fromJson(string,CommentListData.class);
                if(commentListData.getState()==0){
                    ToastManager.toast(FoodDetailActivity.this,commentListData.getMsg());
                }else{
                    comments=commentListData.getComments();
                    listView.setAdapter(new MyAdapter());
                }
            }
            @Override
            public void onFailure(String msg) {
                ToastManager.toast(FoodDetailActivity.this,msg);
            }
        });


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
                convertView = View.inflate(FoodDetailActivity.this, R.layout.food_comment_item, null);
                viewHolder = new ViewHolder();
                viewHolder.user_smartImage = (CircleSmartImageView) convertView.findViewById(R.id.food_comment_userimg);
                viewHolder.tv_user_comment = (TextView) convertView.findViewById(R.id.food_comment_info);
                convertView.setTag(viewHolder);
            } else {
                viewHolder =  (ViewHolder)convertView.getTag();
            }
            viewHolder.user_smartImage.setImageUrl(item.getUserphotourl());
            viewHolder.tv_user_comment.setText(item.getFoodcomment());
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
        public CircleSmartImageView user_smartImage;
/*        public TextView tv_food_neme;*/
        public  TextView tv_user_comment;
    }


}
