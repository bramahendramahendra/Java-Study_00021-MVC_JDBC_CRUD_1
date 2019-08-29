/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Brama Hendra Mahendra
 */
public class Mahasiswa {
    private String nim;
    private String nama;
    private String jurursan;
    private char jk;

    public Mahasiswa(String nim, String nama, String jurursan, char jk) {
        this.nim = nim;
        this.nama = nama;
        this.jurursan = jurursan;
        this.jk = jk;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurursan;
    }

    public void setJurusan(String jurursan) {
        this.jurursan = jurursan;
    }

    public char getJk() {
        return jk;
    }

    public void setJk(char jk) {
        this.jk = jk;
    }
    
    
    
    
}
