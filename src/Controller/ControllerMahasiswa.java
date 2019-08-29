/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Mahasiswa;
import View.ViewMahasiswa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Brama Hendra Mahendra
 */
public class ControllerMahasiswa extends MouseAdapter implements ActionListener{
    
    private ViewMahasiswa view;
    private Database db;

    public ControllerMahasiswa() { //untuk mengatur semua fungsionalitas yg nantinya dipanggil di driver
        view = new ViewMahasiswa();
        db = new Database();
        view.addActionListener(this);
        view.addMouseAdapter(this);
        view.setVisible(true);
        loadTable();
    }
    
    public void loadTable() { //untuk mengatur konfigurasi pada table
       DefaultTableModel model = new DefaultTableModel(new String[]{"NIM","Nama","Jurusan","Jenis Kelamin"}, 0);
        ArrayList<Mahasiswa> mahasiswa = db.getMahasiswa();
        for (Mahasiswa m : mahasiswa) {
            model.addRow(new Object[]{m.getNim(), m.getNama(), m.getJurusan(), m.getJk()});
        }
        view.setTableMahasiswa(model);
    }
    
    public void buttonTambahActionPerformed(){//untuk mengoreksi button apakah dalam keadaan kosong atau tidak
        String nim = view.getNim();
        String nama = view.getNama();
        String jurusan = view.getJurusan();
        char jk = view.getJeniskelamin();
        if (nim.isEmpty() || nama.isEmpty() || jurusan.isEmpty() || jk==' ') {
            view.showMassage("Data Kosong", "Error", 0);
        } else {
            if (db.cekDuplikatNIM(nim)) {
                view.showMassage("NIM sudah ada", "error", 0);
            } else {
                db.addMahasiswa(new Mahasiswa(nim, nama, jurusan, jk));
                view.reset();
                view.showMassage("Data Berhasil Ditambah", "Success", 1);
            }
        }
    }
    
    public void buttonHapusActionPerformed() {
        String nim = view.getNim();
        db.delMahasiswa(nim);
        view.reset();
        view.showMassage("Data Berhasil Dihapus", "Success", 1);
    }
    
    public void buttonUbahActionPerformed(){
        String nim = view.getNim();
        String nama = view.getNama();
        String jurusan = view.getJurusan();
        char jk = view.getJeniskelamin();
        if (nim.isEmpty() || nama.isEmpty() || jurusan.isEmpty() || jk==' ') {
            view.showMassage("Data Kosng", "Error", 0);
        } else {
            if (!db.cekDuplikatNIM(nim)) {
                view.showMassage("NIM tidak ditemukan", "Error", 0);
            } else {
                db.updateMahasiswa(new Mahasiswa(nim, nama, jurusan, jk));
                view.reset();
                view.showMassage("Data berhasil diubah", "success", 1);
            }
        }
    }
    
    public void buttonCariActionPerformed() {
        String cari = view.getCari();
        DefaultTableModel model = new DefaultTableModel(new String[]{"NIM","Nama","Jurusan","Jenis Kelamin"}, 0);
        ArrayList<Mahasiswa> mahasiswa = db.getMahasiswa();
        for (Mahasiswa m : mahasiswa) { 
            if (m.getNim().contains(cari) || m.getNama().contains(cari) || m.getJurusan().contains(cari)) {
                model.addRow(new Object[]{m.getNim(), m.getNama(), m.getJurusan(), m.getJk()});
            }
        }
        view.setTableMahasiswa(model);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source.equals(view.getjButton_tambah())) {
            buttonTambahActionPerformed();
            loadTable();            
        } else if (source.equals(view.getjButton_hapus())) {
            buttonHapusActionPerformed();
            loadTable();
        } else if (source.equals(view.getjButton_ubah())) {
            buttonUbahActionPerformed();
            loadTable();
        } else if (source.equals(view.getjButton_reset())) {
            view.reset();
            loadTable();
        } else if (source.equals(view.getjButton_cari())) {
            buttonCariActionPerformed();
        }

    }
    
    @Override
    public void mousePressed (MouseEvent me) {
        Object source = me.getSource();
        if (source.equals(view.getTableMahasiswa())) {
            int i = view.getSelectedMahasiswa();
            String nim = view.getTableMahasiswa().getModel().getValueAt(i, 0).toString();
            String nama = view.getTableMahasiswa().getModel().getValueAt(i, 1).toString();
            String jurusan = view.getTableMahasiswa().getModel().getValueAt(i, 2).toString();
            char jk = view.getTableMahasiswa().getModel().getValueAt(i, 3).toString().charAt(0);
            
            view.setNim(nim);
            view.setNama(nama);
            view.setJurusan(jurusan);
            view.setJenisKelamin(jk);
        }
    }
    
}
