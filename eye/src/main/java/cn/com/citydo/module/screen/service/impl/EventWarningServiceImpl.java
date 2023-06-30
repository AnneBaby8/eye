package cn.com.citydo.module.screen.service.impl;


import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.Status;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import cn.com.citydo.module.core.inner.ProcessDO;
import cn.com.citydo.module.core.service.DeviceService;
import cn.com.citydo.module.core.service.WarningPolicyService;
import cn.com.citydo.module.core.service.WorkflowNewsService;
import cn.com.citydo.module.core.service.WorkflowService;
import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.screen.dto.NoticeDTO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import cn.com.citydo.module.screen.dto.alarmevent.AlarmEventContentDTO;
import cn.com.citydo.module.screen.dto.alarmevent.CommonInfoDTO;
import cn.com.citydo.module.screen.dto.alarmevent.PicDTO;
import cn.com.citydo.module.screen.dto.alarmevent.PosDTO;
import cn.com.citydo.module.screen.entity.EventWarning;
import cn.com.citydo.module.screen.entity.EventWarningDetail;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;
import cn.com.citydo.module.screen.mapper.EventWarningMapper;
import cn.com.citydo.module.screen.service.EventWarningDetailService;
import cn.com.citydo.module.screen.service.EventWarningService;
import cn.com.citydo.thirdparty.huawei.response.facerepositories.FaceRepositoriesPeople;
import cn.com.citydo.thirdparty.huawei.response.facerepositories.FaceRepositoriesResponse;
import cn.com.citydo.thirdparty.huawei.service.HuaweiApiService;
import cn.com.citydo.utils.FileOperateUtil;
import cn.com.citydo.utils.ImageHandleUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件预警 服务实现类
 *
 * @author blackcat
 * @since 2021-05-12
 */
@Service
@Slf4j
public class EventWarningServiceImpl extends ServiceImpl<EventWarningMapper, EventWarning> implements EventWarningService {

    @Value("${fileserver.url}")
    private String url;

    @Value("${huawei2.peopleAlarmUrl}")
    private String peopleAlarmUrl;

    @Autowired
    private WarningPolicyService warningPolicyService;

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private WorkflowNewsService workflowNewsService;

    @Autowired
    private HuaweiApiService huaweiApiService;

    @Autowired
    private EventWarningDetailService eventWarningDetailService;

    @Autowired
    private DeviceService deviceService;

    @Value("${fileserver.savePath}")
    private String imageSavePath;
    @Value("${fileserver.saveBasePath}")
    private String saveBasePath;
    @Value("${fileserver.savePath}")
    private String savePath;

    @Value("${fileserver.faceHeadSculpture}")
    private String faceHeadSculpture;
    /**
     * 保存推送事件的监测到的图片
     *
     * @param taskId      作业ID
     * @param imageBase64 Base64内容
     * @return 图片存储路径
     */
    private String saveWarnImage(String taskId, String imageBase64) {
        try {
            // 构造图片存储路径
            /*StringBuilder sb = new StringBuilder();
            sb.append(taskId);
            sb.append("-");
            sb.append(DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
            sb.append(".jpeg");*/
            String imageName = ImageHandleUtils.getImageName(taskId);
            // 保存预警图片
            File saveFile = new File(saveBasePath + imageSavePath + imageName);
            FileOperateUtil.saveBase64StringToImageFile(imageBase64, saveFile);
            return imageSavePath + imageName;
        } catch (Exception e) {
            throw new BaseException(Status.ERROR, "数据保存失败");
        }
    }

