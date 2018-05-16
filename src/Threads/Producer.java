package Threads;

// Producer's run method controls a thread that
import Domain.Ball;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

// stores values from 1 to 4 in sharedLocation.
public class Producer extends Thread {

    private Buffer sharedLocation; // reference to shared object
    private Ball object;
    private Image image;
    private ArrayList<Image> images;
    private int numImage;
    private int x;
    private int y;

    // constructor
    public Producer(Buffer shared) throws FileNotFoundException {
        super("Producer");
        this.sharedLocation = shared;
        this.x = 0;
        this.y = 400;
        this.object = new Ball(0, 430);
        setImages();
    }

    // store values from 1 to 4 in sharedLocation
    @Override
    public void run() {

        for (int count = 1; count <= 8; count++) {
            int speed = 10 * (int) (Math.random() * 11);
            // hace que la representacion grafica del producer avance hacia el "peaje"
            while (this.object.getX() < 400) {
                this.setImage(images.get(0));
                this.setX(this.getX() + 10);
                this.object.setX(this.object.getX() + 10);
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                //acumulates count value
                this.sharedLocation.set(this.object.clone());
            }
             catch (CloneNotSupportedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }

            int temp = this.object.getX();
            this.object.setX(0);
            
            speed = 10 * (int) (Math.random() * 11);
            //hace que la representacion grafica del producer se devuelva hacia la "fabrica"
            while (temp > 0) {
                this.setImage(images.get(1));
                this.setX(this.getX() - 10);
                temp -= 10;
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } // end for

        System.err.println(getName() + " done producing." + "\nTerminating " + getName() + ".");

    } // end method run

    public Ball getObject() {
        return object;
    }

    public void setObject(Ball object) {
        this.object = object;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImages() throws FileNotFoundException {
        this.images = new ArrayList<Image>();
        this.images.add(new Image(new FileInputStream("src/Assets/p1.png")));
        this.images.add(new Image(new FileInputStream("src/Assets/p2.png")));
    }

} // end class Producer

