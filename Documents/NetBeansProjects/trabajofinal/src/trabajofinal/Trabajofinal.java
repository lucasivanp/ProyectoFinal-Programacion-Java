/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabajofinal;

/**
 *
 * @author Usuario
 */
public class Trabajofinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FrmSistema frm = new FrmSistema();
                frm.setVisible(true);
            }
        });
    }
}
