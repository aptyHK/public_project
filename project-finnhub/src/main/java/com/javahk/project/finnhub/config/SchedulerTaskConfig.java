package com.javahk.project.finnhub.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class SchedulerTaskConfig {

    // it will become true when run AppStartRunner
    public static boolean start = false;

    @Autowired
    private AdminService adminService;

    //@Scheduled(fixedRate = 60000) // 60sec
    @Scheduled(cron = "0/60 * 21-23,0-4 * * MON-FRI")
    public void fixedTimeTask() throws InterruptedException, FinnhubException {
        if (start) {
            log.info("Refresh times : " + LocalDateTime.now());
            adminService.refresh();
        }
    }
}
