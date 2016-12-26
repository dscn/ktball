package com.newer.kt.Refactor.Request;

import com.frame.app.utils.GsonTools;
import com.newer.kt.Refactor.Base.BaseRestRequest;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScores1v1Bean;
import com.newer.kt.Refactor.Entitiy.RankingLeagueScores3v3Bean;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by jy on 16/6/14.
 */
public class RankingLeagueScores3v3Request extends BaseRestRequest<RankingLeagueScores3v3Bean> {

    public RankingLeagueScores3v3Request(String url) {
        super(url);
    }

    public RankingLeagueScores3v3Request(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public RankingLeagueScores3v3Bean parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
        String result = StringRequest.parseResponseString(url, responseHeaders, responseBody);
        RankingLeagueScores3v3Bean baseEntity = null ;
        try {
            baseEntity = GsonTools.changeGsonToBean(result, RankingLeagueScores3v3Bean.class);
        } catch (Throwable e) {
            // 这里默认的错误可以定义为你们自己的协议
            baseEntity = new RankingLeagueScores3v3Bean();
            baseEntity.response = "false";
        }
        return baseEntity;
    }

    @Override
    public String getAccept() {
        return null;
    }
}
