/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package edu.robertob.p1compi2.view;

import edu.robertob.p1compi2.engine.structs.SymbolTable;

import javax.swing.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author robertob
 */
public class SymbolsReportFrame extends javax.swing.JFrame {

    /**
     * Creates new form SymbolsReportFrame
     */
    public SymbolsReportFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel1.setText("Reporte - Tabla de símbolos");

        jTable1.setFont(new java.awt.Font("Helvetica Neue", 0, 16)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Categoría", "Tipo ID", "# Parametros", "Ámbito", "Linea", "Columna"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setSymbolTableAndShow(SymbolTable globalTable) {
        // before, clean the table
        for (int i = 0; i < this.jTable1.getRowCount(); i++) {
            for (int j = 0; j < this.jTable1.getColumnCount(); j++) {
                this.jTable1.setValueAt("", i, j);
            }
        }

        if (globalTable == null) {
            JOptionPane.showMessageDialog(this, "No hay tabla de símbolos para mostrar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        var symbols = globalTable.collectAllEntries();

        int i = 0;
        for (i = 0; i < symbols.size(); i++) {
            var symbol = symbols.get(i);
            System.out.println("Symbol: " + symbol);
            this.jTable1.setValueAt(i+1, i, 0);
            this.jTable1.setValueAt(symbol.getId(), i, 1);
            this.jTable1.setValueAt(symbol.getCategory(), i, 2);
//            this.jTable1.setValueAt(symbol.getOriginalTypeId(), i, 3);
            this.jTable1.setValueAt(symbol.getTypeId(), i, 3);
            this.jTable1.setValueAt(null, i, 4);
            this.jTable1.setValueAt(symbol.getScopeName(), i, 5);
            this.jTable1.setValueAt(symbol.getLine(), i, 6);
            this.jTable1.setValueAt(symbol.getColumn(), i, 7);
        }

        var functionDeclarations = globalTable.getFunctions();
        for (; i < symbols.size() + functionDeclarations.size(); i++) {
            var function = functionDeclarations.get(i - symbols.size());
            this.jTable1.setValueAt(i+1, i, 0);
            this.jTable1.setValueAt(function.getId(), i, 1);
            this.jTable1.setValueAt("Función", i, 2);
            this.jTable1.setValueAt(function.getTypeId(), i, 3);
            this.jTable1.setValueAt(function.getParams().size(), i, 4);
            this.jTable1.setValueAt("Global", i, 5);
            this.jTable1.setValueAt(function.getLine(), i, 6);
            this.jTable1.setValueAt(function.getColumn(), i, 7);
        }

        var procedureDeclarations = globalTable.getProcedures();
        for (; i < symbols.size() + functionDeclarations.size() + procedureDeclarations.size(); i++) {
            var procedure = procedureDeclarations.get(i - symbols.size() - functionDeclarations.size());
            this.jTable1.setValueAt(i+1, i, 0);
            this.jTable1.setValueAt(procedure.getId(), i, 1);
            this.jTable1.setValueAt("Procedimiento", i, 2);
            this.jTable1.setValueAt(null, i, 3);
            this.jTable1.setValueAt(procedure.getParams().size(), i, 4);
            this.jTable1.setValueAt("Global", i, 5);
            this.jTable1.setValueAt(procedure.getLine(), i, 6);
            this.jTable1.setValueAt(procedure.getColumn(), i, 7);
        }



        this.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
