package word.dao;

import word.model.Word;
import word.util.DBConnection;
import word.sql.SqlWord;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

    // 获取所有单词
    public List<Word> getAllWords() {
        List<Word> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlWord.GET_ALL_WORDS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToWord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 获取学习列表 (随机取指定数量)
    public List<Word> getStudyList(int userId, int limit) {
        List<Word> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SqlWord.GET_STUDY_LIST)) {
            //pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToWord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 【重要修改】映射方法，增加 category 的读取
    private Word mapRowToWord(ResultSet rs) throws SQLException {
        Word w = new Word();
        w.setId(rs.getInt("id"));
        w.setEnglish(rs.getString("english"));
        w.setChinese(rs.getString("chinese"));
        // 这一行必须加，否则读取出来的对象没有分类信息
        w.setCategory(rs.getString("category")); 
        w.setExampleEn(rs.getString("example_en")); 
        w.setExampleCn(rs.getString("example_cn"));
        return w;
    }
}