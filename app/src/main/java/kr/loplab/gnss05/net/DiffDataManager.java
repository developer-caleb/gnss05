package kr.loplab.gnss05.net;

public class DiffDataManager {
	private static DiffDataManager sInstance = null;

	private DiffDataInfo mDiffDataInfo;

	private DiffDataManager() {
		mDiffDataInfo = new DiffDataInfo();
		mDiffDataInfo.setIp("211.144.118.5");
		mDiffDataInfo.setPort(2102);
		mDiffDataInfo.setSourcePoint("RTCM3");
		mDiffDataInfo.setUserName("zd");
		mDiffDataInfo.setPassWord("zd");
	}

	public static DiffDataManager getInstance() {
		if (sInstance == null) {
			synchronized (DiffDataManager.class) {
				if (sInstance == null) {
					sInstance = new DiffDataManager();
				}
			}
		}
		return sInstance;
	}

	public DiffDataInfo getDiffDataInfo() {
		return mDiffDataInfo;
	}

	public void setDiffDataInfo(DiffDataInfo mDiffDataInfo) {
		this.mDiffDataInfo = mDiffDataInfo;
	}

}
