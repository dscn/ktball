package com.newer.kt.Refactor.ui.Avtivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.Business.BitmapManager;
import com.newer.kt.Business.ServiceLoadBusiness;
import com.newer.kt.R;
import com.newer.kt.Refactor.Constants;
import com.newer.kt.Refactor.Entitiy.UserInfo;
import com.newer.kt.Refactor.utils.MD5;
import com.newer.kt.Refactor.view.CircleImageView;
import com.newer.kt.Refactor.view.wheelview.OnWheelScrollListener;
import com.newer.kt.Refactor.view.wheelview.WheelView;
import com.newer.kt.Refactor.view.wheelview.adapter.ArrayWheelAdapter;
import com.newer.kt.Refactor.view.wheelview.adapter.NumericWheelAdapter;
import com.newer.kt.entity.response.CommonResponse;
import com.newer.kt.myClass.MyAlertDialog;
import com.newer.kt.net.callback.RequestCallBack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class UserInfoActivity extends BaseActivity {

    @Bind(R.id.textView105)
    TextView textViewTitle;
    @Bind(R.id.imageView92)
    CircleImageView imageViewAvatar;
    @Bind(R.id.textView117)
    TextView textViewName;
    @Bind(R.id.textView1177)
    TextView textViewSex;
    @Bind(R.id.textView11777)
    TextView textViewBirthday;
    @Bind(R.id.textView117777)
    TextView textViewClass;
    @Bind(R.id.textView1177777)
    TextView textViewPhone;
    @Bind(R.id.relativeLayout888)
    RelativeLayout relativeLayout;

    Intent intent;

    public static final int PAIZHAO_REQUEST_CODE = 1;
    public static final int XIANGCE_REQUEST_CODE = 2;
    public static final int ALBUM_REQUEST_CODE = 3;
    public static final int UPDATA_CODE = 12;

    String avatarData;
    String name;
    String gender;
    String birthday;
    String school_class_id;
    String phone;
    long clubId;
    ServiceLoadBusiness sb;

    String userId;
    String[] sexs = {"男", "女"};
    String[] banji = {"", "1班", "2班", "3班", "4班", "5班"};
    int userCode;

    //    1-3小班 中班 大班   4-9 一年级-六年级   10-12  初一-初三
    String[] nianJi = {"", "小班", "中班", "大班", "一年级", "二年级", "三年级", "四年级",
            "五年级", "六年级", "初一", "初二", "初三"};
    private int fromType;

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case ServiceLoadBusiness.GET_USER_INFO_SUCCESS:
                closeLoadingDialog();
                UserInfo userInfo = (UserInfo) msg.obj;
                initUserInfoData(userInfo);
                break;
            case ServiceLoadBusiness.GET_USER_INFO_FAILURE:
                closeLoadingDialog();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle((String) msg.obj);
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }

    /**
     * 初始化用户信息数据
     *
     * @param userInfo
     */
    private void initUserInfoData(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        if (userCode == 1) {
            if (!userInfo.cls.isEmpty() && !userInfo.grade.isEmpty()) {
                textViewClass.setText(nianJi[Integer.parseInt(userInfo.grade)] + userInfo.cls + "班");
                school_class_id = userInfo.cls;
            } else {
                textViewClass.setText("暂无所属班级");
            }
        } else if (userCode == 2) {
            int grade = intent.getIntExtra("grade", 0);
            int cls = intent.getIntExtra("cls", 0);
            textViewClass.setText(nianJi[grade] + cls + "班");
            school_class_id = String.valueOf(cls);
        }
        String uri = Constants.Head_HOST + userInfo.avatar;
//        imageFile = BitmapManager.getInstance().getPhotoCacheDir(getThis(),uri);
        BitmapManager.getInstance().displayUserLogo(imageViewAvatar,
                uri);
        textViewName.setText(userInfo.nickname);
        name = userInfo.nickname;
        textViewSex.setText(userInfo.gender.equals("MM") ? "女" : "男");
        gender = userInfo.gender;
        textViewPhone.setText(userInfo.phone);
        phone = userInfo.phone;
        textViewBirthday.setText(userInfo.birthday);
        birthday = userInfo.birthday;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        sb = ServiceLoadBusiness.getInstance();
        intent = getIntent();
        userId = intent.getStringExtra(MyClassActivity.EXTRA_USER_ID);
        fromType = intent.getIntExtra(Constants.FORM_STUDENTLIST_STR, 0);
        clubId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_CLUB_ID, 0);
        userCode = intent.getIntExtra(ListActivity.EXTRA_USER_CODE, 0);
        initView();//学生列表扫描过来的
    }

    private void initView() {
        Map<String, String> param = new HashMap<>();
        param.put("user_id", userId);
        showLoadingDiaglog();
        sb.getUserInfo(getThis(), getTAG(), param);
    }

    public void doFinsh(View view) {//退出
        finish();
    }

    public void doPhoto(View view) {//拍照or从照片中选择
        relativeLayout.setVisibility(View.VISIBLE);
    }

    //拍照
    private static File imageFile = new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");

    private void takePhotoFromCamara() {
        try {
            imageFile = new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");
            if (!imageFile.exists()) imageFile.mkdirs();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(intent, PAIZHAO_REQUEST_CODE);
        } catch (Exception e) {
            showToast("请赋予应用使用照相机权限");
        }

    }

    //从相册中选照片
    private Uri imageUri;

    private void chooseFromMedia() {

        imageUri = Uri.fromFile(imageFile);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, ALBUM_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == PAIZHAO_REQUEST_CODE) {
            intentToCrop(null);
        } else if (requestCode == ALBUM_REQUEST_CODE) {
            intentToCrop(data.getData());
        } else if (requestCode == XIANGCE_REQUEST_CODE) {
            if (data == null) return;
            Bitmap photo = data.getParcelableExtra("data");
            imageViewAvatar.setImageBitmap(photo);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/KT足球/NewGameAvatar.png");
            if (f.exists()) {
                f.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(f);
                photo.compress(Bitmap.CompressFormat.PNG, 90, out);
                byte[] bb = File2byte(f);
                String str = "";
                StringBuffer sb = new StringBuffer(str);
                for (byte b : bb) {
                    sb.append(String.valueOf(b));
                }
                avatarData = sb.toString();
                Log.d("avatarDataavatarData", avatarData.toString());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void intentToCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri == null ? Uri.fromFile(imageFile) : uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 280);
        intent.putExtra("outputY", 280);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, XIANGCE_REQUEST_CODE);
    }

    public byte[] File2byte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public void doTakePhoto(View view) {//拍照
        takePhotoFromCamara();
        relativeLayout.setVisibility(View.GONE);
    }

    public void doSeletePhoto(View view) {//从相册中选择
        chooseFromMedia();
        relativeLayout.setVisibility(View.GONE);
    }

    public void doCanal(View view) {//取消
        relativeLayout.setVisibility(View.GONE);
    }

    public void doName(View view) {//编辑姓名
        View v = getLayoutInflater().inflate(R.layout.create_dialog, null);
        final EditText editText = (EditText) v.findViewById(R.id.editText_create);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = editText.getText().toString();
                        textViewName.setText(name);
                    }
                })
                .setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void doSex(View view) {//性别
        new SexDialog(this).show();
    }

    public void doBirthday(View view) {//生日
        new BirthDialog(this).show();
    }

    public void doCommit(View view) {//修改学生信息
        try {
            saveBitmap(drawableToBitmap(imageViewAvatar.getDrawable()), "/KT足球/NewGameAvatar.png");
        } catch (Exception e) {
            return;
        }
        doChangeUserinfo(new RequestCallBack<CommonResponse>() {
            @Override
            public void onSuccess(CommonResponse resultBean, int code, String msg) {
                new MyAlertDialog(getThis()).doAlertDialog("ok");
                AlertDialog.Builder builder = new AlertDialog.Builder(getThis());
                builder.setTitle("更新成功");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(UPDATA_CODE);
                        finish();
                    }
                });
//                    builder.setNegativeButton("取消",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }

    public void saveBitmap(Bitmap bm, String name) {
        Log.e(getTAG(), "保存图片");
        File f = new File(Environment.getExternalStorageDirectory(), name);
//        File f = new File(Environment.getExternalStorageDirectory(), "/KT足球/NewGameAvatar.png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.i(getTAG(), "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void doChangeUserinfo(RequestCallBack<CommonResponse> requestCallBack) {
        showLoadingDiaglog();
//        AccountManager.getInstance().editUserInfo(String.valueOf(userId), String.valueOf(clubId), school_class_id, gender, birthday, phone, name, imageFile, requestCallBack);
//        AccountManager.getInstance().editUserInfo(String.valueOf(userId), String.valueOf(clubId), school_class_id, gender, birthday, phone, name, imageFile, new RequestCallBack<CommonResponse>() {
//            @Override
//            public void onSuccess(CommonResponse resultBean, int code, String msg) {
//                Toast.makeText(UserInfoActivity.this, "信息更改成功，请更新本校数据", Toast.LENGTH_LONG).show();
//                closeLoadingDialog();
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                }, 500);
//            }
//        });

        Map<String, String> map = new HashMap<>();
        map.put("user_id", String.valueOf(userId));
        map.put("club_id", String.valueOf(clubId));
        map.put("school_class_id", school_class_id);
        map.put("gender", gender);
        map.put("birthday", birthday);
        map.put("phone", phone);
        map.put("nickname", name);
        map.put("school_club_id",String.valueOf(userId));
