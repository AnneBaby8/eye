package cn.com.citydo.module.screen.service.impl;

import cn.com.citydo.common.Consts;
import cn.com.citydo.common.enums.DepartmentGradeEnum;
import cn.com.citydo.common.enums.SelfDisposalTypeEnum;
import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.entity.UserDepartment;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.RolePermissionService;
import cn.com.citydo.module.basic.service.UserDepartmentService;
import cn.com.citydo.module.basic.service.UserRoleService;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.service.DeviceService;
import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.core.vo.OverviewWarnTypeVO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import cn.com.citydo.module.screen.enums.CycleTypeEnum;
import cn.com.citydo.module.screen.mapper.DataOverviewMapper;
import cn.com.citydo.module.screen.service.DataOverviewService;
import cn.com.citydo.module.screen.service.EventWarningService;
import cn.com.citydo.module.screen.vo.CycleDataVO;
import cn.com.citydo.module.screen.vo.CycleDetailDataVO;
import cn.com.citydo.module.screen.vo.EventCountVO;
import cn.com.citydo.module.screen.vo.OverviewDataVO;
import cn.com.citydo.utils.BaseTreeNode;
import cn.com.citydo.utils.SecurityUtil;
import cn.com.citydo.utils.TreeNodeUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  数据概览
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/7 10:28
 * @Version 1.0
