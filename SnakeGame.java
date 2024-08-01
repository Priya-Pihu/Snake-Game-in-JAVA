
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
public final class SnakeGame extends JFrame{
    private Board board;
    private JButton restart;
    public SnakeGame(){
        initUI();
    }
    private void initUI(){
        board = new Board();
        add(board);
        restart = new JButton("Restart");
        restart.setFocusable(false);
        restart.addActionListener((ActionEvent e) -> {
            restartGame();
        });
        add(restart, BorderLayout.SOUTH);
        setResizable(false);
        pack();
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void restartGame(){
        board.initGame();
        board.requestFocus();
    }
    public static void main(String args[]){
        new SnakeGame().setVisible(true); // to display the palate
        EventQueue.invokeLater(()->{
            JFrame ex = new SnakeGame();
            ex.setVisible(true);
        });
    }
}