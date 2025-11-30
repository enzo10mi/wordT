/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.controller;

/**
 * 学习会话接口
 * 统一规范新学和复习的行为
 * @author yuzhe
 */
public interface LearningSession {
    
    /**
     * 显示视图界面
     */
    void showView();

    /**
     * 加载单词数据 (核心业务：查新词 OR 查错题)
     */
    void loadData();

    /**
     * 绑定按钮事件监听器
     */
    void initActions();

    /**
     * 保存当前单词状态并进入下一个
     */
    void saveAndNext();

    /**
     * 结束学习，返回主菜单
     */
    void goBackToMain();
}