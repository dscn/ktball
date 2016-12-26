package com.newer.kt.Refactor.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.newer.kt.R;
import com.newer.kt.adapter.ClassListAdapter;
import com.newer.kt.entity.GradeList;

import java.util.List;

/**
 * Created by jy on 16/6/21.
 */
public class ListDialog extends Dialog {

    private RecyclerView rc;
    private List<GradeList> mFileList;
    private ClassListAdapter downListAdapter;

    public ListDialog(Context context, int themeResId, List<GradeList> mFileList) {
        super(context, themeResId);
        this.mFileList = mFileList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_classlist);
        init();
    }

    private void init() {
        rc = (RecyclerView) findViewById(R.id.layout_classlist_rv);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        rc.setLayoutManager(ll);
        downListAdapter = new ClassListAdapter(rc,mFileList);
        rc.setAdapter(downListAdapter);
    }
}
