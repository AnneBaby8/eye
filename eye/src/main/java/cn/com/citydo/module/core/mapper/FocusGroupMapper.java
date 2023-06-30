package cn.com.citydo.module.core.mapper;

import cn.com.citydo.module.core.dto.FocusGroupQUERY;
import cn.com.citydo.module.core.entity.FocusGroup;
import cn.com.citydo.module.core.vo.FocusGroupVO;
import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 重点人员 Mapper 接口
 * </p>
 *
 * @author blackcat
 * @since 2021-06-01
 */
public interface FocusGroupMapper extends BaseMapper<FocusGroup> {

    /**
     * 数据概览-设备列表
     * @return
     */
    //IPage<FocusGroupVO> select(IPage<FocusGroupVO> page, @Param("overviewDataDTO") OverviewDataDTO overviewDataDTO);

    /**
     * 查询重点人员列表（分页）
     *
     */
    IPage<FocusGroupVO> selectFocusGroup(IPage<FocusGroupVO> page, @Param("query") FocusGroupQUERY query);

}
