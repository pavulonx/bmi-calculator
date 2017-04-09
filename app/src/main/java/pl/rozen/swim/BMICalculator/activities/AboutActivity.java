package pl.rozen.swim.BMICalculator.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.rozen.swim.BMICalculator.BuildConfig;
import pl.rozen.swim.BMICalculator.R;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.about_face)
    ImageView face;

    @BindView(R.id.app_version)
    TextView appVersion;
    private Intent shareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        appVersion.setText(getString(R.string.app_version_about, BuildConfig.VERSION_NAME));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        MenuItem share = menu.findItem(R.id.about_menu_item_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(share);
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SHARE SUBJECT");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_application_info));

        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about_menu_item_share) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_application_info));
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.about_face)
    public void onFaceClick(View v) {
        showToast(R.string.hello_face);

    }


    @OnClick({R.id.about_git_site_link, R.id.about_app_author, R.id.about_face})
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
