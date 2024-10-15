    package com.example.foodapp;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.view.menu.ShowableListMenu;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.navigation.NavigationBarView;

    import java.util.ArrayList;
    import java.util.List;

    public class Home extends AppCompatActivity {

        RecyclerView recyclerView, recyclerResView;
        BottomNavigationView bottomNavigationView;
        List<Category_Item> items;
        List<Restaurant_Item> resItems;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_home);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            bottomNavigationView = findViewById(R.id.bottomNavigationView);

            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.menu_tichdiem){
                        Intent intent = new Intent(getApplicationContext(), TichDiem.class);
                        startActivity(intent);
                    }

                    if (item.getItemId() == R.id.menu_thongbao){
                        Intent intent = new Intent(getApplicationContext(), Noti.class);
                        startActivity(intent);
                    }

                    if (item.getItemId() == R.id.menu_toi){
                        Intent intent = new Intent(getApplicationContext(), Info.class);
                        startActivity(intent);
                    }

                    return true;
                }
            });

            ShowRecycleCategoryView();
            ShowRecycleRestaurantView();

        }
        public void ShowRecycleCategoryView(){
            recyclerView = findViewById(R.id.recycleview);
            items = new ArrayList<Category_Item>();
            items.add(new Category_Item(R.drawable.btn_all));
            items.add(new Category_Item(R.drawable.btn_burger));
            items.add(new Category_Item(R.drawable.btn_hotdog));
            items.add(new Category_Item(R.drawable.btn_pizza));

            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            MyAdapter adapter = new MyAdapter(getApplicationContext(), items);
            adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // Xử lý sự kiện click tại đây
                    Toast.makeText(Home.this, "Clicked on category: " + position, Toast.LENGTH_SHORT).show();
                    if(position==1){
                        Intent intent = new Intent(getApplicationContext(),BurgerPage.class);
                        startActivity(intent);
                    }
                    if(position==2){
                        Intent intent = new Intent(getApplicationContext(),HotDogPage.class);
                        startActivity(intent);
                    }
                    if(position==3){
                        Intent intent = new Intent(getApplicationContext(), PizzaPage.class);
                        startActivity(intent);
                    }
                }
            });

            recyclerView.setAdapter(adapter);

        }

        public void ShowRecycleRestaurantView(){
            recyclerResView = findViewById(R.id.recycleviewRes);
            resItems = new ArrayList<Restaurant_Item>();
            resItems.add(new Restaurant_Item(R.drawable.res1,"Four Season Restaurant","Burger - Pizza"));
            resItems.add(new Restaurant_Item(R.drawable.res2,"Golden Restaurant","Burger - Pizza"));
            resItems.add(new Restaurant_Item(R.drawable.res3,"Rose Restaurant","Burger - Pizza"));
            resItems.add(new Restaurant_Item(R.drawable.res4,"LiLy Restaurant","Burger - Pizza"));
            resItems.add(new Restaurant_Item(R.drawable.res5,"Shine Restaurant","Burger - Pizza"));

            recyclerResView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
            recyclerResView.setAdapter(new MyResAdapter(getApplicationContext(),resItems));
        }
    }