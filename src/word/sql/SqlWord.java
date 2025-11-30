package word.sql;

public class SqlWord {
    // 基础查询：获取所有单词
    public static final String GET_ALL_WORDS = "SELECT * FROM Words";
    
    // 【学习模式 SQL】：只获取“没背过”的单词
    // 逻辑：is_studied = false 表示全新词汇
    public static final String GET_STUDY_LIST = 
            "SELECT w.* FROM Words w " +
            "JOIN StudyRecords sr ON w.id = sr.word_id " +
            "WHERE sr.user_id = ? AND sr.is_studied = false " +
            "FETCH FIRST 20 ROWS ONLY"; // 每次只取20个
    
    // 【复习模式 SQL - 核心修改部分】
    // 原逻辑：is_studied = true
    // 新逻辑：is_studied = true (背过) 且 known = false (不认识)
    public static final String GET_REVIEW_WORDS = 
        "SELECT w.* FROM Words w " +
        "JOIN StudyRecords sr ON w.id = sr.word_id " +
        "WHERE sr.user_id = ? " +
        "AND sr.is_studied = true " +
        "AND sr.known = false"; // <--- 这里是关键修改，排除掉已经认识的
    
    // 查询数据库中单词总数
    public static String GET_IF_HAVE_WORDS = "select count(*) from words";
}