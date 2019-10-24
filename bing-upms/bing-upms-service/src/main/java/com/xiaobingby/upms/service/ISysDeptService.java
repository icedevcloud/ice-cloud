package com.xiaobingby.upms.service;

import com.xiaobingby.upms.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobingby.upms.vo.DeptTreeVo;

import java.util.List;

/**
 * <p>
 * sys_organization 组织机构表 服务类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
public interface ISysDeptService extends IService<SysDept> {

    List<DeptTreeVo> getDeptTree();

    Boolean addDept(SysDept sysDept);

    Boolean updateDept(SysDept sysDept);

    Boolean removeDeptById(Long id);

}
