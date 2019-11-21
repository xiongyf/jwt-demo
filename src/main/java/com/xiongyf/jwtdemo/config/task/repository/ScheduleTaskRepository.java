package com.xiongyf.jwtdemo.config.task.repository;

import com.xiongyf.jwtdemo.config.task.pojo.ScheduledTaskBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleTaskRepository extends JpaRepository<ScheduledTaskBean, Long> {

    /**
     * id 获取 任务信息
     */
    ScheduledTaskBean findScheduleTaskBeanById(Long id);

    /**
     * 根据key 获取 任务信息
     */
    ScheduledTaskBean findScheduleTaskBeanByTaskKey(String key);

    /**
     * 获取所有任务
     */
    List<ScheduledTaskBean> findAll();

}
