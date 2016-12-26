package com.newer.kt.entity.parser;

import com.frame.app.utils.GsonTools;

import java.io.Serializable;

public class BaseParseBean<T extends BaseParseBean> implements Serializable {
	/**
	 * 自定义解析方法，默认使用Gson解析，Gson解析出现异常时会调用此方法
	 * @param json
	 * @return
	 */
	public T parse(String json, Class<T> cls){
		return GsonTools.changeGsonToBean(json.toString(), cls);
	}
}
