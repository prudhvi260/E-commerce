package prudhvi.com.e_commerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import prudhvi.com.e_commerce.Model.Reviev;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>{
    private static final String TAG=" ReviewsAdapter ";
    private ArrayList<Reviev>reviews=new ArrayList<>();
    public ReviewsAdapter()
    {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_adapter_list_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(reviews.get(position).getUsername());
        holder.text.setText(reviews.get(position).getText());
        holder.date.setText(reviews.get(position).getData());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
     private TextView name,text,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.username);
            text=(TextView) itemView.findViewById(R.id.reviewText);
            date=(TextView) itemView.findViewById(R.id.txtdate);
        }
    }

    public void setReviews(ArrayList<Reviev> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}
