/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package abinitgui.projects;

import abinitgui.core.MainFrame;
import abinitgui.mdtb.ClustepSimulation;
import abinitgui.mdtb.TightBindingSimulationOld;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class JobMonitorPanel extends javax.swing.JPanel {

    private Machine currentMachine;
    private RemoteJob currentJob;
    
    /**
     * Creates new form JobPanel
     */
    public JobMonitorPanel() {
        initComponents();
        updateList(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        machineCombo = new javax.swing.JComboBox();
        machineLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jobInfoPanel1 = new abinitgui.projects.JobInfoPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        machineCombo.setModel(new DefaultComboBoxModel<Machine>());
        machineCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                machineComboItemStateChanged(evt);
            }
        });
        machineCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                machineComboActionPerformed(evt);
            }
        });

        machineLabel.setText("Select machine :");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jButton1.setText("Refresh list of jobs");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Kill job");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(machineLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(machineCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jobInfoPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(machineLabel)
                            .addComponent(machineCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addComponent(jobInfoPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void machineComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_machineComboActionPerformed
        Machine mach = (Machine)machineCombo.getSelectedItem();
        if(mach != null)
        {
            this.currentMachine = mach;
            updateList(mach.getSubmissionSystem().getRemoteJobs());
        }
    }//GEN-LAST:event_machineComboActionPerformed

    private void machineComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_machineComboItemStateChanged

    }//GEN-LAST:event_machineComboItemStateChanged

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        RemoteJob rj = (RemoteJob)(jList1.getSelectedValue());
        if(rj != null)
        {
            if(this.currentJob != rj)
            {
                this.currentJob = rj;
                jobInfoPanel1.setInfos(rj.printInfos());
            }
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(currentMachine != null)
        {
            updateList(currentMachine.getSubmissionSystem().getRemoteJobs());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(currentJob != null)
        {
            this.currentJob.kill();
            if(currentMachine != null)
            {
                updateList(currentMachine.getSubmissionSystem().getRemoteJobs());
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public void refreshMachines()
    {
        Machine mymach = (Machine)(machineCombo.getSelectedItem());
        
        DefaultComboBoxModel<Machine> model = new DefaultComboBoxModel<>();
        
        for(Machine mach : MainFrame.getMachineDatabase())
        {
            model.addElement(mach);
        }
        
        machineCombo.setModel(model);
        machineCombo.setSelectedItem(mymach);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane2;
    private abinitgui.projects.JobInfoPanel jobInfoPanel1;
    private javax.swing.JComboBox machineCombo;
    private javax.swing.JLabel machineLabel;
    // End of variables declaration//GEN-END:variables


    private void updateList(ArrayList<RemoteJob> remoteJobs) {
        
        DefaultListModel<RemoteJob> model = new DefaultListModel<>();
        
        if(remoteJobs != null)
        {
            for(RemoteJob rj : remoteJobs)
            {
                model.addElement(rj);
            }
        }
        
        jList1.setModel(model);
    }
}
