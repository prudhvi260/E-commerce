package prudhvi.com.e_commerce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import prudhvi.com.e_commerce.Model.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdCartFragment extends Fragment {
    private TextView txtPrice,txtShippingDetails;
    private Button btnback,btnNExt;
    private RadioGroup rgPaymentMethod;
    private Order incommingorder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_third_cart,container,false);
        initViews(view);
        Bundle bundle=getArguments();
        try {
            incommingorder=bundle.getParcelable("order");
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        if (null!=incommingorder){
            txtPrice.setText(String.valueOf(incommingorder.getTotalPrice()));
            txtShippingDetails.setText(" Items: \n\t Address: "+ incommingorder.getAddress()+"\n\t" +
                    "Email: " + incommingorder.getEmail() +"\n\t" +
                    "Zip Code: "+ incommingorder.getZipcode() +"\n\t" +
                    "Phone Number "+ incommingorder.getPhoneNumber());
            btnNExt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                       goToPaymnet();
                }
            });

        }
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
        return view;
    }
    private void goBack()
    {
        Order order=new Order();
        order.setTotalPrice(incommingorder.getTotalPrice());
        order.setItems(incommingorder.getItems());
        Bundle bundle=new Bundle();
        bundle.putParcelable("order",order);
        CartSecondFragment cartSecondFragment=new CartSecondFragment();
        cartSecondFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.out,R.anim.in)
                .replace(R.id.fragment_container1,cartSecondFragment)
                .commit();
    }
    private void goToPaymnet()
    {
        RadioButton radioButton=getActivity().findViewById(rgPaymentMethod.getCheckedRadioButtonId());
        incommingorder.setPaymentMethod(radioButton.getText().toString());
        incommingorder.setSuccess(true);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitClient retrofitClient=retrofit.create(RetrofitClient.class);
        Call<Order>call=retrofitClient.goToFakePayment(incommingorder);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (!response.isSuccessful()){
                    return;
                }
                goToPamentResult(response.body());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }
    private void goToPamentResult(Order order)
    {
        if (order.isSuccess())
        {
            successfulpaymentfragment successfulpaymentfragment1=new successfulpaymentfragment();
            Bundle bundle=new Bundle();
            bundle.putParcelable("order",order);
            successfulpaymentfragment1.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in,R.anim.out)
                    .replace(R.id.fragment_container1,successfulpaymentfragment1).commit();
        }else {
            FailedPaymentFragment failedPaymentFragment=new FailedPaymentFragment();
            Bundle bundle=new Bundle();
            bundle.putParcelable("order",order);
            failedPaymentFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.in,R.anim.out)
                    .replace(R.id.fragment_container1,failedPaymentFragment).commit();
        }
    }
    private void initViews(View view) {
        txtPrice=view.findViewById(R.id.priceExplain1);
        txtShippingDetails=view.findViewById(R.id.txtShippingDetail);
        btnback=view.findViewById(R.id.btnback);
        btnNExt=view.findViewById(R.id.btnFinish);
        rgPaymentMethod=view.findViewById(R.id.rgPaymnetMethods);
    }
}
