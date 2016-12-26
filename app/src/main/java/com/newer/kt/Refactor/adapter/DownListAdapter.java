package com.newer.kt.Refactor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.frame.app.view.NumberProgressBar.NumberProgressBar;
import com.newer.kt.R;
import com.newer.kt.Refactor.Entitiy.LoadFile;

import java.util.List;

/**
 * Created by jy on 16/6/21.
 */
public class DownListAdapter extends BaseRecyclerViewAdapter<LoadFile, DownListAdapter.DownListViewHolder> {

    public DownListAdapter(RecyclerView recyclerView, List<LoadFile> loadFiles) {
        super(recyclerView, loadFiles);
    }

    @Override
    protected void bindView(DownListViewHolder holder, int position, LoadFile model) {
        holder.progressBar.setProgress(model.getProgress());
        holder.name.setText(model.getTitle());
    }

    @Override
    public DownListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownListViewHolder(getRecyclerView(), layoutInflater(getContext(), R.layout.layout_listdown, parent, false), this);
    }

    public class DownListViewHolder extends BaseViewHolder {

        private NumberProgressBar progressBar;
        private TextView name;

        public DownListViewHolder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);
            progressBar = (NumberProgressBar) rootView.findViewById(R.id.layout_listdown_progrees);
            name = (TextView) rootView.findViewById(R.id.layout_listdown_name);
        }
    }
}
