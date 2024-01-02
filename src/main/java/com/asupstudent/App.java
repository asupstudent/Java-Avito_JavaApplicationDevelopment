package com.asupstudent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        String baseUrl = "https://www.avito.ru/kulebaki/orgtehnika_i_rashodniki/printery-ASgBAgICAUSoAoQK?s=1";
        // List<Card> cards = new ArrayList<>();
        var document = Jsoup.connect(baseUrl)
                    .timeout(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 YaBrowser/23.11.0.0 Safari/537.36")
                    .referrer("https://ya.ru/")
                    .get();
        // Нашли список карточек
        Element itemsList = document.selectFirst("div[data-marker=catalog-serp]");
        //Нашли в списке все карточки
        Elements items = itemsList.select("div[data-marker=item]");
        for(Element item: items) {
            /*НАЗВАНИЕ*/
            Element title = item.selectFirst("h3[itemprop=name]");
            /*ОПИСАНИЕ*/
            Element description = item.selectFirst("meta[itemprop=description]");
            /*ЦЕНА + ДЕНЕЖНАЯ ЕДИНИЦА*/
            Element priceContainer = item.selectFirst("p[data-marker=item-price]");
            Element priceCurrency = priceContainer.selectFirst("meta[itemprop=priceCurrency]");
            Element priceCount = priceContainer.selectFirst("meta[itemprop=price]");
            System.out.println(title.text());
            System.out.println(description.attr("content"));
            System.out.println(priceCount.attr("content") + " " + priceCurrency.attr("content"));
        }
    }
}
