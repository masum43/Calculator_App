package anubhav.calculatorapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScientificCal extends AppCompatActivity {

    private EditText eSci1, eSci2;
    private int countSci =0;
    private String expressionSci ="";
    private String textSci ="";
    private Double resultSci =0.0;
    private DBHelper dbHelperSci;
    private Button mode,toggle,square,xpowy,log,sin,cos,tan,sqrt,fact;
    private int toggleModeSci =1;
    private int angleMode=1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific_cal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eSci1 = (EditText) findViewById(R.id.editText);
        eSci2 = (EditText) findViewById(R.id.editText2);
        mode = (Button) findViewById(R.id.degMode);
        toggle = (Button) findViewById(R.id.toggleSci);
        square = (Button) findViewById(R.id.square);
        xpowy = (Button) findViewById(R.id.xpowy);
        log = (Button) findViewById(R.id.log);
        sin = (Button) findViewById(R.id.sin);
        cos = (Button) findViewById(R.id.cos);
        tan = (Button) findViewById(R.id.tan);
        sqrt= (Button) findViewById(R.id.sqrt);
        fact = (Button) findViewById(R.id.factorial);

        dbHelperSci =new DBHelper(this);

        eSci2.setText("0");

        //tags to change the mode from degree to radian and vice versa
        mode.setTag(1);
        //tags to change the names of the buttons performing different operations
        toggle.setTag(1);


    }

    public void onClick(View v)
    {
        toggleModeSci =(int)toggle.getTag();
        angleMode=((int)mode.getTag());
        switch (v.getId()) {
            case R.id.toggleSci:
                //change the button text if switch button is clicked
                if(toggleModeSci ==1)
                {
                    toggle.setTag(2);
                    square.setText(R.string.cube);
                    xpowy.setText(R.string.tenpow);
                    log.setText(R.string.naturalLog);
                    sin.setText(R.string.sininv);
                    cos.setText(R.string.cosinv);
                    tan.setText(R.string.taninv);
                    sqrt.setText(R.string.cuberoot);
                    fact.setText(R.string.Mod);
                }
                else if(toggleModeSci ==2)
                {
                    toggle.setTag(3);
                    square.setText(R.string.square);
                    xpowy.setText(R.string.epown);
                    log.setText(R.string.log);
                    sin.setText(R.string.hyperbolicSine);
                    cos.setText(R.string.hyperbolicCosine);
                    tan.setText(R.string.hyperbolicTan);
                    sqrt.setText(R.string.inverse);
                    fact.setText(R.string.factorial);
                }
                else if(toggleModeSci ==3)
                {
                    toggle.setTag(1);
                    sin.setText(R.string.sin);
                    cos.setText(R.string.cos);
                    tan.setText(R.string.tan);
                    sqrt.setText(R.string.sqrt);
                    xpowy.setText(R.string.xpown);
                }
                break;

            case R.id.degMode:
                //change the angle property for trignometric operations if mode button is clicked
                if(angleMode==1)
                {
                    mode.setTag(2);
                    mode.setText(R.string.mode2);
                }
                else
                {
                    mode.setTag(1);
                    mode.setText(R.string.mode1);
                }
                break;

            case R.id.num0:
                eSci2.setText(eSci2.getText() + "0");
                break;

            case R.id.num1:
                eSci2.setText(eSci2.getText() + "1");
                break;

            case R.id.num2:
                eSci2.setText(eSci2.getText() + "2");
                break;

            case R.id.num3:
                eSci2.setText(eSci2.getText() + "3");
                break;


            case R.id.num4:
                eSci2.setText(eSci2.getText() + "4");
                break;

            case R.id.num5:
                eSci2.setText(eSci2.getText() + "5");
                break;

            case R.id.num6:
                eSci2.setText(eSci2.getText() + "6");
                break;

            case R.id.num7:
                eSci2.setText(eSci2.getText() + "7");
                break;

            case R.id.num8:
                eSci2.setText(eSci2.getText() + "8");
                break;

            case R.id.num9:
                eSci2.setText(eSci2.getText() + "9");
                break;

            case R.id.pi:
                eSci2.setText(eSci2.getText() + "pi");
                break;

            case R.id.dot:
                if (countSci == 0 && eSci2.length() != 0) {
                    eSci2.setText(eSci2.getText() + ".");
                    countSci++;
                }
                break;

            case R.id.clear:
                eSci1.setText("");
                eSci2.setText("");
                countSci = 0;
                expressionSci = "";
                break;

            case R.id.backSpace:
                textSci = eSci2.getText().toString();
                if(textSci.length()>0)
                {
                    if(textSci.endsWith("."))
                    {
                        countSci =0;
                    }
                    String newText= textSci.substring(0, textSci.length()-1);
                    //to delete the data contained in the brackets at once
                    if(textSci.endsWith(")"))
                    {
                        char []a= textSci.toCharArray();
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
                                countSci =0;
                            }
                            //if opening bracket pair for the last bracket is found
                            if(counter==0)
                            {
                                pos=i;
                                break;
                            }
                        }
                        newText= textSci.substring(0,pos);
                    }
                    //if e2 edit text contains only - sign or sqrt or any other text functions
                    // at last then clear the edit text e2
                    if(newText.equals("-")||newText.endsWith("sqrt")||newText.endsWith("log")||newText.endsWith("ln")
                            ||newText.endsWith("sin")||newText.endsWith("asin")||newText.endsWith("asind")||newText.endsWith("sinh")
                            ||newText.endsWith("cos")||newText.endsWith("acos")||newText.endsWith("acosd")||newText.endsWith("cosh")
                            ||newText.endsWith("tan")||newText.endsWith("atan")||newText.endsWith("atand")||newText.endsWith("tanh")
                            ||newText.endsWith("cbrt"))
                    {
                        newText="";
                    }
                    //if pow sign is left at the last or divide sign
                    else if(newText.endsWith("^")||newText.endsWith("/"))
                        newText=newText.substring(0,newText.length()-1);
                    else if(newText.endsWith("pi")||newText.endsWith("e^"))
                        newText=newText.substring(0,newText.length()-2);
                    eSci2.setText(newText);
                }
                break;

            case R.id.plus:
                operationClicked("+");
                break;

            case R.id.minus:
                operationClicked("-");
                break;

            case R.id.divide:
                operationClicked("/");
                break;

            case R.id.multiply:
                operationClicked("*");
                break;

            case R.id.sqrt:
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    toggleModeSci =(int)toggle.getTag();
                    if(toggleModeSci ==1)
                        eSci2.setText("sqrt(" + textSci + ")");
                    else if(toggleModeSci ==2)
                        eSci2.setText("cbrt(" + textSci + ")");
                    else
                        eSci2.setText("1/(" + textSci + ")");
                }
                break;

            case R.id.square:
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    if(toggleModeSci ==2)
                        eSci2.setText("(" + textSci + ")^3");
                    else
                        eSci2.setText("(" + textSci + ")^2");
                }
                break;

            case R.id.xpowy:
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    if(toggleModeSci ==1)
                        eSci2.setText("(" + textSci + ")^");
                    else if(toggleModeSci ==2)
                        eSci2.setText("10^(" + textSci + ")");
                    else
                        eSci2.setText("e^(" + textSci + ")");
                }
                break;

            case R.id.log:
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    if(toggleModeSci ==2)
                        eSci2.setText("ln(" + textSci + ")");
                    else
                        eSci2.setText("log(" + textSci + ")");
                }
                break;

            case R.id.factorial:
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    if(toggleModeSci ==2)
                    {
                        eSci1.setText("(" + textSci + ")%");
                        eSci2.setText("");
                    }
                    else
                    {
                        String res="";
                        try
                        {
                            CalculateFactorial cf=new CalculateFactorial();
                            int []arr=cf.factorial((int)Double.parseDouble(String.valueOf(new ExtendedDoubleEvaluator().evaluate(textSci))));
                            int res_size=cf.getRes();
                            if(res_size>20)
                            {
                                for (int i=res_size-1; i>=res_size-20; i--)
                                {
                                    if(i==res_size-2)
                                        res+=".";
                                    res+=arr[i];
                                }
                                res+="E"+(res_size-1);
                            }
                            else
                            {
                                for (int i=res_size-1; i>=0; i--)
                                {
                                    res+=arr[i];
                                }
                            }
                            eSci2.setText(res);
                        }
                        catch (Exception e)
                        {
                            if(e.toString().contains("ArrayIndexOutOfBoundsException"))
                            {
                                eSci2.setText("Result too big!");
                            }
                            else
                                eSci2.setText("Invalid!!");
                            e.printStackTrace();
                        }
                    }
                }
                break;

            case R.id.sin:
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    if(angleMode==1)
                    {
                        double angle=Math.toRadians(new ExtendedDoubleEvaluator().evaluate(textSci));
                        if(toggleModeSci ==1)
                            eSci2.setText("sin(" + angle + ")");
                        else if(toggleModeSci ==2)
                            eSci2.setText("asind(" + textSci + ")");
                        else
                            eSci2.setText("sinh(" + textSci + ")");
                    }
                    else
                    {
                        if(toggleModeSci ==1)
                            eSci2.setText("sin(" + textSci + ")");
                        else if(toggleModeSci ==2)
                            eSci2.setText("asin(" + textSci + ")");
                        else
                            eSci2.setText("sinh(" + textSci + ")");
                    }
                }
                break;

            case R.id.cos:
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    if(angleMode==1)
                    {
                        double angle=Math.toRadians(new ExtendedDoubleEvaluator().evaluate(textSci));
                        if(toggleModeSci ==1)
                            eSci2.setText("cos(" + angle + ")");
                        else if(toggleModeSci ==2)
                            eSci2.setText("acosd(" + textSci + ")");
                        else
                            eSci2.setText("cosh(" + textSci + ")");
                    }
                    else
                    {
                        if(toggleModeSci ==1)
                            eSci2.setText("cos(" + textSci + ")");
                        else if(toggleModeSci ==2)
                            eSci2.setText("acos(" + textSci + ")");
                        else
                            eSci2.setText("cosh(" + textSci + ")");
                    }
                }
                break;

            case R.id.tan:
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    if(angleMode==1)
                    {
                        double angle=Math.toRadians(new ExtendedDoubleEvaluator().evaluate(textSci));
                        if(toggleModeSci ==1)
                            eSci2.setText("tan(" + angle + ")");
                        else if(toggleModeSci ==2)
                            eSci2.setText("atand(" + textSci + ")");
                        else
                            eSci2.setText("tanh(" + textSci + ")");
                    }
                    else
                    {
                        if(toggleModeSci ==1)
                            eSci2.setText("tan(" + textSci + ")");
                        else if(toggleModeSci ==2)
                            eSci2.setText("atan(" + textSci + ")");
                        else
                            eSci2.setText("tanh(" + textSci + ")");
                    }
                }
                break;

            case R.id.posneg:
                if (eSci2.length() != 0) {
                    String s = eSci2.getText().toString();
                    char arr[] = s.toCharArray();
                    if (arr[0] == '-')
                        eSci2.setText(s.substring(1, s.length()));
                    else
                        eSci2.setText("-" + s);
                }
                break;

            case R.id.equal:
                /*for more knowledge on DoubleEvaluator and its tutorial go to the below link
                http://javaluator.sourceforge.net/en/home/*/
                if (eSci2.length() != 0) {
                    textSci = eSci2.getText().toString();
                    expressionSci = eSci1.getText().toString() + textSci;
                }
                eSci1.setText("");
                if (expressionSci.length() == 0)
                    expressionSci = "0.0";
                try {
                    //evaluate the expression
                    resultSci = new ExtendedDoubleEvaluator().evaluate(expressionSci);
                    //insert expression and result in sqlite database if expression is valid and not 0.0
                    if (String.valueOf(resultSci).equals("6.123233995736766E-17"))
                    {
                        resultSci =0.0;
                        eSci2.setText(resultSci + "");
                    }
                    else if(String.valueOf(resultSci).equals("1.633123935319537E16"))
                        eSci2.setText("infinity");
                    else
                        eSci2.setText(resultSci + "");
                    if (!expressionSci.equals("0.0"))
                        dbHelperSci.insert("SCIENTIFIC", expressionSci + " = " + resultSci);
                } catch (Exception e) {
                    eSci2.setText("Invalid Expression");
                    eSci1.setText("");
                    expressionSci = "";
                    e.printStackTrace();
                }
                break;

            case R.id.openBracket:
                eSci1.setText(eSci1.getText() + "(");
                break;

            case R.id.openCloseBracket:
                if(eSci2.length()!=0)
                    eSci1.setText(eSci1.getText() + eSci2.getText().toString()+ ")");
                else
                    eSci1.setText(eSci1.getText() + ")");
                break;

            case R.id.history:
                Intent i = new Intent(this, History.class);
                i.putExtra("calcName", "SCIENTIFIC");
                startActivity(i);
                break;

        }
    }

    private void operationClicked(String op) {
        if (eSci2.length() != 0) {
            String text = eSci2.getText().toString();
            eSci1.setText(eSci1.getText() + text + op);
            eSci2.setText("");
            countSci = 0;
        } else {
            String text = eSci1.getText().toString();
            if (text.length() > 0) {
                String newText = text.substring(0, text.length() - 1) + op;
                eSci1.setText(newText);
            }
        }
    }
}