package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db;
    Button btAdd, btShow;
    EditText etTitle, etContent, etImageUrl;

    // Giữ màn hình nhập của thầy, đổi hai ô User thành ba ô Article.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        applySystemBarPadding();
        btAdd = findViewById(R.id.btAdd);
        btShow = findViewById(R.id.btShow);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        etImageUrl = findViewById(R.id.etImageUrl);
        btAdd.setOnClickListener(this);
        btShow.setOnClickListener(this);
        if (FirebaseApp.initializeApp(this) == null) {
            Toast.makeText(this, R.string.firebase_config_missing, Toast.LENGTH_LONG).show();
            btAdd.setEnabled(false);
            btShow.setEnabled(false);
            return;
        }
        db = FirebaseFirestore.getInstance();
    }

    // Giữ cách implements OnClickListener: Add lưu bài, Show mở danh sách.
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btAdd) {
            addArticle();
        } else if (view.getId() == R.id.btShow) {
            Intent intent = new Intent(getBaseContext(), ShowDataActivity.class);
            startActivity(intent);
        }
    }

    // Kiểm tra form, tự tạo ID document rồi lưu Article vào collection articles.
    private void addArticle() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        String imageUrl = etImageUrl.getText().toString().trim();
        etTitle.setError(null);
        etContent.setError(null);
        etImageUrl.setError(null);
        if (title.isEmpty()) {
            etTitle.setError(getString(R.string.title_required));
            etTitle.requestFocus();
            return;
        }
        if (content.isEmpty()) {
            etContent.setError(getString(R.string.content_required));
            etContent.requestFocus();
            return;
        }
        if (!imageUrl.startsWith("https://") || !android.util.Patterns.WEB_URL.matcher(imageUrl).matches()) {
            etImageUrl.setError(getString(R.string.image_url_required));
            etImageUrl.requestFocus();
            return;
        }
        DocumentReference document = db.collection("articles").document();
        Article article = new Article(document.getId(), title, content, imageUrl);
        btAdd.setEnabled(false);
        document.set(article).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            // Chỉ xóa form khi Firebase báo đã lưu thành công.
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                btAdd.setEnabled(true);
                if (task.isSuccessful()) {
                    etTitle.setText("");
                    etContent.setText("");
                    etImageUrl.setText("");
                    Toast.makeText(MainActivity.this, R.string.article_saved, Toast.LENGTH_SHORT).show();
                } else {
                    android.util.Log.e("Article", "Không lưu được bài viết", task.getException());
                    Toast.makeText(MainActivity.this, R.string.article_save_error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Chừa chỗ cho thanh hệ thống và bàn phím; dùng listener tường minh thay lambda.
    private void applySystemBarPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new OnApplyWindowInsetsListener() {
            // Áp dụng khoảng trống hệ thống vào layout gốc.
            @NonNull
            @Override
            public WindowInsetsCompat onApplyWindowInsets(@NonNull View view, @NonNull WindowInsetsCompat insets) {
                Insets spaces = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
                view.setPadding(spaces.left, spaces.top, spaces.right, spaces.bottom);
                return insets;
            }
        });
        ViewCompat.requestApplyInsets(findViewById(R.id.main));
    }
}
