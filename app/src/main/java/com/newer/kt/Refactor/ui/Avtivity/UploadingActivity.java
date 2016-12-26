package com.newer.kt.Refactor.ui.Avtivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.www.dao.SideAandB;
import com.ktfootball.www.dao.Users;
import com.newer.kt.R;
import com.newer.kt.Refactor.db.SideAandBDaoHelper;
import com.newer.kt.Refactor.db.UsersDaoHelper;
import com.newer.kt.myClass.GlideCircleTransform;

import java.io.File;
import java.util.List;

import butterknife.Bind;

public class UploadingActivity extends BaseActivity implements View.OnClickListener {

    public static final String LEFT_USER = "left_user";
    public static final String RIGHT_USER = "right_user";
    public static final String LEFT_GRADE = "left_grade";
    public static final String RIGHT_GRADE = "right_grade";
    public static final String KT_CODE = "kt_code";
    @Bind(R.id.game_avatar1_imageView)
    ImageView imageViewA;
    @Bind(R.id.textView40)
    TextView textViewA;//第1个头像和名称
    @Bind(R.id.game_avatar2_imageView)
    ImageView imageViewB;
    @Bind(R.id.textView41)
    TextView textViewB;//第2个头像和名称
    @Bind(R.id.game_avatar3_imageView)
    ImageView imageViewC;
    @Bind(R.id.textView42)
    TextView textViewC;//第3个头像和名称
    @Bind(R.id.game_avatar4_imageView)
    ImageView imageViewD;
    @Bind(R.id.textView43)
    TextView textViewD;//第4个头像和名称
    @Bind(R.id.game_avatar5_imageView)
    ImageView imageViewE;
    @Bind(R.id.textView44)
    TextView textViewE;//第5个头像和名称
    @Bind(R.id.game_avatar6_imageView)
    ImageView imageViewF;
    @Bind(R.id.textView45)
    TextView textViewF;//第6个头像和名称

    @Bind(R.id.imageView57)
    ImageView addLeftBall;//左加
    @Bind(R.id.imageView58)
    ImageView addLeftPass;
    @Bind(R.id.imageView59)
    ImageView addLeftX;
    @Bind(R.id.imageView63)
    ImageView minusLeftBall;//左减
    @Bind(R.id.imageView64)
    ImageView minusLeftPass;
    @Bind(R.id.imageView65)
    ImageView minusLeftX;
    @Bind(R.id.imageView66)
    ImageView addRightBall;//右加
    @Bind(R.id.imageView67)
    ImageView addRightPass;
    @Bind(R.id.imageView68)
    ImageView addRightX;
    @Bind(R.id.imageView72)
    ImageView minusRightBall;//右减
    @Bind(R.id.imageView73)
    ImageView minusRihgtPass;
    @Bind(R.id.imageView74)
    ImageView minusRightX;

    @Bind(R.id.textView54)
    TextView leftBallTextView;//左计分
    @Bind(R.id.textView55)
    TextView leftPassTextView;
    @Bind(R.id.textView56)
    TextView leftXTextView;
    @Bind(R.id.textView57)
    TextView rightBallTextView;//右计分
    @Bind(R.id.textView58)
    TextView rightPassTextView;
    @Bind(R.id.textView59)
    TextView rightXTextView;

    @Bind(R.id.imageView53)
    ImageView leftKtImageView;//左KT键
    @Bind(R.id.imageView54)
    ImageView rightKtImageView;//右KT键

    @Bind(R.id.imageView55)
    ImageView submitImageView;//提交比分
    @Bind(R.id.imageView56)
    ImageView noSubmitImageView;//放弃提交比分

    @Bind(R.id.imageView2)
    ImageView imageViewAA;
    @Bind(R.id.imageView81)
    ImageView imageViewBB;
    @Bind(R.id.imageView82)
    ImageView imageViewCC;
    @Bind(R.id.imageView85)
    ImageView imageViewDD;
    @Bind(R.id.imageView84)
    ImageView imageViewEE;
    @Bind(R.id.imageView83)
    ImageView imageViewFF;

