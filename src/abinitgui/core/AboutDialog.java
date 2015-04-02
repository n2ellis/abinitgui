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

package abinitgui.core;

public class AboutDialog extends javax.swing.JDialog {

    /** Creates new form AboutDialog */
    public AboutDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        versionLabel.setText("Version: " + MainFrame.Version + "." +
                MainFrame.SubVersion + " (" + MainFrame.VerMonth +
                " " + MainFrame.VerYear + ")");
        osLabel.setText("Operating System: " + Utils.osName() +
                " (" + Utils.osArch() + ")");
        javaVersionLabel.setText("Java version number: " + Utils.javaVersion());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        abinitBannerLabel = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();
        authorLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        osLabel = new javax.swing.JLabel();
        abinitVersionLabel = new javax.swing.JLabel();
        javaVersionLabel = new javax.swing.JLabel();
        emailLabel1 = new javax.swing.JLabel();
        authorLabel1 = new javax.swing.JLabel();
        authorLabel2 = new javax.swing.JLabel();
        authorLabel3 = new javax.swing.JLabel();
        emailLabel2 = new javax.swing.JLabel();

        setTitle("About");
        setResizable(false);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setPreferredSize(new java.awt.Dimension(371, 480));

        abinitBannerLabel.setFont(new java.awt.Font("Arial", 1, 70)); // NOI18N
        abinitBannerLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abinit_logo_gui_final.png"))); // NOI18N

        versionLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        versionLabel.setText("Version: ??");

        authorLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        authorLabel.setText("Creator: Flavio ABREU ARAUJO");

        emailLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        emailLabel.setText("email: abinitgui@gmail.com");

        osLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        osLabel.setText("Operating System: ??");

        abinitVersionLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        abinitVersionLabel.setText("Designed for ABINIT version: 7+");

        javaVersionLabel.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        javaVersionLabel.setText("Java version number: ??");

        emailLabel1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        emailLabel1.setForeground(new java.awt.Color(246, 10, 13));
        emailLabel1.setText("<HTML><b>This product comes without any warranty. The potential damages are entirely the responsibility of the user.</b></HTML>");
        emailLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        emailLabel1.setFocusable(false);
        emailLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        emailLabel1.setInheritsPopupMenu(false);
        emailLabel1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        authorLabel1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        authorLabel1.setText("Developers:");

        authorLabel2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        authorLabel2.setText("Flavio ABREU ARAUJO (July 2009)");

        authorLabel3.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        authorLabel3.setText("Yannick GILLET (August 2012)");

        emailLabel2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        emailLabel2.setText("website: http://gui.abinit.org");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailLabel1, 0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(versionLabel)
                            .addComponent(abinitVersionLabel)
                            .addComponent(osLabel)
                            .addComponent(javaVersionLabel)
                            .addComponent(authorLabel)
                            .addComponent(emailLabel)
                            .addComponent(authorLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(authorLabel3)
                                    .addComponent(authorLabel2)))
                            .addComponent(emailLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(abinitBannerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(abinitBannerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(versionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abinitVersionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(osLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(javaVersionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(authorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(authorLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(authorLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(authorLabel3)
                .addGap(18, 18, 18)
                .addComponent(emailLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(emailLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel abinitBannerLabel;
    private javax.swing.JLabel abinitVersionLabel;
    private javax.swing.JLabel authorLabel;
    private javax.swing.JLabel authorLabel1;
    private javax.swing.JLabel authorLabel2;
    private javax.swing.JLabel authorLabel3;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailLabel1;
    private javax.swing.JLabel emailLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel javaVersionLabel;
    private javax.swing.JLabel osLabel;
    private javax.swing.JLabel versionLabel;
    // End of variables declaration//GEN-END:variables

}
