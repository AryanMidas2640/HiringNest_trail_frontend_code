package ui;

import service.ApiService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
//import org.json.JSONArray;
//import org.json.JSONObject;

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

        sidebar.add(dashboardBtn);
        sidebar.add(studentsBtn);
        sidebar.add(recruitersBtn);
        sidebar.add(jobsBtn);
        sidebar.add(applicationsBtn);
        sidebar.add(reportsBtn);
        sidebar.add(notificationBtn);
        sidebar.add(settingsBtn);
        sidebar.add(logoutBtn);

       // String response = ApiService.getAllStudents();

        recruitersBtn.addActionListener(e -> {

            String response = ApiService.getAllRecruiters();

            JFrame frame = new JFrame("Recruiters List");
            frame.setSize(950, 550);
            frame.setLocationRelativeTo(null);

            String[] columns = {
                    "Company",
                    "Username",
                    "Email",
                    "Tenant ID",
                    "Status"
            };

            DefaultTableModel model = new DefaultTableModel(columns, 0);

            try {

                String[] items = response.split("\\{");

                for(String item : items){

                    if(!item.contains("username")) continue;

                    String company = getValue(item, "companyName");
                    String username = getValue(item, "username");
                    String email = getValue(item, "email");
                    String tenantId = getValue(item, "tenantId");
                    String online = getValue(item, "online");

                    model.addRow(new Object[]{
                            company,
                            username,
                            email,
                            tenantId,
                            online
                    });
                }

            } catch(Exception ex){
                ex.printStackTrace();
            }

            JTable table = new JTable(model);

            // ================= PREMIUM UI =================
            table.setRowHeight(40);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.setShowGrid(false);
            table.setIntercellSpacing(new Dimension(0,0));

            table.getTableHeader().setFont(
                    new Font("Segoe UI", Font.BOLD, 14)
            );

            table.setSelectionBackground(new Color(59,130,246));
            table.setSelectionForeground(Color.WHITE);

            JScrollPane pane = new JScrollPane(table);

            frame.add(pane);
            frame.setVisible(true);
        });










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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                AdminDashboard::new
        );
    }




}
