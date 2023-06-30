package cn.com.citydo.utils;

import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.common.Consts;
import cn.hutool.core.util.ObjectUtil;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * Spring Security工具类
 * </p>
 * @author blackcat
 * @since 2020-07-21
 */
public class SecurityUtil {
    /**
     * 获取当前登录用户用户名
     *
     * @return 当前登录用户用户名
     */
    public static String getCurrentUsername() {
        UserPrincipal currentUser = getCurrentUser();
        return ObjectUtil.isNull(currentUser) ? Consts.ANONYMOUS_NAME : currentUser.getUsername();
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息，匿名登录时，为null
     */
    public static UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if(authentication == null){
            return null;
        }
        Object userInfo = authentication.getPrincipal();
        if (userInfo instanceof UserDetails) {
            return (UserPrincipal) userInfo;
        }
        return null;
    }

    public static void main (String[] args){
        SecurityContextHolder securityContextHolder = new SecurityContextHolder();
    }
}
