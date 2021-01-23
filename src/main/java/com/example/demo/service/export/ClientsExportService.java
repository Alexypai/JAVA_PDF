package com.example.demo.service.export;

import com.example.demo.entity.Article;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;

@Service
public class ClientsExportService {
    @Autowired
    private ClientRepository clientRepository;

    public void exportClientsCSV(PrintWriter writer) {
        writer.println("Nom;prenom;Age");
        for (Client client : clientRepository.findAll()) {
            Integer age = LocalDate.now().getYear() - client.getNaisssance().getYear();
            writer.println(client.getNom() + ";" + client.getPrenom() + ";" + age);
        }
    }

    // QUESTION 4
    public void exportClientsExcel(OutputStream outputSteam) {
        try {

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("new sheet=Export_Clients");

            // MISE EN FORME DU TABLEAU
            CellStyle style = wb.createCellStyle();
            style.setBorderBottom(BorderStyle.MEDIUM);
            style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
            style.setBorderLeft(BorderStyle.MEDIUM);
            style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
            style.setBorderRight(BorderStyle.MEDIUM);
            style.setRightBorderColor(IndexedColors.BLUE.getIndex());
            style.setBorderTop(BorderStyle.MEDIUM);
            style.setTopBorderColor(IndexedColors.BLUE.getIndex());

            // MISE EN FORME DE L INDEX
            CellStyle styleIndex = wb.createCellStyle();
            styleIndex.setBorderBottom(BorderStyle.MEDIUM);
            styleIndex.setBottomBorderColor(IndexedColors.BLUE.getIndex());
            styleIndex.setBorderLeft(BorderStyle.MEDIUM);
            styleIndex.setLeftBorderColor(IndexedColors.BLUE.getIndex());
            styleIndex.setBorderRight(BorderStyle.MEDIUM);
            styleIndex.setRightBorderColor(IndexedColors.BLUE.getIndex());
            styleIndex.setBorderTop(BorderStyle.MEDIUM);
            styleIndex.setTopBorderColor(IndexedColors.BLUE.getIndex());


            Font fontIndex = wb.createFont();
            fontIndex.setColor(IndexedColors.PINK.getIndex());
            fontIndex.setBold(true);
            fontIndex.setFontName("Helvetica");
            styleIndex.setFont(fontIndex);

            // INITIALISATION DE L INDEX

            Row row0 = sheet.createRow(0);

            Cell cell00 = row0.createCell(0);
            cell00.setCellValue("Nom");
            cell00.setCellStyle(styleIndex);


            Cell cell01 = row0.createCell(1);
            cell01.setCellValue("Prénom");
            cell01.setCellStyle(styleIndex);

            Cell cell02 = row0.createCell(2);
            cell02.setCellValue("Age");
            cell02.setCellStyle(styleIndex);

            sheet.autoSizeColumn(0);

            // INTRODUCTION DES VALEURS DANS LE TABLEAU

                Integer rowI = 1;
                for (Client client : clientRepository.findAll()) {
                    Row forRow = sheet.createRow(rowI);
                    Integer age = LocalDate.now().getYear() - client.getNaisssance().getYear();
                    for (int cellI = 0; cellI <= 2; cellI++) {
                        Cell cellVal = forRow.createCell(cellI);
                        cellVal.setCellStyle(style);
                        if (cellI == 0){
                            cellVal.setCellValue(client.getNom());
                        }else if (cellI == 1){
                            cellVal.setCellValue(client.getPrenom());
                        }else if(cellI == 2){
                            cellVal.setCellValue(age);
                        }
                        sheet.autoSizeColumn(cellI);
                    }

                    rowI++;
            }
            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
