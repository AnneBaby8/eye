package cn.com.citydo.common.frame;

import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.PageDTO;
import cn.com.citydo.common.valid.InsertGroup;
import cn.com.citydo.common.valid.UpdateGroup;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.utils.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/3/28 23:05
 * @version: 1.0
 * @description:
 */
public abstract class BaseController<T extends BaseEntity, S extends IService<T>, DTO, VO,QUERYDTO extends PageDTO> {

    @Autowired
    protected S baseService;

    @Autowired
    protected ConversionService conversionService;

    protected Class<T> model = currentModelClass();

    protected Class<VO> value = currentVOClass();

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
    }

    protected Class<VO> currentVOClass() {
        return (Class<VO>) ReflectionKit.getSuperClassGenericType(getClass(), 3);
    }

    @ApiOperation(value = "查询详情")
    @GetMapping("/get")
    public ApiResponse<T> get(Long id) {
        return ApiResponse.ofSuccess(baseService.getById(id));
    }

    @ApiOperation(value = "分页")
    @PostMapping("page")
    public ApiResponse<IPage<VO>> page(@RequestBody QUERYDTO query ) {
        Integer pageNo = query.getPageNo();
        Integer pageSize = query.getPageSize();
        T entity = toEntity(query);
        IPage<T> page = new Page<>(pageNo, pageSize);
        IPage<T> iPage = baseService.page(page, new QueryWrapper<>(entity));
        IPage<VO> resultPage = pageConver(iPage);
        return ApiResponse.ofSuccess(resultPage);
    }



    @ApiOperation(value = "新增")
    @PostMapping("create")
    public ApiResponse<Boolean> create(@Validated(InsertGroup.class) @RequestBody  DTO entityDTO) {
        T entity = toEntityConver(entityDTO);
        UserPrincipal user = SecurityUtil.getCurrentUser();
        entity.setCreator(user.getId().toString());
        entity.setUpdator(user.getId().toString());
        return ApiResponse.ofSuccess(baseService.save(entity));
    }

    @ApiOperation(value = "更新")
    @PostMapping("update")
    public ApiResponse<Boolean> update(@Validated(UpdateGroup.class) @RequestBody DTO entityDTO) {
        T entity = toEntityConver(entityDTO);
        if(ObjectUtils.isEmpty(baseService.getById(entity.getId()))){
            throw  new BaseException(EyeStatus.ID_ERROR);
        }
        UserPrincipal user = SecurityUtil.getCurrentUser();
        entity.setUpdator(user.getId().toString());
        return ApiResponse.ofSuccess(baseService.updateById(entity));
    }

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    public ApiResponse<Boolean> delete(@RequestBody DeleteDTO deleteDTO) {
        Long id = deleteDTO.getId();
        if(ObjectUtils.isEmpty(baseService.getById(id))){
           throw  new BaseException(EyeStatus.ID_ERROR);
        }
        return ApiResponse.ofSuccess(baseService.removeById(id));
    }

    protected  T toEntity(QUERYDTO query){
        T t = null;
        try {
            t = model.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (ObjectUtils.isEmpty(t)) {
            throw  new BaseException(EyeStatus.PARAM_ERROR);
        }
        BeanUtils.copyProperties(query, t);
        return t;
    }


    protected T toEntityConver(DTO entityDTO) {
 //       T entity = conversionService.convert(entityDTO, model);
        T t = null;
        try {
            t = model.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (ObjectUtils.isEmpty(t)) {
            throw  new BaseException(EyeStatus.PARAM_ERROR);
        }
        BeanUtils.copyProperties(entityDTO, t);
        return t;
    }

    protected IPage<VO> pageConver(IPage<T> iPage) {
        List<VO> list = new ArrayList<>();
        List<T> records = iPage.getRecords();
        for (T record :records) {
            VO vo = null;
            try {
                vo = value.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (ObjectUtils.isEmpty(vo)) {
                throw  new BaseException(EyeStatus.PARAM_ERROR);
            }
            BeanUtils.copyProperties(record, vo);
 //           VO vo = conversionService.convert(record, value);
            list.add(vo);
        }
        IPage<VO> resultPage = new Page<>();
        BeanUtils.copyProperties(iPage,resultPage);
        resultPage.setRecords(list);
        return resultPage;
    }
}
