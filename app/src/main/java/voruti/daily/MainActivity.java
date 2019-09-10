package voruti.daily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import alt.android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {


    TextView tvTop;
    TextView tvBottom;
    ImageView ivPause;

    boolean paused;

    CountDownTimer cdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        tvTop = findViewById(R.id.tvTop);
        tvBottom = findViewById(R.id.tvBottom);

        ivPause = findViewById(R.id.ivPause);


        cdt = new CountDownTimer(60000, 200) {

            public void onTick(long millisUntilFinished) {
                SpannableString content = new SpannableString(Integer.toString((int) Math.ceil(millisUntilFinished / 1000.0)));
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

                tvTop.setText(content);
                tvBottom.setText(content);
            }

            public void onFinish() {
                SpannableString content = new SpannableString("0");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

                tvTop.setText(content);
                tvBottom.setText(content);
            }

        };


        SpannableString content = new SpannableString("60");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        tvTop.setText(content);
        tvBottom.setText(content);


        tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdt.start();
                paused = false;
                ivPause.setVisibility(View.INVISIBLE);
            }
        });

        tvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paused) {
                    cdt.resume();
                    paused = false;
                    ivPause.setVisibility(View.INVISIBLE);
                } else {
                    cdt.pause();
                    paused = true;
                    ivPause.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
