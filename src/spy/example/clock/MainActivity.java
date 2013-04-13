package spy.example.clock;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	@SuppressWarnings("deprecation")
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

	private MyClock myClock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//去掉程序中的标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(FP, FP));
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		myClock = new MyClock(this, "GMT+8:00");
		linearLayout.addView(myClock, new LinearLayout.LayoutParams(WC, WC));

		setContentView(linearLayout);
	}
}
