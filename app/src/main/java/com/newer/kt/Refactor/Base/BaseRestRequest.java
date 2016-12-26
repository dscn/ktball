package com.newer.kt.Refactor.Base;

import com.newer.kt.Refactor.utils.MD5;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.RestRequest;

/**
 * Created by jy on 16/6/16.
 */
public abstract class BaseRestRequest<T> extends RestRequest<T> {
    public BaseRestRequest(String url) {
        super(url);
        setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        add("authenticity_token", MD5.getToken(url));
    }

    public BaseRestRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        add("authenticity_token", MD5.getToken(url));
    }
}
