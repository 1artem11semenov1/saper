import javax.swing.*;

public class MyButton {
    public JButton button;
    private ImageIcon icon;
    public boolean isMine = false;
    public int number;
    public boolean isOpen = false;
    int posx;
    int posy;


    MyButton(JButton button_u, int x, int y){
        button = button_u;
        posx = x;
        posy = y;
    }
}
