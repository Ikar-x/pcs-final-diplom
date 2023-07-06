package ru.ikar;

import java.util.ArrayList;
import java.util.List;

public class PdfFile {
    private List<MyPdfPage> pages = new ArrayList<>();
    private String name;

    int contPages = 1;

    PdfFile (String path){
        name = path;
        System.out.println("\nЗагружен файл: " + path);
        System.out.print("Добавляем страницы");
    }

    public void addPage(MyPdfPage page) {
        this.pages.add(page);
        System.out.print(" " + contPages);
        contPages++;
    }

    public List<PageEntry> getEntries(String word){
        List<PageEntry> entries = new ArrayList<>();
        for(MyPdfPage page : pages){
            int freq = page.getWorldFreq(word);
            if(freq > 0){
                entries.add(new PageEntry(name, page.getNumber(), freq));
            }
        }
        return entries;
    }
}
