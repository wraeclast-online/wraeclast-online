package examples;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;

public class SimplePaintSurface implements Runnable, ActionListener {

    private static final int WIDTH = 1250;
    private static final int HEIGHT = 800;
    private Random random = new Random();
    private JFrame frame = new JFrame("SimplePaintSurface");
    private JPanel tableaux;

    @Override
    public void run() {
        tableaux = new JPanel(null);
        for (int i = 1500; --i >= 0;) {
            addRandom();
        }
        frame.add(tableaux, BorderLayout.CENTER);
        JButton add = new JButton("Add");
        add.addActionListener(this);
        frame.add(add, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tableaux.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        addRandom();
        tableaux.repaint();
    }

    void addRandom() {
        Letter letter = new Letter(Character.toString((char) ('a' + random.nextInt(26))));
        letter.setBounds(random.nextInt(WIDTH), random.nextInt(HEIGHT), 16, 16);
        tableaux.add(letter);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new SimplePaintSurface());
    }
}

class Letter extends JLabel {

    private Font font1;
    private Font font2;
    private final FontRenderContext fontRenderContext1;
    private final FontRenderContext fontRenderContext2;

    public Letter(final String letter) {
        super(letter);
        setFocusable(true);
        setBackground(Color.RED);
        font1 = getFont();
        font2 = font1.deriveFont(48f);
        fontRenderContext1 = getFontMetrics(font1).getFontRenderContext();
        fontRenderContext2 = getFontMetrics(font2).getFontRenderContext();
        MouseInputAdapter mouseHandler = new MouseInputAdapter() {

            @Override
            public void mouseEntered(final MouseEvent e) {
                Letter.this.setOpaque(true);
                setFont(font2);
                Rectangle bounds = getBounds();
                Rectangle2D stringBounds = font2.getStringBounds(getText(), fontRenderContext2);
                bounds.width = (int) stringBounds.getWidth();
                bounds.height = (int) stringBounds.getHeight();
                setBounds(bounds);
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                Letter.this.setOpaque(false);
                setFont(font1);
                Rectangle bounds = getBounds();
                Rectangle2D stringBounds = font1.getStringBounds(getText(), fontRenderContext1);
                bounds.width = (int) stringBounds.getWidth();
                bounds.height = (int) stringBounds.getHeight();
                setBounds(bounds);
            }
        };
        addMouseListener(mouseHandler);
    }
}