import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;
import java.util.Objects;

public class ATM_UI {
    static JFrame ui_home, ui_dashboard, ui_next, ui_withdraw, ui_deposit, ui_transactions, ui_transfer;
    static JLabel main_heading, label_atm_pin, label_atm_num, label_acc_number, label_name, label_warning, label_mobile, label_email, show_acc_number, show_name, show_mobile_number, show_email, label_address, show_address, label_balance, show_balance, label_withdraw_money, label_deposit_money, label_transfer_money,label_recipient_acc_number,label_re_recipient_acc_number;
    static JTextField input_atm_num, input_withdraw_money, input_deposit_money, input_transfer_money,input_recipient_acc_number,input_re_recipient_acc_number;
    static JPasswordField input_atm_pin;
    static JButton next_btn, btn_trans_hist, btn_withdraw, btn_deposit, btn_quit, btn_back, btn_transfer;
    static private final String jdbc = "com.mysql.cj.jdbc.Driver";
    static private final String db_url = "jdbc:mysql://localhost:3306/any_time_money_bank";
    static private final String db_user = "root";
    static private final String db_pass = "";
    static Date date;
    static String trans_data[][] = new String[100][4];
    static String trans_column[] = {"DATE", "CREDIT", "DEBIT", "BALANCE"};
    static JTable trans_table;
    static JScrollPane sp;
    static int attempts = 0;

