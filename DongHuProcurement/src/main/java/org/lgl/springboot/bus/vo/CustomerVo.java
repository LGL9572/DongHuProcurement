package org.lgl.springboot.bus.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.bus.domain.Customer;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerVo extends Customer {
    private Integer page = 1;
    private Integer limit = 10;
    private Integer[] ids;
}
