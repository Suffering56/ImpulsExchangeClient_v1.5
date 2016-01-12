package impulsexchangeclient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame(Options options) {
        this.options = options;
        initComponents();
        setLocationRelativeTo(null);                                            //позиционирование по центру экрана     
        setTitle("Отдел № " + options.getDepartmentNumber());
        ordersList.setModel(dm);                                                //устанавливаем значение по умолчанию для списка заказов
        departmentLabel.setText(options.getDepartmentNumber() + "/");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addOrderBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ordersList = new javax.swing.JList();
        removeOrderBtn = new javax.swing.JButton();
        toExportBtn = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        orderNumber = new javax.swing.JTextField();
        departmentLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mainMenu = new javax.swing.JMenu();
        optionsCallBtn = new javax.swing.JMenuItem();
        exitBtn = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Обмен");
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

        addOrderBtn.setText("Добавить заказ в список");
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Введите № заказа:");
        jLabel3.setFocusable(false);

        ordersList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ordersList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        ordersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ordersList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ordersListKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(ordersList);

        removeOrderBtn.setText("Убрать заказ из списка");
        removeOrderBtn.setFocusPainted(false);
        removeOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeOrderBtnActionPerformed(evt);
            }
        });

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

        orderNumber.setMaximumSize(new java.awt.Dimension(99999, 20));
        orderNumber.setPreferredSize(null);
        orderNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                orderNumberKeyPressed(evt);
            }
        });

        departmentLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        departmentLabel.setText("112/");
        departmentLabel.setFocusable(false);
        departmentLabel.setMaximumSize(new java.awt.Dimension(99999, 17));
        departmentLabel.setMinimumSize(new java.awt.Dimension(0, 17));
        departmentLabel.setName(""); // NOI18N
        departmentLabel.setPreferredSize(null);

        mainMenu.setText("Главное меню");

        optionsCallBtn.setText("Настройки");
        optionsCallBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsCallBtnActionPerformed(evt);
            }
        });
        mainMenu.add(optionsCallBtn);

        exitBtn.setText("Выход");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });
        mainMenu.add(exitBtn);

        jMenuBar1.add(mainMenu);

        jMenu1.setText("Архив");

        jMenuItem2.setText("Архив (последние 25 заказов)");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Поиск по заказам");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Справка");

        jMenuItem1.setText("Вызов справки");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(removeOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toExportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(departmentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(orderNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jScrollPane2});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(orderNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(departmentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removeOrderBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(toExportBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {departmentLabel, orderNumber});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrderBtnActionPerformed
        String nz = orderNumber.getText().trim();
        nz = options.getDepartmentNumber() + "/" + nz;
        Pattern p = Pattern.compile(options.getDepartmentNumber() + "/\\d+");
        Matcher m = p.matcher(nz);
        if (m.matches()) {                                      //проверка на корректность заказа (только цифры - не менее одной)
            if (!dm.contains(nz)) {                             //проверка на дублирование номера заказа
                dm.addElement(nz);                              //добавить заказ в список
            } else {
                JOptionPane.showMessageDialog(null, "Заказ №" + nz + " уже есть в списке!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Некорректный номер заказа!");
        }
        orderNumber.setText(null);
    }//GEN-LAST:event_addOrderBtnActionPerformed

    private void removeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeOrderBtnActionPerformed
        if (ordersList.getSelectedIndex() != -1) {                              //Если заказ выбран
            dm.remove(ordersList.getSelectedIndex());                           //Удалить из списка
        }
    }//GEN-LAST:event_removeOrderBtnActionPerformed

    private void toExportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toExportBtnActionPerformed
        if (!dm.isEmpty()) {
            progressBar.setValue(0);
            progressBar.setVisible(true);                                          //Включение прогресс бара
            try {
                new DataExport(progressBar, dm, options).start();                  //Запуск второго потока для отправки файла на FTP
            } catch (Exception ex) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Вы не добавили в список ни одного заказа!");
        }
    }//GEN-LAST:event_toExportBtnActionPerformed

    private void optionsCallBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsCallBtnActionPerformed
        OptionsFrame optFrame = new OptionsFrame(options);
        optFrame.setVisible(true);
    }//GEN-LAST:event_optionsCallBtnActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.setTitle("Отдел № " + options.getDepartmentNumber());
        departmentLabel.setText(options.getDepartmentNumber() + "/");
    }//GEN-LAST:event_formWindowGainedFocus

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            ArchiveFrame archiveFrame = new ArchiveFrame();
            archiveFrame.setVisible(true);
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        SearchFrame searchFrame = new SearchFrame();
        searchFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void orderNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orderNumberKeyPressed
        if  (evt.getKeyCode() == 10) {
            addOrderBtn.doClick();
        }
    }//GEN-LAST:event_orderNumberKeyPressed

    private void ordersListKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ordersListKeyPressed
        if  ((evt.getKeyCode() == 127) || (evt.getKeyCode() == 110)) {
            removeOrderBtn.doClick();
        }
    }//GEN-LAST:event_ordersListKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        HelpFrame helpFrame = new HelpFrame();
        helpFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    
    private final DefaultListModel dm = new DefaultListModel();
    private final Options options;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrderBtn;
    private javax.swing.JLabel departmentLabel;
    private javax.swing.JMenuItem exitBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JMenuItem optionsCallBtn;
    private javax.swing.JTextField orderNumber;
    private javax.swing.JList ordersList;
    public javax.swing.JProgressBar progressBar;
    private javax.swing.JButton removeOrderBtn;
    private javax.swing.JButton toExportBtn;
    // End of variables declaration//GEN-END:variables
}
