package anubhav.calculatorapp.purchase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import anubhav.calculatorapp.Config;
import anubhav.calculatorapp.Methods;
import anubhav.calculatorapp.R;
import anubhav.calculatorapp.main.MainActivity;
import anubhav.calculatorapp.pref.AdLockPref;

public class AdPurchaseActivity extends AppCompatActivity {
    private RewardedAd rewardedAd;
    RewardedAdLoadCallback adLoadCallback = null;

    public static int adLeaveCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_subscription);

        rewardedAd = new RewardedAd(this,
                getString(R.string.rewarded_ad_id));
    }


    private void loadRewardedAd(ProgressDialog pd) {
        Activity activityContext = AdPurchaseActivity.this;
        RewardedAdCallback adCallback = new RewardedAdCallback() {
            @Override
            public void onRewardedAdOpened() {
                // Ad opened.
            }

            @Override
            public void onRewardedAdClosed() {
                // Ad closed.
                if (adLeaveCheck != 2){
                    Toast.makeText(AdPurchaseActivity.this, "Please watch the full video to get this reward!", Toast.LENGTH_LONG).show();

                } else {
                    adLeaveCheck=0;
                    AdLockPref.putAdLockState(AdPurchaseActivity.this, Methods.getCurrentDate(), true);
                    Config.isAdEnabled = false;
                    startActivity(new Intent(AdPurchaseActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onUserEarnedReward(@NonNull RewardItem reward) {
                // User earned reward.
                adLeaveCheck=2;
            }

            @Override
            public void onRewardedAdFailedToShow(AdError adError) {
                // Ad failed to display.
            }

        };

        adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
                pd.dismiss();
                adLeaveCheck = 1;
                rewardedAd.show(activityContext, adCallback);


            }


            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }


    public void tryAdFreeBtnClick(View view) {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Please Wait");
        pd.setMessage("Ad is loading...");
        pd.show();
        loadRewardedAd(pd);





    }

    public void onBackClick(View view) {
        finish();
    }
}