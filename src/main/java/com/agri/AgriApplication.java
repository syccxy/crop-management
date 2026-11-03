package com.agri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author agri
 */
@SpringBootApplication
public class AgriApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgriApplication.class, args);
        System.getLogger("AgriApplication").log(System.Logger.Level.INFO, "====== 农作物品种信息管理系统启动成功 ======");
    }
}
