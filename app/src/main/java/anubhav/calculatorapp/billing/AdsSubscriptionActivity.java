package anubhav.calculatorapp.billing;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import anubhav.calculatorapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class AdsSubscriptionActivity extends AppCompatActivity implements BillingClass.BillingErrorHandler {


    //Billing class
    private BillingClass billingClass;

    CardView yearlyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_subscription);
        ButterKnife.bind(this);

        yearlyBtn = findViewById(R.id.yearlyBtn);

        billingClass = new BillingClass(this);
        billingClass.setmCallback(this);
        billingClass.startConnection();


        yearlyBtn.setOnClickListener(v -> {
            try {
                    billingClass.purchaseItemByPos(0);
            } catch (Exception e) {
                Toast.makeText(AdsSubscriptionActivity.this, "Item not found!!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }



    @Override
    public void displayErrorMessage(String message) {

    }

    public void detailsClick(View view) {
        intentToUrl("https://willypeng.com/term-of-use/");
    }

    private void intentToUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void onBackClick(View view) {
        finish();
    }
}