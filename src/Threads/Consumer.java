package Threads;

import Domain.Ball;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

// Consumer's run method controls a thread that loops four
// times and reads a value from sharedLocation each time.
public class Consumer extends Thread {

    private SynchronizedBuffer sharedLocation; // reference to shared object
    private Ball object;
    private Image image;
    private ArrayList<Image> images;
    private ArrayList<Ball> balls;
    private int numImage;
    private int x;
    private int y;

    // constructor
    public Consumer(SynchronizedBuffer shared) throws FileNotFoundException {
        super("Consumer");
        sharedLocation = shared;
        this.x = 1100;
        this.y = 400;
        setImages();
        this.balls = new ArrayList<Ball>();
    }

    // read sharedLocation's value four times and sum the values
    @Override
    public void run() {
        int newX = 0;
        //hace que la representacion grafica del consumer avance hacia el "peaje"
        for (int count = 1; count <= 8; count++) {
            int temp = 1100;
            int speed = 10 * (int) (Math.random() * 11);
            while (temp > 700) {
                this.setX(this.getX() - 10);
                this.setImage(images.get(0));
                temp -= 10;
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //lee el objeto del buffer
            this.object = this.sharedLocation.get();

            this.object.setX(700);
            speed = 10 * (int) (Math.random() * 11);
            //hace que la representacion grafica del consumer se devuelva hacia el "destino" del objeto
            while (this.object.getX() < 1100) {
                this.setImage(images.get(1));
                this.object.setX(this.object.getX() + 10);
                this.setX(this.getX() + 10);
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //se agregan los objetos transportados a un arraylist para poder ser pintados al llegar a su "destino"
            this.object.setY(newX);
            try {
                this.setBalls(this.balls, this.object.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
            newX += 40;
            this.object = null;
        }// end for
        System.err.println(getName() + " has finished." + "\nTerminating " + getName() + ".");
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

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void setBalls(ArrayList<Ball> balls, Ball copy) throws CloneNotSupportedException {
        this.balls.add(copy);
    }

    public void setImages() throws FileNotFoundException {
        this.images = new ArrayList<Image>();
        this.images.add(new Image(new FileInputStream("src/Assets/c1.png")));
        this.images.add(new Image(new FileInputStream("src/Assets/c2.png")));
    }

} // end class Consumer