    boolean leftWin = false;
    boolean rightWin = false;

    String path;//SD卡根目录

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_uploading);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        path = Environment.getExternalStorageDirectory().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initViewClik();
    }

    private void initViewClik() {
        addLeftBall.setOnClickListener(this);
        addLeftPass.setOnClickListener(this);
        addLeftX.setOnClickListener(this);
        minusLeftBall.setOnClickListener(this);
        minusLeftPass.setOnClickListener(this);
        minusLeftX.setOnClickListener(this);
        addRightBall.setOnClickListener(this);
        addRightPass.setOnClickListener(this);
        addRightX.setOnClickListener(this);
        minusRightBall.setOnClickListener(this);
        minusRihgtPass.setOnClickListener(this);
        minusRightX.setOnClickListener(this);
        leftKtImageView.setOnClickListener(this);
        rightKtImageView.setOnClickListener(this);
        submitImageView.setOnClickListener(this);
        noSubmitImageView.setOnClickListener(this);
    }

    String leftBall;
    String leftPall;
    String leftX;
    String rightBall;
    String rightPass;
    String rightX;

    int lBall;
    int lPass;
    int lX;
    int rBall;
    int rPass;
    int rX;

    Intent intent;
    int team;
    String VCRpath;

    private void initView() {//初始化视图
        intent = getIntent();
        team = intent.getIntExtra(KtActivity.USER_TEAM, 0);
        int ktCode = intent.getIntExtra(VSActivity.KT_CODE, 0);
        VCRpath = intent.getStringExtra(VSActivity.VCR_PATH);
        switch (ktCode){
            case 1://左KT
                leftKtImageView.setImageResource(R.drawable.score_kt2x);
                leftWin = true;
                break;
            case 2://右KT
                rightKtImageView.setImageResource(R.drawable.score_kt2x);
                rightWin = true;
                break;
            default:
                break;
        }

        leftBall = intent.getStringExtra(VSActivity.LEFT_BALL_GRADE);
        leftPall = intent.getStringExtra(VSActivity.LEFT_PASS_GRADE);
        leftX = intent.getStringExtra(VSActivity.LEFT_X_GRADE);
        rightBall = intent.getStringExtra(VSActivity.RIGHT_BALL_GRADE);
        rightPass = intent.getStringExtra(VSActivity.RIGHT_PASS_GRADE);
        rightX = intent.getStringExtra(VSActivity.RIGHT_X_GRADE);

        lBall = Integer.parseInt(leftBall);
        lPass = Integer.parseInt(leftPall);
        lX = Integer.parseInt(leftX);
        rBall = Integer.parseInt(rightBall);
        rPass = Integer.parseInt(rightPass);
        rX = Integer.parseInt(rightX);

        leftBallTextView.setText(leftBall);
        leftPassTextView.setText(leftPall);
        leftXTextView.setText(leftX);
        rightBallTextView.setText(rightBall);
        rightPassTextView.setText(rightPass);
        rightXTextView.setText(rightX);

        String userIDA = intent.getStringExtra(KtActivity.USER_IMAGE_A);
        String userIDF = intent.getStringExtra(KtActivity.USER_IMAGE_F);
        List<Users> usersList = UsersDaoHelper.getInstance().queryByNickname(userIDA);
        for(Users user : usersList){
            grade_a = Integer.parseInt(user.getGrade());
        }
        textViewA.setText(userIDA);
        textViewF.setText(userIDF);
        String path1 = path + "/KT足球/" + userIDA + ".png";
        Glide.with(this).load(new File(path1)).transform(new GlideCircleTransform(this)).into(imageViewAA);
        String path2 = path + "/KT足球/" + userIDF + ".png";
        Glide.with(this).load(new File(path2)).transform(new GlideCircleTransform(this)).into(imageViewFF);
        switch (team){
            case 1:
                imageViewB.setVisibility(View.GONE);
                imageViewC.setVisibility(View.GONE);
                imageViewD.setVisibility(View.GONE);
                imageViewE.setVisibility(View.GONE);
                break;
            case 2:
                imageViewC.setVisibility(View.GONE);
                imageViewD.setVisibility(View.GONE);
                String userIDB = intent.getStringExtra(KtActivity.USER_IMAGE_B);
                String userIDE = intent.getStringExtra(KtActivity.USER_IMAGE_E);
                textViewB.setText(userIDB);
                textViewE.setText(userIDE);
                String path3 = path + "/KT足球/" + userIDB + ".png";
                Glide.with(this).load(new File(path3)).transform(new GlideCircleTransform(this)).into(imageViewBB);
                String path4 = path + "/KT足球/" + userIDE + ".png";
                Glide.with(this).load(new File(path4)).transform(new GlideCircleTransform(this)).into(imageViewEE);
                break;
            case 3:
                String userIDC = intent.getStringExtra(KtActivity.USER_IMAGE_C);
                String userIDD = intent.getStringExtra(KtActivity.USER_IMAGE_D);
                String userIDB2 = intent.getStringExtra(KtActivity.USER_IMAGE_D);
                String userIDE2 = intent.getStringExtra(KtActivity.USER_IMAGE_D);
                textViewC.setText(userIDC);
                textViewD.setText(userIDD);
                textViewB.setText(userIDB2);
                textViewE.setText(userIDE2);
                String path5 = path + "/KT足球/" + userIDB2 + ".png";
                Glide.with(this).load(new File(path5)).transform(new GlideCircleTransform(this)).into(imageViewBB);
                String path6 = path + "/KT足球/" + userIDE2 + ".png";
                Glide.with(this).load(new File(path6)).transform(new GlideCircleTransform(this)).into(imageViewEE);
                String path7 = path + "/KT足球/" + userIDC + ".png";
                Glide.with(this).load(new File(path7)).transform(new GlideCircleTransform(this)).into(imageViewCC);
                String path8 = path + "/KT足球/" + userIDD + ".png";
                Glide.with(this).load(new File(path8)).transform(new GlideCircleTransform(this)).into(imageViewDD);
                break;
        }
    }

    public Bitmap getRotatedBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.kt);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(45);
        Bitmap mRotateBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);

        return mRotateBitmap;
    }

    private Bitmap doSeleteByName(String name){
         File filePath = Environment.getExternalStorageDirectory();
        String path = filePath.toString() + "/KT足球/" + name + ".png";
        File file = new File(path);
        if (file.exists()) return BitmapFactory.decodeFile(path);
        return null;
    }

    String user_a;
    String user_b;
    int grade_a;
    int grade_b;
    int leftBallAddPass;
    int rightBallAddPass;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView57://左Ball加
                lBall += 1;
                leftBallTextView.setText(String.valueOf(lBall));
                break;
            case R.id.imageView58://左Pass加
                lPass += 1;
                leftPassTextView.setText(String.valueOf(lPass));
                break;
            case R.id.imageView59://左X加
                lX += 1;
                leftXTextView.setText(String.valueOf(lX));
                break;
            case R.id.imageView63://左Ball减
                if (lBall > 0){
                    lBall -= 1;
                    leftBallTextView.setText(String.valueOf(lBall));
                }
                break;
            case R.id.imageView64://左Pall减
                if (lPass > 0){
                    lPass -= 1;
                    leftPassTextView.setText(String.valueOf(lPass));
                }
                break;
            case R.id.imageView65://左X减
                if (lX > 0){
                    lX -= 1;
                    leftXTextView.setText(String.valueOf(lX));
                }
                break;
            case R.id.imageView66://右Ball加
                rBall += 1;
                rightBallTextView.setText(String.valueOf(rBall));
                break;
            case R.id.imageView67://右Pass加
                rPass += 1;
                rightPassTextView.setText(String.valueOf(rPass));
                break;
            case R.id.imageView68://右X加
                rX += 1;
                rightXTextView.setText(String.valueOf(rX));
                break;
            case R.id.imageView72://右Ball减
                if (rBall > 0){
                    rBall -= 1;
                    rightBallTextView.setText(String.valueOf(rBall));
                }
                break;
            case R.id.imageView73://右Pass减
                if (rPass > 0){
                    rPass -= 1;
                    rightPassTextView.setText(String.valueOf(rPass));
                }
                break;
            case R.id.imageView74://右X减
                if (rX > 0){
                    rX -= 1;
                    rightXTextView.setText(String.valueOf(rX));
                }
                break;
            case R.id.imageView53://左KT键
                leftWin = !leftWin;
                leftKtImageView.setImageResource(leftWin ? R.drawable.score_kt2x : R.drawable.match_kt);
                rightWin = !leftWin;
                rightKtImageView.setImageResource(rightWin ? R.drawable.score_kt2x : R.drawable.match_kt);
                break;
            case R.id.imageView54://右KT键
                rightWin = !rightWin;
                rightKtImageView.setImageResource(rightWin ? R.drawable.score_kt2x : R.drawable.match_kt);
                leftWin = !rightWin;
                leftKtImageView.setImageResource(leftWin ? R.drawable.score_kt2x : R.drawable.match_kt);
                break;
            case R.id.imageView55://提交比分
