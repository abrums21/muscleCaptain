package com.chenglong.baidu.opensrc;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;

import android.os.Bundle;

public class MyPoiOverlay extends OverlayManager {

	private static final int MY_MAX_POI_SIZE = 10;

	private PoiResult mPoiResult = null;

	public MyPoiOverlay(BaiduMap baiduMap) {
		super(baiduMap);
	}

	public void setData(PoiResult mPoiResult) {
		this.mPoiResult = mPoiResult;
	}
	
	public PoiInfo getPoi(Marker marker) {
		int index = marker.getExtraInfo().getInt("index");
		return mPoiResult.getAllPoi().get(index);
	}
	
	
	@Override
	public boolean onMarkerClick(final Marker marker) {
		// TODO Auto-generated method stub
//		Toast.makeText(BMapManager.getContext(), "onMarkerClick", Toast.LENGTH_SHORT).show();
//		
//		if (!mOverlayList.contains(marker)) {
//			return false;
//		}
//		if (marker.getExtraInfo() != null) {
//			int index = marker.getExtraInfo().getInt("index");
//			Log.e("21", "name = "+ mPoiResult.getAllPoi().get(index).name);
//			
//			PoiInfo poi = mPoiResult.getAllPoi().get(index);
//			LatLng ll = marker.getPosition();
//			Button popupButton = new Button(BMapManager.getContext());
//			InfoWindow mInfoWindow = new InfoWindow(popupButton, ll, -47);
//			mBaiduMap.showInfoWindow(mInfoWindow);
//			
//			/* 修改popup形状和内容  */
//	        
//	        popupButton.setText(""+poi.name);	
//	        popupButton.setTextColor(android.graphics.Color.BLUE);
//	        popupButton.setBackgroundResource(R.drawable.popup);
//	             
//	        popupButton.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					PoiInfo poi = mPoiResult.getAllPoi().get(marker.getExtraInfo().getInt("index"));
//					//Toast.makeText(BMapManager.getContext(), "addr:"+poi.address, Toast.LENGTH_SHORT).show();
//					PoiSearch.newInstance().searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
//
//				}
//			});
//			
//		}
		return false;
	}


	@Override
	public boolean onPolylineClick(Polyline arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<OverlayOptions> getOverlayOptions() {
		// TODO Auto-generated method stub

		if (mPoiResult == null || mPoiResult.getAllPoi() == null) {
			return null;
		}
		List<OverlayOptions> markerList = new ArrayList<OverlayOptions>();
		int markerSize = 0;
		for (int i = 0; i < mPoiResult.getAllPoi().size() && markerSize < MY_MAX_POI_SIZE; i++) {
			if (mPoiResult.getAllPoi().get(i).location == null) {
				continue;
			}
			markerSize++;
			Bundle bundle = new Bundle();
			bundle.putInt("index", i);
			markerList.add(new MarkerOptions()
					.icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + markerSize + ".png")).extraInfo(bundle)
					.position(mPoiResult.getAllPoi().get(i).location));

		}
		return markerList;
	}
}
