package ui;

import service.ApiService;
import util.Session;
import javax.swing.table.JTableHeader;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import javax.swing.table.DefaultTableModel;

//import static jdk.internal.org.jline.utils.InfoCmp.Capability.columns;

class StudentDashboard extends JFrame {


    public StudentDashboard() {

        setTitle("Student Dashboard");
        setSize(1200,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(new BackgroundPanel());
        setLayout(null);

        JLabel label = new JLabel("Welcome " + Session.username);
        label.setBounds(160,25,320,40);
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        add(label);

        JLabel sub = new JLabel("Find Your Dream Job Today");
        sub.setBounds(165,60,250,20);
        sub.setForeground(new Color(220,220,220));
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(sub);

        FancyButton btn1 = new FancyButton("View Recruiter Jobs");
        FancyButton btn2 = new FancyButton("All Jobs");
        FancyButton btn3 = new FancyButton("Search Job By ID");
        FancyButton btn4 = new FancyButton("Apply Job");
        FancyButton btn5 = new FancyButton("Logout");
        FancyButton btn6 = new FancyButton("My Applied Jobs");
        FancyButton resumeBtn = new FancyButton("Resume Parsing");

       // JButton btn5 = new JButton("Logout");
        btn5.setBounds(160,500,230,42);


        add(resumeBtn);

        add(btn1); add(btn2); add(btn3);
        add(btn4); add(btn6); add(btn5);

        ImageIcon icon = new ImageIcon(
                "C:\\Users\\DELL\\Downloads\\ProductService\\JobPortalFrontend\\src\\ui\\images\\img.png"
        );
        Image img = icon.getImage().getScaledInstance(420, 420, Image.SCALE_SMOOTH);

        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(700, 150, 420, 420); // RIGHT SIDE PERFECT

        add(imageLabel);

        getContentPane().setComponentZOrder(imageLabel, getContentPane().getComponentCount()-1);

        System.out.println(new java.io.File("src/ui/images/img.png").getAbsolutePath());
        System.out.println(new java.io.File("src/ui/images/img.png").exists());
        addHover(btn1);
        addHover(btn2);
        addHover(btn3);
        addHover(btn4);
        addHover(btn5);
        addHover(btn6);
        addHover(resumeBtn);

        btn1.setBounds(160,105,230,42);
        btn2.setBounds(160,155,230,42);
        btn3.setBounds(160,205,230,42);
        btn4.setBounds(160,255,230,42);
        btn6.setBounds(160,305,230,42);
        resumeBtn.setBounds(160,355,230,42);
       // btn5.setBounds(160,500,230,42);
        resumeBtn.setBounds(160,355,230,42);
        add(resumeBtn);


        resumeBtn.addActionListener(e -> {

            JFileChooser chooser =
                    new JFileChooser();

            int result =
                    chooser.showOpenDialog(null);

            if(result == JFileChooser.APPROVE_OPTION){

                java.io.File file =
                        chooser.getSelectedFile();

                String skills =
                        JOptionPane.showInputDialog(
                                "Enter Skills"
                        );

                String education =
                        JOptionPane.showInputDialog(
                                "Enter Education"
                        );

                if(skills == null || education == null)
                    return;

                String msg =
                        ApiService.uploadResume(
                                file,
                                skills,
                                education
                        );

                showResumeResult(msg);
            }
        });



        // Recruiter Jobs
        btn1.addActionListener(e -> {

            String tenantId =
                    JOptionPane.showInputDialog("Enter Recruiter TenantId");

            if(tenantId == null || tenantId.trim().isEmpty()) return;

            String data = ApiService.getJobsByTenant(tenantId);

            if(data.contains("ERROR")) {
                JOptionPane.showMessageDialog(null,"Error");
                return;
            }

            showJobsTable(data);
        });

        // All Jobs
        btn2.addActionListener(e -> {

            String data = ApiService.getAllJobs();

            if(data.contains("ERROR")) {
                JOptionPane.showMessageDialog(null,"Error");
                return;
            }

            showJobsTable(data);
        });

        // Search Job
        btn3.addActionListener(e -> {

            JDialog dialog = new JDialog((JFrame) null, true);
            dialog.setSize(420,260);
            dialog.setLocationRelativeTo(null);
            dialog.setUndecorated(true);

            // 🔥 MAIN CARD (rounded feel)
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(new Color(20,30,45));
            card.setBorder(BorderFactory.createLineBorder(new Color(0,153,255),2));

            // ================= HEADER =================
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(new Color(0,153,255));
            header.setPreferredSize(new Dimension(400,50));

            JLabel title = new JLabel("   🔍 Search Job");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Segoe UI", Font.BOLD, 17));

            JButton close = new JButton("X");
            close.setForeground(Color.WHITE);
            close.setBackground(new Color(0,153,255));
            close.setBorder(null);
            close.setFocusPainted(false);
            close.setCursor(new Cursor(Cursor.HAND_CURSOR));
            close.addActionListener(e1 -> dialog.dispose());

            header.add(title, BorderLayout.WEST);
            header.add(close, BorderLayout.EAST);

            // ================= BODY =================
            JPanel body = new JPanel();
            body.setLayout(null);
            body.setBackground(new Color(20,30,45));

            JLabel lbl = new JLabel("Enter Job ID");
            lbl.setBounds(40,35,200,20);
            lbl.setForeground(new Color(180,200,220));
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            JTextField field = new JTextField();
            field.setBounds(40,65,320,42);
            field.setFont(new Font("Segoe UI", Font.BOLD, 14));
            field.setForeground(Color.WHITE);
            field.setCaretColor(Color.WHITE);
            field.setBackground(new Color(35,50,70));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0,153,255),1,true),
                    BorderFactory.createEmptyBorder(5,12,5,12)
            ));

