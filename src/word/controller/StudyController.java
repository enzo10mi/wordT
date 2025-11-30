package word.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.util.List;
import word.dao.*;
import word.model.Word;
import word.view.StudyView;
import word.model.User;
import word.view.MainView;

public class StudyController {
    private StudyView view;
    private User user;
    private String currentBook; // 保存当前词书名
    private int currentUserId; 
    private WordDAO wordDAO;
    private StudyrecordDAO recordDAO;
    
    // 数据状态
    private List<Word> wordList;
    private int currentIndex = 0;
    
    private boolean isCurrentWordKnown = false; 

    /**
     * 【修改构造函数】：增加 bookName 参数
     * @param view 视图界面
     * @param user 当前登录用户
     * @param bookName 当前选中的词书
     */
    public StudyController(StudyView view, User user, String bookName) {
        this.view = view;
        this.user = user;
        this.currentBook = bookName; 
        this.currentUserId = user.getId(); 
        
        // 初始化 DAO
        this.wordDAO = new WordDAO();
        this.recordDAO = new StudyrecordDAO(); 
        
        // 1. 确保用户在记录表里有数据 (如果是新注册用户)
        recordDAO.initRecords(currentUserId);
        
        // 2. 加载数据 
        loadData();
        
        // 3. 绑定按钮事件
        initActions();
        
        // 4. 设置窗口标题 (显示正在背哪本书)
        view.setTitle("背单词 - 学习新词 [" + currentBook + "]");
        
        // 5. 显示第一个单词
        if (wordList != null && !wordList.isEmpty()) {
            showCurrentWord();
        }
    }

    /**
     * 加载数据核心逻辑
     */
    private void loadData() {
        wordList = wordDAO.getStudyList(currentUserId, currentBook, 20); 
        
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
            JOptionPane.showMessageDialog(view, "本组单词完成！");
            view.dispose(); 
            goBackToMain(); // 返回主菜单
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
            goBackToMain(); // 返回主菜单
        }
    }
    
    // 返回主菜单
    private void goBackToMain() {
        MainView mainView = new MainView();
        // 返回时记得把当前选的书带回去，否则可能会丢失状态
        new MainController(mainView, user, currentBook); 
        mainView.setVisible(true);
    }
    
    public void showView() {
        view.setVisible(true);
    }
}