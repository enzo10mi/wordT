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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JLabel lblWelcome;
    private JButton btnSelectbook;
    private JButton btnStartStudy;
    private JButton btnReview;
    private JButton btnLogout;

    public MainView() {
        initUI();
    }

    private void initUI() {
        setTitle("背词助手 - 首页");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // 全局背景
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // --- 顶部 Header ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(51, 153, 255)); // 顶部蓝色背景
        headerPanel.setPreferredSize(new Dimension(getWidth(), 120));
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        lblWelcome = new JLabel("Hi, 欢迎回来");
        lblWelcome.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 24));
        lblWelcome.setForeground(Color.WHITE);
        
        JLabel lblSub = new JLabel("今天也要坚持学习哦！");
        lblSub.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(220, 240, 255));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(lblWelcome);
        textPanel.add(lblSub);

        headerPanel.add(textPanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // --- 中间内容区 (Grid Layout) ---
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 15, 15)); // 3行1列，间距15
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        contentPanel.setBackground(Color.WHITE);

        // 创建大卡片按钮
        btnSelectbook = createCardButton("更换词书", "当前词库: 尚未选择", new Color(241, 196, 15), "book_icon");
        btnStartStudy = createCardButton("开始新词", "挑战新的单词", new Color(52, 152, 219), "study_icon");
        btnReview = createCardButton("复习错题", "巩固记忆曲线", new Color(46, 204, 113), "review_icon");

        contentPanel.add(btnSelectbook);
        contentPanel.add(btnStartStudy);
        contentPanel.add(btnReview);

        add(contentPanel, BorderLayout.CENTER);

        // --- 底部 ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(0,0,20,0));
        btnLogout = new JButton("退出登录");
        btnLogout.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        btnLogout.setForeground(Color.GRAY);
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        bottomPanel.add(btnLogout);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 创建带有标题和副标题的漂亮按钮
    private JButton createCardButton(String title, String sub, Color accentColor, String iconName) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 绘制圆角背景
                g2.setColor(new Color(248, 250, 252)); // 很淡的灰
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // 绘制左侧颜色条
                g2.setColor(accentColor);
                g2.fillRoundRect(0, 0, 10, getHeight(), 15, 15);
                g2.fillRect(5, 0, 10, getHeight()); // 补齐左边直角

                super.paintComponent(g2);
                g2.dispose();
            }
        };
        
        btn.setLayout(new BorderLayout());
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(60,60,60));
        
        JLabel lblSub = new JLabel(sub);
        lblSub.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);

        JPanel pText = new JPanel(new GridLayout(2, 1));
        pText.setOpaque(false);
        pText.add(lblTitle);
        pText.add(lblSub);

        // 右侧箭头
        JLabel arrow = new JLabel(">");
        arrow.setFont(new Font("Arial", Font.BOLD, 20));
        arrow.setForeground(Color.LIGHT_GRAY);
        arrow.setBorder(new EmptyBorder(0,0,0,10));

        btn.add(pText, BorderLayout.CENTER);
        btn.add(arrow, BorderLayout.EAST);

        return btn;
    }

    public void setWelcomeText(String text) {
        // 提取用户名，如果有的话
        lblWelcome.setText("Hi, " + text);
    }

    public void addStartStudyListener(ActionListener l) { btnStartStudy.addActionListener(l); }
    public void addReviewListener(ActionListener l) { btnReview.addActionListener(l); }
    public void addLogoutListener(ActionListener l) { btnLogout.addActionListener(l); }
    public void addSelectBookListener(ActionListener l) { btnSelectbook.addActionListener(l); }
}
