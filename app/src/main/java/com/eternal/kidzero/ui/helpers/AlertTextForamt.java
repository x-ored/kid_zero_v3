package com.eternal.kidzero.ui.helpers;

import android.widget.EditText;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class AlertTextForamt {

    public static boolean inputIsEmpty(BaseFrag baseFrag, EditText editText) {

        if (editText.getText().toString().isEmpty()) {
            baseFrag.showAlertDialog(
                    baseFrag.getString(R.string.inp)
                            + " '" + editText.getHint() + "' " + baseFrag.getString(R.string.is_empty)
            );
            return true;
        }
        else {
            return false;
        }
    }
}
