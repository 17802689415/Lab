import com.it.Application;
import com.it.config.LdapConfiguration;
import com.it.utils.CodeUtil;
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
import java.util.concurrent.atomic.AtomicLong;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LdapTest {


    @Test
    public void test() throws Exception {
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

    private static AtomicLong counter = new AtomicLong(10000000L);
    public void t(){

        for (int i = 0; i < 10; i++) {
            String s = String.valueOf(counter.getAndIncrement());
            System.out.println(s);
        }
    }
    @Test
    public void tt(){

        for (int i = 0; i < 20; i++) {
            String encode = CodeUtil.encode();
            System.out.println(encode);
        }

    }
}
