package edu.brandeis.jjwang95.inminder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Password extends AppCompatActivity {
    private DBHelper helper;
    private Cursor cursor;
    private PasswordCursorAdapter adapter;
    private int code = 123;
    private ListView list;
    private SearchView search;
    private Long getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

//        search = (SearchView) findViewById(R.id.password_search);
        list = (ListView) findViewById(R.id.password_list);


        helper = DBHelper.getInstance(getApplicationContext());
        cursor = helper.getAllPasswords();
        String[] from = new String[] {"website","email"};
        int[] to = new int[] {R.id.website,R.id.email};

        adapter = new PasswordCursorAdapter(this,R.layout.activity_password,cursor,from,to,0);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        getID = id;
                        AlertDialog.Builder builder = new AlertDialog.Builder(Password.this);

                        builder.setTitle("Your password is: ");
                        builder.setMessage(helper.getPassword(id).getPassword());
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Copy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("",helper.getPassword(getID).getPassword());
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(getApplicationContext(), "Password copied!", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNeutralButton("Go to site", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Password.this,Password_website.class);
                                String url = helper.getPassword(getID).getWebsite();
                                i.putExtra("site",url);
                                if (URLUtil.isValidUrl(url)) {
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Not a valid website!", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
        );

//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                if (cursor==null) {
////                    Toast.makeText(Password.this,"No results found.",Toast.LENGTH_LONG).show();
////                }
////                adapter.swapCursor(cursor);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String text) {
////                adapter.swapCursor(cursor);
//                adapter.getFilter().filter(text);
//                return true;
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == code) {
            if (resultCode == RESULT_OK) {
                String accountType = data.getStringExtra("account type");
                String accountName = data.getStringExtra("account name");
                String password = data.getStringExtra("password");

                PasswordObject papa = new PasswordObject(accountType, accountName, password);
                long id = helper.createPassword(papa);
                papa.setId(id);
                cursor = helper.getAllPasswords();

                String[] from = new String[] {"website","email"};
                int[] to = new int[] {R.id.website,R.id.email};

                adapter = new PasswordCursorAdapter(this,R.layout.activity_password,cursor,from,to,0);
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
