package word.dao;

import word.util.DBConnection;
import word.sql.SqlStudyrecord;
import java.sql.*;

public class StudyrecordDAO {

    /**
     * 为指定用户初始化单词本
     * @param userId 用户ID
     * @return 初始化插入的行数
     */
    public int initRecords(int userId) {
        int count = 0;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlStudyrecord.INIT_RECORDS_FOR_USER)) {
            
            // 填入 SQL 中的占位符
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId); 
            
            count = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 更新某个单词的学习状态
     * 同时也自动把 is_studied 设为 true
     * @param userId 用户ID
     * @param wordId 单词ID
     * @param known 用户是否认识
     * @return 是否更新成功
     */
    public boolean updateStatus(int userId, int wordId, boolean known) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlStudyrecord.UPDATE_STATUS)) {
            
            pstmt.setBoolean(1, known);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, wordId);
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 【新增】获取当前词书的总单词数
    public int getTotalWordCount(String currentBook) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Words WHERE category = ?";
        
        try (Connection conn = word.util.DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, currentBook);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    // 【新增】获取已学习单词数量
    public int getStudiedWordCount(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM StudyRecords WHERE user_id = ? AND is_studied = true";
        
        try (Connection conn = word.util.DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
    
    // 【新增】获取待复习单词数量（已学习但不认识的）
    public int getUnknownWordCount(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM StudyRecords WHERE user_id = ? AND is_studied = true AND known = false";
        
        try (Connection conn = word.util.DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}