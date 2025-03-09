import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainFrame extends JFrame {
    private static MainFrame instance;
    private int countMinesAtMap = 10;
    public int contMinesForPlayer = 10;
    public int contMinesForGame = 10;
    private MyButton[][] map = new MyButton[10][10];

    private MainFrame(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        /*ImageIcon image = new ImageIcon("src/logo.png");*/
        //this.setIconImage(image.getImage());
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setLayout(null);
        createMap();
        //JLabel minesCounter = new JLabel(Integer.toString(contMinesForPlayer));
        //minesCounter.setBounds(125, 525, 100, 50);
        //this.add(minesCounter);
        this.setVisible(true);
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame("saper");
        }
        return instance;
    }

    private void createMap(){
        // create map
        int x = 100;
        int y = 100;
        for (int i = 0; i<10; ++i){
            for (int j=0; j<10; ++j){
                JButton sq = new JButton();
                MyButton fullSq = new MyButton(sq, i, j);
                sq.setBackground(Color.gray);
                sq.addActionListener(new sqListener(fullSq));
                sq.setBounds(x,y,40,40);
                this.add(sq);
                map[i][j] = fullSq;
                x+=40;
            }
            y+=40;
            x=100;
        }

        // create mines
        Random rand = new Random();
        for (int i = 0; i<countMinesAtMap; ++i){
            int xM = rand.nextInt(10);
            int yM = rand.nextInt(10);
            if (!map[xM][yM].isMine){
                map[xM][yM].isMine = true;
            } else{
                --i;
            }
        }

        //add numbers
        for (int i = 0; i<10; ++i) {
            for (int j = 0; j < 10; ++j) {
                int countMines = checkSq(map,i,j);
                map[i][j].number = countMines;
            }
        }
    }

    private int checkSq(MyButton[][] map, int i, int j){
        int res = 0;
        if(map[i][j].isMine) return -1;
        if (i==0 && j==0){
            if(map[i+1][j].isMine) ++res;
            if(map[i+1][j+1].isMine) ++res;
            if(map[i][j+1].isMine) ++res;
        } else if (i==9 && j==9){
            if(map[i-1][j].isMine) ++res;
            if(map[i-1][j-1].isMine) ++res;
            if(map[i][j-1].isMine) ++res;
        } else if (i==0 && j!=9){
            if(map[i+1][j].isMine) ++res;
            if(map[i+1][j+1].isMine) ++res;
            if(map[i][j+1].isMine) ++res;
            if(map[i][j-1].isMine) ++res;
            if(map[i+1][j-1].isMine) ++res;
        } else if (i==0 && j==9){
            if(map[i+1][j].isMine) ++res;
            if(map[i][j-1].isMine) ++res;
            if(map[i+1][j-1].isMine) ++res;
        } else if (j==0 && i!=9){
            if(map[i+1][j].isMine) ++res;
            if(map[i+1][j+1].isMine) ++res;
            if(map[i][j+1].isMine) ++res;
            if(map[i-1][j+1].isMine) ++res;
            if(map[i-1][j].isMine) ++res;
        } else if (j==0 && i==9){
            if(map[i][j+1].isMine) ++res;
            if(map[i-1][j+1].isMine) ++res;
            if(map[i-1][j].isMine) ++res;
        } else if (i==9 && j!=0) {
            if(map[i-1][j].isMine) ++res;
            if(map[i-1][j-1].isMine) ++res;
            if(map[i][j-1].isMine) ++res;
            if(map[i][j+1].isMine) ++res;
            if(map[i-1][j+1].isMine) ++res;
        } else if (j==9 && i!=0){
            if(map[i-1][j].isMine) ++res;
            if(map[i-1][j-1].isMine) ++res;
            if(map[i][j-1].isMine) ++res;
            if(map[i+1][j-1].isMine) ++res;
            if(map[i+1][j].isMine) ++res;
        } else {
            if(map[i-1][j].isMine) ++res;
            if(map[i-1][j-1].isMine) ++res;
            if(map[i][j-1].isMine) ++res;
            if(map[i+1][j-1].isMine) ++res;
            if(map[i+1][j].isMine) ++res;
            if(map[i][j+1].isMine) ++res;
            if(map[i+1][j+1].isMine) ++res;
            if(map[i-1][j+1].isMine) ++res;
        }
        return res;
    }

    public void openSqs(int i, int j){
        if (!map[i][j].isOpen){
            map[i][j].isOpen = true;
            if (map[i][j].number==0) {
                map[i][j].button.setBackground(Color.LIGHT_GRAY);
                if (i==0 && j==0){
                    openSqs(i + 1, j);
                    openSqs(i, j + 1);
                } else if (i==9 && j==9){
                    openSqs(i, j - 1);
                    openSqs(i - 1, j);
                } else if (i==0 && j!=9){
                    openSqs(i + 1, j);
                    openSqs(i, j + 1);
                    openSqs(i, j - 1);
                } else if (i==0 && j==9){
                    openSqs(i + 1, j);
                    openSqs(i, j - 1);
                } else if (j==0 && i!=9){
                    openSqs(i + 1, j);
                    openSqs(i, j + 1);
                    openSqs(i - 1, j);
                } else if (j==0 && i==9){
                    openSqs(i, j + 1);
                    openSqs(i - 1, j);
                } else if (i==9 && j!=0) {
                    openSqs(i, j + 1);
                    openSqs(i, j - 1);
                    openSqs(i - 1, j);
                } else if (j==9 && i!=0){
                    openSqs(i + 1, j);
                    openSqs(i, j - 1);
                    openSqs(i - 1, j);
                } else{
                    openSqs(i + 1, j);
                    openSqs(i, j + 1);
                    openSqs(i, j - 1);
                    openSqs(i - 1, j);
                }
            } else {
                map[i][j].button.setBackground(Color.LIGHT_GRAY);
                map[i][j].button.add(new JLabel(Integer.toString(map[i][j].number)));
            }
        }
    }

    class sqListener implements ActionListener {
        private int isPressed = 0;
        private MyButton rodnenkaya;
        sqListener(MyButton sq){rodnenkaya = sq;}
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isPressed == 0 && !rodnenkaya.isOpen){
                Image img = null;
                try {
                    img = ImageIO.read(getClass().getResource("1529256.png"));
                    rodnenkaya.button.setIcon(new ImageIcon(img));
                    if (rodnenkaya.isMine){
                        --MainFrame.getInstance().contMinesForGame;
                        if (contMinesForGame==0){
                            JOptionPane.showMessageDialog(null, "ура победа))");
                        }
                    }
                    //MainFrame.getInstance().contMinesForPlayer--;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                MainFrame.getInstance().getContentPane().setVisible(false);
                MainFrame.getInstance().getContentPane().setVisible(true);
                isPressed = 1;
            } else if (isPressed == 1){
                if (!rodnenkaya.isMine) {
                    rodnenkaya.button.setBackground(Color.LIGHT_GRAY);
                    rodnenkaya.button.setIcon(null);
                    if (rodnenkaya.number!=0) {
                        //MainFrame.getInstance().contMinesForPlayer++;
                        rodnenkaya.button.add(new JLabel(Integer.toString(rodnenkaya.number)));
                        MainFrame.getInstance().getContentPane().setVisible(false);
                        MainFrame.getInstance().getContentPane().setVisible(true);
                    } else {
                        //MainFrame.getInstance().contMinesForPlayer++;
                        MainFrame.getInstance().openSqs(rodnenkaya.posx, rodnenkaya.posy);
                        MainFrame.getInstance().getContentPane().setVisible(false);
                        MainFrame.getInstance().getContentPane().setVisible(true);
                    }
                } else {
                   // MainFrame.getInstance().contMinesForPlayer++;
                    rodnenkaya.button.setBackground(Color.red);
                    rodnenkaya.button.setIcon(null);
                    MainFrame.getInstance().getContentPane().setVisible(false);
                    MainFrame.getInstance().getContentPane().setVisible(true);
                    JOptionPane.showMessageDialog(null, "БАБАХ ;(");
                }
                isPressed = 2;
            }
        }
    }
}