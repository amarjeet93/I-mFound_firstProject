package com.e.found.Utils


import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources.NotFoundException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AnyRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object AppUtils {
    fun hidekeyBoard(context: Context, eText: EditText) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(eText.windowToken, 0)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window: Window = activity.window
//            val background = activity.getDrawable(R.drawable.loginback)
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.setStatusBarColor(activity.resources.getColor(R.color.transparent))
//            window.setNavigationBarColor(activity.resources.getColor(R.color.transparent))
//            window.setBackgroundDrawable(background)
        }
    }


    fun isUsernameValid(username: String?): Boolean {
        var isUsernameValid = true
        if (!isEmailValid(username)) {
            if (!isPhoneNumber(username)) {
                isUsernameValid = false
            }
        }
        return isUsernameValid
    }

    fun checkIfEmailOrPhoneNumber(username: String?): Boolean {
        var isUsernameValid = false
        if (!isEmailValid(username)) {
            if (isPhoneNumber(username)) {
                isUsernameValid = true
            }
        }
        return isUsernameValid
    }

    /*validate email*/
    fun validateEmail(
        view: View?,
        context: Context,
        mStrEmail: String
    ): Boolean {
        if (!isValidEmail(mStrEmail)) {

            return false
        }
        return true
    }

    fun isEmailValid(email: String?): Boolean {
        var isValid = false
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val inputStr: CharSequence? = email
        val pattern =
            Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    fun isPhoneNumber(phoneNumber: String?): Boolean {
        var isValid = false

        isValid = try {
            TextUtils.isDigitsOnly(phoneNumber)
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
        return isValid
    }

    fun isOnlyNumberEntered(enterValue: String): Boolean {
        var isValid = false
        val expression = "[0-9]+"
        val inputStr: CharSequence = enterValue
        val pattern =
            Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(inputStr)
        if (matcher.matches()) {
            isValid = true
        }
        return isValid
    }

    fun showToast(ctx: Context?, message: String?) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show()
    }

    fun isLocationEnabled(context: Context): Boolean {
        var locationMode = 0
        val locationProviders: String
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.LOCATION_MODE
                )
            } catch (e: SettingNotFoundException) {
                e.printStackTrace()
            }
            locationMode != Settings.Secure.LOCATION_MODE_OFF
        } else {
            locationProviders = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED
            )
            !TextUtils.isEmpty(locationProviders)
        }
    }
//
//    fun isNetworkConnected(context: Context): Boolean {
//        var haveConnectedWifi = false
//        var haveConnectedMobile = false
//        val cm =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeInfo =null //cm.activeNetworkInfo
//        if (activeInfo != null && activeInfo.isConnectedOrConnecting) {
//            haveConnectedWifi = activeInfo.type == ConnectivityManager.TYPE_WIFI
//            haveConnectedMobile = activeInfo.type == ConnectivityManager.TYPE_MOBILE
//            if (haveConnectedWifi) {
//                haveConnectedWifi = true
//            } else if (haveConnectedMobile) {
//                haveConnectedMobile = true
//            }
//        } else {
//            haveConnectedWifi = false
//            haveConnectedMobile = false
//        }
//        return haveConnectedWifi || haveConnectedMobile
//    }

    fun doubleDigit(digit: String): String {
        var digit = digit
        if (digit.length == 1) {
            digit = "0$digit"
            return digit
        }
        return digit
    }

    fun getUserCountry(context: Context): String {
        return try {
            val tm =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            tm.simCountryIso
        } catch (e: Exception) {
            "in"
        }
    }

    @Throws(NotFoundException::class)
    fun getUriToResource(context: Context, @AnyRes resId: Int): Uri {
        /** Return a Resources instance for your application's package.  */
        val res = context.resources

        /** return uri  */
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
        )
    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection =
                url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    var scrDensityValue = ""
    fun screenDensity(ctx: Context): String {
        when (ctx.resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW ->                 // ...
                scrDensityValue = "ldpi"
            DisplayMetrics.DENSITY_MEDIUM ->                 // ...
                scrDensityValue = "mdpi"
            DisplayMetrics.DENSITY_HIGH ->                 // ...
                scrDensityValue = "hdpi"
            DisplayMetrics.DENSITY_XHIGH ->                 // ...
                scrDensityValue = "xhdpi"
            DisplayMetrics.DENSITY_XXHIGH ->                 // ...
                scrDensityValue = "xxhdpi"
            DisplayMetrics.DENSITY_XXXHIGH ->                 // ...
                scrDensityValue = "xxxhdpi"
        }
        return scrDensityValue
    }

    //email validation
    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    //valid phone
    fun isValidMobile(phone: String?): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

