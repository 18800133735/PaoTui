package bwei.com.citysortlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bwei.com.citysortlist.adapter.RecycleAdapter;
import bwei.com.citysortlist.bean.CityBean;
import bwei.com.citysortlist.dao.CityDao;
import bwei.com.citysortlist.view.SideBar;
import bwei.com.citysortlist.view.SpacesItemDecoration;

public class MinActivity extends Activity {

    private RecyclerView recycleview_main;
    private SideBar slidbar_main;
    List<CityBean> query=new ArrayList<>();
    private String lastName="";
    private Map<String,Integer> map=new HashMap<>();
    private LinearLayoutManager manager;
    private boolean move;
    private int currentPos=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_min);
        recycleview_main = (RecyclerView) findViewById(R.id.recycleview_main);
        slidbar_main = (SideBar) findViewById(R.id.slidbar_main);
        manager = new LinearLayoutManager(this);
        recycleview_main.setLayoutManager(manager);
        recycleview_main.addItemDecoration(new SpacesItemDecoration(this,SpacesItemDecoration.VERTICAL_LIST));
        initData();
        RecycleAdapter adapter=new RecycleAdapter(query,this);
        recycleview_main.setAdapter(adapter);
        recycleview_main.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //在这里进行第二次滚动（最后的100米！）
                if (move ){
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = currentPos - manager.findFirstVisibleItemPosition();
                    if ( 0 <= n && n < recyclerView.getChildCount()){
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = recyclerView.getChildAt(n).getTop();
                        //最后的移动
                        recyclerView.scrollBy(0, top);
                    }
                }
            }
        });
        slidbar_main.setOnSingleTouchListener(new SideBar.OnSingleTouchListener() {
            @Override
            public void onSingleTouch(String s) {
               if(null!=map.get(s)){
                    currentPos=map.get(s);
                   moveToPosition(currentPos);
               }

            }
        });



    }
    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            recycleview_main.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = recycleview_main.getChildAt(n - firstItem).getTop();
            recycleview_main.scrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            recycleview_main.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }

    }
    public void initData(){
        CityDao dao = new CityDao(this);
        query = dao.query();
        query.add(0,new CityBean("北京市","#"));
        map.put("#",0);
        query.add(1,new CityBean("武汉市","常用城市"));
        for (int i = 0; i <query.size() ; i++) {
            if(i-1>=0){
                lastName=query.get(i-1).getNameSort();
            }
            if(!lastName.equals(query.get(i).getNameSort())){
                map.put(query.get(i).getNameSort(),i);
            }
        }

    }


}
