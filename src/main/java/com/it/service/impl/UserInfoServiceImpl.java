package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.config.LdapConfiguration;
import com.it.mapper.UserInfoMapper;
import com.it.pojo.LdapUser;
import com.it.pojo.Login;
import com.it.pojo.UserInfo;
import com.it.service.UserInfoService;
import com.it.utils.JwtTokenUtils;
import com.it.utils.R;
import com.it.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Service;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private RedisCache redisCache;
    @Override
    public R login(Login log) {
        DirContext context = null;
        NamingEnumeration<SearchResult> search = null;
        SearchResult next = null;
        LdapContextSource ldapContextSource = null;
        LdapTemplate ldapTemplate = null;
        LdapUser ldapUser = new LdapUser();
        try {
            LdapConfiguration ldapConfiguration = new LdapConfiguration();
            ldapContextSource = ldapConfiguration.contextSource();
            ldapTemplate = ldapConfiguration.ldapTemplate(ldapContextSource);
            context = ldapTemplate.getContextSource().getContext("jabil" + "\\" + log.getUsername(), log.getPassword());
            SearchControls contro = new SearchControls();
            contro.setSearchScope(SearchControls.SUBTREE_SCOPE);
            contro.setReturningAttributes(new String[] {"cn","title","userPrincipalName","employeeNumber","employeeID","serialNumber","manager"});
            search = context.search("", "employeeNumber="+log.getUsername(), contro);
            next = search.next();
            ldapUser.setCn(next.getAttributes().get("cn").get(0).toString());
            ldapUser.setEmployId(next.getAttributes().get("employeeid").get(0).toString());
            ldapUser.setUserPrincipalName(next.getAttributes().get("userprincipalname").get(0).toString());
            ldapUser.setTitle(next.getAttributes().get("title").get(0).toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return R.error("账号密码不正确",100);
        }finally {
            // 在 finally 块中关闭 context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException e) {
                    // 异常处理
                    e.printStackTrace();
                }
            }
        }

        String jwt = JwtTokenUtils.createJWT(log.getUsername());
        Map<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        map.put("user",ldapUser);
        return R.success(map,200);
    }

}
