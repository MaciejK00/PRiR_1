import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore ;

class Filozof3 extends Thread {
    static  int MAX3 = 1000000;
    static Semaphore[] widelec = new Semaphore[MAX3];
    int mojNum;
    Random losuj;

    public Filozof3(int nr) {
        mojNum = nr;
        losuj = new Random(mojNum);
    }

    public Filozof3() {
    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            int strona = losuj.nextInt(2);
            boolean podnioslDwaWidelce = false;
            do {
                if (strona == 0) {
                    widelec[mojNum].acquireUninterruptibly();
                    if (!(widelec[(mojNum + 1) % MAX3].tryAcquire())) {
                        widelec[mojNum].release();
                    } else {
                        podnioslDwaWidelce = true;
                    }
                } else {
                    widelec[(mojNum + 1) % MAX3].acquireUninterruptibly();
                    if (!(widelec[mojNum].tryAcquire())) {
                        widelec[(mojNum + 1) % MAX3].release();
                    } else {
                        podnioslDwaWidelce = true;
                    }
                }
            } while (podnioslDwaWidelce == false);
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (3000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            widelec[mojNum].release();
            widelec[(mojNum + 1) % MAX3].release();
        }
    }

    public void licz(){

        for ( int i =0; i<MAX3; i++) {
            widelec [ i ]=new Semaphore ( 1 ) ;
        }
        for ( int i =0; i<MAX3; i++) {
            new Filozof3(i).start();
        }
    }
}

class Filozof2 extends Thread {
    static  int MAX2 = 1000000;
    static Semaphore[] widelec = new Semaphore[MAX2];

    int mojNum;

    public Filozof2(int nr) {
        mojNum = nr;
    }
    public Filozof2() {

    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            if (mojNum == 0) {
                widelec[(mojNum + 1) % MAX2].acquireUninterruptibly();
                widelec[mojNum].acquireUninterruptibly();
            } else {
                widelec[mojNum].acquireUninterruptibly();
                Filozof2.widelec[(mojNum + 1) % MAX2].acquireUninterruptibly();
            }
// jedzenie
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (3000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            widelec[mojNum].release();
            widelec[(mojNum + 1) % MAX2].release();
        }
    }

    public void licz(){

        for ( int i =0; i<MAX2; i++) {
            widelec [ i ]=new Semaphore ( 1 ) ;
        }
        for ( int i =0; i<MAX2; i++) {
            new Filozof2(i).start();
        }
    }


}



class Filozof extends Thread {
    static int MAX1 = 1000000;
    static Semaphore[] widelec = new Semaphore[MAX1];
    int mojNum;

    public Filozof(int nr) {
        mojNum = nr;
    }

    public Filozof() {

    }

    public void run() {
        while (true) {
// myslenie
            System.out.println("Mysle ¦ " + mojNum);
            try {
                Thread.sleep((long) (7000 * Math.random()));
            } catch (InterruptedException e) {
            }
            widelec[mojNum].acquireUninterruptibly(); //przechwycenie L widelca
            widelec[(mojNum + 1) % MAX1].acquireUninterruptibly(); //przechwycenie P widelca
// jedzenie
            System.out.println("Zaczyna jesc " + mojNum);
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + mojNum);
            widelec[mojNum].release(); //zwolnienie L widelca
            widelec[(mojNum + 1) % MAX1].release(); //zwolnienie P widelca
        }
    }
    public void licz(){
        for (int i = 0; i< MAX1; i++) {
            widelec [ i ]=new Semaphore ( 1 ) ;
        }
        for (int i = 0; i< MAX1; i++) {
            new Filozof(i).start();
        }
    }
}


public class Zadanie5{
    public void wybierz(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz program(od 1 do 3): ");
        while(true) {
            int w = scanner.nextInt();
            System.out.println("Podaj liczbe filozofow( od 2 do 100 )");
            int l = scanner.nextInt();
            Filozof.MAX1 = l;
            Filozof2.MAX2 = l;
            Filozof3.MAX3= l;
            if(l >=2 && l <=100) {
                switch (w) {
                    case 1:
                        Filozof filozof = new Filozof();

                        filozof.licz();
                        break;
                    case 2:
                        Filozof2 filozof2 = new Filozof2();
                        filozof2.licz();
                        break;
                    case 3:
                        Filozof3 filozof3 = new Filozof3();
                        filozof3.licz();
                        break;
                    default:
                        System.out.println("Zła opcja ");
                        System.out.println("Wybierz program(od 1 do 3): ");
                }
            }
            else {
                System.out.println("Zła liczba filozofow");
                System.out.println("Wybierz program(od 1 do 3): ");
            }
        }
    }

    public static void main(String[] args) {
        Zadanie5 zadanie5 = new Zadanie5();
        zadanie5.wybierz();

    }
}