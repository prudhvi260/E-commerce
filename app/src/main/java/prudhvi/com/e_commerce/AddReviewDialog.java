package prudhvi.com.e_commerce;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import prudhvi.com.e_commerce.Model.GroceryItem;
import prudhvi.com.e_commerce.Model.Reviev;

public class AddReviewDialog extends DialogFragment {

    private EditText edtTxtname,edtTxtReview;
    private TextView txtItemName,txtWarning;
    private Button btnAddReview;
    private int itemid=0;
    public interface AddReview{
        void onAddReviewResult(Reviev reviev);
    }
  private AddReview addReiew;
    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_add_review,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("Add Review")
                .setView(view);

       initviews(view);
       Bundle bundle=getArguments();
       try {

           GroceryItem item=bundle.getParcelable("item");
           txtItemName.setText(item.getName());
           this.itemid=item.getId();

       }catch (NullPointerException e){
           e.printStackTrace();
       }
       btnAddReview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               addReiew();
           }
       });

       return builder.create();
    }
    private void addReiew()
    {
        if (validateData()){
            String name=edtTxtname.getText().toString();
            String ReviewText=edtTxtReview.getText().toString();
            String date=getCurrentDate();
            Reviev reviev=new Reviev(itemid,name,date,ReviewText);
            try {
                addReiew= (AddReview) getActivity();
                addReiew.onAddReviewResult(reviev);
            }catch (ClassCastException e)
            {
                e.printStackTrace();
            }
        }else {
            txtWarning.setText(View.VISIBLE);
        }

    }
    private String getCurrentDate()
    {
        Date date= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
    private void initviews(View view)
    {
        edtTxtname=view.findViewById(R.id.edTxtName);
        edtTxtReview=view.findViewById(R.id.edTxtReiew);
        txtItemName=view.findViewById(R.id.reviewName);
        txtWarning=view.findViewById(R.id.txtWarnning);
        btnAddReview=(Button) view.findViewById(R.id.bttnAdd);
    }
    private boolean validateData()
    {
        if (edtTxtname.getText().toString().equals("")){
            return false;
        }
        if (edtTxtReview.getText().toString().equals("")){
            return false;
        }
        return true;
    }

}
