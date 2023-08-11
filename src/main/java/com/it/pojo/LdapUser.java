package com.it.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.naming.Name;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class LdapUser{

    private Name dn;

    private String employId;

    private String userPrincipalName;

    private String title;

    private String cn;

    public String getEmployId() {
        return employId;
    }

    public void setEmployId(String employId) {
        this.employId = employId;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }


    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }




    @Override
    public String toString() {
        return "LdapUser{" +
                "employId='" + employId + '\'' +
                ", userPrincipalName='" + userPrincipalName + '\'' +
                ", title='" + title + '\'' +
                ", cn='" + cn + '\'' +
                '}';
    }
}
