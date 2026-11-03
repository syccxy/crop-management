package com.agri.service;

import com.agri.common.PageResult;
import com.agri.dto.CropDTO;
import com.agri.entity.Crop;
import com.agri.vo.CropVO;

/**
 * 农作物品种业务接口
 */
public interface CropService {

    /**
     * 新增农作物品种
     *
     * @param dto 前端提交参数（已校验）
     * @return 新增记录 ID
     */
    Long addCrop(CropDTO dto);

    /**
     * 修改农作物品种
     *
     * @param id  主键
     * @param dto 前端提交参数（已校验）
     */
    void updateCrop(Long id, CropDTO dto);

    /**
     * 根据 id 查询详情
     *
     * @param id 主键
     * @return 视图对象
     */
    CropVO getById(Long id);

    /**
     * 删除品种（逻辑删除）
     *
     * @param id 主键
     */
    void deleteCrop(Long id);

    /**
     * 分页 + 动态多条件查询
     *
     * @param pageNum     当前页
     * @param pageSize    每页条数
     * @param cropName    作物名称（模糊，可空）
     * @param varietyName 品种名称（模糊，可空）
     * @param cropType    作物类型（精确，可空）
     * @param plantStatus 推广状态（精确，可空）
     * @return 分页结果
     */
    PageResult<CropVO> pageQuery(Integer pageNum, Integer pageSize,
                                 String cropName, String varietyName,
                                 String cropType, Integer plantStatus);

    /**
     * Entity 转 VO（剥离 deleted 等内部字段，补充状态描述）
     */
    CropVO toVO(Crop crop);
}
