package com.lenovox.newsclient;

import java.util.ArrayList;
import java.util.List;

import bean.NewsBean;
import bean.NewsBean.NewsData;
import bean.NewsBean.Result;

import com.google.gson.Gson;
import com.lenovox.newsclient.constans.Constans;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.AvoidXfermode.Mode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TabDetail {
	public Activity mActivity;
	public View mRootView;
	private ListView mListView;
	public String mType;
	String mUrl;
	private NewsBean mNewsBean;
	private ArrayList<NewsData> mLvData;
	private ProgressBar mProgressBar;
	private ProgressBar mPb;

	public TabDetail(Activity activity, String string) {
		// Log.e("eeee3333", string + "");
		mActivity = activity;
		mRootView = initView();
		mType = string;
		mUrl = Constans.ADDRESS_STRING + "?type=" + mType
				+ "&key=ee7df40d01c7d8a3a65b4ce6932ce34d";
		// Log.e("eeee44444", string + "");
	}

	public View initView() {
		// Log.e("eeee", mActivity + "");
		View view = View.inflate(mActivity, R.layout.tabdetail, null);
		mListView = (ListView) view.findViewById(R.id.tab_detail_listview);
		// 1.找到activity根部的ViewGroup，类型都为FrameLayout。
		FrameLayout rootContain = (FrameLayout) view.findViewById(R.id.frame);
		// 2.初始化控件显示的位置
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		lp.gravity = Gravity.CENTER;
		// 3.设置控件显示位置
		mPb = new ProgressBar(mActivity);
		mPb.setLayoutParams(lp);
		mPb.setVisibility(View.VISIBLE);// 默认不显示
		// 4.将控件加到根节点下
		rootContain.addView(mPb);
		// return pb;
		return view;
	}

	public void initData() {
		mProgressBar = new ProgressBar(mActivity);

		mProgressBar.setVisibility(View.VISIBLE);

		getDataFromServer();
	}

	private void getDataFromServer() {

		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// Log.e("dataaaaaaaa", arg0.result);
				parseData(arg0.result);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// Toast.
			}

		});
	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		mNewsBean = gson.fromJson(result, NewsBean.class);
		mLvData = mNewsBean.result.data;
		mListView.setAdapter(new adapter());
		mProgressBar.setVisibility(View.GONE);
		mPb.setVisibility(View.GONE);
		loadAfter();
	}

	private void loadAfter() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				MainActivity mainActivity = (com.lenovox.newsclient.MainActivity) mActivity;
				// 浏览新闻详情
			//	mainActivity.skip("path", lvData.get(arg2).url);
				Intent intent=new Intent();
				intent.setClass(mActivity, NewsExploreActivity.class);
				intent.putExtra("path", mLvData.get(arg2).url);
				mainActivity.skip(intent);
				// 标记已读状态
				SharedPreferences sharedPreferences = mainActivity
						.getSharedPreferences("readState", Context.MODE_PRIVATE);
				Editor edit = sharedPreferences.edit();
				String uniquekey = mLvData.get(arg2).uniquekey;
				String uniquekeyString = sharedPreferences.getString(
						"uniquekey", "ooo,") + uniquekey;
				edit.putString("uniquekey", uniquekeyString + ",");
				edit.commit();
				changeReadState(arg1);
			}
		});
	}

	protected void changeReadState(View arg1) {
		TextView textView = (TextView) arg1.findViewById(R.id.lv_title);
		textView.setTextColor(Color.GRAY);
	}

	class adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mLvData.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			BitmapUtils bitmapUtils = new BitmapUtils(mActivity);
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(mActivity,
						R.layout.tab_detail_listview_item, null);
				viewHolder.imageView = (ImageView) convertView
						.findViewById(R.id.lv_imageView1);
				viewHolder.textView = (TextView) convertView
						.findViewById(R.id.lv_title);
				viewHolder.textView2 = (TextView) convertView
						.findViewById(R.id.lv_date);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();

			}
			bitmapUtils.display(viewHolder.imageView,
					mLvData.get(position).thumbnail_pic_s);
			viewHolder.textView.setText(mLvData.get(position).title);
			viewHolder.textView2.setText(mLvData.get(position).date);
			return convertView;
		}

	}

	class ViewHolder {
		ImageView imageView;
		TextView textView;
		TextView textView2;
	}
}
