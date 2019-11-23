package com.xiongyf.jwtdemo.system.service;

import com.xiongyf.jwtdemo.system.pojo.SystemParam;

public interface SystemParamService {

    SystemParam findByKey(String key);

    String get(String key);

    SystemParam put(String key, String value);

    boolean isValueEquals(String key1,String key2);

}
