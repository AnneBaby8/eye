package cn.com.citydo.module.screen.service.impl;

import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.screen.entity.EventWarningDetail;
import cn.com.citydo.module.screen.mapper.EventWarningDetailMapper;
import cn.com.citydo.module.screen.service.EventWarningDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 事件预警详情表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-18
 */
@Service
public class EventWarningDetailServiceImpl extends ServiceImpl<EventWarningDetailMapper, EventWarningDetail> implements EventWarningDetailService {

    @Override
    public List<EventWarningDetail> queryByEventWarningId(Long eventWarningId) {
        QueryWrapper<EventWarningDetail> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.lambda().eq(EventWarningDetail::getEventWarningId, eventWarningId);
        return baseMapper.selectList(objectQueryWrapper);
    }
}
