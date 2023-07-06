package ru.ikar;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    List<PdfFile> allPdfFiles = new ArrayList<>();
    List<File> fileNames = new ArrayList<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы

        try (DirectoryStream<Path> files = Files.newDirectoryStream(pdfsDir.toPath())) {
            for (Path path : files)
                fileNames.add(path.toFile());
        }


        for (File pdfFileName : fileNames){
            var doc = new PdfDocument(new PdfReader(pdfFileName));
            var pageNumbers = doc.getNumberOfPages();
            PdfFile pdfFile = new PdfFile(pdfFileName.getName());
            allPdfFiles.add(pdfFile);
            for(int i = 1; i <= pageNumbers; i++) {
                var fromPage = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(fromPage);
                MyPdfPage page = new MyPdfPage(text, i);
                page.indexPage();
                pdfFile.addPage(page);
            }
        }


    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> entries = new ArrayList<>();
        for(PdfFile pdf : allPdfFiles){
            List<PageEntry> entriesFromPage = pdf.getEntries(word);
            if(!entriesFromPage.isEmpty()){
                entries.addAll(entriesFromPage);
            }
        }
        Collections.sort(entries);
        return entries;
    }
}
