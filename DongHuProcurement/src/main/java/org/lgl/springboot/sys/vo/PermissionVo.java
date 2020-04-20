package org.lgl.springboot.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.sys.domain.Permission;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {

    private Integer page = 1;
    private Integer limit = 10;

}
