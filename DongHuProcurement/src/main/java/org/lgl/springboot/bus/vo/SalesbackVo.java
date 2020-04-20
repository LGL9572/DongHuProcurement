package org.lgl.springboot.bus.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.bus.domain.Salesback;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: 落亦-
 * @Date: 2019/12/18 10:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SalesbackVo extends Salesback {

    private Integer page = 1;

    private Integer limit = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


}
