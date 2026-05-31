package ui;

import service.ApiService;

import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {

    JTextField txtUser;
    JPasswordField txtPass;
    JComboBox<String> roleBox;
    JTextField txtEmail;   // 🔥 NEW FIELD

    public SignupFrame() {

        setTitle("Signup");
        setSize(500,460); // 🔥 increased height
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(20,20,45));

        JLabel head = new JLabel("Create Account");
        head.setBounds(130,20,250,35);
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Segoe UI",Font.BOLD,28));
        add(head);

        // ================= USERNAME =================
        JLabel l1 = new JLabel("Username");
        l1.setBounds(60,80,120,20);
        l1.setForeground(Color.WHITE);
        add(l1);

        txtUser = new JTextField();
        txtUser.setBounds(60,105,350,35);
        add(txtUser);

        // ================= EMAIL (NEW 🔥) =================
        JLabel lEmail = new JLabel("Email");
        lEmail.setBounds(60,145,120,20);
        lEmail.setForeground(Color.WHITE);
        add(lEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(60,170,350,35);
        add(txtEmail);

        // ================= PASSWORD =================
        JLabel l2 = new JLabel("Password");
        l2.setBounds(60,215,120,20);
        l2.setForeground(Color.WHITE);
        add(l2);

        txtPass = new JPasswordField();
        txtPass.setBounds(60,240,350,35);
        add(txtPass);

        // ================= ROLE =================
        JLabel l3 = new JLabel("Role");
        l3.setBounds(60,285,120,20);
        l3.setForeground(Color.WHITE);
        add(l3);

        roleBox = new JComboBox<>(new String[]{
                "STUDENT",
                "RECRUITER",
                "ADMIN"
        });

        roleBox.setBounds(60,310,350,35);
        add(roleBox);

        // ================= BUTTON =================
        JButton btn = new JButton("Signup");
        btn.setBounds(60,360,350,40);
        btn.setBackground(new Color(0,140,255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        add(btn);

        btn.addActionListener(e -> doSignup());

        setVisible(true);
    }

    private void doSignup() {

        String username = txtUser.getText().trim();
        String password = String.valueOf(txtPass.getPassword()).trim();
        String role = roleBox.getSelectedItem().toString();
        String email = txtEmail.getText().trim();   // 🔥 NEW

        // ================= VALIDATION =================
        if(username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields required ❌");
            return;
        }

        if(!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Invalid email ❌");
            return;
        }

        // ================= API CALL =================
        String response =
                ApiService.signup(
                        username,
                        password,
                        role,
                        email   // 🔥 IMPORTANT FIX
                );

        if(response.contains("\"success\":true")
                || response.toLowerCase().contains("success")){

            JOptionPane.showMessageDialog(this, "Signup Successful ✅");

            dispose();
            new LoginFrame();

        } else {
            JOptionPane.showMessageDialog(this, "Signup Failed ❌");
        }
    }
}