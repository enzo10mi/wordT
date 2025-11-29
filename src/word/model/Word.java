/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.model;

/**
 *
 * @author yuzhe
 */
public class Word {
    private int id;
    private String english;
    private String chinese;
    private String exampleEn;
    private String exampleCn;
    

    public Word() {}

    //½öµ¥´Ê
    public Word(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }
    
    // ÓÐÀý¾ä
    public Word(String english, String chinese, String exampleEn, String exampleCn) {
        this.english = english;
        this.chinese = chinese;
        this.exampleEn = exampleEn;
        this.exampleCn = exampleCn;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEnglish() { return english; }
    public void setEnglish(String english) { this.english = english; }

    public String getChinese() { return chinese; }
    public void setChinese(String chinese) { this.chinese = chinese; }

    public String getExampleEn() { return exampleEn; }
    public void setExampleEn(String exampleEn) { this.exampleEn = exampleEn; }

    public String getExampleCn() { return exampleCn; }
    public void setExampleCn(String exampleCn) { this.exampleCn = exampleCn; }


    @Override
    public String toString() {
        return english + " - " + chinese ;
    }
}
