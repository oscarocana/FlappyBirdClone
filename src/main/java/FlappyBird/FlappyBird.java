package FlappyBird;
//Import required packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener  {
    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 800;
    public Renderer renderer;
    public int ticks, yMotion;
    public Rectangle bird;
    public boolean gameOver, started = true;
    public Random random;
    public ArrayList<Rectangle> columns;

    public FlappyBird(){
        renderer = new Renderer();
        Timer timer = new Timer(20, this);
        JFrame jframe = new JFrame();
        random = new Random();

        jframe.setTitle("Flappy Bird Clone");
        jframe.add(renderer);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bird = new Rectangle(WIDTH/2-10, HEIGHT/2-10, 20,20);
        columns = new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();

    }
    public static void main(String[] args){
    //Creates a new instance of FlappyBird called flappyBird
    flappyBird= new FlappyBird();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int speed = 10;
        ticks++;

        if (started) {
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                column.x -= speed;
            }
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion = +2;
            }
            bird.y += yMotion;
            //detects if the bird collides with the column
            for (Rectangle column : columns) {
                if (column.intersects(bird)) {
                    gameOver = true;
                }
            }
            //detects if the bird collides with the ground
            if (bird.y > HEIGHT - 120 || bird.y < 0) {
                gameOver = true;
            }
        }
        renderer.repaint();

        for (int i = 0 ; i < columns.size(); i++){
            Rectangle column = columns.get(i);
            if (column.x + column.width < 0){
                columns.remove(column);

                if(column.y == 0) {
                    addColumn(false);
                }
            }
        }
    }
    public void addColumn(boolean start){
        int space = 300;
        int width = 100;
        int height = 50 + random.nextInt(300);
    if (start){
        columns.add(new Rectangle(WIDTH + width +columns.size()*300, HEIGHT - height - 120, width,height));
        columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width,HEIGHT - height - space));
    }
    else {
        columns.add(new Rectangle(columns.get(columns.size()-1).x +600, HEIGHT-height-120, width, height));
        columns.add(new Rectangle(columns.get(columns.size()-1).x, 0, width,HEIGHT - height - space));
    }
    }

    public void paintColumn(Graphics g, Rectangle column){
    g.setColor(Color.green.darker());
    g.fillRect(column.x, column.y, column.width, column.height);

    }

    public void repaint(Graphics g) {
        //sets the colour and position of the sky
        g.setColor(Color.CYAN);
        g.fillRect(0,0,WIDTH,HEIGHT);

        //Sets the colour and position of the bird
        g.setColor(Color.yellow);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        //Sets the colour of the ground using RGB values, also positions the ground
        g.setColor(new Color(102,51,0));
        g.fillRect(0,HEIGHT-150,WIDTH,150);

        g.setColor(Color.green);
        g.fillRect(0,HEIGHT-150,WIDTH,20);

        for (Rectangle column : columns){
            paintColumn(g,column);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial",1,100));

        if(gameOver){
            g.drawString("Game Over!",75,HEIGHT/2 - 50);
        }

    }
}
