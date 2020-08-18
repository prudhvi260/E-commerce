package prudhvi.com.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import prudhvi.com.e_commerce.Model.GroceryItem;

public class MainFragment extends Fragment {
    private static final String tag="MainFragment";
    private Utils utils;
    private RecyclerView newitemreview,popularitemreview,suggesteditemreview;
    private GroceryItemAdapter newItemAdapter,popularItemAdapter,suggestedItemAdapter;
    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        initViews(view);
        initBottomNavigation();
        utils=new Utils(getActivity());
        utils.initdatabase();
        initrecviews();

        return view;
    }

    private void initrecviews() {
        Log.d(tag,"recyclermethod:started");
        newItemAdapter=new GroceryItemAdapter(getActivity());
        popularItemAdapter=new GroceryItemAdapter(getActivity());
        suggestedItemAdapter=new GroceryItemAdapter(getActivity());

        newitemreview.setAdapter(newItemAdapter);
        popularitemreview.setAdapter(popularItemAdapter);
        suggesteditemreview.setAdapter(suggestedItemAdapter);

        newitemreview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularitemreview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        suggesteditemreview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        updateRecViews();

    }
    private void updateRecViews()
    {
        final ArrayList<GroceryItem>newitems=utils.getAllitems();
        Comparator<GroceryItem>newitemscomparator=new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem groceryItem, GroceryItem t1) {
                return groceryItem.getId()-t1.getId();
            }
        };
        Comparator<GroceryItem>reversenewitemcomparator=Collections.reverseOrder(newitemscomparator);
        Collections.sort(newitems,reversenewitemcomparator);
        if (null!=newitems)
        {
            newItemAdapter.setItems(newitems);
        }
        ArrayList<GroceryItem>popularitems=utils.getAllitems();
        Comparator<GroceryItem>popularitycomparator=new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem groceryItem, GroceryItem t1) {
                return comparebypopularity(groceryItem,t1);
            }
        };
        Comparator<GroceryItem>reversecomparator= Collections.reverseOrder(popularitycomparator);//revverse order because 10,9,8..
        Collections.sort(popularitems,reversecomparator);//sorts in reverse order
        popularItemAdapter.setItems(popularitems);
        ArrayList<GroceryItem>suggesteditems=utils.getAllitems();
        Comparator<GroceryItem>suggesteditemscomparator=new Comparator<GroceryItem>() {
            @Override
            public int compare(GroceryItem groceryItem, GroceryItem t1) {
                return groceryItem.getUserpoint()-t1.getUserpoint();
            }
        };
        Comparator<GroceryItem>reversesuggesteditems=Collections.reverseOrder(suggesteditemscomparator);
        Collections.sort(suggesteditems,reversesuggesteditems);
        suggestedItemAdapter.setItems(suggesteditems);
    }

    @Override
    public void onResume() {
        updateRecViews();
        super.onResume();
    }

    private int comparebypopularity(GroceryItem item1, GroceryItem item2)
    {
        Log.d(tag,"comparebypopularity:started");
        if (item1.getPopularityPoint()>item2.getPopularityPoint())
        {
            return 1;
        }
        else if (item1.getPopularityPoint()<item2.getPopularityPoint())
        {
            return -1;
        }else {
            return 0;
        }
    }

    private void initBottomNavigation() {
        Log.d(tag,"BottomNavigationView:Started");
        bottomNavigationView.setSelectedItemId(R.id.homeActivity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.Serach:
                        Intent intent=new Intent(getActivity(),SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.homeActivity:

                        break;
                    case R.id.cart:
                        Intent cartintent=new Intent(getActivity(),CartActivity.class);
                        cartintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartintent);
                        break;
                    default:
                }
                return true;
            }
        });
    }

    private void initViews(View view) {
        Log.d(tag,"initViews: started");
        bottomNavigationView=(BottomNavigationView)view.findViewById(R.id.bottomnavigationview);
        newitemreview=(RecyclerView)view.findViewById(R.id.newItemsreview);
        popularitemreview=(RecyclerView)view.findViewById(R.id.popularItems);
        suggesteditemreview=(RecyclerView)view.findViewById(R.id.suggestedItems);
    }
}
