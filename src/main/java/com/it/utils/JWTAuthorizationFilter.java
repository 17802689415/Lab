package com.it.utils;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @program com.example.network.framework.jwt
 * @description 用户权限的拦截器
 * @auther Mr.Xiong
 * @create 2021-08-14 13:44:03
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request);
        //获取token
        String token = request.getHeader("Authorization");
        System.out.println("1"+token);
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            System.out.println("2"+token);
            return;
        }
        //解析token
        String userid;
        try {
            Claims claims = JwtTokenUtils.parseJWT(token);
            userid = claims.getSubject();
            System.out.println(3+userid);
            System.out.println(myUserDetailsService.getAuthority(userid));
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
//        String redisKey = "login:" + userid;
//        Object loginUser = redisCache.getCacheObject(redisKey);
//        if(Objects.isNull(loginUser)){
//            throw new RuntimeException("用户未登录");
//        }
        //存入SecurityContextHolder
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userid,null,myUserDetailsService.getAuthority(userid));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
        System.out.println(4+token);
    }

}
