package org.example.ooplibrary.Object;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class Book {
    private StringProperty ISBN;
    private StringProperty name;
    private StringProperty yearOfPublication;
    private StringProperty author;
    private List<String> genres;
    private String genresString;
    private StringProperty description;
    private byte[] image;
    private ImageView[] features;

    public Book(String ISBN, String name, String yearOfPublication, String author, List<String> genres, String description, byte[] image) {
        this.ISBN = new SimpleStringProperty(ISBN);
        this.name = new SimpleStringProperty(name);
        this.yearOfPublication = new SimpleStringProperty(yearOfPublication);
        this.author = new SimpleStringProperty(author);
        this.genres = genres;
        this.description = new SimpleStringProperty(description);
        this.image = image;
        this.features = new ImageView[3];
        StringBuilder genresString = new StringBuilder();
        for (int i =0; i< genres.size(); i++) {
            genresString.append(genres.get(i));
            if (i != genres.size() - 1) {
                genresString.append(", ");
            }
        }
        this.genresString = genresString.toString();
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

    public String getGenresString() {
        return genresString;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
        // Convert genres list to a single string
        StringBuilder genresString = new StringBuilder();
        for (int i =0; i< genres.size(); i++) {
            genresString.append(genres.get(i));
            if (i != genres.size() - 1) {
                genresString.append(", ");
            }
        }
        this.genresString = genresString.toString();
    }

    public ImageView[] getFeatures() {
        return features;
    }

    public byte[] getImage() {
        return image;
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