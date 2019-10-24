package com.xiaobingby.upms.vo;

import com.xiaobingby.upms.entity.SysDept;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeptTreeVo extends SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;

    private String key;

    private List<DeptTreeVo> children;

}
