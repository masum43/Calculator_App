package anubhav.calculatorapp.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.physphil.android.unitconverterultimate.Constant;
import com.physphil.android.unitconverterultimate.fragments.ConversionFragment;
import com.physphil.android.unitconverterultimate.models.Conversion;
import com.physphil.android.unitconverterultimate.settings.PreferencesActivity;

import anubhav.calculatorapp.Config;
import anubhav.calculatorapp.Methods;
import anubhav.calculatorapp.R;
import anubhav.calculatorapp.billing.AdsSubscriptionActivity;
import anubhav.calculatorapp.billing.BillingClass;
import anubhav.calculatorapp.billing.SubscriptionConfig;
import anubhav.calculatorapp.pref.AdLockPref;
import anubhav.calculatorapp.unit.UnitConverterFrag;
import anubhav.calculatorapp.common.CommonCalFrag;
import anubhav.calculatorapp.history.HistoryActivity;
import anubhav.calculatorapp.scientific.ScientificCalFrag;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BillingClass.BillingErrorHandler{

    @BindView(R.id.commonMode)
    TextView commonMode;
    @BindView(R.id.sciMode)
    TextView sciMode;
    @BindView(R.id.history)
    ImageView history;
    @BindView(R.id.commonLine)
    View commonLine;
    @BindView(R.id.sciLine)
    View sciLine;
    @BindView(R.id.unitMode)
    TextView unitMode;
    @BindView(R.id.unitLine)
    View unitLine;

    private FrameLayout adContainerView;
    private AdView adView;

    public static InterstitialAd mInterstitialAd;

    private BillingClass billingClass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        newSubsInit();
        setFragment(new CommonCalFrag());

        inflateNavDrawer();


    }

    private void newSubsInit() {
        billingClass = new BillingClass(this);
        billingClass.setmCallback(this);

        billingClass.startConnection();
    }

    private boolean setFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @OnClick({R.id.commonMode, R.id.sciMode, R.id.unitMode, R.id.history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commonMode:
                sciMode.setTextColor(getResources().getColor(R.color.white));
                sciLine.setVisibility(View.GONE);
                unitMode.setTextColor(getResources().getColor(R.color.white));
                unitLine.setVisibility(View.GONE);
                history.setVisibility(View.VISIBLE);

                commonMode.setTextColor(getResources().getColor(R.color.red_light));
                commonLine.setVisibility(View.VISIBLE);
                setFragment(new CommonCalFrag());
                break;
            case R.id.sciMode:
                if (Config.isAdEnabled)
                    showInterstitial();
                commonMode.setTextColor(getResources().getColor(R.color.white));
                commonLine.setVisibility(View.GONE);
                unitMode.setTextColor(getResources().getColor(R.color.white));
                unitLine.setVisibility(View.GONE);
                history.setVisibility(View.VISIBLE);

                sciMode.setTextColor(getResources().getColor(R.color.red_light));
                sciLine.setVisibility(View.VISIBLE);
                setFragment(new ScientificCalFrag());
                break;
            case R.id.unitMode:
                sciMode.setTextColor(getResources().getColor(R.color.white));
                sciLine.setVisibility(View.GONE);
                commonMode.setTextColor(getResources().getColor(R.color.white));
                commonLine.setVisibility(View.GONE);
                history.setVisibility(View.GONE);

                unitMode.setTextColor(getResources().getColor(R.color.red_light));
                unitLine.setVisibility(View.VISIBLE);
                setFragment(new UnitConverterFrag());

                break;
            case R.id.history:
                if (sciLine.getVisibility() == View.VISIBLE) {
                    Intent intent = new Intent(this, HistoryActivity.class);
                    intent.putExtra("calcName", "SCIENTIFIC");
                    startActivity(intent);
                } else {
                    Intent i = new Intent(this, HistoryActivity.class);
                    i.putExtra("calcName", "STANDARD");
                    startActivity(i);
                }

                break;
        }
    }

    private void inflateNavDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.drawer_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        //toolbar.setNavigationIcon(getDrawable(this, R.drawable.ic_baseline_search_24));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //header
        View headerview = navigationView.getHeaderView(0);
        LinearLayout menuSetting = headerview.findViewById(R.id.menu_settings);
        LinearLayout menuRating = headerview.findViewById(R.id.menu_rating);
        LinearLayout menuRemoveAds = headerview.findViewById(R.id.menu_remove_ads);
        menuSetting.setOnClickListener(v -> PreferencesActivity.start(MainActivity.this));
        menuRating.setOnClickListener(v -> rateThisApp());
        menuRemoveAds.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AdsSubscriptionActivity.class)));
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.common:
                setFragment(new CommonCalFrag());
                break;

            case R.id.scientific:
                setFragment(new ScientificCalFrag());
                break;

            case R.id.currency_converter:
                Constant.globalConversionId = Conversion.CURRENCY;
                setFragment(new ConversionFragment());
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void rateThisApp() {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
    }

    private void inflateAdmobAdaptiveBanner() {
        adContainerView = findViewById(R.id.ad_view_container);
        // Step 1 - Create an AdView and set the ad unit ID on it.
        adView = new AdView(this);
        adView.setAdUnitId(getString(R.string.adaptive_banner_ad_unit_id));
        adContainerView.addView(adView);
        loadBanner();
    }

    private void loadBanner() {
        // Create an ad request. Check your logcat output for the hashed device ID
        // to get test ads on a physical device, e.g.,
        // "Use AdRequest.Builder.addTestDevice("ABCDE0123") to get test ads on this
        // device."
        AdRequest adRequest =
                new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();

        AdSize adSize = getAdSize();
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);


        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    public void loadAdmobInters(){

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_inters)); //<string name="admob_inters">ca-app-pub-3940256099942544/1033173712</string>
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("error_ad", loadAdError.getMessage());
            }
            @Override
            public void onAdClosed()
            {
                //reload interstitial
                AdRequest adRequest = new AdRequest.Builder()
//                        .addTestDevice("YOUR_DEVICE_ID")
                        .build();
                mInterstitialAd.loadAd(adRequest);
            }
        });

    }

    public static void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void displayErrorMessage(String message) {
        if (message.equals("done")) {

            SubscriptionConfig.isIAPConnected = true;

            if (billingClass.isSubscribed(SubscriptionConfig.remove_ads_one_year)) {
                SubscriptionConfig.ads_subscription = true;
            }
            else {
                if (AdLockPref.getAdLockState(this, Methods.getCurrentDate())) //if true
                    Config.isAdEnabled = false;
                if (Config.isAdEnabled) {
                    MobileAds.initialize(this, initializationStatus -> {});
                    inflateAdmobAdaptiveBanner();
                    loadAdmobInters();
                }
            }


            //check for subscription
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            finish();
        } else if (message.equals("error")) {

            SubscriptionConfig.isIAPConnected = false;

//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            finish();
        }
    }

}
