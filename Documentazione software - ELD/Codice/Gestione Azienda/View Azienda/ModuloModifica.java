import java.util.Collection;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author laura
 */
public class ModuloModifica extends javax.swing.JFrame {

    /**
     * Creates new form ModuloScarico
     */
    public ModuloModifica(Collection<Object> row, int iUtente, ControlModifica controlModifica) {
        this.idUtente=idUtente;
        this.datiRiepilogativi=row;
        this.controlModifica=controlModifica;
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
        pulsanteModifica = new javax.swing.JButton();
        pulsanteHome = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        Object[] nomiColonne1=new Object[5];
        Object[] nomiColonne2=new Object[4];
        Object matrice1[][]=new Object[1][nomiColonne1.length];
        Object matrice2[][]=new Object[1][nomiColonne2.length];
        if(this.idUtente/1000==1){
            nomiColonne1[0]="ID";
            nomiColonne1[1]="ID Farmacia";
            nomiColonne1[2]="Nome";
            nomiColonne1[3]="Quantità";
            nomiColonne1[4]="Data Consegna";
            matrice1[0]=datiRiepilogativi.toArray();
            jTable1=new javax.swing.JTable(matrice1, nomiColonne1);
        }else{
            nomiColonne2[0]="ID";
            nomiColonne2[1]="Nome";
            nomiColonne2[2]="Quantità";
            nomiColonne2[3]="Data Consegna";
            matrice2[0]=datiRiepilogativi.toArray();
            jTable1=new javax.swing.JTable(matrice2, nomiColonne2);
        }

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
        });

        pulsanteModifica.setBackground(new java.awt.Color(0, 102, 0));
        pulsanteModifica.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pulsanteModifica.setForeground(new java.awt.Color(255, 255, 255));
        pulsanteModifica.setText("Modifica");
        pulsanteModifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifica(evt);
            }
        });

        pulsanteHome.setForeground(new java.awt.Color(255, 255, 255));
        pulsanteHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/home.png"))); // NOI18N
        pulsanteHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                home(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Modifica");

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
                        .addComponent(pulsanteModifica))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pulsanteHome)
                        .addGap(204, 204, 204)
                        .addComponent(jLabel1)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pulsanteHome)
                    .addComponent(jLabel1))
                .addGap(106, 106, 106)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pulsanteAnnulla)
                    .addComponent(pulsanteModifica))
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

    public void compila(){
        this.controlModifica.setDati(this.getDati());
    }

    public String getDati(){
        if(this.idUtente/1000==1){
            return (String)this.jTable1.getValueAt(0,3);
        }else{
            return (String)this.jTable1.getValueAt(0,4);
        }
    }

    private void home(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home
        MenuPrincipale menu=new MenuPrincipale(this.idUtente);
        this.dispose();
    }//GEN-LAST:event_home

    public void annulla(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home
        ControlVisualizza control1;
        ControlVisualizza control2;
        if(this.idUtente/1000==2){
            control1= new ControlVisualizza(this.idUtente, "prenotazioni");
            control1.run();
        }else{
            control2= new ControlVisualizza(this.idUtente, "ordini");
            control2.run();
        }
        this.dispose();
    }//GEN-LAST:event_home

    public void modifica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home
        this.compila();
        this.controlModifica.riepilogo();
        this.dispose();
    }//GEN-LAST:event_home

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton pulsanteAnnulla;
    private javax.swing.JButton pulsanteModifica;
    private javax.swing.JButton pulsanteHome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private ControlModifica controlModifica;
    private int idUtente;
    private Collection<Object> datiRiepilogativi;
    // End of variables declaration//GEN-END:variables
}
