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

public class MainView extends JFrame {

    private JLabel lblWelcome;
    private JButton btnStartStudy;  // 开始学习
    private JButton btnReview;      // 复习
    private JButton btnLogout;      // 退出登录

    public MainView() {
        initUI();
    }

    private void initUI() {
        setTitle("英语背词助手 - 主菜单");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 顶部：欢迎信息 ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        lblWelcome = new JLabel("欢迎使用！");
        lblWelcome.setFont(new Font("SimHei", Font.BOLD, 20));
        topPanel.add(lblWelcome);
        add(topPanel, BorderLayout.NORTH);

        // --- 中间：功能按钮 ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        btnStartStudy = new JButton("开始学习 (新词)");
        btnReview = new JButton("复习 (错题/已学)");
        btnLogout = new JButton("退出登录");

        // 设置按钮大小一致
        Dimension btnSize = new Dimension(200, 50);
        btnStartStudy.setMaximumSize(btnSize);
        btnReview.setMaximumSize(btnSize);
        btnLogout.setMaximumSize(btnSize);
        
        // 居中对齐
        btnStartStudy.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReview.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 添加到面板（加一点间距）
        centerPanel.add(Box.createVerticalGlue()); // 顶开
        centerPanel.add(btnStartStudy);
        centerPanel.add(Box.createVerticalStrut(30)); // 间距
        centerPanel.add(btnReview);
        centerPanel.add(Box.createVerticalStrut(30)); // 间距
        centerPanel.add(btnLogout);
        centerPanel.add(Box.createVerticalGlue()); // 底开

        add(centerPanel, BorderLayout.CENTER);
    }

    // --- 方法：设置欢迎语 ---
    public void setWelcomeText(String text) {
        lblWelcome.setText(text);
    }

    // --- 方法：添加监听器 ---
    public void addStartStudyListener(ActionListener l) {
        btnStartStudy.addActionListener(l);
    }

    public void addReviewListener(ActionListener l) {
        btnReview.addActionListener(l);
    }

    public void addLogoutListener(ActionListener l) {
        btnLogout.addActionListener(l);
    }
}
