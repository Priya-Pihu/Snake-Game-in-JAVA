
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Board extends JPanel implements ActionListener{
    private Image head;     // Images that show on the screen
    private Image dot;      // Images that show on the screen
    private Image apple;    // Images that show on the screen

    private final int all_dot = 900; //total dot size
    private final int dot_size = 10;  // one dot size
    private final int random_pos = 29; // To generate random position for apple

    private int apple_x;    // apple random position coordinate
    private int apple_y;    // apple random position coordinate

    private final int x[] = new int[all_dot];   // x and y axis coordinate
    private final int y[] = new int[all_dot];   // x and y axis coordinate

    private boolean leftdirection = false;      //To move in all direction when key pressed
    private boolean rightdirection = true;  //To move in all direction when key pressed
    private boolean updirection = false;    //To move in all direction when key pressed
    private boolean downdirection = false;  //To move in all direction when key pressed

    private boolean inGame = true;
    private int dots;
    private Timer timer;
    public Board(){ 
        initBoard();
    }
    private void initBoard(){
        addKeyListener(new TAdapter());

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500,500));
        setFocusable(true);

        loadImages();
        initGame();
    }
    // to place head dot and apple in the screen as a snake
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head = i3.getImage();
    }
    // To start game
    public void initGame(){
        dots = 3;
        for(int i=0; i<dots; i++){
            y[i]= 50;
            x[i]= 50 - i * dot_size;
        }
        leftdirection=false;
        rightdirection=true;
        updirection=false;
        downdirection=false;
        inGame=true;

        locateApple();
        if(timer!=null){
            timer.stop();
        }
        timer = new Timer(140, this);
        timer.start();
    } 
    // to set apple location in random places
    public void locateApple(){
        int r = (int)(Math.random() * random_pos);
        apple_x = r * dot_size;
        r = (int)(Math.random() * random_pos);
        apple_y = r * dot_size;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    // to draw head and body of the snake
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            for(int i=0; i<dots; i++){
                if(i==0){
                    g.drawImage(head, x[i], y[i], this);
                }else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg = "Game Over!";
        Font font = new Font("Helvetica", Font.BOLD ,20);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (500 - metrices.stringWidth(msg))/2, 500/2);
    }
    // To move snake when key pressed
    public void move(){
        for(int i =dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftdirection == true){
            x[0] -= dot_size;
        }
        if(rightdirection == true){
            x[0] += dot_size;
        }
        if(updirection == true){
            y[0] -= dot_size;
        }
        if(downdirection == true){
            y[0] += dot_size;
        }
    }
    public void checkApple(){
        if((x[0]==apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();
        }
    }
    public void checkCollision(){
        for(int i=dots;i>0;i--){
            if((i>3) && (x[0]==x[i]) && (y[0]==y[i])){
                inGame=false;
            }
        }
        if(y[0]>=500){
            inGame=false;
        }
        if(x[0]>=500){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    // override the abstract class function and move snake
    @Override
    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && (!rightdirection)){
                leftdirection=true;
                updirection=false;
                downdirection=false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftdirection)){
                rightdirection=true;
                updirection=false;
                downdirection=false;
            }
            if(key == KeyEvent.VK_UP && (!downdirection)){
                updirection=true;
                leftdirection=false;
               rightdirection=false;
            }
            if(key == KeyEvent.VK_DOWN && (!updirection)){
                downdirection=true;
                leftdirection=false;
                rightdirection=false;
                
            }
        }
    }
}
