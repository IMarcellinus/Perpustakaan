import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
/*
UTS PBO
TI-1A_15_Marcellinus
*/
public class perpustakaan {
    static dataUser user_now;
    static ArrayList<dataBuku> dtBuku = new ArrayList<dataBuku>();
    public void tampilkanBukuDipinjam() {
        System.out.println("==============================");
        System.out.println("Nama Buku");
        System.out.println("------------------------------");
        int j = 0;
        for (dataBuku i : user_now.bukuDipinjam) {
            System.out.println(j + ". " + i.getnamaBuku());
            j++;
        }
        System.out.println("==============================");
    }
    private static void clearScreen () throws IOException
    {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex) {
            System.err.println("tidak bisa clear screen");
        }
    }
    private String pilihLogin()
    {
        Scanner input = new Scanner(System.in);
        String pil = input.nextLine();
        return pil;
    }
    private int pilihLoginint()
    {
        Scanner input = new Scanner(System.in);
        int pil = input.nextInt();
        return pil;
    }
    public void tampilkanBuku() {
        System.out.println("==============================");
        System.out.println("No | Nama Buku\t| Jumlah Buku");
        System.out.println("------------------------------");
        int j = 0;
        for (dataBuku i : dtBuku) {
            System.out.println(j + "." + "|" + i.getnamaBuku() + "\t |\t " + i.getjumlahBuku());
            j++;
        }
        System.out.println("==============================");
    }
    public void kembalikanBuku(String nama_buku){
        boolean con = false, con2 = false;
        int index = 0, index2 = 0;
        // cek apakah ada buku di user
        for (dataBuku i : user_now.bukuDipinjam){
            con = (i.getnamaBuku().equals(nama_buku));
            if (con == true){
                index = user_now.bukuDipinjam.indexOf(i);
                break;
            }
        }
        // mencari index buku yg dikembalikan di perpus
        for (dataBuku i : dtBuku) {
            con2 = (i.getnamaBuku().equals(nama_buku));
            if (con2 == true) {
                index2 = dtBuku.indexOf(i);
                break;
            }
        }
        // if ada buku then kembalikan buku dr user ke perpus
        if (con && con2){
            user_now.bukuDipinjam.remove(index);
            dtBuku.get(index2).tambahKuota(1);
            System.out.println("\n" + nama_buku + " telah dikembalikan!");
            return;
        } else {
            System.out.println("\nGagal mengembalikan buku!");
        }
    }
    
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);
        perpustakaan Perpus = new perpustakaan();
        String userinputUsername;
        String userinputPassword;
        int pilihan;
        int index = 0;
        ArrayList<dataUser> dtUser = new ArrayList<dataUser>();
        dataAdmin dtAdm = new dataAdmin("as", "123");
        boolean con = false, con2 = false;
        int jumlahKuotaBuku,x;
        int pilihan_SewaBuku;
        int pilihanUser;
        int pilihanAdmin;
        int pilihanManageUser;
        int pilihanManageBuku;
        do{
            clearScreen();
            // Halaman Utama
            System.out.println("========================================");
            System.out.println("Selamat Datang Di Perpustakaan");
            System.out.println("Login Menu :");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Loqout");
            System.out.println("========================================");
            System.out.println("Masukkan Pilihan (1/2/3): ");
            pilihan = Perpus.pilihLoginint();
            // User Menu
            switch(pilihan)
            {
                // User Menu
                case 1:
                String inputUsername;
                String inputPassword;
                System.out.println("========================================");
                System.out.println("User Menu = ");
                System.out.println("Username = ");
                inputUsername = Perpus.pilihLogin();
                System.out.println("Password = ");
                inputPassword = Perpus.pilihLogin();
                System.out.println("========================================");
                for (dataUser i : dtUser){
                    con = (i.getUsername().equals(inputUsername) && (i.getPassword().equals(inputPassword)));
                    if(con == true)
                    {
                        user_now = i;
                        break;
                    }
                }
                if(con == false)
                {
                    continue;
                }
                if(con == true)
                {
                    System.out.println("========================================");
                    System.out.println("1.List buku tersedia(Memunculkan buku tersedia dan jumlah buku)");
                    System.out.println("2.List buku dipinjam");
                    System.out.println("3.Loqout(Kembali ke Menu Login.)");
                    System.out.println("========================================");
                    System.out.println("Masukkan Pilihan (1/2/3): ");
                    pilihanUser = input.nextInt();
                    // List buku yang ingin dipinjam
                    if (pilihanUser == 1) {
                        Perpus.tampilkanBuku();
                        // Menu Sewa Buku
                        System.out.println("1. Sewa Buku(Menyewa buku berdasarkan nama buku dan di sesuaikan dengan kuota buku dan user tidak bisa menyewa buku yang sama lebih dari 1)");
                        System.out.println("2. Kembali");
                        System.out.println("Masukkan Pilihan (1/2): ");
                        pilihan_SewaBuku = input.nextInt();
                        if (pilihan_SewaBuku == 1) {
                            String inputnamaBuku;
                            System.out.println("Masukkan nama buku yang ingin di pinjam = ");
                            inputnamaBuku = Perpus.pilihLogin();
                            // cek apakah buku yg disewa ada di perpus dan cek stoknya tidak boleh 0
                            for (dataBuku i : dtBuku) {
                                con = ((i.getnamaBuku().equals(inputnamaBuku)) && (i.getjumlahBuku() >= 1));
                                if (con == true) {
                                    index = dtBuku.indexOf(i); // berguna untuk menghapus stok di perpus nanti
                                    break;
                                }
                            }
                            // if ada buku di perpus
                            if (con){
                                // cek apakah user sudah menyewa buku yang sama atau tidak
                                for (dataBuku i : user_now.bukuDipinjam){
                                    con2 = (i.getnamaBuku() == inputnamaBuku);
                                    if (con2 == true){
                                        break;
                                    }
                                }
                            }
                            if (con2 == false){
                                user_now.bukuDipinjam.add(new dataBuku(inputnamaBuku, 1));
                                dtBuku.get(index).sewaBuku(1);
                                System.out.println(inputnamaBuku + " telah disewa!");
                                continue;
                            } else if (con2) {
                                System.out.println("Gagal menyewa buku!");
                                System.out.println("Buku tersebut telah disewa!");
                                continue;
                            } 
                            else {
                                System.out.println("Gagal menyewa buku!");
                                System.out.println("Stok buku kosong!");
                                continue;
                            }
                        }
                        if (pilihan_SewaBuku == 2) {
                            continue;
                        }
                    }
                    // Data buku yang dipinjam di perpustakaan
                    if (pilihanUser == 2) {
                        int pilihanmenuPinjaman;
                        Perpus.tampilkanBukuDipinjam();
                        System.out.println("1. Kembalikan buku(buku dikembalikan berdasarkan nama dan kuota pada buku tersedia berubah juga)");
                        System.out.println("2. Kembali");
                        System.out.println("Masukkan Pilihan (1/2)");
                        pilihanmenuPinjaman = Perpus.pilihLoginint();
                        if(pilihanmenuPinjaman == 1)
                        {
                            String inputnamaPinjam;
                            System.out.println("Masukkan nama buku yang ingin dikembalikan : ");
                            inputnamaPinjam = Perpus.pilihLogin();
                            Perpus.kembalikanBuku(inputnamaPinjam);
                            continue;
                        }
                        if(pilihanmenuPinjaman == 2)
                        {
                            continue;
                        }
                    }
                    // Kembali
                    if (pilihanUser == 3) {
                        continue;
                    }
                }
                // Admin Menu
                case 2:
                    String LoginUserAdmin;
                    String LoginPassAdmin;
                    System.out.println("========================================");
                    System.out.println("Admin Login");
                    System.out.println("Username = ");
                    LoginUserAdmin = Perpus.pilihLogin();
                    System.out.println("Password = ");
                    LoginPassAdmin = Perpus.pilihLogin();
                    System.out.println("========================================");
                    if(LoginUserAdmin.equals(dtAdm.Username) && LoginPassAdmin.equals(dtAdm.Password) )
                    {
                        System.out.println("========================================");
                        System.out.println("Admin Menu");
                        System.out.println("1. Manage User");
                        System.out.println("2. Manage Buku");
                        System.out.println("3. Loqout");
                        System.out.println("========================================");
                        System.out.println("Masukkan Pilihan(1/2/3) : ");
                        pilihanAdmin = Perpus.pilihLoginint();
                        // Manage User
                        switch (pilihanAdmin) {
                            case 1:
                                System.out.println("========================================");
                                System.out.println("1. Tambah User(menambahkan user dan password baru serta username tidak boleh sama dengan yang sudah ada)");
                                System.out.println("2. Delete User(berdasarkan nama)");
                                System.out.println("3. Kembali");
                                System.out.println("========================================");
                                System.out.println("Masukkan Pilihan(1/2/3) : ");
                                pilihanManageUser = Perpus.pilihLoginint();
                                // Tambah User
                                if (pilihanManageUser == 1) {
                                    System.out.println("Masukkan Username = ");
                                    userinputUsername = Perpus.pilihLogin();
                                    System.out.println("Masukkan Password = ");
                                    userinputPassword = Perpus.pilihLogin();
                                    // fitur tambah user
                                    // cek apakah ada user yang sama atau tidak
                                    for (dataUser i : dtUser) {
                                        con = (i.getUsername().equals(userinputUsername));
                                        if (con == true) {
                                            break;
                                        }
                                    }
                                    // if ada user then gagal menambah user
                                    if (con){
                                        System.out.println("Gagal menambah user baru!");
                                        System.out.println("Username tersebut sudah terpakai!");
                                        
                                    } else {
                                        dtUser.add(new dataUser(userinputUsername, userinputPassword));
                                        System.out.println(userinputUsername + " telah ditambahkan sebagai user baru!");
                                        
                                    }
                                    continue;
                                }
                                // Delete User
                                if (pilihanManageUser == 2) {
                                    // fitur delete user
                                    System.out.println("Masukkan Username = ");
                                    userinputUsername = Perpus.pilihLogin();
                                    // cek apakah ada user yang sama atau tidak
                                    for (dataUser i : dtUser){
                                        con = (i.getUsername().equals(userinputUsername));
                                        if (con == true){
                                            index = dtUser.indexOf(i);
                                            break;
                                        }
                                    }
                                    if (con){
                                        dtUser.remove(index);
                                        System.out.println(userinputUsername + " telah dihapus dari daftar user!");
                                        continue;
                                    } else {
                                        System.out.println("Gagal menghapus user!");
                                        System.out.println("Username tersebut tidak ada dalam daftar user!");
                                        continue;
                                    }
                                }
                                // Kembali
                                if (pilihanManageUser == 3) {
                                    continue;
                                }
                                // Manage Buku
                            case 2:
                                String hapusNamaBuku;
                                System.out.println("========================================");
                                System.out.println("1. Masukkan buku baru(Menambahkan buku baru dan kuota)");
                                System.out.println("2. Delete Buku(Menghapus buku berdasarkan nama)");
                                System.out.println("3. Kembali");
                                System.out.println("Masukkan Pilihan(1/2/3) : ");
                                pilihanManageBuku = input.nextInt();
                                Perpus.tampilkanBuku();
                                // tambah buku
                                if (pilihanManageBuku == 1) {
                                    String inputNamaBuku;
                                    // fitur tambah buku
                                    System.out.println("Masukkan nama buku tersedia: ");
                                    inputNamaBuku = Perpus.pilihLogin();
                                    System.out.println("Masukkan Kuota buku tersedia: ");
                                    jumlahKuotaBuku = Perpus.pilihLoginint();
                                    // check buku ada apa belum
                                    for(dataBuku i:dtBuku)
                                    {
                                        con = i.getnamaBuku().equals(inputNamaBuku);
                                        if(con == true)
                                        {
                                            index = dtBuku.indexOf(i);
                                            dtBuku.get(index).setjumlahBuku(jumlahKuotaBuku);
                                            break;
                                        }
                                    }
                                    if(con == false)
                                    {
                                        dtBuku.add(new dataBuku(inputNamaBuku, jumlahKuotaBuku));
                                    }
                                }
                                // menghapus buku
                                if (pilihanManageBuku == 2) {
                                    
                                    System.out.println("Masukkan nama buku yang ingin dihapus = ");
                                    hapusNamaBuku = Perpus.pilihLogin();
                                    for (dataBuku i : dtBuku){
                                        con = (i.getnamaBuku().equals(hapusNamaBuku));
                                        if (con == true){
                                            index = dtBuku.indexOf(i);
                                            break;
                                        }
                                    }
                                    if (con){
                                        dtBuku.remove(index);
                                        System.out.println(hapusNamaBuku + " telah dihapus dari daftar buku!");
                                        
                                        continue;
                                    } 
                                    else {
                                        System.out.println("Gagal menghapus user!");
                                        System.out.println("Buku tersebut tidak ada dalam daftar buku!");
                                        continue;
                                    }
                                }
                                // kembali
                                if (pilihanManageBuku == 3) {
                                    continue;
                                }
                            case 3:
                                continue;
                    }
                }
                else{
                    continue;
                }
                case 3:
                    return;
                default:
                    System.out.println("\nInput anda tidak ditempukan\nSilahkan pilih [1-3]");
            }
        }
        while(true);
    }
}

