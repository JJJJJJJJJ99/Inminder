package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        final EditText account_type = (EditText) findViewById(R.id.account_type);
        final EditText account_name = (EditText) findViewById(R.id.account_name);
        final EditText password = (EditText) findViewById(R.id.password);

        Button edit_password = (Button) findViewById(R.id.edit_password);
        Button save_password = (Button) findViewById(R.id.save_password);
        Button cancel_password = (Button) findViewById(R.id.cancel_password);

        View.OnClickListener editOcl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        View.OnClickListener saveOcl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("account type", account_type.getText().toString());
                data.putExtra("account name",account_name.getText().toString());
                data.putExtra("password", password.getText().toString());
                setResult(RESULT_OK,data);
                finish();
            }
        };

        View.OnClickListener cancelOcl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        edit_password.setOnClickListener(editOcl);
        save_password.setOnClickListener(saveOcl);
        cancel_password.setOnClickListener(cancelOcl);
    }
}
