package cn.com.citydo.module.core.controller;


import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.Consts;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.frame.BaseController;
import cn.com.citydo.common.valid.InsertGroup;
import cn.com.citydo.common.valid.UpdateGroup;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.dto.WarningConfigDTO;
import cn.com.citydo.module.core.dto.WarningConfigQUERY;
import cn.com.citydo.module.core.entity.WarningConfig;
import cn.com.citydo.module.core.entity.WarningPolicy;
import cn.com.citydo.module.core.service.WarningConfigService;
import cn.com.citydo.module.core.service.WarningPolicyService;
import cn.com.citydo.module.core.vo.WarningConfigVO;
import cn.com.citydo.utils.SecurityUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 预警配置表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Api(tags = "预警配置")
@RestController
@RequestMapping("api/core/warningConfig")
public class WarningConfigController extends BaseController<WarningConfig, WarningConfigService, WarningConfigDTO, WarningConfigVO, WarningConfigQUERY> {

    @Autowired
    private WarningPolicyService warningPolicyService;

    @ApiOperation(value = "新增")
    @PostMapping("create")
    @Override
    public ApiResponse<Boolean> create(@Validated(InsertGroup.class) @RequestBody  WarningConfigDTO entityDTO) {
        WarningConfig entity = toEntityConver(entityDTO);
        UserPrincipal user = SecurityUtil.getCurrentUser();
        entity.setCreator(user.getId().toString());
        entity.setUpdator(user.getId().toString());
        entity.setWarnNo(Consts.WARN_PRE+"_"+DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        baseService.check(entity.getName(),entity.getWarnNo(),entity.getWarnType(),null);
        return ApiResponse.ofSuccess(baseService.save(entity));
    }


    @ApiOperation(value = "更新")
    @PostMapping("update")
    @Override
    public ApiResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody WarningConfigDTO entityDTO) {
        WarningConfig entity = toEntityConver(entityDTO);
        if(ObjectUtils.isEmpty(baseService.getById(entity.getId()))){
            throw  new BaseException(EyeStatus.ID_ERROR);
        }
        UserPrincipal user = SecurityUtil.getCurrentUser();
        entity.setUpdator(user.getId().toString());
        baseService.check(entity.getName(),entity.getWarnNo(),entity.getWarnType(),entity.getId());
        return ApiResponse.ofSuccess(baseService.updateById(entity));
    }

    @PostMapping("page")
    @Override
    public ApiResponse<IPage<WarningConfigVO>> page(@RequestBody WarningConfigQUERY query) {
        IPage<WarningConfig> page = new Page<>(query.getPageNo(), query.getPageSize());
        QueryWrapper<WarningConfig> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(query.getName())){
            queryWrapper.lambda().like(WarningConfig::getName,query.getName());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<WarningConfig> iPage = baseService.page(page, queryWrapper);
        IPage<WarningConfigVO> resultPage = pageConver(iPage);
        return ApiResponse.ofSuccess(resultPage);
    }

    @Override
    protected IPage<WarningConfigVO> pageConver(IPage<WarningConfig> iPage) {
        List<WarningConfigVO> list = new ArrayList<>();
        List<WarningConfig> records = iPage.getRecords();
        for (WarningConfig record :records) {
            WarningConfigVO vo = new WarningConfigVO();
            BeanUtils.copyProperties(record, vo);
            List<WarningPolicy>  policyList = warningPolicyService.getByConfigId(record.getId());
            if(!CollectionUtils.isEmpty(policyList)) {
                List<String> policyTypeList = policyList.stream().map(WarningPolicy::getPolicyType).collect(Collectors.toList());
                vo.setPolicyTypeList(policyTypeList);
            }
            list.add(vo);
        }
        IPage<WarningConfigVO> resultPage = new Page<>();
        BeanUtils.copyProperties(iPage,resultPage);
        resultPage.setRecords(list);
        return resultPage;
    }
}

