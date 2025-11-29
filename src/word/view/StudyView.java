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

    // 声明组件
    private JLabel lblWord;          // 显示单词
    private JTextArea txtMeaning;    // 显示释义 (使用TextArea支持多行)
    private JButton btnShowMeaning;  // 查看释义按钮
    private JButton btnNext;         // 下一个按钮
    private JLabel lblProgress;      // 进度提示 (如: 1/20)

    public StudyView() {
        // 窗口基本属性
        setTitle("背词软件 - 学习模式");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口退出程序
        setLocationRelativeTo(null); // 居中显示
        setLayout(new BorderLayout(10, 10)); // 边界布局，间距10

        initCenterPanel();
        initBottomPanel();
    }

    // 中间区域（单词和释义）
    private void initCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // 垂直排列
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20)); // 内边距

        // --- 单词部分 ---
        lblWord = new JLabel("Word Loading...");
        lblWord.setFont(new Font("Arial", Font.BOLD, 40)); // 大字体
        lblWord.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中

        // --- 释义部分 ---
        txtMeaning = new JTextArea("Meaning here...");
        txtMeaning.setFont(new Font("SimHei", Font.PLAIN, 18)); // 中文字体
        txtMeaning.setLineWrap(true);       // 自动换行
        txtMeaning.setWrapStyleWord(true);
        txtMeaning.setEditable(false);      // 不可编辑
        txtMeaning.setOpaque(false);        // 透明背景，像Label一样
        txtMeaning.setBackground(new Color(0,0,0,0));
        txtMeaning.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtMeaning.setVisible(false);       // 默认隐藏释义

        // 将组件加入面板
        centerPanel.add(lblWord);
        centerPanel.add(Box.createVerticalStrut(30)); // 增加30像素的垂直间距
        centerPanel.add(txtMeaning);

        // 将面板加入窗口中间
        this.add(centerPanel, BorderLayout.CENTER);
    }

    // 底部区域（按钮）
    private void initBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnShowMeaning = new JButton("查看释义");
        btnShowMeaning.setPreferredSize(new Dimension(120, 40));
        btnShowMeaning.setFont(new Font("SimHei", Font.PLAIN, 14));

        btnNext = new JButton("下一个 / 认识");
        btnNext.setPreferredSize(new Dimension(120, 40));
        btnNext.setFont(new Font("SimHei", Font.PLAIN, 14));

        // 进度标签
        lblProgress = new JLabel("进度: --/--");

        bottomPanel.add(lblProgress);
        bottomPanel.add(btnShowMeaning);
        bottomPanel.add(btnNext);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    // ================= 提供给 Controller 调用的方法 =================

    public void setWordText(String text) {
        lblWord.setText(text);
    }

    public void setMeaningText(String text) {
        txtMeaning.setText(text);
    }

    public void setMeaningVisible(boolean visible) {
        txtMeaning.setVisible(visible);
        // 重新绘制一下，防止界面刷新不及时
        this.revalidate();
        this.repaint();
    }
    
    public void updateProgress(int current, int total) {
        lblProgress.setText("进度: " + current + "/" + total);
    }

    // 绑定事件监听器
    public void addShowMeaningListener(ActionListener listener) {
        btnShowMeaning.addActionListener(listener);
    }

    public void addNextListener(ActionListener listener) {
        btnNext.addActionListener(listener);
    }

    // 弹窗提示
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
