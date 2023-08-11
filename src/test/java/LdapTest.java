import com.it.Application;
import com.it.config.LdapConfiguration;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LdapTest {


    @Test
    public void test() throws NamingException {
        LdapConfiguration ldapConfiguration = new LdapConfiguration();
        LdapContextSource ldapContextSource = ldapConfiguration.contextSource();
        LdapTemplate ldapTemplate = ldapConfiguration.ldapTemplate(ldapContextSource);
        DirContext context = ldapTemplate.getContextSource().getContext("jabil" + "\\" + "3554536", "010803zl.@1234567");
        SearchControls contro = new SearchControls();
        contro.setSearchScope(SearchControls.SUBTREE_SCOPE);
        contro.setReturningAttributes(new String[] {"cn","title","userPrincipalName","employeeNumber","employeeID","serialNumber","manager"});

        NamingEnumeration<SearchResult> search = context.search("", "employeeNumber=3554536", contro);
        SearchResult next = search.next();
        System.out.println(next);

    }
}
