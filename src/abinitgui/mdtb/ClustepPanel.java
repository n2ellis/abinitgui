/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Universit� catholique de Louvain, Louvain-la-Neuve, Belgium
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions, and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions, and the disclaimer that follows
these conditions in the documentation and/or other materials
provided with the distribution.

3. The names of the author may not be used to endorse or promote
products derived from this software without specific prior written
permission.

In addition, we request (but do not require) that you include in the
end-user documentation provided with the redistribution and/or in the
software itself an acknowledgement equivalent to the following:
"This product includes software developed by the
Abinit Project (http://www.abinit.org/)."

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

For more information on the Abinit Project, please see
<http://www.abinit.org/>.
 */

package abinitgui.mdtb;

import abinitgui.core.MainFrame;
import abinitgui.core.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFileChooser;
import abinitgui.projects.Simulation;
import abinitgui.projects.SimulationPanel;

public class ClustepPanel extends SimulationPanel {
    
    public ClustepPanel() {
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

        openClustepInputFileLabel = new javax.swing.JLabel();
        displayClustepInputFileButton = new javax.swing.JButton();
        geditClustepInputButton = new javax.swing.JButton();
        openClustepInputFileDialogButton = new javax.swing.JButton();
        openClustepInputFileTextField = new javax.swing.JTextField();
        openClustepPositionFileLabel = new javax.swing.JLabel();
        displayClustepPositionFileButton = new javax.swing.JButton();
        geditClustepPositionButton = new javax.swing.JButton();
        openClustepPositionFileDialogButton = new javax.swing.JButton();
        openClustepPositionFileTextField = new javax.swing.JTextField();
        sendClustepCheckBox = new javax.swing.JCheckBox();

        openClustepInputFileLabel.setText("Open the Clustep input file");

        displayClustepInputFileButton.setText("Display");
        displayClustepInputFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayClustepInputFileButtonActionPerformed(evt);
            }
        });

        geditClustepInputButton.setText("Edit");
        geditClustepInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geditClustepInputButtonActionPerformed(evt);
            }
        });

        openClustepInputFileDialogButton.setText("...");
        openClustepInputFileDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openClustepInputFileDialogButtonActionPerformed(evt);
            }
        });

        openClustepPositionFileLabel.setText("Open the Clustep position file");

        displayClustepPositionFileButton.setText("Display");
        displayClustepPositionFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayClustepPositionFileButtonActionPerformed(evt);
            }
        });

        geditClustepPositionButton.setText("Edit");
        geditClustepPositionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geditClustepPositionButtonActionPerformed(evt);
            }
        });

        openClustepPositionFileDialogButton.setText("...");
        openClustepPositionFileDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openClustepPositionFileDialogButtonActionPerformed(evt);
            }
        });

        sendClustepCheckBox.setText("Send CLUSTEP source code and compile");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openClustepInputFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(displayClustepInputFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(geditClustepInputButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openClustepPositionFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(displayClustepPositionFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(geditClustepPositionButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openClustepInputFileDialogButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openClustepInputFileTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openClustepPositionFileDialogButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openClustepPositionFileTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendClustepCheckBox)
                        .addGap(0, 267, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepInputFileLabel)
                    .addComponent(geditClustepInputButton)
                    .addComponent(displayClustepInputFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepInputFileDialogButton)
                    .addComponent(openClustepInputFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepPositionFileLabel)
                    .addComponent(geditClustepPositionButton)
                    .addComponent(displayClustepPositionFileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepPositionFileDialogButton)
                    .addComponent(openClustepPositionFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99)
                .addComponent(sendClustepCheckBox)
                .addGap(188, 188, 188))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void displayClustepInputFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayClustepInputFileButtonActionPerformed
        String fileContent = "";
        try {
            fileContent = Utils.fileToString(openClustepInputFileTextField.getText());
        } catch (FileNotFoundException e) {
            MainFrame.printERR(e.getMessage());
        } catch (IOException e) {
            MainFrame.printERR(e.getMessage());
        }
        MainFrame.mainFrame.clustepInputFileDisplayer.setText(fileContent);
        MainFrame.mainFrame.clustepInputFileDisplayer.show();
    }//GEN-LAST:event_displayClustepInputFileButtonActionPerformed

    @Override
    public void fillFromSimu(Simulation currentSimu)
    {
        if(currentSimu instanceof ClustepSimulation)
        {
            ClustepSimulation simu = (ClustepSimulation)currentSimu;
            openClustepInputFileTextField.setText(simu.getInputFileName());
            openClustepPositionFileTextField.setText(simu.getPositionFileName());
            sendClustepCheckBox.setSelected(simu.isSendClustepSources());
        }
        else
        {
            throw new UnsupportedOperationException("Unable to tackle simulation.");
        }
    }
    
    @Override
    public void fillSimu(Simulation currentSimu)
    {
        if(currentSimu instanceof ClustepSimulation)
        {
            ClustepSimulation simu = (ClustepSimulation)currentSimu;
            simu.setInputFileName(openClustepInputFileTextField.getText());
            simu.setPositionFileName(openClustepPositionFileTextField.getText());
            simu.setSendClustepSources(sendClustepCheckBox.isSelected());
        }
        else
        {
            throw new UnsupportedOperationException("Unable to tackle simulation.");
        }
    }
    
    private void geditClustepInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geditClustepInputButtonActionPerformed

        String fileName = openClustepInputFileTextField.getText();

        MainFrame.editFile(fileName, true);
    }//GEN-LAST:event_geditClustepInputButtonActionPerformed

    private void openClustepInputFileDialogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openClustepInputFileDialogButtonActionPerformed
        JFileChooser fc = new JFileChooser(".");
        File currDir = new File(".");
        String currPath = currDir.getAbsolutePath();
        String basePath = currPath.replace("\\", "/").replace(".", "");
            MainFrame.printDEB(basePath);
            fc.setMultiSelectionEnabled(false);

            int retValue = fc.showOpenDialog(this);
            if (retValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String relPath = file.getAbsolutePath().replace("\\", "/").replace(basePath, "./");
                    openClustepInputFileTextField.setText(relPath);
                }
    }//GEN-LAST:event_openClustepInputFileDialogButtonActionPerformed

    private void displayClustepPositionFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayClustepPositionFileButtonActionPerformed
        String fileContent = "";
        try {
            fileContent = Utils.fileToString(openClustepPositionFileTextField.getText());
        } catch (FileNotFoundException e) {
            MainFrame.printERR(e.getMessage());
        } catch (IOException e) {
            MainFrame.printERR(e.getMessage());
        }
        MainFrame.mainFrame.clustepPositionFileDisplayer.setText(fileContent);
        MainFrame.mainFrame.clustepPositionFileDisplayer.show();
    }//GEN-LAST:event_displayClustepPositionFileButtonActionPerformed

    private void geditClustepPositionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geditClustepPositionButtonActionPerformed

        String fileName = openClustepPositionFileTextField.getText();

        MainFrame.editFile(fileName, true);
    }//GEN-LAST:event_geditClustepPositionButtonActionPerformed

    private void openClustepPositionFileDialogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openClustepPositionFileDialogButtonActionPerformed
        JFileChooser fc = new JFileChooser(".");
        File currDir = new File(".");
        String currPath = currDir.getAbsolutePath();
        String basePath = currPath.replace("\\", "/").replace(".", "");
            MainFrame.printDEB(basePath);
            fc.setMultiSelectionEnabled(false);

            int retValue = fc.showOpenDialog(this);
            if (retValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String relPath = file.getAbsolutePath().replace("\\", "/").replace(basePath, "./");
                    openClustepPositionFileTextField.setText(relPath);
                }
    }//GEN-LAST:event_openClustepPositionFileDialogButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton displayClustepInputFileButton;
    private javax.swing.JButton displayClustepPositionFileButton;
    private javax.swing.JButton geditClustepInputButton;
    private javax.swing.JButton geditClustepPositionButton;
    private javax.swing.JButton openClustepInputFileDialogButton;
    private javax.swing.JLabel openClustepInputFileLabel;
    public javax.swing.JTextField openClustepInputFileTextField;
    private javax.swing.JButton openClustepPositionFileDialogButton;
    private javax.swing.JLabel openClustepPositionFileLabel;
    public javax.swing.JTextField openClustepPositionFileTextField;
    private javax.swing.JCheckBox sendClustepCheckBox;
    // End of variables declaration//GEN-END:variables
}