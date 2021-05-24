package textfont;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class EditTextViewRegular extends androidx.appcompat.widget.AppCompatEditText {

public EditTextViewRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        }

public EditTextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        }

public EditTextViewRegular(Context context) {
        super(context);
        init();
        }

@SuppressLint("WrongConstant")
public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Regular.ttf");
        setTypeface(tf ,1);

        }
        }
