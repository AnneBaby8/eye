package cn.com.citydo.module.core.service.impl;

import cn.com.citydo.common.enums.DepartmentGradeEnum;
import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import cn.com.citydo.module.core.dto.CancleBindDTO;
import cn.com.citydo.module.core.dto.DeviceBindDTO;
import cn.com.citydo.module.core.dto.DeviceBindQUERY;
import cn.com.citydo.module.core.entity.DeviceGridMember;
import cn.com.citydo.module.core.mapper.DeviceGridMemberMapper;
import cn.com.citydo.module.core.service.DeviceGridMemberService;
import cn.com.citydo.module.core.vo.DeviceBindVO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备关联网格员 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
@Slf4j
@Service
public class DeviceGridMemberServiceImpl extends ServiceImpl<DeviceGridMemberMapper, DeviceGridMember> implements DeviceGridMemberService {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 设备绑定
     * @param deviceBindDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bind(DeviceBindDTO deviceBindDTO) {
        log.info("设备绑定，入参为：[{}]", JSONObject.toJSONString(deviceBindDTO));
        //网格员
        List<Long> gridMemberIdList = deviceBindDTO.getGridMemberIdList();
        //设备
        List<Long> deviceIdList = deviceBindDTO.getDeviceIdList();
        //逻辑删除数据
        baseMapper.deleteByDeviceId(deviceIdList);

        for (Long gridMemberId : gridMemberIdList) {
            for (Long deviceId : deviceIdList) {
            //新增
            DeviceGridMember deviceGridMember = new DeviceGridMember();
            deviceGridMember.setDeviceId(deviceId);
            deviceGridMember.setGridMemberId(gridMemberId);
            this.save(deviceGridMember);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public  IPage<DeviceBindVO> record(DeviceBindQUERY query) {
        log.info("开始查询绑定记录，入参为：[{}]", JSONObject.toJSONString(query));
        Long departmentId = query.getDepartmentId();
        departmentId = departmentService.dataHandle(departmentId);
        if(departmentId != null){
            Map<String, Department> parentDepartments = departmentService.getParentDepartments(departmentId);
            if( parentDepartments.containsKey(DepartmentGradeEnum.GRID.getCode()) ){
                query.setGridId(parentDepartments.get(DepartmentGradeEnum.GRID.getCode()).getId());
            }else if( parentDepartments.containsKey(DepartmentGradeEnum.SOCIAL.getCode()) ){
                query.setSocialId(parentDepartments.get(DepartmentGradeEnum.SOCIAL.getCode()).getId());
            }else if( parentDepartments.containsKey(DepartmentGradeEnum.STREET.getCode()) ){
                query.setStreetId(parentDepartments.get(DepartmentGradeEnum.STREET.getCode()).getId());
            }else if( parentDepartments.containsKey(DepartmentGradeEnum.AREA.getCode())){
                query.setAreaId(parentDepartments.get(DepartmentGradeEnum.AREA.getCode()).getId());
            }
        }
        //分页数据
        IPage<DeviceBindVO> page= new Page<DeviceBindVO>(query.getPageNo(),query.getPageSize());
        IPage<DeviceBindVO> pageList = baseMapper.selectBindRecordList(page,query);
        List<DeviceBindVO> records = pageList.getRecords();

        if( !CollectionUtils.isEmpty(records) ){
            for (DeviceBindVO deviceBindVO : records) {
                //获取地址信息
                DepartmentVO partDepartmentName = departmentService.getPartDepartmentName(deviceBindVO.getAreaId(), deviceBindVO.getStreetId(), deviceBindVO.getSocialId());
                deviceBindVO.setAreaName(partDepartmentName.getAreaName());
                deviceBindVO.setStreetName(partDepartmentName.getStreetName());
                deviceBindVO.setSocialName(partDepartmentName.getSocialName());
            }
        }
        return pageList;
    }

    /**
     * 解绑
     * @param cancleBindDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancle(CancleBindDTO cancleBindDTO) {
        log.info("开始解绑网格员，入参为：[{}]", JSONObject.toJSONString(cancleBindDTO));
        QueryWrapper<DeviceGridMember> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.lambda().eq(DeviceGridMember::getDeviceId,cancleBindDTO.getDeviceId()).eq(DeviceGridMember::getGridMemberId,cancleBindDTO.getGridMemberId());
        return this.remove(deleteWrapper);
    }

    @Override
    public List<DeviceGridMember> getByDeviceId(Long deviceId) {
        return this.list(new QueryWrapper<DeviceGridMember>().lambda().eq(DeviceGridMember::getDeviceId,deviceId));
    }

    @Override
    public List<DeviceGridMember> getByGridMemberId(Long gridMemberId, Long deviceId) {
        return this.list(new QueryWrapper<DeviceGridMember>().lambda().eq(DeviceGridMember::getGridMemberId,gridMemberId)
        .eq(DeviceGridMember::getDeviceId,deviceId));
    }
}
