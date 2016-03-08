package fr.do_f.ganjify.login.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.do_f.ganjify.R;
import fr.do_f.ganjify.api.RestClient;
import fr.do_f.ganjify.api.json.LoginResponse;
import fr.do_f.ganjify.home.FeedActivity;
import fr.do_f.ganjify.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * do_f
 */
public class LoginFragment extends Fragment implements Callback<LoginResponse>, View.OnClickListener {

    private static final String TOKEN = "fr.do_f.ganjify.token";
    private static final String ARG_HAVE_TOKEN = "haveToken";

    private static final int ANIM_DURATION = 800;
    private static final int ANIM_DELAY = 600;

    private Boolean haveToken;

    // Login VIEWS
    @Bind(R.id.login_email)
    EditText email;
    @Bind(R.id.login_password)
    EditText        password;
    @Bind(R.id.login_submit)
    Button submit;
    @Bind(R.id.login_title)
    TextView title;
    @Bind(R.id.login_layout)
    LinearLayout loginLayout;
    @Bind(R.id.login_loading)
    LinearLayout    loginLoading;

    // Loading VIEWS
    @Bind(R.id.loading_layout)
    LinearLayout    loadingLayout;
    @Bind(R.id.loading_title)
    TextView        loadingTitle;

    @Bind(R.id.loading_1)
    ImageView loading_1;
    @Bind(R.id.loading_2)
    ImageView       loading_2;
    @Bind(R.id.loading_3)
    ImageView       loading_3;
    @Bind(R.id.loading_4)
    ImageView       loading_4;
    @Bind(R.id.loading_5)
    ImageView       loading_5;

    public LoginFragment() {
    }

    public static LoginFragment newInstance(Boolean haveToken) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_HAVE_TOKEN, haveToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            haveToken = getArguments().getBoolean(ARG_HAVE_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);

        if (haveToken)
            showLoading();
        else
            showLogin();

        return rootView;
    }

    public void showLogin()
    {
        loginLayout.setVisibility(View.VISIBLE);
        loginLoading.setVisibility(View.INVISIBLE);
//        email.setText("dev@ganjify.com");
//        password.setText("ganja1234");
        submit.setOnClickListener(this);
    }

    public void showLoading()
    {
        loginLoading.setVisibility(View.VISIBLE);

        int size = Utils.dpToPx(50);
        loadingLayout.setTranslationY(-size);
        loading_1.animate()
                .alpha(1)
                .setDuration(ANIM_DURATION)
                .setStartDelay(ANIM_DELAY);
        loading_2.animate()
                .alpha(1)
                .setDuration(ANIM_DURATION)
                .setStartDelay(ANIM_DELAY+100);
        loading_3.animate()
                .alpha(1)
                .setDuration(ANIM_DURATION)
                .setStartDelay(ANIM_DELAY + 200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        loadingLayout.animate()
                                .translationY(0)
                                .setDuration(ANIM_DURATION);
                    }
                });

        loading_4.animate()
                .alpha(1)
                .setDuration(ANIM_DURATION)
                .setStartDelay(ANIM_DELAY + 300);
        loading_5.animate()
                .alpha(1)
                .setDuration(ANIM_DURATION)
                .setStartDelay(ANIM_DELAY + 400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        loadingTitle.animate()
                                .alpha(1)
                                .setDuration(2000)
                                .setStartDelay(200)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        Intent i = new Intent(getActivity(), FeedActivity.class);
                                        getActivity().startActivity(i);
                                        getActivity().overridePendingTransition(0, 0);
                                        getActivity().finish();

                                    }
                                })
                                .start();
                    }
                });
    }


    //submit.onClick
    @Override
    public void onClick(View v) {
        LoginResponse.User user = new LoginResponse.User(email.getText().toString(), password.getText().toString());
        Call<LoginResponse> call = RestClient.get().signIn(user);
        call.enqueue(this);
    }

    //LoginResponse onResponse
    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        SharedPreferences prefs = getActivity().getSharedPreferences(
                "fr.do_f.ganjify", Context.MODE_PRIVATE);
        prefs.edit().putString(TOKEN, response.body().getToken()).apply();
        loginLayout.animate()
                .alpha(0)
                .setDuration(1500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        email.clearFocus();
                        password.clearFocus();
                        showLoading();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        loginLayout.setVisibility(View.INVISIBLE);
                    }
                });
    }

    //LoginResponse onFailure
    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        Log.d("Login Activity", "Fail : " + t.getMessage());
    }
}
