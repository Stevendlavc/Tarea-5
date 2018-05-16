/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

/**
 *
 * @author Steven
 */
public class Ball implements Cloneable{
    private int x;
    private int y;
    private Image image;
    
    public Ball(int x, int y) throws FileNotFoundException{
        this.x = x;
        this.y = y;
        this.setImage(new Image(new FileInputStream("src/Assets/ball.png")));
    }
    
    public Ball() throws FileNotFoundException{
        this.setImage(new Image(new FileInputStream("src/Assets/ball.png")));
    }
    
    public Ball clone() throws CloneNotSupportedException{
        return (Ball)super.clone();
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
}
