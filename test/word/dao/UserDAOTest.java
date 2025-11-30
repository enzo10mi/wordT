package word.dao; 

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

        // 1. 【造数据】往数据库里“硬塞”30个单词
        // 这里的单词分类(category)会被设为 "TEST"
        insertMockData();

        // 2. 初始化 DAO
        UserDAO userDAO = new UserDAO();
        StudyrecordDAO studyDAO = new StudyrecordDAO();
        WordDAO wordDAO = new WordDAO(); // 确保引入了最新的 WordDAO

        // 3. 注册并登录一个测试用户
        String username = "testUser_" + System.currentTimeMillis(); 
        User user = new User(username, "123456");
        
        System.out.println("正在注册用户...");
        if (userDAO.register(user)) {
            // 登录拿到 ID
            user = userDAO.login(username, "123456");
            System.out.println("√ 用户登录成功，ID: " + user.getId());

            // 4. 【关键】初始化学习记录
            // 这步会将数据库里所有的词（包括刚才插入的 TEST 类别的词）都关联给这个用户
            int count = studyDAO.initRecords(user.getId());
            System.out.println("√ 学习记录初始化完成，关联单词数: " + count);

            // 5. 【核心测试】测试按分类随机取词
            System.out.println("\n--- 测试: 从 'TEST' 词书中随机取 5 个 ---");
            
            // 【关键修改点】：
            // 之前的代码是: wordDAO.getStudyList(user.getId(), 5);
            // 现在的代码需传入书名: "TEST"
            List<Word> list = wordDAO.getStudyList(user.getId(), "TEST", 5);
            
            if (list.isEmpty()) {
                System.err.println("× 失败：未获取到单词列表！(请检查数据库中是否有 category='TEST' 的单词)");
            } else {
                for (Word w : list) {
                    System.out.println("抽中: " + w.getEnglish() + " [" + w.getCategory() + "] (" + w.getChinese() + ")");
                }
                System.out.println("√ WordDAO 运行正常！指定词书取词功能验证通过。");
            }

        } else {
            System.err.println("× 注册失败");
        }
    }

    // --- 辅助方法：直接用代码插入30个数据 ---
    private static void insertMockData() {
        System.out.println("正在生成30个测试单词...");
        // 确保你的数据库表 Words 确实有 category 字段
        String sql = "INSERT INTO Words (english, chinese, category) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false);
            
            // 循环生成30个词
            for (int i = 1; i <= 30; i++) {
                pstmt.setString(1, "Word-" + i);              // 英文
                pstmt.setString(2, "意思-" + i);              // 中文
                pstmt.setString(3, "TEST");                   // 【分类】：这里设为 TEST
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println(">> 30个测试单词已写入数据库 (Category: TEST)。");
            
        } catch (SQLException e) {
            // 忽略重复插入或其他错误
            System.out.println("插入数据提示 (可能是数据已存在): " + e.getMessage());
        }
    }
}