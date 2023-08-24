package com.vehicle.config.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MyStartLog implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        StringBuilder commandLog = new StringBuilder();
        commandLog.append("+==================================================================================+\n");
        commandLog.append("+                        项目启动成功!!!                                             +\n");
        commandLog.append("+                      Deleba的车辆管理项目                                          +\n");
        commandLog.append("+==================================================================================+\n");
        System.out.println(commandLog.toString());
    }
}

