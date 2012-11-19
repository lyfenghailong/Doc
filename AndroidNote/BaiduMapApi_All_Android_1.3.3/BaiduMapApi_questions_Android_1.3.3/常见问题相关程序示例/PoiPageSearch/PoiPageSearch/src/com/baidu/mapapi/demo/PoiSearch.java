package com.baidu.mapapi.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.PoiOverlay;
import com.baidu.mapapi.demo.R;


public class PoiSearch extends MapActivity {
	Button mBtnSearch = null;	// 搜索按钮
	Button mPageSearch = null;  //翻页搜索
	
	MapView mMapView = null;	// 地图View
	MKSearch mSearch = null;	// 搜索模块，也可去掉地图模块独立使用
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.poisearch);
        
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new BMapApiDemoApp.MyGeneralListener());
		}
		app.mBMapMan.start();
        // 如果使用地图SDK，请初始化地图Activity
        super.initMapActivity(app.mBMapMan);
        
        mMapView = (MapView)findViewById(R.id.bmapView);
        mMapView.setBuiltInZoomControls(true);
        //设置在缩放动画过程中也显示overlay,默认为不绘制
        mMapView.setDrawOverlayWhenZooming(true);
        
        // 初始化搜索模块，注册事件监听
        mSearch = new MKSearch();
        mSearch.init(app.mBMapMan, new MKSearchListener(){

			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				if (error != 0 || res == null) {			
					return;
				}
			    if (res.getCurrentNumPois() > 0) {
				    // 将poi结果显示到地图上,PoiOverlayTest继承PoiOverlay类
			    	PoiOverlayTest poiOverlay = new PoiOverlayTest(PoiSearch.this, mMapView);
					poiOverlay.setData(res.getAllPoi());
				    mMapView.getOverlays().clear();
				    mMapView.getOverlays().add(poiOverlay);
				    mMapView.invalidate();			    	
			    } 
			}
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}
			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}
			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}
			public void onGetAddrResult(MKAddrInfo res, int error) {
			}
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
        });
       
        // 设定搜索按钮的响应
        mBtnSearch = (Button)findViewById(R.id.search);
        
        OnClickListener clickListener = new OnClickListener(){
			public void onClick(View v) {
				SearchButtonProcess(v);
			}
        };
        mBtnSearch.setOnClickListener(clickListener); 
        
        // 设定pageSearch响应
        mPageSearch = (Button)findViewById(R.id.pagesearch);

		OnClickListener clickListener1 = new OnClickListener(){
			public void onClick(View v) {
				PageSearchButtonProcess(v);
			}
		};
		mPageSearch.setOnClickListener(clickListener1); 
	}
	void SearchButtonProcess(View v) {
		if (mBtnSearch.equals(v)) {

			EditText editCity = (EditText)findViewById(R.id.city);
			EditText editSearchKey = (EditText)findViewById(R.id.searchkey);
			mSearch.poiSearchInCity(editCity.getText().toString(), 
					editSearchKey.getText().toString());
		}
	}

	//翻页查询，结果在onGetPoiResult获得，需在poiSearchInCity执行后调用
	void PageSearchButtonProcess(View v) {	
		EditText editSearchKey = (EditText)findViewById(R.id.pagekey);
		mSearch.goToPoiPage( Integer.parseInt(editSearchKey.getText().toString()));

	}
	@Override
	protected void onPause() {
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		app.mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		app.mBMapMan.start();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
class PoiOverlayTest extends PoiOverlay
{
	public PoiOverlayTest(Activity arg0, MapView arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onTap(int i)
	{
		//自定义处理事件
		//
		//
		return super.onTap(i);//响应父类的tap事件
	}	
}