package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.entity.WarningConfig;
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
public interface WarningConfigService extends IService<WarningConfig> {

    List<WarningConfig> getByName(String name);

    List<WarningConfig> getDataByParam(String name,String warnType,Long id);

    List<WarningConfig> getByWarnType(String warnType);

    void check(String name, String warnNo,String warnType,Long id);
}
