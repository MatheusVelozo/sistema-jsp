package util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ReportUtil implements Serializable {

    public static final long serialVersionUID = 1L;

    public byte[] geraRelatorioExcel(List listaDados, String nomeRelatorio, HashMap<String, Object> params, jakarta.servlet.ServletContext servletContext) throws Exception{
        /* Cria a lista de dados que vem do SQL da consulta feita */
        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

        String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

        JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);

        JRXlsExporter exporter = new JRXlsExporter();
        ExporterInput exporterInput = new SimpleExporterInput(impressoraJasper);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(baos);

        exporter.setExporterInput(exporterInput);
        exporter.setExporterOutput(exporterOutput);

        try {
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();

    }

    public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, HashMap<String, Object> params, jakarta.servlet.ServletContext servletContext) throws Exception{
        /* Cria a lista de dados que vem do SQL da consulta feita */
        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

        String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

        JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);

        return JasperExportManager.exportReportToPdf(impressoraJasper);

    }
    public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, jakarta.servlet.ServletContext servletContext) throws Exception{
        /* Cria a lista de dados que vem do SQL da consulta feita */
        JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

        String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

        JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap<>(), jrbcds);

        return JasperExportManager.exportReportToPdf(impressoraJasper);

    }
}
