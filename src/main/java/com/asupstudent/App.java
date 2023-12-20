package com.asupstudent;

import org.jsoup.Jsoup;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            var document = Jsoup.connect("https://logo6.ru/").get();
            var titleElement = document.selectFirst("title");
            System.out.println("Заголовок страницы");
            System.out.println(titleElement.text());
            System.out.println("Заголовки h3");
            var titleElements = document.select("h3");
            for(var element: titleElements) {
                System.out.println(element.text());
            }
            System.out.println("Адреса ссылок");
            var linkElements = document.select("a");
            for(var element: linkElements) {
                System.out.println(element.attr("href"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
