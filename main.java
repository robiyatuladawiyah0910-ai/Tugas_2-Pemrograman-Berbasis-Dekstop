import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.text.NumberFormat;

public class main{
    static Scanner sc =  new Scanner(System.in);
    static NumberFormat nf = NumberFormat.getInstance(new Locale("id","ID"));

    public static void main(String[]args){
        ArrayList<menu>menus = new ArrayList<>();
        seedmenu(menus);

        while(true){
            System.out.println("\n ====== SISTEM RESTORAN RAMAI =====");
            System.out.println("1. Pesanan (Pelanggan)");
            System.out.println("2. Manajemen Menu (Owner / Admin)");
            System.out.println("3. Keluar");
            System.out.print("Pilih Menu : ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")){
                pelangganFlow(menus);
            }else if (choice.equals("2")){
                manajemenFlow(menus);
            }else if(choice.equals("3")){
                System.out.println("Terimakasi telah Datang di Restoran Kami");
                break;
            }else{
                System.out.println("Pilihan Tidak ada pada menu, silakan coba lagi");
            }
        }
    }
    static void seedmenu(ArrayList<menu>menus){
        menus.add(new menu("Nasi Kebuli", 35000, "Makanan"));
        menus.add(new menu("Sate Kambing", 45000, "Makanan"));
        menus.add(new menu("Gule kambing", 40000, "Makanan"));
        menus.add(new menu("Iga Bakar", 50000, "Makanan"));
        menus.add(new menu("Jus Jeruk peras", 7000, "Minuman"));
        menus.add(new menu("Teh Hangat", 5000, "Minuman"));
        menus.add(new menu("Es Teh", 5000, "Minuman"));
        menus.add(new menu("Air Putih", 3000, "Minuman"));
    }
    static void MenampilkanDaftar(ArrayList<menu>menus){
        System.out.println("\n ===== Daftar Menu =====");
        for (int i =0;i<menus.size(); i++){
            menu m = menus.get(i);
            System.out.println((i+1) + ". " + m.getNama() + " (" + m.getKategori() +  ") - Rp " + nf.format(m.getHarga()));

        }
    }
    static void pelangganFlow(ArrayList<menu>menus){
        ArrayList<Integer>orderMenuIndex = new ArrayList<>();
         ArrayList<Integer>orderJumlah = new ArrayList<>();

         System.out.println("\n == Menu Pesanan Pelanggan ===");
         MenampilkanDaftar(menus);
         
         while(true){
            System.out.print("Memasukkan No. Menu (jika tidak 'ketik selesai') : ");
            String in = sc.nextLine().trim();
            if(in.equalsIgnoreCase("Selesai")) break;
            int nomor;
            try{
                nomor = Integer.parseInt(in);
            }catch(NumberFormatException e){
                System.out.println("Masukan Tidak Valid");
                continue;
            }
            if(nomor<1 || nomor > menus.size()){
                System.out.println("Nomor Menu Tidak Tersedia");
                continue;
            }
            System.out.println("Jumlah : ");
            int jumlah = Integer.parseInt(sc.nextLine().trim());
            if(jumlah <= 0){
                System.out.println("Jumlah tidak boleh 0 / nol");
                continue;
            }
            orderMenuIndex.add(nomor - 1);
            orderJumlah.add(jumlah);
            System.out.println("Ditambahkan : " + menus.get(nomor - 1).getNama() + " x" + jumlah);
         }
         if(orderMenuIndex.isEmpty()){
            System.out.println("Tidak ada pesanan, kembali pada mennu utama");
            return;
         }
         HitungdanCetakStruk(menus, orderMenuIndex, orderJumlah);
    }
    static void HitungdanCetakStruk(ArrayList<menu>menus,ArrayList<Integer>idxs,ArrayList<Integer>jumlah){
        int subtotal = 0;
        boolean Minuman = false;
        ArrayList<Integer>minumanIndicesInOrder = new ArrayList<>();
        for (int k = 0; k<idxs.size();k++){
            int min = idxs.get(k);
            menu m = menus.get(min);
            int Harga = m.getHarga() * jumlah.get(k);
            subtotal += Harga;
            if(m.getKategori().equalsIgnoreCase("Minuman")){
                Minuman = true;
                minumanIndicesInOrder.add(k);
            }
        }
        double diskon = 0.0;
        double BogoDiskon = 0.0;
        if(subtotal > 100000){
            diskon = subtotal * 0.10;
        }else if (subtotal > 500000 && Minuman){
            int HargamaxMinuman = 0;
            for(int pos : minumanIndicesInOrder){
                menu m = menus.get(idxs.get(pos));
                if(m.getHarga() > HargamaxMinuman)HargamaxMinuman = m.getHarga();
            }
            BogoDiskon = HargamaxMinuman;

        }
        double subtotalAfterPromo = subtotal - diskon - BogoDiskon;
        double pajak = subtotalAfterPromo * 0.10;
        int biayaPelayanan = 2000;
        double totalAkhir = subtotalAfterPromo + pajak + biayaPelayanan;

        System.out.println("\n==== STRUK PEMESANAN ====");
        for(int k = 0;k<idxs.size();k++){
            menu m = menus.get(idxs.get(k));
            int totalItem = m.getHarga() * jumlah.get(k);
            System.out.println((m.getNama() + " x" + jumlah.get(k) + " = Rp " + nf.format(totalItem)));
        }
    System.out.println("-----------------------------");
        System.out.println("Subtotal : Rp " + nf.format(subtotal));
        if (diskon > 0)System.out.println("Diskon 10% : -Rp " +nf.format((int)diskon));
        if (BogoDiskon > 0)System.out.println("Promo minuman gratis: -Rp " + nf.format((int)BogoDiskon));
        System.out.println("Subtotal setelah promo : Rp " + nf.format((int)subtotalAfterPromo));
        System.out.println("pajak 10%: Rp " + nf.format((int)pajak));
        System.out.println("Biaya Pelayanan: Rp " + nf.format(biayaPelayanan));
        System.out.println("Total Bayar : Rp " + nf.format((int)totalAkhir));
        System.out.println("===== Terima Kasih ====");
    }
    static void manajemenFlow(ArrayList<menu>menus){
        while(true){
            System.out.println("\n ---- Manajemen menu Resto ----");
            System.out.println("1. Tambahkan Menu");
            System.out.println("2. Ubah harga");
            System.out.println("3. Hapus menu");
            System.out.println("4. Lihat daftar menu");
            System.out.println("5. kembali");
            System.out.println("Pilih : ");
            String pilihan = sc.nextLine().trim();

            if(pilihan.equals("1"))tambahMenu(menus);
            else if (pilihan.equals("2"))ubahHarga(menus);
            else if (pilihan.equals("3"))hapusMenu(menus);
            else if (pilihan.equals("4"))MenampilkanDaftar(menus);
            else if (pilihan.equals("5"))break;
            else System.out.println("Pilihann Tidak Valid");
        }
    }
    static void tambahMenu(ArrayList<menu>menus){
        System.out.println("Masukkan menu baru: ");
        String Nama = sc.nextLine().trim();
        System.out.println("Harga: ");
        int Harga = Integer.parseInt(sc.nextLine().trim());
        System.out.println("Kategori makanana / minuman : ");
        String Kategori = sc.nextLine().trim();
        menus.add(new menu(Nama,Harga,Kategori));
        System.out.println("Menu berhasil ditambahkan");
    }
    static void ubahHarga(ArrayList<menu>menus){
        MenampilkanDaftar(menus);
        System.out.println("Nomor menu yang ingin dirubah : ");
        int no = Integer.parseInt(sc.nextLine().trim());
        if(no < 1 || no > menus.size()){
            System.out.println("Nomor tidak valid");
            return;
        }
        menu m = menus.get(no-1);
        System.out.println("Anda memilih : " + m.getNama());
        System.out.println("Yakin ubah harga? (ya/tidak) : ");
        String konfir = sc.nextLine().trim();
        if(konfir.equalsIgnoreCase("ya")){
            System.out.println("Silakan masukkan harga baru: ");
            int hargabaru = Integer.parseInt(sc.nextLine().trim());
            m.setHarga(hargabaru);
            System.out.println("Berhasil ubah harga");
        }else System.out.println("Dibatalakan");
    }
    static void hapusMenu(ArrayList<menu>menus){
        MenampilkanDaftar(menus);
        System.out.println("Nomor menu yang ingin dihapus: ");
        int no = Integer.parseInt(sc.nextLine().trim());
        if(no < 1 || no > menus.size()){
            System.out.println("Nomor tidak valid");
            return;
        }
        menu m = menus.get(no-1);
        System.out.println("Yakin menghapus " + m.getNama() + "? (ya/tidak): ");
        String konfir = sc.nextLine().trim();
        if(konfir.equalsIgnoreCase("ya")){
            menus.remove(no-1);
            System.out.println("Berhasil menghapus menu");
        }else System.out.println("Menghapus dibatalkan");
    }

}