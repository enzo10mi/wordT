package word; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import word.util.DBConnection;
import word.util.DataImporter;
import word.sql.SqlWord;
import word.view.LoginView;
import word.controller.LoginController; 

/**
 * 此文件为项目主入口 
 * Run this file
 * 
 **/

public class Word {

    public static void main(String[] args) {

        System.out.println("=== 系统启动中 (Main: Word) ===");
        
        // ---数据库和数据初始化---
        int count = 0; 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlWord.GET_IF_HAVE_WORDS); 
             ResultSet rs = pstmt.executeQuery()) {
        
            if (rs.next()) {
                count = rs.getInt(1); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("当前数据库单词总数：" + count);
        
        // 检查单词数量是否为0，如果为0则导入
        if(count == 0){
            System.out.println(">> 检测到数据库为空，开始初始化多本词书...");
            // 一次性导入所有词书，并指定不同的 category (分类名)
            // 请确保 src/word/resource/data/ 目录下有这些文件
            DataImporter.importFromTxt("CT4.txt", "四级词汇");
            DataImporter.importFromTxt("CT6.txt", "六级词汇");
            DataImporter.importFromTxt("IELTS.txt", "雅思词汇");
            DataImporter.importFromTxt("gaokao.txt", "高考词汇");
        }
        
        
        // ---启动登录流程 ---
        try {
            // 1. 创建 View (登录视图)
            LoginView loginView = new LoginView();

            // 2. 创建 Controller (登录控制器)，并将 View 传入
            new LoginController(loginView);

            // 3. 显示登录界面
            loginView.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("启动失败，请检查数据库连接或View类代码。");
        }
    }
}