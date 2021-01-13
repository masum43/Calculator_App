package anubhav.calculatorapp.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class AdLockPref {
    public static void putAdLockState(Context context, String key, boolean value) //value = signin
    {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("AdLockPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean(key, value);
            myEdit.apply();
        }
        catch (Exception e)
        {
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean getAdLockState(Context context, String key)
    {
        SharedPreferences sh = context.getSharedPreferences("AdLockPref", MODE_PRIVATE);
        boolean s1 = sh.getBoolean(key, false);
        return s1;
    }
}
