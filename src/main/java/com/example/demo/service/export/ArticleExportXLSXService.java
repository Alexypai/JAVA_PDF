package com.example.demo.service.export;

import com.example.demo.repository.ArticleRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class ArticleExportXLSXService {

    @Autowired
    private ArticleRepository articleRepository;

    public void export(OutputStream outputSteam) {
        try {
            // Apache POI
            Workbook wb = new HSSFWorkbook();
            // TODO

            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
