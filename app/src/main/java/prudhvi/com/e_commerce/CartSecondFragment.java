package prudhvi.com.e_commerce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import prudhvi.com.e_commerce.Model.Order;

public class CartSecondFragment extends Fragment {
    private EditText edttxtAddress,edttxtZipcode,edtTxtPhoneNumber,edtTxtemail;
    private Button btnBack,btnNext;
    private RelativeLayout parent,adddressReLayout,emailReLayout,phoneNumberReLayout,zipcodeReLayout;
    private NestedScrollView nestedScrollView1;
    private Order incommingorder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_second_cart,container,false);
        initviews(view);
        Bundle bundle=getArguments();
        if (null!=bundle)
        {
            incommingorder=bundle.getParcelable("order");
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.out,R.anim.in)
                        .replace(R.id.fragment_container1,new CaetFirstFragment()).commit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()){
                    passData();
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Please Enter All Fields")
                            .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.create().show();
                }
            }
        });
        initRelLayouts();
        return view;
    }

    private void initviews(View view) {
        edttxtAddress=view.findViewById(R.id.editTextAddress);
        edttxtZipcode=view.findViewById(R.id.editTextZipcode);
        edtTxtPhoneNumber=view.findViewById(R.id.edittxtPhoneNumber);
        edtTxtemail=view.findViewById(R.id.edittxtEmail);
        btnBack=view.findViewById(R.id.btnBack);
        btnNext=view.findViewById(R.id.btnNext);
        parent=view.findViewById(R.id.parent);
        adddressReLayout=view.findViewById(R.id.addressReLayout);
        zipcodeReLayout=view.findViewById(R.id.ZipcodeRelLayout);
        emailReLayout=view.findViewById(R.id.emailReLayout);
        phoneNumberReLayout=view.findViewById(R.id.phoneNumberRelativeLayout);
        nestedScrollView1=view.findViewById(R.id.nestedscrollview1);
    }
    private boolean validateData()
    {
        if (edttxtAddress.getText().toString().equals(""))
        {
            return false;
        }
        if (edtTxtemail.getText().toString().equals(""))
        {
            return false;
        }
        if (edttxtZipcode.getText().toString().equals(""))
        {
            return false;
        }
        if (edtTxtPhoneNumber.getText().toString().equals(""))
        {
            return false;
        }
        return true;
    }
    private void passData()
    {
     Bundle bundle=new Bundle();
     bundle.putParcelable("order",incommingorder);
     ThirdCartFragment thirdCartFragment=new ThirdCartFragment();
     thirdCartFragment.setArguments(bundle);
     getActivity().getSupportFragmentManager().beginTransaction()
             .setCustomAnimations(R.anim.in,R.anim.out)
             .replace(R.id.fragment_container1,thirdCartFragment).commit();
    }
    private void closeKeyboard()
    {
        View view=getActivity().getCurrentFocus();
        if (null!=view)
        {
            InputMethodManager imm=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    private void initRelLayouts()
    {
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });
        adddressReLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });
        zipcodeReLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });
        phoneNumberReLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });
        emailReLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });
        nestedScrollView1.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                closeKeyboard();
            }
        });
    }
}
