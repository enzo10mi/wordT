/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package word.view;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yuzhe
 */
public class StudyViewTest {
    
    private StudyView instance;
    
    public StudyViewTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // 在每个测试开始前创建一个新的实例，确保环境干净
        instance = new StudyView();
    }
    
    @After
    public void tearDown() {
        // 销毁窗口资源
        if (instance != null) {
            instance.dispose();
        }
    }

    /**
     * 测试获取窗口标题的方法
     * 以及验证窗口标题是否实际设置成功
     */
    @Test
    public void testGetWindowTitle() {
        System.out.println("getWindowTitle");
        String expResult = "Studying";
        
        // 1. 验证子类重写的方法返回值是否正确
        String result = instance.getWindowTitle();
        assertEquals(expResult, result);
        
        // 2. 验证 JFrame 的标题属性是否真的被父类 BaseCardView 设置了
        assertEquals(expResult, instance.getTitle());
    }

    /**
     * 测试获取模式标签文本的方法
     */
    @Test
    public void testGetModeLabelText() {
        System.out.println("getModeLabelText");
        String expResult = "Studying";
        String result = instance.getModeLabelText();
        assertEquals(expResult, result);
    }
    
    /**
     * 测试 UI 模式切换逻辑 (继承自 BaseCardView)
     * 验证组件的可见性是否随模式改变
     */
    @Test
    public void testSwitchMode() {
        System.out.println("testSwitchMode");
        
        // --- 测试阶段 1: 决策阶段 (isDecisionPhase = true) ---
        instance.switchMode(true);
        
        // 因为在同一个包下，我们可以直接访问 protected 的组件进行断言
        assertFalse("In decision phase, meaning should be hidden", instance.txtMeaning.isVisible());
        assertTrue("In decision phase, Known button should be visible", instance.btnKnown.isVisible());
        assertTrue("In decision phase, Unknown button should be visible", instance.btnUnknown.isVisible());
        assertFalse("In decision phase, Next button should be hidden", instance.btnNext.isVisible());
        
        // --- 测试阶段 2: 结果展示阶段 (isDecisionPhase = false) ---
        instance.switchMode(false);
        
        assertTrue("In result phase, meaning should be visible", instance.txtMeaning.isVisible());
        assertFalse("In result phase, Known button should be hidden", instance.btnKnown.isVisible());
        assertFalse("In result phase, Unknown button should be hidden", instance.btnUnknown.isVisible());
        assertTrue("In result phase, Next button should be visible", instance.btnNext.isVisible());
    }

    /**
     * 测试设置单词和释义文本 (继承自 BaseCardView)
     */
    @Test
    public void testSetContent() {
        System.out.println("testSetContent");
        
        String testWord = "Apple";
        String testMeaning = "n. 苹果";
        
        instance.setWord(testWord);
        instance.setMeaning(testMeaning);
        
        assertEquals("Label text should match setWord", testWord, instance.lblWord.getText());
        assertEquals("TextArea text should match setMeaning", testMeaning, instance.txtMeaning.getText());
    }
}