    @Transactional()
    @Override
    public Long saveNotice(NoticeDTO notice, WarnTypeEnum warnTypeEnum) {

//        List<Device> deviceList = deviceService.getByStreamId(notice.getStreamId());
//        if (CollectionUtils.isEmpty(deviceList)) {
//            log.error("saveNotice 当前设备号【{}】不在系统的设备表,推送参数:[{}]", notice.getStreamId(),notice.toString());
//            throw new BaseException(Status.ERROR, "当前设备号" + notice.getStreamId() + "不在本系统内,拒绝数据");
//        }
        // 构造告警事件信息
        EventWarning entity = new EventWarning();
        BeanUtils.copyProperties(notice, entity);
        // 保存告警图片
        String noticePicPath = notice.getImageBase64();
        if (!WarnTypeEnum.FOCUS_VISIT.equals(warnTypeEnum)) {
            noticePicPath = this.saveWarnImage(notice.getTaskId(), notice.getImageBase64());
        }else{
            String imageName = ImageHandleUtils.getImageName(notice.getTaskId());
           // ImageHandleUtils.download(saveBasePath + savePath,noticePicPath.replaceFirst("192.168.255.2","172.30.1.229"),imageName);
            ImageHandleUtils.download(saveBasePath + savePath,noticePicPath.replaceFirst(peopleAlarmUrl,"http://127.0.0.1:1156"),imageName);

            noticePicPath = savePath + imageName;
        }
        entity.setImageBase64(noticePicPath);
        //TODO 暂时注释掉 图片置空  base64图片太大,该字段置空
        notice.setImageBase64(null);
        entity.setJson(JSONObject.toJSONString(notice));
        entity.setWarnType(warnTypeEnum.getKey());
        // 保存告警事件信息
        boolean save = this.save(entity);
        if (!save) {
            throw new BaseException(Status.ERROR, "数据入库失败");
        }

        EventWarningDetail eventWarningDetail = notice.getEventWarningDetail();
        if(!ObjectUtils.isEmpty(eventWarningDetail)){
            eventWarningDetail.setEventWarningId(entity.getId());
            eventWarningDetailService.save(eventWarningDetail);
        }

        // 是否满足推送条件
        boolean isWarn = warningPolicyService.isWarn(warnTypeEnum);
        boolean process = false;
        String reason = "";
        // 开启流程
        if (isWarn) {
            ProcessDO processDO = workflowService.startProcess(entity);
            process = processDO.getProcess();
            reason = processDO.getReason();
        }
        EventWarning update = new EventWarning();
        update.setId(entity.getId());
        update.setIsWarning(isWarn);
        update.setIsStart(process);
        update.setReason(reason);
        boolean flag = this.updateById(update);
        if (!flag) {
            throw new BaseException(EyeStatus.UPDATE_ERROR);
        }
        return entity.getId();
    }

    @Override
    public List<OverviewDeviceVO> selectRealTimeEventsData(OverviewDataDTO overviewDataDTO) {
        List<OverviewDeviceVO> overviewDeviceVOS = baseMapper.selectRealTimeEventsData(overviewDataDTO);
        for (OverviewDeviceVO overviewDeviceVO : overviewDeviceVOS) {
            DepartmentVO partDepartmentName = departmentService.getPartDepartmentName(overviewDeviceVO.getAreaId(), overviewDeviceVO.getStreetId(), overviewDeviceVO.getSocialId());
            if (partDepartmentName != null) {
                overviewDeviceVO.setAreaName(partDepartmentName.getAreaName());
                overviewDeviceVO.setStreetName(partDepartmentName.getStreetName());
                overviewDeviceVO.setSocialName(partDepartmentName.getSocialName());
            }
            //图片地址拼接
            if (StringUtils.isNotEmpty(overviewDeviceVO.getImageBase64())) {
                if (WarnTypeEnum.CHANNEL.getKey().equals(overviewDeviceVO.getWarnType()) || WarnTypeEnum.GARBAGE.getKey().equals(overviewDeviceVO.getWarnType())
                        || WarnTypeEnum.FOCUS_VISIT.getKey().equals(overviewDeviceVO.getWarnType()) ) {
                    overviewDeviceVO.setImageBase64(url + overviewDeviceVO.getImageBase64());
                }
            }
        }
        return overviewDeviceVOS;
    }

