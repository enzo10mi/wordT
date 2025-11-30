package word.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import word.dao.UserDAO;
import word.dao.StudyrecordDAO;
import word.model.User;
import word.view.LoginView;
import word.view.StudyView;

// 【修复1】必须导入 StudyController，否则无法启动它
import word.controller.StudyController; 

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
            
            // 启动学习界面
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
                // 初始化该用户的所有单词记录 (虽然 StudyController 里也会做，但这里做一次更保险)
                studyrecordDAO.initRecords(registeredUser.getId());
                System.out.println("用户 " + registeredUser.getUsername() + " 单词本初始化完成。");
                
                // 注册完直接进入学习
                startStudyModule(registeredUser);
            }
            
        } else {
            JOptionPane.showMessageDialog(loginView, "注册失败，用户名可能已被占用。", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 启动学习模块
     */
    private void startStudyModule(User user) {
        // 关闭登录窗口
        loginView.dispose(); 
        
        // 【核心修复】
        // 因为我们没有主菜单界面，所以在这里弹一个框询问用户想做什么
        Object[] options = {"学习新词", "复习错题"};
        int choice = JOptionPane.showOptionDialog(null,
                "请选择您想要进行的模式：",
                "模式选择",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        // choice = 0 是学习新词 (false), choice = 1 是复习错题 (true)
        // 如果用户直接关掉窗口，默认选学习 (false)
        boolean isReviewMode = (choice == 1);

        // 创建 View
        StudyView studyView = new StudyView();
        
        // 【修复2】调用新的构造函数，传入 3 个参数：(view, user, isReviewMode)
        StudyController controller = new StudyController(studyView, user, isReviewMode);
        
        // 显示界面
        controller.showView();
    }
}