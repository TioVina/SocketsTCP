package servidortcp;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import servidortcp.ServidorTCP;

public class JframeClienteTCP extends javax.swing.JFrame {

    Socket conexao;
    int numeroSocket = 2230;

    private void digitando(String mensagem) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                
                try {
                    
                    jtMensagemCliente.setEnabled(false);
                    
                    jbEnviarCliente.setEnabled(false);
                    
                    jlDigitando.setText("Robô está digitando...");
                    
                    Thread.sleep(3000);
                    
                    jtMensagemCliente.setEnabled(true);
                    
                    jbEnviarCliente.setEnabled(true);
                    
                    jtaServidor.setText(mensagem);
                    
                    jlDigitando.setText("");
                    
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void criarDiretórioRecebimentoJSON() {
        
        File diretorio = new File(System.getProperty("user.home") + "\\Documents\\RecebimentoJSON");
        
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
    }

    public void conectaSocketServidor() {
        
        try {
            
            conexao = new Socket("localhost", numeroSocket);
            jlEstadoConexao.setText("Conectado");
            jlEstadoConexao.setForeground(Color.green);
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, 
                    "Servidor Offline. Tente ligá-lo.", 
                    ex.toString(), 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void recebeDadosServidor() throws FileNotFoundException,
            IOException, ClassNotFoundException {

        System.out.println("Entrou na funcao recebeDadosServidor");
        long start = System.currentTimeMillis();
        System.out.println(start);
        int bytesLidos;
        int byteAtual = 0;

        byte[] arrayArquivo = new byte[4096];

        InputStream inputStream = conexao.getInputStream();

        criarDiretórioRecebimentoJSON();

        FileOutputStream fileOutStream = new FileOutputStream(System.getProperty("user.home")
                + "\\Documents\\RecebimentoJSON\\RecebimentoJSON.json");

        BufferedOutputStream bufferedOutStream = new BufferedOutputStream(fileOutStream);

        bytesLidos = inputStream.read(arrayArquivo, 0, arrayArquivo.length);

        byteAtual = bytesLidos;
        System.out.println("byteAtual = bytesLidos = " + byteAtual);

        while (bytesLidos > -1) {
            bytesLidos = inputStream.read(arrayArquivo, byteAtual, (arrayArquivo.length - byteAtual));
            System.out.println(bytesLidos);
            if (bytesLidos >= 0) {
                byteAtual = byteAtual + bytesLidos;
            }
        }

        System.out.println("Escrevendo arquivo...");
        
        bufferedOutStream.write(arrayArquivo, 0, byteAtual);
        
        long end = System.currentTimeMillis();
        
        System.out.println("Começo - fim: " + (end - start));
        
        bufferedOutStream.close();
        
        BufferedReader leitor = new BufferedReader(new FileReader(System.getProperty("user.home")
                + "\\Documents\\RecebimentoJSON\\RecebimentoJSON.json"));
        
        digitando(jtaServidor.getText() + "\n" + "Retorno do servidor:\n"
                + leitor.readLine() + "\n");
    }

    ServidorTCP servidor = new ServidorTCP();

    public JframeClienteTCP() {
        initComponents();
        setLocationRelativeTo(this);
        jLabel1.requestFocus();
        digitando("\t               "
                + "Bem-vindo!\n\nClique em conectar para fazer requisições ao servidor TCP.\n\n");
        getContentPane().setBackground(Color.white);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtMensagemCliente = new javax.swing.JTextField();
        jbEnviarCliente = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaServidor = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlDigitando = new javax.swing.JLabel();
        jbConectar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jlEstadoConexao = new javax.swing.JLabel();
        jbDesconectar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setResizable(false);

        jtMensagemCliente.setText("Digite seu comando aqui...");
        jtMensagemCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtMensagemClienteFocusGained(evt);
            }
        });
        jtMensagemCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtMensagemClienteActionPerformed(evt);
            }
        });
        jtMensagemCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtMensagemClienteKeyPressed(evt);
            }
        });

        jbEnviarCliente.setText("Enviar");
        jbEnviarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEnviarClienteActionPerformed(evt);
            }
        });
        jbEnviarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbEnviarClienteKeyPressed(evt);
            }
        });

        jLabel2.setText("Robô (Servidor)");

        jtaServidor.setEditable(false);
        jtaServidor.setColumns(20);
        jtaServidor.setRows(5);
        jScrollPane1.setViewportView(jtaServidor);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Foto_Robson.jpg"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/robo.jpg"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Robô IFSC - Requisições em TCP");

        jLabel5.setText("Robson");

        jbConectar.setText("Conectar");
        jbConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConectarActionPerformed(evt);
            }
        });

        jLabel6.setText("Estado da conexão:");

        jlEstadoConexao.setForeground(new java.awt.Color(255, 0, 0));
        jlEstadoConexao.setText("Desconectado");

        jbDesconectar.setText("Desconectar");
        jbDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDesconectarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(151, 151, 151))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jbConectar)
                                            .addComponent(jLabel6))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jbDesconectar)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jlEstadoConexao)))
                                        .addGap(93, 93, 93))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jbEnviarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(151, 151, 151))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlDigitando, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtMensagemCliente)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbConectar, jbDesconectar, jbEnviarCliente});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jlDigitando, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jtMensagemCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbEnviarCliente)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbDesconectar)
                            .addComponent(jbConectar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jlEstadoConexao))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbEnviarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnviarClienteActionPerformed
       
        if (jlEstadoConexao.getText().equals("Desconectado")) {
            JOptionPane.showMessageDialog(this, "Você está desconectado. Por favor, conecte-se!",
                    "Você está desconectado!", JOptionPane.ERROR_MESSAGE);
            
        } else {

            if (!jtMensagemCliente.getText().equals("get_date")
                    && !jtMensagemCliente.getText().equals("get_time")) {
                digitando(jtaServidor.getText() + "\nInvalid Command!!!");

            } else {

                try {

                    ObjectOutputStream envio = new ObjectOutputStream(conexao.getOutputStream());
                    envio.writeObject(jtMensagemCliente.getText());
                    recebeDadosServidor();
                    conectaSocketServidor();

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_jbEnviarClienteActionPerformed

    private void jbEnviarClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbEnviarClienteKeyPressed
    }//GEN-LAST:event_jbEnviarClienteKeyPressed

    private void jtMensagemClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtMensagemClienteKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            jbEnviarCliente.doClick();
        }
    }//GEN-LAST:event_jtMensagemClienteKeyPressed

    private void jtMensagemClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtMensagemClienteActionPerformed

    }//GEN-LAST:event_jtMensagemClienteActionPerformed

    private void jtMensagemClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtMensagemClienteFocusGained
        jtMensagemCliente.setText("");
    }//GEN-LAST:event_jtMensagemClienteFocusGained

    private void jbConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConectarActionPerformed
        if (jlEstadoConexao.getText().equals("Conectado")) {
            JOptionPane.showMessageDialog(this, "Você já está conectado!",
                    "Você já está conectado!", JOptionPane.ERROR_MESSAGE);
        } else {
            conectaSocketServidor();
        }
    }//GEN-LAST:event_jbConectarActionPerformed

    private void jbDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDesconectarActionPerformed

        if (jlEstadoConexao.getText().equals("Desconectado")) {
            JOptionPane.showMessageDialog(this, "Você já está desconectado.",
                    "Você está desconectado!", JOptionPane.ERROR_MESSAGE);
        } else {

            try {

                ObjectOutputStream envio = new ObjectOutputStream(conexao.getOutputStream());
                envio.writeObject("Pls Create New Socket");
                numeroSocket = numeroSocket + 1;
                conexao.close();
                jlEstadoConexao.setText("Desconectado");
                jlEstadoConexao.setForeground(Color.red);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jbDesconectarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JframeClienteTCP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JframeClienteTCP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JframeClienteTCP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JframeClienteTCP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JframeClienteTCP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbConectar;
    private javax.swing.JButton jbDesconectar;
    private javax.swing.JButton jbEnviarCliente;
    private javax.swing.JLabel jlDigitando;
    private javax.swing.JLabel jlEstadoConexao;
    private javax.swing.JTextField jtMensagemCliente;
    private javax.swing.JTextArea jtaServidor;
    // End of variables declaration//GEN-END:variables
}
