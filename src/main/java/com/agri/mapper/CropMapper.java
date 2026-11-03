package com.agri.mapper;

import com.agri.entity.Crop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 农作物品种 Mapper
 * <p>
 * 继承 MyBatis-Plus BaseMapper，天然具备单表 CRUD 能力，
 * 无需编写任何 SQL。
 */
@Mapper
public interface CropMapper extends BaseMapper<Crop> {
}
