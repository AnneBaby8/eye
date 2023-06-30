package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.dto.WarningPolicyDTO;
import cn.com.citydo.module.core.entity.WarningPolicy;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 预警配置表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
public interface WarningPolicyService extends IService<WarningPolicy> {

    List<WarningPolicy> getByConfigId(Long configId);

    boolean set(WarningPolicyDTO warningPolicyDTO);

    boolean isWarn(WarnTypeEnum warnTypeEnum);

}
