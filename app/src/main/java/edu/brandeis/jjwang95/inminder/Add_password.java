package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_password extends AppCompatActivity {
    private EditText account_type,account_name,password;
    private Button save_password,cancel_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionBarTop);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        myToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Password");

        account_type = (EditText) findViewById(R.id.account_type);
        account_name = (EditText) findViewById(R.id.account_name);
        password = (EditText) findViewById(R.id.password);

//        save_password = (Button) findViewById(R.id.save_password);
//        cancel_password = (Button) findViewById(R.id.cancel_password);

//        View.OnClickListener saveOcl = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent data = new Intent();
//                data.putExtra("account type", account_type.getText().toString());
//                data.putExtra("account name",account_name.getText().toString());
//                data.putExtra("password", password.getText().toString());
//                setResult(RESULT_OK,data);
//                finish();
//            }
//        };

//        View.OnClickListener cancelOcl = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        };

//        save_password.setOnClickListener(saveOcl);
//        cancel_password.setOnClickListener(cancelOcl);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.create_password_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create_pw_done) {
            Intent data = new Intent();
            data.putExtra("account type", account_type.getText().toString());
            data.putExtra("account name",account_name.getText().toString());
            data.putExtra("password", password.getText().toString());
            setResult(RESULT_OK,data);
            finish();
            overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    public boolean onSupportNavigateUp(){
        finish();
        overridePendingTransition(R.anim.silde_in_left, R.anim.slide_out_right);
        return true;
    }
}
