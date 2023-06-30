package cn.com.citydo.module.screen.service;

import cn.com.citydo.module.core.vo.OverviewDeviceVO;
import cn.com.citydo.module.core.vo.OverviewWarnTypeVO;
import cn.com.citydo.module.screen.dto.OverviewDataDTO;
import cn.com.citydo.module.screen.vo.OverviewDataVO;
import cn.com.citydo.utils.BaseTreeNode;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 *  数据概览
 * </p>
 *
 * @author wx227336
 * @Date 2021/6/7 10:28
 * @Version 1.0
*/
public interface DataOverviewService{

    OverviewDataVO data(OverviewDataDTO overviewDataDTO);

    IPage<OverviewDeviceVO> getOverviewDeviceData(OverviewDataDTO overviewDataDTO);

    BaseTreeNode selectDepartmentData();

    OverviewDataDTO getDataOverByDepartmentId(OverviewDataDTO overviewDataDTO);

    List<OverviewWarnTypeVO> getWarnType(OverviewDataDTO overviewDataDTO);
}
