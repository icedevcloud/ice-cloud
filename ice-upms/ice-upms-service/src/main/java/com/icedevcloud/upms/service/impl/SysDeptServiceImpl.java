package com.icedevcloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icedevcloud.common.core.api.Assert;
import com.icedevcloud.upms.entity.SysDept;
import com.icedevcloud.upms.entity.SysUser;
import com.icedevcloud.upms.mapper.SysDeptMapper;
import com.icedevcloud.upms.service.ISysDeptService;
import com.icedevcloud.upms.service.ISysUserService;
import com.icedevcloud.upms.vo.DeptTreeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * sys_dept 部门表 服务实现类
 * </p>
 *
 * @author XiaoBingBy
 * @since 2019-06-02
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Autowired
    private ISysUserService iSysUserService;

    @Override
    public List<DeptTreeVo> getDeptTree() {
        List<SysDept> depts = this.list(Wrappers.<SysDept>lambdaQuery()
                .orderByAsc(SysDept::getSort)
        );
        return genDeptTreeVo(depts);
    }

    private List<DeptTreeVo> genDeptTreeVo(List<SysDept> depts) {
        ArrayList<DeptTreeVo> deptTreeVos = new ArrayList<>();
        depts.forEach(item -> {
            if (item.getPid() == 0) {
                DeptTreeVo temp = new DeptTreeVo();
                BeanUtils.copyProperties(item, temp);
                temp.setTitle(temp.getName());
                temp.setKey(temp.getId().toString());
                deptTreeVos.add(temp);
                genSubDeptTreeVo(depts, temp);
            }
        });
        return deptTreeVos;
    }

    private void genSubDeptTreeVo(List<SysDept> depts, DeptTreeVo deptTreeVo) {
        ArrayList<DeptTreeVo> children = new ArrayList<>();
        depts.forEach(item -> {
            if (deptTreeVo.getId().longValue() == item.getPid().longValue()) {
                DeptTreeVo temp = new DeptTreeVo();
                BeanUtils.copyProperties(item, temp);
                temp.setTitle(temp.getName());
                temp.setKey(temp.getId().toString());
                genSubDeptTreeVo(depts, temp);
                children.add(temp);
            }
        });
        deptTreeVo.setChildren(children);
    }

    @Override
    public Boolean addDept(SysDept sysDept) {
        return this.save(sysDept);
    }

    @Override
    public Boolean updateDept(SysDept sysDept) {
        return this.updateById(sysDept);
    }

    @Override
    public Boolean removeDeptById(Long id) {
        // 查询是否存在子节点 存在不允许删除
        int count = this.count(Wrappers.<SysDept>lambdaQuery()
                .eq(SysDept::getPid, id)
        );
        Assert.fail(count >= 1, "存在子节点,不允许删除!");

        int userDeptCount = iSysUserService.count(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getDeptId, id)
        );
        Assert.fail(userDeptCount >= 1, "部门下存在用户,不允许删除!");

        return this.removeById(id);
    }

}
