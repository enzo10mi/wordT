/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.view;

import javax.swing.UIManager;

/**
 *
 * @author yuzhe
 */
public class StudyViewShow {
    public static void main(String[] args) {
        // 推荐加上这句，让界面风格跟随系统（比如Windows风格），否则是Java默认的金属风格
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        // 启动
        javax.swing.SwingUtilities.invokeLater(() -> {
            // 测试 StudyView 界面
            StudyView view = new StudyView();
            view.setWordText("Apple");
            view.setMeaningText("n. 苹果");
            view.setVisible(true);
            
            // 或者测试 LoginView
            // LoginView login = new LoginView();
            // login.setVisible(true);
        });
    }
}
