package cn.com.citydo.module.basic.controller;



import cn.com.citydo.module.basic.dto.AppLoginRequest;
import cn.com.citydo.module.basic.dto.LoginRequest;
import cn.com.citydo.module.basic.vo.JwtResponse;
import cn.com.citydo.module.basic.vo.UserPrincipal;
import cn.com.citydo.common.ApiResponse;
import cn.com.citydo.common.BaseException;
import cn.com.citydo.common.Status;
import cn.com.citydo.utils.JwtUtil;
import cn.com.citydo.utils.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static cn.com.citydo.common.Consts.REDIS_CODE_KEY_PREFIX;
import static cn.com.citydo.common.Consts.REDIS_MOBILE_LOCK_PREFIX;

/**
 * <p>
 * 认证 Controller，包括用户注册，用户登录请求
 * </p>
 *  @author blackcat
 *  @since 2020-07-21
 */
@Api(tags = "用户登陆、详情")
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录
     */
    @PostMapping("/login")
    public ApiResponse<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        String uuid = loginRequest.getUuid();
        String captcha = loginRequest.getCaptcha();
        String redisCaptcha = stringRedisTemplate.opsForValue().get(REDIS_CODE_KEY_PREFIX + uuid);
        if (StringUtils.isBlank(redisCaptcha) || !redisCaptcha.equals(captcha.toLowerCase())) {
            throw new BaseException(Status.ACCESS_CODE_ERROR);
        }
        String userName = loginRequest.getUsernameOrEmailOrPhone();
        String redisCount = stringRedisTemplate.opsForValue().get(REDIS_MOBILE_LOCK_PREFIX + userName);
        int count = StringUtils.isBlank(redisCount) ? 0 : Integer.parseInt(redisCount);
        if (count >= 3) {
            throw new BaseException(Status.MOBILE_LOCK);
        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, loginRequest.getPassword()));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                count++;
                stringRedisTemplate.opsForValue().set(REDIS_MOBILE_LOCK_PREFIX + userName, count + "", getRemainSecondsOneDay(new Date()), TimeUnit.SECONDS);
            }
            throw e;
        }

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication, loginRequest.getRememberMe());
        if(count > 0){
            //登陆成功,删除之前的失败计数
            stringRedisTemplate.delete(REDIS_MOBILE_LOCK_PREFIX + userName);
        }
        return ApiResponse.ofSuccess(new JwtResponse(jwt));
    }

    @PostMapping("/logout")
    public ApiResponse logout(HttpServletRequest request) {
        try {
            // 设置JWT过期
            jwtUtil.invalidateJWT(request);
        } catch (SecurityException e ){
            throw new BaseException(Status.UNAUTHORIZED);
        }
        return ApiResponse.ofStatus(Status.LOGOUT);
    }


    @ApiOperation(value = "根据token获取用户详情")
    @GetMapping(value = "/getUserDetails")
    public ApiResponse<UserPrincipal> getUserDetails() {
        UserPrincipal currentUser = SecurityUtil.getCurrentUser();
        return ApiResponse.ofSuccess(currentUser);
    }

    /**
     * 快速产生token
     */
    @ApiOperation(value = "快速产生token 跳过验证码")
    @PostMapping("/token")
    public ApiResponse<JwtResponse> token(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmailOrPhone(), loginRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication,loginRequest.getRememberMe());
        return ApiResponse.ofSuccess(new JwtResponse(jwt));
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @ApiOperation(value = "快速产生app token")
    @PostMapping("/appToken")
    public ApiResponse<JwtResponse> appToken(@RequestBody AppLoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getOutUserId());
        if(userDetails == null || !(userDetails instanceof UserPrincipal)){
            throw  new BaseException(500,"未获取到用户信息");
        }
        UserPrincipal userPrincipal = (UserPrincipal) userDetails;
        String jwt = jwtUtil.createJWT(false, userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getRoles(), userPrincipal.getAuthorities());
        JwtResponse jwtResponse = new JwtResponse(jwt);
        return ApiResponse.ofSuccess(jwtResponse.getTokenType()+" "+jwtResponse.getToken());
    }

    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }
}