    static void homeUI() {
        ui_home = new JFrame();
        ui_home.setTitle("Any Time Money Bank - by Neeraj Suman");


        main_heading = new JLabel("Any Time Money Bank", JLabel.CENTER);

        main_heading.setBackground(Color.BLACK);
        main_heading.setForeground(Color.GREEN);
        main_heading.setOpaque(true);
        main_heading.setFont(new Font("Monospaced", Font.BOLD, 48));
        main_heading.setBounds(0, 0, 1000, 100);
        ui_home.add(main_heading);

        label_atm_num = new JLabel("Enter Your 16 digits ATM Number", JLabel.CENTER);
        label_atm_num.setFont(new Font("Serif", Font.PLAIN, 24));
        label_atm_num.setBounds(250, 200, 500, 50);
        ui_home.add(label_atm_num);

        input_atm_num = new JTextField();
        input_atm_num.setFont(new Font("Verdana", Font.BOLD, 32));
        input_atm_num.setHorizontalAlignment(JTextField.CENTER);
        input_atm_num.setBounds(250, 250, 500, 75);
        ui_home.add(input_atm_num);

        next_btn = new JButton("NEXT");
        next_btn.setFont(new Font("SansSerif", Font.ITALIC, 24));
        next_btn.setBounds(800, 250, 100, 75);
        ui_home.add(next_btn);

        label_warning = new JLabel();
        label_warning.setForeground(Color.red);
        label_warning.setBounds(250, 325, 500, 25);
        ui_home.add(label_warning);

        next_btn.addActionListener(e -> {
            String atm_number = input_atm_num.getText();

            if (!(atm_number.length() == 16)) {
                label_warning.setText("Warning:- Please enter 16 digits only!!!");
            } else {
                boolean flag = false;
                try {
                    Class.forName(jdbc);
                    Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
                    PreparedStatement ps = con.prepareStatement("SELECT id from atm_cards where atm_number=?");
                    ps.setString(1, atm_number);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        flag = true;
                    }
                    if (flag) {
                        nextUI(atm_number);
                        ui_home.dispose();
                    } else {
                        label_warning.setText("");
                        JOptionPane.showMessageDialog(next_btn, "Account Not Found");
                    }
                    con.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }


        });


        ui_home.setSize(1000, 600);
        ui_home.setLayout(null);
        ui_home.setVisible(true);
        ui_home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void nextUI(String atm_number) {

        ui_next = new JFrame();
        ui_next.setTitle("Any Time Money Bank - by Neeraj Suman");
        int id;


        main_heading = new JLabel("Any Time Money Bank", JLabel.CENTER);
        main_heading.setBackground(Color.BLACK);
        main_heading.setForeground(Color.GREEN);
        main_heading.setOpaque(true);
        main_heading.setFont(new Font("Monospaced", Font.BOLD, 48));
        main_heading.setBounds(0, 0, 1000, 100);
        ui_next.add(main_heading);

        label_atm_pin = new JLabel("Enter Your 4 Digits ATM Pin", JLabel.CENTER);
        label_atm_pin.setFont(new Font("Serif", Font.PLAIN, 24));
        label_atm_pin.setBounds(250, 200, 500, 50);
        ui_next.add(label_atm_pin);

        input_atm_pin = new JPasswordField();
        input_atm_pin.setFont(new Font("Verdana", Font.BOLD, 32));
        input_atm_pin.setHorizontalAlignment(JPasswordField.CENTER);
        input_atm_pin.setBounds(250, 250, 500, 75);
        ui_next.add(input_atm_pin);

        next_btn = new JButton("NEXT");
        next_btn.setFont(new Font("SansSerif", Font.ITALIC, 24));
        next_btn.setBounds(800, 250, 100, 75);
        ui_next.add(next_btn);

        label_warning = new JLabel();
        label_warning.setForeground(Color.red);
        label_warning.setBounds(250, 325, 500, 25);
        ui_next.add(label_warning);

        next_btn.addActionListener(e -> {
            char[] atm_pin = input_atm_pin.getPassword();
            if (attempts >= 3) {
                JOptionPane.showMessageDialog(next_btn, "Your ATM Number has been blocked!!!");
                label_warning.setText("ALERT:- Please visit the nearest branch!!!");
            } else if (!(atm_pin.length == 4)) {
                label_warning.setText("WARNING:- Please enter 4 digits only!!!");
            } else {
                boolean flag = false;


                try {
                    Class.forName(jdbc);
                    Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
                    PreparedStatement ps = con.prepareStatement("SELECT id FROM atm_cards WHERE atm_number=? AND atm_pin=?");
                    ps.setString(1, atm_number);
                    ps.setString(2, String.valueOf(atm_pin));
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        flag = true;
                    }
                    if (flag) {
                        dashboardUI(atm_number, atm_pin);
                        ui_next.dispose();
                    } else {
                        label_warning.setText("");
                        JOptionPane.showMessageDialog(next_btn, "Incorrect ATM Pin!!!");
                        attempts++;
                    }
                    con.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }


        });


        ui_next.setSize(1000, 600);
        ui_next.setLayout(null);
        ui_next.setVisible(true);
        ui_next.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void dashboardUI(String atm_number, char[] atm_pin) {

        ui_dashboard = new JFrame();
        ui_dashboard.setTitle("Any Time Money Bank - by Neeraj Suman");


        main_heading = new JLabel("Any Time Money Bank", JLabel.CENTER);
        main_heading.setBackground(Color.BLACK);
        main_heading.setForeground(Color.GREEN);
        main_heading.setOpaque(true);
        main_heading.setFont(new Font("Monospaced", Font.BOLD, 48));
        main_heading.setBounds(0, 0, 1000, 100);
        ui_dashboard.add(main_heading);

        label_acc_number = new JLabel("A/c Number:");
        label_acc_number.setBounds(10, 200, 100, 25);
        ui_dashboard.add(label_acc_number);

        show_acc_number = new JLabel();
        show_acc_number.setBounds(120, 200, 300, 25);
        ui_dashboard.add(show_acc_number);

        label_name = new JLabel("NAME:");
        label_name.setBounds(10, 230, 100, 25);
        ui_dashboard.add(label_name);

        show_name = new JLabel();
        show_name.setBounds(120, 230, 300, 25);
        ui_dashboard.add(show_name);

        label_mobile = new JLabel("Mobile No.:");
        label_mobile.setBounds(10, 260, 100, 25);
        ui_dashboard.add(label_mobile);

        show_mobile_number = new JLabel();
        show_mobile_number.setBounds(120, 260, 300, 25);
        ui_dashboard.add(show_mobile_number);

        label_email = new JLabel("Email:");
        label_email.setBounds(10, 290, 100, 25);
        ui_dashboard.add(label_email);

        show_email = new JLabel();
        show_email.setBounds(120, 290, 300, 25);
        ui_dashboard.add(show_email);

        label_address = new JLabel("Address:");
        label_address.setBounds(10, 320, 100, 25);
        ui_dashboard.add(label_address);

        show_address = new JLabel();
        show_address.setBounds(120, 320, 300, 25);
        ui_dashboard.add(show_address);

        label_balance = new JLabel("Current Balance");
        label_balance.setBounds(600, 200, 300, 25);
        label_balance.setFont(new Font("Serif", Font.PLAIN, 24));
        ui_dashboard.add(label_balance);

        show_balance = new JLabel("₹ 0");
        show_balance.setFont(new Font("Serif", Font.BOLD, 32));
        show_balance.setBounds(600, 250, 300, 25);
        ui_dashboard.add(show_balance);

        btn_trans_hist = new JButton("<html>Transaction<br>History</html>");
        btn_trans_hist.setFont(new Font("Serif", Font.ITALIC, 18));
        btn_trans_hist.setBounds(10, 400, 150, 100);
        ui_dashboard.add(btn_trans_hist);
        btn_trans_hist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = 0;
                try {
                    Class.forName(jdbc);
                    Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
                    PreparedStatement ps = con.prepareStatement("SELECT id FROM atm_cards WHERE atm_number=? AND atm_pin=?");
                    ps.setString(1, atm_number);
                    ps.setString(2, String.valueOf(atm_pin));
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        id = rs.getInt("id");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                transactionsUI(id, atm_number, atm_pin);
                ui_dashboard.dispose();
            }
        });

        btn_withdraw = new JButton("<html>Withdraw<br>Money</html>");
        btn_withdraw.setFont(new Font("Serif", Font.ITALIC, 18));
        btn_withdraw.setBounds(170, 400, 150, 100);
        ui_dashboard.add(btn_withdraw);

        btn_withdraw.addActionListener(e -> {
            withdrawUI(atm_number, atm_pin);
            ui_dashboard.dispose();
        });

        btn_deposit = new JButton("<html>Deposit<br>Money</html>");
        btn_deposit.setFont(new Font("Serif", Font.ITALIC, 18));
        btn_deposit.setBounds(380, 400, 150, 100);
        ui_dashboard.add(btn_deposit);

        btn_deposit.addActionListener(e -> {
            depositUI(atm_number, atm_pin);
            ui_dashboard.dispose();
        });

        btn_transfer = new JButton("<html>Transfer<br>Money</html>");
        btn_transfer.setFont(new Font("Serif", Font.ITALIC, 18));
        btn_transfer.setBounds(590, 400, 150, 100);
        ui_dashboard.add(btn_transfer);
        btn_transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferUI(atm_number,atm_pin);
                ui_dashboard.dispose();
            }
        });

        btn_quit = new JButton("Quit");
        btn_quit.setFont(new Font("Serif", Font.ITALIC, 18));
        btn_quit.setBounds(800, 400, 150, 100);
        ui_dashboard.add(btn_quit);
        btn_quit.addActionListener(e -> {
            homeUI();
            ui_dashboard.dispose();
        });


        ui_dashboard.setSize(1000, 600);
        ui_dashboard.setLayout(null);
        ui_dashboard.setVisible(true);
        ui_dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            Class.forName(jdbc);
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM atm_cards WHERE atm_number=? AND atm_pin=?");
            ps.setString(1, atm_number);
            ps.setString(2, String.valueOf(atm_pin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                show_acc_number.setText(rs.getString("account_number"));
                show_name.setText(rs.getString("name"));
                show_email.setText(rs.getString("email"));
                show_mobile_number.setText(rs.getString("mobile"));
                show_address.setText(rs.getString("address"));
                show_balance.setText("₹ " + rs.getString("balance"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void withdrawUI(String atm_number, char[] atm_pin) {
        ui_withdraw = new JFrame();
        ui_withdraw.setTitle("Any Time Money Bank - by Neeraj Suman");


        main_heading = new JLabel("Any Time Money Bank", JLabel.CENTER);
        main_heading.setBackground(Color.BLACK);
        main_heading.setForeground(Color.GREEN);
        main_heading.setOpaque(true);
        main_heading.setFont(new Font("Monospaced", Font.BOLD, 48));
        main_heading.setBounds(0, 0, 1000, 100);
        ui_withdraw.add(main_heading);

        JLabel jl = new JLabel("( Withdraw Money )", JLabel.CENTER);
        jl.setBounds(0, 100, 1000, 25);
        ui_withdraw.add(jl);

        label_balance = new JLabel("Current Balance");
        label_balance.setBounds(100, 200, 300, 25);
        label_balance.setFont(new Font("Serif", Font.PLAIN, 24));
        ui_withdraw.add(label_balance);

        show_balance = new JLabel("₹ 0");
        show_balance.setFont(new Font("Serif", Font.BOLD, 32));
        show_balance.setBounds(100, 250, 300, 25);
        ui_withdraw.add(show_balance);

        label_withdraw_money = new JLabel("Enter Money in Rupees (₹)");
        label_withdraw_money.setFont(new Font("Serif", Font.PLAIN, 24));
        label_withdraw_money.setBounds(250, 300, 500, 50);
        ui_withdraw.add(label_withdraw_money);

        input_withdraw_money = new JTextField();
        input_withdraw_money.setFont(new Font("Verdana", Font.BOLD, 32));
        input_withdraw_money.setBounds(250, 350, 500, 75);
        ui_withdraw.add(input_withdraw_money);

        btn_withdraw = new JButton("OK");
        btn_withdraw.setFont(new Font("SansSerif", Font.ITALIC, 24));
        btn_withdraw.setBounds(800, 350, 100, 75);
        ui_withdraw.add(btn_withdraw);
        btn_withdraw.addActionListener(e -> {
            String withdraw_money = input_withdraw_money.getText();
            double current_balance = 0;
            int id = 0;
            date = new Date();

            try {
                Class.forName(jdbc);
                Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
                PreparedStatement ps = con.prepareStatement("SELECT id,balance from atm_cards where atm_number=? and atm_pin=?");
                ps.setString(1, atm_number);
                ps.setString(2, String.valueOf(atm_pin));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    current_balance = rs.getDouble("balance");
                    id = rs.getInt("id");
                }
                if (current_balance < Double.parseDouble(withdraw_money)) {
                    JOptionPane.showMessageDialog(btn_withdraw, "Insufficient Balance!!!");
                } else {
                    current_balance = current_balance - Double.parseDouble(withdraw_money);
                    PreparedStatement ps1 = con.prepareStatement("update atm_cards set balance=? where atm_number=? and atm_pin=?");
                    PreparedStatement ps2 = con.prepareStatement("insert into id? (date,debit,balance) values(?,?,?)");
                    ps2.setInt(1, id);
                    ps2.setString(2, date.toString());
                    ps2.setString(3, withdraw_money);
                    ps2.setString(4, String.valueOf(current_balance));
                    ps1.setString(1, String.valueOf(current_balance));
                    ps1.setString(2, atm_number);
                    ps1.setString(3, String.valueOf(atm_pin));
                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    show_balance.setText("₹ " + current_balance);
                }
                con.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        });

        try {
            Class.forName(jdbc);
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            PreparedStatement ps = con.prepareStatement("SELECT balance from atm_cards where atm_number=? and atm_pin=?");
            ps.setString(1, atm_number);
            ps.setString(2, String.valueOf(atm_pin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                show_balance.setText("₹ " + rs.getString("balance"));
            }

            con.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        btn_back = new JButton("Back");
        btn_back.setFont(new Font("SansSerif", Font.ITALIC, 24));
        btn_back.setBounds(10, 475, 150, 75);
        ui_withdraw.add(btn_back);
        btn_back.addActionListener(e -> {
            dashboardUI(atm_number, atm_pin);
            ui_withdraw.dispose();
        });


        ui_withdraw.setSize(1000, 600);
        ui_withdraw.setLayout(null);
        ui_withdraw.setVisible(true);
        ui_withdraw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void depositUI(String atm_number, char[] atm_pin) {
        ui_deposit = new JFrame();
        ui_deposit.setTitle("Any Time Money Bank - by Neeraj Suman");


        main_heading = new JLabel("Any Time Money Bank", JLabel.CENTER);
        main_heading.setBackground(Color.BLACK);
        main_heading.setForeground(Color.GREEN);
        main_heading.setOpaque(true);
        main_heading.setFont(new Font("Monospaced", Font.BOLD, 48));
        main_heading.setBounds(0, 0, 1000, 100);
        ui_deposit.add(main_heading);

        JLabel jl = new JLabel("( Deposit Money )", JLabel.CENTER);
        jl.setBounds(0, 100, 1000, 25);
        ui_deposit.add(jl);

        label_balance = new JLabel("Current Balance");
        label_balance.setBounds(100, 200, 300, 25);
        label_balance.setFont(new Font("Serif", Font.PLAIN, 24));
        ui_deposit.add(label_balance);

        show_balance = new JLabel("₹ 0");
        show_balance.setFont(new Font("Serif", Font.BOLD, 32));
        show_balance.setBounds(100, 250, 300, 25);
        ui_deposit.add(show_balance);

        label_deposit_money = new JLabel("Enter Money in Rupees (₹)");
        label_deposit_money.setFont(new Font("Serif", Font.PLAIN, 24));
        label_deposit_money.setBounds(250, 300, 500, 50);
        ui_deposit.add(label_deposit_money);

        input_deposit_money = new JTextField();
        input_deposit_money.setFont(new Font("Verdana", Font.BOLD, 32));
        input_deposit_money.setBounds(250, 350, 500, 75);
        ui_deposit.add(input_deposit_money);

        btn_deposit = new JButton("OK");
        btn_deposit.setFont(new Font("SansSerif", Font.ITALIC, 24));
        btn_deposit.setBounds(800, 350, 100, 75);
        ui_deposit.add(btn_deposit);
        btn_deposit.addActionListener(e -> {
            String deposit_money = input_deposit_money.getText();
            double current_balance = 0;
            int id = 0;
            date = new Date();
            try {
                Class.forName(jdbc);
                Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
                PreparedStatement ps = con.prepareStatement("SELECT id,balance from atm_cards where atm_number=? and atm_pin=?");
                ps.setString(1, atm_number);
                ps.setString(2, String.valueOf(atm_pin));
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    current_balance = rs.getDouble("balance");
                    id = rs.getInt("id");
                }
                current_balance = current_balance + Double.parseDouble(deposit_money);
                PreparedStatement ps1 = con.prepareStatement("update atm_cards set balance=? where atm_number=? and atm_pin=?");
                PreparedStatement ps2 = con.prepareStatement("insert into id? (date,credit,balance) values(?,?,?)");
                ps2.setInt(1, id);
                ps2.setString(2, date.toString());
                ps2.setString(3, deposit_money);
                ps2.setString(4, String.valueOf(current_balance));
                ps1.setString(1, String.valueOf(current_balance));
                ps1.setString(2, atm_number);
                ps1.setString(3, String.valueOf(atm_pin));
                ps1.executeUpdate();
                ps2.executeUpdate();
                show_balance.setText("₹ " + current_balance);
                con.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }


        });

        try {
            Class.forName(jdbc);
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            PreparedStatement ps = con.prepareStatement("SELECT balance from atm_cards where atm_number=? and atm_pin=?");
            ps.setString(1, atm_number);
            ps.setString(2, String.valueOf(atm_pin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                show_balance.setText("₹ " + rs.getString("balance"));
            }

            con.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        btn_back = new JButton("Back");
        btn_back.setFont(new Font("SansSerif", Font.ITALIC, 24));
        btn_back.setBounds(10, 475, 150, 75);
        ui_deposit.add(btn_back);
        btn_back.addActionListener(e -> {
            dashboardUI(atm_number, atm_pin);
            ui_deposit.dispose();
        });


        ui_deposit.setSize(1000, 600);
        ui_deposit.setLayout(null);
        ui_deposit.setVisible(true);
        ui_deposit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void transactionsUI(int id, String atm_number, char[] atm_pin) {
        ui_transactions = new JFrame();
        ui_transactions.setTitle("Any Time Money Bank - by Neeraj Suman");


        main_heading = new JLabel("Any Time Money Bank", JLabel.CENTER);
        main_heading.setBackground(Color.BLACK);
        main_heading.setForeground(Color.GREEN);
        main_heading.setOpaque(true);
        main_heading.setFont(new Font("Monospaced", Font.BOLD, 48));
        main_heading.setBounds(0, 0, 1000, 100);
        ui_transactions.add(main_heading);

        trans_table = new JTable(trans_data, trans_column);
        sp = new JScrollPane(trans_table);
        sp.setBounds(10, 150, 950, 300);
        ui_transactions.add(sp);

        btn_back = new JButton("Back");
        btn_back.setFont(new Font("SansSerif", Font.ITALIC, 24));
        btn_back.setBounds(10, 475, 150, 75);
        ui_transactions.add(btn_back);
        btn_back.addActionListener(e -> {
            dashboardUI(atm_number, atm_pin);
            ui_transactions.dispose();
        });

        try {
            Class.forName(jdbc);
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            PreparedStatement ps = con.prepareStatement("SELECT date,credit,debit,balance from id?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < 4; j++) {
                    trans_data[i][j] = rs.getString(j + 1);
                }
                i++;
            }

            con.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }


        ui_transactions.setSize(1000, 600);
        ui_transactions.setLayout(null);
        ui_transactions.setVisible(true);
        ui_transactions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static void transferUI(String atm_number,char[] atm_pin) {
        ui_transfer = new JFrame();
        ui_transfer.setTitle("Any Time Money Bank - by Neeraj Suman");


        main_heading = new JLabel("Any Time Money Bank", JLabel.CENTER);
        main_heading.setBackground(Color.BLACK);
        main_heading.setForeground(Color.GREEN);
        main_heading.setOpaque(true);
        main_heading.setFont(new Font("Monospaced", Font.BOLD, 48));
        main_heading.setBounds(0, 0, 1000, 100);
        ui_transfer.add(main_heading);

        JLabel jl = new JLabel("( Transfer Money )", JLabel.CENTER);
        jl.setBounds(0, 100, 1000, 25);
        ui_transfer.add(jl);

        label_balance = new JLabel("Current Balance");
        label_balance.setBounds(10, 110, 300, 25);
        label_balance.setFont(new Font("Serif", Font.PLAIN, 24));
        ui_transfer.add(label_balance);

        show_balance = new JLabel("₹ 0");
        show_balance.setFont(new Font("Serif", Font.BOLD, 32));
        show_balance.setBounds(10, 150, 300, 25);
        ui_transfer.add(show_balance);

        label_transfer_money = new JLabel("Enter Money in Rupees (₹)");
        label_transfer_money.setFont(new Font("Serif", Font.PLAIN, 24));
        label_transfer_money.setBounds(250, 200, 500, 50);
        ui_transfer.add(label_transfer_money);

        input_transfer_money = new JTextField();
        input_transfer_money.setFont(new Font("Verdana", Font.BOLD, 32));
        input_transfer_money.setBounds(250, 250, 500, 50);
        ui_transfer.add(input_transfer_money);

        label_recipient_acc_number = new JLabel("Enter Recipient's Account Number: ");
        label_recipient_acc_number.setFont(new Font("Serif",Font.PLAIN,24));
        label_recipient_acc_number.setBounds(250,300,500,50);
        ui_transfer.add(label_recipient_acc_number);

        input_recipient_acc_number = new JTextField();
        input_recipient_acc_number.setFont(new Font("Verdana",Font.BOLD,32));
        input_recipient_acc_number.setBounds(250,350,500,50);
        ui_transfer.add(input_recipient_acc_number);

        label_re_recipient_acc_number = new JLabel("Re-enter Recipient's Account Number: ");
        label_re_recipient_acc_number.setFont(new Font("Serif",Font.PLAIN,24));
        label_re_recipient_acc_number.setBounds(250,400,500,50);
        ui_transfer.add(label_re_recipient_acc_number);

        input_re_recipient_acc_number = new JTextField();
        input_re_recipient_acc_number.setFont(new Font("Verdana",Font.BOLD,32));
        input_re_recipient_acc_number.setBounds(250,450,500,50);
        ui_transfer.add(input_re_recipient_acc_number);


        btn_transfer = new JButton("OK");
        btn_transfer.setFont(new Font("SansSerif", Font.ITALIC, 24));
        btn_transfer.setBounds(800, 475, 100, 75);
        ui_transfer.add(btn_transfer);
        btn_transfer.addActionListener(e -> {
            String transfer_money = input_transfer_money.getText();
            String recipient_acc = input_recipient_acc_number.getText();
            String re_recipient_acc = input_re_recipient_acc_number.getText();
            double current_balance = 0;
            double current_recipient_balance = 0;
            int id = 0;
            int recipient_id = 0;
            date = new Date();

            if (Objects.equals(recipient_acc, re_recipient_acc)){
                try {
                    Class.forName(jdbc);
                    Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
                    PreparedStatement ps3 = con.prepareStatement("SELECT id,balance FROM atm_cards WHERE account_number=?");
                    ps3.setString(1,recipient_acc);
                    ResultSet rs1 = ps3.executeQuery();
                    boolean status = false;
                    while (rs1.next()){
                        current_recipient_balance = rs1.getDouble("balance");
                        recipient_id = rs1.getInt("id");
                        status = true;
                    }
                    if (status){
                        PreparedStatement ps = con.prepareStatement("SELECT id,balance from atm_cards where atm_number=? and atm_pin=?");
                        ps.setString(1, atm_number);
                        ps.setString(2, String.valueOf(atm_pin));
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            current_balance = rs.getDouble("balance");
                            id = rs.getInt("id");
                        }
                        if (current_balance < Double.parseDouble(transfer_money)) {
                            JOptionPane.showMessageDialog(btn_withdraw, "Insufficient Balance!!!");
                        } else {
                            current_balance = current_balance - Double.parseDouble(transfer_money);
                            PreparedStatement ps1 = con.prepareStatement("update atm_cards set balance=? where atm_number=? and atm_pin=?");
                            PreparedStatement ps2 = con.prepareStatement("insert into id? (date,debit,balance) values(?,?,?)");
                            ps2.setInt(1, id);
                            ps2.setString(2, date.toString());
                            ps2.setString(3, transfer_money);
                            ps2.setString(4, String.valueOf(current_balance));
                            ps1.setString(1, String.valueOf(current_balance));
                            ps1.setString(2, atm_number);
                            ps1.setString(3, String.valueOf(atm_pin));
                            ps1.executeUpdate();
                            ps2.executeUpdate();
                            show_balance.setText("₹ " + current_balance);

                            PreparedStatement ps4 = con.prepareStatement("update atm_cards set balance=? where account_number=?");
                            ps4.setString(1, String.valueOf(current_recipient_balance+Double.parseDouble(transfer_money)));
                            ps4.setString(2,recipient_acc);
                            PreparedStatement ps5 = con.prepareStatement("insert into id? (date,credit,balance) values(?,?,?)");
                            ps5.setInt(1, recipient_id);
                            ps5.setString(2, date.toString());
                            ps5.setString(3, transfer_money);
                            ps5.setString(4, String.valueOf(current_recipient_balance+Double.parseDouble(transfer_money)));
                            ps5.executeUpdate();
                            ps4.executeUpdate();
                        }
                    }else {
                        JOptionPane.showMessageDialog(btn_transfer,"Recipient's Account not Found!!!");
                    }

                    con.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            else{
                JOptionPane.showMessageDialog(btn_transfer,"Account numbers are not matched!!!");
            }





        });

        try {
            Class.forName(jdbc);
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            PreparedStatement ps = con.prepareStatement("SELECT balance from atm_cards where atm_number=? and atm_pin=?");
            ps.setString(1, atm_number);
            ps.setString(2, String.valueOf(atm_pin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                show_balance.setText("₹ " + rs.getString("balance"));
            }

            con.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        btn_back = new JButton("Back");
        btn_back.setFont(new Font("SansSerif", Font.ITALIC, 24));
        btn_back.setBounds(10, 475, 150, 75);
        ui_transfer.add(btn_back);
        btn_back.addActionListener(e -> {
            dashboardUI(atm_number, atm_pin);
            ui_transfer.dispose();
        });


        ui_transfer.setSize(1000, 600);
        ui_transfer.setLayout(null);
        ui_transfer.setVisible(true);
        ui_transfer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        homeUI();
    }
}
