package com.eternal.kidzero.ui.helpers;

import android.widget.EditText;

import com.eternal.kidzero.R;
import com.eternal.kidzero.ui.fragments.BaseFrag;

public class TextFormatUtil {

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

    public static String msToStr(long Milisseconds) {
        int h = (int) ((Milisseconds / 1000) / 3600);
        int m = (int) (((Milisseconds / 1000) / 60) % 60);
        int s = (int) ((Milisseconds / 1000) % 60);

        if (s < 0) {
            return null;
        }
        if (h == 0) {
            return m + "m";
        }
        if (m == 0) {
            return h + "h";
        }

        return h + "h " + m + "m";
    }
}
