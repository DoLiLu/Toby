import Ch1.User;
import Ch1.UserDao;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserDaoTest {
        @Test
        public void addAndGet() throws SQLException {
            ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
            UserDao dao = context.getBean("userDao", UserDao.class);

            User user = new User();
            user.setId("Lee");
            user.setName("이정수");
            user.setPassword("Spring");;

            dao.add(user);

            User user2 = dao.get(user.getId());

            assertThat(user2.getName(), is(user.getName()));
            assertThat(user2.getPassword(), is(user.getPassword()));
        }

        public static void main(String[] args) {
            JUnitCore.main("UserDaoTest");
        }
}
