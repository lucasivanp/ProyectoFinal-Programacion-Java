/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package forms.especialidades;

import Datos.Especialidades;
import Entidades.Especialidad;
import miselaneos.FrmInterno;
import static trabajofinal.FrmSistema.iconos;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author author
 */
public class FrmEspecialidades extends FrmInterno {

    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;

    public static int id = 2;

    /**
     * Creates new form FrmLocalice
     */
    public FrmEspecialidades() {
        initComponents();
        _initTable();
        _loadEspecialidad();
    }

    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("ESPECIALIDAD");
        jTable.setModel(modelo);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(200);
    }

    private void _loadEspecialidad() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnNew1.setEnabled(false);
                btnEdit1.setEnabled(false);
                btnDelete1.setEnabled(false);
                btnactu1.setEnabled(false);

                progressEspecialidad.setValue(0);
                ArrayList<Especialidad> especialidadList = new ArrayList<Especialidad>();
                Especialidades cnx = new Especialidades();
                if (cnx.isOkConexion()) {

                    especialidadList = cnx.listespecialidad("SELECT * FROM " + cnx.TABLA);

                    cnx.isCloseConexion();
                }
                progressEspecialidad.setMaximum(especialidadList.size());
                progressEspecialidad.setVisible(true);

                modelo.getDataVector().clear();
                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < especialidadList.size(); index++) {
                    Especialidad a = especialidadList.get(index);
                    modelo.addRow(a.toObject());
                    progressEspecialidad.setValue(index);
                }

                progressEspecialidad.setVisible(false);
                progressEspecialidad.setValue(0);

                setTitle("Especialidades cantidad:" + especialidadList.size());

                btnNew1.setEnabled(true);
                btnEdit1.setEnabled(true);
                btnDelete1.setEnabled(true);
                btnactu1.setEnabled(true);
            }
        });
        t.start();

    }

    private boolean _isValidate(Especialidad a) {
        boolean isOk = false;

        if (a.getNombre().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlCentroEspe, "No Ha Ingresado Especialidad.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }

        isOk = true;

        return isOk;
    }

    private Especialidad _getEspecialidadForm() {
        Especialidad a = new Especialidad();

        a.setId(txtId.toEntero());
        a.setNombre(txtEspecialidad.getText().trim());

        return a;
    }

    private void _setForm(Especialidad a) {
        txtId.setText(String.valueOf(a.getId()));
       
        txtEspecialidad.setText(a.getNombre().trim());

        txtEspecialidad.requestFocus();
    }

    private void _selectionRow() {
        int indexRow = jTable.getSelectedRow();
        int indexModel = jTable.convertRowIndexToModel(indexRow);

        int id = (int) modelo.getValueAt(indexModel, 0);
        String Especialidad = (String) modelo.getValueAt(indexModel, 1);

        Especialidad a = new Especialidad(id, Especialidad);

        _setForm(a);

    }

    private boolean _isNew() {
        boolean isOk = false;
        Especialidad a = _getEspecialidadForm();
        if (_isValidate(a)) {
            //Esta en codiciones
            Especialidades cnx = new Especialidades();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(a);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }

    private boolean _isUpdate() {
        boolean isOk = false;
        Especialidad a = _getEspecialidadForm();
        if (_isValidate(a)) {
            //Esta en codiciones
            Especialidades cnx = new Especialidades();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(a);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }

    private boolean _isDelete() {
        boolean isOk = false;
        Especialidad a = _getEspecialidadForm();
        if (_isValidate(a)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlCentro, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Especialidades cnx = new Especialidades();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(a);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCentro = new javax.swing.JPanel();
        pnlCentroGrid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtFilter = new texto.TxtMayusculas();
        btnfiltrar1 = new Botones.Btnfiltrar();
        btnactu1 = new Botones.Btnactu();
        progressEspecialidad = new javax.swing.JProgressBar();
        pnlEspecialidad = new javax.swing.JPanel();
        pnlCentroEspe = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        txtId = new texto.TxtNro();
        txtEspecialidad = new texto.TxtMayusculas();
        lbl1 = new etiquetas.Lbl();
        pnlButton = new javax.swing.JPanel();
        btnNew1 = new Botones.BtnNew();
        btnEdit1 = new Botones.BtnEdit();
        btnDelete1 = new Botones.BtnDelete();

        setTitle("ESPECIALIDAD");
        setFrameIcon(iconos.getEspecialidades(16)
        );
        setPreferredSize(new java.awt.Dimension(754, 380));
        getContentPane().setLayout(new java.awt.BorderLayout());

        pnlCentro.setLayout(new java.awt.BorderLayout());

        pnlCentroGrid.setLayout(new java.awt.BorderLayout());

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        pnlCentroGrid.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(451, 40));

        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });

        btnactu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactu1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnfiltrar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnactu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnfiltrar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnactu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pnlCentroGrid.add(jPanel1, java.awt.BorderLayout.NORTH);

        progressEspecialidad.setVisible(false);
        pnlCentroGrid.add(progressEspecialidad, java.awt.BorderLayout.PAGE_END);

        pnlCentro.add(pnlCentroGrid, java.awt.BorderLayout.CENTER);

        pnlEspecialidad.setBorder(javax.swing.BorderFactory.createTitledBorder("Especialidad"));
        pnlEspecialidad.setPreferredSize(new java.awt.Dimension(320, 279));
        pnlEspecialidad.setRequestFocusEnabled(false);
        pnlEspecialidad.setVerifyInputWhenFocusTarget(false);

        txtId.setEditable(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        lbl1.setText("Especialidad:");

        javax.swing.GroupLayout pnlCentroEspeLayout = new javax.swing.GroupLayout(pnlCentroEspe);
        pnlCentroEspe.setLayout(pnlCentroEspeLayout);
        pnlCentroEspeLayout.setHorizontalGroup(
            pnlCentroEspeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroEspeLayout.createSequentialGroup()
                .addGroup(pnlCentroEspeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroEspeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCentroEspeLayout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 147, Short.MAX_VALUE))
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCentroEspeLayout.setVerticalGroup(
            pnlCentroEspeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroEspeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCentroEspeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroEspeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        pnlButton.setPreferredSize(new java.awt.Dimension(290, 400));

        btnNew1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNew1ActionPerformed(evt);
            }
        });
        pnlButton.add(btnNew1);

        btnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit1ActionPerformed(evt);
            }
        });
        pnlButton.add(btnEdit1);

        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });
        pnlButton.add(btnDelete1);

        javax.swing.GroupLayout pnlEspecialidadLayout = new javax.swing.GroupLayout(pnlEspecialidad);
        pnlEspecialidad.setLayout(pnlEspecialidadLayout);
        pnlEspecialidadLayout.setHorizontalGroup(
            pnlEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCentroEspe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlEspecialidadLayout.setVerticalGroup(
            pnlEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEspecialidadLayout.createSequentialGroup()
                .addComponent(pnlCentroEspe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlCentro.add(pnlEspecialidad, java.awt.BorderLayout.LINE_END);

        getContentPane().add(pnlCentro, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtObraSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObraSocialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObraSocialActionPerformed

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        orden = new TableRowSorter<TableModel>(modelo);
        this.jTable.setRowSorter(orden);

        RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(txtFilter.getText().trim());
        orden.setRowFilter(filtro);
    }//GEN-LAST:event_txtFilterKeyReleased

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed

    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyReleased
        _selectionRow();
    }//GEN-LAST:event_jTableKeyReleased

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
          try {
        _selectionRow();
    } catch (ClassCastException e) {
              // Agrega aquí la lógica adicional según sea necesario para manejar la excepción.
              
    }
    }//GEN-LAST:event_jTableMouseClicked

    private void btnNew1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNew1ActionPerformed
        if (_isNew()) {
            _setForm(new Especialidad());
            _loadEspecialidad();
        }


    }//GEN-LAST:event_btnNew1ActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed
        if (!btnEdit1.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadEspecialidad();
        }    }//GEN-LAST:event_btnEdit1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        if (!btnDelete1.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _loadEspecialidad();
        }
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnactu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactu1ActionPerformed
        if (!btnactu1.isEnabled()) {
            return;
        }
        _loadEspecialidad();


    }//GEN-LAST:event_btnactu1ActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Botones.BtnDelete btnDelete1;
    private Botones.BtnEdit btnEdit1;
    private Botones.BtnNew btnNew1;
    private Botones.Btnactu btnactu1;
    private Botones.Btnfiltrar btnfiltrar1;
    private etiquetas.Id id1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private etiquetas.Lbl lbl1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlCentroEspe;
    private javax.swing.JPanel pnlCentroGrid;
    private javax.swing.JPanel pnlEspecialidad;
    private javax.swing.JProgressBar progressEspecialidad;
    private texto.TxtMayusculas txtEspecialidad;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtNro txtId;
    // End of variables declaration//GEN-END:variables
}
