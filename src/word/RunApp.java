package word; // 放在最外层包，或者 word.test 包都可以

import java.util.List;
import word.controller.StudyController;
import word.dao.StudyrecordDAO;
import word.dao.UserDAO;
import word.model.User;
import word.util.DataImporter;
import word.view.StudyView;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import word.dao.WordDAO;
import word.model.Word;
import word.sql.SqlApp;


public class RunApp {

    public static void main(String[] args) {

        System.out.println("=== 系统启动中 ===");

        // 1. 【数据层】从 TXT 导入数据到数据库
        // 注意：文件名要和你真实的文件名一致
        DataImporter.importFromTxt(SqlApp.INIT_WORDS, "test");
        
        try {
//            List<Word> wordList;
//            WordDAO wordDAO = new WordDAO();
//            wordList = wordDAO.getStudyList(1, 20); 
//            
//            for(Word i : wordList){
//                System.out.println(i.getEnglish() + " "+ i.getChinese());
//            }
            // 1. 创建 View (视图)
                    StudyView view = new StudyView();
                    
                    // 2. 创建 Controller (控制器)，并将 View 传入
                    StudyController controller = new StudyController(view);
                    
                    // 3. 显示界面
                    controller.showView();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("启动失败，请检查数据库连接或View类代码。");
        }
    }
}