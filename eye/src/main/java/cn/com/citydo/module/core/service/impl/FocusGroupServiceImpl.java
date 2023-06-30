package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.mapper.DepartmentMapper;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.core.dto.FocusGroupQUERY;
import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.core.entity.FocusGroup;
import cn.com.citydo.module.core.mapper.DeviceMapper;
import cn.com.citydo.module.core.mapper.FocusGroupMapper;
import cn.com.citydo.module.core.service.FocusGroupService;
import cn.com.citydo.module.core.vo.FocusGroupVO;
import cn.com.citydo.module.screen.entity.EventWarning;
import cn.com.citydo.module.screen.entity.EventWarningDetail;
import cn.com.citydo.module.screen.enums.EventAlarmTypeEnum;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import cn.com.citydo.module.screen.mapper.DataOverviewMapper;
import cn.com.citydo.module.screen.service.EventWarningDetailService;
import cn.com.citydo.module.screen.service.EventWarningService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 重点人群服务实现类
 * </p>
 *
 * @author shaodl
 * @since 2021-06-22
 */
@Slf4j
@Service
public class FocusGroupServiceImpl extends ServiceImpl<FocusGroupMapper, FocusGroup> implements FocusGroupService {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public IPage<FocusGroupVO> getByQuery(FocusGroupQUERY query) {
        query.setZoneId(departmentService.dataHandle(query.getZoneId()));
        IPage<FocusGroupVO> resultPage = new Page<>(query.getPageNo(), query.getPageSize());
        return this.baseMapper.selectFocusGroup(resultPage, query);
    }

}
