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

public class ArchiveFrame extends javax.swing.JFrame {

    public ArchiveFrame() {
        initComponents();
        setLocationRelativeTo(null);
        try {
            extractArchive();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения архива (archive.bin). Код ошибки:\r\n" + ex.toString(), "ArchiveFrame.extractArchive()", JOptionPane.ERROR_MESSAGE);
        }
        jArchiveList.setModel(archiveList);
        jDateList.setModel(dateList);
    }

    private void extractArchive() throws IOException {
        String archivePath = System.getProperty("user.dir") + "\\archive.bin";
        File archive = new File(archivePath);
        archiveList.clear();
        dateList.clear();
        if (Files.exists(archive.toPath())) {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(archive)));
            String line;
            int counter = 0;

            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (counter >= 25) {
                    break;
                }
                if (!line.equals("")) {
                    archiveList.addElement(extractData(line, "order"));
                    dateList.addElement(extractData(line, "date"));
                }
                counter++;
            }
            in.close();
        } else {
            Files.createFile(archive.toPath());
        }
    }

    private String extractData(String line, String param) {
        Matcher m = p.matcher(line);
        if (m.matches()) {
            switch (param) {
                case "order":
                    return m.group(1);                          //номер заказа!
                case "date":
                    return m.group(2) + "     " + m.group(3);   //дата + время
                default:
                    return "Неверный параметр функции <extractData>";
            }
        } else {
            return "Ошибка чтения строки: <" + line + ">";
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jArchiveList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jDateList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Архив (последние 25 заказов)");
        setResizable(false);

        jArchiveList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jArchiveListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jArchiveList);

        jDateList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jDateListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jDateList);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Номер заказа");
        jLabel1.setFocusable(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Дата");
        jLabel2.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(3, 3, 3))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2, jScrollPane1});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jDateListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jDateListValueChanged
        jArchiveList.setSelectedIndex(jDateList.getSelectedIndex());
    }//GEN-LAST:event_jDateListValueChanged

    private void jArchiveListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jArchiveListValueChanged
        jDateList.setSelectedIndex(jArchiveList.getSelectedIndex());
    }//GEN-LAST:event_jArchiveListValueChanged

    private final Pattern p = Pattern.compile("(\\d+/\\d+)\\p{Space}+(\\d+.\\d+.\\d+)\\p{Space}(\\d+:\\d+:\\d+)");
    private final DefaultListModel archiveList = new DefaultListModel();
    private final DefaultListModel dateList = new DefaultListModel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList jArchiveList;
    private javax.swing.JList jDateList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
