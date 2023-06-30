package cn.com.citydo.thirdparty.huawei.request;

import lombok.Data;

@Data
public class FaceRepositoriesPeopleParam {

    /** 人员名单库id，多个用逗号分开，需要保证同一类型名单，最多支持64个库 */
    private String repositorIds;
    /** 人员名单id，多个用逗号分隔，最多支持100个 */
    private String ids;



}
