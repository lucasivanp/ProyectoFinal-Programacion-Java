/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author author
 */
public class Especialidad {
    private int id;
    
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    public Especialidad(int id,String nombre) {
        this.setId(id);
       
        this.setNombre(nombre);
        
    }

    public Especialidad() {
        this.setId(0);

        this.setNombre("");
       
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

   
    
    public Object[] toObject() {
        Object[] info = new Object[]{getId(),getNombre()};
        return info;
    }

}
