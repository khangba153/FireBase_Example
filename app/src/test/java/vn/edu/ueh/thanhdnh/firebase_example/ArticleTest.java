package vn.edu.ueh.thanhdnh.firebase_example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArticleTest {
    // Kiểm tra constructor giữ đúng bốn thuộc tính của đề.
    @Test
    public void constructorStoresArticleFields() {
        Article article = new Article("article-1", "Tiêu đề", "Nội dung", "https://example.com/image.jpg");
        assertEquals("article-1", article.getId());
        assertEquals("Tiêu đề", article.getTitle());
        assertEquals("Nội dung", article.getContent());
        assertEquals("https://example.com/image.jpg", article.getImage_url());
    }

    // Kiểm tra constructor rỗng và setter phục vụ việc đọc document Firestore.
    @Test
    public void settersUpdateArticleFields() {
        Article article = new Article();
        article.setId("article-2");
        article.setTitle("Bài mới");
        article.setContent("Nội dung mới");
        article.setImage_url("https://example.com/new.jpg");
        assertEquals("article-2", article.getId());
        assertEquals("Bài mới", article.getTitle());
        assertEquals("Nội dung mới", article.getContent());
        assertEquals("https://example.com/new.jpg", article.getImage_url());
    }
}
