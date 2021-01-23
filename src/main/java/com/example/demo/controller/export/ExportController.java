package com.example.demo.controller.export;

import com.example.demo.service.export.ArticleExportCVSService;
import com.example.demo.service.export.ClientsExportService;
import com.example.demo.service.export.ExportPDFITextService;
import com.example.demo.service.export.FactureExportService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Controller pour réaliser export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportController {

    @Autowired
    private ClientsExportService clientsExportService;

    @Autowired
    private ArticleExportCVSService articleExportCVSService;

    @Autowired
    private FactureExportService factureExportService;

    @Autowired
    private ExportPDFITextService exportPDFITextService;

    /**
     * Export des articles au format CSV.
     */
    @GetMapping("/clients/csv")
    public void clientCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");
        PrintWriter writer = response.getWriter();
        clientsExportService.exportClientsCSV(writer);
    }

    @GetMapping("/articles/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");
        PrintWriter writer = response.getWriter();
        articleExportCVSService.exportArticlesCSV(writer);
    }


    @GetMapping("/clients/xlsx")
    public void articlesXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        clientsExportService.exportClientsExcel(outputStream);
    }

    /** Méthode utilisée pour l'export facture (point n°5) */
    @GetMapping("/clients/{id}/factures/xlsx")
    public void clientGetFacturesXLSX(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"client-" + id + "-factures.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        factureExportService.exportFactureExcel(outputStream, id);
    }

    @GetMapping("/articles/pdf")
    public void facturesPDF(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        exportPDFITextService.exportPDF(response.getOutputStream());
    }



}
