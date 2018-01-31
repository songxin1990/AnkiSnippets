package com.song.anki.youdao;

import java.util.List;

/**
 * Created by 001844 on 2018/1/26.
 */
public class YoudaoVo {
    //单词
    private String word;
    //去掉中文，保持格式
    private String collins;
    //短语
    private List<String> phrases;
    //同义词
    private List<String> synonyms;
    //同根词
    private List<String> conjugates;

    public String getFront(){
        return this.word;
    }

    public String getBack(){
        StringBuilder sb= new StringBuilder();
        sb.append("<b>").append("Collins:").append("</b>").append("<br>").append(collins).append("<br>");
        appendList(sb,"phrases",phrases);
        appendList(sb,"synonyms",synonyms);
        appendList(sb,"conjugates",conjugates);
        return sb.toString();
    }

    private StringBuilder appendList(StringBuilder sb,String name,List<String> list) {
        sb.append("<b>").append(name).append(":").append("</b>").append("<br>");
        for(String str:list){
            sb.append(str).append("<br>");
        }
        return sb;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCollins() {
        return collins;
    }

    public void setCollins(String collins) {
        this.collins = collins;
    }

    public List<String> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<String> phrases) {
        this.phrases = phrases;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getConjugates() {
        return conjugates;
    }

    public void setConjugates(List<String> conjugates) {
        this.conjugates = conjugates;
    }

}
