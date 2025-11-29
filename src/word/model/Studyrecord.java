/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package word.model;

/**
 *
 * @author yuzhe
 */
public class Studyrecord {
    private int userId;
    private int wordId;
    private boolean known;//是否掌握，0：不会，1：会

    // private Date lastReviewDate;    // 上次回顾日期。

    public Studyrecord() {}

    public Studyrecord(int userId, int wordId, boolean known) {
        this.userId = userId;
        this.wordId = wordId;
        this.known = known;
    }

    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public int getWordId() {return wordId;}
    public void setWordId(int wordId) {this.wordId = wordId;}

    public boolean isKnown() {return known;}
    public void setKnown(boolean known) {this.known = known;}

}
