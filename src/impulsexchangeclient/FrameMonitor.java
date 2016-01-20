package impulsexchangeclient;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

public class FrameMonitor extends javax.swing.JFrame {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        topTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        botTable = new javax.swing.JTable();
        laminationBox = new javax.swing.JCheckBox();
        garbageBox = new javax.swing.JCheckBox();
        dateLabel = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        dateSelectBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        descriptionLabel = new javax.swing.JLabel();
        commentLabel = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Экспорт в отгрузку");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        topTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null, null}
            },
            new String [] {
                "№ Заказа", "Клиент", "Адрес", "Контактные данные", "Сумма заказа"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(topTable);

        botTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Замерщик", "Количество конструкций", "Доставка", "Демонтаж", "Монтаж"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(botTable);

        laminationBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        laminationBox.setText("Ламинация");
        laminationBox.setFocusPainted(false);

        garbageBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        garbageBox.setText("Вывоз мусора");
        garbageBox.setFocusPainted(false);

        dateLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLabel.setText("Дата монтажа: ");
        dateLabel.setFocusable(false);

        dateField.setEditable(false);

        dateSelectBtn.setText("выбрать...");
        dateSelectBtn.setFocusPainted(false);

        jTextArea1.setColumns(20);
        jScrollPane3.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jScrollPane4.setViewportView(jTextArea2);

        descriptionLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descriptionLabel.setText("Описание заказа:");
        descriptionLabel.setFocusable(false);

        commentLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        commentLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        commentLabel.setText("Комментарии к заказу:");
        commentLabel.setFocusable(false);

        cancelBtn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cancelBtn.setText("Отмена");
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        okBtn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        okBtn.setText("ОК");
        okBtn.setFocusPainted(false);
        okBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 870, Short.MAX_VALUE)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(okBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateSelectBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateField)
                            .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(laminationBox, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(garbageBox, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(72, 72, 72)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(commentLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(dateLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateSelectBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(laminationBox)
                                .addGap(7, 7, 7)
                                .addComponent(garbageBox))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(descriptionLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(commentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okBtn)
                    .addComponent(cancelBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {garbageBox, laminationBox});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelBtn, okBtn});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        mainFrame.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void okBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okBtnActionPerformed
        sentOrdersModel.addElement(entity.getFullOrderName());                   //добавляем заказ в список
        mainFrame.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_okBtnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        mainFrame.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    public FrameMonitor(FrameMain mainFrame, FirebirdOrderEntity entity) {
        this.mainFrame = mainFrame;
        this.entity = entity;
        sentOrdersModel = this.mainFrame.getSentOrdersModel();
        initComponents();
        setLocationRelativeTo(null);
        readData();
    }

    private void readData() {
        topTable.setModel(topTableModel);
        topTableModel.addColumn("№ заказа");
        topTableModel.addColumn("Клиент");
        topTableModel.addColumn("Адрес");
        topTableModel.addColumn("Контактные данные");
        topTableModel.addColumn("Сумма заказа");
        topTableModel.setRowCount(1);
        topTableModel.setValueAt(entity.getFullOrderName(), 0, 0);
        topTableModel.setValueAt(entity.getClient(), 0, 1);
        topTableModel.setValueAt(entity.getAddress(), 0, 2);
        topTableModel.setValueAt(entity.getContacts(), 0, 3);
        topTableModel.setValueAt(entity.getCost(), 0, 4);

        botTable.setModel(botTableModel);
        botTableModel.addColumn("Замерщик");
        botTableModel.addColumn("Кол-во конструкций");
        botTableModel.addColumn("Монтаж");
        botTableModel.addColumn("Демонтаж");
        botTableModel.addColumn("Доставка");
        botTableModel.setRowCount(1);
        botTableModel.setValueAt(entity.getMaster(), 0, 0);
        botTableModel.setValueAt(entity.getConstructionsCount(), 0, 1);
        botTableModel.setValueAt(entity.getMounting(), 0, 2);
        botTableModel.setValueAt(entity.getDismantling(), 0, 3);
        botTableModel.setValueAt(entity.getDelivery(), 0, 4);
    }

    private final FrameMain mainFrame;
    private final DefaultListModel sentOrdersModel;
    private final FirebirdOrderEntity entity;

    private final DefaultTableModel topTableModel = new DefaultTableModel();
    private final DefaultTableModel botTableModel = new DefaultTableModel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable botTable;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JTextField dateField;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton dateSelectBtn;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JCheckBox garbageBox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JCheckBox laminationBox;
    private javax.swing.JButton okBtn;
    private javax.swing.JTable topTable;
    // End of variables declaration//GEN-END:variables
}
