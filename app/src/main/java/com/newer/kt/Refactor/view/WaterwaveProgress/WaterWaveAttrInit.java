package com.newer.kt.Refactor.view.WaterwaveProgress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.newer.kt.R;

public class WaterWaveAttrInit {

	private int progressWidth; // 进度条宽度
	private int progressColor;
	private int progressBgColor;
	private int waterWaveColor;
	private int waterWaveBgColor;
	private int progress2WaterWidth; // 进度条和水波之间的间距
	private boolean showProgress; // 是否显示进度条
	private boolean showNumerical; // 是否显示百分比
	private int fontSize;
	private int textColor;
	private int progress;
	private int maxProgress;

	@SuppressLint("Recycle")
	public WaterWaveAttrInit(Context context, AttributeSet attrs, int defStyle) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.CustomWaterWaveProgress, defStyle, 0);
		progressWidth = typedArray.getDimensionPixelOffset(
				R.styleable.CustomWaterWaveProgress_CustomprogressWidth, 0);
		progressColor = typedArray.getColor(
				R.styleable.CustomWaterWaveProgress_CustomprogressColor, context.getResources().getColor(R.color.main_bottom_text_color));
		progressBgColor = typedArray.getColor(
				R.styleable.CustomWaterWaveProgress_CustomprogressBgColor, Color.parseColor("#222222"));
		waterWaveColor = typedArray.getColor(
				R.styleable.CustomWaterWaveProgress_CustomwaterWaveColor, context.getResources().getColor(R.color.main_bottom_text_color));
		waterWaveBgColor = typedArray.getColor(
				R.styleable.CustomWaterWaveProgress_CustomwaterWaveBgColor, Color.parseColor("#222222"));
		progress2WaterWidth = typedArray.getDimensionPixelOffset(
				R.styleable.CustomWaterWaveProgress_Customprogress2WaterWidth, 0);
		showProgress = typedArray.getBoolean(
				R.styleable.CustomWaterWaveProgress_CustomshowProgress, true);
		showNumerical = typedArray.getBoolean(
				R.styleable.CustomWaterWaveProgress_CustomshowNumerical, true);
		fontSize = typedArray.getDimensionPixelOffset(
				R.styleable.CustomWaterWaveProgress_CustomfontSize, 0);
		textColor = typedArray.getColor(
				R.styleable.CustomWaterWaveProgress_CustomtextColor, 0xFFFFFFFF);
		progress = typedArray.getInteger(
				R.styleable.CustomWaterWaveProgress_Customprogress, 15);
		maxProgress = typedArray.getInteger(
				R.styleable.CustomWaterWaveProgress_CustommaxProgress, 100);
		typedArray.recycle();
	}

	public int getProgressWidth() {
		return progressWidth;
	}

	public int getProgressColor() {
		return progressColor;
	}

	public int getProgressBgColor() {
		return progressBgColor;
	}

	public int getWaterWaveColor() {
		return waterWaveColor;
	}

	public int getWaterWaveBgColor() {
		return waterWaveBgColor;
	}

	public int getProgress2WaterWidth() {
		return progress2WaterWidth;
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	public boolean isShowNumerical() {
		return showNumerical;
	}

	public int getFontSize() {
		return fontSize;
	}

	public int getTextColor() {
		return textColor;
	}

	public int getProgress() {
		return progress;
	}

	public int getMaxProgress() {
		return maxProgress;
	}

}
