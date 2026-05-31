// ===============================
// LoginFrame.java
// UPDATED (ROLE REMOVED)
// username + password only
// ===============================
package ui;

import service.ApiService;
import util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

    JTextField txtUser;
    JPasswordField txtPass;

    public LoginFrame() {

        // ===============================
// LOGIN FRAME SIZE CHANGE
// ===============================

        setTitle("Login");
        setSize(1180,680);   // OLD was 950,520
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new LoginBackground());
        setLayout(null);


// ===============================
// LEFT SIDE TEXT BIGGER
// ===============================

        JLabel title1 = new JLabel("Find Your Dream Job");
        title1.setBounds(70,500,500,45);
        title1.setFont(new Font("Segoe UI",Font.BOLD,38));
        title1.setForeground(new Color(0,170,255));
        add(title1);

        JLabel title2 = new JLabel("Your career journey starts here");
        title2.setBounds(78,548,420,28);
        title2.setFont(new Font("Segoe UI",Font.PLAIN,22));
        title2.setForeground(Color.WHITE);
        add(title2);


// ===============================
// RIGHT LOGIN PANEL BIGGER
// ===============================

        JPanel panel = new JPanel();
        panel.setBounds(660,70,420,500);
        panel.setLayout(null);
        panel.setOpaque(false);
        add(panel);


// ===============================
// PANEL TEXT BIGGER
// ===============================

        JLabel heading = new JLabel("Welcome Back");
        heading.setBounds(95,20,280,40);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI",Font.BOLD,34));
        panel.add(heading);

        JLabel sub = new JLabel("Login to your account");
        sub.setBounds(120,62,220,22);
        sub.setForeground(new Color(220,220,220));
        sub.setFont(new Font("Segoe UI",Font.PLAIN,16));
        panel.add(sub);


// ===============================
// USERNAME
// ===============================

        JLabel lbl1 = new JLabel("Username");
        lbl1.setBounds(45,120,150,22);
        lbl1.setForeground(Color.WHITE);
        lbl1.setFont(new Font("Segoe UI",Font.BOLD,18));
        panel.add(lbl1);

        txtUser = new JTextField();
        txtUser.setBounds(45,148,330,46);
        styleField(txtUser);
        panel.add(txtUser);


// ===============================
// PASSWORD
// ===============================

        JLabel lbl2 = new JLabel("Password");
        lbl2.setBounds(45,225,150,22);
        lbl2.setForeground(Color.WHITE);
        lbl2.setFont(new Font("Segoe UI",Font.BOLD,18));
        panel.add(lbl2);

        txtPass = new JPasswordField();
        txtPass.setBounds(45,253,330,46);
        stylePasswordField(txtPass);
        panel.add(txtPass);


// ===============================
// LOGIN BUTTON
// ===============================

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(45,340,330,48);
        styleButton(loginBtn);
        panel.add(loginBtn);


// ===============================
// SIGNUP
// ===============================

        JLabel signText = new JLabel("Don't have an account?");
        signText.setBounds(70,415,180,22);
        signText.setForeground(Color.WHITE);
        signText.setFont(new Font("Segoe UI",Font.PLAIN,14));
        panel.add(signText);

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setBounds(245,412,110,30);
        styleSmallButton(signupBtn);
        panel.add(signupBtn);

        loginBtn.addActionListener(e -> doLogin());

// ENTER press se bhi login hoga
        txtPass.addActionListener(e -> doLogin());
        txtUser.addActionListener(e -> doLogin());

        signupBtn.addActionListener(e -> {
            dispose();
            new SignupFrame();   // apni signup class ka naam yaha
        });

        setVisible(true);
    }

    // ===============================
// LOGIN UPDATED
// ===============================
    private void doLogin(){

        String username =
                txtUser.getText().trim();

        String password =
                String.valueOf(
                        txtPass.getPassword()
                ).trim();

        if(username.isEmpty() || password.isEmpty()){

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Username and Password"
            );
            return;
        }

        String response =
                ApiService.login(
                        username,
                        password
                );

        System.out.println("LOGIN RESPONSE = " + response);

        if(response != null){

            Session.token =
                    extractValue(response,"token");

            if(Session.token.isEmpty())
                Session.token =
                        extractValue(response,"jwt");

            if(Session.token.isEmpty())
                Session.token =
                        extractValue(response,"accessToken");

            Session.role =
                    extractValue(response,"role");

            Session.username =
                    extractValue(response,"username");

            Session.tenantId =
                    extractValue(response,"tenantId");
            Session.email = extractValue(response,"email");

            System.out.println("TOKEN = " + Session.token);
            System.out.println("ROLE = " + Session.role);

            if(Session.token.isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Token Missing"
                );
                return;
            }

            dispose();

            if("ADMIN".equalsIgnoreCase(Session.role)) {

                new AdminDashboard();

            }
            else if("RECRUITER".equalsIgnoreCase(Session.role)) {

                new RecruiterDashboard();

            }
            else {

                new StudentDashboard();

            }

        }else{

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Login"
            );
        }
    }


    // ===============================
