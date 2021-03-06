package com.ktfootball.www.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table RANKING_LEAGUE_SCORES1V1.
 */
public class RankingLeagueScores1v1 {

    private Long id;
    private String nickname;
    private String age;
    private String gender;
    private String cls;
    private String grade;
    private String scores;
    private String win_rate;

    public RankingLeagueScores1v1() {
    }

    public RankingLeagueScores1v1(Long id) {
        this.id = id;
    }

    public RankingLeagueScores1v1(Long id, String nickname, String age, String gender, String cls, String grade, String scores, String win_rate) {
        this.id = id;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.cls = cls;
        this.grade = grade;
        this.scores = scores;
        this.win_rate = win_rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getWin_rate() {
        return win_rate;
    }

    public void setWin_rate(String win_rate) {
        this.win_rate = win_rate;
    }

}
