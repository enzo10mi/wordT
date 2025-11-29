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
import java.sql.*;
import word.sql.SqlWord;
import word.util.DBConnection;



public class RunApp {

    public static void main(String[] args) {

        System.out.println("=== 系统启动中 ===");
        
        
        int count = 0; //计数器计数单词数量是否为0
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(SqlWord.GET_IF_HAVE_WORDS);
        ResultSet rs = pstmt.executeQuery()) {
        
        if (rs.next()) {
        // 获取结果集中第一列的值（因为你的SQL只查了一个 count(id)）
            count = rs.getInt(1);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("数据库单词数量：" + count);
        
        // 1. 【数据层】从 TXT 导入数据到数据库
        // 注意：文件名要和你真实的文件名一致
        if(count == 0){
            DataImporter.importFromTxt(SqlApp.INIT_WORDS, "test");
        }
        
        //DataImporter.importFromTxt(SqlApp.INIT_WORDS, "test");
        
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