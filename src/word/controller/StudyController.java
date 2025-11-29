/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.controller;

/**
 *
 * @author yuzhe
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.List;
import word.dao.*;
import word.model.Word;
import word.view.StudyView;
import word.model.User;

public class StudyController {
    private StudyView view;
    private WordDAO wordDAO;
    private StudyrecordDAO recordDAO;
    
    // 数据状态
    private List<Word> wordList;
    private int currentIndex = 0;
    
    private int currentUserId; // 【新增】当前登录用户的ID
    // 临时记录当前单词是否认识（可选，用于存入数据库）
    private boolean isCurrentWordKnown = false;  

    public StudyController(StudyView view, User user) {
        this.view = view;
        this.wordDAO = new WordDAO();
        this.recordDAO = new StudyrecordDAO(); // 【新增】初始化 DAO
        this.currentUserId = user.getId(); // 【新增】保存用户ID
        
        // 1. 获取数据
        loadData();
        
        // 2. 绑定事件
        initActions();
        
        // 3. 显示第一个单词
        showCurrentWord();
        System.out.println("read:"+wordList.size());
    }

    private void loadData() {
        // 假设这里取20个
        wordList = wordDAO.getStudyList(1, 20); 
        if (wordList.isEmpty()) {
            JOptionPane.showMessageDialog(view, "恭喜！您已学完所有单词或词库为空！");
            // 这里可以添加逻辑，比如加载下一个词库
        }
    }

    // 核心逻辑：显示当前单词，重置界面为“答题模式”
    private void showCurrentWord() {
        if (currentIndex < wordList.size()) {
            Word w = wordList.get(currentIndex);
            
            view.setWord(w.getEnglish());
            view.setMeaning(w.getChinese());
            
            System.out.println("抽中: " + w.getEnglish() + " (" + w.getChinese() + ")");
            
            
            // *** 关键：切换到答题模式 (藏释义，显选项) ***
            view.switchMode(true); 
        } else {
            javax.swing.JOptionPane.showMessageDialog(view, "背词完成！");
            // 可以在这里处理结束逻辑，比如关闭窗口
        }
    }

    /**
     * 绑定事件
     */
    private void initActions() {
        
        // --- 用户点击 "我认识" ---
        view.addKnownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCurrentWordKnown = true;
                showAnswerPhase(); // 进入阶段二
            }
        });

        // --- 用户点击 "不认识" ---
        view.addUnknownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCurrentWordKnown = false;
                showAnswerPhase(); // 进入阶段二
            }
        });

        // --- 用户点击 "下一个" ---
        view.addNextListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndNext();
            }
        });
    }

    /**
     * 逻辑2：显示答案（阶段二）
     */
    private void showAnswerPhase() {
        // 这里可以根据 isCurrentWordKnown 做一些额外操作
        // 比如如果认识，释义显示黑色；不认识，释义显示红色
        
        // *** 关键：切换到“答案模式”，true表示显示答案 ***
        view.switchMode(false);
    }

    /**
     * 逻辑3：保存并切换到下一个
     */
    private void saveAndNext() {
        Word w = wordList.get(currentIndex);
        
        // 1. 保存学习记录到数据库
        // 参数：用户ID, 单词ID, 是否认识(用于计算艾宾浩斯或简单记录)
        // recordDAO.addRecord(currentUserId, w.getId(), isCurrentWordKnown);
        System.out.println("保存记录: 单词=" + w.getEnglish() + ", 认识=" + isCurrentWordKnown);

        // 2. 下标+1
        currentIndex++;
        
        // ... 前面的代码 ...

    // 3. 判断是否背完
    if (currentIndex < wordList.size()) {
        showCurrentWord(); // 显示下一个（回到阶段一）
    } else {
        // 提示用户
        JOptionPane.showMessageDialog(view, "恭喜！本组单词背诵完成！");
        
        // --- 核心代码开始 ---
        
        // 1. 关闭当前的背词窗口
        // dispose() 会释放窗口占用的资源，完全关闭它
        //view.dispose(); 
        
        // 2. 准备回到主菜单
        // 假设你的主菜单View叫 MainView
        //MainView mainView = new MainView();
        
        // 3. 启动主菜单的控制器
        // 注意：通常需要把 currentUser (当前用户) 传回去，不然主菜单不知道是谁登录的
        //new MainController(mainView, currentUser); // 假设你有这个控制器
        
        // 4. 显示主菜单
        //mainView.setVisible(true);
        
        // --- 核心代码结束 ---
    }

    }
    
    public void showView() {
        view.setVisible(true);
    }
}