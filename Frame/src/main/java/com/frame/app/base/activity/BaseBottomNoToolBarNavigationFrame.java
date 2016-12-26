package com.frame.app.base.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.chongwuzhiwu.frame.R;
import com.frame.app.view.MyFragmentTabHost;

/**
 * Created by jy on 15/11/3.
 */
public abstract class BaseBottomNoToolBarNavigationFrame extends BaseActivity {

    //定义FragmentTabHost对象
    private MyFragmentTabHost mTabHost;

//    //定义数组来存放Fragment界面
//    private Class fragmentArray[] = {F_Home_Pager.class, F_Friends_Pager.class, F_Found_Pager.class, F_My_Pager.class};
//
//    //定义数组来存放按钮图片
//    private int mImageViewArray[] = {R.mipmap.icon_bottom_button, R.mipmap.icon_bottom_button, R.mipmap.icon_bottom_button, R.mipmap.icon_bottom_button};
//
//    //Tab选项卡的文字
//    private String mTextviewArray[] = {"首页", "消息", "发现", "我的"};


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(getThis(), A_Login.class));
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTabHostListener();
    }

    /**
     * 初始化组件
     */
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_bottomnavigation);
        mTabHost = (MyFragmentTabHost) findViewById(R.id.tabhost);
        //实例化布局对象

        //实例化TabHost对象，得到TabHost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//        mTabHost.getTabWidget().setDividerDrawable(R.color.white);
//        mTabHost.getTabWidget().setStripEnabled(true);
        //得到fragment的个数
        int count = getFragmentArray().length;

        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getTextviewArray()[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, getFragmentArray()[i], null);
            //设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.bottom_color);
        }
    }

    private void  initTabHostListener(){
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setOnTabChangedListener(tabId);
            }
        });
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = View.inflate(getThis(), R.layout.tab_item_view2, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(getImageViewArray()[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(getTextviewArray()[index]);
        return view;
    }

    protected void setOnTabChangedListener(String tabId){

    }

    protected MyFragmentTabHost getTabHost(){
        return mTabHost;
    }

    protected View getTabView(int index){
        return getTabHost().getTabWidget().getChildAt(index);
    }

    protected abstract String[] getTextviewArray();

    protected abstract int[] getImageViewArray();

    protected abstract Class[] getFragmentArray();
}

