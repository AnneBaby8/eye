package cn.com.citydo.module.screen.service.impl;


import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.Status;
import cn.com.citydo.module.screen.dto.NoticeDTO;
import cn.com.citydo.module.screen.entity.EventWarning;
import cn.com.citydo.module.screen.entity.HuaweiAlarmEventFocusGroup;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import cn.com.citydo.module.screen.mapper.EventWarningMapper;
import cn.com.citydo.module.screen.mapper.HuaweiAlarmEventFocusGroupMapper;
import cn.com.citydo.module.screen.service.EventWarningService;
import cn.com.citydo.module.screen.service.HuaweiAlarmEventFocusGroupService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事件预警 服务实现类
 *
 * @author blackcat
 * @since 2021-05-12
 */
@Service
public class HuaweiAlarmEventFocusGroupServiceImpl extends ServiceImpl<HuaweiAlarmEventFocusGroupMapper, HuaweiAlarmEventFocusGroup> implements HuaweiAlarmEventFocusGroupService {


}
