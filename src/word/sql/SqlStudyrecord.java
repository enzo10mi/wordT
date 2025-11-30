package word.sql;

public class SqlStudyrecord {
    
    /**
     * 初始化用户的学习记录
     * 【逻辑】：当新用户注册时，把所有单词插入记录表。
     * is_studied 默认为 false (没背过)，known 默认为 false (不认识)。
     */
    public static final String INIT_RECORDS_FOR_USER = 
        "INSERT INTO StudyRecords (user_id, word_id, known, is_studied) " +
        "SELECT ?, id, false, false FROM Words " +
        "WHERE NOT EXISTS (SELECT 1 FROM StudyRecords WHERE user_id = ? AND word_id = Words.id)";

    /**
     * 更新单词的学习状态
     * 【逻辑】：当用户点击“认识”或“不认识”时：
     * 1. 更新 known 状态 (用户选的)
     * 2. 强制将 is_studied 更新为 true (表示这个词已经背过了)
     */
    public static final String UPDATE_STATUS = 
        "UPDATE StudyRecords SET known = ?, is_studied = true WHERE user_id = ? AND word_id = ?";
    
    // 查询单个记录状态 (备用)
    public static final String GET_RECORD_STATUS = 
        "SELECT known FROM StudyRecords WHERE user_id = ? AND word_id = ?";
}