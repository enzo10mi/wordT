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
    private JPasswordField txtConfirmPassword;
    private JButton btnSubmit;
    private JButton btnBack;

    private final Color BG_COLOR = new Color(245, 247, 250);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final int FORM_WIDTH = 280; // 固定宽度

    public RegisterView() {
        setTitle("新用户注册");
        setSize(400, 550); // 稍微高一点
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);
        
        // GridBagLayout 居中
        setLayout(new GridBagLayout());

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("创建账号");
        title.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(50, 50, 50));
        mainPanel.add(title);
        
        mainPanel.add(Box.createVerticalStrut(30));

        // 表单项
        mainPanel.add(createFormItem("用户名", txtUsername = createStyledField()));
        mainPanel.add(Box.createVerticalStrut(10));
        
        mainPanel.add(createFormItem("设置密码", txtPassword = createStyledPasswordField()));
        mainPanel.add(Box.createVerticalStrut(10));
        
        mainPanel.add(createFormItem("确认密码", txtConfirmPassword = createStyledPasswordField()));
        mainPanel.add(Box.createVerticalStrut(30));

        // 按钮
        btnSubmit = createModernButton("立即注册", ACCENT_COLOR, Color.WHITE);
        btnBack = createModernButton("返回登录", new Color(230, 230, 230), Color.GRAY);
        
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(btnSubmit);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnBack);

        add(mainPanel);
    }

    // 辅助方法：创建固定宽度的行
    private JPanel createFormItem(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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

    private JTextField createStyledField() {
        JTextField f = new JTextField();
        styleComponent(f);
        return f;
    }
    private JPasswordField createStyledPasswordField() {
        JPasswordField f = new JPasswordField();
        styleComponent(f);
        return f;
    }
    private void styleComponent(JTextField f) {
        // 核心修正：限制最大宽度
        Dimension size = new Dimension(FORM_WIDTH, 40);
        f.setMaximumSize(size);
        f.setPreferredSize(size);
        
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
    
        private JButton createModernButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            // 重写点击检测范围
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

                // 增加按下的视觉反馈
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

                // 调用父类绘制文字
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

    // Getters & Listeners 保持不变
    public String getUsername() { return txtUsername.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getConfirmPassword() { return new String(txtConfirmPassword.getPassword()); }
    public void addSubmitListener(ActionListener l) { btnSubmit.addActionListener(l); }
    public void addBackListener(ActionListener l) { btnBack.addActionListener(l); }
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }
}