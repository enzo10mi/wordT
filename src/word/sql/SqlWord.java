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
            "FETCH FIRST 20 ROWS ONLY"; // 每次只取20个，防止压力太大
    
    // 【复习模式 SQL】：获取“所有背过的”单词
    // 逻辑：只要 is_studied = true 就全部查出来，不管 known 是 true 还是 false
    // (这就实现了你要求的“复习所有背过的词”)
    public static final String GET_REVIEW_WORDS = 
        "SELECT w.* FROM Words w " +
        "JOIN StudyRecords sr ON w.id = sr.word_id " +
        "WHERE sr.user_id = ? AND sr.is_studied = true";
    
    // 查询数据库中单词总数
    public static String GET_IF_HAVE_WORDS = "select count(*) from words";
}