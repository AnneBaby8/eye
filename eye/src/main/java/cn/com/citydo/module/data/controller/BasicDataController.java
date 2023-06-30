package cn.com.citydo.module.data.controller;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.module.basic.service.*;
import cn.com.citydo.module.core.service.DeviceGridMemberService;
import cn.com.citydo.module.core.service.DeviceService;
import cn.com.citydo.module.data.excel.listener.AccountWriter;
import cn.com.citydo.module.data.excel.listener.DeviceWriter;
import cn.com.citydo.module.data.excel.listener.ExcelDeviceListener;
import cn.com.citydo.module.data.excel.listener.ExcelEventListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author blackcat
 * @create 2021/5/31 14:01
 * @version: 1.0
 * @description:
 */
@Api(tags = "基础数据导入")
@Slf4j
@RestController
@RequestMapping("/api/data")
public class BasicDataController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encrypt;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @ApiImplicitParams({
            @ApiImplicitParam(value = "上传的excel文件", name = "file", required = true, dataType = "__file"),
    })
    @ApiOperation(value = "excel模板导入")
    @PostMapping(value = "/importAccount")
    public ApiResponse<String> importAccount(@RequestParam("file") MultipartFile file) throws IOException {

        ExcelEventListener listener = new ExcelEventListener(userService,encrypt,userRoleService,userDepartmentService,roleService,departmentService);
        //字段校验
        try {
            ExcelReaderSheetBuilder builder = EasyExcel.read(file.getInputStream(), AccountWriter.class, listener).extraRead(CellExtraTypeEnum.MERGE)
                    .sheet(0)
                    .headRowNumber(1);
            builder.doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ApiResponse.ofSuccess();
    }

    /**
     * 处理账号数据（接口2）
     * @param file
     * @return
     */
    @ApiOperation(value = "上传单个文件", notes = "上传单个文件", httpMethod="POST" ,consumes="multipart/form-data")
    @PostMapping(value = "upload")
    @ResponseBody
    public ApiResponse<String> singleFileUpLoad(@ApiParam(value = "文件" , required = true) MultipartFile file) {

        ExcelEventListener listener = new ExcelEventListener(userService,encrypt,userRoleService,userDepartmentService,roleService,departmentService);
        //字段校验
        try {
            ExcelReaderSheetBuilder builder = EasyExcel.read(file.getInputStream(), AccountWriter.class, listener).extraRead(CellExtraTypeEnum.MERGE)
                    .sheet(0)
                    .headRowNumber(1);
            builder.doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.ofSuccess();
    }


    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceGridMemberService deviceGridMemberService;

    @ApiImplicitParams({
            @ApiImplicitParam(value = "上传的excel文件", name = "file", required = true, dataType = "__file"),
    })
    @ApiOperation(value = "excel模板导入")
    @PostMapping(value = "/importDevice")
    public ApiResponse<String> importDevice(@RequestParam("file") MultipartFile file) throws IOException {
        ExcelDeviceListener listener = new ExcelDeviceListener(deviceService,deviceGridMemberService,departmentService,userService);
        //字段校验
        try {
            ExcelReaderSheetBuilder builder = EasyExcel.read(file.getInputStream(), DeviceWriter.class, listener).extraRead(CellExtraTypeEnum.MERGE)
                    .sheet(0)
                    .headRowNumber(1);
            builder.doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.ofSuccess();
    }

    @ApiOperation(value = "删除处置部门账户")
    @PostMapping(value = "/delete/Account")
    public ApiResponse<String> deleteAccount(){
        userService.deleteAccount();
        return ApiResponse.ofSuccess();
    }
}
