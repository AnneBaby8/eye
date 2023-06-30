package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.module.core.entity.WarningConfig;
import cn.com.citydo.module.core.mapper.WarningConfigMapper;
import cn.com.citydo.module.core.service.WarningConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 预警配置表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Service
public class WarningConfigServiceImpl extends ServiceImpl<WarningConfigMapper, WarningConfig> implements WarningConfigService {

    @Override
    public List<WarningConfig> getByName(String name) {
        return this.list(new QueryWrapper<WarningConfig>().lambda().eq(WarningConfig::getName, name));
    }

    @Override
    public List<WarningConfig> getDataByParam(String name,String warnType,Long id) {
        LambdaQueryWrapper<WarningConfig> lambda = new QueryWrapper<WarningConfig>().lambda();
        if(!StringUtils.isEmpty(name)){
            lambda.eq(WarningConfig::getName, name);
        }

        if(!StringUtils.isEmpty(warnType)){
            lambda.eq(WarningConfig::getWarnType, warnType);
        }

        if( id != null ){
            lambda.ne(WarningConfig::getId, id);
        }
        return this.list(lambda);
    }

    @Override
    public List<WarningConfig> getByWarnType(String warnType) {
        return this.list(new QueryWrapper<WarningConfig>().lambda().eq(WarningConfig::getWarnType, warnType));
    }

    @Override
    public void check(String name, String warnNo, String warnType, Long id) {
       /* List<WarningConfig> warnList = getByName(name);
        if (!CollectionUtils.isEmpty(warnList) && !ObjectUtils.isEmpty(id)) {
            warnList = warnList.stream().filter(warningConfig -> !warningConfig.getId().equals(id)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(warnList)) {
            throw new BaseException(EyeStatus.WARN_NAME_EXIST_ERROR);
        }

      *//*  List<WarningConfig> list = this.list(new QueryWrapper<WarningConfig>().lambda().eq(WarningConfig::getWarnNo, warnNo));
        if (!CollectionUtils.isEmpty(warnList) && !ObjectUtils.isEmpty(id)) {
            list = list.stream().filter(warningConfig -> !warningConfig.getId().equals(id)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(list)) {
            throw new BaseException(EyeStatus.WARN_EXIST_ERROR);
        }*//*
        List<WarningConfig> typeList = this.getByWarnType(warnType);
        if (!CollectionUtils.isEmpty(warnList) && !ObjectUtils.isEmpty(id)) {
            typeList = typeList.stream().filter(warningConfig -> !warningConfig.getId().equals(id)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(typeList)) {
            throw new BaseException(500, "当前类型不能继续创建");
        }*/

        List<WarningConfig> warnList = getDataByParam(name,null,id);
        if( !CollectionUtils.isEmpty(warnList) ){
            throw new BaseException(EyeStatus.WARN_NAME_EXIST_ERROR);
        }
        List<WarningConfig> warnTypeList = getDataByParam(name,warnType,id);
        if (!CollectionUtils.isEmpty(warnTypeList)) {
            throw new BaseException(500, "当前类型不能继续创建");
        }
    }
}
