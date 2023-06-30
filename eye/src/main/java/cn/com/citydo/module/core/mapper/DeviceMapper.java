package cn.com.citydo.module.core.mapper;

import cn.com.citydo.module.core.entity.Device;
import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.core.vo.OverviewWarnTypeVO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
public interface DeviceMapper extends BaseMapper<Device> {
    /**
     * 数据概览-设备列表
     * @return
     */
    IPage<OverviewDeviceVO> selectOverviewDeviceData(IPage<OverviewDeviceVO> page,@Param("overviewDataDTO") OverviewDataDTO overviewDataDTO);

    List<OverviewWarnTypeVO> selectWarnTypeDataList(@Param("overviewDataDTO") OverviewDataDTO overviewDataDTO);

}
