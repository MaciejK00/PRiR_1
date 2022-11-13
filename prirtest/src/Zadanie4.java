import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



class fraktal extends Thread{
    public static BufferedImage image;
    int w;
    int h;
    int s;
    int s2;

    fraktal(int s, int s2, int w, int h){
        this.s=s;
        this.s2=s2;
        this.w=w;
        this.h=h;
    }

    @Override
    public void run()
    {

        image = new BufferedImage(800,600, BufferedImage.TYPE_INT_RGB);

        for (int x = s; x < w; x++) {
            for (int y = s2; y < h; y++) {
                double zx = 1.5 * (x - w / 2.0) / (0.5 * JuliaSet.ZOOM * w) + JuliaSet.MOVE_X;
                double zy = ((y - (h / 2.0)) / (0.5 * JuliaSet.ZOOM * h)) + JuliaSet.MOVE_Y;
                float i = JuliaSet.MAX_ITERATIONS;
                while (zx * zx + zy * zy < 4 && i > 0) {
                    double tmp = zx * zx - zy * zy + JuliaSet.CX;
                    zy = 2.0 * zx * zy + JuliaSet.CY;
                    zx = tmp;
                    i--;
                }
                int c = Color.HSBtoRGB((JuliaSet.MAX_ITERATIONS / i) % 1, 1, i > 0 ? 1 : 0);
                image.setRGB(x, y, c);
                setImage(image);
            }
        }
    }

    public void setImage(BufferedImage image) {
        fraktal.image = image;
    }

    public static BufferedImage getImage() {
        return image;
    }
}


class JuliaSet extends JPanel {
    public static final int MAX_ITERATIONS = 300;
    public static final double ZOOM = -5;
    public static final double CX = -0.7;
    public static final double CY = 0.27015;
    public static final double MOVE_X = 0;
    public static final double MOVE_Y = 0;

    public JuliaSet() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.white);
    }

    void drawJuliaSet(Graphics2D g) {
        BufferedImage image = fraktal.getImage();
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawJuliaSet(g);
    }

    public static void main(String[] args) throws InterruptedException {

        fraktal fr1 = new fraktal(0,0,400,300);
        fr1.start();


        fraktal fr2 = new fraktal(400,0,800,300);
        fr2.start();


        fraktal fr3 = new fraktal(0,300,400,600);
        fr3.start();

        fraktal fr4 = new fraktal(400,300,800,600);
        fr4.start();

        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Fraktal Juli");
            f.setResizable(false);
            f.add(new JuliaSet(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}



public class Zadanie4 {
}
