package com.agri.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 农作物品种实体（对应数据库表 crop）
 * <p>
 * Entity 层：和数据库表一一对应，不做参数校验，不直接返回给前端
 */
@Data
@TableName("crop")
public class Crop implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID，数据库自增 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 作物名称，如：水稻、玉米 */
    @TableField("crop_name")
    private String cropName;

    /** 品种名称，如：隆平 206 */
    @TableField("variety_name")
    private String varietyName;

    /** 作物类型：粮食 / 蔬菜 / 水果 / 油料 */
    @TableField("crop_type")
    private String cropType;

    /** 适宜种植区域 */
    @TableField("suit_area")
    private String suitArea;

    /** 生长周期（天） */
    @TableField("growth_cycle")
    private Integer growthCycle;

    /** 亩产预估（kg） */
    @TableField("yield_level")
    private Integer yieldLevel;

    /** 品种推广状态：0 停推 1 推广 */
    @TableField("plant_status")
    private Integer plantStatus;

    /** 逻辑删除标记：0 未删除 1 已删除 */
    @TableLogic
    @TableField("deleted")
    private Integer deleted;

    /** 创建时间（自动填充） */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间（自动填充） */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
