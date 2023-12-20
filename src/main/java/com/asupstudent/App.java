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
            System.out.println(titleElement.text());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
