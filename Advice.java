package com.example.app_dev.healthcare.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_dev.healthcare.R;
import com.example.app_dev.healthcare.item.CustomDialog;
import com.example.app_dev.healthcare.utils.HttpPostSend;
import com.example.app_dev.healthcare.utils.JSONCall;
import com.example.app_dev.healthcare.utils.JSONUtil;

import org.json.JSONObject;
import java.util.ArrayList;

public class Advice extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private static Context mContext;
    private static CustomDialog dialog;

    private final String gray = "#9ea3a8";
    private final String red = "#ff3a56";
    private final String purple = "#8067e9";
    private final String green = "#31ab24";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // prevent screen rotation
        setContentView(R.layout.activity_advice);

        mContext = this;

        getSupportActionBar().setElevation(1); // degree of action bar bottom shadow
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); // for showing custom action bar
        getSupportActionBar().setCustomView(R.layout.activity_custom_action_bar); // apply defined custom action bar

        // back button event in action bar //
        Button backBtn = (Button) findViewById(R.id.action_bar_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog = new CustomDialog(mContext);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // transparent background of dialog window
        dialog.setCanceledOnTouchOutside(false);

        /* Create the adapter that will return a fragment for each of the three
         primary sections of the activity. */
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setTabTextColors(Color.parseColor(gray), Color.parseColor(red)); // initialize first tab color
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor(red));
        // give color each tabs //
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor(red)); // tab color
                    tabLayout.setTabTextColors(Color.parseColor(gray), Color.parseColor(red)); // indicator color
                } else if (tab.getPosition() == 1) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor(purple));
                    tabLayout.setTabTextColors(Color.parseColor(gray), Color.parseColor(purple));
                } else if (tab.getPosition() == 2) {
                    tabLayout.setSelectedTabIndicatorColor(Color.parseColor(green));
                    tabLayout.setTabTextColors(Color.parseColor(gray), Color.parseColor(green));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    } // onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_advice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        // actual contents of each page //
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_advice, container, false);

            int page = getArguments().getInt(ARG_SECTION_NUMBER);
            Button deviceApply = (Button) rootView.findViewById(R.id.device_apply);
            Button videoPlay = (Button) rootView.findViewById(R.id.video_play);

            if (page == 1) {
                deviceApply.setBackground(ContextCompat.getDrawable(mContext, R.drawable.device_apply_button_red));
                videoPlay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.video_play_button_red));
                deviceApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                    }
                });
                videoPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "비디오", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=EMVcS-1ftjE")));
                    }
                });
            } else if (page == 2) {
                deviceApply.setBackground(ContextCompat.getDrawable(mContext, R.drawable.device_apply_button_purple));
                videoPlay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.video_play_button_purple));
            } else if (page == 3) {
                deviceApply.setBackground(ContextCompat.getDrawable(mContext, R.drawable.device_apply_button_green));
                videoPlay.setBackground(ContextCompat.getDrawable(mContext, R.drawable.video_play_button_green));
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "스트레스";
                case 1:
                    return "우울감";
                case 2:
                    return "피로감";
            }
            return null;
        }
    }


    private class TEST_SERVER extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = HttpPostSend.executeTest(params[0]);



            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {




                Log.i("healthmax_test", "S !!!");
//                try {
//                    String loginResult = json.getString("s_type");
//                    if (loginResult.equals("S")) {
//                        Log.i("HealthUp", "get main info success");
//                    } else {
//                        Log.i("HealthUp", "send main info fail");
//                        Toast.makeText(getBaseContext(), "get main info fail", Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else {
                Log.e("healthmax_test", "F !!!");
            }
        }
    }


} // Advice.java