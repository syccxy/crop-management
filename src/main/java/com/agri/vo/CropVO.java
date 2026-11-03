package com.agri.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 农作物品种视图对象（返回给前端）
 * <p>
 * VO 层：与 Entity 解耦，按前端展示需要组合字段，
 * 屏蔽 deleted 等数据库内部字段，不暴露敏感 / 冗余信息。
 */
@Data
public class CropVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    private Long id;

    /** 作物名称 */
    private String cropName;

    /** 品种名称 */
    private String varietyName;

    /** 作物类型 */
    private String cropType;

    /** 适宜种植区域 */
    private String suitArea;

    /** 生长周期（天） */
    private Integer growthCycle;

    /** 亩产预估（kg） */
    private Integer yieldLevel;

    /** 品种推广状态：0 停推 1 推广 */
    private Integer plantStatus;

    /** 推广状态描述（组合字段：推广中 / 已停推） */
    private String plantStatusDesc;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
