package word.sql;

public class SqlWord {
    // 基础查询：获取所有单词
    public static final String GET_ALL_WORDS = "SELECT * FROM Words";
    
    // 【修改后】学习模式核心SQL
    // 逻辑：
    // 1. 联表查询找出 known = false (不认识) 的词
    // 2. ORDER BY RANDOM() 实现随机乱序
    // 3. FETCH FIRST ? ROWS ONLY 实现只取 20 个 (问号是占位符)
    public static final String GET_STUDY_LIST = 
            /*  "SELECT w.* FROM Words w " +
            "JOIN StudyRecords sr ON w.id = sr.word_id " +
            "WHERE sr.user_id = ? AND sr.known = false " +
            "ORDER BY RANDOM() " +
            "FETCH FIRST ? ROWS ONLY";*/
            "SELECT w.* FROM Words w FETCH FIRST 20 ROWS ONLY";
}