package com.agri.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页查询返回封装
 *
 * @param <T> 数据类型
 */
@Data
public class PageResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 总条数 */
    private Long total;

    /** 总页数 */
    private Long pages;

    /** 当前页码 */
    private Long pageNum;

    /** 每页条数 */
    private Long pageSize;

    /** 当前页数据列表 */
    private List<T> list;

    /**
     * 基于 MyBatis-Plus 的 Page 对象构建分页返回
     */
    public static <T> PageResult<T> of(long total, long pages, long pageNum, long pageSize, List<T> list) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setPages(pages);
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setList(list);
        return pageResult;
    }
}
