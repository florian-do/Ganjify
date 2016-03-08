package fr.do_f.ganjify.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.do_f.ganjify.R;
import fr.do_f.ganjify.login.fragment.LoginFragment;
import fr.do_f.ganjify.login.fragment.SignupFragment;

public class LoginActivity extends AppCompatActivity {

    private static final String TOKEN = "fr.do_f.ganjify.token";
    private static final String DEBUG_TAG = "LoginActivity";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter    mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Bind(R.id.container)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        SharedPreferences prefs = this.getSharedPreferences(
                "fr.do_f.ganjify", Context.MODE_PRIVATE);

        //prefs.edit().putString(TOKEN, null).apply();
        Boolean haveToken = (prefs.getString(TOKEN, "null").equals("null")) ? false : true;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), haveToken);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Boolean haveToken;

        public SectionsPagerAdapter(FragmentManager fm, Boolean haveToken)
        {
            super(fm);
            this.haveToken = haveToken;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return LoginFragment.newInstance(haveToken);
                case 1:
                    return SignupFragment.newInstance();
                default:
                    return LoginFragment.newInstance(haveToken);
            }
        }

        @Override
        public int getCount()
        {
            return (haveToken) ? 1 : 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
