package com.shoniz.saledistributemobility.programmer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.data.fileSystem.FileManager;
import com.shoniz.saledistributemobility.utility.data.pref.AppPref;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;

;


public class ProgrammerActivity extends AppCompatActivity {
    private Spinner spinner;
    Context context;

    private View.OnClickListener deleteAllDatabasesClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FileManager.deleteFolderContent(context, DBHelper.getDatabasePath(context));
        }
    };

    private View.OnClickListener deleteSharedprefClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            AppPref.removeAllSharedpref(context);
        }
    };

    private View.OnClickListener deleteDatabaseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String dbName = spinner.getSelectedItem().toString();
            FileManager.deleteDatabase(context, dbName);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                context = this;
        View v = getLayoutInflater().inflate(R.layout.activity_programmer, null);
        setContentView(v);
        v.findViewById(R.id.btnDeleteAllDatabases).setOnClickListener(deleteAllDatabasesClickListener);
        v.findViewById(R.id.btnDeleteSharedPref).setOnClickListener(deleteSharedprefClickListener);
        v.findViewById(R.id.btnDeleteDatabase).setOnClickListener(deleteDatabaseClickListener);

         List<String> spinnerArray = new ArrayList();
         spinnerArray.add("base");
         spinnerArray.add("customer");
         spinnerArray.add("sale");
         spinnerArray.add("user");
         spinnerArray.add("CustomerBase");
         spinnerArray.add("CustomerCredit");
         spinnerArray.add("CustomerBuy");
         spinnerArray.add("CustomerCheque");
         spinnerArray.add("path");
         spinnerArray.add("Order");
         spinnerArray.add("CardIndex");
         spinnerArray.add("customerBuy");
         spinnerArray.add("Location");

        ArrayAdapter adapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.spnDetabases);
        spinner.setAdapter(adapter);


    }
}
