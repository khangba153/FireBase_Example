package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ShowDataActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<Article> articles = new ArrayList<Article>();
    ArticleViewAdapter adapter;
    ListenerRegistration articleListener;

    // Giữ RecyclerView và LinearLayoutManager của thầy để hiển thị Article.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_data);
        applySystemBarPadding();
        recyclerView = findViewById(R.id.reclyclerview);
        adapter = new ArticleViewAdapter(this, articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        if (FirebaseApp.initializeApp(this) == null) {
            Toast.makeText(this, R.string.firebase_config_missing, Toast.LENGTH_LONG).show();
            return;
        }
        db = FirebaseFirestore.getInstance();
    }

    // Lắng nghe articles khi màn hình đang hiển thị, giống SnapshotListener của thầy.
    @Override
    protected void onStart() {
        super.onStart();
        if (db == null) {
            return;
        }
        articleListener = db.collection("articles").addSnapshotListener(new EventListener<QuerySnapshot>() {
            // Mỗi lần dữ liệu đổi, đọc lại danh sách rồi cập nhật Adapter.
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    android.util.Log.e("Article", "Không đọc được articles", error);
                    Toast.makeText(ShowDataActivity.this, R.string.article_load_error, Toast.LENGTH_LONG).show();
                    return;
                }
                if (snapshots != null) {
                    articles.clear();
                    for (QueryDocumentSnapshot document : snapshots) {
                        Article article = document.toObject(Article.class);
                        article.setId(document.getId());
                        articles.add(article);
                    }
                    adapter.update(articles);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    // Gỡ listener khi rời màn hình để không tiếp tục cập nhật giao diện đã đóng.
    @Override
    protected void onStop() {
        if (articleListener != null) {
            articleListener.remove();
            articleListener = null;
        }
        super.onStop();
    }

    // Chừa khoảng trống cho thanh hệ thống giống màn hình nhập.
    private void applySystemBarPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() {
            // Đặt padding theo vùng thanh hệ thống.
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View view, @NonNull WindowInsetsCompat insets) {
                Insets spaces = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                view.setPadding(spaces.left, spaces.top, spaces.right, spaces.bottom);
                return insets;
            }
        });
        ViewCompat.requestApplyInsets(findViewById(R.id.main));
    }
}
