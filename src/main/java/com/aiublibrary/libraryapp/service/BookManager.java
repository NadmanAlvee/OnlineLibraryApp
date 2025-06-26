package com.aiublibrary.libraryapp.service;
import com.aiublibrary.libraryapp.model.Book;

import org.json.*;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

public class BookManager {

    private static final String BOOKS_FILE_NAME = "books.json";
    private List<Book> books;
    private String booksFilePath;

    public BookManager() {
        books = new ArrayList<>();
        initializeBooksFile();
        loadBooks();
    }

    private void initializeBooksFile() {
        File file = new File("src/main/resources/" + BOOKS_FILE_NAME);
        booksFilePath = file.getAbsolutePath();

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]");
                }
                System.out.println("Created new books.json at: " + booksFilePath);
            } catch (IOException e) {
                System.err.println("Error creating books.json at: " + booksFilePath + " - " + e.getMessage());
            }
        } else {
            System.out.println("Located books.json at: " + booksFilePath);
        }
    }

    private void loadBooks() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(booksFilePath)));
            if (content.trim().isEmpty() || content.trim().equals("[]")) {
                books = createInitialBookData();
                saveBooks();
                System.out.println("Books.json was empty or contained an empty array. Populated with initial data and saved.");
                return;
            }
            JSONArray jsonBooks = new JSONArray(content);
            books = new ArrayList<>();
            for (int i = 0; i < jsonBooks.length(); i++) {
                JSONObject bookObj = jsonBooks.getJSONObject(i);
                String id = bookObj.getString("id");
                String title = bookObj.getString("title");
                String author = bookObj.getString("author");
                String imageUrl = bookObj.optString("imageUrl", "");
                String pdfPath = bookObj.optString("pdfPath", "");
                boolean isAvailable = bookObj.getBoolean("isAvailable");
                books.add(new Book(id, title, author, imageUrl, pdfPath, isAvailable));
            }
            System.out.println("Books loaded: " + books.size());
        } catch (IOException e) {
            System.err.println("Error reading books.json from: " + booksFilePath + " - " + e.getMessage());
            books = createInitialBookData();
            saveBooks();
        } catch (Exception e) {
            System.err.println("Error parsing books.json from: " + booksFilePath + " - " + e.getMessage());
            books = createInitialBookData();
            saveBooks();
        }
    }

    public void saveBooks() {
        JSONArray jsonBooks = new JSONArray();
        for (Book book : books) {
            JSONObject bookObj = new JSONObject();
            bookObj.put("id", book.getId());
            bookObj.put("title", book.getTitle());
            bookObj.put("author", book.getAuthor());
            bookObj.put("imageUrl", book.getImageUrl());
            bookObj.put("pdfPath", book.getPdfPath());
            bookObj.put("isAvailable", book.isAvailable());
            jsonBooks.put(bookObj);
        }

        try (FileWriter file = new FileWriter(booksFilePath)) {
            file.write(jsonBooks.toString(4));
            file.flush();
            System.out.println("Books saved to " + booksFilePath);
        } catch (IOException e) {
            System.err.println("Error writing books.json to: " + booksFilePath + " - " + e.getMessage());
        }
    }

    private List<Book> createInitialBookData() {
        List<Book> initialBooks = new ArrayList<>();
        initialBooks.add(new Book("book-001", "HIDE AND SEEK", "T. Albert", "/images/HIDE_AND_SEEK.jpg", "/pdfs/HIDE_AND_SEEK.pdf", true));
        initialBooks.add(new Book("book-002", "Ginger The Giraffe", "T. Albert", "/images/Ginger_The_Giraffe.jpg", "/pdfs/Ginger_The_Giraffe.pdf", true));
        return initialBooks;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public void updateBookAvailability(String bookId, boolean newAvailability) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                book.setAvailable(newAvailability);
                saveBooks();
                System.out.println("Updated book '" + book.getTitle() + "' to available=" + newAvailability + " and saved.");
                return;
            }
        }
        System.err.println("Book with ID " + bookId + " not found for update.");
    }
}
