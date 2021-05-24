package textfont;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * Created by Admin on 12/4/2018.
 */

public class TextViewSemiBold extends androidx.appcompat.widget.AppCompatTextView {

    public TextViewSemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewSemiBold(Context context) {
        super(context);
        init();
    }

    @SuppressLint("WrongConstant")
    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-SemiBold.ttf");
        setTypeface(tf ,1);

    }
}
