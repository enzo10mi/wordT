package word.controller;

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
    
    private int currentUserId; 
    private boolean isCurrentWordKnown = false; 
    
    // 模式标记：true=复习, false=学习
    private boolean isReviewMode;

    /**
     * 构造函数
     * @param view 视图界面
     * @param user 当前登录用户
     * @param isReviewMode 是否为复习模式
     */
    public StudyController(StudyView view, User user, boolean isReviewMode) {
        this.view = view;
        this.currentUserId = user.getId();
        this.isReviewMode = isReviewMode; 
        
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
        if (isReviewMode) {
            view.setTitle("背单词 - 复习所有已背单词");
        } else {
            view.setTitle("背单词 - 学习新词");
        }
        
        // 5. 显示第一个单词
        if (wordList != null && !wordList.isEmpty()) {
            showCurrentWord();
        }
    }

    /**
     * 加载数据核心逻辑
     */
    private void loadData() {
        if (isReviewMode) {
            // 【复习模式】：调用 getReviewList
            // 获取所有 is_studied=true 的单词，不管认不认识
            wordList = wordDAO.getReviewList(currentUserId);
            if (wordList.isEmpty()) {
                JOptionPane.showMessageDialog(view, "您还没有背过任何单词，请先去【学习新词】！");
                view.dispose(); // 关闭窗口
            }
        } else {
            // 【学习模式】：调用 getStudyList
            // 获取所有 is_studied=false 的单词
            wordList = wordDAO.getStudyList(currentUserId, 20); 
            if (wordList.isEmpty()) {
                JOptionPane.showMessageDialog(view, "恭喜！您已学完词库中的所有单词！");
                view.dispose(); // 关闭窗口
            }
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
            JOptionPane.showMessageDialog(view, "本组单词完成！");
            view.dispose(); 
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
        recordDAO.updateStatus(currentUserId, w.getId(), isCurrentWordKnown);
        
        System.out.println("已保存: " + w.getEnglish() + " | 认识: " + isCurrentWordKnown);

        currentIndex++;
        
        // 检查是否还有下一个
        if (currentIndex < wordList.size()) {
            showCurrentWord(); 
        } else {
            JOptionPane.showMessageDialog(view, "恭喜！本次任务完成！");
            view.dispose(); 
        }
    }
    
    public void showView() {
        view.setVisible(true);
    }
}