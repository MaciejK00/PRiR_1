import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class monte_carlo extends Thread{
    double promien;
    double ilePunktow;
     double wynik;

    public monte_carlo(double radius, double numberOfPoints) {
        this.promien = radius;
        this.ilePunktow = numberOfPoints;
    }

    boolean sprawdzPunkt(double x, double y, double Cx, double Cy, double radius)
    {
        return Math.sqrt(Math.pow((x - Cx),2) + Math.pow((y - Cy),2)) <= radius;
    }
    public void run(){
        double squareSide = promien *2;
        double Cx = promien;
        double Cy = promien;
        int punkty = 0;
        double x,y;
        Random random = new Random();
        for (int i = 0; i < ilePunktow; i++) {
            x =  random.nextDouble()*squareSide;
            y =  random.nextDouble()*squareSide;
            if (sprawdzPunkt(x, y, Cx, Cy, promien))
                punkty = punkty + 1;
        }
        wynik = punkty / ilePunktow * Math.pow(squareSide,2);
    }

    public double getWynik() {
        return wynik;
    }
}

public class Zadanie2 {

   public void watki(){
       System.out.println("Podaj promien:");
       Scanner scanner = new Scanner(System.in);
       int r = scanner.nextInt();
       System.out.println("Podaj liczbe punktow:");
       int b = scanner.nextInt();
       System.out.println("Podaj liczbe watkow:");
       int c = scanner.nextInt();
       int res = b / c;
       ArrayList<monte_carlo> lista = new ArrayList<>();
       double wynik = 0;

       for (int i = 0; i < c; i++) {
           lista.add(new monte_carlo(r, res));
           lista.get(i).start();
       }

       while (true) {
           int help = 0;
           for (monte_carlo monte_carlo : lista) {
               if (monte_carlo.isAlive()) {
                   help++;
               }
           }
           if (help == 0) break;
       }

       for (monte_carlo monte_carlo : lista) {
           wynik += monte_carlo.getWynik();
       }
       System.out.println(wynik/c);
   }
    public static void main(String[] args) {
        Zadanie2 zadanie2 = new Zadanie2();
        zadanie2.watki();

    }
}
