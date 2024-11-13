package org.example.ooplibrary.Object;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Book {
    private final StringProperty ISBN;
    private final StringProperty name;
    private final StringProperty yearOfPublication;
    private final StringProperty author;
    private final StringProperty genre;
    private final StringProperty description;
    private final ImageView[] features;

    public Book(String ISBN, String name, String yearOfPublication, String author, String genre, String description) {
        this.ISBN = new SimpleStringProperty(ISBN);
        this.name = new SimpleStringProperty(name);
        this.yearOfPublication = new SimpleStringProperty(yearOfPublication);
        this.author = new SimpleStringProperty(author);
        this.genre = new SimpleStringProperty(genre);
        this.description = new SimpleStringProperty(description);
        this.features = new ImageView[3];
    }

    public String getISBN() {
        return ISBN.get();
    }

    public void setId(String ISBN) {
        this.ISBN.set(ISBN);
    }

    public StringProperty ISBNProperty() {
        return ISBN;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getYearOfPublication() {
        return yearOfPublication.get();
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication.set(yearOfPublication);
    }

    public StringProperty yearOfPublicationProperty() {
        return yearOfPublication;
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public StringProperty authorProperty() {
        return author;
    }

    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public ImageView[] getFeatures() {
        return features;
    }

    public ImageView getFeature(int index) {
        if (index >= 0 && index < features.length) {
            return features[index];
        } else {
            return null;
        }
    }

    public void setFeature(int index, ImageView imageView) {
        if (index >= 0 && index < features.length) {
            features[index] = imageView;
        }
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}