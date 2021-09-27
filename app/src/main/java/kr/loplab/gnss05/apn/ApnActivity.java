package kr.loplab.gnss05.apn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.huace.gnssserver.gnss.data.receiver.EnumModemCommunicationMode;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;
import com.huace.gnssserver.gnss.data.receiver.ModemDialParams;

import java.util.ArrayList;
import java.util.List;

import kr.loplab.gnss05.R;
import kr.loplab.gnss05.common.L;
import kr.loplab.gnss05.receiver.ReceiverCmdProxy;
import kr.loplab.gnss05.receiver.ReceiverService;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateModemCommunicationModeEventArgs;
import kr.loplab.gnss05.receiver.cmd.GetCmdUpdateModemDialParamsEventArgs;
import kr.loplab.gnss05.receiver.cmd.ReceiverCmdEventArgs;
import kr.loplab.gnss05.receiver.entity.ReceiverAsw;

public class ApnActivity extends AppCompatActivity {

	EditText mEtApnAccessPt;
	EditText mEtServerNum;
	EditText mEtDialUserName;
	EditText mEtDialPswd;

	ModemDialParams mData = new ModemDialParams();

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnGet:
				// connect wifi to the receiver
				onGet();
				break;
			case R.id.btnSet:
				// break the wifi connection
				onSet();
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apn);
		initView();
	}

	private void initView() {
		mEtApnAccessPt = (EditText) findViewById(R.id.etApnAccessPt);
		mEtServerNum = (EditText) findViewById(R.id.etServerNum);
		mEtDialUserName = (EditText) findViewById(R.id.etDialUserName);
		mEtDialPswd = (EditText) findViewById(R.id.etDialPswd);
		findViewById(R.id.btnGet).setOnClickListener(mClickListener);
		findViewById(R.id.btnSet).setOnClickListener(mClickListener);
	}

	private void onSet() {
		try {
			if (isInputValid()) {
				mData.strApn = mEtApnAccessPt.getText().toString();
				mData.strDialNum = mEtServerNum.getText().toString();
				mData.strDialName = mEtDialUserName.getText().toString();
				mData.strDialPswd = mEtDialPswd.getText().toString();
				accept(mData);
			}
		} catch (Exception e) {
			L.printException(e);
		}
	}

	private boolean isInputValid() {
		if (mEtApnAccessPt.getText().length() == 0) {
			Toast.makeText(this, R.string.msg_please_input_apn_access_pt,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mEtServerNum.getText().length() == 0) {
			Toast.makeText(this, R.string.msg_please_input_server_num,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	public boolean accept(ModemDialParams apn) {
		GetCmdUpdateModemCommunicationModeEventArgs arg = new GetCmdUpdateModemCommunicationModeEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_MODEM_COMMUNICATION_MODE,
				EnumModemCommunicationMode.MODEM_COMMUNICATION_MODE_GPRS);
		ReceiverCmdProxy.BUS.post(arg);
		ReceiverCmdProxy.BUS.post(new GetCmdUpdateModemDialParamsEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_SET_MODEM_DIAL_PARAM, apn));
		return true;
	}

	private void onGet() {
		ReceiverCmdProxy.BUS.post(new ReceiverCmdEventArgs(
				EnumReceiverCmd.RECEIVER_CMD_GET_MODEM_DIAL_PARAM));
	}

	@Override
	public void onResume() {
		super.onResume();
		List<EnumReceiverCmd> cmds = new ArrayList<>();
		cmds.add(EnumReceiverCmd.RECEIVER_ASW_GET_MODEM_DIAL_PARAM);
		registerReceiver(mReceiver,
				ReceiverService.createReceiverAswIntentFilter(cmds));
	}

	@Override
	public void onStop() {
		super.onStop();
		unregisterReceiver(mReceiver);
	}

	private MyReceiver mReceiver = new MyReceiver();

	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			if (action.equals(EnumReceiverCmd.RECEIVER_ASW_GET_MODEM_DIAL_PARAM
					.name())) {
				final ReceiverAsw asw = ReceiverService
						.getBroadcastData(intent);
				if (asw == null) {
					return;
				}
				switch (asw.getReceiverCmdType()) {
				case RECEIVER_ASW_GET_MODEM_DIAL_PARAM:
					if (asw.getParcelable() instanceof ModemDialParams) {
						updateParam((ModemDialParams) asw.getParcelable());
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private void updateParam(final ModemDialParams apn) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					if (apn != null) {
						mEtApnAccessPt.setText(apn.strApn);
						mEtServerNum.setText(apn.strDialNum);
						mEtDialUserName.setText(apn.strDialName);
						mEtDialPswd.setText(apn.strDialPswd);
					}
				} catch (Exception e) {
					L.printException(e);
				}
			}
		});
	}

}
