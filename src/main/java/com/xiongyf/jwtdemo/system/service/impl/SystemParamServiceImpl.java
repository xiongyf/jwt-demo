package com.xiongyf.jwtdemo.system.service.impl;

import com.xiongyf.jwtdemo.system.pojo.SystemParam;
import com.xiongyf.jwtdemo.system.repository.SystemParamRepository;
import com.xiongyf.jwtdemo.system.service.SystemParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class SystemParamServiceImpl implements SystemParamService {

    @Resource
    SystemParamRepository systemParamRepository;

    @Override
    public SystemParam findByKey(String key) {
        return systemParamRepository.findByKey(key);
    }

    @Override
    public String get(String key) {
        SystemParam s = this.findByKey(key);
        if (s != null) {
            return s.getValue();
        }
        return null;
    }

    @Override
    public SystemParam put(String key, String value) {
        SystemParam systemParam = this.findByKey(key);
        if (systemParam == null) {
            systemParam = new SystemParam();
            systemParam.setCreateTime(LocalDateTime.now());
            systemParam.setKey(key);
        } else {
            systemParam.setUpdateTime(LocalDateTime.now());
        }
        systemParam.setValue(value);
        systemParam = systemParamRepository.save(systemParam);
        return systemParam;
    }

    @Override
    public boolean isValueEquals(String key1, String key2) {
        String value1 = get(key1);
        String value2 = get(key2);
        if (value1 != null && value1.equals(value2)) {
            return true;
        }
        return false;
    }
}
