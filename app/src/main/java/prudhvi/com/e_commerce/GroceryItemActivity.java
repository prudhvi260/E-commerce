package prudhvi.com.e_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import prudhvi.com.e_commerce.Model.GroceryItem;
import prudhvi.com.e_commerce.Model.Reviev;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview{

    private static final String TAG="GroceryItemActivity";
    private ReviewsAdapter adapter;
    private int currentrate=0;
    @Override
    public void onAddReviewResult(Reviev reviev) {
        Log.d(TAG,"onAddReiewResult : we are adding "+reviev.toString());
        utils.addReview(reviev);
        utils.increaseUserPoint(incommingItem,2);
        ArrayList<Reviev>revievs=utils.getReviewsForItem(reviev.getGroceryitemid());
        if (null!=revievs)
        {
            adapter.setReviews(revievs);
        }
    }
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TrackUserTime.LocalBinder binder=
                    (TrackUserTime.LocalBinder)iBinder;
            mservice=binder.getServices();
            isBound=true;
            mservice.setItem(incommingItem);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
               isBound=false;
        }
    };
    private TrackUserTime mservice;
    private Boolean isBound=false;
    private TextView txtNmae,txtPrice,txtDescription,txtAvaliability;
    private ImageView itemImage;
    private Button btnAddToCart;
    private ImageView firstFilledStar,secondFilledStar,thirdFilledstar,firstEmptyStar,secondEmptyStar,thirdEmptyStar;
    private RecyclerView reviewsrecview;

    private RelativeLayout addReviewRelayout;
    private Utils utils;
    private GroceryItem incommingItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);
        utils=new Utils(this);
        initViews();
        Intent intent=getIntent();
        try {
            incommingItem=intent.getParcelableExtra("item");
            this.currentrate=incommingItem.getRate();
            changeVisibility(currentrate);
            setViewvalues();
            utils.increaseUserPoint(incommingItem,1);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

    }

    private void setViewvalues() {
        txtNmae.setText(incommingItem.getName());
        txtPrice.setText(incommingItem.getPrice()+"$");
        txtAvaliability.setText(incommingItem.getAvailableamount()+" number(s) avaliable");
        txtDescription.setText(incommingItem.getDescription());
        Glide.with(this)
                .asBitmap()
                .load(incommingItem.getImageOrl())
                .into(itemImage);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.addItemtoCart(incommingItem.getId());
                Intent intent=new Intent(GroceryItemActivity.this,CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        handleStarsSituation();
        adapter=new ReviewsAdapter();
        reviewsrecview.setAdapter(adapter);
        reviewsrecview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Reviev>revievs=utils.getReviewsForItem(incommingItem.getId());
        if(null!=revievs)
        {
            adapter.setReviews(revievs);
        }
        addReviewRelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 AddReviewDialog addReviewDialog=new AddReviewDialog();
                 Bundle bundle=new Bundle();
                 bundle.putParcelable("item",incommingItem);
                 addReviewDialog.setArguments(bundle);
                 addReviewDialog.show(getSupportFragmentManager(),"add review dialog");
            }
        });
    }

    private void handleStarsSituation() {
        firstEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkifRateHasChanged(1)){
                    updatedatabase(1);
                    changeVisibility(1);
                    changeUserPoint(1);
                }
            }
        });
        secondEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkifRateHasChanged(2)){
                    updatedatabase(2);
                    changeVisibility(2);
                    changeUserPoint(2);
                }

            }
        });
        thirdEmptyStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkifRateHasChanged(3)){
                    updatedatabase(3);
                    changeVisibility(3);
                    changeUserPoint(3);
                }
            }
        });
        firstFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkifRateHasChanged(1)){
                    updatedatabase(1);
                    changeVisibility(1);
                    changeUserPoint(1);
                }
            }
        });
        secondFilledStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkifRateHasChanged(2)){
                    updatedatabase(2);
                    changeVisibility(2);
                    changeUserPoint(2);
                }
            }
        });
        thirdFilledstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkifRateHasChanged(3)){
                    updatedatabase(3);
                    changeVisibility(3);
                    changeUserPoint(3);
                }
            }
        });
    }
    private void updatedatabase(int newrate)
    {
        utils.updateTheRate(incommingItem,newrate);
    }
    private void changeVisibility(int newrate)
    {
        switch (newrate)
        {
            case 0:firstFilledStar.setVisibility(View.GONE);
                   secondFilledStar.setVisibility(View.GONE);
                   thirdFilledstar.setVisibility(View.GONE);
                   firstEmptyStar.setVisibility(View.VISIBLE);
                   secondEmptyStar.setVisibility(View.VISIBLE);
                   thirdEmptyStar.setVisibility(View.VISIBLE);
                   break;
            case 1:firstFilledStar.setVisibility(View.VISIBLE);
                    secondFilledStar.setVisibility(View.GONE);
                    thirdFilledstar.setVisibility(View.GONE);
                    firstEmptyStar.setVisibility(View.GONE);
                    secondEmptyStar.setVisibility(View.VISIBLE);
                    thirdEmptyStar.setVisibility(View.VISIBLE);
                    break;
            case 2:firstFilledStar.setVisibility(View.VISIBLE);
                    secondFilledStar.setVisibility(View.VISIBLE);
                    thirdFilledstar.setVisibility(View.GONE);
                    firstEmptyStar.setVisibility(View.GONE);
                    secondEmptyStar.setVisibility(View.GONE);
                    thirdEmptyStar.setVisibility(View.VISIBLE);
                    break;
            case 3:firstFilledStar.setVisibility(View.VISIBLE);
                    secondFilledStar.setVisibility(View.VISIBLE);
                    thirdFilledstar.setVisibility(View.VISIBLE);
                    firstEmptyStar.setVisibility(View.GONE);
                    secondEmptyStar.setVisibility(View.GONE);
                    thirdEmptyStar.setVisibility(View.GONE);
                    break;
             default:firstFilledStar.setVisibility(View.VISIBLE);
                     secondFilledStar.setVisibility(View.VISIBLE);
                     thirdFilledstar.setVisibility(View.VISIBLE);
                     firstEmptyStar.setVisibility(View.GONE);
                     secondEmptyStar.setVisibility(View.GONE);
                     thirdEmptyStar.setVisibility(View.GONE);
                     break;
        }
    }
    private boolean checkifRateHasChanged(int newRate)
    {
        if(newRate==currentrate)
        {
            return false;
        }
        else {
            return true;
        }
    }

    private void initViews() {
        Log.d(TAG,"initViews : Started");
        txtNmae=(TextView) findViewById(R.id.textname);
        txtPrice=(TextView) findViewById(R.id.txtprice);
        txtDescription=(TextView) findViewById(R.id.textdes);
        txtAvaliability=(TextView) findViewById(R.id.textavaliblity);
        itemImage=(ImageView) findViewById(R.id.itemimage);
        btnAddToCart=(Button) findViewById(R.id.btnaddtocart);
        firstEmptyStar=(ImageView) findViewById(R.id.firstemptystar);
        secondEmptyStar=(ImageView) findViewById(R.id.secondtemptystar);
        thirdEmptyStar=(ImageView) findViewById(R.id.thirdtemptystar);
        firstFilledStar=(ImageView) findViewById(R.id.firstfilledstar);
        secondFilledStar=(ImageView) findViewById(R.id.secondfilledstar);
        thirdFilledstar=(ImageView) findViewById(R.id.thirdfilledstar);
        reviewsrecview=(RecyclerView) findViewById(R.id.reviewsrecview);
        addReviewRelayout=(RelativeLayout) findViewById(R.id.addReviewsRellayout);
    }

private void changeUserPoint(int stars)
{
    utils.increaseUserPoint(incommingItem,(stars-currentrate)*2);
}

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,TrackUserTime.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (isBound){
            unbindService(connection);
        }
    }
}
