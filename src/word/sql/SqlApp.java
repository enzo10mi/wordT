package word.sql;

/**
 * 数据库建表语句定义
 */
public class SqlApp {
    // 1. 创建用户表 (保持不变)
    public static final String CREATE_USER_TABLE = 
        "CREATE TABLE Users (" +
        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "username VARCHAR(50) NOT NULL UNIQUE, " +
        "password VARCHAR(50) NOT NULL, " +
        "CONSTRAINT pk_users PRIMARY KEY (id))";

    // 2. 创建单词表 (保持不变)
    public static final String CREATE_WORD_TABLE = 
        "CREATE TABLE Words (" +
        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "english VARCHAR(100) NOT NULL, " +
        "chinese VARCHAR(255) NOT NULL, " +
        "category VARCHAR(50), " +
        "example_en VARCHAR(500), " +
        "example_cn VARCHAR(500), " +
        "CONSTRAINT pk_words PRIMARY KEY (id))";

    // 3. 创建学习记录表 (【核心修改】：增加了 is_studied 字段)
    public static final String CREATE_STUDY_RECORD_TABLE = 
        "CREATE TABLE StudyRecords (" +
        "user_id INT NOT NULL, " +
        "word_id INT NOT NULL, " +
        "known BOOLEAN DEFAULT FALSE, " +     // 掌握状态 (true=认识, false=不认识)
        "is_studied BOOLEAN DEFAULT FALSE, " + // 【新增】进度标记 (true=已背过, false=未背过)
        "CONSTRAINT pk_study_records PRIMARY KEY (user_id, word_id), " +
        "CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES Users(id), " +
        "CONSTRAINT fk_word FOREIGN KEY (word_id) REFERENCES Words(id))";
    
    // 4. 初始化数据文件名 (保持不变)
    public static final String INIT_WORDS = "test.txt"; 
}