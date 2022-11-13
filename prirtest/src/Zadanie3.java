import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

class negatyw extends Thread{
    int start1,  start2,  koniec1, koniec2;
    BufferedImage image;
    public negatyw(int start1,  int start2,  int koniec1, int koniec2, BufferedImage image){
        this.start1= start1;
        this.start2= start2;
        this.koniec1= koniec1;
        this.koniec2= koniec2;
        this.image = image;
    }

    public void run(){
        for(int i=start1; i<koniec1; i++){
            for(int j=start2; j<koniec2; j++){
                Color c = new Color(image.getRGB(j, i));
                int red = (int)(c.getRed());
                int green = (int)(c.getGreen());
                int blue = (int)(c.getBlue());
                int final_red, final_green, final_blue;
                final_red = 255-red;
                final_green = 255-green;
                final_blue = 255-blue;
                Color newColor = new Color(final_red, final_green, final_blue);
                image.setRGB(j,i,newColor.getRGB());
            }
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}

public class Zadanie3 {
    BufferedImage image;
    int width;
    int height;

    public void licz(){
        File input = new File("ratusz.jpg");
        try {
            image = ImageIO.read(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        width = image.getWidth();
        height = image.getHeight();

        int widthW = width/2;
        int heightH = height/2;

        int firstStart = 1;
        int secondStart =1;

        int firstEnd = heightH;
        int secondEnd = widthW;

        int firstStart2 = 1 ;
        int secondStart2 = secondEnd ;

        int firstEnd2 = firstStart2+heightH-1;
        int secondEnd2 = secondStart2+widthW-1;

        int firstStart3 = firstEnd ;
        int secondStart3 = 1 +1;

        int firstEnd3 = heightH*2;
        int secondEnd3 = widthW;

        int firstStart4 = heightH;
        int secondStart4 = widthW;

        int firstEnd4 = height-1;
        int secondEnd4 = width-1;

        negatyw negatyw = new negatyw(firstStart,secondStart,firstEnd ,secondEnd ,image);
        negatyw.start();

        negatyw negatyw2= new negatyw(firstStart2,secondStart2,firstEnd2 ,secondEnd2 ,image);
        negatyw2.start();


        negatyw negatyw3= new negatyw(firstStart3,secondStart3,firstEnd3 ,secondEnd3 ,image);
        negatyw3.start();

        negatyw negatyw4= new negatyw(firstStart4,secondStart4,firstEnd4 ,secondEnd4 ,image);
        negatyw4.start();


        File ouptut = new File("wynik.jpg");
        try {
            ImageIO.write(negatyw.getImage(), "jpg", ouptut);
            ImageIO.write(negatyw2.getImage(), "jpg", ouptut);
            ImageIO.write(negatyw3.getImage(), "jpg", ouptut);
            ImageIO.write(negatyw4.getImage(), "jpg", ouptut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args){
        Zadanie3 zadanie3 = new Zadanie3();
        zadanie3.licz();
    }
}
