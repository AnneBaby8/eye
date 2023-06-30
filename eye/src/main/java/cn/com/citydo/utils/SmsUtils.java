package cn.com.citydo.utils;

import cn.com.citydo.common.enums.NewsEnum;
import cn.com.citydo.module.core.entity.WorkflowNews;
import cn.com.citydo.module.screen.enums.WarnTypeEnum;

/**
 * @Author blackcat
 * @create 2021/6/17 9:11
 * @version: 1.0
 * @description: 消息发送工具类
 */
public class SmsUtils {


    private static final String MSG_TITLE = "%s：%s";

    private static final String MSG_CONTENT = "所属区域：%s\n" +
            "事件地址：%s\n" +
            "事件类型：%s\n" +
            "事件人员：%s\n" +
            "事件内容：%s\n" +
            "预警时间：%s\n";

    private static final String BACK_MSG_CONTENT = MSG_CONTENT +
            "退回说明：%s\n";

    private static final String SUPRISE_MSG_CONTENT = MSG_CONTENT +
            "督办部门：%s\n" +
            "督办说明：%s\n";


    private static final String WARN_MSG_CONTENT = MSG_CONTENT +
            "负责网格员：%s\n";

    private static final String REPORT_MSG_CONTENT = WARN_MSG_CONTENT +
            "上报人员：%s\n" +
            "上报部门：%s\n" +
            "上报说明：%s\n";

    public static WorkflowNews getMsg(Msg msg) {
        WorkflowNews news = new WorkflowNews();
        news.setUserId(msg.getUserId());
        news.setNewType(msg.getNewsEnum().getCode());
        if (msg.getWorkflowId() != null) {
            news.setBussinessKey(String.valueOf(msg.getWorkflowId()));
        }
        String title;
        String value = WarnTypeEnum.getValueByKey(msg.getEventType());
        if (WarnTypeEnum.HELP_GROUP.getKey().equals(msg.getEventType()) || WarnTypeEnum.FOCUS_VISIT.getKey().equals(msg.getEventType()) || WarnTypeEnum.EMPTY_NESTER.getKey().equals(msg.getEventType())) {
            title = String.format(MSG_TITLE, value, msg.getEventPeople());
        } else {
            title = String.format(MSG_TITLE, value, msg.getAddress());
        }
        news.setTitle(title);
        String content = "";
        NewsEnum newsEnum = msg.getNewsEnum();
        if (NewsEnum.REMIND.equals(newsEnum) || NewsEnum.WARN_GRID.equals(newsEnum)) {
            content = String.format(MSG_CONTENT, msg.getAreaName(), msg.getAddress(), value, msg.getEventPeople(), msg.getEventContent(), msg.getWarnTime());
        } else if (NewsEnum.WARN_GRID_LEAD.equals(newsEnum)) {
            content = String.format(WARN_MSG_CONTENT, msg.getAreaName(), msg.getAddress(), value, msg.getEventPeople(), msg.getEventContent(), msg.getWarnTime(), msg.getGridUserName());
        } else if (NewsEnum.SUPRISE_GRID_LEAD.equals(newsEnum) || NewsEnum.SUPRISE_SOCIAL.equals(newsEnum)) {
            content = String.format(SUPRISE_MSG_CONTENT, msg.getAreaName(), msg.getAddress(), value, msg.getEventPeople(), msg.getEventContent(), msg.getWarnTime(), msg.getSupriseDepartment(), msg.getRemark());
        } else if (NewsEnum.ASSIGN.equals(newsEnum)) {
            content = String.format(WARN_MSG_CONTENT, msg.getAreaName(), msg.getAddress(), value, msg.getEventPeople(), msg.getEventContent(), msg.getWarnTime(), msg.getAssignGridName());
        } else if (NewsEnum.BACK.equals(newsEnum)) {
            content = String.format(BACK_MSG_CONTENT, msg.getAreaName(), msg.getAddress(), value, msg.getEventPeople(), msg.getEventContent(), msg.getWarnTime(), msg.getRemark());
        } else if (NewsEnum.REPORT_GRID.equals(newsEnum) || NewsEnum.REPORT_STREET.equals(newsEnum)) {

            content = String.format(REPORT_MSG_CONTENT, msg.getAreaName(), msg.getAddress(), value, msg.getEventPeople(), msg.getEventContent(), msg.getWarnTime(),
                    msg.getAssignGridName() == null ? msg.getGridUserName() : msg.getAssignGridName(), msg.getSubmitName(), msg.getSubmitDepartment(), msg.getRemark());
        }
        news.setContent(content);
        return news;
    }

}
