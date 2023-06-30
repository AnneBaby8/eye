package cn.com.citydo.module.basic.mapper;

import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> selectDataListByRoleAndUser(Long userId);

    /**
     * 区级及以下数据
     * @return
     */
    List<DepartmentVO> selectAreaDataList();
    /**
     * 街道及以下数据
     * @return
     */
    List<DepartmentVO> selectStreetDataList();
    /**
     * 社区数据
     * @return
     */
    List<DepartmentVO> selectSocialDataList();
    /**
     * 区数据
     * @return
     */
    List<DepartmentVO> selectOnlyAreaDataList();
    /**
     * 街道数据
     * @return
     */
    List<DepartmentVO> selectOnlyStreetDataList();
    /**
     * APP-获取处置员社区
     * @return
     */
    List<DepartmentVO> selectAppSocialDataListByDepartmentId(Long departmentId);
    /**
     * 市-区-街道-社区
     * @return
     */
    List<DepartmentVO> selectPartDataList();
}
