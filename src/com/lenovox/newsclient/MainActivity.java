package com.lenovox.newsclient;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.PageIndicator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class MainActivity extends Activity {

	private ViewPager mViewPager;
	private PageIndicator mPageIndicator;
	private List<String> mTabTitleList;
	private List<TabDetail> mViewList;
	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/**
		 * LeanCloud 
		 */
		AVOSCloud.initialize(this, "8kxkq9LD1wsgsDWAFJrpcSJc-gzGzoHsz",
				"se6LED5IHLAozGE8r57EKul7");
		AVAnalytics.trackAppOpened(getIntent());

		AVObject testObject = new AVObject("TestObject");
		
		testObject.put("foo", "bar");
		testObject.saveInBackground();
		
		
		mActivity = MainActivity.this;
		mViewPager = (ViewPager) findViewById(R.id.newsdetailnews_pager);
		mPageIndicator = (PageIndicator) findViewById(R.id.indicator);
		// 新闻类型标题
		initData();
		
		mViewPager.setAdapter(new adapter());
		mPageIndicator.setViewPager(mViewPager);
		
		//新闻类型详情初始化数据
		mViewList.get(0).initData();
		mPageIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mViewList.get(arg0).initData();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	// public void skip(String string, String url) {
	// Intent intent=new Intent(activity, NewsExploreActivity.class);
	// intent.putExtra(string, url);
	// startActivity(intent);
	// }
	public void skip(Intent intent) {
		startActivity(intent);
	}

	private void initData() {
		mTabTitleList = new ArrayList<String>();
		mTabTitleList.add("头条");
		mTabTitleList.add("社会");
		mTabTitleList.add("国内");
		mTabTitleList.add("国际");
		mTabTitleList.add("娱乐");
		mTabTitleList.add("体育");
		mTabTitleList.add("军事");
		mTabTitleList.add("科技");
		mTabTitleList.add("财经");
		mTabTitleList.add("时尚");
		List<String> tabTiltlePinyi = new ArrayList<String>();
		tabTiltlePinyi.add("top");
		tabTiltlePinyi.add("shehui");
		tabTiltlePinyi.add("guonei");
		tabTiltlePinyi.add("guoji");
		tabTiltlePinyi.add("yule");
		tabTiltlePinyi.add("tiyu");
		tabTiltlePinyi.add("junshi");
		tabTiltlePinyi.add("keji");
		tabTiltlePinyi.add("caijing");
		tabTiltlePinyi.add("shishang");
		mViewList = new ArrayList<TabDetail>();
		for (int i = 0; i < mTabTitleList.size(); i++) {
			// Log.e("eeee22222", activity + "");
			TabDetail tabDetail = new TabDetail(mActivity, tabTiltlePinyi.get(i));
			mViewList.add(tabDetail);
		}
		// Log.e("eeeedddd", viewList.size() + "");
	}

	class adapter extends PagerAdapter {
		@Override
		public CharSequence getPageTitle(int position) {
			return mTabTitleList.get(position);
		}

		@Override
		public int getCount() {
			return mTabTitleList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mViewList.get(position).mRootView);
			return mViewList.get(position).mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
}
