/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texto;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author pablo
 */
public class TxtCuit extends JFormattedTextField {

    public TxtCuit() {
        formato();
        ollentes();
    }

    private void formato() {
        MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("##-########-#");

        } catch (ParseException e) {
            // De momento, no hacemos nada
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        // El caràcter a mostrar en las posiciones escribibles es el
        // subrayado.
        mask.setPlaceholderCharacter('_');
        mask.setCommitsOnValidEdit(false);
        mask.setValidCharacters("0123456789-");

        DefaultFormatterFactory factory = new DefaultFormatterFactory(mask,
                mask, mask, mask);
        setFormatterFactory(factory);
    }
    
    private void ollentes() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    transferFocus();
                }
            }
        });
    }
}
