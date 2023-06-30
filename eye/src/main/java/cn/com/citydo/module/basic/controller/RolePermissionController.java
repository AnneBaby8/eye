package cn.com.citydo.module.basic.controller;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.basic.dto.PermissionListDTO;
import cn.com.citydo.module.basic.dto.RoleGrantDTO;
import cn.com.citydo.module.basic.service.RolePermissionService;
import cn.com.citydo.module.basic.vo.PermissionVO;
import cn.com.citydo.utils.TreeNodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/1 11:14
 * @Version 1.0
 */
@Api(tags = "角色权限管理")
@RestController
@RequestMapping("api/basic/permission")
public class RolePermissionController  {

    @Autowired
    private RolePermissionService baseService;

    @ApiOperation(value = "授权")
    @PostMapping("grant")
    public ApiResponse<Boolean> grant(@RequestBody @Validated RoleGrantDTO roleGrantDTO) {
        return ApiResponse.ofSuccess(baseService.grant(roleGrantDTO));
    }

    @ApiOperation(value = "根据角色获取权限")
    @PostMapping("getPermisssionByRoleId")
    public ApiResponse<List<PermissionVO>> getPermisssionByRoleId(@RequestBody @Validated PermissionListDTO permissionListDTO) {
        return ApiResponse.ofSuccess(baseService.getPermisssionByRoleId(permissionListDTO));
    }

    @ApiOperation(value = "根据角色获取权限-树形结构")
    @PostMapping("getPermisssionTree")
    public ApiResponse<List<PermissionVO>> getPermisssionTree(@RequestBody @Validated PermissionListDTO permissionListDTO) {
        return ApiResponse.ofSuccess(TreeNodeUtil.assembleTree(baseService.getPermisssionByRoleId(permissionListDTO)));
    }
}
