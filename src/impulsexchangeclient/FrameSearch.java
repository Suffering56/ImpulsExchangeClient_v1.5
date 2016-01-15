package impulsexchangeclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class FrameSearch extends javax.swing.JFrame {

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        doSearchBtn = new javax.swing.JButton();
        orderField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jSearchList = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Поиск");
        setMinimumSize(new java.awt.Dimension(207, 300));
        setResizable(false);
        setSize(new java.awt.Dimension(207, 300));

        doSearchBtn.setText("Поиск");
        doSearchBtn.setFocusPainted(false);
        doSearchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doSearchBtnActionPerformed(evt);
            }
        });

        orderField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                orderFieldKeyPressed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Введите номер заказа:");
        jLabel1.setFocusable(false);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(207, 300));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(207, 300));

        jScrollPane1.setViewportView(jSearchList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(orderField)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doSearchBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(orderField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(doSearchBtn)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, orderField});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public FrameSearch() {
        initComponents();
        setLocationRelativeTo(null);
        jSearchList.setModel(searchList);
    }

    private void doSearch() throws IOException {
        String archivePath = System.getProperty("user.dir") + "\\archive.bin";
        File archive = new File(archivePath);
        searchList.clear();

        if (Files.exists(archive.toPath())) {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(archive)));
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (extractOrder(line).contains(orderField.getText())) {
                    searchList.addElement(line);
                }
            }
        } else {
            Files.createFile(archive.toPath());
        }

        if (searchList.isEmpty()) {
            searchList.addElement("По вашему запросу ничего не найдено");
        }
        if (!orderField.getText().equals("")) {
            this.setTitle("Поиск по заказу: " + orderField.getText());
        } else {
            this.setTitle("Поиск по всем заказам");
        }
        orderField.setText("");
    }

    private String extractOrder(String line) {
        Matcher m = p.matcher(line);
        if (m.matches()) {
            return m.group(1);
        } else {
            return "Ошибка чтения строки: <" + line + ">";
        }
    }

    private void doSearchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doSearchBtnActionPerformed
        try {
            doSearch();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения архива (archive.bin).\r\n"
                    + "ex.toString()" + ex.toString(), "SearchFrame.doSearch()", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_doSearchBtnActionPerformed

    private void orderFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orderFieldKeyPressed
        if (evt.getKeyCode() == 10) {
            doSearchBtn.doClick();
        }
    }//GEN-LAST:event_orderFieldKeyPressed

    private final Pattern p = Pattern.compile("\\d+/(\\d+)\\p{Space}+(\\d+.\\d+.\\d+)\\p{Space}(\\d+:\\d+:\\d+)");
    private final DefaultListModel searchList = new DefaultListModel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton doSearchBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList jSearchList;
    private javax.swing.JTextField orderField;
    // End of variables declaration//GEN-END:variables
}
