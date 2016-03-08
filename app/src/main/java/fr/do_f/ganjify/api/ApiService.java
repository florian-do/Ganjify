package fr.do_f.ganjify.api;

import java.util.List;

import fr.do_f.ganjify.api.json.LoginResponse;
import fr.do_f.ganjify.api.json.StatsPost;
import fr.do_f.ganjify.api.json.StatsPostResponse;
import fr.do_f.ganjify.api.json.StatsResponse;
import fr.do_f.ganjify.api.json.UsersResponse;
import fr.do_f.ganjify.api.json.object.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by do_f on 22/02/16.
 */
public interface ApiService {

    @POST("login")
    Call<LoginResponse>         signIn(@Body LoginResponse.User user);

    @GET("statistics")
    Call<List<StatsResponse>>   getStats();

    @POST("statistics")
    Call<StatsPostResponse>         postStats(@Body StatsPost post);

    @GET("users")
    Call<UsersResponse>         getUsers();
}
