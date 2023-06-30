package cn.com.citydo.module.data.excel.listener;

import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.entity.User;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.UserService;
import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.core.entity.DeviceGridMember;
import cn.com.citydo.module.core.service.DeviceGridMemberService;
import cn.com.citydo.module.core.service.DeviceService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/6/1 9:36
 * @version: 1.0
 * @description:
 */
@Slf4j
public class ExcelDeviceListener extends AnalysisEventListener<DeviceWriter> {



    private static final int BATCH_COUNT = 1000;

    private final List<DeviceWriter> list = new ArrayList<>();


    private DeviceService deviceService;

    private DeviceGridMemberService deviceGridMemberService;

    private DepartmentService departmentService;

    private UserService userService;

    public ExcelDeviceListener(DeviceService deviceService, DeviceGridMemberService deviceGridMemberService, DepartmentService departmentService, UserService userService) {
        this.deviceService = deviceService;
        this.deviceGridMemberService = deviceGridMemberService;
        this.departmentService = departmentService;
        this.userService = userService;
    }

    @Override
    public void invoke(DeviceWriter data, AnalysisContext analysisContext) {
        if (data == null) {
            return;
        }
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveOrUpdateData(list);
            list.clear();
        }
    }


    private void saveOrUpdateData(List<DeviceWriter> list) {
        for (DeviceWriter deviceWriter:list) {
            Device device = new Device();
            BeanUtils.copyProperties(deviceWriter,device);
            String de = deviceWriter.getDe();
            String[] split = de.split(",");
            device.setLongitude(split[0]);
            if(split.length == 2){
                device.setLatitude(split[1]);
            }
            Long gridId = Long.valueOf(deviceWriter.getGrid());
            Department department = departmentService.getById(gridId);
            if(department == null){
                log.error("网格id[{}]", gridId);
                throw new RuntimeException("网格id错误"+gridId);
            }
            device.setGridId(gridId);
            String area = deviceWriter.getArea();
            device.setAreaId(getDepartment(area).getId());
            device.setStreetId(getDepartment(deviceWriter.getStreet()).getId());
            device.setSocialId(getDepartment(deviceWriter.getSocial()).getId());
            deviceService.save(device);

            DeviceGridMember entity = new DeviceGridMember();
            entity.setDeviceId(device.getId());
            String username = deviceWriter.getUsername();
            User user = userService.getOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
            if(user == null){
                log.error("当前用户不存在[{}]", username);
                throw new RuntimeException("当前用户不存在"+username);
            }
            entity.setGridMemberId(user.getId());
            deviceGridMemberService.save(entity);
        }

    }

    private Department getDepartment(String name) {
        Department department = departmentService.getOne(new QueryWrapper<Department>().lambda().eq(Department::getName, name));
        if(department == null){
            log.error("[{}]", name);
            throw new RuntimeException("部门不存在"+name);
        }
        return department;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveOrUpdateData(list);
        log.info("所有人员数据存储完成-------------------->");
    }


    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            log.error("第{}行，第{}列解析异常，数据为:{}", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex(),
                    excelDataConvertException.getCellData());
        }
    }




}
