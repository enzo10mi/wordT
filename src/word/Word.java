/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package word;

/**
 *
 * @author yuzhe
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 导入您需要的工具类和 SQL 定义
import word.util.DBConnection;
import word.util.DataImporter;
import word.sql.SqlApp;
import word.sql.SqlWord;

// 【核心修改】导入登录模块，而不是学习模块
import word.view.LoginView;
import word.controller.LoginController; 
public class Word {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("=== 系统启动中 ===");
        
        
        // --- 1. 数据库和数据初始化 (这部分保持不变) ---
        int count = 0; 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlWord.GET_IF_HAVE_WORDS); //
             ResultSet rs = pstmt.executeQuery()) {
        
            if (rs.next()) {
                count = rs.getInt(1); //
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("数据库单词数量：" + count);
        
        // 检查单词数量是否为0，如果为0则导入
        if(count == 0){
            // SqlApp.INIT_WORDS 默认为 "test.txt"
            DataImporter.importFromTxt(SqlApp.INIT_WORDS, "test"); //
        }
        
        
        // --- 2. 【核心修改】启动登录流程 ---
        try {
            // 1. 创建 View (登录视图)
            LoginView loginView = new LoginView();

            // 2. 创建 Controller (登录控制器)，并将 View 传入
            //    (这会自动把 "登录" 和 "注册" 按钮的监听器绑定好)
            new LoginController(loginView);

            // 3. 显示登录界面
            loginView.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("启动失败，请检查数据库连接或View类代码。");
        }
        
    }
    
}
