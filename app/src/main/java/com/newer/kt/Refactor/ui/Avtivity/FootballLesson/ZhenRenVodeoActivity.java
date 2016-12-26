package com.newer.kt.Refactor.ui.Avtivity.FootballLesson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.adapter.AdapterItem;
import com.newer.kt.Refactor.adapter.CommonRcvAdapter;
import com.newer.kt.Refactor.ui.Avtivity.Settings.PlayerActivity;
import com.newer.kt.Refactor.view.video.VideoBuriedPointStandard;
import com.newer.kt.Refactor.view.video.VideoPlayer;
import com.newer.kt.Refactor.view.video.VideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by huangbo on 2016/10/4.
 */
public class ZhenRenVodeoActivity extends BaseActivity {
    //    private VideoView videoView;
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video_zhgenren);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mList = getIntent().getStringArrayListExtra("info");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CommonRcvAdapter<String>(mList) {
            @NonNull
            @Override
            public AdapterItem getItemView(Object type) {
                return new ZhenRenItem();
            }

            @Override
            protected void onItemClick(String model, int position) {
                super.onItemClick(model, position);
                Intent intent = new Intent(ZhenRenVodeoActivity.this, PlayerActivity.class);
                intent.putExtra("vid", mList.get(position).split(",")[position].split("/")[mList.get(position).split(",")[position].split("/").length - 1]);
                startActivity(intent);
            }
        });
//        videoView = (VideoView) findViewById(R.id.mv_player);
//        VideoPlayer.releaseAllVideos();
//        videoView.setUp("http://swarm-pdt.b0.upaiyun.com/mri/mvs/979ffe9af08e470c80b9e873b577e138.mp4","csac4sa56c");
//        videoView.setJcBuriedPointStandard(new VideoBuriedPointStandard() {
//            @Override
//            public void onClickStartThumb(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickBlank(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickBlankFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickStartIcon(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickStartError(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickStop(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickStopFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickResume(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickResumeFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickSeekbar(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onClickSeekbarFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onAutoComplete(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onAutoCompleteFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onEnterFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onQuitFullscreen(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onTouchScreenSeekVolume(String url, Object... objects) {
//
//            }
//
//            @Override
//            public void onTouchScreenSeekPosition(String url, Object... objects) {
//
//            }
//        });


    }

    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }
}
