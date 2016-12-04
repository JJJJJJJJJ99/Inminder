package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
//import com.beardedhen.androidbootstrap.TypefaceProvider;
//import com.beardedhen.androidbootstrap.BootstrapButton;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private SQLiteDatabase db;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        BootstrapButton rem_button = (BootstrapButton) findViewById(R.id.reminder_button);
//        BootstrapButton bill_button = (BootstrapButton) findViewById(R.id.bill_button);
//        BootstrapButton pass_button = (BootstrapButton) findViewById(R.id.password_button);

        /*********************Test*******************************************************/
//        Log.d("Start", "SHOW");
        dbHelper = DBHelper.getInstance(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        dbHelper.onOpen(db);

//        Log.d("Create dbHelper", "Created");
//        PasswordObject testPassword = new PasswordObject("lalalala", "1111");
//        long testPassword_id = dbHelper.createPassword(testPassword);


//        testPassword.setId(testPassword_id);
        //Log.d("Added one password", "Password count" + dbHelper.getAllPasswords().size());
//        dbHelper.deleteAllPassword();
        //Log.d("Delete All", "Password count" + dbHelper.getAllPasswords().size());
//
//        dbHelper.closeDB();
        /*********************Test*******************************************************/

//        rem_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent("edu.brandeis.jjwang95.inminder.Reminder"));
//            }
//        });
//
//        bill_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent("edu.brandeis.jjwang95.inminder.Bill"));
//            }
//        });
//
//        pass_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent("edu.brandeis.jjwang95.inminder.Password"));
//            }
//        });
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Reminder(), "Reminder");
//        adapter.addFragment(new TwoFragment(), "TWO");
//        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
