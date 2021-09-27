package kr.loplab.gnss05.net;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kr.loplab.gnss05.R;
import kr.loplab.gnss05.apn.ApnActivity;
import kr.loplab.gnss05.common.L;
import kr.loplab.gnss05.source.SourceListActivity;


/**
 * Mobile station built-in network
 * 
 * @author wangjun
 * 
 */
public class RoverNetActivity extends AppCompatActivity {

	private EditText mEtIP;
	private EditText mEtPort;
	private EditText mEtUserName;
	private EditText mEtPassWord;
	private TextView mTvSourcePoint;

	private NetCorsController mController = new NetCorsController();

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.llSourceList:
				onSourceList();
				break;
			case R.id.btnConnect:
				// Log in to Cors
				onLogin();
				break;
			case R.id.btnDisConnect:
				// disconnect
				onLogOut();
				break;
			default:
				break;
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rover_net);
		initView();
		updateUi();
	}

	private void updateUi() {
		DiffDataInfo diffDataInfo = DiffDataManager.getInstance()
				.getDiffDataInfo();
		mEtIP.setText(diffDataInfo.getIp());
		mEtPort.setText(String.valueOf(diffDataInfo.getPort()));
		mEtUserName.setText(diffDataInfo.getUserName());
		mEtPassWord.setText(diffDataInfo.getPassWord());
		mTvSourcePoint.setText(diffDataInfo.getSourcePoint());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rover_net, menu);
		return true;
	}
	//APN으로 이동
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_apn) {
			Intent intent = new Intent(this, ApnActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initView() {
		findViewById(R.id.llSourceList).setOnClickListener(mClickListener);
		findViewById(R.id.btnConnect).setOnClickListener(mClickListener);
		findViewById(R.id.btnDisConnect).setOnClickListener(mClickListener);
		mEtIP = (EditText) findViewById(R.id.etIP);
		mEtPort = (EditText) findViewById(R.id.etPort);
		mEtUserName = (EditText) findViewById(R.id.etUserName);
		mEtPassWord = (EditText) findViewById(R.id.etPassWord);
		mTvSourcePoint = (TextView) findViewById(R.id.tvCurSourcePoint);
	}

	private boolean checkStatus() {
		if (!mController.isGnssConnect()) {
			Toast.makeText(this, "No Receiver Select！", Toast.LENGTH_LONG).show();
			return false;
		}
		if (!ipAndPortEnable()) {
			return false;
		}
		if (TextUtils.isEmpty(mTvSourcePoint.getText())) {
			Toast.makeText(this, "Please Choose Source Table！", Toast.LENGTH_LONG).show();
			return false;
		}
		if (mEtUserName.getText().length() == 0) {
			Toast.makeText(this, "Please Input the userName！", Toast.LENGTH_LONG).show();
			return false;
		}
		if (mEtPassWord.getText().length() == 0) {
			Toast.makeText(this, "Please Input the Password！", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	private boolean ipAndPortEnable() {
		if (mEtIP.getText().length() == 0) {
			Toast.makeText(this, "Please Input IP！", Toast.LENGTH_LONG).show();
			return false;
		}
		if (mEtPort.getText().length() == 0) {
			Toast.makeText(this, "Please Input Port！", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	private DiffDataInfo getDataFromUi() {
		int port;
		try {
			port = Integer.parseInt(mEtPort.getText().toString());
		} catch (Exception e) {
			port = -1;
			L.printException(e);
		}
		DiffDataInfo diffDataInfo = new DiffDataInfo();
		diffDataInfo.setIp(mEtIP.getText().toString());
		diffDataInfo.setPort(port);
		diffDataInfo.setSourcePoint(mTvSourcePoint.getText().toString());
		diffDataInfo.setUserName(mEtUserName.getText().toString());
		diffDataInfo.setPassWord(mEtPassWord.getText().toString());
		return diffDataInfo;
	}

	private void onSourceList() {
		if (!ipAndPortEnable()) {
			return;
		}
		int port;
		try {
			port = Integer.parseInt(mEtPort.getText().toString());
		} catch (Exception e) {
			port = -1;
			L.d(this.toString() + "Port String conversion failed!");
			L.printException(e);
		}
		Intent intent = new Intent(this, SourceListActivity.class);
		intent.putExtra(SourceListActivity.IP, mEtIP.getText().toString());
		intent.putExtra(SourceListActivity.PORT, port);
		startActivityForResult(intent, 1);
	}

	private void onLogin() {
		if (!checkStatus()) {
			return;
		}
		DiffDataInfo diffDataInfo = getDataFromUi();
		DiffDataManager.getInstance().setDiffDataInfo(diffDataInfo);
		mController.login(diffDataInfo, this);
	}

	private void onLogOut() {
		if (!mController.isGnssConnect()) {
			Toast.makeText(this, "No Receiver Has Connected！", Toast.LENGTH_LONG).show();
			return;
		}
		mController.loginOut();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case 1:
			String souce = data.getStringExtra(SourceListActivity.SOURCE_LIST);
			if (TextUtils.isEmpty(souce)) {
				return;
			}
			mTvSourcePoint.setText(souce);
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
