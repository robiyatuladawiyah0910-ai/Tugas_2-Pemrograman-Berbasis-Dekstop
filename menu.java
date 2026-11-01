public class menu{
    private String Nama;
    private int Harga;
    private String Kategori;

    public menu(String Nama, int Harga, String Kategori){
        this.Nama = Nama;
        this.Harga = Harga;
        this.Kategori = Kategori;

    }
    public String getNama(){
        return Nama;
    }
    public int getHarga(){
        return Harga;
    }
    public String getKategori(){
        return Kategori;
    }
    public void setHarga(int Harga){
        this.Harga = Harga;
    }
    @Override 
    public String toString(){
        return Nama + "(" + Kategori + ")  Rp " + Harga;
    }
}