            // 🔥 focus glow
            field.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent e) {
                    field.setBorder(BorderFactory.createLineBorder(new Color(0,200,255),2,true));
                }
                public void focusLost(java.awt.event.FocusEvent e) {
                    field.setBorder(BorderFactory.createLineBorder(new Color(0,153,255),1,true));
                }
            });

            body.add(lbl);
            body.add(field);

            // ================= FOOTER =================
            JPanel footer = new JPanel();
            footer.setBackground(new Color(20,30,45));

            JButton searchBtn = new JButton("Search") {
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

                    GradientPaint gp = new GradientPaint(
                            0,0,new Color(0,180,255),
                            0,getHeight(),new Color(0,90,200)
                    );

                    g2.setPaint(gp);
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),25,25);

                    super.paintComponent(g);
                    g2.dispose();
                }
            };

            searchBtn.setForeground(Color.WHITE);
            searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            searchBtn.setBorder(BorderFactory.createEmptyBorder(10,40,10,40));
            searchBtn.setContentAreaFilled(false);
            searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // 🔥 hover glow
            searchBtn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e){
                    searchBtn.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(0,220,255),2,true),
                            BorderFactory.createEmptyBorder(8,38,8,38)
                    ));
                }
                public void mouseExited(MouseEvent e){
                    searchBtn.setBorder(BorderFactory.createEmptyBorder(10,40,10,40));
                }
            });

            // ================= LOGIC =================
            searchBtn.addActionListener(ev -> {

                String jobId = field.getText();

                if(jobId.trim().isEmpty()){
                    JOptionPane.showMessageDialog(dialog,"Enter Job ID");
                    return;
                }

                String data = ApiService.getJobById(jobId);

                if(data.contains("ERROR")){
                    JOptionPane.showMessageDialog(dialog,"Job Not Found");
                    return;
                }

                dialog.dispose();

                // 🔥 PREMIUM FLOW (no table, direct detail)
                showJobDetails(data);
            });

            footer.add(searchBtn);

            // ================= ADD =================
            card.add(header, BorderLayout.NORTH);
            card.add(body, BorderLayout.CENTER);
            card.add(footer, BorderLayout.SOUTH);

            dialog.add(card);
            dialog.setVisible(true);
        });

        // Apply Job
        btn4.addActionListener(e -> {

            JDialog dialog = new JDialog((JFrame) null, true);
            dialog.setSize(420,280);
            dialog.setLocationRelativeTo(null);
            dialog.setUndecorated(true); // 🔥 remove default title bar

            // 🔥 MAIN CARD
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout());
            card.setBackground(new Color(30,40,55));
            card.setBorder(BorderFactory.createLineBorder(new Color(0,153,255), 2));

            // 🔥 HEADER
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(new Color(0,153,255));
            header.setPreferredSize(new Dimension(400,45));

            JLabel title = new JLabel("   Apply Job");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));

            JButton close = new JButton("X");
            close.setForeground(Color.WHITE);
            close.setBackground(new Color(0,153,255));
            close.setBorder(null);
            close.setFocusPainted(false);
            close.addActionListener(ev -> dialog.dispose());

            header.add(title, BorderLayout.WEST);
            header.add(close, BorderLayout.EAST);

            // 🔥 BODY
            JPanel body = new JPanel(new GridLayout(2,2,15,15));
            body.setBorder(BorderFactory.createEmptyBorder(25,25,15,25));
            body.setBackground(new Color(30,40,55));

            JLabel jobLbl = new JLabel("Job ID:");
            jobLbl.setForeground(Color.WHITE);

            JTextField jobField = new JTextField();
            jobField.setBackground(new Color(45,60,80));
            jobField.setForeground(Color.WHITE);
            jobField.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

            JLabel statusLbl = new JLabel("Status:");
            statusLbl.setForeground(Color.WHITE);

            String[] options = {"Applied", "Hold", "Rejected"};
            JComboBox<String> statusBox = new JComboBox<>(options);
            statusBox.setBackground(new Color(45,60,80));
            statusBox.setForeground(Color.WHITE);

            body.add(jobLbl);
            body.add(jobField);
            body.add(statusLbl);
            body.add(statusBox);

            // 🔥 FOOTER BUTTON
            JPanel footer = new JPanel();
            footer.setBackground(new Color(30,40,55));

            JButton applyBtn = new JButton("Submit");
            applyBtn.setFocusPainted(false);
            applyBtn.setForeground(Color.WHITE);
            applyBtn.setBackground(new Color(0,153,255));
            applyBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            applyBtn.setBorder(BorderFactory.createEmptyBorder(8,25,8,25));

            applyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    applyBtn.setBackground(new Color(0,180,255));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    applyBtn.setBackground(new Color(0,153,255));
                }
            });

            applyBtn.addActionListener(ev -> {

                String jobId = jobField.getText();
                String status = (String) statusBox.getSelectedItem();

                if(jobId.trim().isEmpty()){
                    JOptionPane.showMessageDialog(dialog,"Enter Job ID");
                    return;
                }
                System.out.println("JOB: " + jobId + " STATUS: " + status);

                String msg = ApiService.applyJob(jobId ,status);
                JOptionPane.showMessageDialog(dialog,msg);

                dialog.dispose();
            });

            footer.add(applyBtn);

            // 🔥 ADD ALL
            card.add(header, BorderLayout.NORTH);
            card.add(body, BorderLayout.CENTER);
            card.add(footer, BorderLayout.SOUTH);

            dialog.add(card);
            dialog.setVisible(true);
        });

        // Applied Jobs
        btn6.addActionListener(e -> {

            String data = ApiService.myAppliedJobs();

            if(data.contains("ERROR")) {
                JOptionPane.showMessageDialog(null,"Unable to fetch");
                return;
            }

            showAppliedJobsTable(data);
        });

        // Logout
        // Logout
        btn5.addActionListener(e -> {

            ApiService.logout();   // backend logout hit hoga

            Session.token = "";
            Session.role = "";
            Session.username = "";
            Session.tenantId = "";

            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }

    private void showResumeResult(String json){

        JFrame frame = new JFrame("Parsed Resume");
        frame.setSize(900,550);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(15,25,35));
        frame.setLayout(new BorderLayout());

        // 🔥 TITLE
        JLabel title = new JLabel(" Resume Analysis Result", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(15,10,10,10));
        frame.add(title, BorderLayout.NORTH);

        // 🔥 DATA
        String[] columns = {"Field","Value"};
        DefaultTableModel model = new DefaultTableModel(columns,0);

        model.addRow(new Object[]{"👤 Name", getValue(json,"name")});
        model.addRow(new Object[]{"📧 Email", getValue(json,"email")});
        model.addRow(new Object[]{"📱 Phone", getValue(json,"phone")});
        model.addRow(new Object[]{"💻 Skills", getValue(json,"matchedSkills")});
        model.addRow(new Object[]{"🎓 Education", getValue(json,"education")});
        model.addRow(new Object[]{"📈 Experience", getValue(json,"experience")});
        model.addRow(new Object[]{"🎯 Matching Count", getValue(json,"matchingCount")});

        JTable table = new JTable(model);

        // 🔥 TABLE STYLE
        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(25,35,45));
        table.setSelectionBackground(new Color(0,153,255));
        table.setGridColor(new Color(60,60,60));
        table.setShowGrid(false);

        // 🔥 HEADER STYLE
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 17));
        table.getTableHeader().setBackground(new Color(0,153,255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        // 🔥 ONE RENDERER (CENTER + ZEBRA + PADDING)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                JLabel c = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                // ✅ CENTER ALIGN
                c.setHorizontalAlignment(SwingConstants.CENTER);

                // ✅ PADDING
                c.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

                // ✅ ZEBRA COLORS
                if(isSelected){
                    c.setBackground(new Color(0,153,255));
                } else {
                    if(row % 2 == 0)
                        c.setBackground(new Color(25,35,45));
                    else
                        c.setBackground(new Color(30,45,60));
                }

                c.setForeground(Color.WHITE);
                return c;
            }
        });

        // 🔥 SCROLL
        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.getViewport().setBackground(new Color(25,35,45));

        // 🔥 CARD PANEL
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(25,35,45));
        card.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        card.add(pane);

        frame.add(card, BorderLayout.CENTER);

        // 🔥 FOOTER
        JLabel footer = new JLabel(" Resume Parsed Successfully", SwingConstants.CENTER);
        footer.setForeground(new Color(0,255,150));
        footer.setFont(new Font("Segoe UI", Font.BOLD, 15));
        footer.setBorder(BorderFactory.createEmptyBorder(10,10,15,10));

        frame.add(footer, BorderLayout.SOUTH);

        System.out.println(json);

        frame.setVisible(true);
    }

    private void addHover(JButton btn){

        btn.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                btn.setLocation(btn.getX()-5, btn.getY()-3);
            }

            public void mouseExited(MouseEvent e) {
                btn.setLocation(btn.getX()+5, btn.getY()+3);
            }
        });
    }


    // ===============================
    // Stylish Button
    // ===============================




    class FancyButton extends JButton {

        private int rippleX = -1, rippleY = -1, rippleSize = 0;

        public FancyButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder());
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 15));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Ripple trigger
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    rippleX = e.getX();
                    rippleY = e.getY();
                    rippleSize = 0;

                    new Timer(10, ev -> {
                        rippleSize += 15;
                        repaint();
                        if(rippleSize > getWidth()*2){
                            ((Timer)ev.getSource()).stop();
                        }
                    }).start();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // 🔥 SHADOW
            g2.setColor(new Color(0,0,0,80));
            g2.fillRoundRect(5, 7, w-5, h-5, 30, 30);

            // 🔥 GLASS + GRADIENT
            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(0,180,255),
                    0, h, new Color(0,90,200)
            );
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, w, h, 30, 30);

            // 🔥 NEON GLOW (hover)
            if(getModel().isRollover()){
                g2.setColor(new Color(0,200,255,120));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(0,0,w-1,h-1,30,30);
            }

            // 🔥 RIPPLE EFFECT
            if(rippleSize > 0){
                g2.setColor(new Color(255,255,255,80));
                g2.fillOval(rippleX - rippleSize/2, rippleY - rippleSize/2,
                        rippleSize, rippleSize);
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ===============================
    // JOB TABLE
    // ===============================
    private void showJobsTable(String data){

        String[] columns = {"Job ID","Title","Company","City","Salary"};
        DefaultTableModel model = new DefaultTableModel(columns,0);

        // ✅ DATA PARSE
        data = data.replace("[","")
                .replace("]","")
                .replace("},{","}split{");

        String[] jobs = data.split("split");

        for(String job : jobs){

            if(job.trim().isEmpty()) continue;

            model.addRow(new Object[]{
                    getValue(job,"jobId"),
                    getValue(job,"jobTitle"),
                    getValue(job,"companyName"),
                    getValue(job,"city"),
                    getValue(job,"salary")
            });
        }

        // ✅ TABLE
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // ✅ SORTER
        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // ✅ DOUBLE CLICK FIX (IMPORTANT)
        // ✅ TABLE LISTENER
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {

                    int viewRow = table.rowAtPoint(e.getPoint());
                    if(viewRow == -1) return;

                    // ✅ VERY IMPORTANT FIX
                    int modelRow = table.convertRowIndexToModel(viewRow);

                    String jobId = table.getModel().getValueAt(modelRow, 0).toString();

                    System.out.println("CLICKED JOB ID: " + jobId);

                    String jobJson = ApiService.getJobById(jobId);

                    // 🔥 DEBUG
                    System.out.println("API RESPONSE: " + jobJson);

                    if(jobJson == null || jobJson.contains("ERROR") || jobJson.trim().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Job details not found!");
                        return;
                    }

                    showJobDetails(jobJson);
                }
            }
        });

        // ✅ SEARCH FIELD
        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(200,30));
        search.setBackground(new Color(25,35,45));
        search.setForeground(Color.WHITE);
        search.setCaretColor(Color.WHITE);
        search.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        // ✅ SEARCH LOGIC
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {

                String text = search.getText();

                if(text.trim().length() == 0){
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        // 🔥 STYLE
        table.setRowHeight(48);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(18,25,35));
        table.setSelectionBackground(new Color(0,153,255));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0,153,255));
        header.setForeground(Color.WHITE);

        // 🔥 COLUMN WIDTH
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(240);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);

        // 🔥 RENDERER
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column){

                JLabel c = (JLabel) super.getTableCellRendererComponent(
                        table,value,isSelected,hasFocus,row,column);

                c.setBorder(BorderFactory.createEmptyBorder(12,15,12,15));
                c.setHorizontalAlignment(SwingConstants.CENTER);

                if(column == 4){
                    try{
                        double salary = Double.parseDouble(value.toString());

                        if(salary > 500000){
                            c.setForeground(new Color(0,255,150));
                        } else if(salary > 200000){
                            c.setForeground(new Color(255,200,0));
                        } else {
                            c.setForeground(new Color(255,120,120));
                        }

                        c.setText("₹ " + value.toString());
                    }catch(Exception e){
                        c.setForeground(Color.WHITE);
                    }
                } else {
                    c.setForeground(Color.WHITE);
                }

                if(isSelected){
                    c.setBackground(new Color(0,153,255));
                } else {
                    c.setBackground(row % 2 == 0
                            ? new Color(22,30,40)
                            : new Color(26,36,48));
                }

                return c;
            }
        });

        // 🔥 SCROLL
        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createEmptyBorder());

        // 🔥 TOP PANEL (SEARCH)
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(18,25,35));

        JLabel title = new JLabel("🚀 Jobs");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        top.add(title, BorderLayout.WEST);
        top.add(search, BorderLayout.EAST);

        // 🔥 FRAME
        JFrame frame = new JFrame("Jobs Dashboard");
        frame.setSize(1050,550);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(top, BorderLayout.NORTH);
        frame.add(pane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // ===============================
    // Applied Jobs
    // ===============================
    private void showAppliedJobsTable(String data){

        String[] columns = {"Job ID","Title","Company","Status"};
        DefaultTableModel model = new DefaultTableModel(columns,0);

        data = data.replace("[","")
                .replace("]","")
                .replace("},{","}split{");

        String[] jobs = data.split("split");

        for(String job : jobs){

            if(job.trim().isEmpty()) continue;

            model.addRow(new Object[]{
                    getValue(job,"jobId"),
                    getValue(job,"jobTitle"),
                    getValue(job,"companyName"),
                    getValue(job,"status")
            });
        }

        JTable table = new JTable(model);

        // 🔥 BASE UI
        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setForeground(Color.WHITE);
        table.setBackground(new Color(18,25,35));
        table.setSelectionBackground(new Color(0,153,255));
        table.setGridColor(new Color(50,60,70));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setIntercellSpacing(new Dimension(0,0));

        // 🔥 HEADER
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0,153,255));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder());

        // 🔥 COLUMN WIDTH (professional spacing)
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(220);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);

        // 🔥 CUSTOM RENDERER (MAIN MAGIC)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                JLabel c = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                c.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));
                c.setHorizontalAlignment(SwingConstants.CENTER);

                // 🔥 STATUS BADGE (pill style)
                if(column == 3){
                    String status = value.toString().toLowerCase();

                    if(status.contains("applied")){
                        c.setBackground(new Color(46,204,113)); // green
                    } else if(status.contains("rejected")){
                        c.setBackground(new Color(231,76,60)); // red
                    } else {
                        c.setBackground(new Color(241,196,15)); // yellow
                    }

                    c.setForeground(Color.BLACK);
                    c.setHorizontalAlignment(SwingConstants.CENTER);
                    c.setOpaque(true);
                    return c;
                }

                // 🔥 ZEBRA ROWS
                if(isSelected){
                    c.setBackground(new Color(0,153,255));
                } else {
                    if(row % 2 == 0)
                        c.setBackground(new Color(22,30,40));
                    else
                        c.setBackground(new Color(26,36,48));
                }

                c.setForeground(Color.WHITE);
                return c;
            }
        });

        // 🔥 SCROLL (clean)
        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createEmptyBorder());
        pane.getViewport().setBackground(new Color(18,25,35));

        // 🔥 CARD PANEL (dashboard feel)
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(18,25,35));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(40,50,60),1,true),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));
        card.add(pane);

        // 🔥 FRAME
        JFrame frame = new JFrame("My Applied Jobs");
        frame.setSize(950,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(12,18,25));

        // 🔥 TITLE BAR
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(12,18,25));

        JLabel title = new JLabel("My Applications Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10,15,10,10));

        JLabel count = new JLabel("Total: " + model.getRowCount());
        count.setForeground(new Color(0,200,255));
        count.setFont(new Font("Segoe UI", Font.BOLD, 14));
        count.setBorder(BorderFactory.createEmptyBorder(10,10,10,15));

        top.add(title, BorderLayout.WEST);
        top.add(count, BorderLayout.EAST);

        frame.add(top, BorderLayout.NORTH);
        frame.add(card, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // ===============================
    // JSON Value Extractor
    // ===============================
    private String getValue(String text,String key){

        try{

            int start =
                    text.indexOf("\""+key+"\":\"");

            if(start!=-1){

                start += key.length()+4;

                int end =
                        text.indexOf("\"",start);

                return text.substring(start,end);
            }

            start =
                    text.indexOf("\""+key+"\":" );

            if(start!=-1){

                start += key.length()+3;

                int end =
                        text.indexOf(",",start);

                if(end==-1)
                    end=text.indexOf("}",start);

                return text.substring(start,end);
            }

        }catch(Exception e){}

        return "";
    }

    private void showJobDetails(String json){

        String title = getValue(json, "jobTitle");
        String company = getValue(json, "companyName");
        String city = getValue(json, "city");
        String type = getValue(json, "jobType");
        String mode = getValue(json, "workMode");
        String minExp = getValue(json, "minExperience");
        String maxExp = getValue(json, "maxExperience");
        String salary = getValue(json, "salary");
        String email = getValue(json, "email");
        String desc = getValue(json, "description");

        JDialog dialog = new JDialog((JFrame) null, true);
        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setUndecorated(true);

        // 🔥 MAIN GLASS CARD
        JPanel card = new JPanel(new BorderLayout()){
            protected void paintComponent(Graphics g){
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0,0,new Color(20,30,45),
                        getWidth(),getHeight(),new Color(10,20,35)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0,0,getWidth(),getHeight(),25,25);

                g2.setColor(new Color(0,153,255,120));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1,1,getWidth()-3,getHeight()-3,25,25);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // 🔥 HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel titleLbl = new JLabel("💼 " + title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLbl.setForeground(Color.WHITE);

        JButton close = new JButton("✕");
        close.setForeground(Color.WHITE);
        close.setBackground(new Color(255,80,80));
        close.setFocusPainted(false);
        close.setBorder(BorderFactory.createEmptyBorder(5,12,5,12));
        close.addActionListener(e -> dialog.dispose());

        header.add(titleLbl, BorderLayout.WEST);
        header.add(close, BorderLayout.EAST);

        // 🔥 INFO GRID
        JPanel grid = new JPanel(new GridLayout(0,2,15,15));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));

        grid.add(createInfoCard("🏢 Company", company));
        grid.add(createInfoCard("📍 City", city));
        grid.add(createInfoCard("📄 Type", type));
        grid.add(createInfoCard("🏠 Mode", mode));
        grid.add(createInfoCard("📊 Experience", minExp + " - " + maxExp));
        grid.add(createInfoCard("💰 Salary", "₹ " + salary));
        grid.add(createInfoCard("📧 Email", email));

        // 🔥 DESCRIPTION CARD
        JTextArea area = new JTextArea(desc);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBackground(new Color(30,40,55));
        area.setForeground(Color.WHITE);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JScrollPane descPane = new JScrollPane(area);
        descPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0,153,255)),
                "📝 Description",
                0,0,
                new Font("Segoe UI", Font.BOLD, 14),
                Color.WHITE
        ));

        // 🔥 FOOTER BUTTON
        JButton apply = new JButton("Apply Now");
        apply.setBackground(new Color(0,153,255));
        apply.setForeground(Color.WHITE);
        apply.setFont(new Font("Segoe UI", Font.BOLD, 14));
        apply.setFocusPainted(false);
        apply.setBorder(BorderFactory.createEmptyBorder(10,25,10,25));

        apply.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                apply.setBackground(new Color(0,180,255));
            }
            public void mouseExited(MouseEvent e){
                apply.setBackground(new Color(0,153,255));
            }
        });

        apply.addActionListener(e -> {
            ApiService.applyJob(getValue(json,"jobId"), "Applied");
            JOptionPane.showMessageDialog(dialog,"Applied Successfully 🚀");
        });

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.add(apply);

        // 🔥 ADD ALL
        card.add(header, BorderLayout.NORTH);
        card.add(grid, BorderLayout.CENTER);
        card.add(descPane, BorderLayout.SOUTH);
        card.add(footer, BorderLayout.PAGE_END);

        dialog.add(card);
        dialog.setVisible(true);
    }

    private JPanel createInfoCard(String label, String value){

        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(new Color(30,40,55));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60,80,100)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        JLabel l1 = new JLabel(label);
        l1.setForeground(new Color(0,200,255));
        l1.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel l2 = new JLabel(value);
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        box.add(l1, BorderLayout.NORTH);
        box.add(l2, BorderLayout.CENTER);

        return box;
    }



}


// ===============================
// Premium Gradient Background
// ===============================
class BackgroundPanel extends JPanel {

    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gp = new GradientPaint(
                0,0,new Color(15,32,39),
                getWidth(),getHeight(),
                new Color(32,58,67)
        );

        g2d.setPaint(gp);
        g2d.fillRect(0,0,getWidth(),getHeight());

        g2d.setColor(new Color(255,255,255,15));

        for(int i=0;i<15;i++){
            g2d.fillOval(
                    (int)(Math.random()*getWidth()),
                    (int)(Math.random()*getHeight()),
                    80,80
            );
        }
    }
}

