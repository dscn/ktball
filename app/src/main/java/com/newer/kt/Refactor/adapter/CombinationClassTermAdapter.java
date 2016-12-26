package com.newer.kt.Refactor.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileUtil;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.Combinations;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinationDetail;
import com.newer.kt.Refactor.Manager.ListDownManager;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.SchoolGymCourseCombinationDetailRequest;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.LessonListAvtivity;
import com.newer.kt.Refactor.view.CustomViewPager;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class CombinationClassTermAdapter extends BaseRecyclerViewAdapter<String, CombinationClassTermAdapter.CombinationClassTermViewHolder> {

    /**
     * 空间是否居中显示
     */
    private boolean isCenter;

    public CombinationClassTermAdapter(RecyclerView recyclerView, List<String> strings) {
        super(recyclerView, strings);
    }

    public CombinationClassTermAdapter(RecyclerView recyclerView, List<String> strings, boolean isCenter) {
        super(recyclerView, strings);
        this.isCenter = isCenter;
    }

    @Override
    protected void bindView(CombinationClassTermViewHolder holder, int position, String model) {
        holder.name.setText(model);
    }

    @Override
    public CombinationClassTermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CombinationClassTermViewHolder viewHolder = new CombinationClassTermViewHolder(getRecyclerView(), layoutInflater(getContext(), isCenter ? R.layout.item_center_text : R.layout.item_combinationclassterm, parent, false), this);
        return viewHolder;
    }

    public class CombinationClassTermViewHolder extends BaseViewHolder {

        public TextView name;

        public CombinationClassTermViewHolder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);
            name = (TextView) rootView.findViewById(R.id.item_combinationclassterm_name);
        }
    }

}
