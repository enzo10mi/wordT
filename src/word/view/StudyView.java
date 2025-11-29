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

public class StudyView extends JFrame {
    private JLabel lblWord;          // 单词
    private JTextArea txtMeaning;    // 释义
    
    // 底部按钮容器
    private JPanel bottomPanel;      // 放按钮的容器
    private JButton btnKnown;        // 认识
    private JButton btnUnknown;      // 不认识
    private JButton btnNext;         // 下一个

    public StudyView() {
        initUI();
    }

    private void initUI() {
        setTitle("背单词 - 学习");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 中间区域 (单词 + 释义) ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));

        lblWord = new JLabel("Word");
        lblWord.setFont(new Font("Arial", Font.BOLD, 32));
        lblWord.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtMeaning = new JTextArea("Meaning");
        txtMeaning.setFont(new Font("SimHei", Font.PLAIN, 18));
        txtMeaning.setLineWrap(true);
        txtMeaning.setWrapStyleWord(true);
        txtMeaning.setEditable(false);
        txtMeaning.setOpaque(false); // 透明背景
        txtMeaning.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        centerPanel.add(lblWord);
        centerPanel.add(Box.createVerticalStrut(40)); // 间距
        centerPanel.add(txtMeaning);

        this.add(centerPanel, BorderLayout.CENTER);

        // --- 底部区域 (按钮) ---
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        btnKnown = new JButton("我认识");
        btnUnknown = new JButton("不认识");
        btnNext = new JButton("下一个");
        
        // 样式微调
        Dimension btnSize = new Dimension(100, 40);
        btnKnown.setPreferredSize(btnSize);
        btnUnknown.setPreferredSize(btnSize);
        btnNext.setPreferredSize(new Dimension(200, 40)); // "下一个"按钮大一点

        bottomPanel.add(btnKnown);
        bottomPanel.add(btnUnknown);
        bottomPanel.add(btnNext);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    // ==================== 核心逻辑方法 ====================

    /**
     * 切换视图模式
     * @param isDecisionPhase 
     *    true:  答题阶段 (显示 认识/不认识，隐藏 释义，隐藏 下一个)
     *    false: 结果阶段 (隐藏 认识/不认识，显示 释义，显示 下一个)
     */
     public void switchMode(boolean isDecisionPhase) {
        if (isDecisionPhase) {
            // 状态A：刚看到单词
            txtMeaning.setVisible(false);
            btnKnown.setVisible(true);
            btnUnknown.setVisible(true);
            btnNext.setVisible(false);        // 藏下一个
        } else {
            // 状态B：看答案
            txtMeaning.setVisible(true);
        
            // 如果你希望点完还在，就把下面两行注释掉。
            // 但通常为了防误触，我们是会让它们消失的。
            btnKnown.setVisible(false);   
            btnUnknown.setVisible(false);
        
            btnNext.setVisible(true);         // 显下一个
        }
        // 刷新布局，防止按钮隐藏后位置没更新
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    // 设置文本
    public void setWord(String text) { lblWord.setText(text); }
    public void setMeaning(String text) { txtMeaning.setText(text); }

    // 监听器绑定
    public void addKnownListener(ActionListener l) { btnKnown.addActionListener(l); }
    public void addUnknownListener(ActionListener l) { btnUnknown.addActionListener(l); }
    public void addNextListener(ActionListener l) { btnNext.addActionListener(l); }
}