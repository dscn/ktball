package com.frame.app.base;

import android.os.Message;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.business.BaseResponse;
import com.frame.app.business.JudgeResponse;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;

public class BaseBusiness {

    private Message getMessage(int what, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = obj;
        return msg;
    }

    private Message getMessage(int what) {
        Message msg = Message.obtain();
        msg.what = what;
        return msg;
    }

    public void sendMessage(BaseActivity act, int what, Object obj) {
        act.mHandler.sendMessage(getMessage(what, obj));
    }

    public void sendMessage(BaseActivity act, int what) {
        act.mHandler.sendMessage(getMessage(what));
    }

    public void sendMessage(BaseFragment act, int what, Object obj) {
        act.mHandler.sendMessage(getMessage(what, obj));
    }

    public void sendMessage(BaseFragment act, int what) {
        act.mHandler.sendMessage(getMessage(what));
    }

    public <T extends BaseResponse> void isSucces(BaseFragment fragment, String str, int
            succes, int failure, Class<T> clz) {
        LogUtils.e(str);
        try{
            BaseResponse br = GsonTools.changeGsonToBean(str, BaseResponse.class);
            if (br != null && "success".equals(br.response)) {
                T result = GsonTools.changeGsonToBean(str, clz);
                sendMessage(fragment, succes, result);
            } else {
                String retMsg = br.msg;
                if ("".equals(retMsg)) {
                    retMsg = "系统繁忙";
                }
                sendMessage(fragment, failure, retMsg);
            }
        }catch (Exception e){
            sendMessage(fragment, failure, "系统繁忙");
        }
    }

    public <T extends BaseResponse> void isSucces(BaseActivity baseActivity, String str, int
            succes, int failure, Class<T> clz) {
        try{
            BaseResponse br = GsonTools.changeGsonToBean(str, BaseResponse.class);
            if (br != null && "success".equals(br.response)) {
                T result = GsonTools.changeGsonToBean(str, clz);
                sendMessage(baseActivity, succes, result);
            } else {
                String retMsg = br.msg;
                if ("".equals(retMsg)) {
                    retMsg = "系统繁忙";
                }
                sendMessage(baseActivity, failure, retMsg);
            }
        }catch (Exception e){
            sendMessage(baseActivity, failure, "系统繁忙");
        }
    }
}
