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
public class CombinationClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String classString;
    private List<SwipedState> mItemSwipedStates;
    private final int VIEWPAGER = 11;
    private final int CUSTOMVIEWPAGER = 12;
    private List<Combinations> combinationses;
    private BaseActivity activity ;

    private BaseActivity getContext(){
        return activity;
    }

    private enum SwipedState {
        SHOWING_PRIMARY_CONTENT,
        SHOWING_SECONDARY_CONTENT
    }

    public CombinationClassAdapter(BaseActivity activity, List<Combinations> combinationses, String classString) {
        this.classString = classString;
        this.combinationses = combinationses;
        this.activity = activity;
        mItemSwipedStates = new ArrayList<>();
        for (int i = 0; i < combinationses.size(); i++) {
            mItemSwipedStates.add(i, SwipedState.SHOWING_PRIMARY_CONTENT);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String data = SharedPreferencesUtils.getString(getContext(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, combinationses.get(position).id, "");
        if (!"".equals(data)) {
            LogUtils.e("VIEWPAGER");
            return VIEWPAGER;
        }else{
            LogUtils.e("CUSTOMVIEWPAGER");
            return CUSTOMVIEWPAGER;
        }
    }

    @Override
    public int getItemCount() {
        return combinationses.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        CombinationClassViewhoder combinationClassViewhoder = new CombinationClassViewhoder(getRecyclerView(), layoutInflater(getContext(), R.layout.item_combinationclass, parent, false), this);

        if(viewType == VIEWPAGER){
            ViewPager v = (ViewPager) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_combinationclass, parent, false);
            ViewPagerAdapter adapter = new ViewPagerAdapter();

            ((ViewPager) v.findViewById(R.id.viewPager)).setAdapter(adapter);

            //Perhaps the first most crucial part. The ViewPager loses its width information when it is put
            //inside a RecyclerView. It needs to be explicitly resized, in this case to the width of the
            //screen. The height must be provided as a fixed value.
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            v.getLayoutParams().width = displayMetrics.widthPixels;
            v.requestLayout();

            CombinationClassViewhoder combinationClassViewhoder = new CombinationClassViewhoder( v);
            return combinationClassViewhoder;
        }else{
            CustomViewPager v = (CustomViewPager) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_combinationclass2, parent, false);
            ViewPagerAdapter adapter = new ViewPagerAdapter();

            ((CustomViewPager) v.findViewById(R.id.viewPager)).setAdapter(adapter);

            //Perhaps the first most crucial part. The ViewPager loses its width information when it is put
            //inside a RecyclerView. It needs to be explicitly resized, in this case to the width of the
            //screen. The height must be provided as a fixed value.
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            v.getLayoutParams().width = displayMetrics.widthPixels;
            v.requestLayout();

            CombinationClassViewhoder2 combinationClassViewhoder2 = new CombinationClassViewhoder2(v);
            return combinationClassViewhoder2;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Combinations model = combinationses.get(position);
        String data = SharedPreferencesUtils.getString(getContext(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, model.id, "");
        if(getItemViewType(position) == VIEWPAGER){
            final CombinationClassViewhoder combinationClassViewhoder = (CombinationClassViewhoder) holder;
            combinationClassViewhoder.name.setText(model.name);
            combinationClassViewhoder.details.setText(model.intro);
            combinationClassViewhoder.time.setText(model.total_minutes + "分钟");
            if (!"".equals(data)) {
                combinationClassViewhoder.img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_go_right));
                combinationClassViewhoder.ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), LessonListAvtivity.class);
                        intent.putExtra(Constants.LESSON_ID, model.id);
                        intent.putExtra(Constants.LESSON_CLASS, classString);
                        getContext().startActivity(intent);
                    }
                });
            } else {
                combinationClassViewhoder.img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_down));
                combinationClassViewhoder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSchoolGymCourseCombinationDetail(combinationClassViewhoder.img, model.id, position);
                    }
                });
                combinationClassViewhoder.ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) getContext()).showDialogToast("请先下载文件");
                    }
                });
            }

            ((ViewPager) combinationClassViewhoder.mView).setCurrentItem(mItemSwipedStates.get(position).ordinal());
            combinationClassViewhoder.delect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("提示");
                    dialog.setMessage("是否清空本课程已下载的全部内容？");
                    dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (FileUtil.deleteFile(new File(FileUtil.getDecompressionDir(getContext()) + model.name))) {
                                ((BaseActivity) getContext()).showDialogToast("删除成功");
                                SharedPreferencesUtils.saveString(getContext(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, model.id, "");
                                notifyItemChanged(position);
                            } else {
                                ((BaseActivity) getContext()).showDialogToast("所删除的文件不存在");
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.create().show();
                }
            });
            ((ViewPager) combinationClassViewhoder.mView).setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                int previousPagePosition = 0;

                @Override
                public void onPageScrolled(int pagePosition, float positionOffset, int positionOffsetPixels) {
                    if (pagePosition == previousPagePosition)
                        return;

                    switch (pagePosition) {
                        case 0:
                            mItemSwipedStates.set(position, SwipedState.SHOWING_PRIMARY_CONTENT);
                            break;
                        case 1:
                            mItemSwipedStates.set(position, SwipedState.SHOWING_SECONDARY_CONTENT);
                            break;

                    }
                    previousPagePosition = pagePosition;

                    Log.i("MyAdapter", "PagePosition " + position + " set to " + mItemSwipedStates.get(position).ordinal());
                }

                @Override
                public void onPageSelected(int pagePosition) {
                    //This method keep incorrectly firing as the RecyclerView scrolls.
                    //Use the one above instead
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }else{
            final CombinationClassViewhoder2 combinationClassViewhoder = (CombinationClassViewhoder2) holder;
            combinationClassViewhoder.name.setText(model.name);
            combinationClassViewhoder.details.setText(model.intro);
            combinationClassViewhoder.time.setText(model.total_minutes + "分钟");
            if (!"".equals(data)) {
                combinationClassViewhoder.img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_go_right));
                combinationClassViewhoder.ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), LessonListAvtivity.class);
                        intent.putExtra(Constants.LESSON_ID, model.id);
                        intent.putExtra(Constants.LESSON_CLASS, classString);
                        getContext().startActivity(intent);
                    }
                });
            } else {
                combinationClassViewhoder.img.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_down));
                combinationClassViewhoder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSchoolGymCourseCombinationDetail(combinationClassViewhoder.img, model.id, position);
                    }
                });
                combinationClassViewhoder.ll_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) getContext()).showDialogToast("请先下载文件");
                    }
                });
            }

            combinationClassViewhoder.delect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("提示");
                    dialog.setMessage("是否清空本课程已下载的全部内容？");
                    dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (FileUtil.deleteFile(new File(FileUtil.getDecompressionDir(getContext()) + model.name))) {
                                ((BaseActivity) getContext()).showDialogToast("删除成功");
                                SharedPreferencesUtils.saveString(getContext(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, model.id, "");
                                notifyItemChanged(position);
                            } else {
                                ((BaseActivity) getContext()).showDialogToast("所删除的文件不存在");
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.create().show();
                }
            });

            ((CustomViewPager) combinationClassViewhoder.mView).setCurrentItem(mItemSwipedStates.get(position).ordinal());

            ((CustomViewPager) combinationClassViewhoder.mView).setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                int previousPagePosition = 0;

                @Override
                public void onPageScrolled(int pagePosition, float positionOffset, int positionOffsetPixels) {
                    if (pagePosition == previousPagePosition)
                        return;

                    switch (pagePosition) {
                        case 0:
                            mItemSwipedStates.set(position, SwipedState.SHOWING_PRIMARY_CONTENT);
                            break;
                        case 1:
                            mItemSwipedStates.set(position, SwipedState.SHOWING_SECONDARY_CONTENT);
                            break;

                    }
                    previousPagePosition = pagePosition;

                    Log.i("MyAdapter", "PagePosition " + position + " set to " + mItemSwipedStates.get(position).ordinal());
                }

                @Override
                public void onPageSelected(int pagePosition) {
                    //This method keep incorrectly firing as the RecyclerView scrolls.
                    //Use the one above instead
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }

    }

    public class CombinationClassViewhoder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView time;
        public TextView details;
        public ImageView img;
        public Button delect;
        public ViewPager mView;
        public RelativeLayout ll_1;

        public CombinationClassViewhoder(View rootView) {
            super(rootView);
            mView = (ViewPager) itemView;
            name = (TextView) rootView.findViewById(R.id.item_combinationclass_name);
            ll_1 = (RelativeLayout) rootView.findViewById(R.id.primaryContentCardView);
            time = (TextView) rootView.findViewById(R.id.item_combinationclass_time);
            details = (TextView) rootView.findViewById(R.id.item_combinationclass_details);
            img = (ImageView) rootView.findViewById(R.id.item_combinationclass_img);
            delect = (Button) itemView.findViewById(R.id.btn1);
        }
    }

    public class CombinationClassViewhoder2 extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView time;
        public TextView details;
        public ImageView img;
        public Button delect;
        public CustomViewPager mView;
        public RelativeLayout ll_1;
        public RelativeLayout ll_2;

        public CombinationClassViewhoder2(View rootView) {
            super(rootView);
            mView = (CustomViewPager) itemView;
            name = (TextView) rootView.findViewById(R.id.item_combinationclass_name);
            ll_1 = (RelativeLayout) rootView.findViewById(R.id.primaryContentCardView);
            time = (TextView) rootView.findViewById(R.id.item_combinationclass_time);
            ll_2 = (RelativeLayout) rootView.findViewById(R.id.secondaryContentFrameLayout);
            details = (TextView) rootView.findViewById(R.id.item_combinationclass_details);
            img = (ImageView) rootView.findViewById(R.id.item_combinationclass_img);
            delect = (Button) itemView.findViewById(R.id.btn1);
        }
    }

    /**
     * 大课间
     *
     * @param club_id 俱乐部ID
     */
    private void getSchoolGymCourseCombinationDetail(final ImageView img, final String club_id, final int position) {
        SchoolGymCourseCombinationDetailRequest request = new SchoolGymCourseCombinationDetailRequest(Constants.GET_SCHOOL_GYM_COURSE_COMBINATION_DETAIL, RequestMethod.GET);
        request.add("gym_course_combination", club_id);
        CallServer.getRequestInstance().add((BaseActivity) getContext(), 0, request, new HttpListener<SchoolGymCourseCombinationDetail>() {
            @Override
            public void onSucceed(int what, Response<SchoolGymCourseCombinationDetail> response) {
                if (response != null && response.get() != null && response.get().response.equals("success")) {
                    SharedPreferencesUtils.saveString(getContext(), Constants.SCHOOLGYMCOURSECOMBINATIONDETAIL, club_id, GsonTools.createGsonString(response.get()));
                    new ListDownManager(getContext(), response.get(), club_id, CombinationClassAdapter.this, position);
                } else {

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false);
    }

    public void callBack(int position) {
//        notifyDataSetChanged();
        notifyItemChanged(position);
    }
}
