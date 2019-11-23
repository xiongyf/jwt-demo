package com.xiongyf.jwtdemo.system.repository;

import com.xiongyf.jwtdemo.system.pojo.SystemParam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemParamRepository extends CrudRepository<SystemParam, Long> {

    SystemParam findByKey(String key);

}
