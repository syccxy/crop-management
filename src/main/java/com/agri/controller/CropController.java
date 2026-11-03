package com.agri.controller;

import com.agri.common.PageResult;
import com.agri.common.Result;
import com.agri.dto.CropDTO;
import com.agri.service.CropService;
import com.agri.vo.CropVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 农作物品种接口
 * <p>
 * 接口清单：
 * <pre>
 * POST   /crop/add     新增农作物品种（接收 CropDTO，参数校验）
 * PUT    /crop/update  修改农作物品种（接收 CropDTO）
 * GET    /crop/{id}    根据 id 查询（返回 CropVO）
 * DELETE /crop/{id}    删除品种（逻辑删除）
 * GET    /crop/list    分页动态查询
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping("/crop")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    /** 新增农作物品种 */
    @PostMapping("/add")
    public Result<Long> add(@Valid @RequestBody CropDTO dto) {
        log.info("收到新增品种请求: {}", dto);
        return Result.success(cropService.addCrop(dto));
    }

    /** 修改农作物品种 */
    @PutMapping("/update")
    public Result<Void> update(@RequestParam Long id, @Valid @RequestBody CropDTO dto) {
        log.info("收到修改品种请求, id={}, body={}", id, dto);
        cropService.updateCrop(id, dto);
        return Result.success();
    }

    /** 根据 id 查询详情 */
    @GetMapping("/{id}")
    public Result<CropVO> detail(@PathVariable Long id) {
        log.info("收到查询品种详情请求, id={}", id);
        return Result.success(cropService.getById(id));
    }

    /** 删除品种（逻辑删除） */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("收到删除品种请求, id={}", id);
        cropService.deleteCrop(id);
        return Result.success();
    }

    /** 分页动态查询 */
    @GetMapping("/list")
    public Result<PageResult<CropVO>> list(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String cropName,
            @RequestParam(required = false) String varietyName,
            @RequestParam(required = false) String cropType,
            @RequestParam(required = false) Integer plantStatus) {
        log.info("收到分页动态查询请求: pageNum={}, pageSize={}, cropName={}, varietyName={}, cropType={}, plantStatus={}",
                pageNum, pageSize, cropName, varietyName, cropType, plantStatus);
        return Result.success(cropService.pageQuery(pageNum, pageSize, cropName, varietyName, cropType, plantStatus));
    }
}
