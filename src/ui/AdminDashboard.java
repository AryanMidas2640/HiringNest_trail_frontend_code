package ui;

import service.ApiService;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import javax.swing.*;
import javax.swing.border.*;
//import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
//import org.json.JSONArray;
//import org.json.JSONObject;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class AdminDashboard extends JFrame {


    private final Color SIDEBAR = new Color(15, 23, 42);
    private final Color PRIMARY = new Color(59, 130, 246);
    private final Color BACKGROUND = new Color(241, 245, 249);
    private final Color CARD = Color.WHITE;
    private JButton activeButton;

    public AdminDashboard() {

        initUI();
        initHeader();
        initSidebar();
        initCenter();

        setTitle("Admin Dashboard");
        setSize(1450, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // ================= HEADER =================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 75));
        header.setBorder(new EmptyBorder(10, 25, 10, 25));

        JLabel dashboardTitle = new JLabel("Admin Dashboard");
        dashboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel adminLabel = new JLabel("👤 Welcome, Admin");
        adminLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        header.add(dashboardTitle, BorderLayout.WEST);
        header.add(adminLabel, BorderLayout.EAST);

        // ================= SIDEBAR =================

        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setLayout(new GridLayout(10, 1, 0, 10));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel logo = new JLabel("PLACEMENT PORTAL");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        sidebar.add(logo);

        JButton dashboardBtn = createSidebarButton("Dashboard", "dashboard");

        JButton studentsBtn = createSidebarButton("Students", "students");

        JButton recruitersBtn = createSidebarButton("Recruiters", "recruiters");

        JButton jobsBtn = createSidebarButton("Jobs", "jobs");

        JButton applicationsBtn = createSidebarButton("Applications", "applications");

        JButton reportsBtn = createSidebarButton("Reports", "reports");

        JButton notificationBtn = createSidebarButton("Notifications", "notifications");

        JButton settingsBtn = createSidebarButton("Settings", "setingss");

        JButton logoutBtn = createSidebarButton("Logout", "logouts");

        recruitersBtn.addActionListener(e -> openRecruiters());

        sidebar.add(dashboardBtn);
        sidebar.add(studentsBtn);
        sidebar.add(recruitersBtn);
        sidebar.add(jobsBtn);
        sidebar.add(applicationsBtn);
        sidebar.add(reportsBtn);
        sidebar.add(notificationBtn);
        sidebar.add(settingsBtn);
        sidebar.add(logoutBtn);

        jobsBtn.addActionListener(e -> openJobs());
/*
        jobsBtn.addActionListener(e -> {

            String response = ApiService.getAllJobs();

            JFrame frame = new JFrame("Jobs Control Center");
            frame.setSize(1250, 750);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());
            frame.getContentPane().setBackground(new Color(8, 12, 25));

            // ================= HEADER =================
            JPanel header1 = new JPanel(new BorderLayout());
            header1.setBackground(new Color(15, 20, 40));
            header1.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

            JLabel title = new JLabel("💼 Jobs Control Center");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Segoe UI", Font.BOLD, 30));

            header.add(title, BorderLayout.WEST);

            // ================= MODEL =================
            String[] columns = {
                    "Job ID", "Title", "Company", "City",
                    "Type", "Mode", "Experience", "Salary", "Email"
            };

            DefaultTableModel model = new DefaultTableModel(columns, 0);

            int total = 0;

            try {

                if (response != null && !response.isEmpty()) {

                    response = response.trim();

                    if (response.startsWith("[")) response = response.substring(1);
                    if (response.endsWith("]")) response = response.substring(0, response.length() - 1);

                    String[] items = response.split("\\},\\s*\\{");

                    for (String item : items) {

                        item = item.replace("{", "").replace("}", "");

                        String jobId = getValue(item, "jobId");
                        if (jobId == null || jobId.isEmpty()) continue;

                        String titleJob = getValue(item, "jobTitle");
                        String company = getValue(item, "companyName");
                        String city = getValue(item, "city");
                        String jobType = getValue(item, "jobType");
                        String workMode = getValue(item, "workMode");
                        String salary = getValue(item, "salary");
                        String email = getValue(item, "email");

                        String minExp = getValue(item, "minExperience");
                        String maxExp = getValue(item, "maxExperience");

                        String exp = minExp + " - " + maxExp + " yrs";

                        model.addRow(new Object[]{
                                jobId, titleJob, company, city,
                                jobType, workMode, exp, salary, email
                        });

                        total++;
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // ================= KPI =================
            JPanel kpi = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
            kpi.setBackground(new Color(15, 20, 40));

            kpi.add(createMiniCard("Total Jobs", String.valueOf(total), new Color(99,102,241)));

            header.add(kpi, BorderLayout.SOUTH);

            // ================= TABLE =================
            JTable table = new JTable(model) {

                public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {

                    Component c = super.prepareRenderer(r, row, col);

                    if (!isRowSelected(row)) {
                        c.setBackground(row % 2 == 0
                                ? new Color(18, 24, 40)
                                : new Color(22, 30, 50));
                    } else {
                        c.setBackground(new Color(59, 130, 246));
                    }

                    c.setForeground(Color.WHITE);
                    return c;
                }
            };

            table.setRowHeight(46);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.setShowGrid(false);
            table.setIntercellSpacing(new Dimension(0, 0));

            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            table.getTableHeader().setBackground(new Color(15, 20, 40));
            table.getTableHeader().setForeground(new Color(148, 163, 184));

            table.setSelectionBackground(new Color(59, 130, 246));
            table.setSelectionForeground(Color.WHITE);

            // ================= SEARCH =================
            JTextField search = new JTextField();
            search.setPreferredSize(new Dimension(360, 38));
            search.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            search.setBackground(new Color(18, 24, 40));
            search.setForeground(Color.WHITE);
            search.setCaretColor(Color.WHITE);
            search.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            search.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent e) {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search.getText()));
                }
            });

            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            searchPanel.setBackground(new Color(8, 12, 25));
            searchPanel.add(search);

            // ================= WRAPPER =================
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setBackground(new Color(8, 12, 25));
            wrapper.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.getViewport().setBackground(new Color(8, 12, 25));

            wrapper.add(searchPanel, BorderLayout.NORTH);
            wrapper.add(scroll, BorderLayout.CENTER);

            // ================= FINAL =================
            frame.add(header, BorderLayout.NORTH);
            frame.add(wrapper, BorderLayout.CENTER);

            frame.setVisible(true);
        });

 */

       // String response = ApiService.getAllStudents();




        studentsBtn.addActionListener(e -> {

            String response = ApiService.getAllStudents();

            System.out.println(response);
            System.out.println("RESPONSE = ");
            System.out.println(response);

            JFrame frame = new JFrame("Students List");
            frame.setSize(900, 500);
            frame.setLocationRelativeTo(null);

            String[] columns = {
                    "Username",
                    "Email",
                    "Role",
                    "Tenant ID",
                    "Online"
            };

            DefaultTableModel model =
                    new DefaultTableModel(columns, 0);

            try {

                String[] users = response.split("\"id\"");

                System.out.println("TOTAL USERS = " + (users.length - 1));

                for(String user : users){

                    if(!user.contains("username"))
                        continue;

                    String username = getValue(user,"username");
                    String email = getValue(user,"email");
                    String role = getValue(user,"role");
                    String tenantId = getValue(user,"tenantId");
                    String online = getValue(user,"online");

                    System.out.println("USERNAME = " + username);

                    model.addRow(new Object[]{
                            username,
                            email,
                            role,
                            tenantId,
                            online
                    });
                }

            } catch(Exception ex){
                ex.printStackTrace();
            }

            JTable table = new JTable(model){

                @Override
                public Component prepareRenderer(
                        javax.swing.table.TableCellRenderer renderer,
                        int row,
                        int column){

                    Component c =
                            super.prepareRenderer(
                                    renderer,row,column);

                    if(!isRowSelected(row)){

                        if(row % 2 == 0){
                            c.setBackground(Color.WHITE);
                        }else{
                            c.setBackground(
                                    new Color(245,248,252)
                            );
                        }
                    }

                    return c;
                }
            };

            table.setRowHeight(38);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 15));

            table.getTableHeader().setFont(
                    new Font("Segoe UI", Font.BOLD, 16)
            );

            table.getTableHeader().setBackground(
                    new Color(59,130,246)
            );

            table.getTableHeader().setForeground(
                    Color.WHITE
            );

            table.setSelectionBackground(
                    new Color(220,235,255)
            );

            table.setAutoCreateRowSorter(true);

// CENTER ALIGN
            javax.swing.table.DefaultTableCellRenderer center =
                    new javax.swing.table.DefaultTableCellRenderer();

            center.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            for(int i=0;i<table.getColumnCount();i++){
                table.getColumnModel()
                        .getColumn(i)
                        .setCellRenderer(center);
            }

// SEARCH BOX
            JTextField searchField = new JTextField();
            searchField.setFont(
                    new Font("Segoe UI", Font.PLAIN, 15)
            );

            javax.swing.table.TableRowSorter<DefaultTableModel>
                    sorter =
                    new javax.swing.table.TableRowSorter<>(model);

            table.setRowSorter(sorter);

            searchField.addKeyListener(
                    new KeyAdapter() {
                        public void keyReleased(KeyEvent e) {

                            sorter.setRowFilter(
                                    RowFilter.regexFilter(
                                            "(?i)" +
                                                    searchField.getText()
                                    )
                            );
                        }
                    }
            );

// TOP PANEL
            JPanel topPanel =
                    new JPanel(new BorderLayout());

            JLabel title =
                    new JLabel(
                            "🎓 Students Management"
                    );

            title.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            24
                    )
            );

            JLabel count =
                    new JLabel(
                            "Total Students : "
                                    + model.getRowCount()
                    );

            count.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            16
                    )
            );

            topPanel.add(title, BorderLayout.WEST);
            topPanel.add(count, BorderLayout.EAST);

            JScrollPane pane =
                    new JScrollPane(table);

            frame.setLayout(
                    new BorderLayout(10,10)
            );

            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(pane, BorderLayout.CENTER);
            frame.add(searchField, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
        // ================= CENTER =================

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(BACKGROUND);

        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        cardsPanel.setBackground(BACKGROUND);
        cardsPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        cardsPanel.add(
                createCard(
                        "Total Students",
                        ApiService.getStudentCount()
                )
        );

        cardsPanel.add(
                createCard(
                        "Recruiters",
                        ApiService.getRecruiterCount()
                )
        );

        cardsPanel.add(
                createCard(
                        "Jobs Posted",
                        ApiService.getJobCount()
                )
        );

        cardsPanel.add(
                createCard(
                        "Applications",
                        ApiService.getApplicationCount()
                )
        );

        // ================= TABLE =================

        String[] columns = {
                "Activity",
                "Date",
                "Status"
        };

        Object[][] data = {
                {"Student Registered", "01/06/2026", "Completed"},
                {"Recruiter Added Job", "02/06/2026", "Completed"},
                {"Application Submitted", "03/06/2026", "Pending"},
                {"Interview Scheduled", "04/06/2026", "Completed"},
                {"Offer Released", "05/06/2026", "Completed"}
        };

        JTable table = new JTable(
                new DefaultTableModel(data, columns)
        );

        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Recent Activities"
                )
        );

        center.add(cardsPanel, BorderLayout.NORTH);
        center.add(scrollPane, BorderLayout.CENTER);

        // ================= ADD =================

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(center, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }
    private void openJobs() {

        String response = ApiService.getAllJobs();

        JFrame frame = new JFrame("Jobs Control Center");
        frame.setSize(1300, 850);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(248, 250, 252));

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(22, 35, 15, 35));

        JLabel title = new JLabel("💼 Jobs Control Center");
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));
        title.setForeground(new Color(15, 23, 42));

        JLabel subtitle = new JLabel("Manage all job postings in one unified SaaS dashboard");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(100, 116, 139));

        JPanel titleBox = new JPanel(new GridLayout(2, 1));
        titleBox.setBackground(Color.WHITE);
        titleBox.add(title);
        titleBox.add(subtitle);

        header.add(titleBox, BorderLayout.WEST);

        // ================= MODEL =================
        String[] columns = {
                "Job ID", "Title", "Company", "City",
                "Type", "Mode", "Experience", "Salary", "Email"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        int total = 0;

        try {
            if (response != null && !response.isEmpty()) {

                response = response.trim();
                if (response.startsWith("[")) response = response.substring(1);
                if (response.endsWith("]")) response = response.substring(0, response.length() - 1);

                String[] items = response.split("\\},\\s*\\{");

                for (String item : items) {

                    item = item.replace("{", "").replace("}", "");

                    String jobId = getValue(item, "jobId");
                    if (jobId == null || jobId.isEmpty()) continue;

                    String titleJob = getValue(item, "jobTitle");
                    String company = getValue(item, "companyName");
                    String city = getValue(item, "city");
                    String jobType = getValue(item, "jobType");
                    String workMode = getValue(item, "workMode");
                    String salary = getValue(item, "salary");
                    String email = getValue(item, "email");

                    String exp = getValue(item, "minExperience") + " - " +
                            getValue(item, "maxExperience") + " yrs";

                    model.addRow(new Object[]{
                            jobId, titleJob, company, city,
                            jobType, workMode, exp, salary, email
                    });

                    total++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ================= KPI CARDS =================
        // ================= KPI CARDS FIX =================
        JPanel kpi = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        kpi.setBackground(new Color(248, 250, 252)); // light page background

        kpi.add(createMiniCard("Total Jobs", String.valueOf(total), new Color(59,130,246)));
        kpi.add(createMiniCard("Active", String.valueOf(total), new Color(34,197,94)));
        kpi.add(createMiniCard("Draft", "0", new Color(245,158,11)));

        header.add(kpi, BorderLayout.SOUTH);

        // ================= TABLE =================
        JTable table = new JTable(model) {

            public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {

                Component c = super.prepareRenderer(r, row, col);

                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                } else {
                    c.setBackground(new Color(219, 234, 254));
                }

                c.setForeground(new Color(15, 23, 42));

                if (c instanceof JComponent jc) {
                    jc.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
                }

                return c;
            }
        };

        table.setRowHeight(52);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(15, 23, 42));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setSelectionBackground(new Color(191, 219, 254));
        table.setSelectionForeground(new Color(15, 23, 42));

        table.setAutoCreateRowSorter(true);

        // ================= SEARCH BAR =================
        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(450, 44));
        search.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        search.setBackground(Color.WHITE);
        search.setForeground(new Color(15, 23, 42));
        search.setCaretColor(new Color(59,130,246));
        search.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226,232,240)),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search.getText()));
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(248, 250, 252));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        searchPanel.add(search);

        // ================= WRAPPER =================
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(248, 250, 252));
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 35, 30, 35));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(226,232,240)));
        scroll.getViewport().setBackground(Color.WHITE);

        wrapper.add(searchPanel, BorderLayout.NORTH);
        wrapper.add(scroll, BorderLayout.CENTER);

        // ================= FINAL =================
        frame.add(header, BorderLayout.NORTH);
        frame.add(wrapper, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void initCenter() {
    }

    private void initSidebar() {
    }

    private void initHeader() {
    }

    private void initUI() {
    }

    private JPanel createCard(String title, String value) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                new Color(220,220,220),1,true
                        ),
                        new EmptyBorder(20,20,20,20)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 18)
        );

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 42)
        );
        valueLabel.setForeground(PRIMARY);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }



    private String getValue(String text, String key) {

        try {

            String search = "\"" + key + "\":";

            int start = text.indexOf(search);

            if(start == -1){
                return "";
            }

            start += search.length();

            while(start < text.length() &&
                    (text.charAt(start) == '"' ||
                            text.charAt(start) == ' ')){
                start++;
            }

            int end = start;

            while(end < text.length() &&
                    text.charAt(end) != '"' &&
                    text.charAt(end) != ',' &&
                    text.charAt(end) != '}'){
                end++;
            }

            return text.substring(start,end);

        } catch(Exception e){
            return "";
        }

        



    }
    private ImageIcon loadIcon(String name) {

        java.net.URL url = getClass().getResource("/style/" + name + ".png");

        if (url == null) {
            System.out.println("ICON NOT FOUND: " + name);
            return null;
        }

        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        return new ImageIcon(img);
    }

    private JButton createSidebarButton(String text, String iconName) {

        JButton button = new JButton(text);

        final String ICON_PATH =
                "C:\\Users\\DELL\\Downloads\\ProductService\\JobPortalFrontend\\src\\style\\";

        String path = ICON_PATH + iconName + ".png";

        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);

        button.setIcon(new ImageIcon(img));

        button.setIconTextGap(12);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.setForeground(Color.WHITE);
        button.setBackground(SIDEBAR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setFont(new Font("Segoe UI", Font.BOLD, 15));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != activeButton)
                    button.setBackground(PRIMARY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != activeButton)
                    button.setBackground(SIDEBAR);
            }
        });

        button.addActionListener(e -> {
            if (activeButton != null)
                activeButton.setBackground(SIDEBAR);

            activeButton = button;
            activeButton.setBackground(PRIMARY);
        });

        return button;
    }
    private void openRecruiters() {

        String response = ApiService.getAllRecruiters();

        JFrame frame = new JFrame("Recruiters Dashboard");
        frame.setSize(1250, 780);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(8, 10, 20));

        // ================= HEADER (GLASS STYLE) =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(12, 16, 32));
        header.setBorder(BorderFactory.createEmptyBorder(25, 30, 15, 30));

        JLabel title = new JLabel("🏢 Recruiters Control Center");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));

        header.add(title, BorderLayout.WEST);

        // ================= MODEL =================
        String[] columns = {"Company", "Username", "Email", "Tenant ID", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        int onlineCount = 0;

        try {
            if (response != null && !response.isEmpty()) {

                String[] items = response.split("\\{");

                for (String item : items) {

                    if (!item.contains("username")) continue;

                    String company = getValue(item, "companyName");
                    if (company == null || company.isEmpty()) company = "N/A";

                    String username = getValue(item, "username");
                    String email = getValue(item, "email");
                    String tenantId = getValue(item, "tenantId");
                    String online = getValue(item, "online");

                    if ("true".equalsIgnoreCase(online)) onlineCount++;

                    model.addRow(new Object[]{
                            company, username, email, tenantId, online
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int total = model.getRowCount();

        // ================= KPI SECTION =================
        JPanel kpi = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 10));
        kpi.setBackground(new Color(12, 16, 32));

        kpi.add(createMiniCard("TOTAL", String.valueOf(total), new Color(99,102,241)));
        kpi.add(createMiniCard("ONLINE", String.valueOf(onlineCount), new Color(34,197,94)));
        kpi.add(createMiniCard("OFFLINE", String.valueOf(total - onlineCount), new Color(239,68,68)));

        header.add(kpi, BorderLayout.SOUTH);

        // ================= TABLE =================
        JTable table = new JTable(model) {

            public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {

                Component c = super.prepareRenderer(r, row, col);

                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0
                            ? new Color(17, 22, 40)
                            : new Color(20, 26, 48));
                } else {
                    c.setBackground(new Color(59, 130, 246));
                }

                c.setForeground(Color.WHITE);
                return c;
            }
        };

        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(12, 16, 32));
        table.getTableHeader().setForeground(new Color(148, 163, 184));

        table.setSelectionBackground(new Color(59, 130, 246));
        table.setSelectionForeground(Color.WHITE);

        // ================= STATUS PILL =================
        table.getColumnModel().getColumn(4).setCellRenderer((t, value, isSelected, hasFocus, row, col) -> {

            boolean online = "true".equalsIgnoreCase(String.valueOf(value));

            JLabel label = new JLabel(online ? "● ONLINE" : "● OFFLINE");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            label.setFont(new Font("Segoe UI", Font.BOLD, 11));
            label.setForeground(Color.WHITE);
            label.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));

            if (online) label.setBackground(new Color(34, 197, 94));
            else label.setBackground(new Color(239, 68, 68));

            return label;
        });

        // ================= SEARCH BAR (MODERN PILL) =================
        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(380, 40));
        search.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        search.setBackground(new Color(17, 22, 40));
        search.setForeground(Color.WHITE);
        search.setCaretColor(Color.WHITE);
        search.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + search.getText()));
            }
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(8, 10, 20));
        searchPanel.add(search);

        // ================= TABLE WRAPPER (CARD EFFECT) =================
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(new Color(12, 16, 32));
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new Color(12, 16, 32));

        tableWrapper.add(searchPanel, BorderLayout.NORTH);
        tableWrapper.add(scroll, BorderLayout.CENTER);

        // ================= FINAL =================
        frame.add(header, BorderLayout.NORTH);
        frame.add(tableWrapper, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    private JPanel createMiniCard(String title, String value, Color color) {

        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(160, 70));

        // ❌ REMOVE DARK BACKGROUND
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        JLabel t = new JLabel(title);
        t.setForeground(new Color(100, 116, 139));
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel v = new JLabel(value);
        v.setForeground(color);
        v.setFont(new Font("Segoe UI", Font.BOLD, 20));

        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);

        return card;
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                AdminDashboard::new
        );
    }




}
