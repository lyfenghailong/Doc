package com.baidu.demo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;


import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MKEvent;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKMapViewListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;

public class MapViewDemo extends MapActivity {
	

	static MapView mMapView = null;
	int iZoom = 0;
	
	BMapManager mBMapMan;
	String Key = "请输入您的key";
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.mapviewdemo);
 
        if (mBMapMan == null) {
			mBMapMan = new BMapManager(getApplication());
			mBMapMan.init(Key, new MyGeneralListener());
		} 
        mBMapMan.start();
   
        mMapView = (MapView)findViewById(R.id.bmapView);
    
        
	}
	
	private void initMapView(){
        // 如果使用地图SDK，请初始化地图Activity
        boolean initMap = super.initMapActivity(mBMapMan);
        //完成initMapActivity后再设置缩放等控件
        mMapView.setBuiltInZoomControls(true);
        mMapView.setDrawOverlayWhenZooming(true);
        mMapView.displayZoomControls(false);
	}

	@Override
	protected void onPause() {
		
		if(mBMapMan != null) {
			//离开时只把管理类stop，不要destroy，只在整个app结束时destroy
			mBMapMan.stop();

		}
		super.onPause();
	}
	
	@Override
	protected void onResume() {
	
		mBMapMan.start();
		//再次进图时需要执行initMapActivity
		initMapView();
	
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Log.d("MyGeneralListener", "onGetNetworkState error is "+ iError);
			Toast.makeText(MapViewDemo.this, "您的网络出错啦！error is "+ iError,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is "+ iError);
			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(MapViewDemo.this, 
						"请在BMapApiDemoApp.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
