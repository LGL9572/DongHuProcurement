package org.lgl.springboot.sys.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.sys.domain.User;


@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {

    private Integer page = 1;
    private Integer limit = 10;
    /**
     * 验证码
     */
    private String code;

}
