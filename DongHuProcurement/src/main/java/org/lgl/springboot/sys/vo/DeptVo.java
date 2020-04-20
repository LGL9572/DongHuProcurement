package org.lgl.springboot.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.sys.common.DataGridView;
import org.lgl.springboot.sys.domain.Dept;
import org.lgl.springboot.sys.domain.Loginfo;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept {

    private Integer page = 1;
    private Integer limit = 10;



}
