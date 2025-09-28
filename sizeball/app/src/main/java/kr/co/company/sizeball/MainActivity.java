package kr.co.company.sizeball;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button size, pause;
    MySurfaceView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        size = (Button) findViewById(R.id.size);
        pause = (Button) findViewById(R.id.pause);

        view = findViewById(R.id.surfaceView);  // 🔹 SurfaceView 연결

        size.setOnClickListener(v -> {
            view.growAllBalls(true);  // 🔹 버튼 클릭 시 모든 공이 커지기 시작
        });

        pause.setOnClickListener(v -> {
            view.growAllBalls(false);  // 🔹 버튼 클릭 시 모든 공이 커지기 시작
        });
    }

    protected void onPause()
    {
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }
}