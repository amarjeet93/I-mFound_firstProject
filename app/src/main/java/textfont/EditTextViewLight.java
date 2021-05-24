package textfont;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class EditTextViewLight extends androidx.appcompat.widget.AppCompatEditText {

    public EditTextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextViewLight( Context context) {
        super(context);
        init();
    }
    @SuppressLint("WrongConstant")
    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Light.ttf");
        setTypeface(tf ,1);

    }
}

