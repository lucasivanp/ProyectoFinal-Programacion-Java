/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miselaneos;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JDesktopPane;
import static trabajofinal.FrmSistema.img;

/**
 *
 * @author Usuario
 */
public class JDeskTopsistema extends  JDesktopPane{
    private Image backGround;
    public JDeskTopsistema(){
        backGround = img.getBackGround();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, (int) getWidth(), (int) getHeight(), null);
    
    
}
}
