package com.xiaobingby.upms.vo;

import com.xiaobingby.upms.entity.SysPermission;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 嵌套子表格权限管理展示TableTreeVo
 *
 * @author XiaoBingBy
 * @date 2018-12-06 14:43
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class PermissionTableTreeVo extends SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<PermissionTableTreeVo> children;

}
