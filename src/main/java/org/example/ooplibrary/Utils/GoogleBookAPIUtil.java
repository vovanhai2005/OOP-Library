package org.example.ooplibrary.Utils;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.ooplibrary.Object.Book;

public class GoogleBookAPIUtil {
    public static <JsonObject> Book fetchBookDetails(String isbnOrTitle, String type) {
        System.out.println(isbnOrTitle + " " + type);
        String urlString;

        // Xây dựng URL dựa trên loại tìm kiếm
        if (type.equals("isbn")) {
            isbnOrTitle = isbnOrTitle.replace("-", ""); // Loại bỏ dấu gạch ngang trong ISBN nếu có
            urlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbnOrTitle;
        } else if (type.equals("title")) {
            urlString = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + isbnOrTitle;
        } else {
            return null;
        }

        try {
            // Tạo HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Tạo HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(urlString))
                    .GET()
                    .build();

            // Gửi yêu cầu và nhận phản hồi dưới dạng chuỗi
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Phân tích chuỗi JSON trả về
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            // Kiểm tra nếu có sách được tìm thấy
            if (jsonResponse.has("items")) {
                JsonObject bookInfo = jsonResponse.getAsJsonArray("items").get(0).getAsJsonObject().getAsJsonObject("volumeInfo");

                // Lấy các thông tin sách
                String isbn = bookInfo.has("industryIdentifiers")
                        ? bookInfo.getAsJsonArray("industryIdentifiers").get(0).getAsJsonObject().get("identifier").getAsString()
                        : "N/A";
                String title = bookInfo.has("title") ? bookInfo.get("title").getAsString() : "N/A";
                String author = bookInfo.has("authors") ? bookInfo.getAsJsonArray("authors").get(0).getAsString() : "N/A";
                List<String> genres = new ArrayList<>();
                if (bookInfo.has("categories")) {
                    // Iterate over all elements in the "categories" array
                    for (JsonElement categoryElement : bookInfo.getAsJsonArray("categories")) {
                        genres.add(categoryElement.getAsString());
                    }
                } else {
                    genres = List.of();  // Empty list if "categories" is not present
                }
                for (String tmp : genres){
                    System.out.println(tmp);
                }
                String description = bookInfo.has("description") ? bookInfo.get("description").getAsString() : "N/A";
                String yearOfPublication = bookInfo.has("publishedDate") ? bookInfo.get("publishedDate").getAsString() : "N/A";

                // Lấy ảnh thumbnail dưới dạng byte[]
                byte[] image = null;
                if (bookInfo.has("imageLinks")) {
                    String imageUrl = bookInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
                    HttpRequest imageRequest = HttpRequest.newBuilder()
                            .uri(new URI(imageUrl))
                            .GET()
                            .build();

                    HttpResponse<byte[]> imageResponse = client.send(imageRequest, HttpResponse.BodyHandlers.ofByteArray());
                    image = imageResponse.body();
                }

                // Tạo đối tượng Book và trả về
                return new Book(isbn, title, yearOfPublication, author, genres, description, image);
            } else {
                System.out.println("No results found.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Book fetchBookDetailsByISBN(String isbn) {
        return fetchBookDetails(isbn, "isbn");
    }

    public static Book fetchBookDetailsByTitle(String title) {
        return fetchBookDetails(title, "title");
    }

}