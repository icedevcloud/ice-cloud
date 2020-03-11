package com.icedevcloud.upms.service;

import com.icedevcloud.upms.entity.SysDept;
import com.icedevcloud.upms.vo.DeptTreeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sys_dept 部门表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 生成部门Tree
     *
     * @return
     */
    List<DeptTreeVo> getDeptTree();

    /**
     * 添加部门
     *
     * @param sysDept
     * @return
     */
    Boolean addDept(SysDept sysDept);

    /**
     * 更新部门
     *
     * @param sysDept
     * @return
     */
    Boolean updateDept(SysDept sysDept);

    /**
     * 部门Id 删除部门
     *
     * @param id
     * @return
     */
    Boolean removeDeptById(Long id);

}
