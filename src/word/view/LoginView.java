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
    private JButton btnRegister;

    // 配色方案
    private final Color BG_COLOR = new Color(245, 247, 250); 
    private final Color PRIMARY_COLOR = new Color(51, 153, 255); 
    private final int FORM_WIDTH = 280; // 设定表单的固定宽度

    public LoginView() {
        setTitle("用户登录");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 1. 设置背景色
        getContentPane().setBackground(BG_COLOR);
        
        // 2. 使用 GridBagLayout 让整个内容面板在窗口中绝对居中
        setLayout(new GridBagLayout());

        initUI();
    }

    private void initUI() {
        // 创建一个主内容面板，垂直排列
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG_COLOR);
        // 关键：不让面板无限变大，也不让它贴边
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- 标题 ---
        JLabel titleLabel = new JLabel("Word Master");
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        mainPanel.add(titleLabel);
        
        JLabel subTitle = new JLabel("欢迎回来，请登录");
        subTitle.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        subTitle.setForeground(Color.GRAY);
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(subTitle);

        mainPanel.add(Box.createVerticalStrut(30));

        // --- 用户名区域 ---
        mainPanel.add(createFormItem("账号", txtUsername = createStyledTextField()));
        mainPanel.add(Box.createVerticalStrut(15));

        // --- 密码区域 ---
        mainPanel.add(createFormItem("密码", txtPassword = createStyledPasswordField()));
        mainPanel.add(Box.createVerticalStrut(30));

        // --- 按钮 ---
        btnLogin = createModernButton("登 录", PRIMARY_COLOR, Color.WHITE);
        btnRegister = createModernButton("注册账号", new Color(230, 230, 230), Color.GRAY);

        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(btnLogin);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnRegister);

        // 将主面板添加到窗口中心
        add(mainPanel);
    }

    // 创建一个包含 Label 和 Field 的小容器，宽度固定
    private JPanel createFormItem(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        // 关键：强制设置最大宽度，防止越界
        Dimension dim = new Dimension(FORM_WIDTH, 70);
        panel.setPreferredSize(dim);
        panel.setMaximumSize(dim);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 13));
        label.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        field.setAlignmentX(Component.LEFT_ALIGNMENT); 

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        return panel;
    }

    // --- 样式辅助方法 ---

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        styleField(field);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        styleField(field);
        return field;
    }

    private void styleField(JTextField field) {
        // 限制输入框最大宽度
        Dimension fixedSize = new Dimension(FORM_WIDTH, 40);
        field.setMaximumSize(fixedSize); 
        field.setPreferredSize(fixedSize);
        
        field.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private JButton createModernButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            // 1. 【核心修复】重写点击检测范围
            // 告诉 Swing：只要在这个形状内点击，都算点到了按钮，不要穿透过去
            @Override
            public boolean contains(int x, int y) {
                if (getWidth() <= 0 || getHeight() <= 0) {
                    return false;
                }
                // 定义按钮的形状（圆角矩形）
                Shape shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20);
                return shape.contains(x, y);
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 2. 【体验优化】增加按下的视觉反馈
                // 如果按钮被按下，颜色变深一点，否则用原色
                if (getModel().isPressed()) {
                    g2.setColor(bg.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bg.brighter()); // 鼠标悬停变亮（可选）
                } else {
                    g2.setColor(bg);
                }

                // 绘制背景
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose(); // 画完背景销毁临时的 Graphics2D

                // 3. 调用父类绘制文字
                // 注意：要在画完背景后调用，否则文字会被背景盖住
                super.paintComponent(g);
            }
        };

        // 基础样式设置
        btn.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
        btn.setForeground(fg);
        
        // 关键设置：禁用默认绘制，完全由上面 paintComponent 接管
        btn.setOpaque(false); 
        btn.setContentAreaFilled(false); 
        btn.setFocusPainted(false); 
        btn.setBorderPainted(false); 
        
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        
        // 设置固定大小
        Dimension btnSize = new Dimension(FORM_WIDTH, 40);
        btn.setPreferredSize(btnSize);
        btn.setMaximumSize(btnSize);
        
        return btn;
    }

    // ================= 提供给 Controller 的方法 =================

    public String getUsername() { return txtUsername.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    
    public void addLoginListener(ActionListener listener) {
        btnLogin.setActionCommand("登录");
        btnLogin.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        btnRegister.setActionCommand("注册");
        btnRegister.addActionListener(listener);
    }

    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.ERROR_MESSAGE);
    }
}