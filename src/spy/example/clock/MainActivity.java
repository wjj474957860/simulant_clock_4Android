package spy.example.clock;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@SuppressWarnings("deprecation")
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

	private MyClock myClock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(FP, FP));
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		myClock = new MyClock(this, "GMT+8:00");
		linearLayout.addView(myClock, new LinearLayout.LayoutParams(WC, WC));

		setContentView(linearLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class MyClock extends View {
		@SuppressWarnings("deprecation")
		public MyClock(Context context, String sTime_Zone) {
			super(context);
			setsTimeZoneString(sTime_Zone);

			BmBody = BitmapFactory.decodeResource(getResources(),
					R.drawable.clock_body);
			BmdBody = new BitmapDrawable(BmBody);

			BmHour = BitmapFactory.decodeResource(getResources(),
					R.drawable.hour);
			BmdHour = new BitmapDrawable(BmHour);

			BmMinute = BitmapFactory.decodeResource(getResources(),
					R.drawable.minute);
			BmdMinute = new BitmapDrawable(BmMinute);

			BmSecond = BitmapFactory.decodeResource(getResources(),
					R.drawable.second);
			BmdSecond = new BitmapDrawable(BmSecond);

			width = BmBody.getWidth();
			height = BmBody.getHeight();

			centerX = availableWidth / 2;
			centerY = availableHeight / 2;

			paint = new Paint();
			paint.setColor(Color.BLUE);
			run();

		}

		public void run() {
			tickHandler = new Handler();
			tickHandler.post(tickRunnable);
		}

		private Runnable tickRunnable = new Runnable() {
			public void run() {
				postInvalidate();
				tickHandler.postDelayed(tickRunnable, 1000);
			}
		};

		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			Calendar calendar = Calendar.getInstance(TimeZone
					.getTimeZone(sTimeZoneString));
			int hour = calendar.get(Calendar.HOUR);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);
			float hourRotate = hour * 30.0f + minute / 60.0f * 30.0f;
			float minuteRotate = minute * 6.0f;
			float secondRotate = second * 6.0f;

			boolean scaled = false;

			if (availableWidth < width || availableHeight < height) {
				scaled = true;
				float scale = Math.min((float) availableWidth / (float) width,
						(float) availableHeight / (float) height);
				canvas.save();
				canvas.scale(scale, scale, centerX, centerY);
			}
			BmdBody.setBounds(centerX - width / 2, centerY - height / 2,
					centerX + width / 2, centerY + height / 2);
			BmdBody.draw(canvas);

			tempWidth = BmdHour.getIntrinsicWidth();
			tempHeight = BmdHour.getIntrinsicHeight();
			canvas.save();
			canvas.rotate(hourRotate, centerX, centerY);
			BmdHour.setBounds(centerX - tempWidth / 2,
					centerY - tempHeight / 2, centerX + tempWidth / 2, centerY
							+ tempHeight / 2);
			BmdHour.draw(canvas);
			canvas.restore();

			tempWidth = BmdMinute.getIntrinsicWidth();
			tempHeight = BmdMinute.getIntrinsicHeight();
			canvas.save();
			canvas.rotate(minuteRotate, centerX, centerY);
			BmdMinute.setBounds(centerX - tempWidth / 2, centerY - tempHeight
					/ 2, centerX + tempWidth / 2, centerY + tempHeight / 2);
			BmdMinute.draw(canvas);
			canvas.restore();

			tempWidth = BmdSecond.getIntrinsicWidth();
			tempHeight = BmdSecond.getIntrinsicHeight();
			canvas.save();
			canvas.rotate(secondRotate, centerX, centerY);
			BmdSecond.setBounds(centerX - tempWidth / 2, centerY - tempHeight
					/ 2, centerX + tempWidth / 2, centerY + tempHeight / 2);
			BmdSecond.draw(canvas);

			if (scaled) {
				canvas.restore();
			}

		}

		public String getsTimeZoneString() {
			return sTimeZoneString;
		}

		public void setsTimeZoneString(String sTimeZoneString) {
			this.sTimeZoneString = sTimeZoneString;
		}

		Bitmap BmBody;
		Bitmap BmHour;
		Bitmap BmMinute;
		Bitmap BmSecond;

		BitmapDrawable BmdBody;
		BitmapDrawable BmdHour;
		BitmapDrawable BmdMinute;
		BitmapDrawable BmdSecond;

		Paint paint;

		Handler tickHandler;

		int width;
		int height;
		int tempWidth;
		int tempHeight;
		int centerX;
		int centerY;

		int availableWidth = 500;
		int availableHeight = 600;

		private String sTimeZoneString;

	}

}
