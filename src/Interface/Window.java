/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Domain.Ball;
import Threads.Consumer;
import Threads.Producer;
import Threads.SynchronizedBuffer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Steven
 */
public class Window extends Application implements Runnable {

    private Thread thread;
    private Scene scene;
    private Pane pane;
    private Canvas canvas;
    private Image image;

    SynchronizedBuffer sharedLocation;

    Producer producer;
    Consumer consumer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Synchronized Threads");
        initComponents(primaryStage);
        primaryStage.setOnCloseRequest(exit);
        primaryStage.show();
    }

    private void initComponents(Stage primaryStage) throws FileNotFoundException {
        try {
            this.pane = new Pane();
            this.scene = new Scene(this.pane, 1280, 720);
            this.canvas = new Canvas(1280, 720);
            this.pane.getChildren().add(this.canvas);
            this.image = new Image(new FileInputStream("src/Assets/icon.png"));
            primaryStage.setScene(this.scene);

            sharedLocation = new SynchronizedBuffer();
            
            //Inicializamos cada hilo y lo iniciamos
            producer = new Producer(sharedLocation);
            consumer = new Consumer(sharedLocation);

            producer.start();
            consumer.start();

            this.thread = new Thread(this);
            this.thread.start();
        } catch (BufferOverflowException ex) {
        }
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 1280, 720);
        gc.drawImage(this.image, 570, 350);
        gc.drawImage(this.producer.getImage(), this.producer.getX(), this.producer.getY());
        gc.drawImage(this.producer.getObject().getImage(), this.producer.getObject().getX(), this.producer.getObject().getY());
        gc.drawImage(this.consumer.getImage(), this.consumer.getX(), this.consumer.getY());
        if (this.consumer.getObject() != null) {
            gc.drawImage(this.consumer.getObject().getImage(), this.consumer.getObject().getX(), this.consumer.getObject().getY());
        }
        //se dibuja el objeto sobre el "peaje"
        if (this.sharedLocation.getBuffer() != null) {
            gc.drawImage(this.sharedLocation.getBuffer().getImage(), 600, 350);
        }
        
        //se pintan los objetos que el consumer transporta
        ArrayList<Ball> balls = new ArrayList<Ball>();
        balls = this.consumer.getBalls();
        if (balls.size() >= 1) {
            for (int i = 0; i < balls.size(); i++) {
                if (balls != null) {
                    gc.drawImage(balls.get(i).getImage(), balls.get(i).getX(), balls.get(i).getY());
                }
            }
        }
    }

    @Override
    public void run() {
        long start;
        long elapsed;
        long wait;
        int fps = 30;
        long time = 1000 / fps;

        while (true) {
            try {
                start = System.nanoTime();
                elapsed = System.nanoTime() - start;
                wait = time - elapsed / 1000000;
                Thread.sleep(wait);
                GraphicsContext gc = this.canvas.getGraphicsContext2D();
                draw(gc);
            } catch (InterruptedException ex) {
            }
        }
    }

    EventHandler<WindowEvent> exit = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            System.exit(0);
        }
    };
}
