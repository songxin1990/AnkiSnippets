package com.song.anki.ankiconnect;

import com.alibaba.fastjson.JSON;
import com.song.anki.common.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 001844 on 2018/1/26.
 */
public class AnkiService {
    private static final String ANKI_URL = "http://127.0.0.1:8765";
    public AnkiRespVo post(AnkiVo ankiVo){
        try {
            String resp = HttpUtil.okhttpPost(ANKI_URL, ankiVo.toString());
            System.out.println(JSON.toJSONString(resp));
            return JSON.parseObject(resp, AnkiRespVo.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AnkiRespVo addNotes(List<NoteVo> noteVos) {
        AnkiVo ankiVo = new AnkiVo();
        ankiVo.setAction("addNotes");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("notes", noteVos);
        ankiVo.setParams(params);

        return post(ankiVo);
    }
}
