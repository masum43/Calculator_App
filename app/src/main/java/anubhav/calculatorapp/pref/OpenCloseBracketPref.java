package anubhav.calculatorapp.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class OpenCloseBracketPref {
    public static void putBracketMode(Context context, int value) //value = signin
    {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("BracketMode", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putInt("mode", value);
            myEdit.apply();
        }
        catch (Exception e)
        {
            Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public static int getBracketMode(Context context)
    {
        SharedPreferences sh = context.getSharedPreferences("BracketMode", MODE_PRIVATE);
        int s1 = sh.getInt("mode", 0);
        return s1;
    }
}
