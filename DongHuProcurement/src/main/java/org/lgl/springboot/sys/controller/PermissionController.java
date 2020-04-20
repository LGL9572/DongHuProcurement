package org.lgl.springboot.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.lgl.springboot.sys.common.Constast;
import org.lgl.springboot.sys.common.DataGridView;
import org.lgl.springboot.sys.common.ResultObj;
import org.lgl.springboot.sys.common.TreeNode;
import org.lgl.springboot.sys.domain.Permission;
import org.lgl.springboot.sys.service.PermissionService;
import org.lgl.springboot.sys.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LGL
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    /***********************权限管理开始******************************/
    /**
     * 加载部门管理的左边数的json
     */
    @RequestMapping("loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(PermissionVo permissionVo){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constast.TYPE_MENU);
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission: list) {
            boolean spread = permission.getOpen()==0?false:true;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(), permission.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 全查询
     */
    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo){
        IPage<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constast.TYPE_PERMISSION);//只能查询权限
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPercode()),"percode",permissionVo.getPercode());
        queryWrapper.eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        queryWrapper.orderByAsc("ordernum");
        this.permissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加部门
     */
    @RequestMapping("addPermission")
    public ResultObj addAllPermission(PermissionVo permissionVo){
        try {
            permissionVo.setType(Constast.TYPE_PERMISSION);//设置添加类型
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改部门
     */
    @RequestMapping("updatePermission")
    public ResultObj updatePermission(PermissionVo permissionVo){
        try {
            System.out.println(permissionVo.getId() + "+" + permissionVo.getPid());
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除权限
     */
    @RequestMapping("deletePermission")
    public ResultObj deletePermission(PermissionVo permissionVo){
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 计算排序码
     */
    @RequestMapping("loadMaxOrderNum")
    public Map<String,Object> loadMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Permission> page = new Page<>(1,1);
        List<Permission> list = this.permissionService.page(page,queryWrapper).getRecords();
        if(list.size() > 0){
            map.put("value",list.get(0).getOrdernum() + 1);
        }else{
            map.put("value",1);
        }
        return map;
    }
    /***********************权限管理结束******************************/
}

