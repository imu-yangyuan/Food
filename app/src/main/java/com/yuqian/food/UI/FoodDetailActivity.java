package com.yuqian.food.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.yuqian.food.Listener.Listener;
import com.yuqian.food.R;
import com.yuqian.food.SmartImageViewByUrl.SmartImageView;
import com.yuqian.food.model.Comment;
import com.yuqian.food.model.CommentListData;
import com.yuqian.food.model.CommonData;
import com.yuqian.food.model.Food;
import com.yuqian.food.service.Service;
import com.yuqian.food.util.ToastManager;
import com.yuqian.food.viewUtil.CircleSmartImageView;

import java.util.ArrayList;

public class FoodDetailActivity extends Activity {
private ArrayList<Comment> comments;
    private Food food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Bundle bundle = this.getIntent().getExtras();
        food=(Food) bundle.getSerializable("food");
        ((TextView)findViewById(R.id.food_list_item_foodname)).setText(food.getFoodName());
        ((TextView)findViewById(R.id.food_list_item_foodprice)).setText("价格：￥"+food.getFoodPrice());
        ((TextView)findViewById(R.id.food_list_item_food_describe)).setText("描述："+food.getFoodDescribe());
        ((TextView)findViewById(R.id.btn_comment_food)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final EditText editText1=new EditText(FoodDetailActivity.this);
                editText1.setTextColor(Color.parseColor("#000000"));
                editText1.setWidth(1000);
                new AlertDialog.Builder(FoodDetailActivity.this,android.R.style.Animation_Dialog)
                        .setTitle("输入评论")
                        .setView(editText1)
                        .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String commenttxt=editText1.getText().toString();
                                if(commenttxt==null&&commenttxt==""){
                                    ToastManager.toast(FoodDetailActivity.this,"请输入内容！");
                                }
                                else{
                                    addComment(commenttxt);
                                    }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
            }
        });
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
    private void addComment(final String comment){
        Service service=new Service();
        RequestParams requestParams=new RequestParams();
        requestParams.put("foodId",food.getId());
        requestParams.put("userId",1);//设置请求的userId参数
        requestParams.put("comment",comment);
        service.get(FoodDetailActivity.this, "addComment.php", requestParams, new Listener() {
            @Override
            public void onSuccess(Object object) {
                String string=new String((byte[])object);
                CommonData commonData=new Gson().fromJson(string,CommonData.class);
                if(commonData.getState()==0){
                    ToastManager.toast(FoodDetailActivity.this,"评论失败！");
                }else{
                    ToastManager.toast(FoodDetailActivity.this,"评论成功！");
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastManager.toast(FoodDetailActivity.this,"评论失败！");
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
