package com.newer.kt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.kt.R;
import com.newer.kt.Refactor.view.MyLinearLayoutManager;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by leo on 16/11/10.
 */

public class BigClassGifAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int IS_TITLE = 1;
    private final int IS_CONTENT = 2;
    private int isVisible = -1;
    private Context mContext;
    private List<String> mList;
    private MyLinearLayoutManager linearLayoutManager;
    public BigClassGifAdapter(List<String> mList, Context mContext, MyLinearLayoutManager linearLayoutManager) {
        this.mList = mList;
        this.mContext = mContext;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewType == IS_TITLE ? new BigClassGifAdapter.HeadHolder(LayoutInflater.from(mContext).inflate(R.layout.item_demo_title, parent, false)) :
                new BigClassGifAdapter.ContentViewhoder(LayoutInflater.from(mContext).inflate(R.layout.item_demo_content, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == IS_TITLE) {
            ((BigClassGifAdapter.HeadHolder) holder).tv_title.setText(mList.get(position));
        } else {
            ((BigClassGifAdapter.ContentViewhoder) holder).mContent.setText(mList.get(position));
            GifDrawable gifFromResource = null;
            try {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.anim);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((BigClassGifAdapter.ContentViewhoder) holder).mGifImageView.setImageDrawable(gifFromResource);
            final GifDrawable finalGifFromResource = gifFromResource;
            if (isVisible == position) {
                ((BigClassGifAdapter.ContentViewhoder) holder).mGifImageView.setVisibility(View.VISIBLE);
            } else {
                ((BigClassGifAdapter.ContentViewhoder) holder).mGifImageView.setVisibility(View.GONE);
            }
            ((BigClassGifAdapter.ContentViewhoder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isVisible == position) {
                        isVisible = -1;
                        notifyDataSetChanged();
                        ((BigClassGifAdapter.ContentViewhoder) holder).mGifImageView.setVisibility(View.GONE);
                        if (finalGifFromResource != null)
                            finalGifFromResource.stop();
                    } else {
                        if (finalGifFromResource != null) {
                            isVisible = position;
                            notifyDataSetChanged();
                            if (position >= mList.size() - 2) {
                                linearLayoutManager.setStackFromEnd(true);
                            }
//                                else{
//                                    linearLayoutManager.scrollToPositionWithOffset(position-1, 20);
//
//                                }
                            ((BigClassGifAdapter.ContentViewhoder) holder).mGifImageView.setVisibility(View.VISIBLE);
                            if (finalGifFromResource.isRunning()) {
                                finalGifFromResource.reset();
                            } else {
                                finalGifFromResource.start();

                            }
                        }

                    }

                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).contains(" ")) {
            return IS_TITLE;
        } else {
            return IS_CONTENT;
        }
    }

    public class HeadHolder extends RecyclerView.ViewHolder {
        TextView tv_title;

        public HeadHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public class ContentViewhoder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        ImageView mImage;
        @Bind(R.id.tv_content)
        TextView mContent;
        @Bind(R.id.gif1)
        GifImageView mGifImageView;

        public ContentViewhoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}