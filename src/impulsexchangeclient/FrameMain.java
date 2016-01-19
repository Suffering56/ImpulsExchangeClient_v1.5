package impulsexchangeclient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class FrameMain extends javax.swing.JFrame {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addOrderBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jOrdersList = new javax.swing.JList();
        removeOrderBtn = new javax.swing.JButton();
        toExportBtn = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        orderNumber = new javax.swing.JTextField();
        departmentLabel = new javax.swing.JLabel();
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

        jOrdersList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jOrdersList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jOrdersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jOrdersList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jOrdersListKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jOrdersList);

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

        orderNumber.setText("2397");
        orderNumber.setMaximumSize(new java.awt.Dimension(99999, 20));
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

        archiveCallMenuBtn.setText("Архив (последние 25 заказов)");
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

    public FrameMain() {
        initComponents();
        setLocationRelativeTo(null);                                            //позиционирование по центру экрана     
        setTitle("Отдел № " + Options.getDepartmentName());
        jOrdersList.setModel(sentOrdersList);                                   //устанавливаем значение по умолчанию для списка заказов
        departmentLabel.setText(Options.getDepartmentName() + "/");
    }

    private void addOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOrderBtnActionPerformed
        String orderName = orderNumber.getText().trim();
        String fullOrderName = Options.getDepartmentName() + "/" + orderName;
        Pattern p = Pattern.compile(Options.getDepartmentName() + "/\\d+");
        Matcher m = p.matcher(fullOrderName);
        if (m.matches()) {                                                      //проверка на корректность заказа (только цифры - не менее одной)
            if (!sentOrdersList.contains(fullOrderName)) {                      //проверка на дублирование номера заказа
                if (getFirebirdData(orderName)) {
                    sentOrdersList.addElement(fullOrderName);                   //добавить заказ в список
                }
            } else {
                JOptionPane.showMessageDialog(null, "Заказ №" + fullOrderName + " уже есть в списке!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Некорректный номер заказа!");
        }
        orderNumber.setText(null);
    }//GEN-LAST:event_addOrderBtnActionPerformed

    private boolean getFirebirdData(String orderName) {
        FirebirdDataLoader loader = new FirebirdDataLoader(orderName);
        FirebirdOrderEntity entity = loader.extractData();
        if (entity != null) {
            new FrameMonitor(entity).setVisible(true);
            return true;
        }
        return false;
    }

    private void removeOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeOrderBtnActionPerformed
        if (jOrdersList.getSelectedIndex() != -1) {                             //Если заказ выбран
            sentOrdersList.remove(jOrdersList.getSelectedIndex());              //Удалить из списка
        }
    }//GEN-LAST:event_removeOrderBtnActionPerformed

    private void toExportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toExportBtnActionPerformed
        if (!sentOrdersList.isEmpty()) {
            DataExportLauncher launcher = new DataExportLauncher(progressBar, sentOrdersList);
            launcher.runExport();
        } else {
            JOptionPane.showMessageDialog(null, "Вы не добавили в список ни одного заказа!");
        }
    }//GEN-LAST:event_toExportBtnActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.setTitle("Отдел № " + Options.getDepartmentName());
        departmentLabel.setText(Options.getDepartmentName() + "/");
    }//GEN-LAST:event_formWindowGainedFocus

    private void orderNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orderNumberKeyPressed
        if (evt.getKeyCode() == 10) {
            addOrderBtn.doClick();
        }
    }//GEN-LAST:event_orderNumberKeyPressed

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

    private final DefaultListModel sentOrdersList = new DefaultListModel();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addOrderBtn;
    private javax.swing.JMenuItem archiveCallMenuBtn;
    private javax.swing.JLabel departmentLabel;
    private javax.swing.JMenuItem doSearchMenuBtn;
    private javax.swing.JMenuItem exitMenuBtn;
    private javax.swing.JMenuItem getFAQMenuBtn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JList jOrdersList;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JMenuItem optionsCallMenuBtn;
    private javax.swing.JTextField orderNumber;
    public javax.swing.JProgressBar progressBar;
    private javax.swing.JButton removeOrderBtn;
    private javax.swing.JButton toExportBtn;
    // End of variables declaration//GEN-END:variables
}
