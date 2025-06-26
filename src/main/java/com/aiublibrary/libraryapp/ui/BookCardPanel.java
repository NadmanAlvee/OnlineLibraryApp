package com.aiublibrary.libraryapp.ui;

import com.aiublibrary.libraryapp.model.Book;
import com.aiublibrary.libraryapp.service.BookManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class BookCardPanel extends JPanel {

    private Book book;
    private BookManager bookManager;
    private JLabel imageLabel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JButton rentButton;
    private JButton returnButton;
    private JLabel pdfLink;
    private JPanel bottomPanel;

    public BookCardPanel(Book book, BookManager bookManager) {
        this.book = book;
        this.bookManager = bookManager;
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)
        ));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 320));
        setMaximumSize(new Dimension(200, 320));
        setMinimumSize(new Dimension(200, 320));

        initComponents();
        updateCardUI();
    }

    private void initComponents() {
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(150, 150));
        loadImage(book.getImageUrl());
        add(imageLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

        titleLabel = new JLabel("<html><b>" + book.getTitle() + "</b></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.add(titleLabel);

        authorLabel = new JLabel("by " + book.getAuthor());
        authorLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        authorLabel.setForeground(Color.GRAY);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.add(authorLabel);

        add(detailsPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rentButton = new JButton("Rent");
        rentButton.setFont(new Font("Arial", Font.BOLD, 12));
        rentButton.setBackground(new Color(60, 179, 113));
        rentButton.setForeground(Color.GREEN.darker());
        rentButton.setFocusPainted(false);
        rentButton.setBorder(BorderFactory.createRaisedBevelBorder());

        returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 12));
        returnButton.setBackground(new Color(255, 99, 71));
        returnButton.setForeground(Color.RED.darker());
        returnButton.setFocusPainted(false);
        returnButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonPanel.add(rentButton);
        buttonPanel.add(returnButton);

        rentButton.addActionListener(e -> {
            System.out.println("Rent button clicked for: " + book.getTitle());
            if (book.isAvailable()) {
                book.setAvailable(false);
                bookManager.updateBookAvailability(book.getId(), false);
                updateCardUI();
                JOptionPane.showMessageDialog(this, "You have rented \"" + book.getTitle() + "\".", "Rent Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println(book.getTitle() + " is currently rented and cannot be rented again.");
            }
        });

        returnButton.addActionListener(e -> {
            System.out.println("Return button clicked for: " + book.getTitle());
            if (!book.isAvailable()) {
                book.setAvailable(true);
                bookManager.updateBookAvailability(book.getId(), true);
                updateCardUI();
                JOptionPane.showMessageDialog(this, "You have returned \"" + book.getTitle() + "\".", "Return Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println(book.getTitle() + " is already available and cannot be returned.");
            }
        });

        bottomPanel.add(buttonPanel);

        pdfLink = new JLabel("<html><u>View PDF</u></html>");
        pdfLink.setFont(new Font("Arial", Font.PLAIN, 12));
        pdfLink.setForeground(Color.BLUE.darker());
        pdfLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pdfLink.setHorizontalAlignment(SwingConstants.CENTER);
        pdfLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        pdfLink.setBorder(new EmptyBorder(5, 0, 0, 0));
        pdfLink.setVisible(false);

        pdfLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    try {
                        URL resource = getClass().getResource(book.getPdfPath());
                        if (resource != null) {
                            File pdfFile = new File(resource.toURI());
                            if (pdfFile.exists()) {
                                Desktop.getDesktop().open(pdfFile);
                            } else {
                                JOptionPane.showMessageDialog(BookCardPanel.this,
                                        "PDF file not found on disk: " + pdfFile.getAbsolutePath() + "\n(Ensure the PDF exists in src/main/resources/pdfs/)",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(BookCardPanel.this,
                                    "PDF resource not found in classpath: " + book.getPdfPath() + "\n(Ensure the PDF path in Book.java is correct)",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        System.err.println("Error opening PDF: " + ex.getMessage());
                        JOptionPane.showMessageDialog(BookCardPanel.this,
                                "Could not open PDF. Error: " + ex.getMessage() + "\n(Ensure you have a default PDF viewer installed)",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(BookCardPanel.this,
                            "Desktop operations not supported on this system.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pdfLink.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pdfLink.setForeground(Color.BLUE.darker());
            }
        });

        bottomPanel.add(pdfLink);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateCardUI() {
        if (book.isAvailable()) {
            rentButton.setEnabled(true);
            returnButton.setEnabled(false);
            rentButton.setBackground(new Color(60, 179, 113));
            returnButton.setBackground(new Color(200, 200, 200));
            authorLabel.setText("by " + book.getAuthor() + " (Available)");
            authorLabel.setForeground(new Color(0, 128, 0));
            pdfLink.setVisible(false);
        } else {
            rentButton.setEnabled(false);
            returnButton.setEnabled(true);
            rentButton.setBackground(new Color(200, 200, 200));
            returnButton.setBackground(new Color(255, 99, 71));
            authorLabel.setText("by " + book.getAuthor() + " (Rented)");
            authorLabel.setForeground(new Color(178, 34, 34));
            pdfLink.setVisible(true);
        }
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    private void loadImage(String imagePath) {
        URL imageUrl = null;

        if (imagePath != null && !imagePath.isEmpty()) {
            imageUrl = getClass().getResource(imagePath);
        }

        if (imageUrl == null) {
            System.err.println("Specific image not found or path invalid: " + imagePath + ". Trying default.png...");
            imageUrl = getClass().getResource("/images/default.png");
            if (imageUrl == null) {
                System.err.println("Default image (default.png) not found at /images/default.png!");
                imageLabel.setIcon(null);
                imageLabel.setText("Image Missing!");
                return;
            }
        }

        try {
            ImageIcon icon = new ImageIcon(imageUrl);
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(
                    imageLabel.getPreferredSize().width,
                    imageLabel.getPreferredSize().height,
                    Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setText("");
        } catch (Exception e) {
            System.err.println("Error loading image from resource: " + imageUrl + " - " + e.getMessage());
            imageLabel.setIcon(null);
            imageLabel.setText("Image Load Error");
        }
    }
}