class dataBuku
{
    private String namaBuku;
    private int jumlahBuku;
    public dataBuku(String namaBuku, int jumlahBuku)
    {
        this.namaBuku = namaBuku;
        this.jumlahBuku = jumlahBuku;
    }

    public void sewaBuku(int SewaBuku)
    {
        this.jumlahBuku -= SewaBuku;
    }

    public String getnamaBuku()
    {
        return namaBuku;
    }

    public void setnamaBuku(String namaBuku)
    {
        this.namaBuku = namaBuku;
    }

    public int getjumlahBuku()
    {
        return jumlahBuku;
    }

    public void setjumlahBuku(int jumlahBuku)
    {
        this.jumlahBuku = jumlahBuku;
    }

    public void tambahKuota(int jumlahBuku)
    {
        this.jumlahBuku = jumlahBuku;
    }
}

class dataUser
{
    
    public String Username;
    public String Password;
    ArrayList<dataBuku> bukuDipinjam = new ArrayList<dataBuku>();

    public dataUser(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;
    }
    public String getUsername()
    {
        return Username;
    }

    public void setUsername(String Username)
    {
        this.Username = Username;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String Password)
    {
        this.Password = Password;
    }
    
}

class dataAdmin extends dataUser
{
    public dataAdmin(String Username, String Password)
    {
        super(Username, Password);
    }
}