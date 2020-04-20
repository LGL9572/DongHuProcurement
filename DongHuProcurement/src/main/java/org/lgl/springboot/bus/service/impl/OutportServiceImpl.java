package org.lgl.springboot.bus.service.impl;

import org.lgl.springboot.bus.domain.Goods;
import org.lgl.springboot.bus.domain.Inport;
import org.lgl.springboot.bus.domain.Outport;
import org.lgl.springboot.bus.mapper.GoodsMapper;
import org.lgl.springboot.bus.mapper.InportMapper;
import org.lgl.springboot.bus.mapper.OutportMapper;
import org.lgl.springboot.bus.service.OutportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lgl.springboot.sys.common.WebUtils;
import org.lgl.springboot.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LGL
 * @since 2020-03-28
 */
@Service
@Transactional
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService {

    @Autowired
    private InportMapper inportMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addOutPort(Integer id, Integer number, String remark) {
        //1.根据进货单id查询进货信息
        Inport inport = this.inportMapper.selectById(id);
        //2.根据商品id查询商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-number);
        this.goodsMapper.updateById(goods);
        //3.添加退货单信息
        Outport entity = new Outport();
        entity.setGoodsid(inport.getGoodsid());
        entity.setNumber(number);
        User user = (User) WebUtils.getSession().getAttribute("user");
        entity.setOperateperson(user.getName());
        entity.setOutportprice(inport.getInportprice());
        entity.setOutputtime(new Date());
        entity.setPaytype(inport.getPaytype());
        entity.setProviderid(inport.getProviderid());
        entity.setRemark(remark);
        this.baseMapper.insert(entity);
    }
}
