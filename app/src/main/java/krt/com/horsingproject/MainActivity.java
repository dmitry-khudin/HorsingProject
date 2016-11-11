package krt.com.horsingproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText1, editText2, editText3, editText4, editText5;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    List<HousingProject> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        adapter = new ArrayAdapter<String>(;
        data  = new ArrayList<>();
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText1.setKeyListener(null);
        editText2.setKeyListener(null);
        editText3.setKeyListener(null);
        editText4.setKeyListener(null);
        editText5.setKeyListener(null);
        spinner = (Spinner) findViewById(R.id.lstHousingProjects) ;

        HousingDownloadListener listener = new HousingDownloadListener() {
            @Override
            public void housingDataDownloaded(List<HousingProject> housingProjects) {
                List<String> list  = new ArrayList<>();
                data = housingProjects;
                for (int i = 0; i < housingProjects.size(); i++)
                {
                    HousingProject project = housingProjects.get(i);
                    list.add(project.toString());
                }
                adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);
            }
        };

        DownloadHousingTask task = new DownloadHousingTask(listener);
        String url = "http://csundergrad.science.uoit.ca/csci3230u/data/Affordable_Housing.csv";
        task.execute(url);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                display(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

   void display(int i)
   {
       HousingProject project = data.get(i);
       editText1.setText(project.getAddress());
       editText2.setText(project.getMunicipality());
       editText3.setText(project.getNumUnits() + "");
       editText4.setText(project.getLatitude() + "");
       editText5.setText(project.getLongitude() + "");
   }
}
