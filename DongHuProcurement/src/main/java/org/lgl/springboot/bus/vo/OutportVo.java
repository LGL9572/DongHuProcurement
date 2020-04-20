package org.lgl.springboot.bus.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.bus.domain.Inport;
import org.lgl.springboot.bus.domain.Outport;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class OutportVo extends Outport {
    private Integer page = 1;
    private Integer limit = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;

}
