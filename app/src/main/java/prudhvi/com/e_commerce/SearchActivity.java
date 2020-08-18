package prudhvi.com.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import prudhvi.com.e_commerce.Model.GroceryItem;

public class SearchActivity extends AppCompatActivity implements showAllCategoriesDialog.SelectCategory {
    @Override
    public void onSelectedCategoryResult(String category) {
        Intent intent=new Intent(this,ShowitemsByCategoryActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }
    private EditText searchBar;
    private ImageView btnSearch;
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private TextView txtfirstCat,txtsecondCat,txtthirdCat,txtseeallcategories;
    private Utils utils;
    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        utils=new Utils(this);
        adapter=new GroceryItemAdapter(this);
        initViews();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initBottomNavigation();
        initThreeTextViews();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateSearch();
            }
        });
        txtseeallcategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllCategoriesDialog showAllCategoriesDialog1=new showAllCategoriesDialog();
                showAllCategoriesDialog1.show(getSupportFragmentManager(),"all dialog");

            }
        });
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  ArrayList<GroceryItem>items=utils.searchforitem(String.valueOf(charSequence));
                  adapter.setItems(items);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initThreeTextViews() {
        ArrayList<String>categories=utils.getThreeCategories();
        switch (categories.size())
        {
            case 1:txtfirstCat.setText(categories.get(0));
                   txtsecondCat.setVisibility(View.GONE);
                   txtthirdCat.setVisibility(View.GONE);
                   break;
            case 2:txtfirstCat.setText(categories.get(0));
                    txtsecondCat.setText(categories.get(1));
                    txtthirdCat.setVisibility(View.GONE);
                    break;
            case 3: txtfirstCat.setText(categories.get(0));
                    txtsecondCat.setText(categories.get(1));
                    txtthirdCat.setText(categories.get(2));
                    break;
            default:break;
        }
        txtfirstCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchActivity.this,ShowitemsByCategoryActivity.class);
                intent.putExtra("category",txtfirstCat.getText().toString());
                startActivity(intent);
            }
        });
        txtsecondCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchActivity.this,ShowitemsByCategoryActivity.class);
                intent.putExtra("category",txtsecondCat.getText().toString());
                startActivity(intent);
            }
        });
        txtthirdCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchActivity.this,ShowitemsByCategoryActivity.class);
                intent.putExtra("category",txtthirdCat.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void initiateSearch() {

        String text=searchBar.getText().toString();
        ArrayList<GroceryItem>items=utils.searchforitem(text);
        for (GroceryItem item:items)
        {
            utils.increaseUserPoint(item,3);
        }
        adapter.setItems(items);

    }


    private void initViews() {
        searchBar=findViewById(R.id.edttxtSearchbar);
        txtseeallcategories=findViewById(R.id.btnAllCategories);
        btnSearch=findViewById(R.id.btnSearch);
        recyclerView=findViewById(R.id.recyclerview);
        bottomNavigationView=findViewById(R.id.bottomnavigationview);
        txtfirstCat=findViewById(R.id.firstCategory);
        txtsecondCat=findViewById(R.id.secondCategory);
        txtthirdCat=findViewById(R.id.thirdCategory);
    }
    private void initBottomNavigation() {

        bottomNavigationView.setSelectedItemId(R.id.Serach);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.Serach:

                        break;
                    case R.id.homeActivity:
                        Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cart:
                          Intent cartintent=new Intent(SearchActivity.this,CartActivity.class);
                          cartintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                          startActivity(cartintent);
                        break;
                    default:
                }
                return true;
            }
        });
    }


}
