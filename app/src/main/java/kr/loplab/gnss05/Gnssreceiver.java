package kr.loplab.gnss05;

import android.content.res.Resources;
import android.net.Uri;

import com.chc.gnss.sdk.*;

import java.io.InputStream;


public class Gnssreceiver {

  // CHC_Receiver = null;
   // CHC_Receiver receiver = new CHC_Receiver();

    //Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + id);
    String filepath = ""; // The absolute path of the 'features.hcc'
    CHC_RECEIVER_TYPE chc_receiver_type = CHC_RECEIVER_TYPE.CHC_RECEIVER_TYPE_SMART_GNSS;
    CHC_OEM_TYPE oem_type = CHC_OEM_TYPE.CHC_OEM_TYPE_AUTO;

    //config에 있는 파일 넣어놓고, resource로 부르기.
    CHC_ReceiverRef receiveref = new CHC_ReceiverRef(filepath , chc_receiver_type, oem_type);


    void aklslkdj(){
        CHC_CONNECTION_METHOD method = CHC_CONNECTION_METHOD.CHC_CONNECTION_METHOD_BT;
        CHC_Receiver.CHCUpdateConnectionMethod(receiveref, method);
        CHC_CMDRef cmdRef = new CHC_CMDRef();
        CHC_Receiver.CHCGetCmdInitConnection(receiveref , cmdRef );



    }
    // CHC_LP
   // hcc
}
