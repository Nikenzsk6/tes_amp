/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tes_amp;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class class_registrasi {

    private String idRegistrasi, idKegiatan, idAnggota;
    private final Connection con = koneksi.konek(); //penggunaan FINAL membuat variabel koneksi hanya bisa diisi 1x 
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    public String getIdRegistrasi() {
        return idRegistrasi;
    }

    public void setIdRegistrasi(String idRegistrasi) {
        this.idRegistrasi = idRegistrasi;
    }

    public String getIdKegiatan() {
        return idKegiatan;
    }

    public void setIdKegiatan(String idKegiatan) {
        this.idKegiatan = idKegiatan;
    }

    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        this.idAnggota = idAnggota;
    }

    public void simpanRegis() {
        query = "INSERT INTO registrasi VALUES (?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, idRegistrasi);
            ps.setString(2, idKegiatan);
            ps.setString(3, idAnggota);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /*public String cariIdAnggota(String namaAnggota) {
        try {
            query = "SELECT ID_anggota FROM anggota WHERE nama_anggota=?";
            st = con.createStatement();
            ps = con.prepareStatement(query);
            ps.setString(1, namaAnggota);
            rs = ps.executeQuery();

            /*while (rs.next()) {
                return rs.getString("ID_anggota");
            }
            String idAnggota = "";
            if (rs.next()) {
                idAnggota = rs.getString("ID_anggota");
            }
        } catch (SQLException e) {
            System.out.println(e);
            return "";
        }
        return "";
    }*/
    public DefaultTableModel tampilData(String idRegistrasi) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("ID Anggota");
        model.addColumn("Nama Anggota");
        model.addColumn("Status");

        try {
            query = "SELECT r.ID_registrasi, a.ID_anggota, a.nama_anggota, a.status "
                    + "FROM registrasi r "
                    + "JOIN anggota a ON r.ID_anggota = a.ID_anggota "
                    + "WHERE r.ID_registrasi = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, idRegistrasi);
            rs = ps.executeQuery();
            int no = 1;
            while (rs.next()) {
                String idAnggota = rs.getString("ID_anggota");
                String nama = rs.getString("nama_anggota");
                String status = rs.getString("status");

                model.addRow(new Object[]{no++, idAnggota, nama, status});
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return model;
    }
    
    public DefaultTableModel tampilDataAkhir(String idKegiatan, JLabel lbNamaKegiatan, JLabel lbTglMulai) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("ID Anggota");
        model.addColumn("Nama Anggota");
        model.addColumn("Status");

        try {
            query = "SELECT a.ID_anggota, a.nama_anggota, a.status,"
                    + " e.nama_event, e.tgl_mulai "
                    + "FROM registrasi r "
                    + "JOIN anggota a ON r.ID_anggota = a.ID_anggota "
                    + "JOIN event_ujian e ON r.ID_event_ujian = e.ID_event "
                    + "WHERE e.ID_event = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, idKegiatan);
            rs = ps.executeQuery();
            int no = 1;
            while (rs.next()) {
                String idAnggota = rs.getString("ID_anggota");
                String nama = rs.getString("nama_anggota");
                String status = rs.getString("status");
                String namaKegiatan = rs.getString("nama_event");
                String tglMulai = rs.getString("tgl_mulai");
                model.addRow(new Object[]{no++, idAnggota, nama, status});
                lbNamaKegiatan.setText(namaKegiatan);
                lbTglMulai.setText(tglMulai);
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        return model;
    }
    
    public void tampilKegiatan(String idRegistrasi, JLabel lblNamaEvent, JLabel lblTglMulai ){
        try {
            query = "SELECT e.nama_event, e.tgl_mulai FROM event_ujian e"
                    + " JOIN registrasi r ON e.ID_event = r.ID_event_ujian"
                    + " WHERE r.ID_registrasi=?";
            ps=con.prepareStatement(query);
            ps.setString(1, idRegistrasi);
            rs= ps.executeQuery();
            
            if (rs.next()){
                lblNamaEvent.setText(rs.getString("nama_event"));
                lblTglMulai.setText(rs.getString("tgl_mulai"));
            }else{
                lblNamaEvent.setText("-");
                lblTglMulai.setText("-");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
     public DefaultTableModel filterTable(Date tglAwal, Date tglAkhir) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("ID Kegiatan");
        model.addColumn("Nama Kegiatan");
        model.addColumn("Tanggal Mulai");
        model.addColumn("Tanggal Selesai");
        model.addColumn("Lokasi");
        model.addColumn("Jenis Kegiatan");
        model.addColumn("Keterangan");

        try {
            query = "SELECT * FROM event_ujian WHERE tgl_mulai BETWEEN ? AND ? ";

            ps = con.prepareStatement(query);
            // Convert java.util.Date → java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String tglAwalFormatted = sdf.format(tglAwal);
            String tglAkhirFormatted = sdf.format(tglAkhir);

            rs = ps.executeQuery();

            int no = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("ID_event"),
                    rs.getString("nama_event"),
                    rs.getString("tgl_mulai"),
                    rs.getString("tgl_selesai"),
                    rs.getString("lokasi"),
                    rs.getString("jenis"),
                    rs.getString("keterangan")
                });
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return model;
    }
     
      public void aturTable (JTable tData){
        // Warna lembut untuk header
        tData.getTableHeader().setBackground(new Color(102, 204, 255)); // biru pucat (baby blue)
        tData.getTableHeader().setForeground(Color.BLACK);
        tData.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Warna sel tabel (hitam putih natural)
        tData.setBackground(Color.WHITE);
        tData.setForeground(Color.BLACK);
        tData.setGridColor(Color.LIGHT_GRAY);
        tData.setSelectionBackground(new Color(220, 240, 255)); // biru muda saat dipilih
        tData.setSelectionForeground(Color.BLACK);

        // === Mengatur rata tengah teks di tabel ===
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Rata tengah untuk semua kolom
        for (int i = 0; i < tData.getColumnCount(); i++) {
            tData.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Rata tengah header kolom juga
        ((DefaultTableCellRenderer) tData.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        // Mengatur lebar kolom
        tData.getColumnModel().getColumn(0).setPreferredWidth(30);  // No
        tData.getColumnModel().getColumn(1).setPreferredWidth(100); // ID Kegiatan
        tData.getColumnModel().getColumn(2).setPreferredWidth(150); // Nama Kegiatan
        tData.getColumnModel().getColumn(3).setPreferredWidth(100); // Tanggal Mulai
        tData.getColumnModel().getColumn(4).setPreferredWidth(100); // Tanggal Selesai
        tData.getColumnModel().getColumn(5).setPreferredWidth(100); // Lokasi
        tData.getColumnModel().getColumn(6).setPreferredWidth(100); // Jenis Kegiatan
        tData.getColumnModel().getColumn(7).setPreferredWidth(180); // Keterangan
        
    }
}
