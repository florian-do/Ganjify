package fr.do_f.ganjify.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fr.do_f.ganjify.R;
import fr.do_f.ganjify.api.RestClient;
import fr.do_f.ganjify.api.json.StatsPost;
import fr.do_f.ganjify.api.json.StatsPostResponse;
import fr.do_f.ganjify.api.json.StatsResponse;
import fr.do_f.ganjify.api.json.UsersResponse;
import fr.do_f.ganjify.api.json.object.User;
import fr.do_f.ganjify.home.FeedActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedPostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, Callback<StatsPostResponse> {

    public static final String  TYPE = "feed_post_type";
    private static final String TOKEN = "fr.do_f.ganjify.token";
    private static final String PACKAGE = "fr.do_f.ganjify";
    private static final String DEBUG_TAG = "FeedPostActivity";

    private String          token;
    private String          type;
    private List<User>      users;
    private List<String>    ids = new ArrayList<String>();

    @Bind(R.id.feed_post_image)
    ImageView               logo;

    @Bind(R.id.feed_post_submit)
    Button                  submit;

    @Bind(R.id.feed_post_who)
    AutoCompleteTextView    who;

    @Bind(R.id.feed_post_comment)
    EditText                comment;

    @Bind(R.id.feed_post_autocomplete_text)
    TextView                text;

    public static void newInstance(String type, Activity startingActivity)
    {
        Intent i = new Intent(startingActivity, FeedPostActivity.class);
        i.putExtra(TYPE, type);
        startingActivity.startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_post);
        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences(
                PACKAGE, Context.MODE_PRIVATE);
        token = prefs.getString(TOKEN, "null");

        getUsers();
        type = getIntent().getStringExtra(TYPE);
        int logo_id = (type.equals("PET")) ? R.drawable.feed_type_spliff : R.drawable.feed_type_bong;
        int submit_id = (type.equals("PET")) ? R.string.feed_post_validate_pet : R.string.feed_post_validate_douille;
//        String[] languages = {"Android ","java","IOS","SQL","JDBC","Web services"};
//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,languages);
//        who.setThreshold(1);
//        who.setAdapter(adapter);
        who.setOnItemClickListener(this);

        logo.setImageResource(logo_id);
        submit.setText(getResources().getText(submit_id));
    }

    protected void getUsers()
    {
        Call<UsersResponse> call = RestClient.get(token).getUsers();
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                users = response.body().getUsers();
                String[] languages = new String[users.size()+1];
                int i = 0;
                for ( User user: users) {
                    languages[i++] = user.getUsername();
                }
                languages[i] = "Amsterdam";
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.adapter_autocomplete, languages);
                who.setThreshold(1);
                who.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        text.setVisibility(View.VISIBLE);
        String cUser = parent.getItemAtPosition(position).toString();
        Log.d("DEBUG", "onItemClick : " + parent.getItemAtPosition(position).toString());
        if (cUser.equals("Amsterdam"))
        {
            //ids.add("56bf56670b20be1700a03125"); //Alex
            ids.add("56c081360e77481700d5f0fa"); //Justine
            ids.add("56bf56730b20be1700a03126"); //Zanchi
            ids.add("56c8bc3a11716c1700c02626"); //Tim
            ids.add("56c982f711716c1700c02631"); //Flo
            Log.d(DEBUG_TAG, "AMSTERDAM");
            text.setText(text.getText().toString() + " Amsterdam");
            Snackbar.make(view, "Amsterdam à été ajouté", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else
        {
            for ( User user: users)
            {
                if (user.getUsername().equals(cUser))
                {
                    ids.add(user.getId());
                    text.setText(text.getText().toString() + " " + cUser);
                    Snackbar.make(view, cUser+" à été ajouté", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        }

        who.setText(" ");
    }

    @OnClick(R.id.feed_post_submit)
    public void onClick(View v)
    {
        if (ids.size() != 0) {
            for ( String id: ids) {
                Log.d(DEBUG_TAG, "TAMERE LE DEBUG : "+id);
            }
            StatsPost post = new StatsPost(type, ids, comment.getText().toString());
            Call<StatsPostResponse> call = RestClient.get(token).postStats(post);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<StatsPostResponse> call, Response<StatsPostResponse> response) {
        Snackbar.make(getCurrentFocus(), " à été ajouté", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        finish();
    }

    @Override
    public void onFailure(Call<StatsPostResponse> call, Throwable t) {
        Log.d(DEBUG_TAG, "FAIL "+t.getMessage());
    }
}
