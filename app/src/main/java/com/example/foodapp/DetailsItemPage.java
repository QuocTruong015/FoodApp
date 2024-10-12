package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DetailsItemPage extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Comment_Item> items;
    ArrayList<Product> cartList = new ArrayList<>(); // Khởi tạo một lần, không reset lại mỗi lần thêm sản phẩm
    FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_item_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khai báo các view
        ImageView imgMinus = findViewById(R.id.imgMinus);
        TextView txtQuantity = findViewById(R.id.txtQuantity);
        ImageView imgPlus = findViewById(R.id.imgPlus);
        ImageView imgBack = findViewById(R.id.imageViewBack);
        ImageView btnCart = findViewById(R.id.imageView2);
        Button btnAddToCart = findViewById(R.id.buttonAddToCart);
        Button btnOrder = findViewById(R.id.btnOrder);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String foodName = intent.getStringExtra("foodName");
        String price = intent.getStringExtra("price");
        int image = intent.getIntExtra("img", 0);

        // Gán giá trị lên view
        TextView txtFoodName = findViewById(R.id.txtFoodname);
        TextView txtPrice = findViewById(R.id.txtPrice);
        ImageView imageView = findViewById(R.id.imageView3);

        txtFoodName.setText(foodName);
        txtPrice.setText(String.valueOf(price));
        imageView.setImageResource(image);

        db = FirebaseFirestore.getInstance();

        // Thiết lập sự kiện nút "Add to Cart"
        btnAddToCart.setOnClickListener(v -> {
            String quantity = txtQuantity.getText().toString();

            // Kiểm tra số lượng
            if (quantity.isEmpty() || Integer.parseInt(quantity) < 1) {
                Toast.makeText(getApplicationContext(), "Số lượng phải lớn hơn hoặc bằng 1", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo sản phẩm mới
            Product newProduct = new Product(foodName, price, quantity, image);

            // Gửi danh sách giỏ hàng qua màn hình Cart
//            Intent intent1 = new Intent(getApplicationContext(), Cart.class);
//            intent1.putExtra("cartList", cartList);
//            startActivity(intent1);

            // Thêm sản phẩm vào Firestore mà không làm mất dữ liệu cũ
            Map<String, Object> cartItemData = new HashMap<>();
            cartItemData.put("foodName", newProduct.getName());
            cartItemData.put("price", newProduct.getPrice());
            cartItemData.put("quantity", newProduct.getQuantity());
            cartItemData.put("image", newProduct.getImgResId());

            db.collection("carts").document("userCart")
                    .update("cartItems", FieldValue.arrayUnion(cartItemData)) // Thêm sản phẩm mới
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Sản phẩm đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        // Mở giỏ hàng
        btnCart.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), Cart.class);
            intent1.putExtra("cartList", cartList);
            startActivity(intent1);
        });

        // Sự kiện giảm số lượng
        imgMinus.setOnClickListener(v -> {
            int q = Integer.parseInt(txtQuantity.getText().toString());
            if (q > 1) {
                q--;
                txtQuantity.setText(String.valueOf(q));
            }
        });

        // Sự kiện tăng số lượng
        imgPlus.setOnClickListener(v -> {
            int q = Integer.parseInt(txtQuantity.getText().toString());
            q++;
            txtQuantity.setText(String.valueOf(q));
        });

        // Sự kiện nút quay lại
        imgBack.setOnClickListener(v -> finish());
        showCmtRecyclerview();
    }

    // Hiển thị danh sách bình luận
    public void showCmtRecyclerview() {
        recyclerView = findViewById(R.id.recycleviewCmt);
        items = new ArrayList<>();
        items.add(new Comment_Item(R.drawable.ava1, "Lee Son Sin", "It's really good! I love this so much!!!"));
        // ... Thêm các bình luận khác vào danh sách

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommentRecycleviewAdapter commentRecycleviewAdapter = new CommentRecycleviewAdapter(items, this);
        recyclerView.setAdapter(commentRecycleviewAdapter);
    }
}
