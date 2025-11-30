/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.controller;

/**
 *
 * @author yuzhe
 */

import word.view.MainView;
import word.view.StudyView;
import word.view.ReviewView;
import word.view.LoginView;
import word.model.User; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class MainController {
    private MainView view;
    private User user;

    // 构造函数接收 View 和 userId
    public MainController(MainView view, User user) {
        this.view = view;
        this.user = user;
        
        // 设置欢迎语
        view.setWelcomeText("欢迎你，" + user.getUsername());
        
        initActions();
    }

    private void initActions() {
        
        // 点击“开始学习”
        view.addStartStudyListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); // 关闭主菜单
                
                // 进入学习界面
                StudyView studyView = new StudyView();
                // 传入 userId
                new StudyController(studyView, user); 
                studyView.setVisible(true);
            }
        });

        // 点击“复习”
        view.addReviewListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                
                ReviewView reviewView = new ReviewView();
                new ReviewController(reviewView, user);
                reviewView.setVisible(true);
                
            }
        });

        // 点击“退出登录”
        view.addLogoutListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); // 关闭主菜单
                
                // 回到登录界面
                LoginView loginView = new LoginView();
                new LoginController(loginView);
                loginView.setVisible(true);
            }
        });
    }
}
