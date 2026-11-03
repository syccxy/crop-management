package com.agri.service.impl;

import com.agri.common.PageResult;
import com.agri.dto.CropDTO;
import com.agri.entity.Crop;
import com.agri.exception.BusinessException;
import com.agri.mapper.CropMapper;
import com.agri.service.CropService;
import com.agri.vo.CropVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 农作物品种业务实现
 * <p>
 * 关键点：
 * 1. 动态多条件查询：传参不为空才拼接条件，全部为空则查全表；
 * 2. 分页使用 MyBatis-Plus 物理分页插件；
 * 3. 关键业务节点打印 info 日志，异常场景抛出 BusinessException。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {

    private final CropMapper cropMapper;

    @Override
    public Long addCrop(CropDTO dto) {
        log.info("新增农作物品种, 参数: {}", dto);
        Crop crop = new Crop();
        BeanUtils.copyProperties(dto, crop);
        cropMapper.insert(crop);
        log.info("新增农作物品种成功, id={}", crop.getId());
        return crop.getId();
    }

    @Override
    public void updateCrop(Long id, CropDTO dto) {
        log.info("修改农作物品种, id={}, 参数: {}", id, dto);
        Crop crop = cropMapper.selectById(id);
        if (crop == null) {
            throw new BusinessException("要修改的品种不存在，id=" + id);
        }
        Crop update = new Crop();
        BeanUtils.copyProperties(dto, update);
        update.setId(id);
        cropMapper.updateById(update);
        log.info("修改农作物品种成功, id={}", id);
    }

    @Override
    public CropVO getById(Long id) {
        log.info("根据 id 查询品种详情, id={}", id);
        Crop crop = cropMapper.selectById(id);
        if (crop == null) {
            throw new BusinessException("品种不存在，id=" + id);
        }
        return toVO(crop);
    }

    @Override
    public void deleteCrop(Long id) {
        log.info("删除农作物品种, id={}", id);
        Crop crop = cropMapper.selectById(id);
        if (crop == null) {
            throw new BusinessException("要删除的品种不存在，id=" + id);
        }
        cropMapper.deleteById(id);
        log.info("删除农作物品种成功（逻辑删除）, id={}", id);
    }

    @Override
    public PageResult<CropVO> pageQuery(Integer pageNum, Integer pageSize,
                                        String cropName, String varietyName,
                                        String cropType, Integer plantStatus) {
        log.info("分页动态查询, pageNum={}, pageSize={}, cropName={}, varietyName={}, cropType={}, plantStatus={}",
                pageNum, pageSize, cropName, varietyName, cropType, plantStatus);

        // 参数兜底
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        // 动态条件：传参不为空则作为查询条件，为空不拼接
        LambdaQueryWrapper<Crop> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.hasText(cropName)) {
            wrapper.like(Crop::getCropName, cropName);
        }
        if (StringUtils.hasText(varietyName)) {
            wrapper.like(Crop::getVarietyName, varietyName);
        }
        if (StringUtils.hasText(cropType)) {
            wrapper.eq(Crop::getCropType, cropType);
        }
        if (plantStatus != null) {
            wrapper.eq(Crop::getPlantStatus, plantStatus);
        }
        wrapper.orderByDesc(Crop::getUpdateTime);

        Page<Crop> page = cropMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        List<CropVO> voList = page.getRecords().stream().map(this::toVO).toList();

        log.info("分页动态查询完成, 总条数={}, 总页数={}, 当前页条数={}",
                page.getTotal(), page.getPages(), voList.size());
        return PageResult.of(page.getTotal(), page.getPages(), pageNum, pageSize, voList);
    }

    @Override
    public CropVO toVO(Crop crop) {
        CropVO vo = new CropVO();
        BeanUtils.copyProperties(crop, vo);
        // 组合展示字段，屏蔽数据库内部字段
        vo.setPlantStatusDesc(crop.getPlantStatus() != null && crop.getPlantStatus() == 1 ? "推广中" : "已停推");
        return vo;
    }
}
