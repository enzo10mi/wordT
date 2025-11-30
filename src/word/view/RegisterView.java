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

public class RegisterView extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword; // 确认密码框
    private JButton btnSubmit; // 提交注册
    private JButton btnBack;   // 返回登录

    public RegisterView() {
        setTitle("新用户注册");
        setSize(400, 350); // 稍微高一点
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. 用户名
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("用户名:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        txtUsername = new JTextField(15);
        add(txtUsername, gbc);

        // 2. 密码
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("设置密码:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        txtPassword = new JPasswordField(15);
        add(txtPassword, gbc);
        
        // 3. 确认密码 
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("确认密码:"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        txtConfirmPassword = new JPasswordField(15);
        add(txtConfirmPassword, gbc);

        // 4. 按钮
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel();
        
        btnSubmit = new JButton("确认注册");
        btnBack = new JButton("返回登录");
        
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnBack);
        add(buttonPanel, gbc);
    }

    // --- Getters ---
    public String getUsername() { return txtUsername.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getConfirmPassword() { return new String(txtConfirmPassword.getPassword()); }

    // --- Listeners ---
    public void addSubmitListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
