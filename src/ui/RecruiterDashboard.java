package ui;

import service.ApiService;
import util.Session;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RecruiterDashboard extends JFrame {

    JPanel sideMenu;
    Timer slider;
    boolean menuOpen = false;

    JLabel totalJobsLbl;
    JLabel appliedLbl;
    JLabel noApplyLbl;

    JTable activeTable;
    DefaultTableModel activeModel;

    public RecruiterDashboard() {

        setTitle("Recruiter Premium Dashboard");
        setSize(1280, 1000);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(new RecruiterPremiumBG());
        setLayout(null);

        // =====================================================
        // TOP BAR
        // =====================================================

        JButton menuBtn = new JButton("☰");
        menuBtn.setBounds(20, 18, 50, 50);
        styleMenu(menuBtn);
        add(menuBtn);

        JLabel title = new JLabel("Welcome " + Session.username);
        title.setBounds(860, 16, 380, 40);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        add(title);

        JLabel sub = new JLabel("Recruiter Smart Hiring Dashboard");
        sub.setBounds(865, 55, 350, 20);
        sub.setForeground(new Color(220,220,220));
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(sub);

        // =====================================================
        // SIDE MENU
        // =====================================================

        sideMenu = new JPanel(null);
        sideMenu.setBounds(-240, 0, 240, 760);
        sideMenu.setBackground(new Color(7,15,38,245));
        add(sideMenu);

        JLabel sideTitle = new JLabel("Recruiter");
        sideTitle.setBounds(90, 28, 150, 30);
        sideTitle.setForeground(Color.WHITE);
        sideTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        sideMenu.add(sideTitle);

        JButton btn1 = menuButton("Post Job", 120);
        JButton btn2 = menuButton("My Jobs", 185);
        JButton btn3 = menuButton("Applicants", 250);
        JButton btn4 = menuButton("Logout", 650);

        sideMenu.add(btn1);
        sideMenu.add(btn2);
        sideMenu.add(btn3);
        sideMenu.add(btn4);

        // =====================================================
        // CARDS
        // =====================================================

        JPanel c1 = premiumCard("Total Jobs", 130, 170);
        totalJobsLbl = countLabel();
        c1.add(totalJobsLbl);

        JPanel c2 = premiumCard("Applied Jobs", 485, 170);
        appliedLbl = countLabel();
        c2.add(appliedLbl);

        JPanel c3 = premiumCard("Not Applied", 840, 170);
        noApplyLbl = countLabel();
        c3.add(noApplyLbl);

        add(c1);
        add(c2);
        add(c3);

        // =====================================================
        // QUICK BUTTONS
        // =====================================================

        JButton q1 = actionBtn("Post Job", 180, 500);
        JButton q2 = actionBtn("View Jobs", 500, 500);
        JButton q3 = actionBtn("Applicants", 820, 500);

        add(q1);
        add(q2);
        add(q3);


        // =====================================================
// STEP 2:
// Constructor me QUICK BUTTONS wale section ke JUST NICHE
// add(q1); add(q2); add(q3); ke baad ye add karo
// =====================================================

        JLabel live = new JLabel("Live Status");
        live.setBounds(530, 650, 350, 28);
        live.setForeground(Color.WHITE);
        live.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(live);

        String[] cols = {
                "Name",
                "Status",
                "Login Time",
                "Logout Time"
        };

        activeModel = new DefaultTableModel(cols,0);

       /* activeTable = new JTable(activeModel);
        activeTable.setRowHeight(28);
        activeTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        activeTable.setForeground(Color.WHITE);
        activeTable.setBackground(new Color(18,28,58));
        activeTable.setGridColor(new Color(0,145,255));

        */


        activeTable = new JTable(activeModel);
        activeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        activeTable.getColumnModel().getColumn(0).setPreferredWidth(130);
        activeTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        activeTable.getColumnModel().getColumn(2).setPreferredWidth(290);
        activeTable.getColumnModel().getColumn(3).setPreferredWidth(290);


        activeTable.setRowHeight(34);
        activeTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        activeTable.setForeground(Color.WHITE);
        activeTable.setBackground(new Color(14,22,48));
        activeTable.setSelectionBackground(new Color(255,70,160));
        activeTable.setSelectionForeground(Color.WHITE);
        activeTable.setShowGrid(false);
        activeTable.setIntercellSpacing(new Dimension(0,0));
        activeTable.setFocusable(false);
        activeTable.setBorder(null);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int col) {

                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, col);

                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setForeground(Color.WHITE);
                lbl.setOpaque(true);

                // selected row
                if(isSelected){
                    lbl.setBackground(new Color(255,70,160));
                }
                else{
                    // zebra rows
                    if(row % 2 == 0)
                        lbl.setBackground(new Color(18,28,58));
                    else
                        lbl.setBackground(new Color(12,20,44));
                }

                // status column color
                if(col == 1 && value != null){
                    String s = value.toString().toLowerCase();

                    if(s.equals("online"))
                        lbl.setForeground(new Color(0,255,140));

                    else if(s.equals("offline"))
                        lbl.setForeground(new Color(255,120,120));
                }

                return lbl;
            }
        };

        for(int i=0; i<activeTable.getColumnCount(); i++){
            activeTable.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JTableHeader h = activeTable.getTableHeader();
        h.setPreferredSize(new Dimension(100,38));
        h.setFont(new Font("Segoe UI", Font.BOLD, 15));
        h.setForeground(Color.WHITE);
        h.setBackground(new Color(0,145,255));
        h.setBorder(BorderFactory.createLineBorder(new Color(0,180,255)));
/*
        JScrollPane sp = new JScrollPane(activeTable);
        sp.setBounds(15, 70, 895, 210);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(new Color(10,18,45));
        add(sp);

 */

        h = activeTable.getTableHeader();
        h.setPreferredSize(new Dimension(100, 35));
        h.setBackground(new Color(0,145,255));
        h.setForeground(Color.WHITE);
        h.setFont(new Font("Segoe UI", Font.BOLD, 14));

        activeTable.setShowGrid(false);
        activeTable.setIntercellSpacing(new Dimension(0,0));
        activeTable.setFillsViewportHeight(true);
        activeTable.setBorder(null);

        /*JTableHeader h = activeTable.getTableHeader();
        h.setBackground(new Color(0,145,255));
        h.setForeground(Color.WHITE);
        h.setFont(new Font("Segoe UI", Font.BOLD, 14));

         */

        h = activeTable.getTableHeader();
        h.setBackground(new Color(0,145,255));
        h.setForeground(Color.WHITE);
        h.setFont(new Font("Segoe UI", Font.BOLD, 14));
        h.setBorder(null);

       /* JScrollPane sp = new JScrollPane(activeTable);
        sp.setBounds(220, 600, 850, 115);
        add(sp);

        */
        JScrollPane sp = new JScrollPane(activeTable);
        sp.setBounds(170,700,850,230);
        sp.setBorder(null);
        sp.getViewport().setBackground(new Color(18,28,58));

        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(sp);

        loadActiveUsers();
        new Timer(5000, e -> loadActiveUsers()).start();


        // =====================================================
        // EVENTS
        // =====================================================

        menuBtn.addActionListener(e -> animateMenu());

        btn1.addActionListener(e -> showPostJobForm());
        btn2.addActionListener(e -> showMyJobs());
        btn3.addActionListener(e -> showApplicants());

        q1.addActionListener(e -> showPostJobForm());
        q2.addActionListener(e -> showMyJobs());
        q3.addActionListener(e -> showApplicants());

        btn4.addActionListener(e -> {

            try {
                ApiService.logout();   // backend logout call
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            dispose();
            new LoginFrame();
        });

        loadCounts();

        setVisible(true);
    }

    // =====================================================
    private void animateMenu() {

        slider = new Timer(5, null);

        slider.addActionListener(e -> {

            int x = sideMenu.getX();

            if (!menuOpen) {

                if (x < 0)
                    sideMenu.setBounds(x + 20, 0, 240, 760);
                else {
                    sideMenu.setBounds(0,0,240,760);
                    slider.stop();
                    menuOpen = true;
                }

            } else {

                if (x > -240)
                    sideMenu.setBounds(x - 20,0,240,760);
                else {
                    sideMenu.setBounds(-240,0,240,760);
                    slider.stop();
                    menuOpen = false;
                }
            }
        });

        slider.start();
    }

    // =====================================================
    private void loadCounts() {

        try {

            String jobs = ApiService.myPostedJobs();
            String apps = ApiService.myApplicants();

            int total = (jobs != null && jobs.contains("jobId"))
                    ? jobs.split("jobId").length - 1 : 0;

            int applied = (apps != null && apps.contains("jobId"))
                    ? apps.split("jobId").length - 1 : 0;

            int no = total - applied;
            if(no < 0) no = 0;

            totalJobsLbl.setText("" + total);
            appliedLbl.setText("" + applied);
            noApplyLbl.setText("" + no);

        } catch(Exception e){

            totalJobsLbl.setText("0");
            appliedLbl.setText("0");
            noApplyLbl.setText("0");
        }
    }

    // =====================================================
    // POST JOB FORM (FIXED)
    // =====================================================

    private void showPostJobForm() {

        JDialog dialog = new JDialog(this, true);
        dialog.setSize(620, 760);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        dialog.setLayout(new BorderLayout());

        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel main = new JPanel(null);
        main.setBackground(new Color(7,15,38));
        main.setBorder(BorderFactory.createLineBorder(
                new Color(0,145,255), 2));

        // =====================================================
        // TOP HEADER
        // =====================================================

        JPanel top = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0,0,new Color(0,145,255),
                        getWidth(),0,new Color(120,40,255)
                );
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };

        top.setLayout(null);
        top.setBounds(0,0,620,85);

        JLabel head = new JLabel("Post New Job");
        head.setBounds(25,18,260,35);
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Segoe UI", Font.BOLD, 30));

        JLabel sub = new JLabel("Premium Recruiter Hiring Panel");
        sub.setBounds(28,50,260,18);
        sub.setForeground(new Color(235,235,235));
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton close = new JButton("✕");
        close.setBounds(560,20,40,35);
        close.setBackground(new Color(255,70,160));
        close.setForeground(Color.WHITE);
        close.setFont(new Font("Segoe UI", Font.BOLD, 18));
        close.setBorder(null);
        close.setFocusPainted(false);
        close.addActionListener(e -> dialog.dispose());

        top.add(head);
        top.add(sub);
        top.add(close);

        main.add(top);

        // =====================================================
        // FIELDS
        // =====================================================

        JTextField id      = premiumField(main,"Job ID",120);
        JTextField title   = premiumField(main,"Job Title",190);
        JTextField company = premiumField(main,"Company",260);
        JTextField city    = premiumField(main,"City",330);
        JTextField type    = premiumField(main,"Job Type",400);
        JTextField mode    = premiumField(main,"Work Mode",470);
        JTextField salary  = premiumField(main,"Salary",540);
        JTextField email   = premiumField(main,"Email",610);

        // =====================================================
        // BUTTONS
        // =====================================================

        JButton post = new JButton("Post Job");
        post.setBounds(120,680,170,45);
        post.setBackground(new Color(0,145,255));
        post.setForeground(Color.WHITE);
        post.setFont(new Font("Segoe UI", Font.BOLD, 17));
        post.setBorder(null);
        post.setFocusPainted(false);
        post.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(330,680,170,45);
        cancel.setBackground(new Color(255,70,160));
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        cancel.setBorder(null);
        cancel.setFocusPainted(false);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        main.add(post);
        main.add(cancel);

        cancel.addActionListener(e -> dialog.dispose());

        // =====================================================
        // POST ACTION
        // =====================================================

        post.addActionListener(e -> {

            String msg = ApiService.postJob(
                    id.getText(),
                    title.getText(),
                    company.getText(),
                    city.getText(),
                    type.getText(),
                    mode.getText(),
                    salary.getText(),
                    email.getText()
            );

            dialog.dispose();

            SwingUtilities.invokeLater(() -> {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Job Posted Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );



                loadCounts();
                revalidate();
                repaint();
            });
        });

        // =====================================================

        dialog.add(main);
        dialog.setVisible(true);
    }

    // =====================================================
    // PREMIUM TABLE
    // =====================================================

    private void premiumTable(String title, JTable table) {

        JDialog d = new JDialog(this, true);
        d.setSize(1120, 650);
        d.setLocationRelativeTo(this);
        d.setUndecorated(true);
        d.setLayout(new BorderLayout());

        // =====================================================
        // MAIN CONTAINER WITH SHADOW LOOK
        // =====================================================

        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(new Color(0,0,0));

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(7,15,38));
        main.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,170,255), 2),
                BorderFactory.createEmptyBorder(2,2,2,2)
        ));

        // =====================================================
        // TOP HEADER PREMIUM
        // =====================================================

        JPanel top = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0,0,new Color(0,145,255),
                        getWidth(),0,new Color(90,40,255)
                );
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };

        top.setPreferredSize(new Dimension(1000, 72));

        JLabel heading = new JLabel("   " + title);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));

        JLabel sub = new JLabel("Premium Recruiter Analytics");
        sub.setForeground(new Color(230,230,230));
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel left = new JPanel(new GridLayout(2,1));
        left.setOpaque(false);
        left.add(heading);
        left.add(sub);

        JButton closeTop = new JButton("✕");
        closeTop.setFocusPainted(false);
        closeTop.setBorder(null);
        closeTop.setForeground(Color.WHITE);
        closeTop.setBackground(new Color(255,70,160));
        closeTop.setFont(new Font("Segoe UI", Font.BOLD, 22));
        closeTop.setPreferredSize(new Dimension(70,72));
        closeTop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeTop.addActionListener(e -> d.dispose());

        top.add(left, BorderLayout.WEST);
        top.add(closeTop, BorderLayout.EAST);

        // =====================================================
        // TABLE STYLING
        // =====================================================

        table.setRowHeight(38);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(18,28,58));
        table.setSelectionBackground(new Color(255,70,160));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(40,70,120));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(0,1));

        JTableHeader h = table.getTableHeader();
        h.setPreferredSize(new Dimension(100,42));
        h.setFont(new Font("Segoe UI", Font.BOLD, 15));
        h.setBackground(new Color(0,145,255));
        h.setForeground(Color.WHITE);
        h.setReorderingAllowed(false);

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        center.setForeground(Color.WHITE);
        center.setBackground(new Color(18,28,58));

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));
        sp.getViewport().setBackground(new Color(18,28,58));

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER,15,15));
        bottom.setBackground(new Color(7,15,38));

        JButton refresh = new JButton("Refresh");
        refresh.setPreferredSize(new Dimension(150,42));
        refresh.setBackground(new Color(0,145,255));
        refresh.setForeground(Color.WHITE);
        refresh.setFont(new Font("Segoe UI", Font.BOLD, 15));
        refresh.setBorder(null);
        refresh.setFocusPainted(false);

        JButton close = new JButton("Close");
        close.setPreferredSize(new Dimension(150,42));
        close.setBackground(new Color(255,70,160));
        close.setForeground(Color.WHITE);
        close.setFont(new Font("Segoe UI", Font.BOLD, 15));
        close.setBorder(null);
        close.setFocusPainted(false);
        close.addActionListener(e -> d.dispose());

        refresh.addActionListener(e -> {
            d.dispose();
            if(title.equalsIgnoreCase("My Jobs"))
                showMyJobs();
            else
                showApplicants();
        });

        bottom.add(refresh);
        bottom.add(close);

        // =====================================================

        main.add(top, BorderLayout.NORTH);
        main.add(sp, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        outer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        outer.add(main);

        d.add(outer);
        d.setVisible(true);
    }

    // =====================================================
    private void showMyJobs() {

        String json = ApiService.myPostedJobs();

        String[] cols = {
                "Job ID","Title","Company","City","Type","Mode","Salary"
        };

        DefaultTableModel model = new DefaultTableModel(cols,0);

        try {

            json = json.replace("[","").replace("]","");
            String[] rows = json.split("\\},\\{");

            for(String row : rows){

                row = row.replace("{","").replace("}","");

                model.addRow(new Object[]{
                        getValue(row,"jobId"),
                        getValue(row,"jobTitle"),
                        getValue(row,"companyName"),
                        getValue(row,"city"),
                        getValue(row,"jobType"),
                        getValue(row,"workMode"),
                        getValue(row,"salary")
                });
            }

            premiumTable("My Jobs", new JTable(model));

        } catch(Exception e){
            JOptionPane.showMessageDialog(this,json);
        }
    }

    // =====================================================
    private void showApplicants() {

        String json = ApiService.myApplicants();

        String[] cols = {
                "Student","Username","Job ID","Title","Company","Status"
        };

        DefaultTableModel model = new DefaultTableModel(cols,0);

        try {

            json = json.replace("[","").replace("]","");
            String[] rows = json.split("\\},\\{");

            for(String row : rows){

                row = row.replace("{","").replace("}","");

                model.addRow(new Object[]{
                        getValue(row,"studentName"),
                        getValue(row,"studentUsername"),
                        getValue(row,"jobId"),
                        getValue(row,"jobTitle"),
                        getValue(row,"companyName"),
                        getValue(row,"status")
                });
            }

            premiumTable("Applicants", new JTable(model));

        } catch(Exception e){
            JOptionPane.showMessageDialog(this,json);
        }
    }

    // =====================================================
