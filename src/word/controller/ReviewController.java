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
import java.util.List;
import javax.swing.JOptionPane;
import word.dao.StudyrecordDAO;
import word.dao.WordDAO;
import word.model.User;
import word.model.Word;
import word.view.MainView;
import word.view.ReviewView;

public class ReviewController {
    private ReviewView view;
    private User user;
    private int currentUserId; 
    private WordDAO wordDAO;
    private StudyrecordDAO recordDAO;
    
    // 数据状态
    private List<Word> wordList;
    private int currentIndex = 0;
    
    private boolean isCurrentWordKnown = false; 

    /**
     * 构造函数
     * @param view 视图界面
     * @param user 当前登录用户
     */
    public ReviewController(ReviewView view, User user) {
        this.view = view;
        this.user = user;
        this.currentUserId = user.getId();
        
        // 初始化 DAO
        this.wordDAO = new WordDAO();
        this.recordDAO = new StudyrecordDAO(); 
        
        // 1. 确保用户在记录表里有数据 (如果是新注册用户)
        recordDAO.initRecords(currentUserId);
        
        // 2. 加载数据 (根据模式不同，加载的数据也不同)
        loadData();
        
        // 3. 绑定按钮事件
        initActions();
        
        // 4. 设置窗口标题
        view.setTitle("背单词 - 复习错题本"); // 标题也可以稍微改一下
        
        // 5. 显示第一个单词
        if (wordList != null && !wordList.isEmpty()) {
            showCurrentWord();
        }
    }

    /**
     * 加载数据核心逻辑
     */
    private void loadData() {
        wordList = wordDAO.getReviewList(currentUserId);
        
        if (wordList.isEmpty()) {
            System.out.println("警告：进入学习界面但列表为空");
            return;
        }
    }

    // 显示当前单词
    private void showCurrentWord() {
        if (currentIndex < wordList.size()) {
            Word w = wordList.get(currentIndex);
            view.setWord(w.getEnglish());
            view.setMeaning(w.getChinese());
            view.switchMode(true); // 进入答题阶段
        } else {
            JOptionPane.showMessageDialog(view, "本组复习完成！");
            view.dispose(); 
            // 这里你可以选择是否要在复习完一组后跳回主菜单，或者直接关闭
            gobackToMain();
        }
    }

    // 绑定按钮事件
    private void initActions() {
        // "我认识" 按钮
        view.addKnownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCurrentWordKnown = true;
                showAnswerPhase(); 
            }
        });

        // "不认识" 按钮
        view.addUnknownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCurrentWordKnown = false;
                showAnswerPhase(); 
            }
        });

        // "下一个" 按钮
        view.addNextListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndNext();
            }
        });
    }

    // 显示答案阶段
    private void showAnswerPhase() {
        view.switchMode(false); 
    }

    // 保存并进入下一个
    private void saveAndNext() {
        if (wordList == null || wordList.isEmpty()) return;

        Word w = wordList.get(currentIndex);
        
        // 保存到数据库
        // 此操作会将 is_studied 设为 true，并更新 known 状态
        // 如果用户点了"认识"(true)，下次这个词就不会出现在复习列表里了
        // 如果点了"不认识"(false)，下次它还会出现
        recordDAO.updateStatus(currentUserId, w.getId(), isCurrentWordKnown);
        
        System.out.println("已保存: " + w.getEnglish() + " | 认识: " + isCurrentWordKnown);

        currentIndex++;
        
        // 检查是否还有下一个
        if (currentIndex < wordList.size()) {
            showCurrentWord(); 
        } else {
            JOptionPane.showMessageDialog(view, "恭喜！本次复习任务完成！");
            view.dispose(); 
            gobackToMain();
        }
    }
    
    // 返回主菜单
    private void gobackToMain() {
        MainView mainView = new MainView();
        new MainController(mainView, user); 
        mainView.setVisible(true);
    }
    
    public void showView() {
        view.setVisible(true);
    }
}