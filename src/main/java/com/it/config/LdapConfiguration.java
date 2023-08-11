package com.it.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import javax.naming.Context;
import java.security.Security;
import java.util.Hashtable;
import java.util.Objects;

/**
 * @author margo
 * @date 2021/11/4
 */
@Slf4j
public class LdapConfiguration {

    private LdapTemplate ldapTemplate;


    private String ldapUrl = "LDAPS://corp.JABIL.ORG";


    private String ldapBaseDc = "DC=corp,DC=JABIL,DC=org";


    private String ldapUsername = "jabil\\3554536";


    private String ldapPasswd = "010803zl.@1234567";


    /**
     * 继承LdapContextSource重写getAnonymousEnv方法来加载，
     * 使连接ldap时用SSL连接（由于修改AD密码时必须使用SSL连接）
     */
    public class SsldapContextSource extends LdapContextSource {
        @Override
        public Hashtable<String, Object> getAnonymousEnv(){

            Hashtable<String, Object> anonymousEnv = super.getAnonymousEnv();
            anonymousEnv.put("java.naming.security.protocol", "ssl");
            anonymousEnv.put("java.naming.ldap.factory.socket", CustomSslSocketFactory.class.getName());
            return anonymousEnv;
        }
    }


    @Bean
    public LdapContextSource contextSource() {

        SsldapContextSource ldapContextSource = new SsldapContextSource();
        ldapContextSource.setBase(ldapBaseDc);
        ldapContextSource.setUrl(ldapUrl);
        ldapContextSource.setUserDn(ldapUsername);
        ldapContextSource.setPassword(ldapPasswd);
        ldapContextSource.setPooled(false);
        ldapContextSource.setReferral("follow");
        ldapContextSource.afterPropertiesSet();
        return ldapContextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {

        if (Objects.isNull(contextSource)) {
            throw new RuntimeException("ldap contextSource error");
        }
        if (null == ldapTemplate) {
            ldapTemplate = new LdapTemplate(contextSource);
        }
        return ldapTemplate;
    }

}

