/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miselaneos;

import static trabajofinal.FrmSistema.JDeskTopsistema;
import forms.ObraSocial.FrmObraSocial;
import forms.especialidades.FrmEspecialidades;
import forms.pacientes.FrmPacient;
import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.JInternalFrame;

/**
 *
 * @author Usuario
 */
public class GUI {

    private final HashMap<Integer, FrmInterno> frmList;

    public GUI() {
        frmList = new HashMap<>();
    }

    /*public JInternalFrame loadObrasocial(int id, boolean isView) {
        JInternalFrame frm = null;
        if (frmList.containsKey(id)) {
            frm = frmList.get(id);
        } else {
            frm = new FrmObraSocial(); // o cualquier otra subclase de JInternalFrame que necesites
            frmList.put(id, (FrmInterno) frm);
            JDeskTopsistema.add(frm, BorderLayout.CENTER);
        }
        if (isView) {
            frm.show();
        }
        return frm;
    }*/


    public FrmObraSocial loadObrasocial(int id, boolean isView) {
        FrmObraSocial frm = null;
        if (frmList.containsKey(id)) {
            frm = (FrmObraSocial) frmList.get(id);
        } else {
            frm = new FrmObraSocial();
            frmList.put(id, frm);
            JDeskTopsistema.add(frm, BorderLayout.CENTER);
        }
        if (isView) {
            frm._show();
        }
        return frm;
    }

    public FrmPacient loadPacient(int id, boolean isView) {
        FrmPacient frm = null;
        if (frmList.containsKey(id)) {
            frm = (FrmPacient) frmList.get(id);
        } else {
            frm = new FrmPacient();
            frmList.put(id, frm);
            JDeskTopsistema.add(frm, BorderLayout.CENTER);
        }
        if (isView) {
            frm._show();
        }
        return frm;
    }

    public FrmEspecialidades loadEspecialidades(int id, boolean isView) {
        FrmEspecialidades frm = null;
        if (frmList.containsKey(id)) {
            frm = (FrmEspecialidades) frmList.get(id);
        } else {
            frm = new FrmEspecialidades();
            frmList.put(id, frm);
            JDeskTopsistema.add(frm, BorderLayout.CENTER);
        }
        if (isView) {
            frm._show();
        }
        return frm;

    }
   /* public JInternalFrame loadEspecialidades(int id, boolean isView) {
    JInternalFrame frm = null;
    if (frmList.containsKey(id)) {
        frm = frmList.get(id);
    } else {
        frm = new FrmEspecialidades(); // Asegúrate de usar FrmEspecialidades aquí
        frmList.put(id, (FrmInterno) frm);
        JDeskTopsistema.add(frm, BorderLayout.CENTER);
    }
    if (isView) {
        frm.show();
    }
    return frm;
}*/
}
