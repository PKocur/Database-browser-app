package pl.pk99.databasebrowser;

import android.util.Log;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

class TextFieldValidatorNotEmpty extends TextFieldValidator {

    TextFieldValidatorNotEmpty (EditText text) {
        super(text);
    }

    @Override
    boolean validate () {
        return getEditText().getText().length() != 0;
    }
}
