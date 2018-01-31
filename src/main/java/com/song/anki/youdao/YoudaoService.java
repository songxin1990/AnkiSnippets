package com.song.anki.youdao;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 001844 on 2018/1/26.
 */
public class YoudaoService {
    private static final String YOUDAO_URL = "https://www.youdao.com/w/eng/";

    public List<String> parse(String youdaoXmlPath) throws IOException {
        String html = null;
        try {
            html = FileUtils.readFileToString(new File(youdaoXmlPath), "utf8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        Document doc = Jsoup.parse(html, "", Parser.xmlParser());
        List<String> words = doc.select("word").eachText();
        return words;
    }

    public YoudaoVo translate(String word) throws IOException {
        if (StringUtils.isEmpty(word)) {
            return null;
        }
        Document docTempx = Jsoup.connect(YOUDAO_URL + word).get();
        //先抓取柯林斯的解释，没有的话，使用英英 .collinsMajorTrans
        Elements collins = docTempx.select("div.collinsToggle li");
        StringBuilder collinsSB = new StringBuilder();
        if (collins == null || collins.size() == 0) {
            collinsSB.append(docTempx.select("#tEETrans").text());
        } else {
            for (Element collin : collins) {
                String collinsExplain = collin.select(".collinsMajorTrans").text();//做好能去掉中文
                collinsSB.append(collinsExplain).append("<br>");
                Elements examples = collin.select(".exampleLists");
                for (Element exam : examples) {
                    String examSentence = "Example:" + exam.select("div.examples p").first().text();
                    collinsSB.append(examSentence).append("<br>");
                }
            }
        }
        //短语,
        List<String> phrase = docTempx.select("#wordGroup p.wordGroup").eachText();
//        List<String> phraseTop = docTempx.select("#webPhrase p.wordGroup").eachText();
        //同义词
        Elements synonymsElements = docTempx.select("#synonyms li");
        List<String> synonyms = new ArrayList<String>();
        for (Element synonymE : synonymsElements) {
            String synonymLi = synonymE.text();
            String synonym = synonymE.nextElementSibling().text();
            String result = synonymLi + "<br>" + synonym;
            synonyms.add(result);
        }
        //同根词
        List<String> relWordTab = docTempx.select("#relWordTab p").eachText();

        YoudaoVo youdaoVo = new YoudaoVo();
        youdaoVo.setWord(word);
        youdaoVo.setCollins(collinsSB.toString());
        youdaoVo.setPhrases(phrase);
        youdaoVo.setSynonyms(synonyms);
        youdaoVo.setConjugates(relWordTab);
        return youdaoVo;
    }
}
