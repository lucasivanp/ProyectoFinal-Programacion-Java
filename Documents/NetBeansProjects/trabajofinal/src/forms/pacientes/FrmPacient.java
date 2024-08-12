/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.pacientes;


import Datos.Pacientes;
import Entidades.Paciente;

import miselaneos.FrmInterno;
import static trabajofinal.FrmSistema.iconos;

import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import static trabajofinal.FrmSistema.fe;

import javax.swing.RowFilter;

/**
 *
 * @author author
 */
public class FrmPacient extends FrmInterno {

    public static int id = 3;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;

    /**
     * Creates new form FrmClients
     */
    public FrmPacient() {
        initComponents();

        _loadPacient();
    }

    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Nombre(s)");
        modelo.addColumn("Apellido");
        modelo.addColumn("Documento");
        modelo.addColumn("Email");
        modelo.addColumn("Numero Telefonico");
        

        jTable.setRowSorter(null); //Elimino Filtro
        jTable.setModel(modelo);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(160);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(80);
    }

    private void _loadPacient() {
        if (!btnNew2.isEnabled()) {
            return;
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnNew2.setEnabled(false);
                btnEdit1.setEnabled(false);
                btnDelete1.setEnabled(false);
                btnactu2.setEnabled(false);

                _initTable();

                ProgressBarPacient.setValue(0);
                ArrayList<Paciente> pacientesList = new ArrayList<Paciente>();
                Pacientes cnx = new Pacientes();
                if (cnx.isOkConexion()) {

                    pacientesList = cnx.listPaciente("SELECT * FROM " + cnx.TABLA);

                    cnx.isCloseConexion();
                }
                ProgressBarPacient.setMaximum(pacientesList.size());
                ProgressBarPacient.setVisible(true);

                modelo.getDataVector().clear();
                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < pacientesList.size(); index++) {
                    Paciente c = pacientesList.get(index);
                    modelo.addRow(c.toObject());
                    ProgressBarPacient.setValue(index);
                }

                ProgressBarPacient.setVisible(false);
                ProgressBarPacient.setValue(0);

               
                
                btnNew2.setEnabled(true);
                btnEdit1.setEnabled(true);
                btnDelete1.setEnabled(true);
                btnactu2.setEnabled(true);

            }
        });
        t.start();
    }

    private boolean _isValidate(Paciente c) {
        boolean isOk = false;

        if (c.getName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Nombres.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }
        if (c.getLastName().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Apellido.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }
        if (c.getDocument() == 0) {
            JOptionPane.showMessageDialog(pnlFicha, "No Ha Ingresado Documento.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }

        isOk = true;

        return isOk;
    }

    private Paciente _getPacientForm() {
        Paciente c = new Paciente();

        c.setId(txtId.toEntero());
        c.setName(txtName.getText().trim());
        c.setLastName(txtLastName.getText().trim());
        c.setDocument(txtDocument.toEntero());
        c.setId_obrasocial(pnlComboObrasocial._getobrasocSelected().getId());
        c.setMovils(txtMovil.getText().trim());
        c.setEmail(txtEmail.getText().trim());
        c.setFecha(fe.getFechaACalendar(FechaN.getDate()));
       
        return c;
    }

    private void _setForm(Paciente c) {
        txtId.setText(String.valueOf(c.getId()));
        txtName.setText(c.getName().trim());
        txtLastName.setText(c.getLastName().trim());
        txtDocument.setText(String.valueOf(c.getDocument()));
        pnlComboObrasocial._setobrasocSelected(c.getId_obrasocial());
        txtMovil.setText(c.getMovils().trim());
        txtEmail.setText(c.getEmail().trim());
        FechaN.setCalendar(c.getFecha());
        if (c.getFecha()== null) {
            c.setFecha(Calendar.getInstance());
        }
        FechaN.setDate(c.getFecha().getTime());
    }

    private void _selectionRow() {
        try {
            int indexRow = jTable.getSelectedRow();
            int indexModel = jTable.convertRowIndexToModel(indexRow);

            int id = (int) modelo.getValueAt(indexModel, 0);
            Paciente c = new Paciente();
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                c = cnx.getPaciente(id);
                cnx.isOkConexion();

            }
            _setForm(c);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void _filter(String texto) {
        try {
            orden = new TableRowSorter<TableModel>(modelo);
            this.jTable.setRowSorter(orden);

            RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(texto.trim());
            orden.setRowFilter(filtro);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean _isNew() {
        boolean isOk = false;
        Paciente c = _getPacientForm();
        if (_isValidate(c)) {
            //Esta en codiciones
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(c);
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
        Paciente c = _getPacientForm();
        if (_isValidate(c)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlFicha, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(c);
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
        Paciente c = _getPacientForm();
        if (_isValidate(c)) {
            //Esta en codiciones
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(c);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        pnlListPacient = new javax.swing.JPanel();
        pnlFilList = new javax.swing.JPanel();
        lblFilter1 = new etiquetas.LblFilter();
        txtFilter = new texto.TxtMayusculas();
        btnactu2 = new Botones.Btnactu();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        progressClients = new javax.swing.JProgressBar();
        ProgressBarPacient = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        pnlFicha = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        fechaN = new etiquetas.Lbl();
        NroTel = new etiquetas.Lbl();
        Email = new etiquetas.Lbl();
        txtLastName = new texto.TxtMayusculas();
        txtName = new texto.TxtMayusculas();
        txtEmail = new texto.TxtMinusculas();
        txtDocument = new texto.TxtNro();
        txtMovil = new texto.TxtFormato1Ejem();
        txtId = new texto.TxtNro();
        Dni = new etiquetas.Lbl();
        Nombres = new etiquetas.Lbl();
        Apell = new etiquetas.Lbl();
        FechaN = new com.toedter.calendar.JDateChooser();
        pnlComboObrasocial = new forms.ObraSocial.pnlComboObrasocial();
        pnlButtons = new javax.swing.JPanel();
        btnNew2 = new Botones.BtnNew();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        btnDelete1 = new Botones.BtnDelete();
        btnEdit1 = new Botones.BtnEdit();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));

        setTitle("Pacientes");
        setFrameIcon(iconos.getClient(16)
        );
        setPreferredSize(new java.awt.Dimension(733, 431));
        getContentPane().setLayout(new java.awt.BorderLayout());

        jTabbedPane.setPreferredSize(new java.awt.Dimension(400, 200));
        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        pnlListPacient.setLayout(new java.awt.BorderLayout());

        pnlFilList.setPreferredSize(new java.awt.Dimension(762, 40));

        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });

        btnactu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactu2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFilListLayout = new javax.swing.GroupLayout(pnlFilList);
        pnlFilList.setLayout(pnlFilListLayout);
        pnlFilListLayout.setHorizontalGroup(
            pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(btnactu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        pnlFilListLayout.setVerticalGroup(
            pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilListLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnactu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pnlListPacient.add(pnlFilList, java.awt.BorderLayout.NORTH);

        pnlList.setLayout(new java.awt.BorderLayout());

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

        pnlList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        progressClients.setVisible(false);
        pnlList.add(progressClients, java.awt.BorderLayout.PAGE_START);

        ProgressBarPacient.setVisible(false);
        pnlList.add(ProgressBarPacient, java.awt.BorderLayout.PAGE_END);

        pnlListPacient.add(pnlList, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Lista", iconos.getList(16)
            , pnlListPacient);

        pnlFicha.setBorder(javax.swing.BorderFactory.createTitledBorder("Pacientes"));
        pnlFicha.setPreferredSize(new java.awt.Dimension(400, 300));

        fechaN.setText("F.Nacimiento:");

        NroTel.setText("Nro. de Tel. / Cel.:");

        Email.setText("E-Mail:");

        txtLastName.setLenghtText(25);
        txtLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameActionPerformed(evt);
            }
        });

        txtName.setLenghtText(30);
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        txtEmail.setLenghtText(45);

        txtDocument.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentActionPerformed(evt);
            }
        });

        txtMovil.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMovil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMovilActionPerformed(evt);
            }
        });

        txtId.setEditable(false);
        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        Dni.setText("Dni:");

        Nombres.setText("Nombres:");

        Apell.setText("Apellidos:");

        javax.swing.GroupLayout pnlFichaLayout = new javax.swing.GroupLayout(pnlFicha);
        pnlFicha.setLayout(pnlFichaLayout);
        pnlFichaLayout.setHorizontalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addComponent(Dni, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(txtDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 295, Short.MAX_VALUE))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFichaLayout.createSequentialGroup()
                                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(Apell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Nombres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fechaN, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(FechaN, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlFichaLayout.createSequentialGroup()
                                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NroTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addComponent(pnlComboObrasocial, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlFichaLayout.setVerticalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Nombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Apell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fechaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FechaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Dni, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(NroTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlComboObrasocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(335, 335, 335))
        );

        pnlButtons.setLayout(new java.awt.GridLayout(8, 1, 5, 5));

        btnNew2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNew2ActionPerformed(evt);
            }
        });
        pnlButtons.add(btnNew2);
        pnlButtons.add(filler2);

        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });
        pnlButtons.add(btnDelete1);

        btnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEdit1ActionPerformed(evt);
            }
        });
        pnlButtons.add(btnEdit1);
        pnlButtons.add(filler1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(pnlFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFicha, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Ficha Cliente", jPanel2);

        getContentPane().add(jTabbedPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed

    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed

    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
       
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
            _selectionRow();
    }//GEN-LAST:event_jTableMouseClicked

    private void jTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyReleased

    }//GEN-LAST:event_jTableKeyReleased

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
 orden = new TableRowSorter<TableModel>(modelo);
        this.jTable.setRowSorter(orden);

        RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(txtFilter.getText().trim());
        orden.setRowFilter(filtro);
    }//GEN-LAST:event_txtFilterKeyReleased

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtMovilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMovilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMovilActionPerformed

    private void txtDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void txtLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameActionPerformed

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged

    }//GEN-LAST:event_jTabbedPaneStateChanged

    private void btnactu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactu2ActionPerformed
        _loadPacient();
    }//GEN-LAST:event_btnactu2ActionPerformed

    private void btnNew2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNew2ActionPerformed
        if (_isNew()) {
            _setForm(new Paciente());
            // _loadPacient();
        }
    }//GEN-LAST:event_btnNew2ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed

        if (!btnDelete1.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            //_loadPacient();
        }
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void btnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEdit1ActionPerformed

        if (!btnEdit1.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            //_loadPacient();
        }
    }//GEN-LAST:event_btnEdit1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private etiquetas.Lbl Apell;
    private etiquetas.Lbl Dni;
    private etiquetas.Lbl Email;
    private com.toedter.calendar.JDateChooser FechaN;
    private etiquetas.Lbl Nombres;
    private etiquetas.Lbl NroTel;
    private javax.swing.JProgressBar ProgressBarPacient;
    private Botones.BtnDelete btnDelete1;
    private Botones.BtnEdit btnEdit1;
    private Botones.BtnNew btnNew2;
    private Botones.Btnactu btnactu2;
    private etiquetas.Lbl fechaN;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private etiquetas.Id id1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTable;
    private etiquetas.LblFilter lblFilter1;
    private javax.swing.JPanel pnlButtons;
    private forms.ObraSocial.pnlComboObrasocial pnlComboObrasocial;
    private javax.swing.JPanel pnlFicha;
    private javax.swing.JPanel pnlFilList;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlListPacient;
    private javax.swing.JProgressBar progressClients;
    private texto.TxtNro txtDocument;
    private texto.TxtMinusculas txtEmail;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtNro txtId;
    private texto.TxtMayusculas txtLastName;
    private texto.TxtFormato1Ejem txtMovil;
    private texto.TxtMayusculas txtName;
    // End of variables declaration//GEN-END:variables

}
