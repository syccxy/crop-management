package com.agri.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 农作物品种提交参数对象（新增 / 修改共用）
 * <p>
 * DTO 层：接收前端提交的表单数据，承载所有参数校验规则，
 * 校验失败由全局异常处理器统一捕获并返回友好提示。
 */
@Data
public class CropDTO {

    /** 作物名称：必填，2~20 个字符 */
    @NotBlank(message = "作物名称不能为空")
    @Size(min = 2, max = 20, message = "作物名称长度必须在 2~20 个字符之间")
    private String cropName;

    /** 品种名称：必填，2~30 个字符 */
    @NotBlank(message = "品种名称不能为空")
    @Size(min = 2, max = 30, message = "品种名称长度必须在 2~30 个字符之间")
    private String varietyName;

    /** 作物类型：必填，只能是 粮食/蔬菜/水果/油料 */
    @NotBlank(message = "作物类型不能为空")
    @Pattern(regexp = "^(粮食|蔬菜|水果|油料)$", message = "作物类型只能是：粮食、蔬菜、水果、油料")
    private String cropType;

    /** 适宜种植区域：必填，最长 50 个字符 */
    @NotBlank(message = "适宜种植区域不能为空")
    @Size(max = 50, message = "适宜种植区域长度不能超过 50 个字符")
    private String suitArea;

    /** 生长周期（天）：必填，1~365 */
    @NotNull(message = "生长周期不能为空")
    @Min(value = 1, message = "生长周期不能小于1天")
    @Max(value = 365, message = "生长周期不能超过365天")
    private Integer growthCycle;

    /** 亩产预估（kg）：必填，不能为负，上限 100000 */
    @NotNull(message = "亩产预估不能为空")
    @Min(value = 0, message = "亩产预估不能为负数")
    @Max(value = 100000, message = "亩产预估不能超过100000kg")
    private Integer yieldLevel;

    /** 品种推广状态：必填，0 停推 1 推广 */
    @NotNull(message = "推广状态不能为空")
    @Min(value = 0, message = "推广状态只能是 0（停推）或 1（推广）")
    @Max(value = 1, message = "推广状态只能是 0（停推）或 1（推广）")
    private Integer plantStatus;
}
