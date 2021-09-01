package kr.loplab.gnss05.activities.export

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.evrencoskun.tableview.TableView
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.workmanager.AppDatabase
import kr.loplab.gnss05.activities.workmanager.WorkerActivity
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.databinding.ActivityCustomFileFormatBinding
import kr.loplab.gnss05.databinding.ActivityWorkManagerBinding
import kr.loplab.gnss05.tableview.TableViewModel

class CustomFileFormatActivity : ActivityBase<ActivityCustomFileFormatBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_custom_file_format
    private lateinit var mTableView: TableView
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        TableViewModel.selectedIndex = -1
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, Define.WORKERS_DB)
            .allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용
            .fallbackToDestructiveMigration()
            .build()
        mTableView = findViewById(R.id.tableview2)

    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btAdd.setOnClickListener {
            intent = Intent(this, UserFormatMake::class.java)
            ActivityCompat.startActivityForResult(this, intent, Define.REQUEST_WORKER_MANAGE_ADD, null)
        }
    }

    override fun initDatabinding() {

    }
}