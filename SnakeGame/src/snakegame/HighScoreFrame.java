package snakegame;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HighScoreFrame {

    HighScoreFrame() {
        JFrame score = new JFrame("Top 9 Players");
        JTable jt = new JTable();
        jt.setBounds(3, 3, 380, 380);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jt);
        ResultSet rs = null;
        String uname = "root";
        String passoword = "mysql";
        String url = "jdbc:mysql://localhost:3306/snakegame";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url, uname, passoword);
            Statement statement = con.createStatement();

            String qstring = "select * from highscore order by score desc limit 9";
            rs = statement.executeQuery(qstring);
            ResultSetMetaData rsmd = rs.getMetaData();
            DefaultTableModel model = (DefaultTableModel) jt.getModel();
            
            
            int cols = rsmd.getColumnCount() - 1;
            String[] colName = new String[2];
            //colName = {"Name","Score"};
            for (int i = 0; i < cols; i++) {
                colName[i] = rsmd.getColumnName(i + 1);
            }
            
            model.setColumnIdentifiers(colName);
            String[] colName1 = {"Name", "Score"};
            model.addRow(colName1);
            String name;
            String pscore;
            
            while (rs.next()) {
                name = rs.getString(2);
                pscore = Integer.toString(rs.getInt(3));
                String[] row = {name, pscore};
                model.addRow(row);
            }
            statement.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
//        jt.setFont(Font.BOLD);
        
        score.add(jt);
        score.add(scrollPane);
        score.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        score.setSize(400, 205);
//        score.pack();
        score.setResizable(false);
        score.setLocationRelativeTo(null);
        score.setLayout(null);
        score.setVisible(true);
    }
    
}
