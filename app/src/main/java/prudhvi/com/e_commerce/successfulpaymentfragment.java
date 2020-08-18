package prudhvi.com.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import prudhvi.com.e_commerce.Model.GroceryItem;
import prudhvi.com.e_commerce.Model.Order;

public class successfulpaymentfragment extends Fragment {
    private Button btnGoBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_success_payment,container,false);
        Utils utils=new Utils(getActivity());
        utils.removeCartItems();
        Bundle bundle=getArguments();
        try {
            Order order=bundle.getParcelable("order");
            ArrayList<Integer>itemIds=order.getItems();
            utils.addpopularityPoint(itemIds);
            ArrayList<GroceryItem>items=utils.getItemsByID(itemIds);
            for (GroceryItem item:items)
            {
                utils.increaseUserPoint(item,3);
            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        btnGoBack=view.findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
}