// extractValue UPDATED
// ===============================
    private String extractValue(String text, String key){

        try{

            String search =
                    "\"" + key + "\"";

            int start =
                    text.indexOf(search);

            if(start == -1){
                return "";
            }

            start =
                    text.indexOf(":", start) + 1;

            while(start < text.length() &&
                    (text.charAt(start) == ' ' ||
                            text.charAt(start) == '"')){

                start++;
            }

            int end = start;

            while(end < text.length() &&
                    text.charAt(end) != '"' &&
                    text.charAt(end) != ',' &&
                    text.charAt(end) != '}'){

                end++;
            }

            return text.substring(
                    start,
                    end
            ).trim();

        }catch(Exception e){

            e.printStackTrace();
        }

        return "";
    }
    // ===============================
    // BUTTON STYLE
    // ===============================
    private void styleButton(JButton btn){

        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setForeground(Color.WHITE);
        btn.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        btn.setBackground(
                new Color(0,140,255)
        );

        btn.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        btn.addMouseListener(
                new MouseAdapter() {

                    public void mouseEntered(
                            MouseEvent e){

                        btn.setBackground(
                                new Color(
                                        255,70,170
                                )
                        );
                    }

                    public void mouseExited(
                            MouseEvent e){

                        btn.setBackground(
                                new Color(
                                        0,140,255
                                )
                        );
                    }
                }
        );
    }

    private void styleSmallButton(JButton btn){

        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setForeground(Color.WHITE);

        btn.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        // Premium Blue Color
        btn.setBackground(
                new Color(58,123,213)
        );

        btn.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        btn.setOpaque(true);
        btn.setContentAreaFilled(true);

        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {

                btn.setBackground(
                        new Color(38,103,193)
                );
            }

            public void mouseExited(MouseEvent e) {

                btn.setBackground(
                        new Color(58,123,213)
                );
            }

            public void mousePressed(MouseEvent e) {

                btn.setBackground(
                        new Color(28,83,173)
                );
            }

            public void mouseReleased(MouseEvent e) {

                btn.setBackground(
                        new Color(38,103,193)
                );
            }
        });
    }

    private void styleField(JTextField txt){

        txt.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        17
                )
        );

        txt.setForeground(Color.BLACK);
        txt.setCaretColor(Color.BLACK);

        txt.setBackground(Color.WHITE);

        txt.setBorder(
                BorderFactory
                        .createCompoundBorder(
                                BorderFactory
                                        .createLineBorder(
                                                new Color(180,180,180),1
                                        ),
                                BorderFactory
                                        .createEmptyBorder(
                                                5,12,5,12
                                        )
                        )
        );
    }

    private void stylePasswordField(
            JPasswordField txt){

        txt.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        17
                )
        );

        txt.setForeground(Color.BLACK);
        txt.setCaretColor(Color.BLACK);
        txt.setEchoChar('*');

        txt.setBackground(Color.WHITE);

        txt.setBorder(
                BorderFactory
                        .createCompoundBorder(
                                BorderFactory
                                        .createLineBorder(
                                                new Color(180,180,180),1
                                        ),
                                BorderFactory
                                        .createEmptyBorder(
                                                5,12,5,12
                                        )
                        )
        );
    }
}
// ===============================
// PREMIUM BACKGROUND
// ===============================
class LoginBackground extends JPanel {

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // smooth graphics
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // ===============================
        // MAIN GRADIENT BG
        // ===============================
        GradientPaint gp = new GradientPaint(
                0, 0,
                new Color(5, 10, 35),
                getWidth(),
                getHeight(),
                new Color(20, 70, 160)
        );

        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // ===============================
        // GLOW CIRCLES
        // ===============================
        g2.setColor(new Color(0, 180, 255, 80));
        g2.fillOval(40, 70, 260, 260);

        g2.setColor(new Color(255, 70, 170, 70));
        g2.fillOval(180, 40, 180, 180);

        g2.setColor(new Color(255, 255, 255, 25));
        g2.fillOval(160, 320, 320, 320);

        g2.setColor(new Color(0, 255, 180, 40));
        g2.fillOval(930, 60, 220, 220);

        // ===============================
        // LEFT SIDE PREMIUM ICON
        // ===============================
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 110));
        g2.drawString("💼", 140, 250);

        // ===============================
        // LEFT SIDE TITLE
        // ===============================
        g2.setFont(new Font("Segoe UI", Font.BOLD, 44));
        g2.drawString("CareerConnect", 70, 355);

        // ===============================
        // LEFT SIDE SUBTITLE
        // ===============================
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        g2.setColor(new Color(235, 235, 235));
        g2.drawString("Premium Job Portal Experience", 78, 405);

        // ===============================
        // LEFT BOTTOM LINE
        // ===============================
        GradientPaint line = new GradientPaint(
                80, 435,
                new Color(0, 180, 255),
                420, 435,
                new Color(255, 70, 170)
        );

        g2.setPaint(line);
        g2.fillRoundRect(80, 430, 340, 5, 10, 10);

        // ===============================
        // RIGHT SIDE LOGIN GLASS PANEL
        // ===============================
        g2.setColor(new Color(255, 255, 255, 18));
        g2.fillRoundRect(650, 70, 420, 500, 35, 35);

        g2.setColor(new Color(255, 255, 255, 45));
        g2.drawRoundRect(650, 70, 420, 500, 35, 35);
    }
}
