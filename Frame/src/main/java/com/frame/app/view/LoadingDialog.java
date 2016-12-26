package com.frame.app.view;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chongwuzhiwu.frame.R;

/**
 * 菊花类
 * 
 * @author jiangyu
 *
 */
public class LoadingDialog extends AlertDialog {

	private TextView loading_msg;
	private static int model = 1;
	private String message = null;
	private boolean isCancelFocus = false;
	private boolean isShowText = true;

	/**
	 * 
	 * @param context
	 * @param model
	 *            1、无背景、无文字的LoadingDialog
	 */
	public LoadingDialog(Context context) {
		super(context, R.style.AlertDialogStyle);
		init();
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		init();
	}

	private void init() {
		message = "";
//		message = getContext().getResources().getString(R.string.loading);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
		if (isCancelFocus) {
			cancelFocus();
		}
	}

	public void setText(String message) {
		this.message = message;
		if (loading_msg != null) {
			loading_msg.setText(this.message);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		switch (model) {
		case 1:
			showLoadingDialog();
			break;
		case 2:
			showCustomLoadingDialog();
			break;
		case 3:
			showRotateLoadingDialog();
			break;
		default:
			showDefaultLoadingDialog();
			break;
		}
	}

	public void setText(int resId) {
		setText(getContext().getResources().getString(resId));
	}

	/**
	 * 有背景、文字的LoadingDialog
	 */
	private void showDefaultLoadingDialog() {
		setContentView(R.layout.view_tips_loading);
		setTextView(R.id.tips_loading_msg);
	}

	/**
	 * 无背景、无文字的LoadingDialog
	 */
	private void showLoadingDialog() {
		setContentView(R.layout.ios_loading);

	}

	/**
	 * 无背景、无文字的LoadingDialog
	 */
	private void showRotateLoadingDialog() {
		setContentView(R.layout.loading_dialog);// 得到加载view
		ImageView spaceshipImage = (ImageView) findViewById(R.id.img);
		spaceshipImage.setImageResource(R.mipmap.progress_o);
//		spaceshipImage.setImageResource(R.drawable.juhua_1);
		setTextView(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				getContext(), R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
	}

	/**
	 * 无背景、无文字的LoadingDialog
	 */
	private void showCustomLoadingDialog() {
		setContentView(R.layout.customprogressdialog);
		setTextView(R.id.id_tv_loadingmsg);// 提示文字
		ImageView imageView = (ImageView) findViewById(R.id.loadingImageView);
		imageView.setBackgroundResource(R.drawable.progress_round_juhua);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView
				.getBackground();
		animationDrawable.start();
	}

	private void setTextView(int id) {
		loading_msg = (TextView) findViewById(id);// 提示文字
		if (!isShowText) {
			loading_msg.setVisibility(View.GONE);
		}else{
			loading_msg.setText(message);
		}
	}

	/**
	 * 让LoadingDialog不抢占焦点
	 */
	public void cancelFocus() {
		getWindow().getAttributes().gravity = Gravity.CENTER;
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
	}
}
