package pl.rozen.swim.BMICalculator.activities;

import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.rozen.swim.BMICalculator.utils.CountBMIForImperials;
import pl.rozen.swim.BMICalculator.utils.CountBMIForMetrics;
import pl.rozen.swim.BMICalculator.interfaces.ICountBMI;
import pl.rozen.swim.BMICalculator.R;

public class MyActivity extends AppCompatActivity {

//    @BindView(R.id.bmi_toolbar)
//    Toolbar toolbar;

    @BindView(R.id.units_RadioGroup)
    RadioGroup unitsRadioGroup;

    @BindView(R.id.height_EditText)
    EditText heightEditText;
    @BindView(R.id.mass_EditText)
    EditText massEditText;

    @BindView(R.id.height_unit_TextView)
    TextView heightUnitsTextView;
    @BindView(R.id.mass_unit_TextView)
    TextView massUnitsTextView;

    @BindView(R.id.user_bmi_label)
    TextView userBmiLabelTextView;
    @BindView(R.id.result_TextView)
    TextView resultTextView;
    @BindView(R.id.general_description_TextView)
    TextView generalDescriptionTextView;
    @BindView(R.id.detailed_description_TextView)
    TextView detailedDescriptionTextView;


    private Locale locale;

    private static final String ACTIVE_RADIO_BUTTON = "activeRadioButton";
    private static final String HEIGHT_INPUT = "heightInputtedText";
    private static final String MASS_INPUT = "massInputtedText";

    private static final String RESULT_BMI = "bmiResult";

    private static final String PERSIST_STATE_FLAG = "persistStateFlag";

