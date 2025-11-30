/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.controller;

/**
 *
 * @author yuzhe
 */

import word.view.RegisterView;
import word.view.LoginView;
import word.dao.UserDAO;
import word.model.User; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController {
    private RegisterView view;
    private UserDAO userDAO;

    public RegisterController(RegisterView view) {
        this.view = view;
        this.userDAO = new UserDAO();
        initActions();
    }

    private void initActions() {
        // 点击注册按钮
        view.addSubmitListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        // 点击返回按钮
        view.addBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBackToLogin();
            }
        });
    }

    private void register() {
        String username = view.getUsername();
        String password = view.getPassword();
        String confirm = view.getConfirmPassword(); 

        if (username.isEmpty() || password.isEmpty()) {
            view.showMessage("用户名和密码不能为空！");
            return;
        }
        // 确认密码
        if (!password.equals(confirm)) {
            view.showMessage("两次输入的密码不一致！");
            return;
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        // 调用 DAO
        boolean success = userDAO.register(newUser); 
        
        if (success) {
            view.showMessage("注册成功！请登录。");
            goBackToLogin(); // 注册成功后自动跳回登录页
        } else {
            view.showMessage("注册失败，用户名可能已存在。");
        }
    }

    private void goBackToLogin() {
        view.dispose(); // 关闭注册窗口
        
        // 打开登录窗口
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true);
    }
}