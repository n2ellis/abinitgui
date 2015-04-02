/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
 */
package abinitgui.projects;

import abinitgui.core.MainFrame;

public class SubmissionScriptFrame extends javax.swing.JFrame {

    private SubmissionScript script;
    
    public SubmissionScriptFrame() {
        initComponents();
        typeChooser.setSelectedItem("Frontend");
        parallelBox.setSelected(true);
        parallelBoxActionPerformed(null);
    }

    public void setScript(SubmissionScript script) {
        this.script = script;

        typeChooser.setSelectedItem(script.getSystem());
        mailField.setText(script.getEmail());
        memField.setText(script.getMemoryMax());
        nbProcsField.setText(script.getNbProcs());
        parallelBox.setSelected(script.isParallel());
        preProcessArea.setText(script.getPreProcessPart());
        postProcessArea.setText(script.getPostProcessPart());
        timeField.setText(script.getTimeLimit());
    }

    public SubmissionScript getScript() {
        return script;
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
        typeChooser = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nbProcsField = new javax.swing.JTextField();
        memField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        timeField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mailField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        parallelBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        preProcessArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        postProcessArea = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        validButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Submission script details");
        setResizable(false);

        typeChooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Frontend", "SGE", "SLURM" }));
        typeChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeChooserActionPerformed(evt);
            }
        });

        jLabel1.setText("Submission system :");

        jLabel2.setText("Number of processors :");

        nbProcsField.setText("1");

        memField.setText("2000");

        jLabel3.setText("Memory limit (MB) :");

        timeField.setText("23:59:59");

        jLabel4.setText("Time limit :");

        mailField.setText("yanikou19@gmail.com");

        jLabel5.setText("Email :");

        parallelBox.setText("Parallel");
        parallelBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parallelBoxActionPerformed(evt);
            }
        });

        preProcessArea.setColumns(20);
        preProcessArea.setRows(5);
        jScrollPane1.setViewportView(preProcessArea);

        jLabel6.setText("Pre-processing commands :");

        postProcessArea.setColumns(20);
        postProcessArea.setRows(5);
        jScrollPane2.setViewportView(postProcessArea);

        jLabel7.setText("Post-processing commands :");

        validButton.setText("Validate");
        validButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(typeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(parallelBox, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(memField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(nbProcsField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mailField, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(validButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nbProcsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(memField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parallelBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(validButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void typeChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeChooserActionPerformed
        // Save to script instance !
        int index = typeChooser.getSelectedIndex();
        if (index == -1) {
            MainFrame.printERR("Please choose a simulation submission type!");
        }
        String type = (String) typeChooser.getSelectedItem();

        if (type.equals("Frontend")) {
            EnableSubmitFields(false);
        } else {
            EnableSubmitFields(true);
        }
    }//GEN-LAST:event_typeChooserActionPerformed

    public void EnableSubmitFields(boolean isEnabled) {
        mailField.setEnabled(isEnabled);
        memField.setEnabled(isEnabled);
        timeField.setEnabled(isEnabled);
    }

    private void validButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validButtonActionPerformed
        // Save to script instance !
        int index = typeChooser.getSelectedIndex();
        if (index == -1) {
            MainFrame.printERR("Please choose a type!");
        }
        String type = (String) typeChooser.getSelectedItem();
        if (!type.equals(script.getSystem())) {
            // Rebuilt the script !
            switch (type) {
                case "SGE":
                    script = new SGEScript();
                    break;
                case "Frontend":
                    script = new FrontendScript();
                    break;
                case "SLURM":
                    script = new SLURMScript();
                    break;
                default:
                    MainFrame.printERR("The simulation submission type " +
                            type + " is not yet implemented.");
                    break;
            }
        }
        script.setEmail(mailField.getText());
        script.setMemoryMax(memField.getText());
        script.setNbProcs(nbProcsField.getText());
        script.setParallel(parallelBox.isSelected());
        script.setPreProcessPart(preProcessArea.getText());
        script.setPostProcessPart(postProcessArea.getText());
        script.setTimeLimit(timeField.getText());
        MainFrame.getSimulation().getRemoteJob().setScript(script);
        this.setVisible(false);
    }//GEN-LAST:event_validButtonActionPerformed

    private void parallelBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parallelBoxActionPerformed
        nbProcsField.setEnabled(parallelBox.isSelected());
    }//GEN-LAST:event_parallelBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField mailField;
    private javax.swing.JTextField memField;
    private javax.swing.JTextField nbProcsField;
    private javax.swing.JCheckBox parallelBox;
    private javax.swing.JTextArea postProcessArea;
    private javax.swing.JTextArea preProcessArea;
    private javax.swing.JTextField timeField;
    private javax.swing.JComboBox typeChooser;
    private javax.swing.JButton validButton;
    // End of variables declaration//GEN-END:variables
}
