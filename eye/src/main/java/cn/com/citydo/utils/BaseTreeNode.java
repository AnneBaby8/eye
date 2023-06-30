package cn.com.citydo.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author blackcat
 * @create 2021/5/28 17:25
 * @version: 1.0
 * @description:
 */
@Data
public class BaseTreeNode {

    /**
     * 子Id
     */
    private Long id;
    /**
     * 父ID
     */
    private Long pId;

    private String name;

    private List<BaseTreeNode> child;


    public void addChild(BaseTreeNode baseTreeNode) {
        if (this.child == null) {
            this.setChild(new ArrayList());
        }
        this.getChild().add(baseTreeNode);
    }
}
