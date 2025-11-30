package word.dao;

import word.model.Word;
import word.util.DBConnection;
import word.sql.SqlWord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

    // (为了兼容旧代码保留，实际业务中可能不再直接用)
    public List<Word> getAllWords() {
        return new ArrayList<>(); 
    }

    /**
     * 获取所有词书分类列表
     * 用于登录后的下拉框选择
     */
    public List<String> getAllCategories() {
        List<String> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlWord.GET_ALL_CATEGORIES);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String cat = rs.getString("category");
                // 确保不为空
                if (cat != null && !cat.isEmpty()) {
                    list.add(cat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 【学习模式】：获取新单词列表 (支持指定词书)
     * @param userId 当前用户ID
     * @param category 用户选择的词书名 (如 "四级词汇")
     * @param limit 限制数量
     * @return 单词列表
     */
    public List<Word> getStudyList(int userId, String category, int limit) {
        List<Word> list = new ArrayList<>();
        // 【修改】使用带分类筛选的 SQL
        String sql = SqlWord.GET_STUDY_LIST_BY_BOOK;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId); // 填入 userId
            pstmt.setString(2, category); // 填入书名
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToWord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 【复习模式】：获取复习列表
     * 获取所有背过的单词 (is_studied = true) 且 不认识 (known = false)
     * @param userId 当前用户ID
     * @return 单词列表
     */
    public List<Word> getReviewList(int userId) {
        List<Word> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlWord.GET_REVIEW_WORDS)) {
            
            pstmt.setInt(1, userId);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToWord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 辅助方法：将数据库结果映射为对象
    private Word mapRowToWord(ResultSet rs) throws SQLException {
        Word w = new Word();
        w.setId(rs.getInt("id"));
        w.setEnglish(rs.getString("english"));
        w.setChinese(rs.getString("chinese"));
        // 加上 try-catch 防止旧数据库或者字段缺失导致的报错
        try { w.setCategory(rs.getString("category")); } catch (SQLException e) {} 
        try { w.setExampleEn(rs.getString("example_en")); } catch (SQLException e) {}
        try { w.setExampleCn(rs.getString("example_cn")); } catch (SQLException e) {}
        return w;
    }
    
}