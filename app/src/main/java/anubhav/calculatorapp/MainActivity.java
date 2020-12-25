package anubhav.calculatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setFragment(new CommonCalFrag());


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

    @OnClick({R.id.commonMode, R.id.sciMode, R.id.history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commonMode:
                sciMode.setTextColor(getResources().getColor(R.color.white));
                sciLine.setVisibility(View.GONE);
                commonMode.setTextColor(getResources().getColor(R.color.red_light));
                commonLine.setVisibility(View.VISIBLE);
                setFragment(new CommonCalFrag());
                break;
            case R.id.sciMode:
                commonMode.setTextColor(getResources().getColor(R.color.white));
                commonLine.setVisibility(View.GONE);
                sciMode.setTextColor(getResources().getColor(R.color.red_light));
                sciLine.setVisibility(View.VISIBLE);
                setFragment(new ScientificCalFrag());
                break;
            case R.id.history:
                if (sciLine.getVisibility() == View.VISIBLE) {
                    Intent intent = new Intent(this, History.class);
                    intent.putExtra("calcName", "SCIENTIFIC");
                    startActivity(intent);
                }
                else {
                    Intent i=new Intent(this,History.class);
                    i.putExtra("calcName","STANDARD");
                    startActivity(i);
                }

                break;
        }
    }
}
