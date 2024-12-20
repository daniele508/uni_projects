import java.util.Collection;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Utente
 */
public class ModuloPrenotazione extends javax.swing.JFrame {

    /**
     * Creates new form ModuloPrenotazione
     */
    public ModuloPrenotazione( int idUtente, Collection<Object> datiRiepilogativi, ControlPrenota controlPrenota) {
        this.idUtente=idUtente;
        this.datiRiepilogativi=datiRiepilogativi;
        this.controlPrenota= controlPrenota;
        initComponents();
        this.setVisible(true);
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

        Object[] nomiColonne= {"ID", "Nome", "Principio Attivo"};
        Object[][] matrice = new Object[1][nomiColonne.length];
        matrice[0]=datiRiepilogativi.toArray();

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(matrice, nomiColonne);
        model.addColumn("Quantità");
        prenotazione = new javax.swing.JTable(model);


        pulsanteAnnulla = new javax.swing.JButton();
        pulsantePrenota = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Prenotazione");
        jLabel1.setForeground(new java.awt.Color(0,153,153));

        prenotazione.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(prenotazione);

        pulsanteAnnulla.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pulsanteAnnulla.setText("ANNULLA");
        pulsanteAnnulla.setBackground(new java.awt.Color(255,0,0));
        pulsanteAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulla(evt);
            }
        });

        pulsantePrenota.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pulsantePrenota.setText("PRENOTA");
        pulsantePrenota.setBackground(new java.awt.Color(0,102,0));
        pulsantePrenota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenota(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(pulsanteAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                .addComponent(pulsantePrenota, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(146, 146, 146))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pulsanteAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pulsantePrenota, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(243, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void annulla(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pulsanteAnnullaActionPerformed
        ControlVisualizza controlVisualizza= new ControlVisualizza(this.idUtente,"farmaci");
        controlVisualizza.run();
        this.dispose();
    }//GEN-LAST:event_pulsanteAnnullaActionPerformed

    private void prenota(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pulsantePrenotaActionPerformed
     this.compila();
     this.controlPrenota.scadenza();
     this.dispose();
     
    }//GEN-LAST:event_pulsantePrenotaActionPerformed

    private String getDati(){
        return (String)this.prenotazione.getValueAt(0,3);
    }
    
    private void compila(){
        this.controlPrenota.setDati(this.getDati());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private ControlPrenota controlPrenota;
    private int idUtente;
    private Collection<Object> datiRiepilogativi;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable prenotazione;
    private javax.swing.JButton pulsanteAnnulla;
    private javax.swing.JButton pulsantePrenota;
    // End of variables declaration//GEN-END:variables
}