    private static final int DEFAULT_WRONG_RB_ID = -192837912;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        ButterKnife.bind(this);
        restoreSavedInstanceState(savedInstanceState);
        locale = getResources().getConfiguration().getLocales().get(0);
//        setSupportActionBar(toolbar);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreSavedInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        saveInstanceState(outState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstanceState(outState);
    }

    private void saveInstanceState(Bundle outState) {
        outState.putCharSequence(RESULT_BMI, resultTextView.getText());
        outState.putInt(ACTIVE_RADIO_BUTTON, unitsRadioGroup.getCheckedRadioButtonId());
        outState.putString(HEIGHT_INPUT, String.valueOf(heightEditText.getText()));
        outState.putString(MASS_INPUT, String.valueOf(massEditText.getText()));
    }

    private void restoreSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String savedResultBMI = savedInstanceState.getString(RESULT_BMI);
            try {
                float toDisplay = Float.parseFloat(savedResultBMI);
                displayResultBMI(toDisplay);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int checkedRadioButtonID = savedInstanceState.getInt(ACTIVE_RADIO_BUTTON, DEFAULT_WRONG_RB_ID);
            String heightInput = savedInstanceState.getString(HEIGHT_INPUT, null);
            String massInput = savedInstanceState.getString(MASS_INPUT, null);
            restoreUI(heightInput, massInput, checkedRadioButtonID);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        restoreState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        restoreState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        persistState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        persistState();
    }


    private void restoreUI(String heightInput, String massInput, int checkedRadioButtonID) {
        if (checkedRadioButtonID != DEFAULT_WRONG_RB_ID) {
            unitsRadioGroup.check(checkedRadioButtonID);
            if (isMetricUnitsChecked()) {
                heightUnitsTextView.setText(R.string.height_metric_unit);
                massUnitsTextView.setText(R.string.mass_metric_unit);
            } else {
                heightUnitsTextView.setText(R.string.height_imperial_unit);
                massUnitsTextView.setText(R.string.mass_imperial_unit);
            }
        }

        if (heightInput != null)
            heightEditText.setText(heightInput);

        if (massInput != null)
            massEditText.setText(massInput);
    }

    private void restoreState() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean restore = preferences.getBoolean(PERSIST_STATE_FLAG, false);
        if (restore) {
            String heightInput = preferences.getString(HEIGHT_INPUT, null);
            String massInput = preferences.getString(MASS_INPUT, null);
            int checkedRadioButtonID = preferences.getInt(ACTIVE_RADIO_BUTTON, DEFAULT_WRONG_RB_ID);
            restoreUI(heightInput, massInput, checkedRadioButtonID);
        }
    }


    private void persistState() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //TODO: check whether prsist flag is checked
        editor.putInt(ACTIVE_RADIO_BUTTON, unitsRadioGroup.getCheckedRadioButtonId());
        editor.putString(HEIGHT_INPUT, String.valueOf(heightEditText.getText()));
        editor.putString(MASS_INPUT, String.valueOf(massEditText.getText()));
        editor.apply();
    }


    @OnClick(R.id.calc_bmi_Button)
    public void onClickCalculateBMI(View view) {
        String heightS = heightEditText.getText().toString();
        String massS = massEditText.getText().toString();

        ICountBMI bmiCalc;

        if (isMetricUnitsChecked())
            bmiCalc = new CountBMIForMetrics();
        else
            bmiCalc = new CountBMIForImperials();

        float height = -1.0f;
        float mass = -1.0f;
        float toDisplay;

        boolean errorsOccurred = false;

        try {
            height = Float.parseFloat(heightS);
            if (!bmiCalc.isValidHeight(height)) {
                warnWrongHeight();
                errorsOccurred = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            warnWrongHeight();
            errorsOccurred = true;
        }

        try {
            mass = Float.parseFloat(massS);
            if (!bmiCalc.isValidMass(mass)) {
                warnWrongMass();
                errorsOccurred = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            warnWrongMass();
            errorsOccurred = true;
        }

        if (!errorsOccurred) {
            toDisplay = bmiCalc.countBMI(mass, height);
            displayResultBMI(toDisplay);
        } else {
            setResultInvisible();
        }
    }

    private void displayResultBMI(float result) {

        String format = String.format(locale, "%.2f", result);

        if (result < 18.5) {
            resultTextView.setText(format);
            resultTextView.setTextColor(getColor(R.color.underweightBMI));
            generalDescriptionTextView.setText(R.string.underweight_bmi_general_description);
            generalDescriptionTextView.setTextColor(getColor(R.color.underweightBMI));
            detailedDescriptionTextView.setText(R.string.underweight_bmi_detailed_description);
        } else if (18.5 <= result && result <= 25.0) {
            resultTextView.setText(format);
            resultTextView.setTextColor(getColor(R.color.normal_bmi));
            generalDescriptionTextView.setText(R.string.normal_bmi_general_description);
            generalDescriptionTextView.setTextColor(getColor(R.color.normal_bmi));
            detailedDescriptionTextView.setText(R.string.normal_bmi_detailed_description);
        } else if (25.0 < result && result < 30.0) {
            resultTextView.setText(format);
            resultTextView.setTextColor(getColor(R.color.overweightBMI));
            generalDescriptionTextView.setText(R.string.overweight_bmi_general_description);
            generalDescriptionTextView.setTextColor(getColor(R.color.overweightBMI));
            detailedDescriptionTextView.setText(R.string.overweight_bmi_detailed_description);

        } else if (30.0 <= result) {
            resultTextView.setText(format);
            resultTextView.setTextColor(getColor(R.color.obeseBMI));
            generalDescriptionTextView.setText(R.string.obese_bmi_general_description);
            generalDescriptionTextView.setTextColor(getColor(R.color.obeseBMI));
            detailedDescriptionTextView.setText(R.string.obese_bmi_detailed_description);
        }
        setResultVisible();
    }

    @OnClick({R.id.metric_units_RadioButton, R.id.imperial_units_RadioButton})
    public void onRadioButtonClicked(View view) {
        if (isMetricUnitsChecked()) {
            heightUnitsTextView.setText(R.string.height_metric_unit);
            massUnitsTextView.setText(R.string.mass_metric_unit);
        } else {
            heightUnitsTextView.setText(R.string.height_imperial_unit);
            massUnitsTextView.setText(R.string.mass_imperial_unit);
        }
    }

    private void setResultInvisible() {
        userBmiLabelTextView.setVisibility(View.GONE);
        resultTextView.setVisibility(View.GONE);
        generalDescriptionTextView.setVisibility(View.GONE);
        detailedDescriptionTextView.setVisibility(View.GONE);

    }

    private void setResultVisible() {
        userBmiLabelTextView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        generalDescriptionTextView.setVisibility(View.VISIBLE);
        detailedDescriptionTextView.setVisibility(View.VISIBLE);

    }

    private boolean isMetricUnitsChecked() {
        return unitsRadioGroup.getCheckedRadioButtonId() == R.id.metric_units_RadioButton;
    }

    private void warnWrongMass() {
        massEditText.setError(getString(R.string.wrong_mass_err));
        massEditText.setText("");
    }

    private void warnWrongHeight() {
        heightEditText.setError(getString(R.string.wrong_height_err));
        heightEditText.setText("");

    }

}
