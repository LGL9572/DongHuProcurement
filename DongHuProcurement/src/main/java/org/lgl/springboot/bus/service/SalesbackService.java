package org.lgl.springboot.bus.service;

import org.lgl.springboot.bus.domain.Salesback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LGL
 * @since 2020-04-04
 */
public interface SalesbackService extends IService<Salesback> {
    /**
     * 对商品销售进行退货处理
     * @param id    销售单ID
     * @param number    退货数量
     * @param remark    备注
     */
    void addSalesback(Integer id, Integer number, String remark);

}
