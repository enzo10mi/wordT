/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.view;

/**
 *
 * @author yuzhe
 */

public class ReviewView extends BaseCardView {

    public ReviewView() {
        super();
    }

    @Override
    protected String getWindowTitle() {
        return "复习单词";
    }

    @Override
    protected String getModeLabelText() {
        return "正在复习";
    }
    
    // 如果复习模式有特有的逻辑，可以在这里添加
}

//import javax.swing.*;
//import javax.swing.border.EmptyBorder;
//import java.awt.*;
//import java.awt.event.ActionListener;
//
//public class ReviewView extends JFrame {
//    private JLabel lblWord;
//    private JTextArea txtMeaning;
//    
//    private JPanel bottomPanel;
//    private JButton btnKnown;
//    private JButton btnUnknown;
//    private JButton btnNext;
//
//    // 颜色定义
//    private final Color COLOR_BG = new Color(240, 242, 245); // 背景灰
//    private final Color COLOR_CARD = Color.WHITE; // 卡片白
//    private final Color COLOR_GREEN = new Color(46, 204, 113); // 认识
//    private final Color COLOR_RED = new Color(231, 76, 60);   // 不认识
//    private final Color COLOR_BLUE = new Color(52, 152, 219); // 下一个
//
//    public ReviewView() {
//        initUI();
//    }
//
//    private void initUI() {
//        setTitle("复习单词");
//        setSize(600, 500); // 宽一点
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//        getContentPane().setBackground(COLOR_BG);
//        setLayout(new BorderLayout());
//
//        // --- 顶部：进度条或标题 (可选) ---
//        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        topBar.setOpaque(false);
//        topBar.setBorder(new EmptyBorder(10, 20, 0, 0));
//        JLabel lblMode = new JLabel("正在复习");
//        lblMode.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 14));
//        lblMode.setForeground(Color.GRAY);
//        topBar.add(lblMode);
//        add(topBar, BorderLayout.NORTH);
//
//        // --- 中间：单词卡片 ---
//        // 使用一个圆角卡片面板
//        JPanel cardPanel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Graphics2D g2 = (Graphics2D) g;
//                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                g2.setColor(COLOR_CARD);
//                // 绘制圆角矩形背景
//                g2.fillRoundRect(20, 20, getWidth()-40, getHeight()-40, 30, 30);
//                // 简单的边框
//                g2.setColor(new Color(220, 220, 220));
//                g2.drawRoundRect(20, 20, getWidth()-40, getHeight()-40, 30, 30);
//            }
//        };
//        cardPanel.setOpaque(false);
//        cardPanel.setLayout(new GridBagLayout()); // 为了绝对居中
//
//        // 单词内容容器
//        JPanel wordContent = new JPanel();
//        wordContent.setLayout(new BoxLayout(wordContent, BoxLayout.Y_AXIS));
//        wordContent.setOpaque(false);
//        wordContent.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        // 单词
//        lblWord = new JLabel("Vocabulary");
//        lblWord.setFont(new Font("Arial", Font.BOLD, 48)); // 超大字体
//        lblWord.setForeground(new Color(44, 62, 80));
//        lblWord.setAlignmentX(Component.CENTER_ALIGNMENT);
//        
//        // 释义区
//        txtMeaning = new JTextArea("这里是释义区域\nn. 词汇量");
//        txtMeaning.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
//        txtMeaning.setForeground(new Color(100, 100, 100));
//        txtMeaning.setLineWrap(true);
//        txtMeaning.setWrapStyleWord(true);
//        txtMeaning.setEditable(false);
//        txtMeaning.setOpaque(false);
//        txtMeaning.setAlignmentX(Component.CENTER_ALIGNMENT);
//        // 限制释义宽度
//        txtMeaning.setSize(new Dimension(400, 100)); 
//        txtMeaning.setMaximumSize(new Dimension(400, 200));
//
//        wordContent.add(lblWord);
//        wordContent.add(Box.createVerticalStrut(30)); // 间距
//        wordContent.add(txtMeaning);
//
//        cardPanel.add(wordContent);
//        add(cardPanel, BorderLayout.CENTER);
//
//        // --- 底部：操作按钮 ---
//        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
//        bottomPanel.setOpaque(false);
//        
//        // 创建漂亮的圆形按钮
//        btnKnown = createActionButton("认识", COLOR_GREEN);
//        btnUnknown = createActionButton("不认识", COLOR_RED);
//        btnNext = createActionButton("下一个 ->", COLOR_BLUE);
//        
//        // 设置大小
//        btnNext.setPreferredSize(new Dimension(200, 50)); 
//
//        bottomPanel.add(btnUnknown); // 把不认识放左边，防误触习惯
//        bottomPanel.add(btnKnown);
//        bottomPanel.add(btnNext);
//
//        add(bottomPanel, BorderLayout.SOUTH);
//    }
//    
//    // 创建底部的大按钮
//    private JButton createActionButton(String text, Color bg) {
//        JButton btn = new JButton(text) {
//            @Override
//            protected void paintComponent(Graphics g) {
//                Graphics2D g2 = (Graphics2D) g.create();
//                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                g2.setColor(bg);
//                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40); // 胶囊形状
//                super.paintComponent(g2);
//                g2.dispose();
//            }
//        };
//        btn.setForeground(Color.WHITE);
//        btn.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 16));
//        btn.setFocusPainted(false);
//        btn.setBorderPainted(false);
//        btn.setContentAreaFilled(false);
//        btn.setPreferredSize(new Dimension(140, 50));
//        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        return btn;
//    }
//
//    // ==================== 逻辑方法 ====================
//
//     public void switchMode(boolean isDecisionPhase) {
//        if (isDecisionPhase) {
//            // 答题阶段
//            txtMeaning.setVisible(false);
//            btnKnown.setVisible(true);
//            btnUnknown.setVisible(true);
//            btnNext.setVisible(false);
//        } else {
//            // 结果阶段
//            txtMeaning.setVisible(true);
//            btnKnown.setVisible(false);   
//            btnUnknown.setVisible(false);
//            btnNext.setVisible(true);
//        }
//        bottomPanel.revalidate();
//        bottomPanel.repaint();
//    }
//
//    public void setWord(String text) { lblWord.setText(text); }
//    public void setMeaning(String text) { txtMeaning.setText(text); }
//
//    public void addKnownListener(ActionListener l) { btnKnown.addActionListener(l); }
//    public void addUnknownListener(ActionListener l) { btnUnknown.addActionListener(l); }
//    public void addNextListener(ActionListener l) { btnNext.addActionListener(l); }
//}