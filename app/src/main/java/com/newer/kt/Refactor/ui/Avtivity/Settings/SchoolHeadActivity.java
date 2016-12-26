package com.newer.kt.Refactor.ui.Avtivity.Settings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.frame.app.base.activity.BaseActivity;
import com.newer.kt.R;
import com.newer.kt.Refactor.view.ChooseHeadDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/21.
 */

public class SchoolHeadActivity extends BaseActivity implements ChooseHeadDialog.OnDialogOnClickListener {
    @Bind(R.id.image_head)
    ImageView image_head;
    public static final int PAIZHAO_REQUEST_CODE = 1;
    public static final int XIANGCE_REQUEST_CODE = 2;
    public static final int ALBUM_REQUEST_CODE = 3;
    public static final int UPDATA_CODE = 12;
    ChooseHeadDialog dialog;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_school_head);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @OnClick(R.id.cancle)
    public void cancle() {
        finish();
    }

    @OnClick(R.id.save)
    public void save() {
        if (dialog == null) {
            dialog = new ChooseHeadDialog(this, R.style.transparentFrameWindowStyle);
            dialog.setDialogOnClickListener(this);
        }
        dialog.show();
        dimActivity(dialog, 0.6f);
    }

    @Override
    public void onSave() {

    }

    @Override
    public void onPiaZhao() {
        takePhotoFromCamara();
    }

    @Override
    public void onXiangChe() {
        chooseFromMedia();
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
            image_head.setImageBitmap(photo);
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

    private void intentToCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri == null ? Uri.fromFile(imageFile) : uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);// 输出图片大小
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, XIANGCE_REQUEST_CODE);
    }
}
