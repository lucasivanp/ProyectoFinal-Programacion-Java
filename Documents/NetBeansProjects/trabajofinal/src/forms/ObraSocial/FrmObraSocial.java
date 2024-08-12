package forms.ObraSocial;

import Datos.ObraSocial;
import Entidades.obraSocial;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import miselaneos.FrmInterno;
import static trabajofinal.FrmSistema.iconos;

/*
* @author author
 */
public class FrmObraSocial extends FrmInterno {

    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;

    public static int id = 1;

    /**
     *
     */
    public FrmObraSocial() {
        initComponents();
        _initTable();
        _loadObrasocial();
    }

    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Obra Social");
        jTable.setModel(modelo);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(200);
    }

    private void _loadObrasocial() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnNew1.setEnabled(false);
                btnEdit1.setEnabled(false);
                btnDelete.setEnabled(false);
                btnactu1.setEnabled(false);

                progressObraSocial.setValue(0);
                ArrayList<obraSocial> ObraSocialList = new ArrayList<obraSocial>();
                ObraSocial cnx = new ObraSocial();
                if (cnx.isOkConexion()) {

                    ObraSocialList = cnx.listObraSocial("SELECT * FROM " + cnx.TABLA);

                    cnx.isCloseConexion();
                }
                progressObraSocial.setMaximum(ObraSocialList.size());
                progressObraSocial.setVisible(true);

                modelo.getDataVector().clear();
                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < ObraSocialList.size(); index++) {
                    obraSocial l = ObraSocialList.get(index);
                    modelo.addRow(l.toObject());
                    progressObraSocial.setValue(index);
                }

                progressObraSocial.setVisible(false);
                progressObraSocial.setValue(0);

                setTitle("Obra Social:" + ObraSocialList.size());

                btnNew1.setEnabled(true);
                btnEdit1.setEnabled(true);
                btnDelete.setEnabled(true);
                btnactu1.setEnabled(true);

            }
        });
        t.start();
    }

    private boolean _isValidate(obraSocial l) {
        boolean isOk = false;

        if (l.getObrasocial().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlInfo, "No Ha Ingresado ObraSocial.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }

        isOk = true;

        return isOk;
    }

    private obraSocial _getObrasocialForm() {
        obraSocial l = new obraSocial();

        l.setId(txtNro1.toEntero());
        l.setObrasocial(txtEspecialidades.getText().trim());

        return l;
    }

    private void _setForm(obraSocial l) {
        txtNro1.setText(String.valueOf(l.getId()));
        txtEspecialidades.setText(l.getObrasocial().trim());

        txtEspecialidades.requestFocus();
    }

    private void _selectionRow() {
        int indexRow = jTable.getSelectedRow();
        int indexModel = jTable.convertRowIndexToModel(indexRow);

        int id = (int) modelo.getValueAt(indexModel, 0);
        String obraSocial = (String) modelo.getValueAt(indexModel, 1);

        obraSocial l = new obraSocial(id, obraSocial);

        _setForm(l);

    }

    private boolean _isNew() {
        boolean isOk = false;
        obraSocial l = _getObrasocialForm();
        if (_isValidate(l)) {
            //Esta en codiciones
            ObraSocial cnx = new ObraSocial();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(l);
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
        obraSocial l = _getObrasocialForm();
        if (_isValidate(l)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlcentro, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            ObraSocial cnx = new ObraSocial();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(l);
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
        obraSocial l = _getObrasocialForm();
        if (_isValidate(l)) {
            //Esta en codiciones
            ObraSocial cnx = new ObraSocial();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(l);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlcentro = new javax.swing.JPanel();
        pnlCentroGrid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblFilter1 = new etiquetas.LblFilter();
        txtMayusculas1 = new texto.TxtMayusculas();
        btnactu1 = new Botones.Btnactu();
        progressObraSocial = new javax.swing.JProgressBar();
        pnlCentroespeci = new javax.swing.JPanel();
        pnlInfo = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        txtNro1 = new texto.TxtNro();
        lbl1 = new etiquetas.Lbl();
        txtEspecialidades = new texto.TxtMayusculas();
        pnlbutton = new javax.swing.JPanel();
        btnNew1 = new Botones.BtnNew();
        btnEdit1 = new Botones.BtnEdit();
        btnDelete = new Botones.BtnDelete();

        setFrameIcon(iconos.getEsp(16)
        );
        setPreferredSize(new java.awt.Dimension(570, 371));
        getContentPane().setLayout(new java.awt.BorderLayout());

        pnlcentro.setPreferredSize(new java.awt.Dimension(772, 451));
        pnlcentro.setLayout(new java.awt.BorderLayout());

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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        pnlCentroGrid.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        lblFilter1.setText("Filrar:");

        txtMayusculas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMayusculas1KeyReleased(evt);
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
                .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMayusculas1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
                .addComponent(btnactu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMayusculas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnactu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlCentroGrid.add(jPanel1, java.awt.BorderLayout.NORTH);

        progressObraSocial.setVisible(false);
        pnlCentroGrid.add(progressObraSocial, java.awt.BorderLayout.PAGE_END);

        pnlcentro.add(pnlCentroGrid, java.awt.BorderLayout.CENTER);

        pnlCentroespeci.setPreferredSize(new java.awt.Dimension(290, 40));
        pnlCentroespeci.setLayout(new java.awt.BorderLayout());

        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder("Obra Social"));
        pnlInfo.setPreferredSize(new java.awt.Dimension(320, 280));

        lbl1.setText("Obra Social:");

        txtEspecialidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEspecialidadesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNro1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(259, 259, 259))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEspecialidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(301, 301, 301))
        );

        pnlCentroespeci.add(pnlInfo, java.awt.BorderLayout.CENTER);
        pnlInfo.getAccessibleContext().setAccessibleName("ObraSocial");

        pnlbutton.setPreferredSize(new java.awt.Dimension(290, 40));

        btnNew1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNew1ActionPerformed(evt);
            }
        });
        pnlbutton.add(btnNew1);

        btnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit1ActionPerformed(evt);
            }
        });
        pnlbutton.add(btnEdit1);

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlbutton.add(btnDelete);

        pnlCentroespeci.add(pnlbutton, java.awt.BorderLayout.SOUTH);

        pnlcentro.add(pnlCentroespeci, java.awt.BorderLayout.LINE_END);

        getContentPane().add(pnlcentro, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEspecialidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEspecialidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEspecialidadesActionPerformed

    private void btnNew1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNew1ActionPerformed
        if (_isNew()) {
            _setForm(new obraSocial());
            _loadObrasocial();
        }
    }//GEN-LAST:event_btnNew1ActionPerformed

    private void btnactu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactu1ActionPerformed

        _loadObrasocial();
    }//GEN-LAST:event_btnactu1ActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed
        if (_isUpdate()) {
            _loadObrasocial();
    }//GEN-LAST:event_btnEdit1ActionPerformed
    }
    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
         try {
        _selectionRow();
    } catch (ClassCastException e) {
        e.printStackTrace();
        // Agrega aquí la lógica adicional según sea necesario para manejar la excepción.
    }
    }//GEN-LAST:event_jTableMouseClicked

    private void jTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyPressed
       _selectionRow();
    }//GEN-LAST:event_jTableKeyPressed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
      if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
           _loadObrasocial();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtMayusculas1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMayusculas1KeyReleased
        orden = new TableRowSorter<TableModel>(modelo);
        this.jTable.setRowSorter(orden);

        RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(txtMayusculas1.getText().trim());
        orden.setRowFilter(filtro);
    }//GEN-LAST:event_txtMayusculas1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static Botones.BtnDelete btnDelete;
    public static Botones.BtnEdit btnEdit1;
    public static Botones.BtnNew btnNew1;
    public static Botones.Btnactu btnactu1;
    public static etiquetas.Id id1;
    public static javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    public static etiquetas.Lbl lbl1;
    public static etiquetas.LblFilter lblFilter1;
    public static javax.swing.JPanel pnlCentroGrid;
    public static javax.swing.JPanel pnlCentroespeci;
    private javax.swing.JPanel pnlInfo;
    public static javax.swing.JPanel pnlbutton;
    private javax.swing.JPanel pnlcentro;
    public static javax.swing.JProgressBar progressObraSocial;
    public static texto.TxtMayusculas txtEspecialidades;
    public static texto.TxtMayusculas txtMayusculas1;
    public static texto.TxtNro txtNro1;
    // End of variables declaration//GEN-END:variables
}
