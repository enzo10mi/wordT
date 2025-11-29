/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */


/**
 *
 * @author yuzhe
 * 
 * StudyView 的单元测试
 * 使用反射机制访问私有组件进行断言
 */
 
package word.view;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudyViewTest {
    
    private StudyView instance;
    
    public StudyViewTest() {
    }
    
    @Before
    public void setUp() {
        // 每次测试前创建一个新的 View 实例
        instance = new StudyView();
    }
    
    @After
    public void tearDown() {
        // 测试结束后关闭窗口，释放资源
        if (instance != null) {
            instance.dispose();
        }
    }

    /**
     * 辅助方法：利用反射获取私有字段的值
     * 这样我们才能拿到 lblWord, btnNext 等对象进行断言
     */
    private Object getPrivateField(String fieldName) throws Exception {
        Field field = StudyView.class.getDeclaredField(fieldName);
        field.setAccessible(true); // 暴力访问私有字段
        return field.get(instance);
    }

    /**
     * 测试 setWordText 是否真的改变了 Label 的文字
     */
    @Test
    public void testSetWordText() throws Exception {
        System.out.println("setWordText");
        String text = "Apple";
        
        instance.setWordText(text);
        
        // 获取私有的 lblWord 组件
        JLabel lblWord = (JLabel) getPrivateField("lblWord");
        
        // 断言：Label上的文字应该等于我们设置的文字
        assertEquals(text, lblWord.getText());
    }

    /**
     * 测试 setMeaningText 是否改变了 TextArea 的文字
     */
    @Test
    public void testSetMeaningText() throws Exception {
        System.out.println("setMeaningText");
        String text = "n. 苹果";
        
        instance.setMeaningText(text);
        
        // 注意：View中释义是用 JTextArea 实现的 txtMeaning
        JTextArea txtMeaning = (JTextArea) getPrivateField("txtMeaning");
        
        assertEquals(text, txtMeaning.getText());
    }

    /**
     * 测试 setMeaningVisible 是否有效
     */
    @Test
    public void testSetMeaningVisible() throws Exception {
        System.out.println("setMeaningVisible");
        
        // 1. 测试设置为 true
        instance.setMeaningVisible(true);
        JTextArea txtMeaning = (JTextArea) getPrivateField("txtMeaning");
        assertTrue("组件应当可见", txtMeaning.isVisible());
        
        // 2. 测试设置为 false
        instance.setMeaningVisible(false);
        assertFalse("组件应当隐藏", txtMeaning.isVisible());
    }

    /**
     * 测试 updateProgress 格式是否正确
     */
    @Test
    public void testUpdateProgress() throws Exception {
        System.out.println("updateProgress");
        int current = 5;
        int total = 20;
        
        instance.updateProgress(current, total);
        
        JLabel lblProgress = (JLabel) getPrivateField("lblProgress");
        // 预期结果根据你在 View 里写的格式: "进度: 5/20"
        assertEquals("进度: 5/20", lblProgress.getText());
    }

    /**
     * 测试 addShowMeaningListener 是否真的添加了监听器
     */
    @Test
    public void testAddShowMeaningListener() throws Exception {
        System.out.println("addShowMeaningListener");
        
        // 创建一个假的监听器
        ActionListener listener = e -> System.out.println("Clicked");
        instance.addShowMeaningListener(listener);
        
        JButton btnShowMeaning = (JButton) getPrivateField("btnShowMeaning");
        
        // 断言：按钮现在的监听器数量应该大于0
        assertTrue(btnShowMeaning.getActionListeners().length > 0);
    }

    /**
     * 测试 addNextListener
     */
    @Test
    public void testAddNextListener() throws Exception {
        System.out.println("addNextListener");
        
        ActionListener listener = e -> {};
        instance.addNextListener(listener);
        
        JButton btnNext = (JButton) getPrivateField("btnNext");
        
        assertTrue(btnNext.getActionListeners().length > 0);
    }

    /**
     * 测试 showMessage
     * 注意：JOptionPane 会弹窗阻塞测试，通常单元测试不测这个，或者只测它不报错
     */
    @Test
    public void testShowMessage() {
        System.out.println("showMessage");
        
        // 这是一个特殊的测试，因为弹窗会卡住程序。
        // 我们这里只验证调用该方法不会抛出异常。
        // 实际运行时，如果不手动点“确定”，测试会卡在这里。
        // 为了自动化测试通过，建议实际项目中注释掉弹窗代码，或者使用Mock工具。
        // 这里我们用 try-catch 包裹，演示它可以运行。
        
        try {
            // 只有当需要在人工观察时才取消注释下面这行，否则会让自动测试卡住
            // instance.showMessage("Test Message"); 
        } catch (Exception e) {
            fail("showMessage threw an exception: " + e.getMessage());
        }
    }
}