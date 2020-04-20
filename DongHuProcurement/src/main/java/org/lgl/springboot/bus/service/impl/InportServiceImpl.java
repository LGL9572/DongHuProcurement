package org.lgl.springboot.bus.service.impl;

import org.lgl.springboot.bus.domain.Goods;
import org.lgl.springboot.bus.domain.Inport;
import org.lgl.springboot.bus.mapper.GoodsMapper;
import org.lgl.springboot.bus.mapper.InportMapper;
import org.lgl.springboot.bus.service.InportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LGL
 * @since 2020-03-28
 */
@Service
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Inport entity) {
        //根据商品编号查询商品
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(entity.getNumber()+goods.getNumber());
        goodsMapper.updateById(goods);
        //保存进货信息
        return super.save(entity);
    }

    @Override
    public boolean updateById(Inport entity) {
        //根据进货ID查询进货
        Inport inport = this.baseMapper.selectById(entity.getId());
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(entity.getGoodsid());
        //库存的算法 当前库存减去进货单之前的数量+修改之后的数量
        goods.setNumber(goods.getNumber()-inport.getNumber()+entity.getNumber());
        this.goodsMapper.updateById(goods);
        //更新进货单
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        //根据进货ID查询进货
        Inport inport = this.baseMapper.selectById(id);
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        //库存的算法 当前数量 - 进货的数量
        goods.setNumber(goods.getNumber()-inport.getNumber());
        this.goodsMapper.updateById(goods);
        return super.removeById(id);
    }
}
