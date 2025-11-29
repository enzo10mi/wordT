package word; // 放在最外层包，或者 word.test 包都可以

import word.controller.StudyController;
import word.dao.StudyrecordDAO;
import word.dao.UserDAO;
import word.model.User;
import word.util.DataImporter;
import word.view.StudyView;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class RunApp {

    public static void main(String[] args) {
        try {
            // 美化界面风格
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        System.out.println("=== 系统启动中 ===");

        // 1. 【数据层】从 TXT 导入数据到数据库
        // 注意：文件名要和你真实的文件名一致
        DataImporter.importFromTxt("test.txt", "test");

    }
}