// ACTIVE USERS TABLE DATA LOAD
// =====================================================

    private void loadActiveUsers() {

        try {
            activeModel.setRowCount(0);

            String json = ApiService.getActiveUsers();

            json = json.replace("[", "").replace("]", "");

            String[] rows = json.split("\\},\\{");

            java.util.LinkedHashMap<String, Object[]> map =
                    new java.util.LinkedHashMap<>();

            for(String row : rows){

                row = row.replace("{","").replace("}","");

                String username   = getValue(row,"username");
                String status     = getValue(row,"status");
                String loginTime  = getValue(row,"loginTime");
                String logoutTime = getValue(row,"logoutTime");

                map.put(username, new Object[]{
                        username,
                        status,
                        loginTime,
                        logoutTime
                });
            }

            for(Object[] data : map.values()){
                activeModel.addRow(data);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // =====================================================
    private JTextField premiumField(JPanel panel, String text, int y) {

        JLabel lbl = new JLabel(text);
        lbl.setBounds(55, y - 22, 220, 20);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lbl);

        JTextField tf = new JTextField();
        tf.setBounds(55, y, 510, 42);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBackground(new Color(18,28,58));

        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0,145,255), 2),
                BorderFactory.createEmptyBorder(5,12,5,12)
        ));

        panel.add(tf);

        return tf;
    }
