package com.example.foodapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HotDogPage extends AppCompatActivity {

    RecyclerView recyclerView;
    List<BurgerItem> items;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hot_dog_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ShowRecycleHotDogView();

        imgBack = findViewById(R.id.imageView);
        imgBack.setOnClickListener(v -> finish());
    }

    public void ShowRecycleHotDogView(){
        recyclerView = findViewById(R.id.recycleview);
        items = new ArrayList<BurgerItem>();
        items.add(new BurgerItem("Burger King", "Four Season", "$40", R.drawable.hotdog1, R.drawable.ic_plus));
        items.add(new BurgerItem("Burger King", "Four Season", "$40", R.drawable.hotdog2, R.drawable.ic_plus));
        items.add(new BurgerItem("Burger Cheese", "Four Season", "$40", R.drawable.hotdog3, R.drawable.ic_plus));
        items.add(new BurgerItem("Burger King", "Four Season", "$40", R.drawable.hotdog4, R.drawable.ic_plus));
        items.add(new BurgerItem("Burger King", "Four Season", "$40", R.drawable.hotdog5, R.drawable.ic_plus));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new BurgerAdapter(this, items));
    }
}