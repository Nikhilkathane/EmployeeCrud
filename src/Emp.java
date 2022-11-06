import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
public class Emp {
    private JPanel Main;
    private JTextField textName;
    private JTextField textSallery;
    private JTextField textMobile;
    private JButton Save;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField textId;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Emp");
        frame.setContentPane(new Emp().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;
    public void connect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/nikscompany","root","Niks@25252");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
           ex.printStackTrace();
        }
        catch (SQLException ex)
        {

        }
    }


    void table_load()
    {
        try
        {
           pst = con.prepareStatement("select * from employee");
           ResultSet rs =pst.executeQuery();
           table1.setModel(DbUtils.resultSetToTableModel(rs));
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }






    public Emp() {
        connect();
        table_load();
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empname,sallery,mobileno;

                empname=textName.getText();
                sallery=textSallery.getText();
                mobileno=textMobile.getText();


                try{
                    pst = con.prepareStatement("insert into employee(empname,sallary,mobileno)values(?,?,?)");
                    pst.setString(1,empname);
                    pst.setString(2,sallery);
                    pst.setString(3,mobileno);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"record Added !!!!");
                    table_load();
                    textName.setText("");
                    textSallery.setText("");
                    textMobile.setText("");
                    textName.requestFocus();

                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();

                }






            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    String empid = textId.getText();

                    pst= con.prepareStatement("select empname,sallary,mobileno from employee where id = ?");
                    pst.setString(1,empid);
                    ResultSet rs =pst.executeQuery();
                    if(rs.next()==true)
                    {
                        String empname =rs.getString(1);
                        String empsallery =rs.getString(2);
                        String empmobileno =rs.getString(3);
                        textName.setText(empname);
                        textSallery.setText(empsallery);
                        textMobile.setText(empmobileno);
                    }
                    else {
                        textName.setText("");
                        textSallery.setText("");
                        textMobile.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Empolyee No");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }







            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid,empname,sallery,mobileno;

                empname=textName.getText();
                sallery=textSallery.getText();
                mobileno=textMobile.getText();
                empid=textId.getText();

                try{
                    pst=con.prepareStatement("update employee set empname = ?,sallary = ?,mobileno = ? where id = ?");
                    pst.setString(1,empname);
                    pst.setString(2,sallery);
                    pst.setString(3,mobileno);
                    pst.setString(4,empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Updated !!!");
                    table_load();
                    textName.setText("");
                    textSallery.setText("");
                    textMobile.setText("");
                    textName.requestFocus();


                }
                catch (SQLException el)
                {
                    el.printStackTrace();
                }








            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid;

                empid = textId.getText();

                try
                {
                    pst=con.prepareStatement("delete from employee where id = ?");
                    pst.setString(1,empid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Deleted !!!");
                    table_load();
                    textName.setText("");
                    textSallery.setText("");
                    textMobile.setText("");
                    textName.requestFocus();






                }
                catch (SQLException el)
                {
                    el.printStackTrace();
                }





            }
        });
    }
}
