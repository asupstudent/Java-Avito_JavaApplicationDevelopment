package com.asupstudent;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class App 
{
    public static void main( String[] args )
    {
        String baseUrl = "https://www.avito.ru/kulebaki/orgtehnika_i_rashodniki/printery-ASgBAgICAUSoAoQK?s=1";
        List<Card> cards = new ArrayList<>();
        Document document = null;
        try {
             document = Jsoup.connect(baseUrl)
                        .timeout(10 * 1000)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 YaBrowser/23.11.0.0 Safari/537.36")
                        .referrer("http://www.google.com")
                        .get();
        }
        catch(IOException ex) {
            System.out.println("Авито не дал ответ, нужно запустить еще раз");
            return;
        }
        // Нашли список карточек
        Element itemsList = document.selectFirst("div[data-marker=catalog-serp]");
        //Нашли в списке все карточки
        Elements items = itemsList.select("div[data-marker=item]");
        for(Element item: items) {
            Card card = new Card();
            /*НАЗВАНИЕ*/
            Element title = item.selectFirst("h3[itemprop=name]");
            /*ОПИСАНИЕ*/
            Element description = item.selectFirst("meta[itemprop=description]");
            /*ЦЕНА + ДЕНЕЖНАЯ ЕДИНИЦА*/
            Element priceContainer = item.selectFirst("p[data-marker=item-price]");
            Element priceCurrency = priceContainer.selectFirst("meta[itemprop=priceCurrency]");
            Element priceCount = priceContainer.selectFirst("meta[itemprop=price]");
            /*СОЗДАЕМ ОБЪЕКТ Card*/
            card.setTitle(title.text());
            card.setDescription(description.attr("content"));
            card.setPrice(priceCount.attr("content"));
            card.setCurrency(priceCurrency.attr("content"));
            cards.add(card);
            /*ВЫВОД В КОНСОЛЬ*/
            // System.out.println(title.text());
            // System.out.println(description.attr("content"));
            // System.out.println(priceCount.attr("content") + " " + priceCurrency.attr("content"));
        }

        File csvFile = new File("output.csv");

        try (PrintWriter printWriter = new PrintWriter(csvFile, StandardCharsets.UTF_8)) {
            // Обработать BOM
            printWriter.write('\ufeff');
            for (Card card : cards) {
                List<String> row = new ArrayList<>();
                row.add("\"" + card.getTitle() + "\"");
                row.add("\"" + card.getDescription() + "\"");
                row.add("\"" + card.getPrice() + "\"");
                row.add("\"" + card.getCurrency() + "\"");

                // Вывод строки в CSV файл
                printWriter.println(String.join(",", row));
            }
        }
        catch(IOException ex) {
            System.out.println("Ошибка записи в CSV файл");
            return;
        }
    }
}
