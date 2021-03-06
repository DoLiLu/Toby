package Book1.dao;

import Book1.domain.Level;
import Book1.domain.User;
import Book1.sqlservice.SqlService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class UserDaoJdbc implements UserDao {
    private Map<String, String> sqlMap;

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private SqlService sqlService;

    public void setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    private RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        }
    };


    public void add(final User user) {
        this.jdbcTemplate.update(this.sqlService.getSql("userAdd"), user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLevel(), user.getRecommend());
//        this.jdbcConText.workWithStatementStrategy(
//            new StatementStrategy() {
//                public PreparedStatement makePreparedStatement(Connection c) throws SQLException {                  // 콜백 함수 생성 부
//                    PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
//                    ps.setString(1, user.getId());
//                    ps.setString(2, user.getName());
//                    ps.setString(3, user.getPassword());
//                    return ps;
//                }
//            }
//        );

//        ==>

//        this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) values (?,?,?,?,?,?)",
//                                        user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());

    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"),
                new Object[] {id}, this.userMapper);
//        1. 맨 처음
//        Connection c = this.dataSource.getConnection();
//        PreparedStatement ps = c
//                .prepareStatement("select * from users where id = ?");
//        ps.setString(1, id);
//
//        ResultSet rs = ps.executeQuery();
//
//        User user = null;
//        if(rs.next()) {
//            user = new User();
//            user.setId(rs.getString("id"));
//            user.setName(rs.getString("name"));
//            user.setPassword(rs.getString("password"));
//        }
//        rs.close();
//        ps.close();
//        c.close();
//
//        if(user == null) throw new EmptyResultDataAccessException(1);
//
//        return user;

//        ==>

//        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
//            new Object[] {id}, this.userMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"),
                this.userMapper
        );
//        return this.jdbcTemplate.query("select * from users order by id",
//                this.userMapper
//        );
    }


    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
//        this.jdbcTemplate.update(
//            new PreparedStatementCreator() {
//                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                    return con.prepareStatement("delete from users");
//                }
//            }
//        );
//      == >
//        this.jdbcTemplate.update("delete from users");
    }


    public int getCount() {
//        return this.jdbcTemplate.queryForInt("select count(*) from users");
        return this.jdbcTemplate.queryForInt(this.sqlService.getSql("userGetCount"));
    }
//        1. 맨 처음
//        Connection c = dataSource.getConnection();
//
//        PreparedStatement ps = c.prepareStatement("select count(*) from users");
//
//        ResultSet rs = ps.executeQuery();
//        rs.next();
//        int count = rs.getInt(1);
//
//        rs.close();
//        ps.close();
//        c.close();
//

//        return count;
//        ==>

//        2. 콜백 함수 생략 X
//        return this.jdbcTemplate.query(
//            new PreparedStatementCreator() {
//                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                    return con.prepareStatement("select count(*) from users");
//                }
//        },  new ResultSetExtractor<Integer>() {
//                public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//                    rs.next();
//                    return rs.getInt(1);
//                }
//            }

//        );

//        ==>  콜백 함수 생략 후 최종 쿼리 = 주석 X 코드
    public void update(User user1) {
        this.jdbcTemplate.update(this.sqlService.getSql("userUpdate"),
                                        user1.getName(), user1.getPassword(), user1.getLevel().intValue(), user1.getLogin(), user1.getRecommend(),
                                        user1.getId());
//        this.jdbcTemplate.update("update users set name = ?, password = ?, level = ?, login = ?, recommend = ? where id = ?",
//                                        user1.getName(), user1.getPassword(), user1.getLevel().intValue(), user1.getLogin(), user1.getRecommend(),
//                                        user1.getId());
    }

}
