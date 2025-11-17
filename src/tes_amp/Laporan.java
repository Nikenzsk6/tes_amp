/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tes_amp;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.io.FileOutputStream;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class Laporan {

    public void generateDataRegistrasi(String idKegiatan, JLabel lbNama, JLabel lbTgl) {

        try {
            // Format tanggal hari ini (YYYY-MM-DD)
            String tanggal = new java.text.SimpleDateFormat("yyyy-MM-dd")
                    .format(new java.util.Date());

            // Gabungkan menjadi nama file
            String fileName = "Laporan_Data_Anggota_Registrasi_PORSIGAL_" + tanggal + ".pdf";
            String path = System.getProperty("user.home") + "/OneDrive/Documents/laporan/Laporan_Data_registrasi.pdf";

            // Buat dokumen dan writer
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            class_registrasi regis = new class_registrasi();
            DefaultTableModel model = regis.tampilDataAkhir(idKegiatan, lbNama, lbTgl);

            // === Header PDF ===
            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Laporan Registrasi Peserta", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Nama Event     : " + lbNama.getText()));
            document.add(new Paragraph("Tanggal Mulai  : " + lbTgl.getText()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Daftar Peserta:", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(new Paragraph(" "));

            // === Tabel PDF ===
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            // Atur lebar setiap kolom (dalam satuan relatif)
            float[] columnWidths = {1f, 3f, 5f, 3f};
            table.setWidths(columnWidths);

            // Header
            PdfPCell cell;

            cell = new PdfPCell(new Phrase("No"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("ID Anggota"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Nama Anggota"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Status"));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            for (int i = 0; i < model.getRowCount(); i++) {
                table.addCell(model.getValueAt(i, 0).toString()); // No
                table.addCell(model.getValueAt(i, 1).toString()); // ID
                table.addCell(model.getValueAt(i, 2).toString()); // Nama Anggota
                table.addCell(model.getValueAt(i, 3).toString()); // Status
            }

            document.add(table);

            document.add(new Paragraph("\nLaporan dibuat otomatis oleh sistem.", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));

            document.close();
            JOptionPane.showMessageDialog(null, "PDF berhasil dibuat!");
            System.out.println("PDF berhasil dibuat dari method tampilDataAkhir!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void generateLaporanEvent(Date tglAwal, Date tglAkhir) {

        try {
            // Format tanggal hari ini (YYYY-MM-DD)
            String tanggal = new java.text.SimpleDateFormat("yyyy-MM-dd")
                    .format(new java.util.Date());

            // Gabungkan menjadi nama file
            String fileName = "Laporan_Data_Event_PORSIGAL_" + tanggal + ".pdf";
            String path = System.getProperty("user.home") + "/OneDrive/Documents/laporan/"+fileName;

            // Buat dokumen dan writer
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            class_event event = new class_event();
            DefaultTableModel model = event.filterTable(tglAwal, tglAkhir);

            // === FONT ===
            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font fontSubTitle = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Font fontCell = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
            Font fontFooter = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC);

            // === JUDUL ===
            Paragraph title = new Paragraph("Laporan Data Event", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\nLAPORAN DATA EVENT", fontSubTitle));
            document.add(new Paragraph("Rentang: " + tglAwal + " s/d " + tglAkhir, fontSubTitle));
            document.add(new Paragraph(" "));

            // === TABEL ===
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 3f, 4f, 3f, 3f, 3f, 3f, 5f});

            // HEADER
            PdfPCell header;

            String[] headers = {
                "No", "ID Kegiatan", "Nama Kegiatan", "Tanggal Mulai",
                "Tanggal Selesai", "Lokasi", "Jenis", "Keterangan"
            };

            for (String h : headers) {
                header = new PdfPCell(new Phrase(h, fontHeader));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(header);
            }

            // ISI TABEL
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    table.addCell(new Phrase(model.getValueAt(i, j).toString(), fontCell));
                }
            }

            document.add(table);

            // FOOTER
            document.add(new Paragraph("\nLaporan dibuat otomatis oleh sistem.", fontFooter));

            document.close();
            JOptionPane.showMessageDialog(null, "PDF berhasil dibuat!");
            //            System.out.println("PDF berhasil dibuat dari method tampilDataAkhir!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void generateLaporanRegistrasi(Date tglAwal, Date tglAkhir) {

        try {
            // Format tanggal hari ini (YYYY-MM-DD)
            String tanggal = new java.text.SimpleDateFormat("yyyy-MM-dd")
                    .format(new java.util.Date());

            // Gabungkan menjadi nama file
            String fileName = "Laporan_Data_Registrasi_PORSIGAL_" + tanggal + ".pdf";
            String path = System.getProperty("user.home") + "/OneDrive/Documents/laporan/"+fileName;

            // Buat dokumen dan writer
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            class_registrasi regis = new class_registrasi();
            DefaultTableModel model = regis.filterTable(tglAwal, tglAkhir);

            // === FONT ===
            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font fontSubTitle = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Font fontCell = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
            Font fontFooter = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC);

            // === JUDUL ===
            Paragraph title = new Paragraph("Laporan Data Event", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\nLAPORAN DATA EVENT", fontSubTitle));
            document.add(new Paragraph("Rentang: " + tglAwal + " s/d " + tglAkhir, fontSubTitle));
            document.add(new Paragraph(" "));

            // === TABEL ===
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 3f, 4f, 3f, 3f, 3f, 3f, 5f});

            // HEADER
            PdfPCell header;

            String[] headers = {
                "No", "ID Kegiatan", "Nama Kegiatan", "Tanggal Mulai",
                "Tanggal Selesai", "Lokasi", "Jenis", "Keterangan"
            };

            for (String h : headers) {
                header = new PdfPCell(new Phrase(h, fontHeader));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(header);
            }

            // ISI TABEL
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    table.addCell(new Phrase(model.getValueAt(i, j).toString(), fontCell));
                }
            }

            document.add(table);

            // FOOTER
            document.add(new Paragraph("\nLaporan dibuat otomatis oleh sistem.", fontFooter));

            document.close();
            JOptionPane.showMessageDialog(null, "PDF berhasil dibuat!");
            //            System.out.println("PDF berhasil dibuat dari method tampilDataAkhir!");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
