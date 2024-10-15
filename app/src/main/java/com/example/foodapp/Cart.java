package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity {

    private ArrayList<Product> cartItems = new ArrayList<>();
    private FirebaseFirestore db;
    private RecyclerView recyclerViewCart; // Khai báo biến RecyclerView
    ImageView imgBack;
    Button btnThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgBack = findViewById(R.id.imageViewBack);
        btnThanhToan = findViewById(R.id.button);
        recyclerViewCart = findViewById(R.id.recyclerView); // Khởi tạo RecyclerView
        db = FirebaseFirestore.getInstance(); // Khởi tạo Firestore

        imgBack.setOnClickListener(v -> finish());

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        ArrayList<Product> list = (ArrayList<Product>) intent.getSerializableExtra("cartList");

        if (list != null) {
            cartItems.addAll(list); // Thêm sản phẩm từ intent vào danh sách giỏ hàng
        }

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        ProductAdapter adapter = new ProductAdapter(cartItems, this);
        recyclerViewCart.setAdapter(adapter);

        // Gọi hàm tải dữ liệu từ Firestore nếu cần
        loadCartData();
    }

    private void loadCartData() {
        db.collection("carts").document("userCart")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Map<String, Object> data = task.getResult().getData();
                        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("cartItems");

                        if (items != null) {
                            cartItems.clear(); // Xóa danh sách hiện tại để tránh trùng lặp

                            for (Map<String, Object> item : items) {
                                String foodName = (String) item.get("foodName");
                                String price = (String) item.get("price");
                                String quantity = (String) item.get("quantity");
                                Long imageLong = (Long) item.get("image"); // Nhận giá trị dưới dạng Long
                                int image = imageLong.intValue(); // Chuyển đổi Long thành int

                                Product product = new Product(foodName, price, quantity, image);
                                cartItems.add(product); // Thêm sản phẩm vào danh sách
                            }
                        }

                        // Cập nhật adapter
                        ((ProductAdapter) recyclerViewCart.getAdapter()).notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Không thể tải dữ liệu giỏ hàng.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
