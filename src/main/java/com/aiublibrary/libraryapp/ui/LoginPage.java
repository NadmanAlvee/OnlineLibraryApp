package com.aiublibrary.libraryapp.ui;

import com.aiublibrary.libraryapp.service.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    private UserManager userManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton signInButton;
    private JButton signUpButton;
    private JButton toggleSignUpButton;
    private JButton toggleSignInButton;
    private JLabel messageLabel;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public LoginPage(UserManager userManager) {
        this.userManager = userManager;
        setTitle("LibraryApp - Sign In / Sign Up");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel("Welcome to LibraryApp", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(75, 0, 130));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(Color.RED);
        mainPanel.add(messageLabel, BorderLayout.SOUTH);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(mainPanel.getBackground());

        JPanel signInFormPanel = new JPanel(new GridBagLayout());
        signInFormPanel.setBackground(mainPanel.getBackground());
        signInFormPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(106, 90, 205), 2, true),
                "Sign In",
                0,
                0,
                new Font("Arial", Font.BOLD, 18),
                new Color(75, 0, 130)
        ));

        GridBagConstraints gbcSignIn = new GridBagConstraints();
        gbcSignIn.insets = new Insets(8, 8, 8, 8);
        gbcSignIn.fill = GridBagConstraints.HORIZONTAL;
        gbcSignIn.weightx = 1.0;

        gbcSignIn.gridx = 0; gbcSignIn.gridy = 0;
        signInFormPanel.add(new JLabel("Username:"), gbcSignIn);
        gbcSignIn.gridx = 1; gbcSignIn.gridy = 0;
        usernameField = new JTextField(20);
        signInFormPanel.add(usernameField, gbcSignIn);

        gbcSignIn.gridx = 0; gbcSignIn.gridy = 1;
        signInFormPanel.add(new JLabel("Password:"), gbcSignIn);
        gbcSignIn.gridx = 1; gbcSignIn.gridy = 1;
        passwordField = new JPasswordField(20);
        signInFormPanel.add(passwordField, gbcSignIn);

        gbcSignIn.gridx = 0; gbcSignIn.gridy = 2;
        gbcSignIn.gridwidth = 2;
        signInButton = new JButton("Sign In");
        signInButton.setFont(new Font("Arial", Font.BOLD, 16));
        signInButton.setBackground(new Color(123, 104, 238));
        signInButton.setForeground(Color.BLACK);
        signInButton.setFocusPainted(false);
        signInButton.setBorder(BorderFactory.createRaisedBevelBorder());
        signInButton.setPreferredSize(new Dimension(100, 35));
        signInFormPanel.add(signInButton, gbcSignIn);

        gbcSignIn.gridy = 3;
        toggleSignUpButton = new JButton("Don't have an account? Sign Up");
        toggleSignUpButton.setFont(new Font("Arial", Font.PLAIN, 12));
        toggleSignUpButton.setForeground(new Color(70, 130, 180));
        toggleSignUpButton.setBorderPainted(false);
        toggleSignUpButton.setContentAreaFilled(false);
        toggleSignUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signInFormPanel.add(toggleSignUpButton, gbcSignIn);

        cardPanel.add(signInFormPanel, "SignIn");

        JPanel signUpFormPanel = new JPanel(new GridBagLayout());
        signUpFormPanel.setBackground(mainPanel.getBackground());
        signUpFormPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(106, 90, 205), 2, true),
                "Sign Up",
                0,
                0,
                new Font("Arial", Font.BOLD, 18),
                new Color(75, 0, 130)
        ));

        GridBagConstraints gbcSignUp = new GridBagConstraints();
        gbcSignUp.insets = new Insets(8, 8, 8, 8);
        gbcSignUp.fill = GridBagConstraints.HORIZONTAL;
        gbcSignUp.weightx = 1.0;

        gbcSignUp.gridx = 0; gbcSignUp.gridy = 0;
        signUpFormPanel.add(new JLabel("Username:"), gbcSignUp);
        gbcSignUp.gridx = 1; gbcSignUp.gridy = 0;
        final JTextField signUpUsernameField = new JTextField(20);
        signUpFormPanel.add(signUpUsernameField, gbcSignUp);

        gbcSignUp.gridx = 0; gbcSignUp.gridy = 1;
        signUpFormPanel.add(new JLabel("Password:"), gbcSignUp);
        gbcSignUp.gridx = 1; gbcSignUp.gridy = 1;
        final JPasswordField signUpPasswordField = new JPasswordField(20);
        signUpFormPanel.add(signUpPasswordField, gbcSignUp);

        gbcSignUp.gridx = 0; gbcSignUp.gridy = 2;
        signUpFormPanel.add(new JLabel("Confirm Pass:"), gbcSignUp);
        gbcSignUp.gridx = 1; gbcSignUp.gridy = 2;
        final JPasswordField signUpConfirmPasswordField = new JPasswordField(20);
        signUpFormPanel.add(signUpConfirmPasswordField, gbcSignUp);

        gbcSignUp.gridx = 0; gbcSignUp.gridy = 3;
        gbcSignUp.gridwidth = 2;
        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));
        signUpButton.setBackground(new Color(123, 104, 238));
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createRaisedBevelBorder());
        signUpButton.setPreferredSize(new Dimension(100, 35));
        signUpFormPanel.add(signUpButton, gbcSignUp);

        gbcSignUp.gridy = 4;
        toggleSignInButton = new JButton("Already have an account? Sign In");
        toggleSignInButton.setFont(new Font("Arial", Font.PLAIN, 12));
        toggleSignInButton.setForeground(new Color(70, 130, 180));
        toggleSignInButton.setBorderPainted(false);
        toggleSignInButton.setContentAreaFilled(false);
        toggleSignInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpFormPanel.add(toggleSignInButton, gbcSignUp);

        cardPanel.add(signUpFormPanel, "SignUp");

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        add(mainPanel);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = LoginPage.this.usernameField.getText().trim();
                String password = new String(LoginPage.this.passwordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    showMessage("Username and Password cannot be empty!", false);
                    return;
                }

                if (userManager.authenticateUser(username, password)) {
                    showMessage("Login successful!", true);
                    SwingUtilities.invokeLater(() -> {
                        new ProductListPage(username).setVisible(true);
                        dispose();
                    });
                } else {
                    showMessage("Invalid username or password.", false);
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = signUpUsernameField.getText().trim();
                String password = new String(signUpPasswordField.getPassword()).trim();
                String confirmPassword = new String(signUpConfirmPasswordField.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    showMessage("All fields are required for signup!", false);
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    showMessage("Passwords do not match!", false);
                    return;
                }
                if (password.length() < 6) {
                    showMessage("Password must be at least 6 characters long.", false);
                    return;
                }

                if (userManager.registerUser(username, password)) {
                    showMessage("Registration successful! You can now sign in.", true);
                    signUpUsernameField.setText("");
                    signUpPasswordField.setText("");
                    signUpConfirmPasswordField.setText("");
                    cardLayout.show(cardPanel, "SignIn");
                    LoginPage.this.usernameField.setText(username);
                    LoginPage.this.passwordField.setText("");
                } else {
                    showMessage("Registration failed. Username might already exist.", false);
                }
            }
        });

        toggleSignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "SignUp");
                messageLabel.setText("");
            }
        });

        toggleSignInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "SignIn");
                messageLabel.setText("");
            }
        });

        cardLayout.show(cardPanel, "SignIn");
    }

    private void showMessage(String message, boolean isSuccess) {
        messageLabel.setText(message);
        messageLabel.setForeground(isSuccess ? new Color(34, 139, 34) : Color.RED);
    }
}
