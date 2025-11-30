package word.sql;

public class SqlWord {
    // 基础查询：获取所有单词
    public static final String GET_ALL_WORDS = "SELECT * FROM Words";
    
    // 【新增 1】查询系统里所有的词书分类 (去重)
    // 用于在下拉框里显示：[四级词汇, 六级词汇, 雅思词汇, 高考词汇]
    public static final String GET_ALL_CATEGORIES = "SELECT DISTINCT category FROM Words";

    // 【新增 2 - 核心修改】按【分类】获取学习列表
    // 逻辑：用户ID匹配 + 没背过(is_studied=false) + 属于那本书(category=?)
    public static final String GET_STUDY_LIST_BY_BOOK = 
            "SELECT w.* FROM Words w " +
            "JOIN StudyRecords sr ON w.id = sr.word_id " +
            "WHERE sr.user_id = ? " +
            "AND sr.is_studied = false " +
            "AND w.category = ? " +  // <--- 核心筛选：只取选定词书的词
            "FETCH FIRST 20 ROWS ONLY"; // 每次只取20个
    
    // 【复习模式 SQL - 错题本模式】
    // 逻辑：is_studied = true (背过) 且 known = false (不认识)
    public static final String GET_REVIEW_WORDS = 
        "SELECT w.* FROM Words w " +
        "JOIN StudyRecords sr ON w.id = sr.word_id " +
        "WHERE sr.user_id = ? " +
        "AND sr.is_studied = true " +
        "AND sr.known = false"; // <--- 排除掉已经认识的
    
    // 查询数据库中单词总数
    public static String GET_IF_HAVE_WORDS = "select count(*) from words";
    
    // (旧的查询保留备用，防止改动太大报错)
    public static final String GET_STUDY_LIST = 
            "SELECT w.* FROM Words w JOIN StudyRecords sr ON w.id = sr.word_id WHERE sr.user_id = ? AND sr.is_studied = false FETCH FIRST 20 ROWS ONLY";
}