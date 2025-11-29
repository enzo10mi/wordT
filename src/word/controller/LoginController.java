package word.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import word.dao.UserDAO;
import word.dao.StudyrecordDAO;
import word.model.User;
import word.view.LoginView;
import word.view.StudyView;

// 需要导入StudyController才能在登录成功后启动它
// import word.controller.StudyController; // 如果有，请取消注释

public class LoginController implements ActionListener {
    
    private final LoginView loginView;
    private final UserDAO userDAO;
    private final StudyrecordDAO studyrecordDAO;

    public LoginController(LoginView view) {
        this.loginView = view;
        this.userDAO = new UserDAO();
        this.studyrecordDAO = new StudyrecordDAO();
        
        // 绑定按钮事件监听器
        loginView.addLoginListener(this);
        loginView.addRegisterListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String username = loginView.getUsername();
        String password = new String(loginView.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "用户名和密码不能为空！", "错误", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("登录".equals(command)) {
            handleLogin(username, password);
        } else if ("注册".equals(command)) {
            handleRegister(username, password);
        }
    }

    /**
     * 处理登录逻辑
     */
    private void handleLogin(String username, String password) {
        User user = userDAO.login(username, password);
        
        if (user != null) {
            JOptionPane.showMessageDialog(loginView, "登录成功，欢迎 " + user.getUsername() + "！");
            
            // 1. 登录成功后，为新用户初始化学习记录（如果之前没初始化过）
            // studyrecordDAO.initRecords(user.getId());
            
            // 2. 启动学习界面
            startStudyModule(user);
            
        } else {
            JOptionPane.showMessageDialog(loginView, "登录失败，用户名或密码错误。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 处理注册逻辑
     */
    private void handleRegister(String username, String password) {
        User newUser = new User(username, password);
        boolean success = userDAO.register(newUser);
        
        if (success) {
            JOptionPane.showMessageDialog(loginView, "注册成功，请使用该账号登录！");
            
            // 注册成功后，直接登录获取 ID，并初始化学习记录
            User registeredUser = userDAO.login(username, password);
            if (registeredUser != null) {
                // 3. 注册成功后，初始化该用户的所有单词记录
                studyrecordDAO.initRecords(registeredUser.getId());
                System.out.println("用户 " + registeredUser.getUsername() + " 单词本初始化完成。");
            }
            
        } else {
            JOptionPane.showMessageDialog(loginView, "注册失败，用户名可能已被占用。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 启动学习模块，并将用户数据传递给 StudyController
     */
    private void startStudyModule(User user) {
        // 关闭登录窗口
        loginView.dispose(); 
        
        // 启动学习窗口
        StudyView studyView = new StudyView();
        // 假设StudyController有一个接受 User 对象的构造函数
        new StudyController(studyView, user); 
        studyView.setVisible(true);
    }
}