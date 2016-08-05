package com.chenglong.muscle;

import com.chenglong.muscle.puzzle.SettingActivity;
import com.chenglong.muscle.tool.AlbumActivity;
import com.chenglong.muscle.tool.CalculationActivity;
import com.chenglong.muscle.tool.DietActivity;
import com.chenglong.muscle.tool.InfoActivity;
import com.chenglong.muscle.tool.LessionActivity;
import com.chenglong.muscle.tool.MapActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class ToolFrag2 extends Fragment implements android.widget.AdapterView.OnItemClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.tool, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		GridView gv = (GridView) getActivity().findViewById(R.id.tool_grid);
		gv.setAdapter(new ToolAdapter(getActivity()));
		gv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0: /* 地图 */
		{
			Intent intent = new Intent(getActivity(), MapActivity.class);
			startActivity(intent);
			break;
		}
		case 1: /* 课程  */
		{
			Intent intent = new Intent(getActivity(), LessionActivity.class);
			startActivity(intent);
			break;
		}
		case 2: /* 餐饮  */
		{
			Intent intent = new Intent(getActivity(), DietActivity.class);
			startActivity(intent);
			break;
		}
		case 3: /* 咨询  */
		{
			Intent intent = new Intent(getActivity(), InfoActivity.class);
			startActivity(intent);
			break;
		}
		case 4: /* 游戏  */
		{
			Intent intent = new Intent(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
		}
		case 5: /* 计算  */
		{
			Intent intent = new Intent(getActivity(), CalculationActivity.class);
			startActivity(intent);
			break;
		}
		case 6: /* 相册 */
		{
			Intent intent = new Intent(getActivity(), AlbumActivity.class);
			startActivity(intent);
			break;
		}
		default: {
			break;
		}
		}
	}
}
