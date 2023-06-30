package cn.com.citydo.module.basic.service;

import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import cn.com.citydo.module.core.vo.WorkflowCount;
import cn.com.citydo.module.screen.vo.OverviewDepartmentVO;
import cn.com.citydo.utils.BaseTreeNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
public interface DepartmentService extends IService<Department> {

    List<DepartmentVO> getAll();

    List<DepartmentVO> getDepartments();

    String getDepartmentName(Long areaId, Long streetId, Long socialId);

    List<String> getDepartment(Long userId);

    List<DepartmentVO> getByParentId(Long parentId);

    DepartmentVO getDepartmentNameById(Long id);

    /**
     * 获取地址信息
     *
     * @param areaId
     * @param streetId
     * @param socialId
     * @return
     */
    DepartmentVO getPartDepartmentName(Long areaId, Long streetId, Long socialId);

    void getNodeList(List<BaseTreeNode> nodeList, List<DepartmentVO> list);

    /**
     * 数据概览-部门列表
     *
     * @param authorityList
     * @return
     */
    OverviewDepartmentVO getTreeData(List<String> authorityList);

    /**
     * APP-获取当前用户的所有社区
     *
     * @return
     */
    List<Department> getDepartmentDataByGrade(String departmentGrade);

    /**
     *
     */
    WorkflowCount getInfoByDepartmentId(Long departmentId);

    /**
     * 根据部门编号查询父级级联的区县/街道/社区/
     *
     * @param departmentId 部门编号
     * @return
     **/
    Map<String, Department> getParentDepartments(Long departmentId);


    Long dataHandle(Long departmentId);

    List<BaseTreeNode> getAppDepartmentDataList();

    List<DepartmentVO> getPartDepartments();

}
