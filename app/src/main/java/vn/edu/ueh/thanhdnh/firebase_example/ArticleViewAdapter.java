package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private LayoutInflater mInflater;
    private List<Article> articles;

    // Nhận danh sách Article, giữ cấu trúc constructor của UserViewAdapter.
    public ArticleViewAdapter(Context context, List<Article> articles) {
        this.mInflater = LayoutInflater.from(context);
        this.articles = articles;
    }

    // Thay danh sách sau khi Firestore gửi dữ liệu.
    public void update(List<Article> articles) {
        this.articles = articles;
    }

    // Tạo giao diện một bài viết từ XML.
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customView = mInflater.inflate(R.layout.article_list, parent, false);
        return new ArticleViewHolder(customView);
    }

    // Gán bốn thuộc tính của Article và tải ảnh cho đúng ô đang hiển thị.
    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.getTxtId().setText("ID: " + article.getId());
        holder.getTxtTitle().setText(article.getTitle());
        holder.getTxtContent().setText(article.getContent());
        holder.getTxtImageUrl().setText(article.getImage_url());
        Picasso.get().cancelRequest(holder.getImageArticle());
        holder.getImageArticle().setImageResource(android.R.drawable.ic_menu_gallery);
        String imageUrl = article.getImage_url();
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            Picasso.get().load(imageUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_gallery)
                    .fit().centerCrop()
                    .into(holder.getImageArticle());
        }
    }

    // Trả về số bài viết trong danh sách.
    @Override
    public int getItemCount() {
        return articles.size();
    }
}
