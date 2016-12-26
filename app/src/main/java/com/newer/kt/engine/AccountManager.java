package com.newer.kt.engine;


import com.newer.kt.entity.response.CommonResponse;
import com.newer.kt.net.KTLoadManager;
import com.newer.kt.net.callback.RequestCallBack;
import com.newer.kt.net.params.Load;

import java.io.File;

/**
 * Created by Kyle on 16/3/11.
 */
public class AccountManager {

//    private static AccountManager instance = new AccountManager();
//
//    private AccountManager() {
//    }
//
//    public static AccountManager getInstance() {
//        return instance;
//    }
//
//    public void editUserInfo(String user_id, String club_id, String school_class_id, String gender, String birthday, String phone, String nickname, File avatar, RequestCallBack<CommonResponse> requestCallBack) {
//        KTLoadManager.getInstance().loadDataPost(Load.paramsMaterial(user_id, club_id, school_class_id, gender, birthday, phone, nickname, avatar), CommonResponse.class, requestCallBack);
//    }
}