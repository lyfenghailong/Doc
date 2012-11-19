package com.baidu.demo;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.baidu.demo.adapter.ImageAdapter;

public class MainActivity extends ActivityGroup implements OnTouchListener {
	private GridView gvTopBar;  
    private ImageAdapter topImgAdapter;  
    private LinearLayout container;// 装载sub Activity的容器   
	
    private static final int ID_CUSTOM = 0;
    private static final int ID_BAIDU = 1;
    private static final int ID_SIMPLE = 2;
    
    /** 底部按钮图片 **/  
    int[] topbar_image_array = { 
    		R.drawable.main_tab_list, 
    		R.drawable.main_tab_search, 
    		R.drawable.main_tab_web};
    
    int[] topbar_image_array_highlight = {
    		R.drawable.icon_list_selected, 
    		R.drawable.icon_search_selected, 
    		R.drawable.icon_web_selected};
    
    int[] topbar_image_array_selected = { 
    		R.drawable.icon_list_first,  
    		R.drawable.icon_search_first, 
    		R.drawable.icon_web_first};
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_group);
        
        gvTopBar = (GridView) this.findViewById(R.id.gvTopBar); 
        gvTopBar.setNumColumns(topbar_image_array.length);          // 设置每行列数   
        gvTopBar.setSelector(new ColorDrawable(Color.TRANSPARENT)); // 选中的时候为透明色   
        gvTopBar.setGravity(Gravity.CENTER);                        // 位置居中   
        gvTopBar.setVerticalSpacing(0);                             // 垂直间隔
        topImgAdapter = new ImageAdapter(this, topbar_image_array, topbar_image_array_selected, topbar_image_array_highlight);
        gvTopBar.setAdapter(topImgAdapter);                         // 设置菜单Adapter
      
        gvTopBar.setOnTouchListener(this);
        container = (LinearLayout) findViewById(R.id.Container);  
        SwitchActivity(0);//默认打开第0页   
    }

    private int x = 0;
    private int y = 0;
    private int startId = 0;
    private int endId = 0;
    
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
	        //当手指按下的时候，将手指按下的条目设置为高亮
	        case MotionEvent.ACTION_DOWN:
	            x = (int) event.getX();
	            y = (int) event.getY();
	            startId = gvTopBar.pointToPosition(x, y);
	          
	            break;
	        //当手指离开的时候，若当前条目为按下的条目，则启动条目，否则取消高亮
	        case MotionEvent.ACTION_UP:
	            x = (int) event.getX();
	            y = (int) event.getY();
	            endId = gvTopBar.pointToPosition(x, y);
	            if (startId == endId) {
	                SwitchActivity(endId);
	            } else {
	  
	            }
	            break;
	    }
	    return true;
	}
	
	
	/** 
     * 根据ID打开指定的Activity 
     * @param id GridView选中项的序号 
     */  
	void SwitchActivity(int id) {

		container.removeAllViews();// 必须先清除容器中所有的View
		switch (id) {
		case ID_CUSTOM:
			Intent intent = new Intent(this, ExpandableList1.class);
			View mCustomView = getLocalActivityManager().startActivity(
					"ExpandableList1", intent).getDecorView();
			container.addView(mCustomView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			break;
		case ID_BAIDU:
			intent = new Intent(this, MapViewDemo.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			View mBaiduView = getLocalActivityManager().startActivity(
					"MapViewDemo", intent).getDecorView();
			container.addView(mBaiduView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			break;
		case ID_SIMPLE:
			intent = new Intent(this, ExpandableList3.class);
			View mCustomView2 = getLocalActivityManager().startActivity(
					"ExpandableList3", intent).getDecorView();
			container.addView(mCustomView2, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		
	}
}