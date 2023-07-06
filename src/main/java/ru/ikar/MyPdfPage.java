package ru.ikar;

import java.util.*;

public class MyPdfPage {
    private String text;
    Map<String, Integer> indexWorldsInPage = new HashMap<>();
    private int number;

    MyPdfPage(String text, int number){
        this.text = text;
        this.number = number;
    }

    public void indexPage(){
        var words = text.toLowerCase().split("\\P{IsAlphabetic}+");
        for (String word : words)
        {
            if (!indexWorldsInPage.containsKey(word))
            {
                indexWorldsInPage.put(word, 0);
            }
            indexWorldsInPage.put(word, indexWorldsInPage.get(word) + 1);
        }
    }

    public int getWorldFreq(String world){
        if(indexWorldsInPage.containsKey(world)) return indexWorldsInPage.get(world);
        return 0;
    }

    public int getNumber(){
        return number;
    }

    public Map<String, Integer> getIndexWorldsInPage(){
        return indexWorldsInPage;
    }
}
