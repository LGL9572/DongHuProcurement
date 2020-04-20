package org.lgl.springboot.bus.service.impl;

import org.lgl.springboot.bus.domain.Goods;
import org.lgl.springboot.bus.domain.Sales;
import org.lgl.springboot.bus.domain.Salesback;
import org.lgl.springboot.bus.mapper.GoodsMapper;
import org.lgl.springboot.bus.mapper.SalesMapper;
import org.lgl.springboot.bus.mapper.SalesbackMapper;
import org.lgl.springboot.bus.service.SalesbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lgl.springboot.sys.common.WebUtils;
import org.lgl.springboot.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LGL
 * @since 2020-04-04
 */
@Service
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements SalesbackService {

    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public void addSalesback(Integer id, Integer number, String remark) {
        //1.通过销售单ID查询出销售单信息
        Sales sales = salesMapper.selectById(id);
        //2.根据商品ID查询商品信息
        Goods goods = goodsMapper.selectById(sales.getGoodsid());
        //3.修改商品的数量     商品的数量-退货的数量
        goods.setNumber(goods.getNumber()+number);
        //修改进货的数量
        sales.setNumber(sales.getNumber()-number);
        salesMapper.updateById(sales);
        //4.进行修改
        goodsMapper.updateById(goods);
        //5.添加退货单信息
        Salesback salesback = new Salesback();
        salesback.setGoodsid(sales.getGoodsid());
        salesback.setNumber(number);
        User user = (User) WebUtils.getSession().getAttribute("user");
        salesback.setOperateperson(user.getName());
        salesback.setSalebackprice(sales.getSaleprice());
        salesback.setPaytype(sales.getPaytype());
        salesback.setSalesbacktime(new Date());
        salesback.setRemark(remark);
        salesback.setCustomerid(sales.getCustomerid());
        getBaseMapper().insert(salesback);
    }



}
