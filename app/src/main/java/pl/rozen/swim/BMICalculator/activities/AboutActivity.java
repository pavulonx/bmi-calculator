package pl.rozen.swim.BMICalculator.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import pl.rozen.swim.BMICalculator.R;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.about_face)
    ImageView face;

    @BindView(R.id.about_background)
    ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    public void onFaceClick(View v) {
        showToast(R.string.hello_face);
    }

    public void onGitClick(View view) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_site)));
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            showToast(R.string.share_error);
        }
    }

    private void showToast(int toShow) {
        Toast.makeText(this, getString(toShow), Toast.LENGTH_SHORT).show();
    }


}
