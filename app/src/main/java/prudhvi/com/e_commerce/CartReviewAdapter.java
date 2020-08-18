package prudhvi.com.e_commerce;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import prudhvi.com.e_commerce.Model.GroceryItem;

public class CartReviewAdapter extends RecyclerView.Adapter<CartReviewAdapter.ViewHolder> {
    private ArrayList<GroceryItem>items=new ArrayList<>();
    public interface GetTotalPrice{
        void onGettingTotalPriceResult(double price);
    }
    public interface DeleteCartItem{
        void onDeletingResult(GroceryItem item);
    }
    private DeleteCartItem deleteCartItem;
    private GetTotalPrice getTotalPrice;
    private Fragment fragment;

    public CartReviewAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public CartReviewAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_rec_view_list,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtName.setText(items.get(position).getName());
        holder.txtPrice.setText(String.valueOf(items.get(position).getPrice()));

        deleteCartItem=(DeleteCartItem)fragment;
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(fragment.getActivity())
                        .setTitle("Delete Item")
                        .setMessage("Are You want to delete"+items.get(position).getName()+" ")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteCartItem.onDeletingResult(items.get(position));

                            }
                        });
                builder.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       private TextView txtName,txtPrice,txtDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName=itemView.findViewById(R.id.txtName);
            txtPrice=itemView.findViewById(R.id.txtprice);
            txtDelete=itemView.findViewById(R.id.btndelete);

        }
    }
    private void calculatePrice(){

        try {
            getTotalPrice=(GetTotalPrice)fragment;
        }catch (ClassCastException e)
        {
            e.printStackTrace();
        }
        double totalPrice=0;
        for (GroceryItem item:items){
            totalPrice+=item.getPrice();
        }
        getTotalPrice.onGettingTotalPriceResult(totalPrice);
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        calculatePrice();
        notifyDataSetChanged();
    }
}
