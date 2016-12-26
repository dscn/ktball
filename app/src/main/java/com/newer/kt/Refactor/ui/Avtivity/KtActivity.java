package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.ktfootball.www.dao.Users;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.R;
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.myClass.MyAlertDialog;
import com.newer.kt.myClass.MyCircleImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class KtActivity extends BaseActivity {

    //    public static final String USER_AVATAR_ID = "user_avatar_id";
    public static final String IMAGEVIEW_CODE = "imageview_code";
    public static final String USER_TEAM = "user_team";
    public static final String USER_IMAGE_A = "user_image_a";
    public static final String USER_IMAGE_A_URL = "user_image_a_url";
    public static final String USER_IMAGE_B = "user_image_b";
    public static final String USER_IMAGE_B_URL = "user_image_b_url";
    public static final String USER_IMAGE_C = "user_image_c";
    public static final String USER_IMAGE_C_URL = "user_image_c_url";
    public static final String USER_IMAGE_D = "user_image_d";
    public static final String USER_IMAGE_D_URL= "user_image_d_url";
    public static final String USER_IMAGE_E = "user_image_e";
    public static final String USER_IMAGE_E_URL = "user_image_e_url";
    public static final String USER_IMAGE_F = "user_image_f";
    public static final String USER_IMAGE_F_URL = "user_image_f_url";

    @Bind(R.id.game_avatar1_imageView)
    MyCircleImageView imageViewAvatarA;
    @Bind(R.id.game_avatar2_imageView)
    MyCircleImageView imageViewAvatarB;
    @Bind(R.id.game_avatar3_imageView)
    MyCircleImageView imageViewAvatarC;
    @Bind(R.id.game_avatar4_imageView)
    MyCircleImageView imageViewAvatarD;
    @Bind(R.id.game_avatar5_imageView)
    MyCircleImageView imageViewAvatarE;
    @Bind(R.id.game_avatar6_imageView)
    MyCircleImageView imageViewAvatarF;

    @Bind(R.id.textView40)
    TextView textViewA;
    @Bind(R.id.textView41)
    TextView textViewB;
    @Bind(R.id.textView42)
    TextView textViewC;
    @Bind(R.id.textView43)
    TextView textViewD;
    @Bind(R.id.textView44)
    TextView textViewE;
    @Bind(R.id.textView45)
    TextView textViewF;

    @Bind(R.id.imageView2)
    MyCircleImageView imageViewA;
    @Bind(R.id.imageView81)
    MyCircleImageView imageViewB;
    @Bind(R.id.imageView82)
    MyCircleImageView imageViewC;
    @Bind(R.id.imageView85)
    MyCircleImageView imageViewD;
    @Bind(R.id.imageView84)
    MyCircleImageView imageViewE;
    @Bind(R.id.imageView83)
    MyCircleImageView imageViewF;

    int code;
    int team;

    /**
     * 存储用户的 Map
     */
    private Map<Integer, String> userMap;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_kt);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    //初始化视图
    private void initView() {
        userMap = new HashMap<>();
        Intent intent = getIntent();
        code = intent.getIntExtra(GameSelectorActivity.KT_CODE, 0);

        imageViewAvatarA.setVisibility(View.VISIBLE);
        imageViewAvatarB.setVisibility(View.VISIBLE);
        imageViewAvatarC.setVisibility(View.VISIBLE);
        imageViewAvatarD.setVisibility(View.VISIBLE);
        imageViewAvatarE.setVisibility(View.VISIBLE);
        imageViewAvatarF.setVisibility(View.VISIBLE);

        switch (code) {
            case 1:
                imageViewAvatarB.setVisibility(View.GONE);
                imageViewAvatarC.setVisibility(View.GONE);
                imageViewAvatarD.setVisibility(View.GONE);
                imageViewAvatarE.setVisibility(View.GONE);
                team = 1;//1v1

                userMap.put(1, "");
                userMap.put(6, "");

                break;
            case 2:
                imageViewAvatarC.setVisibility(View.GONE);
                imageViewAvatarD.setVisibility(View.GONE);
                team = 2;//2v2

                userMap.put(1, "");
                userMap.put(2, "");
                userMap.put(5, "");
                userMap.put(6, "");

                break;
            case 3:
                team = 3;//3v3

                userMap.put(1, "");
                userMap.put(2, "");
                userMap.put(3, "");
                userMap.put(4, "");
                userMap.put(5, "");
                userMap.put(6, "");

                break;
        }
    }

    //扫描用户二维码
    public void doScan(View view) {

        Intent intent = new Intent(this, CaptureActivity.class);
        intent.putExtra(CaptureActivity.CAPTURE_CODE, 2);

        //判断点击的是哪张图片
        switch (view.getId()) {
            case R.id.game_avatar1_imageView:
                intent.putExtra(IMAGEVIEW_CODE, 1);
                break;
            case R.id.game_avatar2_imageView:
                intent.putExtra(IMAGEVIEW_CODE, 2);
                break;
            case R.id.game_avatar3_imageView:
                intent.putExtra(IMAGEVIEW_CODE, 3);
                break;
            case R.id.game_avatar4_imageView:
                intent.putExtra(IMAGEVIEW_CODE, 4);
                break;
            case R.id.game_avatar5_imageView:
                intent.putExtra(IMAGEVIEW_CODE, 5);
                break;
            case R.id.game_avatar6_imageView:
                intent.putExtra(IMAGEVIEW_CODE, 6);
                break;
        }
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == 2) {
                //取值
                Bundle bundle = data.getBundleExtra("bundle");
                String userScanResult = bundle.getString(CaptureActivity.USER_SCAN_RESULT);
                int imageViewCode = bundle.getInt(CaptureActivity.TWO_IMAGEVIEW_CODE);
                if (userScanResult.equals("null")) {
                    new MyAlertDialog(this).doAlertDialog("该用户非本校用户");
                } else {

                    if (team == 1) {
                        LogUtils.e(userMap.get(1) + "......"+ userScanResult);
                        if (userMap.get(1).equals(userScanResult)
                                || userMap.get(6).equals(userScanResult)) {
                            new MyAlertDialog(this).doAlertDialog("用户已存在");
                            return;
                        }

                    } else if (team == 2) {

                        if (userMap.get(1).equals(userScanResult)
                                || userMap.get(2).equals(userScanResult)
                                || userMap.get(5).equals(userScanResult)
                                || userMap.get(6).equals(userScanResult)) {
                            new MyAlertDialog(this).doAlertDialog("用户已存在");
                            return;
                        }

                    } else if (team == 3) {
                        if (userMap.get(1).equals(userScanResult)
                                || userMap.get(2).equals(userScanResult)
                                || userMap.get(3).equals(userScanResult)
                                || userMap.get(4).equals(userScanResult)
                                || userMap.get(5).equals(userScanResult)
                                || userMap.get(6).equals(userScanResult)) {
                            new MyAlertDialog(this).doAlertDialog("用户已存在");
                            return;
                        }
                    }

                    userMap.put(imageViewCode, userScanResult);
                    LogUtils.e("imageViewCode = " + imageViewCode +"...."+userScanResult);
                    boolean check = true;

                    if (team == 1) {

                        if (userMap.get(1).equals("")) check = false;
                        if (userMap.get(6).equals("")) check = false;

                    } else if (team == 2) {

                        if (userMap.get(1).equals("")) check = false;
                        if (userMap.get(2).equals("")) check = false;
                        if (userMap.get(5).equals("")) check = false;
                        if (userMap.get(6).equals("")) check = false;

                    } else if (team == 3) {

                        if (userMap.get(1).equals("")) check = false;
                        if (userMap.get(2).equals("")) check = false;
                        if (userMap.get(3).equals("")) check = false;
                        if (userMap.get(4).equals("")) check = false;
                        if (userMap.get(5).equals("")) check = false;
                        if (userMap.get(6).equals("")) check = false;

                    }

                    if (check) {
                        //TODO 跳转活动
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(KtActivity.this, VSActivity.class);
                                intent.putExtra(USER_TEAM, team);
                                intent.putExtra(USER_IMAGE_A, textViewA.getText());
                                intent.putExtra(USER_IMAGE_A_URL, (String) textViewA.getTag());
                                intent.putExtra(USER_IMAGE_F, textViewF.getText());
                                intent.putExtra(USER_IMAGE_F_URL, (String)textViewF.getTag());
                                switch (team) {
                                    case 2:
                                        intent.putExtra(USER_IMAGE_B, textViewB.getText());
                                        intent.putExtra(USER_IMAGE_B_URL, (String)textViewB.getTag());
                                        intent.putExtra(USER_IMAGE_E, textViewE.getText());
                                        intent.putExtra(USER_IMAGE_E_URL, (String)textViewE.getTag());
                                        break;
                                    case 3:
                                        intent.putExtra(USER_IMAGE_C, textViewC.getText());
                                        intent.putExtra(USER_IMAGE_C_URL, (String)textViewC.getTag());
                                        intent.putExtra(USER_IMAGE_D, textViewD.getText());
                                        intent.putExtra(USER_IMAGE_D_URL, (String)textViewD.getTag());
                                        break;
                                }
                                startActivity(intent);
                                finish();
                            }
                        }, 800);
                    }

                    List<Users> list = UsersDaoHelper.getInstance().queryByUserId(userScanResult);
                    for (Users user : list) {
                        String avatar = user.getAvatar();
                        String nickname = user.getNickname();
                        switch (imageViewCode) {
                            case 1:
                                String path1 = Constants.Head_HOST + avatar;
                                BitmapManager.getInstance().displayUserLogo(imageViewA, path1);
                                textViewA.setVisibility(View.VISIBLE);
                                textViewA.setText(nickname);
                                textViewA.setTag(path1);
                                break;
                            case 2:
                                String path2 = Constants.Head_HOST + avatar;
                                BitmapManager.getInstance().displayUserLogo(imageViewB, path2);
                                textViewB.setVisibility(View.VISIBLE);
                                textViewB.setText(nickname);
                                textViewB.setTag(path2);
                                break;
                            case 3:
                                String path3 = Constants.Head_HOST + avatar;
                                BitmapManager.getInstance().displayUserLogo(imageViewC, path3);
                                textViewC.setVisibility(View.VISIBLE);
                                textViewC.setText(nickname);
                                textViewC.setTag(path3);
                                break;
                            case 4:
                                String path4 = Constants.Head_HOST + avatar;
                                BitmapManager.getInstance().displayUserLogo(imageViewD, path4);
                                textViewD.setVisibility(View.VISIBLE);
                                textViewD.setText(nickname);
                                textViewD.setTag(path4);
                                break;
                            case 5:
                                String path5 = Constants.Head_HOST + avatar;
                                BitmapManager.getInstance().displayUserLogo(imageViewE, path5);
                                textViewE.setVisibility(View.VISIBLE);
                                textViewE.setText(nickname);
                                textViewE.setTag(path5);
                                break;
                            case 6:
                                String path6 = Constants.Head_HOST + avatar;
                                BitmapManager.getInstance().displayUserLogo(imageViewF, path6);
                                textViewF.setVisibility(View.VISIBLE);
                                textViewF.setText(nickname);
                                textViewF.setTag(path6);
                                break;
                        }
                    }
                }
            }
        }
    }

    public void doBack(View view) {
        finish();
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 1; i <= userMap.size(); i++) {
            userMap.remove(i);
        }
        userMap.clear();
    }
}