//                doUploadData();
                submitImageView.setClickable(false);
                Intent intentUp = new Intent(UploadingActivity.this,GameResultActivity.class);
                switch (team){
                    case 1:
                        user_a = doSeleteUserId(textViewA.getText().toString());
                        user_b = doSeleteUserId(textViewF.getText().toString());
                        break;
                    case 2:
                        user_a = doSeleteUserId(textViewA.getText().toString()) + "|" + doSeleteUserId(textViewB.getText().toString());
                        user_b = doSeleteUserId(textViewF.getText().toString()) + "|" + doSeleteUserId(textViewE.getText().toString());
                        break;
                    case 3:
                        user_a = doSeleteUserId(textViewA.getText().toString()) + "|" + doSeleteUserId(textViewB.getText().toString()) + "|" + doSeleteUserId(textViewC.getText().toString());
                        user_a = doSeleteUserId(textViewF.getText().toString()) + "|" + doSeleteUserId(textViewE.getText().toString()) + "|" + doSeleteUserId(textViewD.getText().toString());
                        break;
                }
                intentUp.putExtra(LEFT_USER,user_a);
                intentUp.putExtra(RIGHT_USER,user_b);
                leftBallAddPass = lBall * 2 + lPass;
                rightBallAddPass = rBall * 2 + rPass;
                intentUp.putExtra(LEFT_GRADE,leftBallAddPass);
                intentUp.putExtra(RIGHT_GRADE, rightBallAddPass);
                int i = 0;
                if (leftWin) {
                    i = 1;
                } else {
                    i = 2;
                }
                intentUp.putExtra(KT_CODE, i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                doSavaToken();
                startActivity(intentUp);
                finish();
                break;
            case R.id.imageView56://放弃提交比分
                File file = new File(VCRpath);
                if (file.exists()){
                    file.delete();
                }
                finish();
                break;
        }
    }

    //根据昵称查Id
    private String doSeleteUserId(String s) {
        String id = null;
        List<Users> usersList = UsersDaoHelper.getInstance().queryByNickname(s);
        for(Users users : usersList){
            id = users.getUser_id();
        }
        return id;
    }

    int result_a;
    int result_b;
    int isKT_a = 0;
    int isKT_b = 0;
    private void doSavaToken() {
        int gradeA = doGetAddScores_a();
        int gradeB = doGetAddScores_b();
        if (leftWin){
            result_a = 1;
            result_b = -1;
            isKT_a = 1;
            isKT_b = 0;
        } else if (rightWin){
            result_a = -1;
            result_b = 1;
            isKT_a = 0;
            isKT_b = 1;
        } else if (leftWin == false && rightWin == false){
            if (leftBallAddPass > rightBallAddPass){
                result_a = 1;
                result_b = -1;
            } else if (leftBallAddPass < rightBallAddPass){
                result_a = -1;
                result_b = 1;
            } else {
                result_a = result_b = 0;
            }
        }
        String time = intent.getStringExtra(VSActivity.CURRENT_VCR_TIME);
        SideAandB side = new SideAandB();
        side.setUsers(user_a);
        side.setAdd_scores(gradeA);
        side.setResult(result_a);
        side.setGoals(lBall);
        side.setPannas(lPass);
        side.setFouls(lX);
        side.setFlagrant_fouls(0);
        side.setPanna_ko(isKT_a);
        side.setAbstained(0);
        side.setUsers_b(user_b);
        side.setAdd_scores_b(gradeB);
        side.setResult_b(result_b);
        side.setGoals_b(rBall);
        side.setPannas_b(rPass);
        side.setFouls_b(rX);
        side.setFlagrant_fouls_b(0);
        side.setPanna_ko_b(isKT_b);
        side.setAbstained_b(0);
        side.setPath(VCRpath);
        side.setTime(time);
        side.setGame_type(team);
        SideAandBDaoHelper.getInstance().addData(side);
    }

    private int doGetAddScores_b() {
        int add_scores_b =0;
        if (grade_b <= grade_a){
            if (rightWin){
                add_scores_b = (int) Math.floor((grade_a - grade_b) * 1.5 + 15);
            } else if (leftWin){
                add_scores_b = 1;
            } else {
                add_scores_b = (int) Math.floor(((grade_b - grade_a) * 1.5 + 15)/3);
            }
        } else if (grade_b - grade_a <= 2){
            if (rightWin){
                add_scores_b = (int) Math.floor((grade_b - grade_a) * 3 + 15);
            } else if (leftWin){
                add_scores_b = 1;
            } else {
                add_scores_b = (int) Math.floor(((grade_b - grade_a) * 3 + 15)/3);
            }
        } else if (grade_b - grade_a > 2){
            if (rightWin){
                add_scores_b = (int) Math.floor((grade_b - grade_a) * 2 + 13);
            } else if (leftWin){
                add_scores_b = 1;
            } else {
                add_scores_b = (int) Math.floor(((grade_b - grade_a) * 2 + 13)/3);
            }
        }
        return add_scores_b;
    }



    private int doGetAddScores_a() {
        int add_scores_a = 0;
        if (grade_a <= grade_b){
            if (leftWin){
                add_scores_a = (int) Math.floor((grade_b - grade_a) * 1.5 + 15);
            } else if (rightWin){
                add_scores_a = 1;
            } else {
                add_scores_a = (int) Math.floor(((grade_b - grade_a) * 1.5 + 15)/3);
            }
        } else if (grade_a - grade_b <= 2){
            if (leftWin){
                add_scores_a = (int) Math.floor((grade_a - grade_b) * 3 + 15);
            } else if (rightWin){
                add_scores_a = 1;
            } else {
                add_scores_a = (int) Math.floor(((grade_a - grade_b) * 3 + 15)/3);
            }
        } else if (grade_a - grade_b > 2){
            if (leftWin){
                add_scores_a = (int) Math.floor((grade_a - grade_b) * 2 + 13);
            } else if (rightWin){
                add_scores_a = 1;
            } else {
                add_scores_a = (int) Math.floor(((grade_a - grade_b) * 2 + 13)/3);
            }
        }
        return add_scores_a;
    }
}
