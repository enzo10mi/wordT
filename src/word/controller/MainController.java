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
import word.dao.StudyrecordDAO; // 导入学习记录DAO
import java.sql.*; // 导入SQL相关包

public class MainController {
    private MainView view;
    private User user;
    private String currentBook; // 存选中的书名

    // 【构造函数】
    public MainController(MainView view, User user, String bookName) {
        this.view = view;
        this.user = user;
        this.currentBook = bookName; 
        
        // 更新标题栏，显示当前用户和书名
        view.setTitle("Recite words - " + user.getUsername() + " [Current dictionary: " + currentBook + "]");
        
        // 加载统计信息
        loadStatistics();
        
        initActions();
    }
    
    public MainController(MainView view, User user) {
        this(view, user, "四级词汇");
    }
    
    // 加载学习统计信息
    private void loadStatistics() {
        StudyrecordDAO dao = new StudyrecordDAO();
        
        try {
            // 获取已学习单词数量
            int studiedCount = dao.getStudiedWordCount(user.getId());
            // 获取当前词书的总单词数
            int totalCount = dao.getTotalWordCount(currentBook);
            // 获取待复习单词数量（已学习但不认识的）
            int unknownCount = dao.getUnknownWordCount(user.getId());
            
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
                        "Great! All the words in" + currentBook + " have been learned\nPlease click\"选择词书\" to change book", 
                        "Congratulation", 
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
                        "Currently, there are no misspelled words that need to be reviewed\n",
                        "Review completed",
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
    
    // 处理选书逻辑
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
                            "Welcome back! \nPlease select a dictionary:",
                            "Select a dictionary",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            possibilities[0]);

        // 如果用户做出了选择
        if ((s != null) && (s.length() > 0)) {
            currentBook = s; // 1. 保存用户选的书
            System.out.println("用户切换词书为: " + currentBook); // 调试信息
            
            // 2. 实时更新窗口标题，给用户反馈
            view.setTitle("Recite words - " + user.getUsername() + " [Current dictionary: " + currentBook + "]");
            
            // 重新加载统计信息
            loadStatistics();
        }
    }
}