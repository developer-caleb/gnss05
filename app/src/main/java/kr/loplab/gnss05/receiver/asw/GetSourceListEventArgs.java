package kr.loplab.gnss05.receiver.asw;

import java.io.Serializable;

public class GetSourceListEventArgs implements Serializable {
	private static final long serialVersionUID = 8984935344062981185L;
	private boolean status;

	public GetSourceListEventArgs(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return status;
	}
}
