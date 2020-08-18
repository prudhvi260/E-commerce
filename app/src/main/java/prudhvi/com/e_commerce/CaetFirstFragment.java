package prudhvi.com.e_commerce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import prudhvi.com.e_commerce.Model.GroceryItem;
import prudhvi.com.e_commerce.Model.Order;

public class CaetFirstFragment extends Fragment implements CartReviewAdapter.GetTotalPrice,
        CartReviewAdapter.DeleteCartItem {

    private TextView txtprice, txtNoItem;
    private RecyclerView recyclerView;
    private Utils utils;
    private Button btnnext;
    private CartReviewAdapter adapter;
    private double totalPrice=0;
    private ArrayList<Integer>items;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_cart, container, false);
        initViews(view);
        items=new ArrayList<>();
        utils = new Utils(getActivity());
        initRecView();
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order=new Order();
                order.setTotalPrice(totalPrice);
                order.setItems(items);
                Bundle bundle=new Bundle();
                bundle.putParcelable("order",order);
                CartSecondFragment cartSecondFragment=new CartSecondFragment();
                cartSecondFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.in,R.anim.out)
                        .replace(R.id.fragment_container1,cartSecondFragment).commit();

            }
        });
        return view;
    }

    private void initRecView() {
        adapter = new CartReviewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Utils utils = new Utils(getActivity());
        ArrayList<Integer> itemsIds = utils.getCartItems();
        if (null != itemsIds) {
            ArrayList<GroceryItem>items=utils.getItemsByID(itemsIds);
            if (items.size() == 0) {
                btnnext.setVisibility(View.GONE);
                btnnext.setEnabled(false);
                recyclerView.setVisibility(View.GONE);
                txtNoItem.setVisibility(View.VISIBLE);
            } else {
                btnnext.setVisibility(View.VISIBLE);
                btnnext.setEnabled(true);
                recyclerView.setVisibility(View.VISIBLE);
                txtNoItem.setVisibility(View.GONE);

            }
            this.items=itemsIds;
            adapter.setItems(items);
        }

    }

    private void initViews(View view) {
        txtprice = view.findViewById(R.id.txtSum);
        recyclerView = view.findViewById(R.id.recyclerview2);
        btnnext = view.findViewById(R.id.btnNext);
        txtNoItem = view.findViewById(R.id.txtNoItem);

    }

    @Override
    public void onGettingTotalPriceResult(double price) {
        txtprice.setText(String.valueOf(price));
        this.totalPrice=price;

    }

    @Override
    public void onDeletingResult(GroceryItem item) {
        ArrayList<Integer>itemids=new ArrayList<>();
        itemids.add(item.getId());
        ArrayList<GroceryItem>items=utils.getItemsByID(itemids);
        if (items.size()>0){
            ArrayList<Integer> newItemsIds = utils.deleteCartItem(items.get(0));
            if (newItemsIds.size() == 0) {
                btnnext.setVisibility(View.GONE);
                btnnext.setEnabled(false);
                recyclerView.setVisibility(View.GONE);
                txtNoItem.setVisibility(View.VISIBLE);
            } else {
                btnnext.setVisibility(View.VISIBLE);
                btnnext.setEnabled(true);
                recyclerView.setVisibility(View.VISIBLE);
                txtNoItem.setVisibility(View.GONE);

            }
            ArrayList<GroceryItem>newItems=utils.getItemsByID(newItemsIds);
            this.items=itemids;
            adapter.setItems(newItems);

        }

    }
}
