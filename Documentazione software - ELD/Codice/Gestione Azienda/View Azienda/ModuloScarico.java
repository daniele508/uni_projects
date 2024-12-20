import java.util.Collection;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author laura
 */
public class ModuloScarico extends javax.swing.JFrame {

    /**
     * Creates new form ModuloScarico
     */
    public ModuloScarico(Collection<Object> datiRiepilogativi, int idUtente, ControlScarico control) {
        this.datiRiepilogativi=datiRiepilogativi;
        this.idUtente=idUtente;
        this.controlScarico=control;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        pulsanteAnnulla = new javax.swing.JButton();
        pulsanteScarica = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        Object[] nomiColonne={"ID", "Nome", "Principio Attivo"};
        Object[][] matrice= new Object[1][nomiColonne.length];
        matrice[0]=datiRiepilogativi.toArray();

        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(matrice, nomiColonne);
        model.addColumn("Quantità");

        jTable1=new javax.swing.JTable(model);


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setViewportView(jTable1);

        pulsanteAnnulla.setBackground(new java.awt.Color(255, 0, 0));
        pulsanteAnnulla.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pulsanteAnnulla.setForeground(new java.awt.Color(255, 255, 255));
        pulsanteAnnulla.setText("Annulla");
        pulsanteAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulla(evt);
            }
        }); // NOI18N

        pulsanteScarica.setBackground(new java.awt.Color(0, 102, 0));
        pulsanteScarica.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pulsanteScarica.setForeground(new java.awt.Color(255, 255, 255));
        pulsanteScarica.setText("Scarica");
        pulsanteScarica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scarica(evt);
            }
        }); // NOI18N

        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home.png")));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                home(evt);
            }
        }); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Scarico");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(pulsanteAnnulla)
                        .addGap(175, 175, 175)
                        .addComponent(pulsanteScarica))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3)
                        .addGap(204, 204, 204)
                        .addComponent(jLabel1)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addComponent(jLabel1))
                .addGap(106, 106, 106)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pulsanteAnnulla)
                    .addComponent(pulsanteScarica))
                .addContainerGap(167, Short.MAX_VALUE))
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

    public void scarica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home
        this.compila();
        this.controlScarico.run();
        this.dispose();
    }//GEN-LAST:event_home

    public void compila(){
        this.controlScarico.setDati(this.getDati());
    }

    public String getDati(){
        return (String)this.jTable1.getValueAt(0,3);
    }

    private void home(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home
        MenuPrincipale menu=new MenuPrincipale(this.idUtente);
        this.dispose();
    }//GEN-LAST:event_home

    private void annulla(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home
        ControlVisualizza control;
        if(this.idUtente/1000==2){
            control= new ControlVisualizza(this.idUtente, "farmaci");
        }else{
            control= new ControlVisualizza(this.idUtente, "magazzino");
        }
        control.run();
        this.dispose();
    }//GEN-LAST:event_home

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton pulsanteAnnulla;
    private javax.swing.JButton pulsanteScarica;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private int idUtente;
    private ControlScarico controlScarico;
    private Collection<Object> datiRiepilogativi;
    // End of variables declaration//GEN-END:variables
}
