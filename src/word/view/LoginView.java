/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.view;

/**
 *
 * @author yuzhe
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister; // 预留注册按钮

    public LoginView() {
        setTitle("用户登录");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // 网格包布局，适合表单

        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 组件之间的间距
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一行：用户名标签
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("用户名:"), gbc);

        // 第一行：用户名输入框
        gbc.gridx = 1; gbc.gridy = 0;
        txtUsername = new JTextField(15);
        add(txtUsername, gbc);

        // 第二行：密码标签
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("密  码:"), gbc);

        // 第二行：密码输入框
        gbc.gridx = 1; gbc.gridy = 1;
        txtPassword = new JPasswordField(15);
        add(txtPassword, gbc);

        // 第三行：按钮面板
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; // 占两列
        JPanel buttonPanel = new JPanel();
        
        btnLogin = new JButton("登录");
        btnRegister = new JButton("注册");
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);
        add(buttonPanel, gbc);
    }

    // ================= 提供给 Controller 的方法 =================

    public String getUsername() {
        return txtUsername.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.setActionCommand("登录");
        btnLogin.addActionListener(listener);
    }

// 供 Controller 调用，绑定注册事件
    public void addRegisterListener(ActionListener listener) {
        btnRegister.setActionCommand("注册");
        btnRegister.addActionListener(listener);
    }

    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "错误", JOptionPane.ERROR_MESSAGE);
    }
}
