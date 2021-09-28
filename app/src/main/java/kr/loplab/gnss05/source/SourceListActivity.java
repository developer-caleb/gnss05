package kr.loplab.gnss05.source;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import kr.loplab.gnss05.R;
import kr.loplab.gnss05.common.DialogUtils;
import kr.loplab.gnss05.receiver.ReceiverService;
import kr.loplab.gnss05.receiver.asw.GetSourceListEventArgs;
import kr.loplab.gnss05.receiver.sourcelist.GetSourceFromReceiver;

/**
 * Source list
 * 
 * @author wangjun
 * 
 */
public class SourceListActivity extends AppCompatActivity {
	String TAG = this.getClass().getSimpleName();
	public static String IP = "Ip";
	public static String PORT = "Port";

	/**
	 * source list
	 */
	public static final String SOURCE_LIST = "SOURCE_LIST";

	private String mIp = "";
	private int mPort = -1;

	private ListView mLvSourceList;

	private Dialog mDialog;

	private Runnable mDelayCancelRunnable = new Runnable() {
		@Override
		public void run() {
			if (mDialog != null && mDialog.isShowing()) {
				cancel();
				Toast.makeText(SourceListActivity.this, "Failed to get the source list！",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sourcelist);
		initView();
		updateSourceList();
		initData();
	}

	private void initData() {
		mIp = getIntent().getStringExtra(IP);
		mPort = getIntent().getIntExtra(PORT, -1);
		Log.d(TAG, "initData: ip: " + mIp);
		Log.d(TAG, "initData: port: " + mPort);
		// TODO Auto-generated method stub

	}

	private void initView() {
		mLvSourceList = (ListView) findViewById(R.id.lvSourceList);
		mLvSourceList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent();
				String source = (String) mLvSourceList.getAdapter().getItem(position);
				it.putExtra(SOURCE_LIST, source);
				setResult(Activity.RESULT_OK, it);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.source_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_obtain) {
			obtainSourceList();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//리시버로 받고나서 업데이트
	private void updateSourceList() {
		List<String> data = GetSourceFromReceiver.getInstance().getSourceList();
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_expandable_list_item_1, data);
		mLvSourceList.setAdapter(arrayAdapter);
	}

	private void obtainSourceList() {
		//소스리스트 받기 dialog 시작
		if (TextUtils.isEmpty(mIp) || mPort <= 0) {
			Toast.makeText(SourceListActivity.this, "Failed To Get the IP and Address！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		mDialog = DialogUtils.showProgressDialog(this, R.string.msg_initing);
		int delaytime =15; //원래 15
		//delaytime 이후에 cancel 시키기
		mLvSourceList.postDelayed(mDelayCancelRunnable, delaytime * 1000);
		GetSourceFromReceiver.getInstance().loadSourceList(mIp, mPort);
	}

	private void cancel() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(GetSourceListEventArgs.class.getName());
		registerReceiver(mReceiver, filter);
		Log.d(TAG, "onResume: 리시버 등록");
	}

	@Override
	public void onStop() {
		super.onStop();
		unregisterReceiver(mReceiver);
		Log.d(TAG, "onStop: 리시버 등록 해제");
	}

	private MyReceiver mReceiver = new MyReceiver();

	//리시버로 성공한 내역 받기,
	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(GetSourceListEventArgs.class.getName())) {
				Log.d(TAG, "onReceive: 받음");
				Serializable serializable = intent.getSerializableExtra(ReceiverService.RECEIVER_DATA);
				if (serializable instanceof GetSourceListEventArgs) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							mLvSourceList.removeCallbacks(mDelayCancelRunnable);
							cancel();
							Toast.makeText(SourceListActivity.this, "Source Table Get Success！",
									Toast.LENGTH_SHORT).show();
							updateSourceList();
						}
					});
				}
			}
			
		}
	}
}
