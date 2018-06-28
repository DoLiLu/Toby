package Book1;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcConText {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {     // 템플릿
        Connection c = null;
        PreparedStatement ps = null;

        try{
            c = dataSource.getConnection();

            ps =stmt.makePreparedStatement(c);                                              // 콜백 호출

            ps.executeUpdate();
        } catch (SQLException e){
            throw e;
        } finally {
            if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
            if (c != null) { try {c.close(); } catch (SQLException e) {} }
        }
    }


    public void executeSql(final String query) throws SQLException {
        workWithStatementStrategy(
            new StatementStrategy() {
                public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                    PreparedStatement ps = c.prepareStatement(query);
                    return ps;
                }
            }
        );
    }
}
