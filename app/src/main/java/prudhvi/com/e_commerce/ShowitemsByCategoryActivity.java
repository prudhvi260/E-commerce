package prudhvi.com.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import prudhvi.com.e_commerce.Model.GroceryItem;

public class ShowitemsByCategoryActivity extends AppCompatActivity {
    private TextView txtName;
    private RecyclerView recyclerView;
    private GroceryItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showitems_by_category);
        initviews();
        adapter=new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        try {
            Intent intent=getIntent();
            String category=intent.getStringExtra("category");
            Utils utils=new Utils(this);
            ArrayList<GroceryItem>items=utils.getItemsByCategory(category);
            adapter.setItems(items);
            txtName.setText(category);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    private void initviews() {
        txtName=(TextView) findViewById(R.id.txtCategory);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView1);
    }
}
