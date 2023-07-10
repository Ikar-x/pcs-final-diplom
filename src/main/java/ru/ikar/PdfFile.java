package ru.ikar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PdfFile {
    private List<MyPdfPage> pages = new ArrayList<>();
    private String name;
    private Map<String, Integer> indexWorldsInFile = new HashMap<>();

    private int contPages = 1;

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

    public void indexingFile(){
        for(MyPdfPage pages : this.pages){
            indexWorldsInFile = Stream.of(indexWorldsInFile, pages.getIndexWorldsInPage())
                    .flatMap(m -> m.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue + newValue));
        }
    }

    public Map<String, Integer> getIndexWorldsInFile() {
        return indexWorldsInFile;
    }
}
