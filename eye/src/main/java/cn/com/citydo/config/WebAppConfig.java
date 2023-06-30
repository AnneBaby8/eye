package cn.com.citydo.config;



import cn.com.citydo.module.basic.conver.DepartmentDTOConver;
import cn.com.citydo.module.basic.conver.DepartmentVOConver;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Configuration
public class WebAppConfig {



    @Bean(name = "conversionService")
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> set = new HashSet<>();
        DepartmentVOConver departmentVOConver = new DepartmentVOConver();
        set.add(departmentVOConver);
        DepartmentDTOConver departmentDTOConver = new DepartmentDTOConver();
        set.add(departmentDTOConver);
        bean.setConverters(set);
        bean.afterPropertiesSet();
        return bean.getObject();
    }


    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        //转换日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //注册自定义的编辑器
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
