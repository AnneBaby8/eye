package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.module.core.dto.PolicyDTO;
import cn.com.citydo.module.core.dto.WarningPolicyDTO;
import cn.com.citydo.module.core.entity.WarningConfig;
import cn.com.citydo.module.core.entity.WarningPolicy;
import cn.com.citydo.module.core.mapper.WarningPolicyMapper;
import cn.com.citydo.module.core.service.WarningConfigService;
import cn.com.citydo.module.core.service.WarningPolicyService;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 预警配置表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Service
public class WarningPolicyServiceImpl extends ServiceImpl<WarningPolicyMapper, WarningPolicy> implements WarningPolicyService {


    @Autowired
    private WarningConfigService warningConfigService;

    @Override
    public List<WarningPolicy> getByConfigId(Long configId) {
        return this.list(new QueryWrapper<WarningPolicy>().lambda().eq(WarningPolicy::getConfigId, configId));
    }


    @Transactional
    @Override
    public boolean set(WarningPolicyDTO warningPolicyDTO) {
        Long configId = warningPolicyDTO.getConfigId();
        List<PolicyDTO> policyList = warningPolicyDTO.getPolicyList();
        List<WarningPolicy> warnList = this.getByConfigId(configId);
        if (!CollectionUtils.isEmpty(warnList)) {
            List<Long> idList = warnList.stream().map(WarningPolicy::getId).collect(Collectors.toList());
            boolean delete = this.removeByIds(idList);
            if (!delete) {
                throw new BaseException(EyeStatus.UPDATE_ERROR);
            }
        }
        List<WarningPolicy> list = new ArrayList<>();
        for (PolicyDTO policyDTO : policyList) {
            WarningPolicy warningPolicy = new WarningPolicy();
            warningPolicy.setConfigId(configId);
            BeanUtils.copyProperties(policyDTO, warningPolicy);
            list.add(warningPolicy);
        }
        return this.saveBatch(list);
    }

    @Override
    public boolean isWarn(WarnTypeEnum warnTypeEnum) {
        boolean isWarn = false;
        List<WarningConfig> configs = warningConfigService.getByWarnType(warnTypeEnum.getKey());
        if (!CollectionUtils.isEmpty(configs)) {
            Long configId = configs.get(0).getId();
            List<WarningPolicy> policyList = this.getByConfigId(configId);
            if (!CollectionUtils.isEmpty(policyList)) {
                for (WarningPolicy warningPolicy : policyList) {
                    Date effectStartTime = warningPolicy.getEffectStartTime();//生效开始时间
                    Date effectEndTime = warningPolicy.getEffectEndTime();//生效结束时间
                    if(!ObjectUtils.isEmpty(effectEndTime)){
                        effectEndTime = DateUtil.endOfDay(effectEndTime);//这个月的第几天
                    }
                    String warnStartTime = warningPolicy.getWarnStartTime();
                    String warnEndTime = warningPolicy.getWarnEndTime();
                    Date nowRight = new Date();
                    if (nowRight.after(effectStartTime) && nowRight.before(effectEndTime)) {
                        int hour = DateUtil.hour(nowRight, true);
                        int minute = DateUtil.minute(nowRight);
                        //如果两个时间段都不配置,则默认全天
                        if (StringUtils.isBlank(warnStartTime) && StringUtils.isBlank(warnEndTime)) {
                            isWarn = true;
                            break;
                        }
                        if (StringUtils.isBlank(warnStartTime) || StringUtils.isBlank(warnEndTime)) {
                            continue;
                        }
                        if (hour > Integer.valueOf(warnStartTime.substring(0, warnStartTime.length() - 3)) && hour < Integer.valueOf(warnEndTime.substring(0, warnEndTime.length() - 3))) {
                            isWarn = true;
                            break;
                        }
                        if (hour == Integer.valueOf(warnStartTime.substring(0, warnStartTime.length() - 3)) && minute >= Integer.valueOf(warnStartTime.substring(warnStartTime.length() - 2, warnStartTime.length()))) {
                            isWarn = true;
                            break;
                        }

                        if (hour == Integer.valueOf(warnEndTime.substring(0, warnEndTime.length() - 3)) && minute <= Integer.valueOf(warnEndTime.substring(warnEndTime.length() - 2, warnEndTime.length()))) {
                            isWarn = true;
                            break;
                        }
                    }
                }
            }
        }
        return isWarn;
    }

}
