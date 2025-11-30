package word.controller;

import word.view.LoginView;
import word.view.RegisterView;
import word.view.MainView; // 主背词界面
import word.dao.UserDAO;
import word.dao.WordDAO; // 【新增】引入 WordDAO 用于查书
import word.model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane; // 【新增】引入弹窗

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
            
            // --- 【核心修改】登录成功后，弹出词书选择框 ---
            String selectedBook = selectBook();
            
            MainView mainView = new MainView();
            // 关键：要把当前登录的 User 和 选中的书名 传给下一个控制器
            // (MainController 构造函数需要对应修改)
            new MainController(mainView, user, selectedBook); 
            mainView.setVisible(true);
            
        } else {
            view.showErrorMessage("用户名或密码错误！");
        }
    }
    
    // 【新增】私有方法：处理选书逻辑
    private String selectBook() {
        WordDAO wordDAO = new WordDAO();
        // 1. 从数据库获取所有可用的词书分类
        List<String> books = wordDAO.getAllCategories();
        
        // 如果数据库没书（理论上 RunApp 已初始化，不会空），给个默认值
        if (books.isEmpty()) return "四级词汇";
        
        // 2. 弹窗让用户选择
        Object[] possibilities = books.toArray();
        String s = (String) JOptionPane.showInputDialog(
                            null,
                            "欢迎回来！\n请选择您本学期要学习的词书:",
                            "选择词书",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            possibilities[0]);

        // 如果用户点了取消或关闭，默认选第一本，防止程序崩溃
        if ((s != null) && (s.length() > 0)) {
            return s;
        }
        return books.get(0);
    }

    private void goToRegister() {
        view.dispose(); // 关闭登录窗
        
        // 打开注册窗
        RegisterView regView = new RegisterView();
        new RegisterController(regView); // 启动注册控制器
        regView.setVisible(true);
    }
}