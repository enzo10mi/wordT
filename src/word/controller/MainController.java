package word.controller;

import word.model.User;
import word.view.MainView;
import word.view.StudyView;
import word.view.ReviewView;
import word.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import word.dao.WordDAO;
import word.model.Word;
import word.dao.StudyrecordDAO; // 【新增】导入学习记录DAO
import java.sql.*; // 【新增】导入SQL相关包

public class MainController {
    private MainView view;
    private User user;
    private String currentBook; // 用来存选中的书名

    // 【构造函数】
    public MainController(MainView view, User user, String bookName) {
        this.view = view;
        this.user = user;
        this.currentBook = bookName; 
        
        // 更新标题栏，显示当前用户和书名
        view.setTitle("背单词系统 - " + user.getUsername() + " [当前词书: " + currentBook + "]");
        
        // 【新增】加载统计信息
        loadStatistics();
        
        initActions();
    }
    
    // 兼容旧代码的构造函数 
    public MainController(MainView view, User user) {
        this(view, user, "四级词汇");
    }
    
    // 【修改】加载学习统计信息
    private void loadStatistics() {
        try {
            // 获取已学习单词数量
            int studiedCount = getStudiedWordCount();
            // 获取当前词书的总单词数
            int totalCount = getTotalWordCount();
            // 获取待复习单词数量（已学习但不认识的）
            int unknownCount = getUnknownWordCount();
            
            // 更新界面显示
            view.setStudiedCount(studiedCount, totalCount); // 传递两个参数
            view.setUnknownCount(unknownCount);
            
        } catch (SQLException e) {
            System.err.println("加载统计信息失败: " + e.getMessage());
            // 如果出错，显示默认值
            view.setStudiedCount(0, 0);
            view.setUnknownCount(0);
        }
    }
    
    // 【新增】获取当前词书的总单词数
    private int getTotalWordCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Words WHERE category = ?";
        
        try (Connection conn = word.util.DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, currentBook);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    // 【新增】获取已学习单词数量
    private int getStudiedWordCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM StudyRecords WHERE user_id = ? AND is_studied = true";
        
        try (Connection conn = word.util.DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    // 【新增】获取待复习单词数量（已学习但不认识的）
    private int getUnknownWordCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM StudyRecords WHERE user_id = ? AND is_studied = true AND known = false";
        
        try (Connection conn = word.util.DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private void initActions() {
        // --- 1. "开始学习" 按钮 ---
        view.addStartStudyListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WordDAO dao = new WordDAO();
                
                // 先查一下还有没有新词
                List<Word> studyList = dao.getStudyList(user.getId(), currentBook, 20);

                // 1. 检查结果
                if (studyList.isEmpty()) {
                    // 如果列表为空，说明这本书这一轮背完了
                    JOptionPane.showMessageDialog(view, 
                        "太棒了！《" + currentBook + "》的所有单词已学完。\n请点击\"选择词书\"切换其他书籍，或等待重置。", 
                        "恭喜", 
                        JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(studyList.isEmpty());
                    return; // 直接结束，不打开新窗口
                }

                // 2. 如果有词，才跳转
                view.dispose(); // 关闭主菜单
                
                StudyView studyView = new StudyView();
                
                new StudyController(studyView, user, currentBook); 
                
                studyView.setVisible(true);
            }
        });

        // --- 2. "复习" 按钮 ---
        view.addReviewListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WordDAO dao = new WordDAO();
                
                // 先查一下有没有错题
                List<Word> reviewList = dao.getReviewList(user.getId());

                // 1. 检查结果
                if (reviewList.isEmpty()) {
                    JOptionPane.showMessageDialog(view, 
                        "恭喜！目前没有需要复习的错题。\n(所有已背单词都已掌握，或尚未开始学习)",
                        "复习完成",
                        JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(reviewList.isEmpty());
                    return; // 直接结束
                }

                // 2. 如果有错题，才跳转
                view.dispose();
                
                ReviewView reviewView = new ReviewView();
                new ReviewController(reviewView, user); 
                reviewView.setVisible(true);
            }
        });
        
        // --- 3. "退出登录" 按钮  ---
        view.addLogoutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                // 返回登录界面...
                LoginView loginView = new LoginView();
                
                new LoginController(loginView);
                loginView.setVisible(true);
            }
        });
        
        // --- 4. "选择词书" 按钮  ---
        view.addSelectBookListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectBook();
            }
        });
    }
    
    // 【新增】私有方法：处理选书逻辑
    private void selectBook() {
        WordDAO wordDAO = new WordDAO();
        // 1. 从数据库获取所有可用的词书分类
        List<String> books = wordDAO.getAllCategories();
        
        // 如果数据库没书（理论上 RunApp 已初始化，不会空），给个默认值
        if (books.isEmpty()) currentBook = "四级词汇";
        
        // 2. 弹窗让用户选择
        Object[] possibilities = books.toArray();
        String s = (String) JOptionPane.showInputDialog(
                            view,
                            "欢迎回来！\n请选择您本学期要学习的词书:",
                            "选择词书",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            possibilities[0]);

        // 【修正这里】如果用户做出了选择
        if ((s != null) && (s.length() > 0)) {
            currentBook = s; // 1. 保存用户选的书
            System.out.println("用户切换词书为: " + currentBook); // 调试信息
            
            // 2. 实时更新窗口标题，给用户反馈
            view.setTitle("背单词系统 - " + user.getUsername() + " [当前词书: " + currentBook + "]");
            
            // 【新增】重新加载统计信息
            loadStatistics();
        }
    }
}