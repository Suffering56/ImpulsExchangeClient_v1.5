package impulsexchangeclient;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class FrameNewOrder extends javax.swing.JFrame {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        depLabel = new javax.swing.JLabel();
        orderField = new javax.swing.JTextField();
        nextBtn = new javax.swing.JButton();
        cancelBtn = new javax.swing.JButton();
        dontSend = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Новый заказ");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        depLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        depLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        depLabel.setText("112/");
        depLabel.setFocusable(false);

        orderField.setText("7309");
        orderField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                orderFieldKeyPressed(evt);
            }
        });

        nextBtn.setText("Далее");
        nextBtn.setFocusPainted(false);
        nextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBtnActionPerformed(evt);
            }
        });

        cancelBtn.setText("Отмена");
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        dontSend.setText("не добавлять в отгрузку");
        dontSend.setFocusPainted(false);
        dontSend.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(depLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(orderField, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(cancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(dontSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(orderField))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(depLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(dontSend, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextBtn)
                    .addComponent(cancelBtn))
                .addGap(5, 5, 5))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cancelBtn, nextBtn});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public FrameNewOrder(FrameMain mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        setLocationRelativeTo(null);
        mainFrame.setEnabled(false);
        forExportOrdersModel = mainFrame.getForExportOrdersModel();
        depLabel.setText(Options.getDepartmentName() + "/");
    }

    private void nextBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_nextBtnActionPerformed
        String fullOrderName = Options.getDepartmentName() + "/" + orderField.getText().trim();
        if (isValidOrder(fullOrderName)) {                                      //проверка на корректность заказа (только цифры - не менее одной)
            if (!forExportOrdersModel.contains(fullOrderName)) {                //проверка на дублирование номера заказа
                selectAndShowNextFrame(fullOrderName);
            } else {
                JOptionPane.showMessageDialog(null, "Заказ №" + fullOrderName + " уже есть в списке!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Некорректный номер заказа!");
        }
        orderField.setText(null);
    }//GEN-LAST:event_nextBtnActionPerformed

    private void selectAndShowNextFrame(String fullOrderName) {
        if (!dontSend.isSelected()) {       //отправляем заказ в отгрузку
            getFirebirdData(orderField.getText().trim());
        } else {                            //не отправляем заказ в отгрузку
            forExportOrdersModel.addElement(fullOrderName);
            mainFrame.setEnabled(true);
        }
        this.dispose();
    }

    private void getFirebirdData(String orderName) {
        FirebirdDataLoader loader = new FirebirdDataLoader(mainFrame, orderName);
        OrderEntity entity = loader.extractData();
        if (entity != null) {
            new FrameMonitor(mainFrame, entity).setVisible(true);
        }
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        mainFrame.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void orderFieldKeyPressed(KeyEvent evt) {//GEN-FIRST:event_orderFieldKeyPressed
        if (evt.getKeyCode() == 10) {
            nextBtn.doClick();
        } else if (evt.getKeyCode() == 27) {
            cancelBtn.doClick();
        }
    }//GEN-LAST:event_orderFieldKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        mainFrame.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    private boolean isValidOrder(String fullOrderName) {
        Pattern p = Pattern.compile(Options.getDepartmentName() + "/\\d+");
        Matcher m = p.matcher(fullOrderName);
        return m.matches();
    }

    private final DefaultListModel forExportOrdersModel;
    private final FrameMain mainFrame;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelBtn;
    private javax.swing.JLabel depLabel;
    private javax.swing.JCheckBox dontSend;
    private javax.swing.JButton nextBtn;
    private javax.swing.JTextField orderField;
    // End of variables declaration//GEN-END:variables
}
