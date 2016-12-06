package edu.brandeis.jjwang95.inminder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BillNote extends AppCompatActivity {

    public EditText billNote;
    public BillObject bill;
    public DBHelper dbHelper;
    public SQLiteDatabase db;
    public long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_note);
        dbHelper = DBHelper.getInstance(getApplicationContext());
        dbHelper.onOpen(db);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b != null){
            id = (long) b.get("id");
        }
        bill = dbHelper.getBill(id);

        billNote = (EditText) findViewById(R.id.billTextNote);
        billNote.setText(bill.getNote());
        Button save = (Button) findViewById(R.id.billButtonSave);
        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("billNote", billNote.getText().toString());
                bill.setNote(billNote.getText().toString());
                dbHelper.updateBill(bill);
                //Intent intent = new Intent(BillNote.this, Bill.class);
                //BillNote.this.startActivity(intent);
                finish();
            }
        });

        dbHelper.updateBill(bill);
    }

}
