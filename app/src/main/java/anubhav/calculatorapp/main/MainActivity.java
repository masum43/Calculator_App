package anubhav.calculatorapp.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.physphil.android.unitconverterultimate.Constant;
import com.physphil.android.unitconverterultimate.api.models.Currency;
import com.physphil.android.unitconverterultimate.fragments.ConversionFragment;
import com.physphil.android.unitconverterultimate.models.Conversion;
import com.physphil.android.unitconverterultimate.settings.PreferencesActivity;

import anubhav.calculatorapp.R;
import anubhav.calculatorapp.unit.UnitConverterFrag;
import anubhav.calculatorapp.common.CommonCalFrag;
import anubhav.calculatorapp.history.HistoryActivity;
import anubhav.calculatorapp.scientific.ScientificCalFrag;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setFragment(new CommonCalFrag());

        inflateNavDrawer();


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
}
