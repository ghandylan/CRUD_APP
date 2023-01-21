import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Controller {


    public static void SQLConnect() {
        try {
            // CONNECT TO MYSQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUD_APP", "root", "");
            System.out.println("Connected to database");
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void truncateTable() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUD_APP", "root", "");
            String query = "TRUNCATE TABLE users";
            PreparedStatement pst = con.prepareStatement(query);
            pst.executeUpdate();
            System.out.println("Table cleared");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showTable() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUD_APP", "root", "");
            String query = "SELECT * FROM `users`";
            PreparedStatement pst = con.prepareStatement(query);
            // loop through the result set
            ResultSet rs = pst.executeQuery();

            // empty the table before adding new data
            DefaultTableModel model = (DefaultTableModel) Main.jTable1.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                String col1 = rs.getString("id");
                String col2 = rs.getString("Name");
                String col3 = rs.getString("Mobile_Number");

                String[] tbData = {col1, col2, col3};
                DefaultTableModel tblModel = (DefaultTableModel) Main.jTable1.getModel();
                tblModel.addRow(tbData);
                Main.jTable1.setModel(tblModel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void addUser() {
        String name = Main.jTextField1.getText();
        String mobile = Main.jTextField2.getText();
        if (Main.jTextField1.getText().equals("") || Main.jTextField2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
        } else {
            try {
                // create a sql date object, so we can use it in our INSERT statement
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUD_APP", "root", "");
                String query = "INSERT INTO `users`(`name`, `mobile_number`) VALUES (?,?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, mobile);
                pst.executeUpdate();
                showTable();
                JOptionPane.showMessageDialog(null, "Data Inserted Successfully");
                System.out.println("Data Inserted Successfully");
                Main.jTextField1.setText("");
                Main.jTextField2.setText("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public static void deleteUser() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            // delete the row from the database
            try {
                // delete selected row from the database
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUD_APP", "root", "");
                // convert selected ID string to int
                int id = Integer.parseInt(Main.jTable1.getModel().getValueAt(Main.selectedRow, 0).toString());
                String query = "DELETE FROM `users` WHERE id = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, id);
                pst.executeUpdate();
                // print deleted row data
                pst.executeUpdate();
                showTable();
                JOptionPane.showMessageDialog(null, "Data Deleted Successfully");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        System.out.println("Row deleted" + Main.selectedRow);
    }

    public static void updateUser() {
        // TODO add your handling code here:
        try {
            new UpdateForm().setVisible(true);
        } catch (Exception e) {
            System.out.println("Please select a row first");
        }
        showTable();
    }

    public static void updateData() {

    }
}
