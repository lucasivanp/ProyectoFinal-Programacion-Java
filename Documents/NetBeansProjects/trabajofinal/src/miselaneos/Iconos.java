/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miselaneos;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author Usuario
 */
public class Iconos {
    static final String SEP = System.getProperty("file.separator"); //constante que contiene el separador de directorios
    static final String PATHIMG = new File("").getAbsolutePath() + SEP; // constante que contiene la direccion donde se ejecuta el proyecto

    /**
     * Metodo que recibe un valor entero usado para el ancho, otro para el alto
     * y por ultimo una clase ImageIcon. Estos valores son usados para lograr
     * una imagen redimensionada.-
     *
     * @param x
     * @param y
     * @param icono
     * @return
     */
    public ImageIcon getSizeIcon(int x, int y, ImageIcon icono) {
        ImageIcon ImagenIconizable = icono;
        Image imgTrabajar = ImagenIconizable.getImage();
        Image imagenIconizable = imgTrabajar.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imagenIconizable);
    }

    /**
     * Metodo que recibe el tamaño que se va a usar tanto para ancho y alto de
     * la imagen
     *
     * @param size
     * @return
     */
    public ImageIcon getSys(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/sys2.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getAdd(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/add.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getEdit(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/edit.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getDelete(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/delete.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getList(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/list.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getFilter(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/filter.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getUser(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/user.png"));
        return getSizeIcon(size, size, icono);
    }

    public ImageIcon getClient(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/CLIENTES2.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getEsp(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/especialid.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getobrasoc(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/obrasoc.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getExit(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/exit2.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getKey(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/key.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getUpdate(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/update.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getCheck(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/check.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getClock(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/clock.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getAcept(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/acept.png"));
        return getSizeIcon(size, size, icono);
    }
    
    public ImageIcon getRevision(int size) {
        ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/revision.png"));
        return getSizeIcon(size, size, icono);
    }
     public ImageIcon getEspecialidades(int size){
        ImageIcon icono = new ImageIcon(getClass().getResource("/Iconos/especialidades.png"));
        return getSizeIcon(size,size,icono);
    }
}
