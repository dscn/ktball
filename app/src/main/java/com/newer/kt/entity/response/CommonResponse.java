package com.newer.kt.entity.response;

import com.newer.kt.entity.parser.BaseParseBean;

public class CommonResponse extends BaseParseBean {
    @Override
    public BaseParseBean parse(String json, Class cls) {
        return this;
    }
}