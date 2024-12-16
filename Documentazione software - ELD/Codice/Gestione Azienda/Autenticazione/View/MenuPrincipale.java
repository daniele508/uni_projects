public class MenuPrincipale extends javax.swing.JFrame{

	private javax.swing.JLabel cognome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nome;
    private javax.swing.JButton pulsanteVisCon;
    private javax.swing.JButton pulsanteVisMag;
    private javax.swing.JButton pulsanteVisOrd;
    private javax.swing.JButton pulsanteVisSeg;
    private javax.swing.JButton pulsanteVisFar;
    private javax.swing.JButton pulsanteVisPre;
    private ControlVisualizza controlVisualizza;
    private ControlPrenota controlPrenota;
    //private ControlSegnala controlSegnala;
    private Utente utente;
    private int idUtente;

    public MenuPrincipale(int idUtente){
    	this.idUtente=idUtente;
    	initComponents();
    	this.setVisible(true);
    }

	public MenuPrincipale(Utente utente){
		this.utente=utente;
		initComponents();
		this.setVisible(true);

	}

	public void initComponents(){
		jPanel1 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nome = new javax.swing.JLabel();
        cognome = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Menu Principale");

        nome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nome.setForeground(new java.awt.Color(0, 153, 153));
        nome.setText("Nome");

        cognome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cognome.setForeground(new java.awt.Color(0, 153, 153));
        cognome.setText("Cognome");

        

        if(this.utente instanceof Corriere || (this.idUtente!=0 && this.idUtente/1000==0)){
        	Corriere corriere;
        	if(this.utente!=null){
        		corriere=(Corriere)this.utente;
        		this.nome.setText(corriere.getNome());
	        	this.cognome.setText(corriere.getCognome());
	        }else{
	        	this.nome.setText(String.valueOf(this.idUtente));
	        	this.cognome.setText("Corriere");
	        }

        	pulsanteVisCon = new javax.swing.JButton();

	        pulsanteVisCon.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
	        pulsanteVisCon.setText("Visualizza consegne");
	        pulsanteVisCon.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                visualizzaConsegne(evt);
	            }
	        });

	         javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
	        jPanel2.setLayout(jPanel2Layout);
	        jPanel2Layout.setHorizontalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addContainerGap(178, Short.MAX_VALUE)
	                .addComponent(jLabel1)
	                .addGap(86, 86, 86)
	                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(nome)
	                    .addComponent(cognome))
	                .addGap(31, 31, 31))
	        );
	        jPanel2Layout.setVerticalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(jPanel2Layout.createSequentialGroup()
	                        .addGap(18, 18, 18)
	                        .addComponent(nome)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(cognome))
	                    .addGroup(jPanel2Layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(jLabel1)))
	                .addContainerGap(30, Short.MAX_VALUE))
	        );

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(pulsanteVisCon)
	                .addGap(226, 226, 226))
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(162, 162, 162)
	                .addComponent(pulsanteVisCon)
	                .addContainerGap(200, Short.MAX_VALUE))
	        );

	        jPanel2.getAccessibleContext().setAccessibleParent(this);

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

        }else if(this.utente instanceof Impiegato || (this.idUtente!=0 && this.idUtente/1000==1)){
        	Impiegato impiegato;
        	if(this.utente!=null){
        		impiegato=(Impiegato)this.utente;
        		this.nome.setText(impiegato.getNome());
	        	this.cognome.setText(impiegato.getCognome());
	        }else{
	        	this.nome.setText(String.valueOf(this.idUtente));
	        	this.cognome.setText("Impiegato");
	        }


        	pulsanteVisMag = new javax.swing.JButton();
	        pulsanteVisOrd = new javax.swing.JButton();
	        pulsanteVisSeg = new javax.swing.JButton();

        	pulsanteVisMag.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
	        pulsanteVisMag.setText("Visualizza magazzino");
	        pulsanteVisMag.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                visualizzaMagazzino(evt);
	            }
	        });

	        pulsanteVisOrd.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
	        pulsanteVisOrd.setText("Visaulizza ordini");
	        pulsanteVisOrd.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                visualizzaOrdini(evt);
	            }
	        });

	        pulsanteVisSeg.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
	        pulsanteVisSeg.setText("Visualizza segnalazioni");
	        pulsanteVisSeg.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                visualizzaSegnalazioni(evt);
	            }
	        });

	        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
	        jPanel2.setLayout(jPanel2Layout);
	        jPanel2Layout.setHorizontalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(jLabel1)
	                .addGap(86, 86, 86)
	                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(nome)
	                    .addComponent(cognome))
	                .addGap(31, 31, 31))
	        );
	        jPanel2Layout.setVerticalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(jPanel2Layout.createSequentialGroup()
	                        .addGap(18, 18, 18)
	                        .addComponent(nome)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(cognome))
	                    .addGroup(jPanel2Layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(jLabel1)))
	                .addContainerGap(30, Short.MAX_VALUE))
	        );

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGap(213, 213, 213)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(pulsanteVisSeg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(pulsanteVisOrd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(pulsanteVisMag, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                .addContainerGap(217, Short.MAX_VALUE))
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addContainerGap())
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(96, 96, 96)
	                .addComponent(pulsanteVisMag)
	                .addGap(41, 41, 41)
	                .addComponent(pulsanteVisOrd)
	                .addGap(43, 43, 43)
	                .addComponent(pulsanteVisSeg)
	                .addContainerGap(106, Short.MAX_VALUE))
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

        }else if(this.utente instanceof Farmacista || (this.idUtente!=0 && this.idUtente/1000==2)){
        	Farmacista farmacista;
        	if(this.utente!=null){
        		farmacista=(Farmacista)this.utente;
        		this.nome.setText(farmacista.getNome());
	        	this.cognome.setText(farmacista.getCognome());
	        }else{
	        	this.nome.setText(String.valueOf(this.idUtente));
	        	this.cognome.setText("Farmacista");
	        }

        		

        	pulsanteVisFar = new javax.swing.JButton();
        	pulsanteVisPre = new javax.swing.JButton();

        	pulsanteVisFar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
	        pulsanteVisFar.setText("Visualizza farmaci");
	        pulsanteVisFar.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                visualizzaFarmaci(evt);
	            }
	        });

	        pulsanteVisPre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
	        pulsanteVisPre.setText("Visualizza prenotazioni");
	        pulsanteVisPre.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                visualizzaPrenotazioni(evt);
	            }
	        });

	        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
	        jPanel2.setLayout(jPanel2Layout);
	        jPanel2Layout.setHorizontalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(jLabel1)
	                .addGap(86, 86, 86)
	                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(nome)
	                    .addComponent(cognome))
	                .addGap(31, 31, 31))
	        );
	        jPanel2Layout.setVerticalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(jPanel2Layout.createSequentialGroup()
	                        .addGap(18, 18, 18)
	                        .addComponent(nome)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(cognome))
	                    .addGroup(jPanel2Layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(jLabel1)))
	                .addContainerGap(30, Short.MAX_VALUE))
	        );

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                .addContainerGap(215, Short.MAX_VALUE)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                    .addComponent(pulsanteVisPre)
	                    .addComponent(pulsanteVisFar, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addGap(213, 213, 213))
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addContainerGap())
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
	                .addComponent(pulsanteVisFar)
	                .addGap(52, 52, 52)
	                .addComponent(pulsanteVisPre)
	                .addGap(141, 141, 141))
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
        }
	}

	private void visualizzaMagazzino(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizzaMagazzino
		if(this.utente!=null){
        	this.controlVisualizza=new ControlVisualizza(this.utente.getID(), "magazzino");
		}else{
			this.controlVisualizza=new ControlVisualizza(this.idUtente, "magazzino");
		}
        this.controlVisualizza.run();
        this.dispose();
    }//GEN-LAST:event_visualizzaMagazzino

    private void visualizzaOrdini(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizzaOrdini
       	if(this.utente!=null){
        	this.controlVisualizza=new ControlVisualizza(this.utente.getID(), "ordini");
		}else{
			this.controlVisualizza=new ControlVisualizza(this.idUtente, "ordini");
		}
        this.controlVisualizza.run();
        this.dispose();
    }//GEN-LAST:event_visualizzaOrdini

    private void visualizzaSegnalazioni(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizzaSegnalazioni
        if(this.utente!=null){
        	this.controlVisualizza=new ControlVisualizza(this.utente.getID(), "segnalazioni");
		}else{
			this.controlVisualizza=new ControlVisualizza(this.idUtente, "segnalazioni");
		}
        this.controlVisualizza.run();
        this.dispose();
    }//GEN-LAST:event_visualizzaSegnalazioni

    private void visualizzaConsegne(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizzaMagazzino
        if(this.utente!=null){
        	this.controlVisualizza=new ControlVisualizza(this.utente.getID(), "consegne");
		}else{
			this.controlVisualizza=new ControlVisualizza(this.idUtente, "consegne");
		}
        this.controlVisualizza.run();
        this.dispose();
    }//GEN-LAST:event_visualizzaMagazzino

    private void visualizzaFarmaci(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizzaOrdini
        if(this.utente!=null){
        	this.controlVisualizza=new ControlVisualizza(this.utente.getID(), "farmaci");
		}else{
			this.controlVisualizza=new ControlVisualizza(this.idUtente, "farmaci");
		}
        this.controlVisualizza.run();
        this.dispose();
    }//GEN-LAST:event_visualizzaOrdini

    private void visualizzaPrenotazioni(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_visualizzaSegnalazioni
        if(this.utente!=null){
        	this.controlVisualizza=new ControlVisualizza(this.utente.getID(), "prenotazioni");
		}else{
			this.controlVisualizza=new ControlVisualizza(this.idUtente, "prenotazioni");
		}
        this.controlVisualizza.run();
        this.dispose();
    }//GEN-LAST:event_visualizzaSegnalazioni

    public void start(){
    	if(this.utente!=null){
        	this.controlPrenota=new ControlPrenota(this.utente.getID());
        	//this.controlSegnala=new ControlSegnala(this.utente.getID());
		}else{
			this.controlPrenota=new ControlPrenota(this.idUtente);
			//this.controlSegnala=new ControlSegnala(this.idUtente);
		}

    }
}