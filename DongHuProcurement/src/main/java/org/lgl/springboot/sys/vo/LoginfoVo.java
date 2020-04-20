package org.lgl.springboot.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.sys.domain.Loginfo;
import org.lgl.springboot.sys.domain.Permission;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginfoVo extends Loginfo {

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;//接受多个id

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date endTime;

}