    @Override
    public void saveHuaweiNorthOrientationEvent(AlarmEventContentDTO content) {
        // 获取预警事件信息
        CommonInfoDTO commonInfo = content.getCommonInfo();
        // 构造预警事件信息
        NoticeDTO notice = new NoticeDTO();
        notice.setStreamId(commonInfo.getCameraId());
        notice.setEventType(commonInfo.getAlarmPicGroupName());
        notice.setTaskId(commonInfo.getTaskId());
        notice.setTimestamp(Long.valueOf(commonInfo.getAlarmTime()) / 1000);
        notice.setMessageId(commonInfo.getAlarmId());
        PicDTO pic = content.getPrivateInfo().getPic();
        notice.setImageBase64(pic != null ? pic.getImageUrl() : "");




        // 根据重点人员预警信息中附带的人员名单库ID和人员名单ID信息，调取华为北向外部接口-查询人员名单接口获取重点人员详细信息
        // 构造调取"华为北向外部接口-查询人员名单"的查询条件
        Map<String, String> params = new HashMap() {
            {
                put("repositorIds", commonInfo.getBlkgrpId());
                put("ids", commonInfo.getBlacklistId());
            }
        };

        EventWarningDetail eventWarningDetail = new EventWarningDetail();
        // 查询重点人员数据
        FaceRepositoriesResponse faceRepositoriesResponse = huaweiApiService.queryPeople(params);
        List<FaceRepositoriesPeople> faceRepositoriesPeopleList = faceRepositoriesResponse.getPeopleList();
        if (faceRepositoriesPeopleList.size() > 0) {
            log.info("开始保存人员详细信息:" + content);
            // 默认取第一个
            FaceRepositoriesPeople people = faceRepositoriesPeopleList.get(0);
            log.info("推送的人员信息：[{}]" ,JSONObject.toJSONString(people));
            // 保存重点人员监测数据
            // 保存预警重点人员详细信息
            eventWarningDetail.setUsername(people.getName());
            eventWarningDetail.setCredentialNumber(people.getCredentialNumber());
            if (people.getFaceList() != null && people.getFaceList().size() > 0) {
                String url = people.getFaceList().get(0).getUrl();
                String imageName = ImageHandleUtils.getImageName(notice.getTaskId());
                ImageHandleUtils.download(saveBasePath + faceHeadSculpture,url.replaceFirst(peopleAlarmUrl,"http://127.0.0.1:1156"),imageName);
                eventWarningDetail.setPicture( faceHeadSculpture + imageName);
            }
            eventWarningDetail.setSex(people.getGender());
            //客户端没有联系方式，暂取职业
            eventWarningDetail.setPhone(people.getOccupation());
            //eventWarningDetail.setTag(people.getTag());
            // 客户端无地址字段,暂取备注
            eventWarningDetail.setAddress(people.getDescription());
//            eventWarningDetail.setTrack();
            // 客户端无标签字段,暂取国籍
            eventWarningDetail.setTag(people.getCountry());
            eventWarningDetail.setCapture(pic != null ? pic.getImageUrl() : "");
            if (content.getPrivateInfo().getMetaDataDTO() != null) {
                eventWarningDetail.setSimilarity(String.valueOf(content.getPrivateInfo().getMetaDataDTO().getScr()));
                //获取人脸坐标
                PosDTO pos = content.getPrivateInfo().getMetaDataDTO().getPos();
                log.info("人脸坐标为：[{}]",JSONObject.toJSONString(pos));
                if( pos != null ){
                    eventWarningDetail.setPicTop(pos.getTop());
                    eventWarningDetail.setPicLeft(pos.getLeft());
                    eventWarningDetail.setPicRight(pos.getRight());
                    eventWarningDetail.setPicBottom(pos.getBottom());
                }
            }
          //  eventWarningDetailService.save(eventWarningDetail);
        } else {
            throw new BaseException(Status.ERROR, "根据人员名单库ID和人员名单ID，查找不到重点人员信息");
        }

        notice.setEventWarningDetail(eventWarningDetail);
        if(commonInfo.getAlarmPicGroupName().contains("重点上访")) {
            log.info("保存重点上访人员事件数据......");
            // 保存预警事件，需要根据事件类型来区分
             this.saveNotice(notice, WarnTypeEnum.FOCUS_VISIT);
        } else if(commonInfo.getAlarmPicGroupName().contains("空巢老人")) {
            log.info("保存空巢老人事件人员数据......");
            // 保存预警事件，需要根据事件类型来区分
            this.saveNotice(notice, WarnTypeEnum.EMPTY_NESTER);
        } else if(commonInfo.getAlarmPicGroupName().contains("帮扶人群")) {
            log.info("保存帮扶人群人员事件数据......");
            // 保存预警事件，需要根据事件类型来区分
            this.saveNotice(notice, WarnTypeEnum.HELP_GROUP);
        } else {
            return;
        }

    }

}
