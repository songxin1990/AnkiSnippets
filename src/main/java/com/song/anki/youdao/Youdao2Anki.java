package com.song.anki.youdao;

import com.song.anki.ankiconnect.AnkiService;
import com.song.anki.ankiconnect.NoteVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 001844 on 2018/1/26.
 * 将有道词典中的单词本导入到Anki中
 * 1. 单词本到处为xml文件
 * 2. 单词本因为是英中的，我需要的是英英的，所以需要调用官方接口获取英文释义
 * 3. 将得到的释义通过Anki connect生成note，插入到Anki中
 */
public class Youdao2Anki {

    public static void main(String[] args) throws IOException {
        String youdaoXml = "F:\\sojob\\youdao.xml";
        YoudaoService youdaoService = new YoudaoService();
        List<String> words = youdaoService.parse(youdaoXml);

        List<NoteVo> noteVos = new ArrayList<NoteVo>();
        AnkiService ankiService = new AnkiService();
        for (String word : words) {
            YoudaoVo youdaoVo = youdaoService.translate(word);
            NoteVo noteVo = NoteVo.convert(youdaoVo);
            noteVos.add(noteVo);
            ankiService.addNotes(noteVos);
            /*if(noteVos.size()>=10){
                new AnkiService().addNotes(noteVos);
                noteVos.clear();
            }*/
        }
    }

}