//    fun isNetworkAvailable(context: Context): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo
//            .isConnected
//    }

    //snackbar
    fun showSnackbar(
        ctx: Context?,
        view: View,
        str: String?,
        cv: Int
    ) {
        var view = view
        val snack = Snackbar.make(view, str!!, Snackbar.LENGTH_LONG)
        view = snack.view
        view.setBackgroundColor(cv)
        val tv =
            view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        tv.setTextColor(Color.WHITE)
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        view.layoutParams = params
        snack.show()
    }

    fun hideKeyboard(ctx: Context) {
        val inputManager = ctx
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val v = (ctx as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun isSimSupport(context: Context): Boolean {
        val tm =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager //gets the current TelephonyManager
        return tm.simState != TelephonyManager.SIM_STATE_ABSENT
    }
/*get country code dynamic from telephone manager services*/
//    fun getCountryCodeFromSim(context: Context): String {
//        var countryCode: String = "1"
//
//        try {
//            if (isSimSupport(context)) {
//                countryCode = getCountryCodeFromTelephonyManager(context)
//            } else {
//                countryCode = "1"
//            }
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            countryCode = "1"
//        }
//
//
//        return countryCode
//    }
  /**
     * Get ISO 3166-1 alpha-2 country code for this device (or null if not available)
     *
     * @param context Context reference to get the TelephonyManager instance from
     * @return country code or null
     */
    fun getUserCountryName(context: Context): String? {
        try {
            val tm =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountry = tm.simCountryIso
            if (simCountry != null && simCountry.length == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US)
            } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                val networkCountry = tm.networkCountryIso
                if (networkCountry != null && networkCountry.length == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US)
                }
            }
        } catch (e: Exception) {
        }
        return null
    }

    fun convertUtCTimeToLocal(messageDate: String): Long {
        if (messageDate.contains(" ")) {


            //   SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy kk:mm:ss");
            val df =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            df.timeZone = TimeZone.getTimeZone("UTC")
            // df.setTimeZone(tz);
            var date: Date? = null
            try {
                date = df.parse(messageDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date!!.time
        }
        return java.lang.Long.valueOf(messageDate)
    }

    fun getColor(context: Context, id: Int): Int {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getColor(context, id)
        } else {
            context.resources.getColor(id)
        }
    }


    private fun getCurrentTime(ctx: Context): Long {
        var currentTime: Long = 0
        currentTime = System.currentTimeMillis()
        return currentTime
    }

    @JvmStatic
    fun hideKeyboardBackFragment(ctx: Context) {
        try {
            val inputManager = ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // check if no view has focus:
            val v = (ctx as Activity).currentFocus ?: return
            inputManager.hideSoftInputFromWindow(v.windowToken, 0)
        } catch (ex: Exception) {
            println("Keyboard hide error")
            ex.printStackTrace()
        }
    }

    fun pxToDp(ctx: Context, px: Int): Int {
        val displayMetrics = ctx.resources.displayMetrics
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun dpToPx(ctx: Context, dp: Int): Int {
        val displayMetrics = ctx.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 180).toInt()
    }

    fun getDeviceUniqueID(activity: Activity): String {
        var device_unique_id = "android123"
        try {
            device_unique_id = Settings.Secure.getString(
                activity.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return device_unique_id
    }

    fun checkPhoneOrTablet(ctx: Activity): Int {
        var istablet = 0
        val metrics = DisplayMetrics()
        // DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        ctx.windowManager.defaultDisplay.getMetrics(metrics)
        val yInches = metrics.heightPixels / metrics.ydpi
        val xInches = metrics.widthPixels / metrics.xdpi
        val diagonalInches =
            Math.sqrt(xInches * xInches + yInches * yInches.toDouble())
        istablet = if (diagonalInches >= 6.5) {
            // 6.5inch device or bigger
            1
        } else {
            // smaller device
            0
        }
        return istablet
    }

    private fun isTabletDevice(activityContext: Context): Boolean {
        val device_large =
            activityContext.resources.configuration.screenLayout and
                    Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        val metrics = DisplayMetrics()
        val activity = activityContext as Activity
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        if (device_large) {
            //Tablet
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_TV) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_HIGH) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_280) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_400) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_XXHIGH) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_560) {
                return true
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_XXXHIGH) {
                return true
            }
        } else {
            //Mobile
        }
        return false
    }
/*String name validation only accept alphabets and spaces*/
    fun validateLetters(txt: String?): Boolean {
        val regx = "^[a-zA-Z\\s]+$"
        val pattern =
            Pattern.compile(regx, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(txt)
        return matcher.find()
    }

/*password Condition*/
    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,16}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    internal class RecyclerTouchListener(
        context: Context?,
        recycleView: RecyclerView,
        private val clicklistener: ClickListener?
    ) :
        OnItemTouchListener {
        private val gestureDetector: GestureDetector
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

        init {
            gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recycleView.findChildViewUnder(e.x, e.y)
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child))
                    }
                }
            })
        }
    }
    interface ClickListener {
        fun onClick(view: View?, position: Int)
        fun onLongClick(view: View?, position: Int)
    }

}


