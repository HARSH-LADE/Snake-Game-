import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private int[] snakexlength=new int[750];
    private int[]snakeylength=new int[750];

    private int[]xPos={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[]yPos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    private Random random=new Random();
    private int enemyX,enemyY;
    private int lengthOfSnake=2;
    private int moves = 0;
    private int score=0;


    boolean right=true;
    boolean left= false;
    boolean up=false;
    boolean down=false;
    private boolean gameOver=false;


    private  ImageIcon snaketitle2 = new ImageIcon(getClass().getResource("snaketitle2.jpg"));
    private  ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png.jpg"));
    private  ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png.jpg"));
    private  ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png.jpg"));
    private  ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png.jpg"));
    private  ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png.jpg"));
    private ImageIcon enemy=new ImageIcon(getClass().getResource("enemy.png.jpg"));


    
    private Timer timer;
    private int delay=100;

     GamePanel(){
         addKeyListener(this);
         setFocusable(true);
         setFocusTraversalKeysEnabled(true);
         if(lengthOfSnake>=5){
             delay=1;
         }
         
         timer=new Timer(delay, this);
         timer.start();

         newEnemy();

     }

    private void newEnemy() {
         enemyX=xPos[random.nextInt(34)];
        enemyY=yPos[random.nextInt(23)];

        for(int i=lengthOfSnake-1;i>=0;--i){
            if(snakexlength[i]==enemyX &&snakeylength[i]==enemyY){
                newEnemy();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,852,55);
        g.drawRect(24,74,851,576);

        snaketitle2.paintIcon(this,g,25,11);

       if(moves==0){
            snakexlength[0]=100;
            snakexlength[1]=75;
            snakexlength[2]=50;

            snakeylength[0]=100;
            snakeylength[1]=100;
            snakeylength[2]=100;

        }
        if(left){
            leftmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
            moves++;
        }
        if(right){
            rightmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
            moves++;
        }
        if(up){
           upmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
            moves++;
        }
        if(down){
            downmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]);
            moves++;
        }
        for(int i =1;i<lengthOfSnake;++i){
            snakeimage.paintIcon(this,g,snakexlength[i],snakeylength[i]);
            moves++;
        }
        enemy.paintIcon(this,g,enemyX,enemyY);
        if(gameOver){
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("#Game Over!!",300,300);

            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Press SPACE to Restart",320,350);

        }
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,14));
        g.drawString("Score :"+score,750,30);
        g.drawString("Length :"+lengthOfSnake,750,50);

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

         for(int i = lengthOfSnake-1;i>0;--i){
             snakexlength[i]=snakexlength[i-1];
             snakeylength[i]=snakeylength[i-1];


         }

         if(left){
             snakexlength[0]=snakexlength[0]-25;
         }
        if(right){
            snakexlength[0]=snakexlength[0]+25;
        } if(up){
            snakeylength[0]=snakeylength[0]-25;
        }
        if(down){
            snakeylength[0]=snakeylength[0]+25;
        }

        if(snakexlength[0]>850)snakexlength[0]=25;
        if(snakexlength[0]<25)snakexlength[0]=850;
        if(snakeylength[0]>625)snakeylength[0]=75;
        if(snakeylength[0]<75)snakeylength[0]=625;

        collideWithBody();
        collideWithEnemy();
        repaint();


    }

    private void collideWithBody() {
         for(int i=lengthOfSnake-1;i>0;--i){
             if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]){
                 timer.stop();
                 gameOver=true;
             }
         }

    }

    private void collideWithEnemy() {
         if(snakexlength[0]==enemyX && snakeylength[0]==enemyY){

             newEnemy();
             lengthOfSnake++;
             score++;
         }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


         if(e.getKeyCode()==KeyEvent.VK_SPACE){
             restart();

         }

        if(e.getKeyCode()== KeyEvent.VK_LEFT && (!right)){
            left = true;
            up = false;
            down=false;
        }
        if(e.getKeyCode()== KeyEvent.VK_RIGHT && (!left)){
            right = true;
            up = false;
            down=false;
        }
        if(e.getKeyCode()== KeyEvent.VK_UP && (!down)){
            left = false;
            right = false;
            up = true;
        }
        if(e.getKeyCode()== KeyEvent.VK_DOWN  && (!up) ){
            left = false;
            right = false;
            down=true;
        }
    }

    private void restart() {
         gameOver=false;
         moves=0;
         score=0;
         lengthOfSnake=3;
         left=false;
         right=true;
         up=false;
         down=false;
         timer.start();
         repaint();


    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
