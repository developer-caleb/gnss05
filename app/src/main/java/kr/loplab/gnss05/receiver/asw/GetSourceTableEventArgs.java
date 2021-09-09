package kr.loplab.gnss05.receiver.asw;

import com.huace.gnssserver.gnss.data.receiver.DataSourceList;
import com.huace.gnssserver.gnss.data.receiver.EnumReceiverCmd;

/**
 * get the data source
 * 
 * @author wangjun
 * 
 */
public class GetSourceTableEventArgs extends ReceiverDataEventArgs {

	DataSourceList mDataSourceList;

	public GetSourceTableEventArgs(EnumReceiverCmd cmd, DataSourceList data) {
		super(cmd);
		this.mDataSourceList = data;
	}

	public DataSourceList getDataSourceList() {
		return mDataSourceList;
	}
}
