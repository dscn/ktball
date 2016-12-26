package com.newer.kt.Refactor.Request;

import com.frame.app.utils.GsonTools;
import com.newer.kt.Refactor.Base.BaseRestRequest;
import com.newer.kt.entity.StudyFinished;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/23.
 */
public class StudyFinishedRequest extends BaseRestRequest<StudyFinished> {

    public StudyFinishedRequest(String url) {
        super(url);
    }

    public StudyFinishedRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public StudyFinished parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        StudyFinished baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, StudyFinished.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new StudyFinished();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
