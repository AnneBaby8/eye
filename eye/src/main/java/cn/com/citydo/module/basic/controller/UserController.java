package cn.com.citydo.module.basic.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.frame.DeleteDTO;
import cn.com.citydo.common.valid.InsertGroup;
import cn.com.citydo.common.valid.UpdateGroup;
import cn.com.citydo.module.basic.dto.PasswordDTO;
import cn.com.citydo.module.basic.dto.UserDTO;
import cn.com.citydo.module.basic.dto.UserQUERY;
import cn.com.citydo.module.basic.entity.Role;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.entity.UserDepartment;
import cn.com.citydo.module.basic.entity.UserRole;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.UserDepartmentService;
import cn.com.citydo.module.basic.service.UserRoleService;
import cn.com.citydo.module.basic.service.UserService;
import cn.com.citydo.module.basic.vo.UserDetailVO;
import cn.com.citydo.module.basic.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("api/basic/user")
public class UserController {


    @Autowired
    private UserService baseService;

    @Autowired
    private BCryptPasswordEncoder encrypt;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @ApiOperation(value = "修改密码")
    @PostMapping("updatePassword")
    public ApiResponse<Boolean> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        Long id = passwordDTO.getId();
        String oldPassword = passwordDTO.getOldPassword();
        String password = passwordDTO.getPassword();
        User org = baseService.getById(id);
        if (org == null) {
            throw new BaseException(500, "用户不存在");
        }
        if (!encrypt.matches(oldPassword, org.getPassword())) {
            throw new BaseException(500, "密码错误");
        }
        User entity = new User();
        entity.setId(id);
        if (StringUtils.isNotBlank(password)) {
            entity.setPassword(encrypt.encode(password));
        }
        return ApiResponse.ofSuccess(baseService.updateById(entity));
    }


    @ApiOperation(value = "新增")
    @PostMapping("create")
    public ApiResponse<Long> create(@Validated(InsertGroup.class) @RequestBody UserDTO entityDTO) {
        return ApiResponse.ofSuccess(baseService.saveOrUpdate(entityDTO));
    }

    @ApiOperation(value = "更新")
    @PostMapping("update")
    public ApiResponse<Long> update(@Validated(UpdateGroup.class) @RequestBody UserDTO entityDTO) {
        return ApiResponse.ofSuccess(baseService.saveOrUpdate(entityDTO));
    }

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    public ApiResponse<Boolean> delete(@RequestBody DeleteDTO deleteDTO) {
        Long id = deleteDTO.getId();
        if (ObjectUtils.isEmpty(baseService.getById(id))) {
            throw new BaseException(EyeStatus.ID_ERROR);
        }
        return ApiResponse.ofSuccess(baseService.removeById(id));
    }

    @ApiOperation(value = "分页")
    @PostMapping("page")
    public ApiResponse<IPage<UserVO>> page(@RequestBody UserQUERY query) {
        IPage<User> page = new Page<>(query.getPageNo(), query.getPageSize());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(query.getRoleId())) {
            //关联表信息
            QueryWrapper<UserRole> roleWrapper = new QueryWrapper();
            roleWrapper.lambda().eq(UserRole::getRoleId, query.getRoleId());
            List<UserRole> userRoles = userRoleService.list(roleWrapper);
            if (!CollectionUtils.isEmpty(userRoles)) {
                List<Long> userIdList = userRoles.stream()
                        .map(UserRole::getUserId)
                        .collect(Collectors.toList());
                queryWrapper.lambda().in(User::getId, userIdList);
            }
        }
        if (StringUtils.isNotBlank(query.getNickname())) {
            queryWrapper.lambda().like(User::getNickname, query.getNickname());
        }
        if (StringUtils.isNotBlank(query.getUsername())) {
            queryWrapper.lambda().like(User::getUsername, query.getUsername());
        }
        IPage<User> iPage = baseService.page(page, queryWrapper);
        return ApiResponse.ofSuccess(pageConver(iPage));
    }

    @ApiOperation(value = "查询详情")
    @GetMapping("/get")
    public ApiResponse<UserDetailVO> get(Long id) {
        UserDetailVO userDetail = new UserDetailVO();
        User user = baseService.getById(id);
        if (!ObjectUtils.isEmpty(user)) {
            BeanUtils.copyProperties(user, userDetail);
            List<Role> roles = baseService.getRoles(user.getId());
            if (!CollectionUtils.isEmpty(roles)) {
                userDetail.setRoleList(roles.stream().map(Role::getId)
                        .collect(Collectors.toList()));
            }
            List<UserDepartment> list = userDepartmentService.getByUserId(user.getId());
            if (!CollectionUtils.isEmpty(list)) {
                userDetail.setDepartmentId(list.get(0).getDepartmentId());
            }
        }
        return ApiResponse.ofSuccess(userDetail);
    }

    protected IPage<UserVO> pageConver(IPage<User> iPage) {
        List<UserVO> list = new ArrayList<>();
        List<User> records = iPage.getRecords();
        for (User record : records) {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(record, vo);

            List<Role> roles = baseService.getRoles(record.getId());
            if (!CollectionUtils.isEmpty(roles)) {
                vo.setRoleNames(roles.stream().map(Role::getName)
                        .collect(Collectors.toList()));
            }
            vo.setDepartmentNames(departmentService.getDepartment(record.getId()));
            list.add(vo);
        }
        IPage<UserVO> resultPage = new Page<>();
        BeanUtils.copyProperties(iPage, resultPage);
        resultPage.setRecords(list);
        return resultPage;
    }


}

