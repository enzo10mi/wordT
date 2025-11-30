/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.controller;

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

/**
 *
 * @author yuzhe
 */

public class ReviewController implements LearningSession {
    private ReviewView view;
    private User user;
    private int currentUserId; 
    private WordDAO wordDAO;
    private StudyrecordDAO recordDAO;
    
    private List<Word> wordList;
    private int currentIndex = 0;
    private boolean isCurrentWordKnown = false; 

    public ReviewController(ReviewView view, User user) {
        this.view = view;
        this.user = user;
        this.currentUserId = user.getId();
        
        this.wordDAO = new WordDAO();
        this.recordDAO = new StudyrecordDAO(); 
        
        recordDAO.initRecords(currentUserId);
        
        loadData();
        initActions();
        
        view.setTitle("背单词 - 复习错题本");
        
        if (wordList != null && !wordList.isEmpty()) {
            showCurrentWord();
        }
    }

    // 【修改2】实现接口方法 (改为 public)
    @Override
    public void showView() {
        view.setVisible(true);
    }

    @Override
    public void loadData() {
        wordList = wordDAO.getReviewList(currentUserId);
        if (wordList.isEmpty()) {
            System.out.println("警告：进入复习界面但列表为空");
        }
    }

    private void showCurrentWord() {
        if (currentIndex < wordList.size()) {
            Word w = wordList.get(currentIndex);
            view.setWord(w.getEnglish());
            view.setMeaning(w.getChinese());
            view.switchMode(true); 
        } else {
            JOptionPane.showMessageDialog(view, "本组复习完成！");
            view.dispose(); 
            goBackToMain();
        }
    }

    @Override
    public void initActions() {
        view.addKnownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCurrentWordKnown = true;
                showAnswerPhase(); 
            }
        });

        view.addUnknownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCurrentWordKnown = false;
                showAnswerPhase(); 
            }
        });

        view.addNextListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAndNext();
            }
        });
    }

    private void showAnswerPhase() {
        view.switchMode(false); 
    }

    @Override
    public void saveAndNext() {
        if (wordList == null || wordList.isEmpty()) return;

        Word w = wordList.get(currentIndex);
        
        recordDAO.updateStatus(currentUserId, w.getId(), isCurrentWordKnown);
        System.out.println("已保存: " + w.getEnglish() + " | 认识: " + isCurrentWordKnown);

        currentIndex++;
        
        if (currentIndex < wordList.size()) {
            showCurrentWord(); 
        } else {
            JOptionPane.showMessageDialog(view, "恭喜！本次复习任务完成！");
            view.dispose(); 
            goBackToMain();
        }
    }
    
    @Override
    public void goBackToMain() {
        MainView mainView = new MainView();
        // 这里复习完通常默认回主界面，如果 MainController 需要 bookName，这里可能需要注意
        // 你的旧代码里这里使用的是 User 构造，所以默认是 "四级词汇"，如果要精确控制可以传参
        new MainController(mainView, user); 
        mainView.setVisible(true);
    }
}