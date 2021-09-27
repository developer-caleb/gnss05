package kr.loplab.gnss05.net;

public class DiffDataManager {
	private static DiffDataManager sInstance = null;

	private DiffDataInfo mDiffDataInfo;

	private DiffDataManager() {
		mDiffDataInfo = new DiffDataInfo();
		mDiffDataInfo.setIp("210.117.198.83");
		mDiffDataInfo.setPort(2201);
		mDiffDataInfo.setSourcePoint("RTCM3");
		mDiffDataInfo.setUserName("dreamtns00");
		mDiffDataInfo.setPassWord("ngii");
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
