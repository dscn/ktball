package com.frame.app.base.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.frame.app.application.BaseApplication;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.manager.ThirdPartyManager;
import com.frame.app.view.LoadingDialog;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

	/** UI 线程ID */
	private long mUIThreadId;
	public View rootView;
	private LoadingDialog loadingDialog;
	private Activity mContext;
	protected String TAG;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
		TAG = this.getClass().getSimpleName();
		loadingDialog = new LoadingDialog(activity); // 初始化进度条
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 避免多次从xml中加载布局文件
		if (rootView == null) {
			initView(savedInstanceState);
			setListener();
			initData(savedInstanceState);
		} else {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
		}
		return rootView;
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onResume() {
		super.onResume();
		ThirdPartyManager.onPageStart(getClass().getSimpleName()); //统计页面
	}

	/**
	 * 友盟统计
	 */
	@Override
	public void onPause() {
		super.onPause();
		ThirdPartyManager.onPageEnd(getClass().getSimpleName());
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			onUserVisible();
		}
	}

	protected void setContentView(@LayoutRes int layoutResID) {
		rootView = LayoutInflater.from(getThis()).inflate(layoutResID, null);
		ButterKnife.bind(this,rootView);
	}

	protected void setContentView(View layoutResID) {
		rootView = layoutResID;
	}

	/**
	 * 查找View
	 *
	 * @param id   控件的id
	 * @param <VT> View类型
	 * @return
	 */
	protected <VT extends View> VT getViewById(@IdRes int id) {
		return (VT) rootView.findViewById(id);
	}

	public View getRootView() {
		return rootView;
	}

	protected BaseActivity getThis() {
		return (BaseActivity) mContext;
	}

	protected BaseActivity getBaseActivity() {
		if(mContext instanceof BaseActivity) {
			return (BaseActivity) mContext;
		} else {
			return null;
		}
	}
	
	protected void setThis(Activity context){
		mContext = context;
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
	 * 获得Handler
	 * @return
	 */
	protected Handler getHandler() {
		return mHandler;
	}

	/**
	 * 获取UI线程ID
	 * @return UI线程ID
	 */
	public long getUIThreadId() {
		return mUIThreadId;
	}

	/**
	 * 在非UI线程调用,能更改UI
	 * @param run
	 * @return
	 */
	public boolean post(Runnable run) {
		return mHandler.post(run);
	}

	/**
	 * delay时间后调用此Runnable对象
	 * @param run 要执行的run方法
	 * @param delay 定时时间
	 * @return
	 */
	public boolean postDelayed(Runnable run, long delay) {
		return mHandler.postDelayed(run, delay);
	}

	/**
	 * 关闭定时器
	 * @param run
	 */
	public void removeCallbacks(Runnable run) {
		mHandler.removeCallbacks(run);
	}

	public String[] getStringArray(int resId) {
		return getThis().getResources().getStringArray(resId);
	}

	public int getColor(int resId) {
		return getThis().getResources().getColor(resId);
	}

	/**
	 * 对toast的简易封装。线程安全，可以在非UI线程调用。
	 * @param resId
	 * @param duration
	 */
	public void showToast(final int resId, final int duration) {
		if (Process.myTid() == mUIThreadId) {
			// 调用在UI线程
			Toast.makeText(getThis(), resId, duration).show();
		} else {
			// 调用在非UI线程
			post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getThis(), resId, duration).show();
				}
			});
		}
	}

	/**
	 * 弹出提示
	 * @param str
	 */
	public void showToast(String str) {
		showToast(str, Toast.LENGTH_SHORT);
	}

	/**
	 * 弹出提示
	 * @param resId
	 */
	public void showToast(int resId) {
		showToast(resId, Toast.LENGTH_SHORT);
	}

	/**
	 * 对toast的简易封装。线程安全，可以在非UI线程调用。
	 * @param text
	 * @param duration
	 */
	public void showToast(final CharSequence text, final int duration) {
		if (Process.myTid() == mUIThreadId) {
			// 调用在UI线程
			Toast.makeText(getThis(), text, duration).show();
		} else {
			// 调用在非UI线程
			post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getThis(), text, duration).show();
				}
			});
		}
	}

	/**
	 * 弹出测试吐司
	 * @param str
	 */
	public void getTextToast(String str) {
		if (BaseApplication.isTest) {
			showToast(str);
		}
	}

	/**
	 * 显示进度条
	 */
	public void showLoadingDiaglog() {
		showLoadingDiaglog(null);
	}

	/**
	 * 显示进度条
	 * @param str
	 */
	public void showLoadingDiaglog(String str) {
		if (mContext != null) {
			if (loadingDialog == null) {
				loadingDialog = new LoadingDialog(getThis()); // 初始化进度条
			}
			if (!TextUtils.isEmpty(str)) {
				loadingDialog.setText(str);
			}
			loadingDialog.show();
			dimActivity(loadingDialog, 0f);
		}
	}
	
	/**
	 * 显示正在加载数据的dialog
	 * @param resId
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

	/**
	 * 取消LoadingDialog的背景颜色
	 * @param dialog
	 * @param dimAmount
	 */
	private void dimActivity(Dialog dialog, float dimAmount) {
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.dimAmount = dimAmount;
		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	/**
	 * 初始化View控件
	 */
	protected abstract void initView(Bundle savedInstanceState);

	/**
	 * 给View控件添加事件监听器
	 */
	protected abstract void setListener();

	/**
	 * 处理业务逻辑，状态恢复等操作
	 *
	 * @param savedInstanceState
	 */
	protected abstract void initData(Bundle savedInstanceState);

	/**
	 * 当fragment对用户可见时，会调用该方法，可在该方法中懒加载网络数据
	 */
	protected abstract void onUserVisible();

	/**
	 * 业务数据处理后的回调, 一般用于异步网络请求完成后执行的界面刷新
	 */
	protected abstract void initHandler(android.os.Message msg);

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

	protected <T extends ContextWrapper> void startActivity(Class<T> clz) {
		startActivity(new Intent(getThis(), clz));
	}
}
