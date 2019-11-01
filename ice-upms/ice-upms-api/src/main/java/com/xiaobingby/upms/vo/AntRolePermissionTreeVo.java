package com.xiaobingby.upms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Ant 角色权限树返回接口
 */
@Data
@NoArgsConstructor
public class AntRolePermissionTreeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> ids;

    private List<PermissionTreeVo> record;

}
