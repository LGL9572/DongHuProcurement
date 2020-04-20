package org.lgl.springboot.bus.service;

import org.lgl.springboot.bus.domain.Outport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LGL
 * @since 2020-03-28
 */
public interface OutportService extends IService<Outport> {
    /**
     * 退货
     * @param id id
     * @param number 退货数量
     * @param remark 退货备注
     */
    void addOutPort(Integer id, Integer number, String remark);
}
