package com.song.anki.ankiconnect;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class AnkiVo {
    private String action;

    private Integer version=5;

    private Map<String,Object> params;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
