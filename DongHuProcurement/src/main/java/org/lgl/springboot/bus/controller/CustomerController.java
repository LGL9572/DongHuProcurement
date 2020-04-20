package org.lgl.springboot.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.lgl.springboot.bus.domain.Customer;
import org.lgl.springboot.bus.service.CustomerService;
import org.lgl.springboot.bus.vo.CustomerVo;
import org.lgl.springboot.sys.common.Constast;
import org.lgl.springboot.sys.common.DataGridView;
import org.lgl.springboot.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LGL
 * @since 2020-03-23
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 查询
     */
    @RequestMapping("loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo customerVo){
        IPage<Customer> page = new Page<>(customerVo.getPage(),customerVo.getLimit());
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()),"customername",customerVo.getCustomername());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getPhone()),"phone",customerVo.getPhone());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getConnectionperson()),"connectionperson",customerVo.getConnectionperson());
        this.customerService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 添加
     * @param customerVo
     * @return
     */
    @RequestMapping("addCustomer")
    public ResultObj addCustomer(CustomerVo customerVo){
        try {
            this.customerService.save(customerVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改
     * @param customerVo
     * @return
     */
    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(CustomerVo customerVo){
        try {
            this.customerService.updateById(customerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteCustomer")
    public ResultObj deleteCustomer(Integer id){
        try {
            this.customerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 批量删除
     * @param customerVo
     * @return
     */
    @RequestMapping("batchDeleteCustomer")
    public ResultObj batchDeleteCustomer(CustomerVo customerVo){
        try {
            Collection<Serializable> idList = new ArrayList<>();
            for (Integer id : customerVo.getIds()) {
                idList.add(id);
            }
            this.customerService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 加载所有客户的下拉列表
     * @return
     */
    @RequestMapping("loadAllCustomerForSelect")
    public DataGridView loadAllCustomerForSelect(){
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<Customer>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Customer> list = customerService.list(queryWrapper);
        return new DataGridView(list);
    }


}