*/
@Slf4j
@Service
public class DataOverviewServiceImpl  implements DataOverviewService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private EventWarningService eventWarningService;

    @Autowired
    private DataOverviewMapper baseMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Override
    public OverviewDataVO data(OverviewDataDTO overviewDataDTO) {
        log.info("开始查询首页数据，入参为：[{}]", JSONObject.toJSONString(overviewDataDTO));
        OverviewDataVO overviewDataVO = new OverviewDataVO();

        if(overviewDataDTO.getDepartmentId() != null){
            overviewDataDTO = this.getDataOverByDepartmentId(overviewDataDTO);
        }

        //AI事件趋势
        List<EventCountVO> eventCountVOList = this.selectPeriodData(overviewDataDTO);
        overviewDataVO.setEventCountVOList(eventCountVOList);

        //AI事件统计
        List<CycleDataVO> cycleDataList = this.getCycleDataList(overviewDataDTO);
        overviewDataVO.setCycleDataVOList(cycleDataList);

        //AI实时事件
        List<OverviewDeviceVO> overviewDeviceVOSList = eventWarningService.selectRealTimeEventsData(overviewDataDTO);
        overviewDataVO.setOverviewDeviceVOSList(overviewDeviceVOSList);
        return overviewDataVO;
    }

    /**
     * 计算环比率
     * @param overviewDataDTO
     * @return
     */
    public List<CycleDataVO> getCycleDataList(OverviewDataDTO overviewDataDTO){
        log.info("数据概览-事件统计，入参为：[{}]",JSONObject.toJSONString(overviewDataDTO));
        List<CycleDataVO> cycleDataVOList = new ArrayList<>();

        //日
        overviewDataDTO.setChainType(CycleTypeEnum.DAY.getKey());
        CycleDetailDataVO dayData = this.selectCycleData(overviewDataDTO);
        cycleDataVOList.add(new CycleDataVO(dayData,CycleTypeEnum.DAY.getKey()));

        //周
        overviewDataDTO.setChainType(CycleTypeEnum.WEEK.getKey());
        CycleDetailDataVO weekData = this.selectCycleData(overviewDataDTO);
        cycleDataVOList.add(new CycleDataVO(weekData,CycleTypeEnum.WEEK.getKey()));

        //月
        overviewDataDTO.setChainType(CycleTypeEnum.MONTH.getKey());
        CycleDetailDataVO monthData = this.selectCycleData(overviewDataDTO);
        cycleDataVOList.add(new CycleDataVO(monthData,CycleTypeEnum.MONTH.getKey()));

        //年
        overviewDataDTO.setChainType(CycleTypeEnum.YEAR.getKey());
        CycleDetailDataVO yearData = this.selectCycleData(overviewDataDTO);
        cycleDataVOList.add(new CycleDataVO(yearData,CycleTypeEnum.YEAR.getKey()));
        return cycleDataVOList;
    }

    /**
     * 数据概览-设备列表
     * @param overviewDataDTO
     * @return
     */
    @Override
    public IPage<OverviewDeviceVO> getOverviewDeviceData(OverviewDataDTO overviewDataDTO){
        if( overviewDataDTO == null ){
            return null;
        }
        if(overviewDataDTO.getDepartmentId() != null){
            overviewDataDTO = this.getDataOverByDepartmentId(overviewDataDTO);
        }

        return deviceService.selectOverviewDeviceData(overviewDataDTO);
    }

    @Override
    public BaseTreeNode selectDepartmentData() {
        List<BaseTreeNode> nodeList = new ArrayList<>();
        List<DepartmentVO> list = departmentService.getPartDepartments();
        departmentService.getNodeList(nodeList, list);
        UserPrincipal user = SecurityUtil.getCurrentUser();
        Long parentId = 0L;
        List<UserDepartment> departmentList = userDepartmentService.getByUserId(user.getId());
        if(!CollectionUtils.isEmpty(departmentList)){
            parentId =  departmentList.get(0).getDepartmentId();
        }
        return  TreeNodeUtil.assembleTreeById(parentId, nodeList);
    }

    /**
     * 查询近七日数据
     * @param overviewDTO 入参
     * @return
     */
    public List<EventCountVO> selectPeriodData(OverviewDataDTO overviewDTO) {
        log.info("数据概览-事件趋势，入参为：[{}]", JSONObject.toJSONString(overviewDTO));
        return baseMapper.selectPeriodData(overviewDTO);
    }
    /**
     * 查询事件/误报（上周期+下周期）
     * @param overviewDTO
     * @return
     */
    public CycleDetailDataVO selectCycleData(OverviewDataDTO overviewDTO) {
        CycleDetailDataVO cycleDetailDataVO =  new CycleDetailDataVO();
        //事件
        cycleDetailDataVO.setEventNextCycleCount(baseMapper.selectNextCycleData(overviewDTO));
        cycleDetailDataVO.setEventLastCycleCount(baseMapper.selectLastCycleData(overviewDTO));

        //误报
        overviewDTO.setSelfDisposalType(SelfDisposalTypeEnum.FALSE_ALARM.getCode());
        cycleDetailDataVO.setFalseAlarmNextCycleCount(baseMapper.selectNextCycleData(overviewDTO));
        cycleDetailDataVO.setFalseAlarmLastCycleCount(baseMapper.selectLastCycleData(overviewDTO));
        overviewDTO.setSelfDisposalType(Consts.empty);
        return this.getCycleRate(cycleDetailDataVO);
    }

    /**
     * 查询环比率
     * @param cycleDetailDataVO
     * @return
     */
    public CycleDetailDataVO getCycleRate(CycleDetailDataVO cycleDetailDataVO) {
        Long eventLastCycleCount = cycleDetailDataVO.getEventLastCycleCount();
        Long eventNextCycleCount = cycleDetailDataVO.getEventNextCycleCount();

        Long falseAlarmLastCycleCount = cycleDetailDataVO.getFalseAlarmLastCycleCount();
        Long falseAlarmNextCycleCount = cycleDetailDataVO.getFalseAlarmNextCycleCount();

        //事件环比率
        Long eventDiffCount = eventNextCycleCount - eventLastCycleCount;
        if( eventDiffCount == 0 || eventLastCycleCount == 0){
            cycleDetailDataVO.setEventCycleRate(BigDecimal.ZERO);
        }else{
            BigDecimal eventCycleRate = BigDecimal.valueOf(eventDiffCount).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(eventLastCycleCount), 0, BigDecimal.ROUND_HALF_EVEN);
            cycleDetailDataVO.setEventCycleRate(eventCycleRate);
        }
        //误报环比率
        Long falseAlarmDiffCount = falseAlarmNextCycleCount - cycleDetailDataVO.getFalseAlarmLastCycleCount();
        if( falseAlarmDiffCount == 0 || falseAlarmLastCycleCount == 0){
            cycleDetailDataVO.setFalseAlarmCycleRate(BigDecimal.ZERO);
        }else{
            BigDecimal eventCycleRate = BigDecimal.valueOf(falseAlarmDiffCount).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(falseAlarmLastCycleCount), 0, BigDecimal.ROUND_HALF_EVEN);
            cycleDetailDataVO.setFalseAlarmCycleRate(eventCycleRate);
        }

        //误报率

        if( falseAlarmNextCycleCount == 0 || eventNextCycleCount == 0){
            cycleDetailDataVO.setFalseAlarmRate(BigDecimal.ZERO);
        }else{
            BigDecimal eventCycleRate = BigDecimal.valueOf(falseAlarmNextCycleCount).divide(BigDecimal.valueOf(eventNextCycleCount), 0, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100));
            cycleDetailDataVO.setFalseAlarmRate(eventCycleRate);
        }

        return cycleDetailDataVO;
    }
    @Override
    public OverviewDataDTO getDataOverByDepartmentId(OverviewDataDTO overviewDTO){
        if(overviewDTO.getDepartmentId() != null){
            Map<String, Department> parentDepartments = departmentService.getParentDepartments(overviewDTO.getDepartmentId());
            if (parentDepartments.containsKey(DepartmentGradeEnum.GRID.getCode())) {
                overviewDTO.setGridId(parentDepartments.get(DepartmentGradeEnum.GRID.getCode()).getId());
            }else if( parentDepartments.containsKey(DepartmentGradeEnum.SOCIAL.getCode()) ){
                overviewDTO.setSocialId(parentDepartments.get(DepartmentGradeEnum.SOCIAL.getCode()).getId());
            } else if( parentDepartments.containsKey(DepartmentGradeEnum.STREET.getCode()) ){
                overviewDTO.setStreetId(parentDepartments.get(DepartmentGradeEnum.STREET.getCode()).getId());
            }else if( parentDepartments.containsKey(DepartmentGradeEnum.AREA.getCode()) ){
                overviewDTO.setAreaId(parentDepartments.get(DepartmentGradeEnum.AREA.getCode()).getId());
            }
        }
        return overviewDTO;
    }

    @Override
    public List<OverviewWarnTypeVO> getWarnType(OverviewDataDTO overviewDataDTO) {
        if( overviewDataDTO == null ){
            return null;
        }
        if(overviewDataDTO.getDepartmentId() != null){
            overviewDataDTO = this.getDataOverByDepartmentId(overviewDataDTO);
        }

        List<OverviewWarnTypeVO> overviewWarnTypeVOS = deviceService.selectWarnTypeDataList(overviewDataDTO);
        return overviewWarnTypeVOS;
    }
}
