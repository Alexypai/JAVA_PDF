package com.example.demo.service.export;

import com.example.demo.controller.export.ExportController;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import com.example.demo.repository.LigneFactureRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.FactureService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FactureExportService {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private LigneFactureRepository ligneFactureRepository;

    @Autowired
    private ClientRepository clientRepository;




    public void exportFactureExcel(OutputStream outputSteam, Long id) {
        try {

            // STYLE DE CERTAINES CELLULES EN GRAS
            Workbook wb = new XSSFWorkbook();
            CellStyle styleIndex = wb.createCellStyle();
            Font fontIndex = wb.createFont();
            fontIndex.setBold(true);
            fontIndex.setFontName("Helvetica");
            styleIndex.setFont(fontIndex);

            // PREMIERE FEUILLES TOUTES LES FACTURES CLIENT AVEC LEURS ID

            Optional<Client> client = clientRepository.findById(id);
            Sheet sheetTotal = wb.createSheet(client.get().getNom()+" "+client.get().getPrenom());

            Integer CountFacture = 0;
            Row row3 = sheetTotal.createRow(3);

            // INITIALISATION DE L INDEX + INTRODUCTION DES DONNEES

            for (Facture facture : factureRepository.findAllByClient(client)) {
                // INITIALISATION INDEX
                if (CountFacture == 0){
                    String name = facture.getClient().getNom();
                    String lastName = facture.getClient().getPrenom();
                    Integer birthday = facture.getClient().getNaisssance().getYear();
                    for (int rowI = 0; rowI <= 2; rowI++) {
                        Row row = sheetTotal.createRow(rowI);
                        Cell cell0 = row.createCell(0);
                        Cell cell1 = row.createCell(1);
                        if (rowI == 0) {
                            cell0.setCellValue("Nom : ");
                            cell1.setCellValue(name);
                        } else if (rowI == 1) {
                            cell0.setCellValue("Prénom :");
                            cell1.setCellValue(lastName);
                        } else if (rowI == 2) {
                            cell0.setCellValue("Année de naissance :");
                            cell1.setCellValue(birthday);
                            sheetTotal.autoSizeColumn(0);
                        }
                    }
                }

                // INTRODUCTION DES DONNEES

                Integer cellVal = CountFacture + 1;
                Cell idFactureCell = row3.createCell(cellVal);
                idFactureCell.setCellValue(facture.getId());
                CountFacture++;
                Cell cell03 = row3.createCell(0);
                cell03.setCellValue(CountFacture + " facture(s) :");
                cell03.setCellStyle(styleIndex);

            }

            // NOUVELLE FEUILLES POUR CHAQUE FACTURE AVEC TOTAL DES DEPENSES DE LA FACTURE
            for (Facture facture : factureRepository.findAllByClient(client)) {
                // CREATION DE LA PAGE + INITIALISATION
                Sheet sheetFacture = wb.createSheet("Facture n°"+ facture.getId());
                Row initRow = sheetFacture.createRow(0);
                Cell cell00 = initRow.createCell(0);
                Cell cell01 = initRow.createCell(1);
                Cell cell02 = initRow.createCell(2);
                cell00.setCellValue("Désignation");
                cell00.setCellStyle(styleIndex);
                cell01.setCellValue("Quantité");
                cell01.setCellStyle(styleIndex);
                cell02.setCellValue("Prix unitaire");
                cell02.setCellStyle(styleIndex);
                Integer countLigneFacture = 0;
                double Total = 0;
                // INTRODUCTION DES DONNEES
                for (LigneFacture ligneFacture : ligneFactureRepository.findAllByFacture(Optional.of(facture))) {
                    Row row = sheetFacture.createRow(countLigneFacture + 1);
                    //Row row = sheetFacture.createRow(0);
                    for (int cellI = 0; cellI <= 2; cellI++) {
                        Cell cellVal = row.createCell(cellI);
                        if (cellI == 0) {
                            cellVal.setCellValue(ligneFacture.getArticle().getLibelle());
                        } else if (cellI == 1) {
                            cellVal.setCellValue(ligneFacture.getQuantite());
                        } else if (cellI == 2) {
                            cellVal.setCellValue(ligneFacture.getArticle().getPrix());
                        }
                        sheetFacture.autoSizeColumn(cellI);
                    }
                    // COMPTE LE TOTAL DES DEPENSES
                    double totalLigne = ligneFacture.getQuantite() * ligneFacture.getArticle().getPrix();
                    Total = Total + totalLigne;
                    countLigneFacture++;
                }
                // INTRODUCTION DU TOTAL
                Row totalRow = sheetFacture.createRow(3);
                Cell totalNameCell = totalRow.createCell(1);
                Cell totalCell = totalRow.createCell(2);
                totalNameCell.setCellValue("Total");
                totalNameCell.setCellStyle(styleIndex);
                totalCell.setCellValue(Total);
            }
            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
