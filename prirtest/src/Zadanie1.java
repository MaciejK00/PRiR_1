import java.util.LinkedList;
import java.util.Random;

class DworzecAutobusowy {
    static int Dworzec =1;
    static int START=2;
    static int KONIEC_TRASY =4;
    int ilosc_peronow;
    int ilosc_zajetych;
    int ilosc_autobusow;
    int numer_help;
    DworzecAutobusowy(int ilosc_peronow, int ilosc_autobusow){
        this.ilosc_peronow =ilosc_peronow;
        this.ilosc_autobusow =ilosc_autobusow;
        this.ilosc_zajetych=0;
    }
    synchronized int start(int numer){
        ilosc_zajetych--;
        numer_help=numer;
        System.out.println("Pozwolenie na odjazd autobusowi "+numer);
        return START;
    }
    synchronized int przyjedz(){
        try{
            Thread.currentThread().sleep(1000);//sleep for 1000 ms
        }
        catch(Exception ie){
        }
        if(ilosc_zajetych< ilosc_peronow){
            ilosc_zajetych++;
            System.out.println("Pozwolenie na przyjazd na peronie "+ilosc_zajetych);
            return Dworzec;
        }
        else
        {return KONIEC_TRASY;}
    }
    synchronized void zmniejsz(){
        Zadanie1.autobusy.get(numer_help).stop();
        Zadanie1.autobusy.remove(numer_help);
        System.out.println("KONIEC PALIWA");
        if(ilosc_autobusow == ilosc_peronow) System.out.println("ILOSC AUTOBUSOW TAKA SAMA JAK ILOSC PERONOW ______________");
    }
}


class Autobus extends Thread {
    static int Dworzec = 1;
    static int START = 2;
    static int TRASA = 3;
    static int KONIEC_TRASY = 4;
    static int KONIEC_PALIWA = 5;
    static int TANKUJ = 1000;
    static int REZERWA = 500;
    String[] stacja_paliw = new String[]{"ORLEN","SHELL","BP","PRONAR","LOTOS","CIRCLEK","AUCHAN","MOYA"};
    String[] lokalizacje = new String[]{"LAS VEGAS","CHICAGO","NOWY YORK","WARSZAWA","LOS ANGELES","KRAKOW","WROCLAW","SUWALKI"};

    int numer;
    int paliwo;
    int stan;
    DworzecAutobusowy l;
    Random rand;

    public Autobus(int numer, int paliwo, DworzecAutobusowy l) {
        this.numer = numer;
        this.paliwo = paliwo;
        this.stan = TRASA;
        this.l = l;
        rand = new Random();
    }

    public void run() {
        while (true) {
            if (stan == Dworzec) {
                if (rand.nextInt(2) == 1) {
                    stan = START;
                    paliwo = TANKUJ;
                    System.out.println("prosze o pozwolenie na odjazd - Autobus:  " + numer);
                    stan = l.start(numer);
                } else {
                    System.out.println("Postoje sobie jeszcze troche");
                }
            } else if (stan == START) {
                int random = rand.nextInt(lokalizacje.length);
                System.out.println("Odjazd do "+lokalizacje[random]+" -  Autobusu:  " + numer);
                stan = TRASA;
            } else if (stan == TRASA) {
                paliwo -= rand.nextInt(500);
                if (paliwo <= REZERWA) {
                    stan = KONIEC_TRASY;
                } else try {
                    sleep(rand.nextInt(1000));
                } catch (Exception e) {
                }
            } else if (stan == KONIEC_TRASY) {
                System.out.println("Prosze o pozowolenie na przyjazd: " + numer + " ilosc paliwa " + paliwo);
                stan = l.przyjedz();
                if (stan == KONIEC_TRASY) {
                    paliwo -= rand.nextInt(500);
                    if (paliwo<0) paliwo =0;
                    System.out.println("REZERWA " + paliwo);

                    if (paliwo <= 0) {
                        stan = KONIEC_PALIWA;
                        int random = rand.nextInt(stacja_paliw.length);
                        System.out.println("KIEROWCA IDZIE PO PALIWO NA STACJE: "+stacja_paliw[random]);
                    }
                }
            } else if (stan == KONIEC_PALIWA) {
                System.out.println("KONIEC PALIWA -  AUTOBUS:  " + numer);
                l.zmniejsz();
            }
        }
    }
}

public class Zadanie1 {
    static int ilosc_autobusow =100;
    static int ilosc_peronow =5;
    static DworzecAutobusowy dworzec;
    static LinkedList<Autobus> autobusy = new LinkedList<>();
    public static void main(String[] args) {
        dworzec =new DworzecAutobusowy(ilosc_peronow, ilosc_autobusow);
        for(int i = 0; i< ilosc_autobusow; i++){
            autobusy.add(new Autobus(i,2000, dworzec));
            autobusy.get(i).start();
        }

    }
}
