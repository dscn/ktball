package com.newer.kt.Refactor.Request;

import com.frame.app.utils.GsonTools;
import com.newer.kt.Refactor.Base.BaseRestRequest;
import com.newer.kt.Refactor.Entitiy.RankingPowerBean;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class RankingPowerRequest extends BaseRestRequest<RankingPowerBean> {

    public RankingPowerRequest(String url) {
        super(url);
    }

    public RankingPowerRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public RankingPowerBean parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        RankingPowerBean baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, RankingPowerBean.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new RankingPowerBean();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
