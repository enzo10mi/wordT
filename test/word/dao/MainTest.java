package word.dao;

import word.util.DBConnection;
import java.sql.Connection;
import word.util.DBConnection;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("开始连接数据库...");
        
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("数据库连接成功！");
                System.out.println("请检查项目根目录下是否生成了 'WordLearningDB' 文件夹。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}