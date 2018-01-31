package com.song.anki.ankiconnect;

import com.song.anki.youdao.YoudaoVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 001844 on 2018/1/26.
 */
public class NoteVo {
    private String deckName="Default";

    private String modelName = "Basic";

    private Map<String, String> fields;

    private List<String> tags;

    public NoteVo() {
    }

    public static NoteVo convert(YoudaoVo youdaoVo){
        if(youdaoVo==null){
            return  null;
        }
        NoteVo noteVo = new NoteVo();
//        noteVo.setDeckName("NewWords");
        noteVo.setDeckName("Default");
//        noteVo.setDeckName("test");
        noteVo.setModelName("Basic");
        Map<String,String> fields = new HashMap();
        fields.put("Front",youdaoVo.getFront());
        fields.put("Back",youdaoVo.getBack());
        noteVo.setFields(fields);
        noteVo.setTags(null);
        return noteVo;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

