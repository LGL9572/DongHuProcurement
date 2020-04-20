package org.lgl.springboot.bus.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lgl.springboot.bus.domain.Goods;
import org.lgl.springboot.bus.domain.Provider;

@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsVo extends Goods {
    private Integer page = 1;
    private Integer limit = 10;
}
