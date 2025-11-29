package word.sql;

public class SqlApp {
    // 1. 创建用户表 (保持不变)
    public static final String CREATE_USER_TABLE = 
        "CREATE TABLE Users (" +
        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "username VARCHAR(50) NOT NULL UNIQUE, " +
        "password VARCHAR(50) NOT NULL, " +
        "CONSTRAINT pk_users PRIMARY KEY (id))";

    // 2. 创建单词表 (【修改】：增加了 category 字段)
    public static final String CREATE_WORD_TABLE = 
        "CREATE TABLE Words (" +
        "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
        "english VARCHAR(100) NOT NULL, " +
        "chinese VARCHAR(255) NOT NULL, " +
        "category VARCHAR(50), " +         // 新增：分类字段
        "example_en VARCHAR(500), " +
        "example_cn VARCHAR(500), " +
        "CONSTRAINT pk_words PRIMARY KEY (id))";

    // 3. 创建学习记录表 (保持不变)
    public static final String CREATE_STUDY_RECORD_TABLE = 
        "CREATE TABLE StudyRecords (" +
        "user_id INT NOT NULL, " +
        "word_id INT NOT NULL, " +
        "known BOOLEAN DEFAULT FALSE, " +
        "CONSTRAINT pk_study_records PRIMARY KEY (user_id, word_id), " +
        "CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES Users(id), " +
        "CONSTRAINT fk_word FOREIGN KEY (word_id) REFERENCES Words(id))";
    
    // 4. 初始化数据为空 (因为我们现在改用文件导入了)
    public static final String INIT_WORDS = "test.txt"; 
}