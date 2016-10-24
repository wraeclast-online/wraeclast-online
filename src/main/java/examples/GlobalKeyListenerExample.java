package examples;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalKeyListenerExample implements NativeKeyListener, WindowListener {

    public GlobalKeyListenerExample() {
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
        SwingUtilities.invokeLater(() -> {
            frame.setAutoRequestFocus(true);
            frame.setSize(100 , 150);
            frame.setType(Window.Type.UTILITY);
            frame.setUndecorated(true);
            frame.setLayout( null );
            frame.addWindowListener(this);
            frame.setAlwaysOnTop(true);
        });

    }
    
    private void setVis(boolean vis) {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(vis);
            System.out.println(frame.isVisible() + " " + frame.isFocused());
        });
    }


    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
        System.out.println("windowClosed");
        setVis(false);
    }

    public void windowClosing(WindowEvent e) {
    }
    public void windowIconified(WindowEvent e) {
        System.out.println("windowIconified");
        setVis(false);
    }
    public void windowDeiconified(WindowEvent e) {
    }
    public void windowActivated(WindowEvent e) { /* Unimplemented */ }
    public void windowDeactivated(WindowEvent e) {
        System.out.println("windowDeactivated");
        setVis(false);
    }

    private boolean ctrlPressed = false;

    public void nativeKeyPressed(NativeKeyEvent e) {
//        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }

        if (isCtrl(e)) {
            ctrlPressed = true;
        }

        if (ctrlPressed && e.getKeyCode() == NativeKeyEvent.VC_C) {
            System.out.println("COPY" + i++ + " " + Thread.currentThread().getName());
            setVis(!frame.isVisible());
//            frame.requestFocus();
//            toolTip.setVisible(true);
//            JOptionPane.
        }

    }
int i = 0;
    private boolean isCtrl(NativeKeyEvent e) {
        return e.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || e.getKeyCode() == NativeKeyEvent.VC_CONTROL_R;
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
//        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

//        if (e.getKeyCode() == NativeKeyEvent.VC_LEFT) System.out.println("VC_LEFT");
        if (isCtrl(e)) {
            ctrlPressed = false;
        }
    }

    //Create a JFrame
    JFrame frame = new JFrame();
    JToolTip toolTip = new JToolTip();

    public void nativeKeyTyped(NativeKeyEvent e) {
//        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()) + " " + e.getKeyCode());


    }
    
    

    public static void main(String[] args) {
        // Create custom logger and level.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
    }
}