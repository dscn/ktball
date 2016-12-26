package com.newer.kt.Refactor.Request;

import com.frame.app.utils.GsonTools;
import com.newer.kt.Refactor.Base.BaseRestRequest;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScores1v1Bean;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class RankingLeagueScores1v1Request extends BaseRestRequest<RankingLeagueScores1v1Bean> {

    public RankingLeagueScores1v1Request(String url) {
        super(url);
    }

    public RankingLeagueScores1v1Request(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public RankingLeagueScores1v1Bean parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        RankingLeagueScores1v1Bean baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, RankingLeagueScores1v1Bean.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new RankingLeagueScores1v1Bean();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
