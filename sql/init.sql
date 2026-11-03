-- 农作物品种信息管理系统 建表脚本
-- 数据库：agri_db
CREATE DATABASE IF NOT EXISTS agri_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE agri_db;

DROP TABLE IF EXISTS `crop`;
CREATE TABLE `crop` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `crop_name`    VARCHAR(50)  NOT NULL COMMENT '作物名称',
    `variety_name` VARCHAR(100) NOT NULL COMMENT '品种名称',
    `crop_type`    VARCHAR(20)  NOT NULL COMMENT '作物类型：粮食/蔬菜/水果/油料',
    `suit_area`    VARCHAR(100) NOT NULL COMMENT '适宜种植区域',
    `growth_cycle` INT          NOT NULL COMMENT '生长周期（天）',
    `yield_level`  INT          NOT NULL COMMENT '亩产预估（kg）',
    `plant_status` TINYINT      NOT NULL DEFAULT 1 COMMENT '品种推广状态：0 停推 1 推广',
    `deleted`      TINYINT      NOT NULL DEFAULT 0 COMMENT '逻辑删除：0 未删除 1 已删除',
    `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_crop_name` (`crop_name`),
    KEY `idx_crop_type` (`crop_type`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '农作物品种表';

-- 初始化测试数据
INSERT INTO `crop` (`crop_name`, `variety_name`, `crop_type`, `suit_area`, `growth_cycle`, `yield_level`, `plant_status`) VALUES
('水稻', '隆平206',   '粮食', '湖南、湖北、江西', 130, 650,  1),
('水稻', '湘早籼45号', '粮食', '湖南',             110, 480,  1),
('玉米', '郑单958',   '粮食', '黄淮海地区',       100, 700,  1),
('玉米', '先玉335',   '粮食', '东北、华北',       120, 750,  0),
('白菜', '快菜一号',   '蔬菜', '全国大部分地区',    45, 4000, 1),
('番茄', '粉果番茄',   '蔬菜', '山东、河北',       150, 6000, 1),
('苹果', '红富士',     '水果', '山东烟台、陕西',   240, 3500, 1),
('柑橘', '沙糖橘',     '水果', '广西、广东',       280, 2800, 0),
('花生', '鲁花14号',   '油料', '山东、河南',       130, 350,  1),
('油菜', '华油杂62',   '油料', '长江流域',         210, 200,  1),
('大豆', '中黄13',     '油料', '黄淮海地区',        95, 180,  0),
('辣椒', '线椒王',     '蔬菜', '湖南、四川',        90, 2500, 1);
