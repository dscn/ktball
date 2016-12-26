package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.view.MyPullUpListView;
import com.newer.kt.Refactor.view.video.VideoPlayer;
import com.newer.kt.Refactor.view.video.VideoPlayerStandard;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/21.
 */

public class VideoCenterActivity extends BaseActivity implements MyPullUpListView.MyPullUpListViewCallBack,SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.listView1)
    MyPullUpListView mListView;
    private List<String> mList = new ArrayList<>();
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseAdapter mAdapter;
    private boolean isLoadMore;
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video_center);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mListView.setMyPullUpListViewCallBack(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mList.add("dasdsadas");
        mList.add("dasdsadas");
        mList.add("dasdsadas");
        mList.add("dasdsadas");
        mListView.setAdapter(mAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Object getItem(int position) {
                return mList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                VideoView videoView;
                if (convertView == null) {
                    videoView = new VideoView();
                    convertView = getLayoutInflater().inflate(R.layout.item_video_center, null);
                    videoView.videoPlayer = (VideoPlayerStandard) convertView.findViewById(R.id.video_view);
                    convertView.setTag(videoView);
                } else {
                    videoView = (VideoView) convertView.getTag();
                }
                videoView.videoPlayer.setUp("http://2449.vod.myqcloud.com/2449_bfbbfa3cea8f11e5aac3db03cda99974.f20.mp4"
                        , "KT测试");
                return convertView;
            }
        });

    }

    @OnClick(R.id.cancle)
    public void cancle() {
        finish();
    }

    @Override
    public void scrollBottomState() {
        if(!isLoadMore) {
            isLoadMore = true;
            mList.add("dasdsadas");
            mList.add("dasdsadas");
            mList.add("dasdsadas");
            mList.add("dasdsadas");
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        if (!isLoadMore) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }


    public class VideoView {
        VideoPlayerStandard videoPlayer;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoPlayer.releaseAllVideos();
    }
}
