package com.song.anki.taotehking;

import com.song.anki.ankiconnect.AnkiService;
import com.song.anki.ankiconnect.NoteVo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

/**
 * Created by 001844 on 2018/1/29.
 */
public class TaoTehKing2Anki {

    public static void main(String[] args) throws IOException {
        String url = "http://www.daodejing.org/";
        Document doc = Jsoup.connect(url).get();
        List<String> texts = doc.select("#common p.STYLE4").eachText();
        List<String> sections = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (String str : texts) {
            if (str.equals("")) {
                sections.add(sb.toString());
                sb.delete(0, sb.length());
            } else {
                sb.append(str);
            }
        }
        sections.add(sb.toString());

        AnkiService ankiService = new AnkiService();
        List<NoteVo> noteVos = new ArrayList<NoteVo>();
        for (String sec : sections) {
            NoteVo noteVo = new NoteVo();
            Map<String, String> fields = new HashMap();
            fields.put("Front", sec);
            fields.put("Back", "");
            noteVo.setFields(fields);
            noteVo.setTags(Arrays.asList("Tao"));
            noteVos.add(noteVo);
        }
        ankiService.addNotes(noteVos);
    }
}
