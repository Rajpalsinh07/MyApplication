package login1.myapplication;

import android.widget.EditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Moksha on 3/21/2017.
 */

public interface REGAPI {

    @GET("/Smart_guj/services/register1.php")
    Call<String> register1(@Query("Name") String name, @Query("Phnno") String phnno, @Query("Emailid") String emailid , @Query("Password") String password);

    @GET("/Smart_Guj/services/login.php")
    Call<String> login(@Query("Name") String name, @Query("Password") String password);

    @POST("/Smart_Guj/services/complaint.php")
    Call<String> complaint(@Query("Category") String category, @Query("Location") String location, @Query("Detail") String detail , @Query("img")String imagestring, @Query("Mobile")String mobile);


    @GET("/Smart_Guj/services/get_user.php")
    Call<String> contact(@Query("name") String name, @Query("phnno") String phnno, @Query("emailid") String emailid ,@Query("password") String password);

    @GET("my_json")
    Call<List<cmodel>> getcmodeldetails();

    @GET("/Smart_Guj/services/historical_det.php")
    Call<List<TourismModel>> historicaldet();

    @GET("/Smart_Guj/services/form.php")
    Call<List<FormModel>> form();



}
