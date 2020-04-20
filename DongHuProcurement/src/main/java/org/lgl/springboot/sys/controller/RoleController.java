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
import org.lgl.springboot.sys.domain.Role;
import org.lgl.springboot.sys.service.PermissionService;
import org.lgl.springboot.sys.service.RoleService;
import org.lgl.springboot.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LGL
 * @since 2020-03-18
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;
    /**
     * 查询
     */
    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){
        IPage<Role> page = new Page<>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        queryWrapper.orderByDesc("createTime");

        this.roleService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 添加
     * @param roleVo
     * @return
     */
    @RequestMapping("addRole")
    public ResultObj addRole(RoleVo roleVo){
        try {
            roleVo.setCreatetime(new Date());
            this.roleService.save(roleVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改
     * @param roleVo
     * @return
     */
    @RequestMapping("updateRole")
    public ResultObj updateRole(RoleVo roleVo){
        try {
            this.roleService.updateById(roleVo);
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
    @RequestMapping("deleteRole")
    public ResultObj deleteRole(Integer id){
        try {
            this.roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据角色ID加载菜单和权限的数的json串
     * @param roleId
     * @return
     */
    @RequestMapping("initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer roleId){
        //查询所有可用的菜单和权限
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Permission> allPermissions = permissionService.list(queryWrapper);
        /**
         * 1.根据角色ID查询当前角色 拥有的权限和菜单ID
         * 2.根据菜单ID查询权限和菜单数据
         */
        List<Integer> currentRolePermission = this.roleService.queryRolePermissionIdsByRid(roleId);
        List<Permission> currentPermissions = null;
        if(currentRolePermission.size()>0){//如果有ID就去查询
            queryWrapper.in("id",currentRolePermission);
            currentPermissions = permissionService.list(queryWrapper);
        }else{
            currentPermissions = new ArrayList<>();
        }

        //构造List<TreeNode>
        List<TreeNode> nodes = new ArrayList<>();
        for (Permission p1:allPermissions) {
            String checkArr = "0";
            for (Permission p2:currentPermissions) {
                if(p1.getId() == p2.getId()){
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = (null==p1.getOpen()||p1.getOpen()==1)?true:false;
            nodes.add(new TreeNode(p1.getId(),p1.getPid(),p1.getTitle(),spread,checkArr));
        }
        return new DataGridView(nodes);
    }
    /**
     * 保存角色和菜单权限之间的关系
     */
    @RequestMapping("saveRolePermission")
    public ResultObj saveRolePermission(Integer rid,Integer[] ids){
        try{
            this.roleService.saveRolePermission(rid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DISPATCH_EPPOR;
        }
    }
}

