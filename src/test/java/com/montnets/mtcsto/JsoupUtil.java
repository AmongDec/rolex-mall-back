package com.montnets.mtcsto;

import com.rolex.mall.pojo.good.GoodTable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsoupUtil {
    public static void main(String[] args) throws Exception {
        new JsoupUtil().parse("腕表");
    }

    public List<GoodTable> parse(String keyword) throws Exception {
        String url = "https://search.jd.com/Search?keyword="+keyword;
        Document document = Jsoup.parse(new URL(url), 300000);

        Element element = document.getElementById("J_goodsList");
        Elements elements  = element.getElementsByTag("li");

        ArrayList<GoodTable> list = new ArrayList<GoodTable>();
        for(Element e : elements){
            String img = e.getElementsByTag("img").eq(0).attr("src");
            String name = e.getElementsByClass("p-name").eq(0).text();
            String price = e.getElementsByClass("p-price").eq(0).text();
            GoodTable goodTable = new GoodTable();
            goodTable.setGName(name);
            goodTable.setGPrice(Double.parseDouble(price));
            goodTable.setGMainPic(img);
            goodTable.setGMaterial("黄金及蚝式钢");
            goodTable.setGVersion("蚝式，36毫米");
            goodTable.setGState(1);
            list.add(goodTable);
        }
        return list;
    }
}
