package com.frame.app.base.activity;

import android.app.Dialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.frame.app.application.BaseApplication;
import com.frame.app.manager.AppManager;
import com.frame.app.utils.LogUtils;
import com.frame.app.view.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

	/** UI 线程ID */
	private long mUIThreadId;
	protected LoadingDialog loadingDialog;
	private DisplayMetrics  displayMetrics = null;
	private String TAG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = getClass().getSimpleName();
		AppManager.getAppManager().addActivity(getThis()); // 加入activity栈管理器
		loadingDialog = new LoadingDialog(getThis()); // 初始化进度条
		initView(savedInstanceState);
		ButterKnife.bind(this);//绑定
		EventBus.getDefault().register(this);
		initData(savedInstanceState);
		setListener();
		LogUtils.allowD = BaseApplication.isTest; // 测试环境下允许打log
		LogUtils.e(this.getClass().getSimpleName()+"");
	}

	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(getThis());
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	protected <V> V getViewById(int resId) {
		return (V) findViewById(resId);
	}

	public Drawable getDownDrawable(String path) {
		return new BitmapDrawable(path);
	}

	/**
	 * 初始化handler
	 */
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			initHandler(msg);
		};
	};

	/**
	 * 业务数据处理后的回调, 一般用于异步网络请求完成后执行的界面刷新
	 */
	protected abstract void initHandler(android.os.Message msg);

	/**
	 * 基层Activity的初始化, 如有必要则重写此方法(如界面crash之后的恢复, 引入外库等)
	 * 
	 * @param savedInstanceState
	 */
	protected abstract void initView(Bundle savedInstanceState);

	/**
	 * 给View控件添加事件监听器
	 */
	protected abstract void setListener();

	/**
	 * 这两天一直被这个问题困扰，假如app长时间在后台运行，再点击进入会crash，而且fragment页面有重叠现象，让我十分不爽。研究了一天，
	 * 终于明白其中的原理并加以解决。解决办法如下： 如果系统内存不足、或者切换横竖屏、或者app长时间在后台运行，Activity都可能会被系统回收，
	 * 然后Fragment并不会随着Activity的回收而被回收，从而导致，Fragment丢失对应的Activity。
	 * 这里，假设我们继承于FragmentActivity的类为MainActivity，其中用到的Fragment为FragmentA。
	 * app发生的变化为：某种原因系统回收MainActivity——FragmentA被保存状态未被回收——再次点击app进入——
	 * 首先加载的是未被回收的FragmentA的页面
	 * ——由于MainActivity被回收，系统会重启MainActivity，FragmentA也会被再次加载
	 * ——页面出现混乱，因为一层未回收的FragmentA覆盖在其上面
	 * ——（假如FragmentA使用到了getActivity()方法）会报空指针，出现crash。 原理虽然有点绕，但是解决办法很简单：
	 * MainActivity重写onSaveInstanceState方法
	 * ，将super.onSaveInstanceState(outState);注释掉
	 * ，让其不再保存Fragment的状态，达到其随着MainActivity一起被回收的效果！
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {

	}

	/**
	 * 初始化界面数据
	 */
	protected abstract void initData(Bundle savedInstanceState);

	/**
	 * 获取UI线程ID
	 * 
	 * @return UI线程ID
	 */
	public long getUIThreadId() {
		return mUIThreadId;
	}

	public BaseActivity getThis() {
		return this;
	}

	public boolean post(Runnable run) {
		return mHandler.post(run);
	}

	public boolean postDelayed(Runnable run, long delay) {
		return mHandler.postDelayed(run, delay);
	}

	public void removeCallbacks(Runnable run) {
		mHandler.removeCallbacks(run);
	}

	public int getResourcesColor(int resId) {
		return getThis().getResources().getColor(resId);
	}

	public String[] getStringArray(int resId) {
		return getThis().getResources().getStringArray(resId);
	}

	// 吐司
	public void showToast(String str) {
		if(str != null && !"".equals(str.trim())){
			showToast(str, Toast.LENGTH_SHORT);
		}
	}

	public void showToast(int resId) {
		showToast(resId, Toast.LENGTH_SHORT);
	}

	/** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
	public void showToast(final int resId, final int duration) {
		if (Process.myTid() == mUIThreadId) {
			// 调用在UI线程
			Toast.makeText(getBaseContext(), resId, duration).show();
		} else {
			// 调用在非UI线程
			post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getBaseContext(), resId, duration).show();
				}
			});
		}
	}

	/** 对toast的简易封装。线程安全，可以在非UI线程调用。 */
	public void showToast(final CharSequence text, final int duration) {
		if (Process.myTid() == mUIThreadId) {
			// 调用在UI线程
			Toast.makeText(getBaseContext(), text, duration).show();
		} else {
			// 调用在非UI线程
			post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getBaseContext(), text, duration).show();
				}
			});
		}
	}

	public void showTextToast(String str) {
		if (BaseApplication.isTest) {
			showToast(str);
		}
	}

	public void showLoadingDiaglog() {
		showLoadingDiaglog(null);
	}

	/**
	 * 显示正在加载数据的dialog
	 */
	public void showLoadingDiaglog(String tip) {
		if (loadingDialog == null) {
			loadingDialog = new LoadingDialog(this); // 初始化进度条
		}
		if (!TextUtils.isEmpty(tip)) {
			loadingDialog.setText(tip);
		}
		loadingDialog.show();
		dimActivity(loadingDialog, 0f);
	}

	/**
	 * 显示正在加载数据的dialog
	 */
	public void showLoadingDiaglog(int resId) {
		if (loadingDialog == null) {
			loadingDialog = new LoadingDialog(getThis()); // 初始化进度条
		}
		loadingDialog.setText(resId);
		loadingDialog.show();
		dimActivity(loadingDialog, 0f);
	}

	/**
	 * 关闭正在显示的dialog
	 */
	public void closeLoadingDialog() {
		if (loadingDialog != null) {
			if (loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
		}
	}

	public Handler getHandler() {
		return mHandler;
	}

	public void dimActivity(Dialog dialog, float dimAmount) {
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = dimAmount;
		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	public void onResume() {
		super.onResume();
		Bugtags.onResume(this);
//		ThirdPartyManager.onResume(this);
	}
	public void onPause() {
		super.onPause();
		Bugtags.onPause(this);
//		ThirdPartyManager.onPause(this);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		//注：回调 3
		Bugtags.onDispatchTouchEvent(this, event);
		return super.dispatchTouchEvent(event);
	}

	protected <T extends ContextWrapper> void startActivity(Class<T> clz) {
		startActivity(new Intent(this, clz));
	}

	public int dp2px(float f)
	{
		return (int)(0.5F + f * getScreenDensity());
	}

	public float getScreenDensity() {
		if (this.displayMetrics == null) {
			setDisplayMetrics(getResources().getDisplayMetrics());
		}
		return this.displayMetrics.density;
	}

	public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
		this.displayMetrics = DisplayMetrics;
	}

	public String getTAG() {
		return TAG;
	}

	public void setTAG(String TAG) {
		this.TAG = TAG;
	}

	public void showDialogToast(String str, String str1,DialogInterface.OnClickListener listener1,String str2,DialogInterface.OnClickListener listener2){
		AlertDialog.Builder builder = new AlertDialog.Builder(getThis());
		builder.setMessage(str);
		builder.setTitle("提示");
		if(listener1 == null){
			listener1 = showDialogToastListener;
		}
		builder.setNegativeButton(str1,listener1);
		builder.setPositiveButton(str2,listener2);
		builder.create().show();
	}

	public void showDialogToast(String str, DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getThis());
		builder.setMessage(str);
		builder.setTitle("提示");
		if (listener == null) {
			listener = showDialogToastListener;
		}
		builder.setNegativeButton("确定", listener);
		builder.create().show();
	}

	private DialogInterface.OnClickListener showDialogToastListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};

	public void showDialogToast(String str) {
		showDialogToast(str, null);
	}

	public LoadingDialog getLoadingDialog() {
		return loadingDialog;
	}

	@Subscribe
	public void Default(BaseEvent event){

	}

	private class BaseEvent{

	}
}
