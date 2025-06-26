package com.aiublibrary.libraryapp.ui;

import com.aiublibrary.libraryapp.model.Book;
import com.aiublibrary.libraryapp.service.BookManager;
import com.aiublibrary.libraryapp.service.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductListPage extends JFrame {

    private String loggedInUsername;
    private JPanel booksGridPanel;
    private BookManager bookManager;

    public ProductListPage(String username) {
        this.loggedInUsername = username;
        this.bookManager = new BookManager();

        setTitle("LibraryApp - Product List (Logged in as: " + loggedInUsername + ")");
        setSize(800, 600);
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

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(mainPanel.getBackground());

        JLabel welcomeLabel = new JLabel("Welcome, " + loggedInUsername + "!", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(75, 0, 130));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(220, 20, 60));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createRaisedBevelBorder());
        logoutButton.setPreferredSize(new Dimension(80, 30));
        headerPanel.add(logoutButton, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JLabel listTitle = new JLabel("Available Books:", SwingConstants.LEFT);
        listTitle.setFont(new Font("Arial", Font.BOLD, 18));
        listTitle.setForeground(new Color(75, 0, 130));
        JPanel listTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        listTitlePanel.setBackground(mainPanel.getBackground());
        listTitlePanel.add(listTitle);
        mainPanel.add(listTitlePanel, BorderLayout.CENTER);

        booksGridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        booksGridPanel.setBackground(new Color(250, 250, 255));
        booksGridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<Book> allBooks = bookManager.getAllBooks();

        for (Book book : allBooks) {
            booksGridPanel.add(new BookCardPanel(book, bookManager));
        }

        JScrollPane scrollPane = new JScrollPane(booksGridPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(106, 90, 205), 1, true));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        ProductListPage.this,
                        "Are you sure you want to log out?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    SwingUtilities.invokeLater(() -> {
                        new LoginPage(new UserManager()).setVisible(true);
                        dispose();
                    });
                }
            }
        });
    }
}
