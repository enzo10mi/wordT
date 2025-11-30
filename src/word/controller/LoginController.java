package word.controller;

import word.view.LoginView;
import word.view.RegisterView;
import word.view.MainView; // 主背词界面
import word.dao.UserDAO;
import word.dao.WordDAO; // 引入 WordDAO 用于查书
import word.model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane; // 引入弹窗

public class LoginController {
    private LoginView view;
    private UserDAO userDAO;

    public LoginController(LoginView view) {
        this.view = view;
        this.userDAO = new UserDAO();
        initActions();
    }

    private void initActions() {
        // 处理登录按钮
        view.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // 处理注册按钮 (跳转)
        view.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToRegister();
            }
        });
    }

    private void login() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            view.showErrorMessage("用户名或密码不能为空");
            return;
        }

        // 数据库校验
        User user = userDAO.login(username, password); // 假设DAO返回User对象，失败返回null
        
        if (user != null) {
            // 登录成功，关闭登录窗
            view.dispose(); 
            
            // 默认选书
            String selectedBook = "四级词汇";
            
            MainView mainView = new MainView();
            // 关键：要把当前登录的 User 和 选中的书名 传给下一个控制器
            // (MainController 构造函数需要对应修改)
            new MainController(mainView, user, selectedBook); 
            mainView.setVisible(true);
            
        } else {
            view.showErrorMessage("用户名或密码错误！");
        }
    }
    

    private void goToRegister() {
        view.dispose(); // 关闭登录窗
        
        // 打开注册窗
        RegisterView regView = new RegisterView();
        new RegisterController(regView); // 启动注册控制器
        regView.setVisible(true);
    }
}