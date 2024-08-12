/*
 * clase para manejar las obras sociales
 */
package Entidades;

/**
 *
 * @author author
 */
public class obraSocial {

    private int id;
    private String obrasocial;
    private String fecha;

    public obraSocial(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public obraSocial(int id, String obrasocial) {
        this.setId(id);
        this.setObrasocial(obrasocial);
    }

    public obraSocial() {
        this.setId(0);
        this.setObrasocial("");
        this.setFecha("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObrasocial() {
        return obrasocial;
    }

    public void setObrasocial(String obrasocial) {
        this.obrasocial = obrasocial;
    }

    public Object[] toObject() {
        Object[] info = new Object[]{getId(), getObrasocial(), getFecha(),};
        return info;
    }
}
