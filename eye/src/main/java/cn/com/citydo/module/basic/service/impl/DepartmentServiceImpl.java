package cn.com.citydo.module.basic.service.impl;

import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.Consts;
import cn.com.citydo.common.EyeStatus;
import cn.com.citydo.common.enums.DepartmentGradeEnum;
import cn.com.citydo.module.basic.entity.Department;
import cn.com.citydo.module.basic.entity.UserDepartment;
import cn.com.citydo.module.basic.mapper.DepartmentMapper;
import cn.com.citydo.module.basic.service.DepartmentService;
import cn.com.citydo.module.basic.service.UserDepartmentService;
import cn.com.citydo.module.basic.vo.DepartmentVO;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.module.core.vo.WorkflowCount;
import cn.com.citydo.module.screen.enums.DepartmentLevelEnum;
import cn.com.citydo.module.screen.vo.OverviewDepartmentVO;
import cn.com.citydo.utils.BaseTreeNode;
import cn.com.citydo.utils.SecurityUtil;
import cn.com.citydo.utils.TreeNodeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author blackcat
 * @since 2021-05-20
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    private static final Long ZERO = new Long(0);

    @Override
    public List<DepartmentVO> getAll() {
        List<DepartmentVO> list = new ArrayList<>();
        List<Department> records = this.list();
        for (Department record : records) {
            DepartmentVO vo = conversionService.convert(record, DepartmentVO.class);
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<DepartmentVO> getDepartments() {
        List<DepartmentVO> list = getAll();
        if (!CollectionUtils.isEmpty(list)) {
            list = list.stream()
                    .filter(item -> (0 == item.getIsGrid())).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public String getDepartmentName(Long areaId, Long streetId, Long socialId) {
        StringBuilder sb = new StringBuilder();
        Department area = this.getById(areaId);
        getName(sb, area);
        Department street = this.getById(streetId);
        getName(sb, street);
        Department social = this.getById(socialId);
        getName(sb, social);
        return sb.toString();
    }


    @Override
    public List<String> getDepartment(Long userId) {
        List<String> list = new ArrayList<>(10);
        List<UserDepartment> departments = userDepartmentService.list(new QueryWrapper<UserDepartment>().lambda().eq(UserDepartment::getUserId, userId));
        if (!CollectionUtils.isEmpty(departments)) {
            UserDepartment userDepartment = departments.get(0);
            Long departmentId = userDepartment.getDepartmentId();
            getName(departmentId, list);
            Collections.reverse(list);
        }
        return list;
    }

    @Override
    public List<DepartmentVO> getByParentId(Long parentId) {
        List<DepartmentVO> list = new ArrayList<>();
        List<Department> records = this.list(new QueryWrapper<Department>().lambda().eq(Department::getParentId, parentId));
        for (Department record : records) {
            DepartmentVO vo = conversionService.convert(record, DepartmentVO.class);
            list.add(vo);
        }
        return list;
    }

    @Override
    public DepartmentVO getDepartmentNameById(Long id) {
        Department department = baseMapper.selectById(id);
        if (department != null) {
        }
        return null;
    }

    @Override
    public DepartmentVO getPartDepartmentName(Long areaId, Long streetId, Long socialId) {
        DepartmentVO departmentVO = new DepartmentVO();
        Department area = this.getById(areaId);
        if (area != null) {
            departmentVO.setAreaName(area.getName());
        }
        Department street = this.getById(streetId);
        if (street != null) {
            departmentVO.setStreetName(street.getName());
        }
        Department social = this.getById(socialId);
        if (social != null) {
            departmentVO.setSocialName(social.getName());
        }
        return departmentVO;
    }

    private void getName(Long departmentId, List<String> list) {
        Department department = this.getById(departmentId);
        if (!ObjectUtils.isEmpty(department) && !ZERO.equals(department.getParentId())) {
            list.add(department.getName());
            getName(department.getParentId(), list);
        }
    }

    private void getName(StringBuilder sb, Department department) {
        if (department != null) {
            sb.append(department.getName());
        }
    }

    @Override
    public void getNodeList(List<BaseTreeNode> nodeList, List<DepartmentVO> list) {
        for (DepartmentVO departmentVO : list) {
            BaseTreeNode node = new BaseTreeNode();
            node.setId(departmentVO.getId());
            node.setPId(departmentVO.getParentId());
            node.setName(departmentVO.getName());
            nodeList.add(node);
        }
    }

    /**
     * 数据概览-部门列表
     *
     * @param authorityList
     * @return
     */
    @Override
    public OverviewDepartmentVO getTreeData(List<String> authorityList) {
        OverviewDepartmentVO overviewDepartmentVO = new OverviewDepartmentVO();

        List<DepartmentVO> allList = new ArrayList<>();
        List<DepartmentVO> parentList = new ArrayList<>();

        if (authorityList.contains(Consts.DATA_OVERVIEW_CITY_DATA)) {
            //市
            allList = this.getAll();
            overviewDepartmentVO.setDepartmentLevel(DepartmentLevelEnum.CITY_DATA.getKey());
        } else if (authorityList.contains(Consts.DATA_OVERVIEW_AREA_DATA)) {
            //区
            allList = baseMapper.selectAreaDataList();
            parentList = baseMapper.selectOnlyAreaDataList();
            overviewDepartmentVO.setDepartmentLevel(DepartmentLevelEnum.AREA_DATA.getKey());
        } else if (authorityList.contains(Consts.DATA_OVERVIEW_STREET_DATA)) {
            //街道
            allList = baseMapper.selectStreetDataList();
            parentList = baseMapper.selectOnlyStreetDataList();
            overviewDepartmentVO.setDepartmentLevel(DepartmentLevelEnum.STREET_DATA.getKey());
        } else if (authorityList.contains(Consts.DATA_OVERVIEW_SOCIAL_DATA)) {
            //社区
            allList = baseMapper.selectSocialDataList();
            overviewDepartmentVO.setDepartmentLevel(DepartmentLevelEnum.SOCIAL_DATA.getKey());
        }
        List<BaseTreeNode> allNodeList = new ArrayList<>();
        this.getNodeList(allNodeList, allList);

        if (!CollectionUtils.isEmpty(parentList)) {
            List<BaseTreeNode> parentNodes = new ArrayList<>();
            this.getNodeList(parentNodes, parentList);

            for (BaseTreeNode t : parentNodes) {
                TreeNodeUtil.assembleTree(t, allNodeList);
            }
            overviewDepartmentVO.setBaseTreeNodeList(parentNodes);
        } else if (authorityList.contains(Consts.DATA_OVERVIEW_SOCIAL_DATA)) {
            overviewDepartmentVO.setBaseTreeNodeList(allNodeList);
        } else {
            overviewDepartmentVO.setBaseTreeNodeList(TreeNodeUtil.assembleTree(allNodeList));
        }
        return overviewDepartmentVO;

    }

    @Override
    public List<Department> getDepartmentDataByGrade(String departmentGrade) {
        List<Department> list = new ArrayList<>();
        UserPrincipal user = SecurityUtil.getCurrentUser();
        if (user != null) {
            List<UserDepartment> byUserId = userDepartmentService.getByUserId(user.getId());
            if (!CollectionUtils.isEmpty(byUserId)) {
                for (UserDepartment userDepartment : byUserId) {
                    Long departmentId = userDepartment.getDepartmentId();
                    Map<String, Department> parentDepartments = this.getParentDepartments(departmentId);
                    if (parentDepartments.containsKey(departmentGrade)) {
                        Department department = parentDepartments.get(departmentGrade);
                        list.add(department);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public WorkflowCount getInfoByDepartmentId(Long departmentId) {
        WorkflowCount workflowCount = new WorkflowCount();
        //获取网格信息
        Department grid = baseMapper.selectById(departmentId);
        if (grid != null) {
            workflowCount.setGridName(grid.getName());
            Department social = baseMapper.selectById(grid.getParentId());
            if (social != null) {
                workflowCount.setSocialName(social.getName());
            }
        }
        return null;
    }

    @Override
    public Map<String, Department> getParentDepartments(Long departmentId) {
        Map<String, Department> result = new HashMap<String, Department>();
        List<Department> list = new ArrayList<Department>();
        long curDepartmentId = departmentId;
        while (true) {
            QueryWrapper<Department> queryWrapper = new QueryWrapper();
            LambdaQueryWrapper<Department> lambda = queryWrapper.lambda();
            lambda.eq(Department::getId, curDepartmentId);
            Department department = this.getOne(lambda);
            list.add(department);
            if (department.getParentId() == 0) {
                break;
            }
            curDepartmentId = department.getParentId();
        }
        int index = list.size() - 1;
        int level = 0;
        for (; index >= 0; index--, level++) {
            Department department = list.get(index);
            switch (level) {
                case 0:
                    result.put("city", department);
                    break;
                case 1:
                    result.put("area", department);
                    break;
                case 2:
                    result.put("street", department);
                    break;
                case 3:
                    result.put("social", department);
                    break;
                case 4:
                    result.put("grid", department);
            }
        }
        return result;
    }

    @Override
    public Long dataHandle(Long departmentId) {
        if (ObjectUtils.isEmpty(departmentId)) {
            UserPrincipal user = SecurityUtil.getCurrentUser();
            List<UserDepartment> departmentList = userDepartmentService.getByUserId(user.getId());
            if (!CollectionUtils.isEmpty(departmentList)) {
                UserDepartment userDepartment = departmentList.get(0);
                departmentId = userDepartment.getDepartmentId();
            } else {
                throw new BaseException(500, "当前用户没有部门");
            }
        }
        Department department = this.getById(departmentId);
        if (ObjectUtils.isEmpty(department)) {
            throw new BaseException(EyeStatus.PARAM_ERROR);
        }
        //天津特殊处理
        if (new Long(0).equals(department.getParentId())) {
            return null;
        }
        return departmentId;
    }

    @Override
    public List<BaseTreeNode> getAppDepartmentDataList() {
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        if( currentUser == null ){
            return null;
        }
        List<UserDepartment> byUserId = userDepartmentService.getByUserId(currentUser.getId());
        if( !CollectionUtils.isEmpty(byUserId) ){
            Long departmentId = byUserId.get(0).getDepartmentId();
            Map<String, Department> map = this.getParentDepartments(departmentId);
            if( map.containsKey(DepartmentGradeEnum.STREET.getCode()) ){
                List<DepartmentVO> list = baseMapper.selectAppSocialDataListByDepartmentId(departmentId);
                if( !CollectionUtils.isEmpty(list) ){
                    List<BaseTreeNode> nodeList = new ArrayList<>();
                    this.getNodeList(nodeList, list);
                    return TreeNodeUtil.assembleTree(nodeList);
                }
            }
        }
        return null;
    }

    @Override
    public List<DepartmentVO> getPartDepartments() {
        return baseMapper.selectPartDataList();
    }
}
