package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;

@Service
public class ArticleExportCVSService {

    @Autowired
    private ArticleRepository articleRepository;

    public void exportArticlesCSV(PrintWriter writer) {
        writer.println("Libelle;prix;stock");
        for (Article article:articleRepository.findAll()){
                writer.println("\""+article.getLibelle()+"\""+";"+article.getPrix()+";"+article.getStock());
        }
    }

}
