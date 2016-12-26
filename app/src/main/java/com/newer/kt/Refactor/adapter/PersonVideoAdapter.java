package com.newer.kt.Refactor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.newer.kt.R;

import java.util.List;

/**
 * Created by jy on 16/6/22.
 */
public class PersonVideoAdapter extends BaseRecyclerViewAdapter<String, PersonVideoAdapter.PersonVedioViewHolder> {

    public PersonVideoAdapter(RecyclerView recyclerView, List<String> strings) {
        super(recyclerView, strings);
    }

    @Override
    protected void bindView(PersonVedioViewHolder holder, int position, String model) {
        holder.tv.setText(getName(model));
    }

    @Override
    public PersonVedioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PersonVedioViewHolder vedioViewHolder = new PersonVedioViewHolder(getRecyclerView(), LayoutInflater.from(getContext()).inflate(R.layout.item_personvedio, parent, false), this);
        return vedioViewHolder;
    }

    public class PersonVedioViewHolder extends BaseViewHolder {

        private ImageView img;
        private TextView tv;

        public PersonVedioViewHolder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);
            img = (ImageView) rootView.findViewById(R.id.item_personvedio_img);
            tv = (TextView) rootView.findViewById(R.id.item_personvedio_tv);
        }
    }

    private String getName(String url){
        int index = url.indexOf(",");
        return url.substring(index+1);
    }
}
