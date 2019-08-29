/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Mahasiswa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brama Hendra Mahendra
 */
public class Database {
    private Connection conn = null;
    private Statement stat = null;
    private ResultSet ress = null;
    private ArrayList<Mahasiswa> mahasiswa = new ArrayList<>();

    public Database() {
        loadMahasiswa();
    }
    
    public void connect () {
        try {
            String url = "jdbc:mysql://localhost/project_java_testo";
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(url, user, pass);
            stat = conn.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void disconnect() {
        try {
            conn.close();
            stat.close();
        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public boolean manipulate (String query) {
        boolean cek = false;
        try {
            int rows = stat.executeUpdate(query);
            if (rows > 0) {
                cek = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        return cek;
    }
    
    public ArrayList<Mahasiswa> getMahasiswa() {
        return mahasiswa;
    }
    
    public boolean cekDuplikatNIM(String nim) { //untuk mengecek apakah ada kata duplikat atau tidak
        boolean cek = false;
        for (Mahasiswa m : mahasiswa){
            if (m.getNim().equals(nim)) {
                cek = true;
                break;
            }
        }
        return cek;
    }
     
    public void addMahasiswa(Mahasiswa m){ //insert data ke databas
        connect();
        String query = "INSERT INTO mahasiswa VALUES (";
        query += "'" + m.getNim() + "',";
        query += "'" + m.getNama()+ "',";
        query += "'" + m.getJurusan()+ "',";
        query += "'" + m.getJk()+ "'";
        query += ")";
        if(manipulate(query)) mahasiswa.add(m);
        disconnect();
    }
    
    public void loadMahasiswa() {
        connect();
        try {
            String query = "Select * From mahasiswa";
            ress = stat.executeQuery(query);
            while (ress.next()) {
                mahasiswa.add(new Mahasiswa(ress.getString("nim"), ress.getString("nama"), ress.getString("jurusan"), ress.getString("jk").charAt(0)));
            }
        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        disconnect();
    }
    
    public void delMahasiswa (String nim) {
        connect();
        String query = "DELETE FROM mahasiswa WHERE nim='" + nim + "'";
        if (manipulate(query)) {
            for (Mahasiswa mhs : mahasiswa) {
                if (mhs.getNim().equals(nim)) {
                    mahasiswa.remove(mhs);
                    break;
                }
            }
        }
        disconnect();
    }
    
    public void updateMahasiswa (Mahasiswa m) {
        connect();
        String query = "UPDATE mahasiswa SET";
        query += " nama='" + m.getNama() + "',";
        query += " jurusan='" + m.getJurusan()+ "',";
        query += " jk='" + m.getJk()+ "'";
        query += " WHERE nim='" + m.getNim()+ "'";
        if (manipulate(query)) {
            for (Mahasiswa mhs : mahasiswa) {
                if (mhs.getNim().equals(m.getNim())) {
                    mhs.setNama(m.getNama());
                    mhs.setJurusan(m.getJurusan());
                    mhs.setJk(m.getJk());
                    break;
                }
            }
        }
        disconnect();
    }
    
}
