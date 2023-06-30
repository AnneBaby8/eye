package cn.com.citydo.module.core.mapper;

import cn.com.citydo.module.core.dto.DeviceBindQUERY;
import cn.com.citydo.module.core.entity.DeviceGridMember;
import cn.com.citydo.module.core.vo.DeviceBindVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备关联网格员 Mapper 接口
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
public interface DeviceGridMemberMapper extends BaseMapper<DeviceGridMember> {
    /**
     * 根据网格员删除数据
     * @param list
     */
    void deleteByDeviceId(List<Long> list);

    /**
     * 根据网格员和设备获取一条记录
     * @param hashMap
     * @return
     */
    DeviceGridMember selectOneByDeviceAndMember(Map<String,Object> hashMap);
    /**
     * 绑定记录
     */
    IPage<DeviceBindVO> selectBindRecordList(IPage<DeviceBindVO> page, @Param("query") DeviceBindQUERY query);
}
