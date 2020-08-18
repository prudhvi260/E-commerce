package prudhvi.com.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private  static final String tag="MainActivity";
    private DrawerLayout drawer;
    private NavigationView navigationview;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.drawer_open,R.string.drawer_closed);
        drawer.addDrawerListener(toggle);
        toggle.syncState();  //adds animation to toggle
        navigationview.setNavigationItemSelectedListener(this);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,new MainFragment());
        transaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.cart:
                Intent cartintent=new Intent(MainActivity.this,CartActivity.class);
                cartintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cartintent);
                break;
            case R.id.categories:
                showAllCategoriesDialog showAllCategoriesDialog1=new showAllCategoriesDialog();
                showAllCategoriesDialog1.show(getSupportFragmentManager(),"all categories");
                break;
            case R.id.aboutus:
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("About Us")
                        .setMessage("This App is Developed By PrudhviRao Nelavelli")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create().show();
                break;
            case R.id.terms:
                AlertDialog.Builder termsbuilder=new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Terms of us")
                        .setMessage("No Terms & Conditions :)")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                termsbuilder.create().show();
                break;
            case R.id.license:
                LicensesDialog licensesDialog=new LicensesDialog();
                licensesDialog.show(getSupportFragmentManager(),"licenses Dialog");

                break;
            default:
        }
        return false;
    }
    public void initViews(){
        drawer= findViewById(R.id.drawer);
        navigationview= findViewById(R.id.navigationDrawer);
        toolbar= findViewById(R.id.toolbar);
    }


}
