package cn.com.citydo.common;

/**
 * <p>
 * 常量池
 * </p>
 * @author blackcat
 * @since 2020-07-21
 */
public interface Consts {


    /**
     * 启用
     */
    Integer ENABLE = 1;
    /**
     * 禁用
     */
    Integer DISABLE = 0;

    /**
     * 页面
     */
    Integer PAGE = 1;

    /**
     * 按钮
     */
    Integer BUTTON = 2;

    /**
     * JWT 在 Redis 中保存的key前缀
     */
    String REDIS_JWT_KEY_PREFIX = "security:jwt:";

    /**
     * 星号
     */
    String SYMBOL_STAR = "*";

    /**
     * 邮箱符号
     */
    String SYMBOL_EMAIL = "@";

    /**
     * 默认当前页码
     */
    Integer DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认每页条数
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 匿名用户 用户名
     */
    String ANONYMOUS_NAME = "匿名用户";


    String REDIS_CODE_KEY_PREFIX = "mobile:code:";


    String REDIS_MOBILE_LOCK_PREFIX = "mobile:lock:";
    /**
     * 用户默认密码
     */
    String DEFAULT_PASSWORD = "123456";
    /**
     * 空字符串
     */
    String empty = "";
    /**
     * 0
     */
    String ZERO = "0";
    /**
     * 1
     */
    String ONE = "1";
    /**
     * 2
     */
    String TWO = "2";
    /**
     * 3
     */
    String THREE = "3";
    /**
     * 数据概览-市级数据
     */
    String DATA_OVERVIEW_CITY_DATA = "data:/data-overview/city-data";
    /**
     * 数据概览-区级数据
     */
    String DATA_OVERVIEW_AREA_DATA = "data:/data-overview/area-data";
    /**
     * 数据概览-街道数据
     */
    String DATA_OVERVIEW_STREET_DATA = "data:/data-overview/street-data";
    /**
     * 数据概览-社区数据
     */
    String DATA_OVERVIEW_SOCIAL_DATA = "data:/data-overview/social-data";
    /**
     * 预警编号前缀
     */
    String WARN_PRE = "YJ";
}
