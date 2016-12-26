package com.newer.kt.Refactor.Entitiy;

import com.frame.app.business.BaseResponse;

/**
 * Created by ww on 2016/3/18.
 */
public class Token extends BaseResponse{

//  { response: "success",
// is_judge: 0或1(0: 不是裁判 1: 是裁判),
    public int is_judge;
// is_club_manager: 0或1(0: 不是俱乐部管理员 1: 是俱乐部管理员),
    public int is_club_manager;
// youku_token: "优酷token",
    public String youku_token;
// tudou_token: "土豆token"  }
    public String tudou_token;

    @Override
    public String toString() {
        return "Token{" +
                "is_judge=" + is_judge +
                ", is_club_manager=" + is_club_manager +
                ", youku_token='" + youku_token + '\'' +
                ", tudou_token='" + tudou_token + '\'' +
                '}';
    }
}
