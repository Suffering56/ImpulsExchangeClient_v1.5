package impulsexchangeclient;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class FrameMain extends javax.swing.JFrame {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addOrderBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jOrdersList = new javax.swing.JList();
        removeOrderBtn = new javax.swing.JButton();
        toExportBtn = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        mainMenu = new javax.swing.JMenu();
        optionsCallMenuBtn = new javax.swing.JMenuItem();
        exitMenuBtn = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        archiveCallMenuBtn = new javax.swing.JMenuItem();
        doSearchMenuBtn = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        getFAQMenuBtn = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusable(false);
        setName("mainFrame"); // NOI18N
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        addOrderBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addOrderBtn.setText("Добавить новый заказ");
        addOrderBtn.setFocusPainted(false);
        addOrderBtn.setPreferredSize(new java.awt.Dimension(151, 21));
        addOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOrderBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Заказы:");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setFocusable(false);

        jOrdersList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jOrdersList.setModel(sentOrdersModel);
        jOrdersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jOrdersList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jOrdersListKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jOrdersList);

        removeOrderBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        removeOrderBtn.setText("Убрать выделенный заказ");
        removeOrderBtn.setFocusPainted(false);
        removeOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeOrderBtnActionPerformed(evt);
            }
        });

        toExportBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        toExportBtn.setText("Отправить на сервер");
        toExportBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        toExportBtn.setFocusPainted(false);
        toExportBtn.setPreferredSize(new java.awt.Dimension(151, 23));
        toExportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toExportBtnActionPerformed(evt);
            }
        });

        progressBar.setToolTipText("");
        progressBar.setFocusable(false);
        progressBar.setStringPainted(true);

        mainMenu.setText("Главное меню");

        optionsCallMenuBtn.setText("Настройки");
        optionsCallMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsCallMenuBtnActionPerformed(evt);
            }
        });
        mainMenu.add(optionsCallMenuBtn);

        exitMenuBtn.setText("Выход");
        exitMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuBtnActionPerformed(evt);
            }
        });
        mainMenu.add(exitMenuBtn);

        jMenuBar1.add(mainMenu);

        jMenu1.setText("Архив");

        archiveCallMenuBtn.setText("Последние 25 заказов");
        archiveCallMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveCallMenuBtnActionPerformed(evt);
            }
        });
        jMenu1.add(archiveCallMenuBtn);

        doSearchMenuBtn.setText("Поиск по заказам");
        doSearchMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doSearchMenuBtnActionPerformed(evt);
            }
        });
        jMenu1.add(doSearchMenuBtn);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Справка");

        getFAQMenuBtn.setText("Вызов справки");
        getFAQMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFAQMenuBtnActionPerformed(evt);
            }
        });
        jMenu3.add(getFAQMenuBtn);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(removeOrderBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(addOrderBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(toExportBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jScrollPane2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addOrderBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addComponent(removeOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(toExportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public FrameMain() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void addOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrderBtnActionPerformed
        new FrameNewOrder(this).setVisible(true);
    }//GEN-LAST:event_addOrderBtnActionPerformed

    private void removeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeOrderBtnActionPerformed
        if (jOrdersList.getSelectedIndex() != -1) {
            sentOrdersModel.remove(jOrdersList.getSelectedIndex());
        }
    }//GEN-LAST:event_removeOrderBtnActionPerformed

    private void toExportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toExportBtnActionPerformed
        if (!sentOrdersModel.isEmpty()) {
            DataExportLauncher launcher = new DataExportLauncher(progressBar, sentOrdersModel);
            launcher.runExport();
        } else {
            JOptionPane.showMessageDialog(null, "Вы не добавили в список ни одного заказа!");
        }
    }//GEN-LAST:event_toExportBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.setTitle("Отдел № " + Options.getDepartmentName());
    }//GEN-LAST:event_formWindowGainedFocus

    private void jOrdersListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jOrdersListKeyPressed
        if ((evt.getKeyCode() == 127) || (evt.getKeyCode() == 110)) {
            removeOrderBtn.doClick();
        }
    }//GEN-LAST:event_jOrdersListKeyPressed

    private void optionsCallMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsCallMenuBtnActionPerformed
        new FrameOptions(this).setVisible(true);
    }//GEN-LAST:event_optionsCallMenuBtnActionPerformed

    private void archiveCallMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveCallMenuBtnActionPerformed
        new FrameArchive().setVisible(true);
    }//GEN-LAST:event_archiveCallMenuBtnActionPerformed

    private void doSearchMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doSearchMenuBtnActionPerformed
        new FrameSearch().setVisible(true);
    }//GEN-LAST:event_doSearchMenuBtnActionPerformed

    private void getFAQMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getFAQMenuBtnActionPerformed
        new FrameHelp().setVisible(true);
    }//GEN-LAST:event_getFAQMenuBtnActionPerformed

    private void exitMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuBtnActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuBtnActionPerformed

    public DefaultListModel getSentOrdersModel() {
        return sentOrdersModel;
    }

    private final DefaultListModel sentOrdersModel = new DefaultListModel();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrderBtn;
    private javax.swing.JMenuItem archiveCallMenuBtn;
    private javax.swing.JMenuItem doSearchMenuBtn;
    private javax.swing.JMenuItem exitMenuBtn;
    private javax.swing.JMenuItem getFAQMenuBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JList jOrdersList;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JMenuItem optionsCallMenuBtn;
    public javax.swing.JProgressBar progressBar;
    private javax.swing.JButton removeOrderBtn;
    private javax.swing.JButton toExportBtn;
    // End of variables declaration//GEN-END:variables
}
