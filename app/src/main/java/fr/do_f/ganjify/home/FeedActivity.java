package fr.do_f.ganjify.home;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.do_f.ganjify.R;
import fr.do_f.ganjify.home.activity.FeedPostActivity;
import fr.do_f.ganjify.home.fragment.FeedFragment;
import fr.do_f.ganjify.login.LoginActivity;

public class FeedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TOKEN = "fr.do_f.ganjify.token";
    private static final String PACKAGE = "fr.do_f.ganjify";

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.menu)
    FloatingActionMenu      menu;

    @Bind(R.id.menu_spliff)
    com.github.clans.fab.FloatingActionButton       spliff;

    @Bind(R.id.menu_douille)
    com.github.clans.fab.FloatingActionButton       douille;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, FeedFragment.newInstance(null, null))
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.menu_spliff)
    public void onSpliffClick()
    {
        menu.close(true);
        FeedPostActivity.newInstance("PET", this);
    }

    @OnClick(R.id.menu_douille)
    public void onDouilleClick()
    {
        menu.close(true);
        FeedPostActivity.newInstance("DOUILLE", this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, FeedFragment.newInstance(null, null))
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_dashboard) {

        } else if (id == R.id.nav_snoopwheel) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_signout) {
            SharedPreferences prefs = getSharedPreferences(
                    PACKAGE, Context.MODE_PRIVATE);
            prefs.edit().putString(TOKEN, null).apply();
            drawer.closeDrawer(GravityCompat.START);
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
