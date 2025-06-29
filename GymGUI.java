import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * GymGUI class provides a graphical user interface for managing
 * gym members including regular and premium members.
 * Now with data persistence capabilities and improved contrast.
 */
public class GymGUI extends JFrame {
    // ArrayList to store both RegularMember and PremiumMember objects
    private ArrayList<GymMember> members;

    // Text fields for member information
    private JTextField idField, nameField, locationField, phoneField, emailField;
    private JTextField referralField, paidAmountField, removalReasonField, trainerNameField;
    private JTextField regularPlanPriceField, premiumPlanChargeField, discountAmountField;
    private JTextField loyaltyPointsField; // Added loyalty points field

    // Radio buttons for gender
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderGroup;

    // ComboBoxes for dates and plan
    private JComboBox<String> dobDayComboBox, dobMonthComboBox, dobYearComboBox;
    private JComboBox<String> msDayComboBox, msMonthComboBox, msYearComboBox;
    private JComboBox<String> planComboBox;

    // Buttons for actions
    private JButton addRegularMemberButton, addPremiumMemberButton;
    private JButton activateMembershipButton, deactivateMembershipButton;
    private JButton markAttendanceButton, upgradePlanButton;
    private JButton calculateDiscountButton, payDueAmountButton;
    private JButton revertRegularMemberButton, revertPremiumMemberButton;
    private JButton displayButton, clearButton, saveButton; // Added save button

    // Constants for plans and pricing
    private final String[] PLANS = {"Basic", "Standard", "Deluxe"};
    private final double BASIC_PRICE = 6500;
    private final double STANDARD_PRICE = 12500;
    private final double DELUXE_PRICE = 18500;
    private final double PREMIUM_CHARGE = 50000;

    // File to store member data
    private final String DATA_FILE = "gym_members.docx";

    // Updated colors for enhanced contrast and modern design
    private final Color HEADER_COLOR = new Color(41, 128, 185);
    private final Color PANEL_COLOR = new Color(236, 240, 241);
    private final Color BUTTON_COLOR = new Color(39, 174, 96);
    private final Color BUTTON_TEXT_COLOR = new Color(12, 12, 12);
    private final Color TEXT_COLOR = new Color(44, 62, 80);
    private final Color FORM_BG_COLOR = new Color(245, 245, 245);
    private final Color ACCENT_COLOR = new Color(231, 76, 60);
    private final Color TAB_SELECTED_COLOR = new Color(52, 152, 219);

    /**
     * Constructor initializes the GUI components and sets up the layout
     */
    public GymGUI() {
        // Initialize member ArrayList
        members = new ArrayList<>();

        // Set up the JFrame
        setTitle("Gym Membership Management System");
        setSize(950, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(FORM_BG_COLOR);

        // Create the main panel with a border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(FORM_BG_COLOR);

        // Add header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Initialize all buttons
        initializeButtons();

        // Create and add the input panel
        JPanel inputPanel = createInputPanel();
        mainPanel.add(new JScrollPane(inputPanel), BorderLayout.CENTER);

        // Create and add the button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);

        // Load existing members from file
        loadMembersFromFile();

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    /**
     * Creates a header panel with logo and title
     * @return JPanel containing the header
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HEADER_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = HEADER_COLOR;
                Color color2 = new Color(52, 152, 219); // Lighter blue
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("GYM MEMBERSHIP MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Add a logo on the left with improved design
        JPanel logoPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int size = Math.min(getWidth(), getHeight()) - 10;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                // Draw outer circle
                g.setColor(Color.WHITE);
                g.fillOval(x, y, size, size);

                // Draw inner circle
                g.setColor(ACCENT_COLOR);
                g.fillOval(x + 5, y + 5, size - 10, size - 10);

                // Draw text
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics fm = g.getFontMetrics();
                String text = "GYM";
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getHeight();
                g.drawString(text, x + (size - textWidth) / 2, y + (size + textHeight) / 2 - 2);
            }
        };
        logoPanel.setPreferredSize(new Dimension(80, 50));
        logoPanel.setOpaque(false);
        titlePanel.add(logoPanel, BorderLayout.WEST);

        panel.add(titlePanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Initializes all the buttons
     */
    private void initializeButtons() {
        // Member Management Buttons
        addRegularMemberButton = createStyledButton("Add Regular Member");
        addRegularMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRegularMember();
            }
        });

