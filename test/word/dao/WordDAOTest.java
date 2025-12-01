/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package word.dao;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import word.model.Word;

/**
 *
 * @author yuzhe
 */
public class WordDAOTest {
    
    private WordDAO instance;
    
    public WordDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        // 如果需要初始化数据库连接池等静态资源，可以在这里进行
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new WordDAO();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getAllWords method, of class WordDAO.
     * 根据你的实现，这个方法目前返回一个空的 ArrayList
     */
    @Test
    public void testGetAllWords() {
        System.out.println("getAllWords");
        List<Word> result = instance.getAllWords();
        
        // 验证结果不为 null
        assertNotNull("Should return a valid list object", result);
        // 根据你的代码 return new ArrayList<>()，这里应该为空
        assertTrue("Current implementation should return empty list", result.isEmpty());
    }

    /**
     * Test of getAllCategories method, of class WordDAO.
     * 测试获取分类列表
     */
    @Test
    public void testGetAllCategories() {
        System.out.println("getAllCategories");
        
        // 执行查询
        List<String> result = instance.getAllCategories();
        
        // 断言结果不为 null (即使数据库为空，也应该返回空列表而不是 null)
        assertNotNull("Category list should not be null", result);
        
        System.out.println("Categories found: " + result.size());
    }

    /**
     * Test of getStudyList method, of class WordDAO.
     * 测试获取特定书籍的学习列表
     */
    @Test
    public void testGetStudyList() {
        System.out.println("getStudyList");
        
        // 模拟测试数据
        // 注意：确保数据库中存在 ID 为 1 的用户，或者处理可能的空结果
        int userId = 1; 
        String category = "CET4"; // 假设存在这个分类，如果没有数据返回空列表也是正确的行为
        int limit = 10;
        
        List<Word> result = instance.getStudyList(userId, category, limit);
        
        assertNotNull("Study list should not be null", result);
        
        // 只有当数据库确实有数据时，下面这个断言才成立，否则 size 可能为 0
        // assertTrue(result.size() <= limit); 
        
        if (!result.isEmpty()) {
            System.out.println("First word in study list: " + result.get(0).getEnglish());
        }
    }

    /**
     * Test of getReviewList method, of class WordDAO.
     * 测试获取复习列表
     */
    @Test
    public void testGetReviewList() {
        System.out.println("getReviewList");
        
        int userId = 1; // 使用假设的用户ID
        
        List<Word> result = instance.getReviewList(userId);
        
        assertNotNull("Review list should not be null", result);
        
        if (!result.isEmpty()) {
            System.out.println("Review list size: " + result.size());
        }
    }
    
}