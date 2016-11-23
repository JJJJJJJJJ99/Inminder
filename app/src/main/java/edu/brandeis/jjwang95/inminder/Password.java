package edu.brandeis.jjwang95.inminder;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Password extends AppCompatActivity {
    private DBHelper helper;
    private Cursor cursor;
    private PasswordCursorAdapter adapter;
    private int code = 888;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        SearchView search = new SearchView(getApplicationContext());
        list = new ListView(getApplicationContext());

        helper = DBHelper.getInstance(getApplicationContext());
        cursor = helper.getAllPasswords();
        String[] from = new String[] {"website","email","password"};
        int[] to = new int[] {R.id.website,R.id.emails, R.id.password};

        adapter = new PasswordCursorAdapter(this,cursor);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Password.this);

                        builder.setTitle("Your password is: ");
                        builder.setMessage(helper.getPassword(id).getPassword());
                        builder.setPositiveButton("I got it!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==code) {
            if (resultCode == RESULT_OK) {
                String accountType = data.getStringExtra("account type");
                String accountName = data.getStringExtra("account name");
                String password = data.getStringExtra("password");

                helper.createPassword(new PasswordObject(accountType, accountName, password));
                cursor = helper.getAllPasswords();

                String[] from = new String[] {"website","email","password"};
                int[] to = new int[] {R.id.website,R.id.emails, R.id.password};

                adapter = new PasswordCursorAdapter(this,cursor);
                list.setAdapter(adapter);


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.passwordmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();
        if(selected == R.id.add_new_password){
            startActivityForResult(new Intent(this,Add_password.class),code);
        }

        return super.onOptionsItemSelected(item);
    }
}
