package com.chenglong.muscle.tool;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.chenglong.baidu.opensrc.MyPoiOverlay;
import com.chenglong.muscle.R;
import com.chenglong.muscle.R.drawable;
import com.chenglong.muscle.R.id;
import com.chenglong.muscle.R.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MapActivity extends Activity
		implements BDLocationListener, OnMapStatusChangeListener, OnGetPoiSearchResultListener, OnMarkerClickListener {

	private MapView mapView;
	private BaiduMap baiduMap;
	private Boolean locationFlag;
	private LocationClient locClient;
	private final String keyWordInSearch = "健身房";
	private final int radiusInSearch = 5 * 1000;
	private double latitude = 0;
	private double longitude = 0;
	private PoiSearch poiSearch;
	private MyPoiOverlay myPoiOverlay;
	private String phoneNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.map);
		setTitle("美队健身：附近健身房");
		
		Toast.makeText(this, "本功能依赖于网络访问权限，请确认开启", Toast.LENGTH_SHORT).show();
		
		/* 基础地图及定位图层功能 */
		mapSetting();
		/* 我的位置图标动作 */
		myLocSetting();
		/* POI图层实现 */
		poiSetting();
	}
	

	private void poiSetting() {
		// TODO Auto-generated method stub
		poiSearch = PoiSearch.newInstance();
		myPoiOverlay = new MyPoiOverlay(baiduMap);
		poiSearch.setOnGetPoiSearchResultListener(this);
		baiduMap.setOnMapStatusChangeListener(this);
		baiduMap.setOnMarkerClickListener(this);
	}

	private void poiSearch(double centerLat, double centerLon) {
		// TODO Auto-generated method stub
		poiSearch.searchNearby(new PoiNearbySearchOption().radius(radiusInSearch).keyword(keyWordInSearch)
				.location(new LatLng(centerLat, centerLon)));
	}

	private void myLocSetting() {
		// TODO Auto-generated method stub
		ImageView mylocImage = (ImageView)findViewById(R.id.myLoc_frag3);

		mylocImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				locationFlag = true;
				locClient.requestLocation();
			}
		});

	}

	private void mapSetting() {
		// TODO Auto-generated method stub
		mapView = (MapView)findViewById(R.id.map_frag3);
		baiduMap = mapView.getMap();
		// baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

		/* 定位 */
		locationFlag = true;

		baiduMap.setMyLocationEnabled(true); /* 先于配置定位信息 */

		BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.location_arrow);
		baiduMap.setMyLocationConfigeration(
				new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, mCurrentMarker));

		/* 启动监听器用来实时定位 */
		locClient = new LocationClient(getApplication());
		locClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption(); /* 设置定位信息选项 */
		option.setOpenGps(true); // 打开GPS
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(20000); // 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
		locClient.setLocOption(option);

		locClient.start(); /* 影响定时器功能 */
		// locClient.requestLocation();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		locClient.stop();
		baiduMap.setMyLocationEnabled(false);
		super.onDestroy();
		mapView.onDestroy();
		mapView = null;
		System.gc();
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		if (location == null || mapView == null)
			return;

		latitude = location.getLatitude();
		longitude = location.getLongitude();

		if (true != locationFlag)
			return;
		locationFlag = false;

		MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).longitude(longitude)
				.direction(100).latitude(latitude).build();
		baiduMap.setMyLocationData(locData);

		LatLng ll = new LatLng(latitude, longitude);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		baiduMap.animateMapStatus(u); /* 以动画方式更新地图状态 耗时300ms */
	}

	@Override
	public void onMapStatusChange(MapStatus arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onMapStatusChangeFinish(MapStatus arg0) {
		// TODO Auto-generated method stub
		LatLng latLng = baiduMap.getMapStatus().target;
		poiSearch(latLng.latitude, latLng.longitude);
	}

	@Override
	public void onMapStatusChangeStart(MapStatus arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		// TODO Auto-generated method stub
		if ((null == result) || (result.error != SearchResult.ERRORNO.NO_ERROR))
			return;		
		
//		TextView phoneLink = new TextView(getActivity());
//		phoneLink.setText("电话：" + result.getTelephone());
//		phoneLink.setAutoLinkMask(Linkify.PHONE_NUMBERS);
		
		String telephone = result.getTelephone();
		if ((telephone.isEmpty()) || (telephone.equals("0"))) {
			phoneNo = "tel:" + result.getTelephone();
		}
		else
		{
			telephone = "暂无";
			phoneNo = "";
		}
			
		String[] info = {"地址: " + result.getAddress(), 
				         "电话：" + telephone,
				         "营业时间：" + result.getShopHours(),
				         "价格：" + result.getPrice(), 
				         "综合评价：" + result.getOverallRating(), 
				         "服务评价：" + result.getServiceRating()};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog dialog = builder.setTitle(result.getName()).setIcon(R.drawable.icon_gym).setPositiveButton("关闭", null).setItems(info, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if ((1 == which) && (!phoneNo.isEmpty()))
				{
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNo));
					startActivity(intent);
				}
				
			}
		}).create();
		
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
    	lp.alpha = 0.6f;
    	dialog.getWindow().setAttributes(lp);	
    	
    	dialog.show();
	}
	

	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), "onGetPoiResult : "+result.error, Toast.LENGTH_SHORT).show();
		if ((null == result) ||(result.error != SearchResult.ERRORNO.NO_ERROR))
		    return;
		
//		for (PoiInfo poi: result.getAllPoi())
//		{
//			/* 无需做类型判断  */
//			//Toast.makeText(getActivity(), "city:"+poi.city+" name:"+poi.name+" address:"+poi.address, Toast.LENGTH_SHORT).show();	    
//		}
		myPoiOverlay.setData(result);
		myPoiOverlay.addToMap();		
	}

	@Override
	public boolean onMarkerClick(final Marker marker) {
		// TODO Auto-generated method stub
        PoiInfo poi = myPoiOverlay.getPoi(marker);
        
        LatLng ll = marker.getPosition();
		Button popupButton = new Button(this);
		InfoWindow mInfoWindow = new InfoWindow(popupButton, ll, -47);
		baiduMap.showInfoWindow(mInfoWindow);
		
		/* 修改popup形状和内容  */
        popupButton.setText(poi.name.toString());	
        popupButton.setTextColor(android.graphics.Color.BLACK);
        popupButton.setBackgroundResource(R.drawable.popup);
             
        popupButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PoiInfo poi = myPoiOverlay.getPoi(marker);
				// TODO Auto-generated method stub
				poiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
			}
        });
		return false;
	}
}