/*
    private String getValue(String text, String key){

        try{

            // ===============================
            // STRING VALUE
            // ===============================
            String search1 = "\"" + key + "\":\"";
            int start = text.indexOf(search1);

            if(start != -1){

                start = start + search1.length();
                int end = text.indexOf("\"", start);

                return text.substring(start, end);
            }

            // ===============================
            // NUMBER VALUE
            // ===============================
            String search2 = "\"" + key + "\":";
            start = text.indexOf(search2);

            if(start != -1){

                start = start + search2.length();

                while(start < text.length() &&
                        text.charAt(start) == ' '){
                    start++;
                }

                int end = text.indexOf(",", start);

                if(end == -1){
                    end = text.length();
                }

                return text.substring(start, end)
                        .replace("}", "")
                        .trim();
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return "";
    }

 */
private String getValue(String text, String key) {

    try {

        String search = "\"" + key + "\":";
        int start = text.indexOf(search);

        if(start == -1) return "";

        start += search.length();

        while(start < text.length() &&
                (text.charAt(start) == ' ' || text.charAt(start) == '\"')) {
            start++;
        }

        int end = start;

        while(end < text.length() &&
                text.charAt(end) != ',' &&
                text.charAt(end) != '}'
        ) {
            end++;
        }

        String val = text.substring(start, end)
                .replace("\"", "")
                .trim();

        return val;

    } catch(Exception e) {
        return "";
    }
}

    // =====================================================
    private JPanel premiumCard(String title, int x, int y){

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(x,y,280,170);
        p.setBackground(new Color(255,255,255,40));

        JLabel lbl = new JLabel(title,SwingConstants.CENTER);
        lbl.setBounds(20,25,240,30);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI",Font.BOLD,22));
        p.add(lbl);

        return p;
    }

    private JLabel countLabel(){

        JLabel l = new JLabel("0",SwingConstants.CENTER);
        l.setBounds(20,75,240,45);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI",Font.BOLD,46));
        return l;
    }

    private JButton menuButton(String text,int y){

        JButton b = new JButton(text);
        b.setBounds(20,y,190,45);
        styleSideButton(b);
        hover(b);
        return b;
    }

    private JButton actionBtn(String text,int x,int y){

        JButton b = new JButton(text);
        b.setBounds(x,y,220,50);
        b.setFont(new Font("Segoe UI",Font.BOLD,18));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(0,145,255));
        b.setBorder(null);
        b.setFocusPainted(false);
        hover(b);
        return b;
    }

    private void hover(JButton b){

        Color normal = b.getBackground();

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(255,70,160));
            }

            public void mouseExited(MouseEvent e) {
                b.setBackground(normal);
            }
        });
    }

    private void styleMenu(JButton b){

        b.setBorder(null);
        b.setFocusPainted(false);
        b.setBackground(new Color(0,145,255));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI",Font.BOLD,18));
    }

    private void styleSideButton(JButton b){

        b.setFocusPainted(false);
        b.setBackground(new Color(0,145,255));
        b.setForeground(Color.WHITE);
        b.setHorizontalAlignment(SwingConstants.CENTER);
        b.setFont(new Font("Segoe UI",Font.BOLD,17));
        b.setBorder(null);
    }
}

// =====================================================

class RecruiterPremiumBG extends JPanel {

    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        GradientPaint gp = new GradientPaint(
                0,0,new Color(3,18,52),
                getWidth(),getHeight(),
                new Color(15,80,160));

        g2.setPaint(gp);
        g2.fillRect(0,0,getWidth(),getHeight());
    }
}