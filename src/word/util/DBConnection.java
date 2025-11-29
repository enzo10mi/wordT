package word.util;

import word.resource.DbProperties;
import word.sql.SqlApp;
import java.sql.*;

public class DBConnection {

    // 静态代码块：加载驱动 (只执行一次)
    static {
        try {
            Class.forName(DbProperties.DRIVER_CLASS);//创建数据库
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败！请检查 jar 包是否导入。");
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(
            DbProperties.DB_URL, 
            DbProperties.USER, 
            DbProperties.PASSWORD
        );
        
        // 获取连接时，顺便检查表是否存在，不存在就初始化
        checkAndInitTables(conn);
        
        return conn;
    }

    /**
     * 检查并初始化表结构
     */
    private static void checkAndInitTables(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // 1. 尝试创建 Users 表
            try {
                stmt.execute(SqlApp.CREATE_USER_TABLE);
                System.out.println(">> 系统初始化: Users 表创建成功。");
            } catch (SQLException e) {
                // Derby 错误码 X0Y32 代表表已存在，忽略它
                if (!"X0Y32".equals(e.getSQLState())) throw e;
            }

            // 2. 尝试创建 Words 表
            try {
                stmt.execute(SqlApp.CREATE_WORD_TABLE);
                System.out.println(">> 系统初始化: Words 表创建成功。");
                
                // 如果是刚创建的表，顺便插点数据进去测试
                //stmt.execute(SqlApp.INIT_WORDS);
                System.out.println(">> 系统初始化: 写入初始单词数据。");
            } catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) throw e;
            }

            // 3. 尝试创建 StudyRecords 表
            try {
                stmt.execute(SqlApp.CREATE_STUDY_RECORD_TABLE);
                System.out.println(">> 系统初始化: StudyRecords 表创建成功。");
            } catch (SQLException e) {
                if (!"X0Y32".equals(e.getSQLState())) throw e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}