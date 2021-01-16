package anubhav.calculatorapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.physphil.android.unitconverterultimate.settings.Preferences;

import anubhav.calculatorapp.history.HistoryActivity;
import anubhav.calculatorapp.R;
import anubhav.calculatorapp.db.DBHelper;
import anubhav.calculatorapp.pref.OpenCloseBracketPref;
import anubhav.calculatorapp.scientific.ExtendedDoubleEvaluator;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommonCalFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public CommonCalFrag() {
        // Required empty public constructor
    }


    public static CommonCalFrag newInstance(String param1, String param2) {
        CommonCalFrag fragment = new CommonCalFrag();
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
    private EditText upperEt, downEt;
    private int count = 0;
    private String expression = "";
    private String text = "";
    private Double result = 0.0;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_common_cal, container, false);
        ButterKnife.bind(this, v);

        upperEt = (EditText) v.findViewById(R.id.editText1);
        downEt = (EditText) v.findViewById(R.id.editText2);
        dbHelper = new DBHelper(getContext());

        return v;
    }

    @OnClick({R.id.clear, R.id.backSpace, R.id.percent, R.id.divide, R.id.ll1, R.id.num7, R.id.num8, R.id.num9, R.id.multiply, R.id.num4, R.id.num5, R.id.num6, R.id.minus, R.id.num1, R.id.num2, R.id.num3, R.id.plus, R.id.openCloseBracket, R.id.num0, R.id.dot, R.id.equal, R.id.square, R.id.posneg, R.id.history, R.id.ll_common})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear:
                upperEt.setText("");
                downEt.setText("");
                count=0;
                expression="";
                break;

            case R.id.backSpace:
                backSpaceTask();
                break;

            case R.id.percent:
                percentClickListener();
                break;

            case R.id.openCloseBracket:
                if (OpenCloseBracketPref.getBracketMode(getContext()) == 0) { //0 means open bracket is not used
                    downEt.setText("("+downEt.getText().toString());
                    OpenCloseBracketPref.putBracketMode(getContext(),1); //1 means open bracket is used, now use close bracket
                }
                else {
                    downEt.setText(downEt.getText().toString()+")");
                    OpenCloseBracketPref.putBracketMode(getContext(),0);
                }
                break;

            case R.id.divide:
                operationClicked("/");
                break;

            case R.id.ll1:
                break;

            case R.id.num7:
                downEt.setText(downEt.getText()+"7");
                break;
            case R.id.num8:
                downEt.setText(downEt.getText()+"8");
                break;
            case R.id.num9:
                downEt.setText(downEt.getText()+"9");
                break;
            case R.id.multiply:
                operationClicked("*");
                break;
            case R.id.num4:
                downEt.setText(downEt.getText()+"4");
                break;
            case R.id.num5:
                downEt.setText(downEt.getText()+"5");
                break;
            case R.id.num6:
                downEt.setText(downEt.getText()+"6");
                break;
            case R.id.minus:
                operationClicked("-");
                break;
            case R.id.num1:
                downEt.setText(downEt.getText()+"1");
                break;
            case R.id.num2:
                downEt.setText(downEt.getText()+"2");
                break;
            case R.id.num3:
                downEt.setText(downEt.getText()+"3");
                break;
            case R.id.plus:
                operationClicked("+");
                break;
            case R.id.num0:
                downEt.setText(downEt.getText()+"0");
                break;
            case R.id.dot:
                if(count==0 && downEt.length()!=0)
                {
                    downEt.setText(downEt.getText()+".");
                    count++;
                }
                break;
            case R.id.equal:
                equalClickListener();
                break;
            case R.id.square:
                if(downEt.length()!=0)
                {
                    text= downEt.getText().toString();
                    downEt.setText("("+text+")^2");
                }
                break;
            case R.id.posneg:
                if(downEt.length()!=0)
                {
                    String s= downEt.getText().toString();
                    char arr[]=s.toCharArray();
                    if(arr[0]=='-')
                        downEt.setText(s.substring(1,s.length()));
                    else
                        downEt.setText("-"+s);
                }
                break;
            case R.id.history:
                Intent i=new Intent(getContext(), HistoryActivity.class);
                i.putExtra("calcName","STANDARD");
                startActivity(i);
                break;
            case R.id.ll_common:
                break;
        }
    }

    private void percentClickListener() {
        if(downEt.length()!=0)
        {
            text = downEt.getText().toString();
            expression= upperEt.getText().toString()+text;


            if ((upperEt.getText().toString() != null) && (upperEt.getText().toString().length() > 0)) {
                String operand = upperEt.getText().toString().substring(upperEt.getText().toString().length() - 1);
                if (operand.equals("+")) {
                    String mainAmount = upperEt.getText().toString().substring(0, upperEt.getText().toString().length() - 1);
                    result = Double.parseDouble(mainAmount) + Double.parseDouble(mainAmount) * (Double.parseDouble(text)/100);
                }
                else if (operand.equals("-")) {
                    String mainAmount = upperEt.getText().toString().substring(0, upperEt.getText().toString().length() - 1);
                    result = Double.parseDouble(mainAmount) - Double.parseDouble(mainAmount) * (Double.parseDouble(text)/100);
                }
                else if (operand.equals("*")) {
                    String mainAmount = upperEt.getText().toString().substring(0, upperEt.getText().toString().length() - 1);
                    result = Double.parseDouble(mainAmount) * Double.parseDouble(mainAmount) * (Double.parseDouble(text)/100);
                }
                else if (operand.equals("/")) {
                    String mainAmount = upperEt.getText().toString().substring(0, upperEt.getText().toString().length() - 1);
                    result = Double.parseDouble(mainAmount) / Double.parseDouble(mainAmount) * (Double.parseDouble(text)/100);
                }
                else {
                    result = Double.parseDouble(text)/100;
                }
            }
            else {
                result = Double.parseDouble(text)/100;
            }

        }

        try
        {
            upperEt.setText(expression+"%");
            downEt.setText(result+"");
            if(expression.length()==0)
                expression="0.0";
        }
        catch (Exception e)
        {
            downEt.setText("Invalid Expression");
            upperEt.setText("");
            expression="";
            e.printStackTrace();
        }
    }

    private void equalClickListener() {
        if(downEt.length()!=0)
        {
            text= downEt.getText().toString();
            expression= upperEt.getText().toString()+text;
        }
        upperEt.setText(expression);
        if(expression.length()==0)
            expression="0.0";
        try
        {
            //evaluate the expression
            result=new ExtendedDoubleEvaluator().evaluate(expression);
            //insert expression and result in sqlite database if expression is valid and not 0.0
            if(!expression.equals("0.0"))
                dbHelper.insert("STANDARD",expression+" = "+result);
            downEt.setText(String.format("%."+ Preferences.getNumberDecimals() +"f", result));
        }
        catch (Exception e)
        {
            downEt.setText("Invalid Expression");
            upperEt.setText("");
            expression="";
            e.printStackTrace();
        }
    }

    private void backSpaceTask() {
        text= downEt.getText().toString();
        if(text.length()>0)
        {
            if(text.endsWith("."))
            {
                count=0;
            }
            String newText=text.substring(0,text.length()-1);
            //to delete the data contained in the brackets at once
            if(text.endsWith(")"))
            {
                char []a=text.toCharArray();
                int pos=a.length-2;
                int counter=1;
                //to find the opening bracket position
                for(int i=a.length-2;i>=0;i--)
                {
                    if(a[i]==')')
                    {
                        counter++;
                    }
                    else if(a[i]=='(')
                    {
                        counter--;
                    }
                    //if decimal is deleted b/w brackets then count should be zero
                    else if(a[i]=='.')
                    {
                        count=0;
                    }
                    //if opening bracket pair for the last bracket is found
                    if(counter==0)
                    {
                        pos=i;
                        break;
                    }
                }
                newText=text.substring(0,pos);
            }
            //if e2 edit text contains only - sign or sqrt at last then clear the edit text e2
            if(newText.equals("-")||newText.endsWith("sqrt"))
            {
                newText="";
            }
            //if pow sign is left at the last
            else if(newText.endsWith("^"))
                newText=newText.substring(0,newText.length()-1);

            downEt.setText(newText);
        }
    }

    private void operationClicked(String op)
    {
        if(downEt.length()!=0)
        {
            String text= downEt.getText().toString();
            upperEt.setText(upperEt.getText() + text+op);
            downEt.setText("");
            count=0;
        }
        else
        {
            String text= upperEt.getText().toString();
            if(text.length()>0)
            {
                String newText=text.substring(0,text.length()-1)+op;
                upperEt.setText(newText);
            }
        }
    }
}