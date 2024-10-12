package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BurgerPage extends AppCompatActivity {

    RecyclerView recyclerView;
    List<BurgerItem> items;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_burger_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ShowRecycleBurgerView();
        imgBack = findViewById(R.id.imageView);
        imgBack.setOnClickListener(v -> finish());
    }

    public void ShowRecycleBurgerView(){
        recyclerView = findViewById(R.id.recycleview);
        items = new ArrayList<BurgerItem>();
        items.add(new BurgerItem("Burger King", "Four Season", "$40", R.drawable.bur1, R.drawable.ic_plus));
        items.add(new BurgerItem("Burger King", "Four Season", "$40", R.drawable.bur2, R.drawable.ic_plus));
        items.add(new BurgerItem("Burger Cheese", "Four Season", "$40", R.drawable.bur3, R.drawable.ic_plus));
        items.add(new BurgerItem("Burger King", "Four Season", "$40", R.drawable.bur4, R.drawable.ic_plus));
        items.add(new BurgerItem("Burger King", "Four Season", "$40", R.drawable.bur5, R.drawable.ic_plus));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        BurgerAdapter burgerAdapter = new BurgerAdapter(this, items);
        recyclerView.setAdapter(burgerAdapter);
    }
}