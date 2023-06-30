package cn.com.citydo.module.basic.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.frame.BaseController;
import cn.com.citydo.module.basic.dto.RoleDTO;
import cn.com.citydo.module.basic.dto.RoleQUERY;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.service.RoleService;
import cn.com.citydo.module.basic.vo.RoleVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("api/basic/role")
public class RoleController extends BaseController<Role, RoleService, RoleDTO, RoleVO, RoleQUERY> {

    @PostMapping("page")
    @Override
    public ApiResponse<IPage<RoleVO>> page(@RequestBody  RoleQUERY roleQUERY) {
        IPage<Role> page = new Page<>(roleQUERY.getPageNo(), roleQUERY.getPageSize());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(roleQUERY.getName())){
            queryWrapper.lambda().like(Role::getName,roleQUERY.getName());
        }
        if(StringUtils.isNotBlank(roleQUERY.getDescription())){
            queryWrapper.lambda().like(Role::getDescription,roleQUERY.getDescription());
        }
        IPage<Role> iPage = baseService.page(page, queryWrapper);
        IPage<RoleVO> resultPage = pageConver(iPage);
        return ApiResponse.ofSuccess(resultPage);
    }

    @GetMapping("list")
    public ApiResponse<List<Role>> list() {
        List<Role> list = baseService.list();
        return ApiResponse.ofSuccess(list);
    }
}

