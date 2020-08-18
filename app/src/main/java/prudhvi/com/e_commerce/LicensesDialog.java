package prudhvi.com.e_commerce;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LicensesDialog extends DialogFragment {

    private TextView textView;
    private Button btnDismiss;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view=getActivity().getLayoutInflater().inflate(R.layout.dialog_lisences,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Licences");
        textView=view.findViewById(R.id.textView);
        textView.setText(getLicencesText());
        btnDismiss=view.findViewById(R.id.btnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return builder.create();
    }
    private String getLicencesText()
    {
        String message="This App Licences Belongs to Prudhvi";
        return message;
    }
}
