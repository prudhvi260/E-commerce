package prudhvi.com.e_commerce;

import prudhvi.com.e_commerce.Model.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitClient {
    @POST("posts")//result of this post we are going to recieve a same Order Call<>
    Call<Order>goToFakePayment(@Body Order order);
}