//        map.put("avatar", avatar);

        ServiceLoadBusiness.getInstance().updateUserInfo(getThis(), getTAG(), map, imageFile);
    }

    public void doGrader(View view) {//班级年级
        new GradeDialog(this).show();
    }

    public void doPhone(View view) {//联系方式
        View v = getLayoutInflater().inflate(R.layout.edit_phone, null);
        final EditText editText = (EditText) v.findViewById(R.id.editText_create);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phone = editText.getText().toString();
                        textViewPhone.setText(phone);
                    }
                })
                .setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void doDelete(View view) {//删除玩家
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("确定删除该学生?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (fromType) {
                    case Constants.FORM_STUDENTLIST:
                        clubId = 0;
                        break;
                    case Constants.FORM_CLASS:
                        break;
                }
                school_class_id = "";
                doChangeUserinfo(new RequestCallBack<CommonResponse>() {
                    @Override
                    public void onSuccess(CommonResponse resultBean, int code, String msg) {
                        Toast.makeText(UserInfoActivity.this, "信息更改成功，请更新本校数据", Toast.LENGTH_LONG).show();
                        closeLoadingDialog();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                setResult(UPDATA_CODE);
                                finish();
                            }
                        }, 500);
                    }
                });
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();
            }
        });
        builder.create().show();
    }

    //选择性别对话框
    class SexDialog
            extends Dialog
            implements View.OnClickListener {

        public SexDialog(Context context) {
            super(context);
        }

        private WheelView sex;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(UserInfoActivity.this, R.layout.dialog_wheel, null);

            sex = (WheelView) view.findViewById(R.id.year);

            view.findViewById(R.id.month).setVisibility(View.GONE);
            view.findViewById(R.id.day).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.selectDialog_txv_title)).setText("选择性别");

            ArrayWheelAdapter<String> s = new ArrayWheelAdapter<>(UserInfoActivity.this, sexs);

            sex.setViewAdapter(s);

            sex.setCyclic(false);
            sex.setVisibleItems(sexs.length);
            sex.setCurrentItem(0);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectDialog_txv_confirm:
                    int i = sex.getCurrentItem();
                    if (i == 0) {
                        textViewSex.setText("男");
                        gender = "GG";
                    } else {
                        textViewSex.setText("女");
                        gender = "MM";
                    }
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }

    //生日
    class BirthDialog
            extends Dialog
            implements OnWheelScrollListener, View.OnClickListener {

        private WheelView year;
        private WheelView month;
        private WheelView day;

        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            NumericWheelAdapter temp = new NumericWheelAdapter(UserInfoActivity.this, 1, getDay(year.getCurrentItem() + 1950, month.getCurrentItem() + 1));
            temp.setLabel("日");
            day.setViewAdapter(temp);
        }

        public BirthDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(UserInfoActivity.this, R.layout.dialog_wheel, null);

            year = (WheelView) view.findViewById(R.id.year);
            month = (WheelView) view.findViewById(R.id.month);
            day = (WheelView) view.findViewById(R.id.day);

            NumericWheelAdapter numericWheelAdapter1 =
                    new NumericWheelAdapter(UserInfoActivity.this, 1950, Calendar.getInstance().get(Calendar.YEAR) + 10);
            numericWheelAdapter1.setLabel("年");
            year.setViewAdapter(numericWheelAdapter1);
            year.setCyclic(true);
            year.setVisibleItems(7);
            year.setCurrentItem(Calendar.getInstance().get(Calendar.YEAR) - 1950);
            year.addScrollingListener(this);

            NumericWheelAdapter numericWheelAdapter2 =
                    new NumericWheelAdapter(UserInfoActivity.this, 1, 12);
            numericWheelAdapter2.setLabel("月");
            month.setViewAdapter(numericWheelAdapter2);
            month.setCyclic(true);
            month.setCurrentItem(Calendar.getInstance().get(Calendar.MONTH));
            month.setVisibleItems(7);
            month.addScrollingListener(this);

            NumericWheelAdapter numericWheelAdapter3 =
                    new NumericWheelAdapter(UserInfoActivity.this, 1, getDay(1996, 1));
            numericWheelAdapter3.setLabel("日");
            day.setViewAdapter(numericWheelAdapter3);
            day.setCyclic(true);
            day.setCurrentItem(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1);
            day.setVisibleItems(7);
            day.addScrollingListener(this);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        private int getDay(int year, int month) {
            int day = 30;
            boolean flag = false;
            switch (year % 4) {
                case 0:
                    flag = true;
                    break;
                default:
                    flag = false;
                    break;
            }
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    day = 31;
                    break;
                case 2:
                    day = flag ? 29 : 28;
                    break;
                default:
                    day = 30;
                    break;
            }
            return day;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectDialog_txv_confirm:
                    int y = year.getCurrentItem() + 1950;
                    int m = month.getCurrentItem() + 1;
                    int d = day.getCurrentItem() + 1;
                    String data = y + "-" + m + "-" + d;
                    textViewBirthday.setText(data);
                    birthday = data;
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }

    class GradeDialog
            extends Dialog
            implements View.OnClickListener {

        public GradeDialog(Context context) {
            super(context);
        }

        private WheelView myGrader;
        private WheelView myCls;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            super.onCreate(savedInstanceState);

            View view = View.inflate(UserInfoActivity.this, R.layout.dialog_wheel, null);

            myGrader = (WheelView) view.findViewById(R.id.year);
            myCls = (WheelView) view.findViewById(R.id.month);

            view.findViewById(R.id.day).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.selectDialog_txv_title)).setText("选择班级");

            ArrayWheelAdapter<String> s1 = new ArrayWheelAdapter<>(UserInfoActivity.this, nianJi);
            ArrayWheelAdapter<String> s2 = new ArrayWheelAdapter<>(UserInfoActivity.this, banji);

            myGrader.setViewAdapter(s1);
            myCls.setViewAdapter(s2);

            myGrader.setCyclic(false);
            myGrader.setVisibleItems(nianJi.length);
            myGrader.setCurrentItem(1);

            myCls.setCyclic(false);
            myCls.setVisibleItems(banji.length);
            myCls.setCurrentItem(1);

            view.findViewById(R.id.selectDialog_txv_cacel).setOnClickListener(this);
            view.findViewById(R.id.selectDialog_txv_confirm).setOnClickListener(this);

            setContentView(view);

            setCanceledOnTouchOutside(false);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectDialog_txv_confirm:
                    int i = myGrader.getCurrentItem();
                    int j = myCls.getCurrentItem();
                    textViewClass.setText(nianJi[i] + banji[j]);
                    school_class_id = String.valueOf(j);
                case R.id.selectDialog_txv_cacel:
                    dismiss();
                    break;
            }
        }
    }
}
