package flappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rafal on 02017-05-25.
 */
public class FlappyBird implements ActionListener, MouseListener, KeyListener {

    public static FlappyBird flappyBird;

    public final int WIDTH = 1200, HEIGHT = 800;

    public Renderer renderer;

    public Rectangle bird;

    public int ticks, yMotion, score;

    public ArrayList<Rectangle> columns;

    public Random random;

    public boolean gameOver, started;

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public FlappyBird() {
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20, this); //action listener

        renderer = new Renderer();
        random = new Random();

        jFrame.add(renderer);
        jFrame.setTitle("Flappy Bird");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(WIDTH, HEIGHT); // ctrl+space to capitalize
        jFrame.addMouseListener(this);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        columns = new ArrayList<>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();

    }

    public void jump() {
        if (gameOver) {
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            columns.clear();
            yMotion = 0;
            score = 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            gameOver = false;
        }
        if (!started) {
            started = true;
        } else if (!gameOver) {
            if (yMotion > 0) {
                yMotion = 0;
            }
            yMotion -= 10;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int columnsSpeed = 10;

        ticks++;

        if (started) {
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                column.x -= columnsSpeed;
            }
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                if (column.x + column.width < 0) {//jezeli za ekranem
                    columns.remove(column);
                    if (column.y == 0) {// if top column add another one
                        addColumn(false);
                    }
                    addColumn(false);
                }
            }
        }


        bird.y += yMotion;

        for (Rectangle column : columns
                ) {
            if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10 &&
                    bird.x + bird.width / 2 < column.x + column.width / 2 + 10) {
                score++;
            }
            if (column.intersects(bird)) {
                gameOver = true;
                bird.x = column.x - bird.width;
            }
        }
        if (bird.y > HEIGHT - 120 || bird.y < 0) {

            gameOver = true;
        }

        if (bird.y + yMotion >= HEIGHT - 120) {//fall naturally when killed
            bird.y = HEIGHT - 120 - bird.height;
        }

        renderer.repaint();

    }

    public void addColumn(boolean isStartingColumn) {
        int spaceBetweenColumns = 300;
        int width = 100;
        int height = 50 + random.nextInt(300);

        if (isStartingColumn) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300,
                    HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300,
                    0, width, HEIGHT - height - spaceBetweenColumns));
        } else { //appending columns to the starting one
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600,
                    HEIGHT - height - 120, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x,
                    0, width, HEIGHT - height - spaceBetweenColumns));
        }


    }

    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void repaint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 120, WIDTH, 120);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 120, WIDTH, 20);

        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for (Rectangle column : columns
                ) {
            paintColumn(g, column);
        }
        g.setColor(Color.white);
        g.setFont(new Font(("Arial"), 1, 100));
        if (!started) {
            g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
        }
        if (gameOver) {
            g.drawString("Game Over", 100, HEIGHT / 2 - 50);
        }
        if (!gameOver && started) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }

    }

    public static void main(String[] args) {
        flappyBird = new FlappyBird();

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
