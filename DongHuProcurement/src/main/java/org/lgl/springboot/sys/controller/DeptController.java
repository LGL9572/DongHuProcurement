package org.lgl.springboot.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.lgl.springboot.sys.common.DataGridView;
import org.lgl.springboot.sys.common.ResultObj;
import org.lgl.springboot.sys.common.TreeNode;
import org.lgl.springboot.sys.domain.Dept;
import org.lgl.springboot.sys.service.DeptService;
import org.lgl.springboot.sys.vo.DeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LGL
 * @since 2020-03-16
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 加载部门管理的左边数的json
     */
    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo){
        List<Dept> list = this.deptService.list();
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Dept dept: list) {
            boolean spread = dept.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(dept.getId(),dept.getPid(), dept.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 全查询
     */
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo){
        IPage<Dept> page = new Page<>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());
        queryWrapper.eq(deptVo.getId()!=null,"id",deptVo.getId())
                .or().eq(deptVo.getId()!=null,"pid",deptVo.getId());
        queryWrapper.orderByAsc("ordernum");
        this.deptService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加部门
     */
    @RequestMapping("addDept")
    public ResultObj addAllDept(DeptVo deptVo){
        try {
            deptVo.setCreatetime(new Date());
            this.deptService.save(deptVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改部门
     */
    @RequestMapping("updateDept")
    public ResultObj updateDept(DeptVo deptVo){
        try {
            System.out.println(deptVo.getId() + "+" + deptVo.getPid());
            this.deptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除部门
     */
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(DeptVo deptVo){
        try {
            this.deptService.removeById(deptVo.getId());
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 查看当前部门下是否有子部门
     */
    @RequestMapping("checkDeptHasChildrenNode")
    public Map<String,Object> checkDeptHasChildrenNode(DeptVo deptVo){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<Dept> queryWrapper = new QueryWrapper<Dept>();
        queryWrapper.eq("pid",deptVo.getId());
        List<Dept> list = this.deptService.list(queryWrapper);
        if(list.size()>0){
            map.put("value",true);
        }else{
            map.put("value",false);
        }
        return map;
    }

    /**
     * 计算排序码
     */
    @RequestMapping("loadMaxOrderNum")
    public Map<String,Object> loadMaxOrderNum(){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Dept> page = new Page<>(1,1);
        List<Dept> list = this.deptService.page(page,queryWrapper).getRecords();
        if(list.size()>0){
            map.put("value",list.get(0).getOrdernum()+1);
        }else{
            map.put("value",1);
        }
        return map;
    }

}

