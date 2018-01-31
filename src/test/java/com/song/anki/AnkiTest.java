package com.song.anki;

import com.song.anki.ankiconnect.AnkiService;
import com.song.anki.ankiconnect.AnkiVo;
import com.song.anki.ankiconnect.NoteVo;
import com.song.anki.youdao.YoudaoService;
import com.song.anki.youdao.YoudaoVo;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 001844 on 2018/1/26.
 */
public class AnkiTest {


    @Test
    public void test() throws IOException {
        String word = "stray";
        YoudaoService youdaoService = new YoudaoService();
        YoudaoVo youdaoVo = youdaoService.translate(word);
        NoteVo noteVo = NoteVo.convert(youdaoVo);
        List<NoteVo> noteVos = Arrays.asList(noteVo);
        new AnkiService().addNotes(noteVos);
    }

    @Test
    public void addNotesTest() throws IOException {
        NoteVo noteVo = new NoteVo();
        noteVo.setDeckName("Default");
        noteVo.setModelName("Basic");
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("Front", "test6");
        fields.put("Back", "backTest");
        noteVo.setFields(fields);

        AnkiVo ankiVo = new AnkiVo();
        ankiVo.setAction("addNotes");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("notes", Arrays.asList(noteVo));
        ankiVo.setParams(params);
        new AnkiService().post(ankiVo);
    }
}
