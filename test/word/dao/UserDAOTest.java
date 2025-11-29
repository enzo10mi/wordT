package word.dao; // 确保包名正确，如果你的类在 word.dao 包里测试，就改成 word.dao

import word.model.User;
import word.model.Word;
import word.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDAOTest {

    public static void main(String[] args) {
        System.out.println("=== 综合功能测试 (Mock Data) ===");

        // 1. 【造数据】往数据库里“硬塞”30个单词 (绕过文件读取，防止乱码干扰)
        insertMockData();

        // 2. 初始化 DAO
        UserDAO userDAO = new UserDAO();
        StudyrecordDAO studyDAO = new StudyrecordDAO();
        WordDAO wordDAO = new WordDAO();

        // 3. 注册并登录一个测试用户
        String username = "testUser_" + System.currentTimeMillis(); // 随机用户名防止重复
        User user = new User(username, "123456");
        
        System.out.println("正在注册用户...");
        if (userDAO.register(user)) {
            // 登录拿到 ID
            user = userDAO.login(username, "123456");
            System.out.println("? 用户登录成功，ID: " + user.getId());

            // 4. 【关键】初始化学习记录
            // 这步如果不跑，WordDAO.getStudyList 查不到数据
            int count = studyDAO.initRecords(user.getId());
            System.out.println("? 学习记录初始化完成，关联单词数: " + count);

            // 5. 【核心测试】测试随机取词 (WordDAO)
            System.out.println("\n--- 测试: 从30个词中随机取 5 个 ---");
            List<Word> list = wordDAO.getStudyList(user.getId(), 5);
            
            if (list.isEmpty()) {
                System.err.println("? 失败：未获取到单词列表！");
            } else {
                for (Word w : list) {
                    System.out.println("抽中: " + w.getEnglish() + " (" + w.getChinese() + ")");
                }
                System.out.println("? WordDAO 运行正常！随机取词功能验证通过。");
            }

        } else {
            System.err.println("? 注册失败");
        }
    }

    // --- 辅助方法：直接用代码插入30个数据 ---
    private static void insertMockData() {
        System.out.println("正在生成30个测试单词...");
        String sql = "INSERT INTO Words (english, chinese, category) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false);
            
            // 循环生成30个词
            for (int i = 1; i <= 30; i++) {
                pstmt.setString(1, "Word-" + i);              // 英文
                pstmt.setString(2, "意思-" + i);              // 中文
                pstmt.setString(3, "TEST");                   // 分类
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println(">> 30个测试单词已写入数据库。");
            
        } catch (SQLException e) {
            // 如果表已经存在且有数据，可能会报错，忽略它或者打印出来
            System.out.println("插入数据提示: " + e.getMessage());
        }
    }
}