package cn.com.citydo.module.core.service;

import cn.com.citydo.module.core.dto.FocusGroupQUERY;
import cn.com.citydo.module.core.entity.FocusGroup;
import cn.com.citydo.module.core.vo.FocusGroupVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface FocusGroupService extends IService<FocusGroup> {

    /**
     * 查询重点人员
     **/
    IPage<FocusGroupVO> getByQuery(FocusGroupQUERY query);
}
