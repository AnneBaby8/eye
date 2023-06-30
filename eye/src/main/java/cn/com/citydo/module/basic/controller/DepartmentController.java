package cn.com.citydo.module.basic.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.frame.BaseController;
import cn.com.citydo.module.basic.dto.DepartmentDTO;
import cn.com.citydo.module.basic.dto.DepartmentQUERY;
import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.entity.UserDepartment;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.UserDepartmentService;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.utils.BaseTreeNode;
import cn.com.citydo.utils.SecurityUtil;
import cn.com.citydo.utils.TreeNodeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Api(tags = "部门管理")
@RestController
@RequestMapping("api/basic/department")
public class DepartmentController extends BaseController<Department, DepartmentService, DepartmentDTO, DepartmentVO, DepartmentQUERY> {


    @Override
    protected Department toEntityConver(DepartmentDTO entityDTO) {
        Department entity = conversionService.convert(entityDTO, Department.class);
        return entity;
    }

    @Override
    protected IPage<DepartmentVO> pageConver(IPage<Department> iPage) {
        List<DepartmentVO> list = new ArrayList<>();
        List<Department> records = iPage.getRecords();
        for (Department record : records) {
            DepartmentVO vo = conversionService.convert(record, DepartmentVO.class);
            list.add(vo);
        }
        IPage<DepartmentVO> resultPage = new Page<>();
        BeanUtils.copyProperties(iPage, resultPage);
        resultPage.setRecords(list);
        return resultPage;
    }


    @ApiOperation(value = "根据parentId 获取部门")
    @GetMapping("/getByParentId")
    public ApiResponse<DepartmentVO> getByParentId(@RequestParam(defaultValue = "0") Long parentId) {
        return ApiResponse.ofSuccess(baseService.getByParentId(parentId));
    }


    @ApiOperation(value = "所有部门")
    @GetMapping("/getAll")
    public ApiResponse<DepartmentVO> getAll() {
        return ApiResponse.ofSuccess(baseService.getAll());
    }


    @ApiOperation(value = "拿到所有部门(除了网格)")
    @GetMapping("/getDepartments")
    public ApiResponse<DepartmentVO> getDepartments() {
        return ApiResponse.ofSuccess(baseService.getDepartments());
    }


    @Autowired
    private UserDepartmentService userDepartmentService;

    @ApiOperation(value = "树形结构-根据当前用户")
    @GetMapping("/getAllTree")
    public ApiResponse<List<BaseTreeNode>> getAllTree() {
        List<BaseTreeNode> nodeList = new ArrayList<>();
        List<DepartmentVO> list = baseService.getAll();
        baseService.getNodeList(nodeList, list);
        UserPrincipal user = SecurityUtil.getCurrentUser();
        Long parentId = 0L;
        List<UserDepartment> departmentList = userDepartmentService.getByUserId(user.getId());
        if(!CollectionUtils.isEmpty(departmentList)){
            parentId =  departmentList.get(0).getDepartmentId();
        }
        return ApiResponse.ofSuccess(TreeNodeUtil.assembleTreeById(parentId, nodeList));
    }


    @ApiOperation(value = "树形结构-拿到所有部门(除了网格)")
    @GetMapping("/getDepartmentsTree")
    public ApiResponse<List<BaseTreeNode>> getDepartmentsTree() {
        List<BaseTreeNode> nodeList = new ArrayList<>();
        List<DepartmentVO> list = baseService.getDepartments();
        baseService.getNodeList(nodeList, list);
        return ApiResponse.ofSuccess(TreeNodeUtil.assembleTree(nodeList));
    }
}

