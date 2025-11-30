package word.controller;

import word.model.User;
import word.view.MainView;
import word.view.StudyView;
import word.view.ReviewView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private MainView view;
    private User user;
    private String currentBook; // 用来存选中的书名

    // 【构造函数】：接收 MainView, User, 和 书名
    public MainController(MainView view, User user, String bookName) {
        this.view = view;
        this.user = user;
        this.currentBook = bookName; // 记下来，后面传给学习界面
        
        // 更新标题栏，显示当前用户和书名，提升体验
        view.setTitle("背单词系统 - " + user.getUsername() + " [当前词书: " + currentBook + "]");
        
        initActions();
    }
    
    // 兼容旧代码的构造函数 (可选，防止其他地方报错)
    public MainController(MainView view, User user) {
        this(view, user, "四级词汇");
    }

    private void initActions() {
        // --- 1. "开始学习" 按钮 ---
        // 【修正点】这里的方法名改成和 MainView 里的一致：addStartStudyListener
        view.addStartStudyListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭主菜单
                view.dispose(); 
                
                // 打开学习界面
                StudyView studyView = new StudyView();
                
                // 【关键】：把 currentBook (用户选的书) 传给学习控制器
                new StudyController(studyView, user, currentBook); 
                
                studyView.setVisible(true);
            }
        });

        // --- 2. "复习" 按钮 ---
        // 这里保持原样，因为 MainView 里叫 addReviewListener
        view.addReviewListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                
                // 打开复习界面 (错题本模式)
                ReviewView reviewView = new ReviewView();
                
                // 复习不需要传书名 (因为我们决定采用全局错题本模式)
                new ReviewController(reviewView, user); 
                
                reviewView.setVisible(true);
            }
        });
        
        // --- 3. "退出登录" 按钮 (如果有的话) ---
        view.addLogoutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                // 返回登录界面...
                // new LoginController(new LoginView())...
            }
        });
    }
}