package com.newer.kt.Refactor.Request;

import com.frame.app.utils.GsonTools;
import com.newer.kt.Refactor.Base.BaseEntity;
import com.newer.kt.Refactor.Base.BaseRestRequest;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/23.
 */
public class BaseEntityRequest extends BaseRestRequest<BaseEntity> {

    public BaseEntityRequest(String url) {
        super(url);
    }

    public BaseEntityRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public BaseEntity parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        BaseEntity baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, BaseEntity.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new BaseEntity();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
