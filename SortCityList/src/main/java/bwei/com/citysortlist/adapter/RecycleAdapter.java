package bwei.com.citysortlist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bwei.com.citysortlist.R;
import bwei.com.citysortlist.bean.CityBean;

/**
 * 作者：JinCaiXia
 * 邮箱：jincaixia123@aliyun.com
 * 时间：2017/2/9
 * 文档说明：
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private List<CityBean> list;
    private Context context;
    private String lastName="";


    public RecycleAdapter(List<CityBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tv_recycleview_lv,null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CityBean cityBean = list.get(position);

        if(position==0){
            holder.rl_namesort.setVisibility(View.GONE);
            holder.tv_cityname.setVisibility(View.GONE);
            holder.ll_cityname.setVisibility(View.VISIBLE);
            holder.tv_cityname2.setText(cityBean.getCityName());
        }else{
            holder.ll_cityname.setVisibility(View.GONE);
            holder.tv_cityname.setBackgroundColor(Color.parseColor("#FAFAFA"));
            holder.tv_cityname.setText(cityBean.getCityName());
        }
        if(position==1){
            if("常用城市".equals(list.get(position).getNameSort())){
                holder.tv_cityname.setBackgroundResource(R.drawable.round_bg);
            }
        }

        if(position-1>=0){
            lastName = list.get(position-1).getNameSort();
        }
            if(lastName.equals(cityBean.getNameSort())){
                holder.rl_namesort.setVisibility(View.GONE);
             //   holder.tv_namesort.setVisibility(View.GONE);
            }else{
                if(position==0) {
                    return;
                }
                holder.rl_namesort.setVisibility(View.VISIBLE);
              //  holder.tv_namesort.setVisibility(View.VISIBLE);
                holder.tv_namesort.setText(cityBean.getNameSort());

            }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
   public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_cityname;
       TextView  tv_namesort;
       TextView tv_cityname2;
       View iv_bg;
       RelativeLayout rl_namesort;
       LinearLayout ll_cityname;
       public MyViewHolder(View itemView) {
            super(itemView);
           tv_cityname=  (TextView) itemView.findViewById(R.id.tv_cityname);
           tv_cityname2=  (TextView) itemView.findViewById(R.id.tv_cityname2);
            tv_namesort= (TextView) itemView.findViewById(R.id.tv_namesort);

           iv_bg=itemView.findViewById(R.id.iv_bg);
          rl_namesort= (RelativeLayout) itemView.findViewById(R.id.rl_namesort);
           ll_cityname = (LinearLayout) itemView.findViewById(R.id.ll_cityname);
       }
    }
}
