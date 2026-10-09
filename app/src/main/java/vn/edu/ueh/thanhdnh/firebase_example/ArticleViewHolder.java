package vn.edu.ueh.thanhdnh.firebase_example;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {
    private TextView txtId, txtTitle, txtContent, txtImageUrl;
    private ImageView imageArticle;

    // Tìm các thành phần của một ô Article, thay cho hai TextView User.
    public ArticleViewHolder(@NonNull View itemView) {
        super(itemView);
        txtId = itemView.findViewById(R.id.txt_id);
        txtTitle = itemView.findViewById(R.id.txt_title);
        txtContent = itemView.findViewById(R.id.txt_content);
        txtImageUrl = itemView.findViewById(R.id.txt_image_url);
        imageArticle = itemView.findViewById(R.id.image_article);
    }

    // Lấy TextView ID.
    public TextView getTxtId() { return txtId; }

    // Lấy TextView tiêu đề.
    public TextView getTxtTitle() { return txtTitle; }

    // Lấy TextView nội dung.
    public TextView getTxtContent() { return txtContent; }

    // Lấy TextView URL ảnh.
    public TextView getTxtImageUrl() { return txtImageUrl; }

    // Lấy ImageView để hiển thị ảnh mạng.
    public ImageView getImageArticle() { return imageArticle; }
}
