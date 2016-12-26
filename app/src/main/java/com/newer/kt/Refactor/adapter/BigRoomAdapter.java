package com.newer.kt.Refactor.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.SharedPreferencesUtils;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.BigClassrooms;
import com.newer.kt.Refactor.Entitiy.Combinations;
import com.newer.kt.Refactor.Entitiy.SchoolGymCourseCombinationDetail;
import com.newer.kt.Refactor.Manager.ListDownManager;
import com.newer.kt.Refactor.Net.CallServer;
import com.newer.kt.Refactor.Net.HttpListener;
import com.newer.kt.Refactor.Request.SchoolGymCourseCombinationDetailRequest;
import com.newer.kt.Refactor.ui.Avtivity.FootballLesson.LessonListAvtivity;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.List;

/**
 * Created by jy on 16/8/10.
 */
public class BigRoomAdapter extends BaseRecyclerViewAdapter<BigClassrooms, BigRoomAdapter.BigRoomViewhoder> {

    public BigRoomAdapter(RecyclerView recyclerView, List<BigClassrooms> combinationses) {
        super(recyclerView, combinationses);
    }

    @Override
    protected void bindView(final BigRoomViewhoder holder, int position, final BigClassrooms model) {
        holder.name.setText(model.name);
        holder.details.setText(model.intro);
    }

    @Override
    public BigRoomViewhoder onCreateViewHolder(ViewGroup parent, int viewType) {
        BigRoomViewhoder combinationClassViewhoder = new BigRoomViewhoder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_bigroom, parent, false), this);
        return combinationClassViewhoder;
    }

    public class BigRoomViewhoder extends BaseViewHolder {

        public TextView name;
        public TextView details;

        public BigRoomViewhoder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);

            name = (TextView) rootView.findViewById(R.id.item_bigroom_title);
            details = (TextView) rootView.findViewById(R.id.item_bigroom_details);
        }
    }
}
