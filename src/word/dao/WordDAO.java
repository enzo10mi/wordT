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
     * 【学习模式】：获取新单词列表
     * @param userId 当前用户ID
     * @param limit 限制数量 (虽然SQL里写死了20，保留参数方便扩展)
     * @return 单词列表
     */
    public List<Word> getStudyList(int userId, int limit) {
        List<Word> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlWord.GET_STUDY_LIST)) {
            
            pstmt.setInt(1, userId); // 必须填入 userId 才能查 is_studied
            
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
     * 获取所有背过的单词 (is_studied = true)
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