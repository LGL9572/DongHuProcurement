package org.lgl.springboot.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.sys.domain.Notice;
import org.lgl.springboot.sys.domain.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends Role {

    private Integer page = 1;
    private Integer limit = 10;


}
