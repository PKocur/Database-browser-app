package pl.pk99.databasebrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.pk99.databasebrowser.data.CatsData;
import pl.pk99.databasebrowser.data.CatsDataManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CatsDataManager catsDataManager;
    Button btnAddCat;
    Button btnShowCats;

    TextFieldValidator etxtName;
    TextFieldValidator etxtBreed;
    TextFieldValidator etxtBirth;
    RadioButton rbtnMale;
    RadioButton rbtnFemale;
    CheckBox cboxMicrochipped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catsDataManager = new CatsDataManager(getApplicationContext());
        catsDataManager.insertSampleData();

        etxtName = new TextFieldValidatorNotEmpty((EditText) findViewById(R.id.etxtName));
        etxtBreed = new TextFieldValidatorNotEmpty((EditText) findViewById(R.id.etxtBreed));
        etxtBirth = new TextFieldValidatorDate((EditText) findViewById(R.id.etxtBirth));
        InputDateTextFormatter.format(etxtBirth.getEditText());

        rbtnMale = (RadioButton) findViewById(R.id.rbtnMale);
        rbtnFemale = (RadioButton) findViewById(R.id.rbtnFemale);

        cboxMicrochipped = (CheckBox) findViewById(R.id.cboxMicrochipped);

        btnAddCat = (Button) findViewById(R.id.addCatButton);
        btnShowCats = (Button) findViewById(R.id.showCatsButton);

        btnAddCat.setOnClickListener(this);
        btnShowCats.setOnClickListener(this);
    }

    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.showCatsButton:
                Intent intent = new Intent(this, ShowCatsActivity.class);
                startActivity(intent);
                //catsDataManager.showData();
                break;

            case R.id.addCatButton:
                if(validateFields(etxtName, etxtBreed, etxtBirth)) {
                    String gender = rbtnMale.isChecked() ? "Male" : "Female";
                    String microchipped  = cboxMicrochipped.isChecked() ? "Yes" : "No";

                    catsDataManager.addCat(etxtName.getEditText().getText().toString(),
                            etxtBreed.getEditText().getText().toString(),
                            etxtBirth.getEditText().getText().toString(), gender, microchipped);

                    Toast.makeText(getApplicationContext(), "Dodałem kota!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Nie dodałem kota!",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    boolean validateFields(TextFieldValidator... textFieldValidators) {
        boolean value = true;
        for (TextFieldValidator textFieldValidator : textFieldValidators) {
            if(textFieldValidator.validate()) {
                textFieldValidator.getEditText().setBackgroundTintList
                        (getApplicationContext().getColorStateList(R.color.colorPrimaryDark));
            } else {
                textFieldValidator.getEditText().setBackgroundTintList
                        (getApplicationContext().getColorStateList(R.color.colorRed));
                value = false;
            }
        }
        return value;
    }

}
