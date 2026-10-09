package vn.edu.ueh.thanhdnh.firebase_example;

// Thay User bằng Article có đúng bốn thuộc tính của đề.
public class Article {
    private String id;
    private String title;
    private String content;
    private String image_url;

    // Constructor rỗng để Firestore chuyển document thành Article.
    public Article() {
    }

    // Tạo bài viết với ID document, tiêu đề, nội dung và URL ảnh.
    public Article(String id, String title, String content, String image_url) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image_url = image_url;
    }

    // Đọc ID bài viết.
    public String getId() { return id; }

    // Gán ID bài viết.
    public void setId(String id) { this.id = id; }

    // Đọc tiêu đề.
    public String getTitle() { return title; }

    // Gán tiêu đề.
    public void setTitle(String title) { this.title = title; }

    // Đọc nội dung.
    public String getContent() { return content; }

    // Gán nội dung.
    public void setContent(String content) { this.content = content; }

    // Đọc URL ảnh; tên getter giữ đúng khóa image_url trong Firestore.
    public String getImage_url() { return image_url; }

    // Gán URL ảnh.
    public void setImage_url(String image_url) { this.image_url = image_url; }
}
