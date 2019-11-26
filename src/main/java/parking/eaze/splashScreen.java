//parkingEazeTeam

package parking.eaze;


        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.PorterDuff;
        import android.os.Bundle;
        import android.os.Handler;

        import android.widget.ProgressBar;

        import androidx.appcompat.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);



        //animRotate.setAnimationListener(this);

        Handler handlerr = new Handler();
        handlerr.postDelayed(new Runnable() {
            @Override
            public void run() {


                new Thread(new Runnable()
                {
                    public void run()
                    {
                        while (progressStatus < 100)
                        {
                            progressStatus += 5;
                            // Update the progress bar and display the
                            //current value in the text view
                            handler.post(new Runnable()
                            {
                                public void run()
                                {
                                    progressBar.setProgress(progressStatus);

                                }
                            });
                            try
                            {
                                // Sleep for 200 milliseconds.
                                //Just to display the progress slowly
                                Thread.sleep(150);
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();


            }


        }, 1000);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(splashScreen.this,chooseLocation.class);
                startActivity(i);
            }
        }, 3000);
    }
}