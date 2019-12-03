package today.news.com;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
//引导页面
public class SplashActivity extends AppCompatActivity {

    private FullScreenVideoView mVideoView;
    private TextView mTvTimer;
    private CustomCountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mVideoView = findViewById(R.id.vv_paly);
        mTvTimer = findViewById(R.id.tv_splash_timer);
        mTvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //一般较大文件都存放在 res目录下的 Raw 和 assets 文件夹中
        //VideoView ： 对 MediaPlayer 的一层封装，只支持 3gp 和 mp4 格式的播放。
        //videoview 的全屏问题： videoview 会根据视频文件的大小来改变自身的大小，所以要自定义 view。
        //Android 中 View 和 ViewGroup 是组合设计模式， 23 种设计模式之一。
        mVideoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+ File.separator + R.raw.splash));

        //设置一个准备监听
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        //设置一个完成监听
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });

        timer = new CustomCountDownTimer(5,new CustomCountDownTimer.ICountDownHandler() {
            @Override
            public void onTicker(int time) {
                mTvTimer.setText(time+"秒");
            }
            @Override
            public void onFinish() {
                mTvTimer.setText("跳过");
            }
        });
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
