package anubhav.calculatorapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.physphil.android.unitconverterultimate.Constant;
import com.physphil.android.unitconverterultimate.fragments.ConversionFragment;
import com.physphil.android.unitconverterultimate.models.Conversion;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnitConverterFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.ll_cooking)
    LinearLayout llCooking;
    @BindView(R.id.ll_currency)
    LinearLayout llCurrency;
    @BindView(R.id.ll_storage)
    LinearLayout llStorage;
    @BindView(R.id.ll_energy)
    LinearLayout llEnergy;
    @BindView(R.id.ll_fuel)
    LinearLayout llFuel;
    @BindView(R.id.ll_length)
    LinearLayout llLength;
    @BindView(R.id.ll_mass)
    LinearLayout llMass;
    @BindView(R.id.ll_power)
    LinearLayout llPower;
    @BindView(R.id.ll_pressure)
    LinearLayout llPressure;
    @BindView(R.id.ll_speed)
    LinearLayout llSpeed;
    @BindView(R.id.ll_temperature)
    LinearLayout llTemperature;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.ll_torque)
    LinearLayout llTorque;
    @BindView(R.id.ll_volume)
    LinearLayout llVolume;


    public UnitConverterFrag() {
        // Required empty public constructor
    }

    public static UnitConverterFrag newInstance(String param1, String param2) {
        UnitConverterFrag fragment = new UnitConverterFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_unit_converter, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

//    @OnClick(R.id.ll_area)
//    public void onViewClicked() {
//        Constant.globalConversionId = 0;
//        goToOtherFragment(getContext(), new ConversionFragment());
//    }

    public static boolean goToOtherFragment(Context c, Fragment fragment) {

        if (fragment != null) {

            ((FragmentActivity) c).getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public static boolean goToOtherFragWithBundle(Context c, Fragment fragment, HashMap<String, Integer> hashMap) {

        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("hashmap", hashMap);
            fragment.setArguments(bundle);
            ((FragmentActivity) c).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @OnClick({R.id.ll_area, R.id.ll_cooking, R.id.ll_currency, R.id.ll_storage, R.id.ll_energy, R.id.ll_fuel, R.id.ll_length, R.id.ll_mass, R.id.ll_power, R.id.ll_pressure, R.id.ll_speed, R.id.ll_temperature, R.id.ll_time, R.id.ll_torque, R.id.ll_volume})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_area:
                Constant.globalConversionId = Conversion.AREA;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_cooking:
                Constant.globalConversionId = Conversion.COOKING;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_currency:
                Constant.globalConversionId = Conversion.CURRENCY;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_storage:
                Constant.globalConversionId = Conversion.STORAGE;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_energy:
                Constant.globalConversionId = Conversion.ENERGY;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_fuel:
                Constant.globalConversionId = Conversion.FUEL;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_length:
                Constant.globalConversionId = Conversion.LENGTH;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_mass:
                Constant.globalConversionId = Conversion.MASS;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_power:
                Constant.globalConversionId = Conversion.POWER;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_pressure:
                Constant.globalConversionId = Conversion.PRESSURE;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_speed:
                Constant.globalConversionId = Conversion.SPEED;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_temperature:
                Constant.globalConversionId = Conversion.TEMPERATURE;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_time:
                Constant.globalConversionId = Conversion.TIME;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_torque:
                Constant.globalConversionId = Conversion.TORQUE;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
            case R.id.ll_volume:
                Constant.globalConversionId = Conversion.VOLUME;
                goToOtherFragment(getContext(), new ConversionFragment());
                break;
        }
    }
}