        addPremiumMemberButton = createStyledButton("Add Premium Member");
        addPremiumMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPremiumMember();
            }
        });

        activateMembershipButton = createStyledButton("Activate Membership");
        activateMembershipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activateMembership();
            }
        });

        deactivateMembershipButton = createStyledButton("Deactivate Membership");
        deactivateMembershipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deactivateMembership();
            }
        });

        // Attendance & Plans Buttons
        markAttendanceButton = createStyledButton("Mark Attendance");
        markAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAttendance();
            }
        });

        upgradePlanButton = createStyledButton("Upgrade Plan");
        upgradePlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upgradePlan();
            }
        });

        // Financial Buttons
        calculateDiscountButton = createStyledButton("Calculate Discount");
        calculateDiscountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateDiscount();
            }
        });

        payDueAmountButton = createStyledButton("Pay Due Amount");
        payDueAmountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payDueAmount();
            }
        });

        // Administrative Buttons
        revertRegularMemberButton = createStyledButton("Revert Regular Member");
        revertRegularMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revertRegularMember();
            }
        });

        revertPremiumMemberButton = createStyledButton("Revert Premium Member");
        revertPremiumMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revertPremiumMember();
            }
        });

        displayButton = createStyledButton("Display Members");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMembers();
            }
        });

        clearButton = createStyledButton("Clear Fields");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        // New Save Button
        saveButton = createStyledButton("Save on File");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMembersToFile();
            }
        });
    }

    /**
     * Creates a styled button with hover effects and improved contrast
     * @param text Button text
     * @return Styled JButton
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR); // White text for better contrast
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(22, 160, 133), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15) // Increased padding
        ));

        // Add hover effect with better contrast
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(22, 160, 133)); // Darker green
                button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    /**
     * Creates and returns the panel for input fields
     * @return JPanel containing all input components
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 12, 12)); // Increased spacing
        panel.setBackground(FORM_BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Member Basic Information section
        addSectionHeader(panel, "Member Basic Information");

        // ID - Only allowing integers
        panel.add(createLabelWithIcon("Member ID (Numbers only):", "id"));
        idField = new JTextField(10);
        idField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                String text = field.getText().trim();
                if (text.isEmpty()) return true;

                try {
                    Integer.parseInt(text);
                    return true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(GymGUI.this,
                            "Member ID must contain only numbers!",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });
        panel.add(createStyledTextField(idField));

        // Name
        panel.add(createLabelWithIcon("Name:", "person"));
        nameField = new JTextField(20);
        panel.add(createStyledTextField(nameField));

        // Location
        panel.add(createLabelWithIcon("Location:", "location"));
        locationField = new JTextField(20);
        panel.add(createStyledTextField(locationField));

        // Phone
        panel.add(createLabelWithIcon("Phone:", "phone"));
        phoneField = new JTextField(15);
        panel.add(createStyledTextField(phoneField));

        // Email
        panel.add(createLabelWithIcon("Email:", "email"));
        emailField = new JTextField(30);
        panel.add(createStyledTextField(emailField));

        // Gender (radio buttons)
        panel.add(createLabelWithIcon("Gender:", "gender"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.setBackground(FORM_BG_COLOR);
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        styleRadioButton(maleRadioButton);
        styleRadioButton(femaleRadioButton);
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        panel.add(genderPanel);

        // Dates section
        addSectionHeader(panel, "Dates Information");

        // Date of Birth
        panel.add(createLabelWithIcon("Date of Birth:", "calendar"));
        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dobPanel.setBackground(FORM_BG_COLOR);

        // Day combo box
        String[] days = generateDays();
        dobDayComboBox = new JComboBox<>(days);
        styleComboBox(dobDayComboBox);

        // Month combo box
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        dobMonthComboBox = new JComboBox<>(months);
        styleComboBox(dobMonthComboBox);

        // Year combo box
        String[] years = generateYears(1950, 2025);
        dobYearComboBox = new JComboBox<>(years);
        styleComboBox(dobYearComboBox);

        dobPanel.add(new JLabel("Day:"));
        dobPanel.add(dobDayComboBox);
        dobPanel.add(new JLabel("Month:"));
        dobPanel.add(dobMonthComboBox);
        dobPanel.add(new JLabel("Year:"));
        dobPanel.add(dobYearComboBox);
        panel.add(dobPanel);

        // Membership Start Date
        panel.add(createLabelWithIcon("Membership Start Date:", "calendar"));
        JPanel msPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        msPanel.setBackground(FORM_BG_COLOR);

        // Day combo box
        msDayComboBox = new JComboBox<>(days);
        styleComboBox(msDayComboBox);

        // Month combo box
        msMonthComboBox = new JComboBox<>(months);
        styleComboBox(msMonthComboBox);

        // Year combo box
        msYearComboBox = new JComboBox<>(years);
        styleComboBox(msYearComboBox);

        msPanel.add(new JLabel("Day:"));
        msPanel.add(msDayComboBox);
        msPanel.add(new JLabel("Month:"));
        msPanel.add(msMonthComboBox);
        msPanel.add(new JLabel("Year:"));
        msPanel.add(msYearComboBox);
        panel.add(msPanel);

        // Membership Details section
        addSectionHeader(panel, "Membership Details");

        // Plan (for Regular Members)
        panel.add(createLabelWithIcon("Plan (Regular Members Only):", "plan"));
        planComboBox = new JComboBox<>(PLANS);
        styleComboBox(planComboBox);
        panel.add(planComboBox);

        // Referral Source
        panel.add(createLabelWithIcon("Referral Source:", "referral"));
        referralField = new JTextField(20);
        panel.add(createStyledTextField(referralField));

        // Paid Amount
        panel.add(createLabelWithIcon("Paid Amount:", "money"));
        paidAmountField = new JTextField(10);
        panel.add(createStyledTextField(paidAmountField));

        // Removal Reason
        panel.add(createLabelWithIcon("Removal Reason:", "note"));
        removalReasonField = new JTextField(30);
        panel.add(createStyledTextField(removalReasonField));

        // Trainer's Name (for Premium Members only)
        panel.add(createLabelWithIcon("Trainer's Name (Premium Members Only):", "trainer"));
        trainerNameField = new JTextField(20);
        trainerNameField.setEnabled(true); // Disabled by default, enabled only for premium members
        JPanel trainerPanel = createStyledTextField(trainerNameField);
        panel.add(trainerPanel);

        // Information Display section
        addSectionHeader(panel, "Information Display");

        // Non-editable fields
        panel.add(createLabelWithIcon("Regular Plan Price:", "price"));
        regularPlanPriceField = new JTextField(10);
        regularPlanPriceField.setEditable(false);
        panel.add(createDisplayField(regularPlanPriceField));

        panel.add(createLabelWithIcon("Premium Plan Charge:", "price"));
        premiumPlanChargeField = new JTextField(10);
        premiumPlanChargeField.setEditable(false);
        premiumPlanChargeField.setText(String.format("%.2f", PREMIUM_CHARGE));
        panel.add(createDisplayField(premiumPlanChargeField));

        panel.add(createLabelWithIcon("Discount Amount:", "discount"));
        discountAmountField = new JTextField(10);
        discountAmountField.setEditable(false);
        panel.add(createDisplayField(discountAmountField));

        // Loyalty Points field
        panel.add(createLabelWithIcon("Loyalty Points:", "loyalty"));
        loyaltyPointsField = new JTextField(10);
        loyaltyPointsField.setEditable(false);
        loyaltyPointsField.setText("0");
        panel.add(createDisplayField(loyaltyPointsField));

        // Update plan price when plan changes
        planComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlanPrice();
            }
        });

        // Initialize plan price
        updatePlanPrice();

        // Add action listeners for premium member selection
        addPremiumMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trainerNameField.setEnabled(true);
            }
        });

        addRegularMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trainerNameField.setEnabled(false);
                trainerNameField.setText("");
            }
        });

        return panel;
    }

    /**
     * Creates a styled label with an icon
     * @param text Label text
     * @param iconType Type of icon
     * @return Styled JLabel
     */
    private JLabel createLabelWithIcon(String text, String iconType) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13)); // Slightly larger font
        label.setForeground(TEXT_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        return label;
    }

    /**
     * Adds a section header to the panel with improved styling
     * @param panel Panel to add the header to
     * @param text Header text
     */
    private void addSectionHeader(JPanel panel, String text) {
        JLabel sectionLabel = new JLabel(text);
        sectionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        sectionLabel.setForeground(HEADER_COLOR);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(FORM_BG_COLOR);
        headerPanel.add(sectionLabel, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, HEADER_COLOR), // Thicker underline
                BorderFactory.createEmptyBorder(12, 5, 7, 5) // More space above/below
        ));

        panel.add(headerPanel);
        panel.add(new JLabel()); // Empty label for layout
    }

    /**
     * Creates a styled text field container with improved design
     * @param field The text field to style
     * @return JPanel containing the styled text field
     */
    private JPanel createStyledTextField(JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FORM_BG_COLOR);

        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8) // Increased padding
        ));

        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates a styled display field (non-editable) with better contrast
     * @param field The text field to style
     * @return JPanel containing the styled field
     */
    private JPanel createDisplayField(JTextField field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FORM_BG_COLOR);

        field.setFont(new Font("Arial", Font.BOLD, 13)); // Bold for better readability
        field.setBackground(new Color(236, 240, 241)); // Light grey background
        field.setForeground(new Color(41, 128, 185)); // Blue text for better visibility
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8) // Increased padding
        ));

        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Styles a radio button with improved contrast
     * @param button Radio button to style
     */
    private void styleRadioButton(JRadioButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 13));
        button.setBackground(PANEL_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
    }

    /**
     * Styles a combo box with improved design
     * @param comboBox Combo box to style
     */
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(TEXT_COLOR);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        ((JComponent) comboBox.getRenderer()).setOpaque(true);
    }

    /**
     * Creates and returns the panel for action buttons with improved layout
     * @return JPanel containing all buttons
     */
    private JPanel createButtonPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 8, 8)); // Increased spacing
        mainPanel.setBackground(FORM_BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        // Panel 1: Member Management
        JPanel memberPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        memberPanel.setBackground(PANEL_COLOR);
        memberPanel.setBorder(createTitledBorder("Member Management"));
        memberPanel.add(addRegularMemberButton);
        memberPanel.add(addPremiumMemberButton);
        memberPanel.add(activateMembershipButton);
        memberPanel.add(deactivateMembershipButton);

        // Panel 2: Attendance & Plans
        JPanel attendancePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        attendancePanel.setBackground(PANEL_COLOR);
        attendancePanel.setBorder(createTitledBorder("Attendance & Plans"));
        attendancePanel.add(markAttendanceButton);
        attendancePanel.add(upgradePlanButton);

        // Panel 3: Financial
        JPanel financialPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        financialPanel.setBackground(PANEL_COLOR);
        financialPanel.setBorder(createTitledBorder("Financial"));
        financialPanel.add(calculateDiscountButton);
        financialPanel.add(payDueAmountButton);

        // Panel 4: Administrative
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        adminPanel.setBackground(PANEL_COLOR);
        adminPanel.setBorder(createTitledBorder("Administrative"));
        adminPanel.add(revertRegularMemberButton);
        adminPanel.add(revertPremiumMemberButton);
        adminPanel.add(displayButton);
        adminPanel.add(clearButton);
        adminPanel.add(saveButton); // Added save button

        // Add panels to main panel
        mainPanel.add(memberPanel);
        mainPanel.add(attendancePanel);
        mainPanel.add(financialPanel);
        mainPanel.add(adminPanel);

        return mainPanel;
    }

    /**
     * Creates a titled border for button panels with improved styling
     * @param title The title text
     * @return Border with title
     */
    private Border createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(HEADER_COLOR, 2), // Thicker border
                title,
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                HEADER_COLOR);
    }

    /**
     * Updates the regular plan price field based on the selected plan
     */
    private void updatePlanPrice() {
        String selectedPlan = (String) planComboBox.getSelectedItem();
        double price = 0.0;

        if (selectedPlan.equals("Basic")) {
            price = BASIC_PRICE;
        } else if (selectedPlan.equals("Standard")) {
            price = STANDARD_PRICE;
        } else if (selectedPlan.equals("Deluxe")) {
            price = DELUXE_PRICE;
        }

        regularPlanPriceField.setText(String.format("%.2f", price));

        // Calculate and display discount amount (10% of the plan price)
        double discount = price * 0.1; // 10% discount
        discountAmountField.setText(String.format("%.2f", discount));
    }

    /**
     * Adds a new Regular Member to the system
     */
    private void addRegularMember() {
        // Validate ID first - must be integer
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = idText;

        // Check for ID duplication
        if (isMemberIdDuplicate(id)) {
            JOptionPane.showMessageDialog(this, "Member ID already exists. Each member must have a unique ID.",
                    "Duplicate ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get values from fields
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String location = locationField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        // Get gender
        String gender = "";
        if (maleRadioButton.isSelected()) {
            gender = "Male";
        } else if (femaleRadioButton.isSelected()) {
            gender = "Female";
        } else {
            JOptionPane.showMessageDialog(this, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get DOB
        String dob = dobDayComboBox.getSelectedItem() + "-" +
                dobMonthComboBox.getSelectedItem() + "-" +
                dobYearComboBox.getSelectedItem();

        // Get membership start date
        String membershipStartDate = msDayComboBox.getSelectedItem() + "-" +
                msMonthComboBox.getSelectedItem() + "-" +
                msYearComboBox.getSelectedItem();

        // Get plan
        String plan = (String) planComboBox.getSelectedItem();

        // Get referral source
        String referralSource = referralField.getText().trim();

        // Get paid amount
        double paidAmount = 0.0;
        String paidAmountText = paidAmountField.getText().trim();
        if (!paidAmountText.isEmpty()) {
            try {
                paidAmount = Double.parseDouble(paidAmountText);
                if (paidAmount < 0) {
                    JOptionPane.showMessageDialog(this, "Paid Amount cannot be negative!",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Paid Amount must be a valid number!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            // Create and add the Regular Member
            RegularMember regularMember = new RegularMember(
                    id, name, location, phone, email, gender, dob,
                    membershipStartDate, referralSource, paidAmount, plan
            );

            members.add(regularMember);

            // Display success message with improved styling
            JOptionPane.showMessageDialog(this, "Regular Member added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Print to console
            System.out.println("Regular Member Added:");
            System.out.println(regularMember.toString());
            System.out.println();

            // Save to file
            saveMembersToFile();

            // Clear fields after successful addition
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding Regular Member: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new Premium Member to the system
     */
    private void addPremiumMember() {
        // Validate ID first - must be integer
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = idText;

        // Check for ID duplication
        if (isMemberIdDuplicate(id)) {
            JOptionPane.showMessageDialog(this, "Member ID already exists. Each member must have a unique ID.",
                    "Duplicate ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get values from fields
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String location = locationField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        // Get gender
        String gender = "";
        if (maleRadioButton.isSelected()) {
            gender = "Male";
        } else if (femaleRadioButton.isSelected()) {
            gender = "Female";
        } else {
            JOptionPane.showMessageDialog(this, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get DOB
        String dob = dobDayComboBox.getSelectedItem() + "-" +
                dobMonthComboBox.getSelectedItem() + "-" +
                dobYearComboBox.getSelectedItem();

        // Get membership start date
        String membershipStartDate = msDayComboBox.getSelectedItem() + "-" +
                msMonthComboBox.getSelectedItem() + "-" +
                msYearComboBox.getSelectedItem();

        // Get referral source
        String referralSource = referralField.getText().trim();

        // Get paid amount
        double paidAmount = 0.0;
        String paidAmountText = paidAmountField.getText().trim();
        if (!paidAmountText.isEmpty()) {
            try {
                paidAmount = Double.parseDouble(paidAmountText);
                if (paidAmount < 0) {
                    JOptionPane.showMessageDialog(this, "Paid Amount cannot be negative!",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Paid Amount must be a valid number!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Get trainer name (required for Premium Members)
        String trainerName = trainerNameField.getText().trim();
        if (trainerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Trainer's Name is required for Premium Members!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Create and add the Premium Member
            PremiumMember premiumMember = new PremiumMember(
                    id, name, location, phone, email, gender, dob,
                    membershipStartDate, referralSource, paidAmount, trainerName
            );

            members.add(premiumMember);

            // Display success message
            JOptionPane.showMessageDialog(this, "Premium Member added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Print to console
            System.out.println("Premium Member Added:");
            System.out.println(premiumMember.toString());
            System.out.println();

            // Save to file
            saveMembersToFile();

            // Clear fields after successful addition
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding Premium Member: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Activates the membership for a member with the given ID
     */
    private void activateMembership() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GymMember member = findMemberById(idText);
        if (member != null) {
            // Check if membership is already active
            if (member.isActive()) {
                JOptionPane.showMessageDialog(this, "The user is already activated.",
                        "Already Active", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            member.activateMembership();
            JOptionPane.showMessageDialog(this, "Membership activated successfully for ID: " + idText,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Save changes to file
            saveMembersToFile();

            // Print to console
            System.out.println("Membership Activated for member ID: " + idText);
            System.out.println();
        } else {
            JOptionPane.showMessageDialog(this, "Member with ID " + idText + " not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Deactivates the membership for a member with the given ID
     */
    private void deactivateMembership() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GymMember member = findMemberById(idText);
        if (member != null) {
            // Check if membership is already inactive
            if (!member.isActive()) {
                JOptionPane.showMessageDialog(this, "The user is already deactivated.",
                        "Already Inactive", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            member.deactivateMembership();
            JOptionPane.showMessageDialog(this, "Membership deactivated successfully for ID: " + idText,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Save changes to file
            saveMembersToFile();

            // Print to console
            System.out.println("Membership Deactivated for member ID: " + idText);
            System.out.println();
        } else {
            JOptionPane.showMessageDialog(this, "Member with ID " + idText + " not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Marks attendance for a member with the given ID
     */
    private void markAttendance() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GymMember member = findMemberById(idText);
        if (member != null) {
            // Check if the member is active
            if (member.isActive()) {
                member.markAttendance();

                // Update loyalty points field
                loyaltyPointsField.setText(String.valueOf(member.getLoyaltyPoints()));

                JOptionPane.showMessageDialog(this, "Attendance marked successfully for ID: " + idText,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                // Save changes to file
                saveMembersToFile();

                // Print to console
                System.out.println("Attendance Marked for member ID: " + idText);
                System.out.println("Current Attendance: " + member.getAttendance());
                System.out.println("Current Loyalty Points: " + member.getLoyaltyPoints());
                System.out.println();
            } else {
                JOptionPane.showMessageDialog(this, "Cannot mark attendance: Member is not active!",
                        "Inactive Member", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Member with ID " + idText + " not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Upgrades the plan for a regular member with the given ID
     */
    private void upgradePlan() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GymMember member = findMemberById(idText);
        if (member != null) {
            if (member instanceof RegularMember) {
                RegularMember regularMember = (RegularMember) member;

                // Check if member is active
                if (!regularMember.isActive()) {
                    JOptionPane.showMessageDialog(this, "Cannot upgrade plan: Member is not active!",
                            "Inactive Member", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Get the new plan
                String newPlan = (String) planComboBox.getSelectedItem();
                String result = regularMember.upgradePlan(newPlan);

                JOptionPane.showMessageDialog(this, result, "Plan Upgrade", JOptionPane.INFORMATION_MESSAGE);

                // Save changes to file
                saveMembersToFile();

                // Print to console
                System.out.println("Plan Upgrade for member ID: " + idText);
                System.out.println("New Plan: " + regularMember.getPlan());
                System.out.println("New Price: " + regularMember.getPrice());
                System.out.println();
            } else {
                JOptionPane.showMessageDialog(this, "Member with ID " + idText + " is not a Regular Member!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Member with ID " + idText + " not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Calculates discount for a premium member with the given ID
     */
    private void calculateDiscount() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GymMember member = findMemberById(idText);
        if (member != null) {
            if (member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                String result = premiumMember.calculateDiscount();

                JOptionPane.showMessageDialog(this, result, "Discount Calculation", JOptionPane.INFORMATION_MESSAGE);

                // Update discount amount field
                discountAmountField.setText(String.format("%.2f", premiumMember.getDiscountAmount()));

                // Save changes to file
                saveMembersToFile();

                // Print to console
                System.out.println("Discount Calculated for member ID: " + idText);
                System.out.println("Discount Amount: " + premiumMember.getDiscountAmount());
                System.out.println();
            } else {
                JOptionPane.showMessageDialog(this, "Member with ID " + idText + " is not a Premium Member!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Member with ID " + idText + " not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Processes payment for a premium member with the given ID
     */
    private void payDueAmount() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get payment amount
        double amount = 0.0;
        String amountText = paidAmountField.getText().trim();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount to pay!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Paid Amount must be greater than zero!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Paid Amount must be a valid number!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        GymMember member = findMemberById(idText);
        if (member != null) {
            if (member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                String result = premiumMember.payDueAmount(amount);

                JOptionPane.showMessageDialog(this, result, "Payment", JOptionPane.INFORMATION_MESSAGE);

                // Save changes to file
                saveMembersToFile();

                // Print to console
                System.out.println("Payment made for member ID: " + idText);
                System.out.println("Amount Paid: " + amount);
                System.out.println("Total Paid Amount: " + premiumMember.getPaidAmount());
                System.out.println("Full Payment Status: " + (premiumMember.isFullPayment() ? "Complete" : "Incomplete"));
                System.out.println();
            } else {
                JOptionPane.showMessageDialog(this, "Member with ID " + idText + " is not a Premium Member!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Member with ID " + idText + " not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reverts (removes) a Regular Member with the given ID
     */
    private void revertRegularMember() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the removal reason
        String removalReason = removalReasonField.getText().trim();
        if (removalReason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a removal reason!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find the member and check if it's a RegularMember
        GymMember memberToRemove = null;
        for (GymMember member : members) {
            if (member.getId().equals(idText) && member instanceof RegularMember) {
                memberToRemove = member;
                break;
            }
        }

        if (memberToRemove != null) {
            RegularMember regularMember = (RegularMember) memberToRemove;
            regularMember.revertRegularMember(removalReason);
            members.remove(memberToRemove);

            JOptionPane.showMessageDialog(this, "Regular Member with ID " + idText + " has been removed.",
                    "Member Removed", JOptionPane.INFORMATION_MESSAGE);

            // Save changes to file
            saveMembersToFile();

            // Print to console
            System.out.println("Regular Member Removed - ID: " + idText);
            System.out.println("Removal Reason: " + removalReason);
            System.out.println();

            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Regular Member with ID " + idText + " not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reverts (removes) a Premium Member with the given ID
     */
    private void revertPremiumMember() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Member ID!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verify ID is numeric
        try {
            Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Member ID must contain only numbers!",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the removal reason
        String removalReason = removalReasonField.getText().trim();
        if (removalReason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a removal reason!",
                    "Missing Information", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find the member and check if it's a PremiumMember
        GymMember memberToRemove = null;
        for (GymMember member : members) {
            if (member.getId().equals(idText) && member instanceof PremiumMember) {
                memberToRemove = member;
                break;
            }
        }

        if (memberToRemove != null) {
            PremiumMember premiumMember = (PremiumMember) memberToRemove;
            premiumMember.revertPremiumMember(removalReason);
            members.remove(memberToRemove);

            JOptionPane.showMessageDialog(this, "Premium Member with ID " + idText + " has been removed.",
                    "Member Removed", JOptionPane.INFORMATION_MESSAGE);

            // Save changes to file
            saveMembersToFile();

            // Print to console
            System.out.println("Premium Member Removed - ID: " + idText);
            System.out.println("Removal Reason: " + removalReason);
            System.out.println();

            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Premium Member with ID " + idText + " not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears all input fields and resets selections
     */
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        phoneField.setText("");
        emailField.setText("");

        genderGroup.clearSelection();

        dobDayComboBox.setSelectedIndex(0);
        dobMonthComboBox.setSelectedIndex(0);
        dobYearComboBox.setSelectedIndex(0);

        msDayComboBox.setSelectedIndex(0);
        msMonthComboBox.setSelectedIndex(0);
        msYearComboBox.setSelectedIndex(0);

        planComboBox.setSelectedIndex(0);

        referralField.setText("");
        paidAmountField.setText("");
        removalReasonField.setText("");
        trainerNameField.setText("");
        loyaltyPointsField.setText("0");

        // Disable trainer field (only enabled for premium members)
        trainerNameField.setEnabled(false);
    }

    /**
     * Checks if a member with the given ID already exists
     * @param id the ID to check
     * @return true if the ID already exists, false otherwise
     */
    private boolean isMemberIdDuplicate(String id) {
        for (GymMember member : members) {
            if (member.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds a member by their ID
     * @param id the ID to search for
     * @return the GymMember object if found, null otherwise
     */
    private GymMember findMemberById(String id) {
        for (GymMember member : members) {
            if (member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }

    /**
     * Generates an array of days (1-31) for combo boxes
     * @return array of days as strings
     */
    private String[] generateDays() {
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        return days;
    }

    /**
     * Generates an array of years for combo boxes
     * @param startYear the first year in the range
     * @param endYear the last year in the range
     * @return array of years as strings
     */
    private String[] generateYears(int startYear, int endYear) {
        int count = endYear - startYear + 1;
        String[] years = new String[count];
        for (int i = 0; i < count; i++) {
            years[i] = String.valueOf(startYear + i);
        }
        return years;
    }

    /**
     * Displays all members in separate panels based on their type with improved UI
     */
    private void displayMembers() {
        // Create a new frame for displaying members
        JFrame displayFrame = new JFrame("Member Information");
        displayFrame.setSize(900, 650);
        displayFrame.setLayout(new BorderLayout());
        displayFrame.getContentPane().setBackground(FORM_BG_COLOR);

        // Add a header to the display frame with improved styling
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = HEADER_COLOR;
                Color color2 = new Color(52, 152, 219); // Lighter blue
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 60)); // Taller header

        JLabel headerLabel = new JLabel("GYM MEMBERS INFORMATION", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Larger font
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        displayFrame.add(headerPanel, BorderLayout.NORTH);

        // Create a tabbed pane to separate Regular and Premium members with improved styling
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(PANEL_COLOR);
        tabbedPane.setForeground(TEXT_COLOR);

        // Panel for Regular Members
        JPanel regularPanel = new JPanel(new BorderLayout());
        regularPanel.setBackground(PANEL_COLOR);
        regularPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextArea regularTextArea = new JTextArea();
        regularTextArea.setEditable(false);
        regularTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        regularTextArea.setBackground(Color.WHITE);
        regularTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        regularTextArea.setLineWrap(true);
        regularTextArea.setWrapStyleWord(true);

        JScrollPane regularScrollPane = new JScrollPane(regularTextArea);
        regularScrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        regularPanel.add(regularScrollPane, BorderLayout.CENTER);

        // Panel for Premium Members
        JPanel premiumPanel = new JPanel(new BorderLayout());
        premiumPanel.setBackground(PANEL_COLOR);
        premiumPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextArea premiumTextArea = new JTextArea();
        premiumTextArea.setEditable(false);
        premiumTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        premiumTextArea.setBackground(Color.WHITE);
        premiumTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        premiumTextArea.setLineWrap(true);
        premiumTextArea.setWrapStyleWord(true);

        JScrollPane premiumScrollPane = new JScrollPane(premiumTextArea);
        premiumScrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        premiumPanel.add(premiumScrollPane, BorderLayout.CENTER);

        // Add the panels to the tabbed pane
        tabbedPane.addTab("Regular Members", regularPanel);
        tabbedPane.addTab("Premium Members", premiumPanel);

        // Set tab icons with improved styling
        tabbedPane.setTabComponentAt(0, createTabLabel("Regular Members", new Color(39, 174, 96)));
        tabbedPane.setTabComponentAt(1, createTabLabel("Premium Members", new Color(231, 76, 60)));

        // Display the members' information in the text areas
        boolean hasRegularMembers = false;
        boolean hasPremiumMembers = false;

        for (GymMember member : members) {
            if (member instanceof RegularMember) {
                RegularMember regularMember = (RegularMember) member;
                regularTextArea.append(formatMemberInfo(regularMember) + "\n\n" +
                        "----------------------------------------\n\n");
                hasRegularMembers = true;

                // Also print to console
                System.out.println("DISPLAYING REGULAR MEMBER:");
                System.out.println(regularMember.toString());
                System.out.println();
            } else if (member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                premiumTextArea.append(formatMemberInfo(premiumMember) + "\n\n" +
                        "----------------------------------------\n\n");
                hasPremiumMembers = true;

                // Also print to console
                System.out.println("DISPLAYING PREMIUM MEMBER:");
                System.out.println(premiumMember.toString());
                System.out.println();
            }
        }

        // Add messages if no members exist
        if (!hasRegularMembers) {
            regularTextArea.setText("No regular members found.");
        }

        if (!hasPremiumMembers) {
            premiumTextArea.setText("No premium members found.");
        }

        // Add the tabbed pane to the display frame
        displayFrame.add(tabbedPane, BorderLayout.CENTER);

        // Add button panel with improved styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(PANEL_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        // Add close button with improved styling
        JButton closeButton = createStyledButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayFrame.dispose();
            }
        });
        buttonPanel.add(closeButton);

        // Add save button with improved styling
        JButton saveToFileButton = createStyledButton("Save to File");
        saveToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMembersToFile();
                JOptionPane.showMessageDialog(displayFrame,
                        "Members successfully saved to database file.",
                        "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(saveToFileButton);

        displayFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Show the display frame
        displayFrame.setLocationRelativeTo(this);
        displayFrame.setVisible(true);
    }

    /**
     * Creates a styled label for tabs with improved design
     * @param text The tab text
     * @param color The tab color
     * @return JLabel styled for use in tab
     */
    private JLabel createTabLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        return label;
    }

    /**
     * Formats member information for display with improved readability
     * @param member The GymMember to format
     * @return Formatted string with member information
     */
    private String formatMemberInfo(GymMember member) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(member.getId()).append("\n");
        sb.append("Name: ").append(member.getName()).append("\n");
        sb.append("Location: ").append(member.getLocation()).append("\n");
        sb.append("Phone: ").append(member.getPhone()).append("\n");
        sb.append("Email: ").append(member.getEmail()).append("\n");
        sb.append("Gender: ").append(member.getGender()).append("\n");
        sb.append("Date of Birth: ").append(member.getDob()).append("\n");
        sb.append("Membership Start Date: ").append(member.getMembershipStartDate()).append("\n");
        sb.append("Active Status: ").append(member.isActive() ? "Active" : "Inactive").append("\n");
        sb.append("Attendance: ").append(member.getAttendance()).append("\n");
        sb.append("Loyalty Points: ").append(member.getLoyaltyPoints()).append("\n");

        if (member instanceof RegularMember) {
            RegularMember regularMember = (RegularMember) member;
            sb.append("Member Type: Regular Member\n");
            sb.append("Plan: ").append(regularMember.getPlan()).append("\n");
            sb.append("Price: ").append(String.format("%.2f", regularMember.getPrice())).append("\n");
        } else if (member instanceof PremiumMember) {
            PremiumMember premiumMember = (PremiumMember) member;
            sb.append("Member Type: Premium Member\n");
            sb.append("Trainer: ").append(premiumMember.getPersonalTrainer()).append("\n");
            sb.append("Premium Charge: ").append(String.format("%.2f", premiumMember.getPremiumCharge())).append("\n");
            sb.append("Discount Amount: ").append(String.format("%.2f", premiumMember.getDiscountAmount())).append("\n");
            sb.append("Paid Amount: ").append(String.format("%.2f", premiumMember.getPaidAmount())).append("\n");
            sb.append("Full Payment: ").append(premiumMember.isFullPayment() ? "Yes" : "No").append("\n");
        }

        return sb.toString();
    }

    /**
     * Saves all members to a text file
     */
    private void saveMembersToFile() {
        try {
            FileWriter fileWriter = new FileWriter(DATA_FILE);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Write header
            printWriter.println("# GYM MEMBER DATABASE");
            printWriter.println("# FORMAT: TYPE|ID|NAME|LOCATION|PHONE|EMAIL|GENDER|DOB|MEMBERSHIP_START|REFERRAL|PAID_AMOUNT|ACTIVE|ATTENDANCE|LOYALTY|ADDITIONAL_DATA");
            printWriter.println();

            // Write each member as a line
            for (GymMember member : members) {
                if (member instanceof RegularMember) {
                    RegularMember regularMember = (RegularMember) member;
                    printWriter.println("REGULAR|" +
                            regularMember.getId() + "|" +
                            regularMember.getName() + "|" +
                            regularMember.getLocation() + "|" +
                            regularMember.getPhone() + "|" +
                            regularMember.getEmail() + "|" +
                            regularMember.getGender() + "|" +
                            regularMember.getDob() + "|" +
                            regularMember.getMembershipStartDate() + "|" +
                            regularMember.getReferralSource() + "|" +
                            regularMember.getPaidAmount() + "|" +
                            regularMember.isActive() + "|" +
                            regularMember.getAttendance() + "|" +
                            regularMember.getLoyaltyPoints() + "|" +
                            regularMember.getPlan() + "," + regularMember.getPrice());
                } else if (member instanceof PremiumMember) {
                    PremiumMember premiumMember = (PremiumMember) member;
                    printWriter.println("PREMIUM|" +
                            premiumMember.getId() + "|" +
                            premiumMember.getName() + "|" +
                            premiumMember.getLocation() + "|" +
                            premiumMember.getPhone() + "|" +
                            premiumMember.getEmail() + "|" +
                            premiumMember.getGender() + "|" +
                            premiumMember.getDob() + "|" +
                            premiumMember.getMembershipStartDate() + "|" +
                            premiumMember.getReferralSource() + "|" +
                            premiumMember.getPaidAmount() + "|" +
                            premiumMember.isActive() + "|" +
                            premiumMember.getAttendance() + "|" +
                            premiumMember.getLoyaltyPoints() + "|" +
                            premiumMember.getPersonalTrainer() + "," +
                            premiumMember.isFullPayment() + "," +
                            premiumMember.getDiscountAmount());
                }
            }

            printWriter.close();
            System.out.println("Members data saved to " + DATA_FILE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving members to file: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Loads members from the data file
     */
    private void loadMembersFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("Data file not found. Starting with empty database.");
            return;
        }

        try {
            members.clear(); // Clear existing members
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            // Skip header lines
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 15) {
                    System.out.println("Invalid line format: " + line);
                    continue;
                }

                String type = parts[0];
                String id = parts[1];
                String name = parts[2];
                String location = parts[3];
                String phone = parts[4];
                String email = parts[5];
                String gender = parts[6];
                String dob = parts[7];
                String membershipStartDate = parts[8];
                String referralSource = parts[9];
                double paidAmount = Double.parseDouble(parts[10]);
                boolean active = Boolean.parseBoolean(parts[11]);
                int attendance = Integer.parseInt(parts[12]);
                double loyaltyPoints = Double.parseDouble(parts[13]);
                String additionalData = parts[14];

                if ("REGULAR".equals(type)) {
                    String[] planData = additionalData.split(",");
                    String plan = planData[0];
                    // Create regular member
                    RegularMember regularMember = new RegularMember(
                            id, name, location, phone, email, gender, dob,
                            membershipStartDate, referralSource, paidAmount, plan
                    );

                    // Set additional properties
                    regularMember.setAttendance(attendance);
                    regularMember.setLoyaltyPoints(loyaltyPoints);
                    if (active) {
                        regularMember.activateMembership();
                    }

                    members.add(regularMember);
                } else if ("PREMIUM".equals(type)) {
                    String[] premiumData = additionalData.split(",");
                    String trainer = premiumData[0];

                    // Create premium member
                    PremiumMember premiumMember = new PremiumMember(
                            id, name, location, phone, email, gender, dob,
                            membershipStartDate, referralSource, paidAmount, trainer
                    );

                    // Set additional properties
                    premiumMember.setAttendance(attendance);
                    premiumMember.setLoyaltyPoints(loyaltyPoints);
                    if (active) {
                        premiumMember.activateMembership();
                    }

                    // Set premium-specific properties if available
                    if (premiumData.length > 1) {
                        boolean fullPayment = Boolean.parseBoolean(premiumData[1]);
                        premiumMember.setFullPayment(fullPayment);
                    }

                    if (premiumData.length > 2) {
                        double discountAmount = Double.parseDouble(premiumData[2]);
                        premiumMember.setDiscountAmount(discountAmount);
                    }

                    members.add(premiumMember);
                }
            }

            reader.close();
            System.out.println("Loaded " + members.size() + " members from " + DATA_FILE);

            // Show success message with improved design
            if (members.size() > 0) {
                JOptionPane.showMessageDialog(this,
                        "Successfully loaded " + members.size() + " members from database.",
                        "Data Loaded", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading members from file: " + e.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        // Set the look and feel to system look and feel for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Use Swing's event dispatch thread to create and show the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GymGUI gui = new GymGUI();
                gui.setVisible(true);

                // Add help dialog showing usage of the application with improved styling
                JOptionPane.showMessageDialog(gui,
                        "Welcome to Gym Membership Management System!\n\n" +
                                "• Member ID must contain only numbers\n" +
                                "• Trainer's Name is only for Premium Members\n" +
                                "• Member data is automatically saved to 'gym_members.docx'\n" +
                                "• You can save the database manually using the 'Save on File' button\n" +
                                "• Members are automatically loaded when starting the application\n\n" +
                                "The new interface has improved contrast for better accessibility.",
                        "Getting Started", JOptionPane.INFORMATION_MESSAGE);

                // Print welcome message to console
                System.out.println("GYM MEMBERSHIP MANAGEMENT SYSTEM STARTED");
            }
        });
    }
}