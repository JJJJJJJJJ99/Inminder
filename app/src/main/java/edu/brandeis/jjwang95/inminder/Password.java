package edu.brandeis.jjwang95.inminder;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class Password extends Fragment {
    private DBHelper helper;
    private Cursor cursor;
    private PasswordCursorAdapter adapter;
    private int code = 123;
    private ListView list;
    private Long getID;
    private ImageButton password_add_btn;

    public Password() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_password, container, false);
        list = (ListView) rootView.findViewById(R.id.password_list);
        helper = DBHelper.getInstance(getActivity());

        cursor = helper.getAllPasswords();
        String[] from = new String[] {"website","email"};
        int[] to = new int[] {R.id.website,R.id.email};

        adapter = new PasswordCursorAdapter(getActivity(),R.layout.activity_password,cursor,from,to,0);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        getID = id;

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                        builder.setTitle("Your password is: ");
                        builder.setMessage(helper.getPassword(id).getPassword());

                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deletePassword(getID);
                                adapter.changeCursor(helper.getAllPasswords());
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Copy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("",helper.getPassword(getID).getPassword());
                                clipboard.setPrimaryClip(clip);
                                Toast.makeText(getActivity(), "Password copied!", Toast.LENGTH_LONG).show();
                            }
                        });

                        final String url = helper.getPassword(getID).getWebsite();

                        if (URLUtil.isValidUrl(url)) {
                            builder.setNeutralButton("Go to site", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getActivity(),Password_website.class);
                                    i.putExtra("site",url);
                                    startActivity(i);
                                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                }
                            });
                        }

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
        );

        password_add_btn = (ImageButton)rootView.findViewById(R.id.password_add_btn);
//        password_add_btn.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryLight));
        password_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivityForResult(new Intent(getActivity(),Add_password.class),code);
                Typeface myTface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nawabiat.ttf");
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View textEntryView = factory.inflate(R.layout.activity_add_password, null);
                final EditText name = (EditText) textEntryView.findViewById(R.id.account_name);
                name.setHint("Account Name");
                name.setTypeface(myTface);
                name.setTextSize(30);
                final EditText type = (EditText) textEntryView.findViewById(R.id.account_type);
                type.setHint("Account Type");
                type.setTypeface(myTface);
                type.setTextSize(30);
                final EditText password = (EditText) textEntryView.findViewById(R.id.password);
                password.setHint("Password");
                password.setTypeface(myTface);
                password.setTextSize(30);

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                alert.setTitle("New Password");

                alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String accountType = type.getText().toString();
                        String accountName = name.getText().toString();
                        String pwd = password.getText().toString();
                        PasswordObject po = new PasswordObject(accountType, accountName, pwd);
                        long id = helper.createPassword(po);
                        adapter.changeCursor(helper.getAllPasswords());
                        list.setAdapter(adapter);
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.setView(textEntryView);
                alert.show();
            }
        });

        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == code) {
//            if (resultCode == getActivity().RESULT_OK) {
//                String accountType = data.getStringExtra("account type");
//                String accountName = data.getStringExtra("account name");
//                String password = data.getStringExtra("password");
//
//                PasswordObject po = new PasswordObject(accountType, accountName, password);
//                long id = helper.createPassword(po);
//                po.setId(id);
//                cursor = helper.getAllPasswords();
//
//                String[] from = new String[] {"website","email"};
//                int[] to = new int[] {R.id.website,R.id.email};
//
//                adapter = new PasswordCursorAdapter(getActivity(),R.layout.activity_password,cursor,from,to,0);
//                list.setAdapter(adapter);
//
//            }
//        }
//    }
}
