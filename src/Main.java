import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PokerCardShuffler::new);
    }
}
