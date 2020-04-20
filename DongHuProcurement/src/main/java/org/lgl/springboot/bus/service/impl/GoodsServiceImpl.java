package org.lgl.springboot.bus.service.impl;

import org.lgl.springboot.bus.domain.Goods;
import org.lgl.springboot.bus.mapper.GoodsMapper;
import org.lgl.springboot.bus.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LGL
 * @since 2020-03-24
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Override
    public boolean save(Goods entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(Goods entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public Goods getById(Serializable id) {
        return super.getById(id);
    }
}
