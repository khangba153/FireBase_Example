# FireBase Example: đổi User thành Article

Bài thực hành giữ luồng của project thầy: nhập form → lưu Firestore → xem danh sách
bằng RecyclerView và cập nhật khi dữ liệu đổi bằng SnapshotListener.

## Yêu cầu và các file đổi

Article có bốn trường: `String id`, `String title`, `String content`, `String image_url`.
ID tự tạo bởi Firestore và cũng được lưu trong document. Collection mới là `articles`;
không đổi hoặc xóa collection `users` cũ.

Java nằm trong `app/src/main/java/vn/edu/ueh/thanhdnh/firebase_example/`:

- `User.java` → `Article.java`: constructor rỗng cho Firestore, constructor đủ bốn trường, getter/setter.
- `UserViewAdapter.java` → `ArticleViewAdapter.java`: gán ID, tiêu đề, nội dung, URL và ảnh vào từng ô.
- `UserViewHolder.java` → `ArticleViewHolder.java`: giữ các thành phần của một ô Article.
- `MainActivity.java`: nhập title/content/image_url, tạo ID rồi lưu vào articles.
- `ShowDataActivity.java`: nghe articles, đọc thành List<Article> và cập nhật Adapter.

XML nằm trong `app/src/main/res/`:

- `layout/activity_main.xml` và `layout-land/activity_main.xml`: form Article cho dọc/ngang.
- `layout/contact_list.xml` → `layout/article_list.xml`: một giao diện item dùng lại cho mọi bài.
- `layout/activity_show_data.xml`: giữ ConstraintLayout gốc và RecyclerView của thầy.
- `values/strings.xml`: chữ của giao diện và thông báo lỗi.

Thêm dependency RecyclerView 1.3.2 tường minh và Picasso 2.8 để hiển thị ảnh HTTPS.
Giữ package, applicationId, Gradle và hai Activity gốc. Không thêm màn hình chi tiết,
lượt xem hoặc chức năng ngoài yêu cầu. Java không dùng lambda/ternary/Stream;
comment tiếng Việt phía trên mỗi hàm. Form chỉ được xóa sau khi lưu thành công.

## Cấu hình Firebase trước khi chạy

Project clone chưa có `app/google-services.json`. Cần dùng Firebase của bạn:

1. Trong Firebase Console, đăng ký Android app với package
   `vn.edu.ueh.thanhdnh.firebase_example`.
2. Tải `google-services.json` và đặt tại `D:\FireBase_Example\app\google-services.json`.
3. Bật Cloud Firestore, chọn database `(default)` và cấu hình rules phù hợp với bài lab.
   App gốc không có đăng nhập. Rules yêu cầu xác thực sẽ từ chối thao tác hiện tại;
   không mở quyền công khai cho dữ liệu thật chỉ để chạy bài.
4. Sync Gradle rồi chạy `MainActivity`, nhập tiêu đề, nội dung và URL ảnh HTTPS.
5. Bấm Thêm; sau thông báo thành công, bấm Xem danh sách để kiểm tra bài mới.

File cấu hình Firebase được gitignore. Không dùng cấu hình Firebase của thầy để ghi thử dữ liệu.
Nếu bạn cấu hình rules cần đăng nhập thì phải bổ sung xác thực như một yêu cầu riêng.

## Kiểm tra

Build đầy đủ: `./gradlew.bat :app:assembleDebug :app:testDebugUnitTest`.
Thiếu google-services.json sẽ khiến task processDebugGoogleServices thất bại.
Để chỉ kiểm tra Java/XML và unit test khi chưa có cấu hình:
`./gradlew.bat :app:assembleDebug :app:testDebugUnitTest -PskipFirebaseConfig`.
APK tạo bằng chế độ kiểm tra này không phải bản đã kết nối Firebase. App thông báo thiếu cấu hình
và vô hiệu hóa nút ghi/đọc nếu không khởi tạo được Firebase.

Máy hiện dùng JDK 21 tại `C:\Users\Admin\.jdks\jbr-21.0.11` cho Gradle 8.9.
Unit test Article kiểm tra constructor và getter/setter; không ghi vào Firestore thật.

![Project gốc của thầy](Firestore.gif)
