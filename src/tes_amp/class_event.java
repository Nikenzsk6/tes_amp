/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tes_amp;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class class_event {

    String ID_event, nama_event, lokasi, jenis, keterangan, iD_user, tgl_mulai, tgl_selesai;
    private final Connection con = koneksi.konek(); //penggunaan FINAL membuat variabel koneksi hanya bisa diisi 1x 
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    public String getID_event() {
        return ID_event;
    }

    public void setID_event(String ID_event) {
        this.ID_event = ID_event;
    }

    public String getNama_event() {
        return nama_event;
    }

    public void setNama_event(String nama_event) {
        this.nama_event = nama_event;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getiD_user() {
        return iD_user;
    }

    public void setiD_user(String iD_user) {
        this.iD_user = iD_user;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_selesai() {
        return tgl_selesai;
    }

    public void setTgl_selesai(String tgl_selesai) {
        this.tgl_selesai = tgl_selesai;
    }

    public void tambah_event() {
        query = "INSERT INTO event_ujian VALUES (?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, ID_event);
            ps.setString(2, nama_event);
            ps.setString(3, tgl_mulai);
            ps.setString(4, tgl_selesai);
            ps.setString(5, lokasi);
            ps.setString(6, jenis);
            ps.setString(7, keterangan);
            ps.setString(8, iD_user);

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan ");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Ditambahkan ");
        }
    }

    public void ubah_event() {
        query = "UPDATE event_ujian SET nama_event=?, tgl_mulai=?, tgl_selesai=?, lokasi=?, jenis=?,"
                + " keterangan=?, ID_user=? WHERE ID_event=?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nama_event);
            ps.setString(2, tgl_mulai);
            ps.setString(3, tgl_selesai);
            ps.setString(4, lokasi);
            ps.setString(5, jenis);
            ps.setString(6, keterangan);
            ps.setString(7, iD_user);
            ps.setString(8, ID_event);
            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah ");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah ");
        }
    }

    public void hapus_event() {
        query = "DELETE FROM event_ujian WHERE ID_event = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, ID_event);

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus ");
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus ");
        }
    }

    public DefaultTableModel showRegistrasi() {
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
            query = "SELECT * FROM event_ujian WHERE jenis = 'Registrasi'";
            st = con.createStatement();
            rs = st.executeQuery(query);

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

    public String getIdEventByName(String namaEvent) {
        String idEvent = "";
        try {
            query = "SELECT ID_event FROM event_ujian WHERE nama_event=?";
            ps = con.prepareStatement(query);
            ps.setString(1, namaEvent);
            rs = ps.executeQuery();

            if (rs.next()) {
                idEvent = rs.getString("ID_event");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return idEvent;
    }

    public DefaultTableModel showEvent() {
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
            query = "SELECT * FROM event_ujian";

            st = con.createStatement();
            rs = st.executeQuery(query);

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
