package word.util;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;

public class DataImporter {

    public static void importFromTxt(String fileName, String category) {
        String sql = "INSERT INTO Words (english, chinese, category) VALUES (?, ?, ?)";
        
        // 读取文件流
        InputStream is = DataImporter.class.getResourceAsStream("/word/resource/data/" + fileName);
        if (is == null) {
            System.err.println("? 找不到文件: " + fileName);
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             // 配合你刚才保存的 ANSI 格式，这里用 GBK 读取
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("GBK")))) {
            
            conn.setAutoCommit(false);
            String line;
            int count = 0;
            
            System.out.println("正在导入 " + fileName + " ...");

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                // 【核心修改】用正则 \\s+ 切割（适配 1 个或多个空格）
                // 限制切成 2 段：[0]=英文, [1]=后面所有的中文
                String[] parts = line.trim().split("\\s+", 2);
                
                if (parts.length >= 2) {
                    pstmt.setString(1, parts[0].trim()); // 英文
                    pstmt.setString(2, parts[1].trim()); // 中文
                    pstmt.setString(3, category);        // 分类 (如 "CET4")
                    pstmt.addBatch();
                    count++;
                }
                
                if (count % 1000 == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                }
            }
            pstmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("? 成功导入 [" + category + "] 共 " + count + " 条数据。");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}