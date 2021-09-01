package kr.loplab.gnss05.activities.export

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.evrencoskun.tableview.TableView
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.workmanager.AppDatabase
import kr.loplab.gnss05.activities.workmanager.Worker
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.databinding.ActivityCustomFileFormatBinding
import kr.loplab.gnss05.tableview.TableViewAdapter
import kr.loplab.gnss05.tableview.TableViewListener
import kr.loplab.gnss05.tableview.TableViewModel

class CustomFileFormatActivity : ActivityBase<ActivityCustomFileFormatBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_custom_file_format
    private lateinit var mTableView: TableView
    lateinit var tableFileFormatViewModel: TableViewModel
    lateinit var tableViewAdapter : TableViewAdapter

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
            ActivityCompat.startActivityForResult(this, intent, Define.REQUEST_FILE_FORMAT_ADD, null)
        }
        viewBinding.btEdit.setOnClickListener {
            if(TableViewModel.selectedIndex<=-1 || TableViewModel.selectedIndex>=tableFileFormatViewModel.rowHeaderList.size){
                showToast("수정할 행을 선택해주세요."); return@setOnClickListener
            }
            Log.d(TAG, "initListener: edit ${TableViewModel.selectedIndex}")
            intent = Intent(this, UserFormatMake::class.java)
            intent.putExtra("selectPosition", TableViewModel.selectedIndex);
            ActivityCompat.startActivityForResult(this, intent, Define.REQUEST_FILE_FORMAT_EDIT, null)
        }
        viewBinding.btDelete.setOnClickListener {
            // mTableView.
            if(TableViewModel.selectedIndex<=-1 || TableViewModel.selectedIndex>=tableFileFormatViewModel.rowHeaderList.size){
                showToast("삭제할 행을 선택해주세요."); return@setOnClickListener
            }
            Log.d(TAG, "initListener: delete ${TableViewModel.selectedIndex}")
            db.fileFormatDao().delete(db.fileFormatDao().all[TableViewModel.selectedIndex])
            refresh()
            if(tableFileFormatViewModel.rowHeaderList.size>0){
                mTableView.selectedRow = 0
                TableViewModel.selectedIndex = 0}else{
                mTableView.selectedRow = -1
                TableViewModel.selectedIndex = -1
                //mTableView.set
            }
        }
    }

    override fun initDatabinding() {
        refresh()
    }
    fun refresh(){
        var  fileformatslist : List<FileFormat> = db.fileFormatDao().all
        mTableView = findViewById(R.id.tableview2)
        tableFileFormatViewModel = TableFileFormatViewModel(fileformatslist)
        //추가 시작

        tableViewAdapter = TableViewAdapter(tableFileFormatViewModel)
        mTableView.setTableViewListener(TableViewListener(mTableView))
        mTableView.setAdapter(tableViewAdapter)
        //추가 끝
        tableViewAdapter.setAllItems(
            tableFileFormatViewModel.getColumnHeaderList(), tableFileFormatViewModel
                .getRowHeaderList(), tableFileFormatViewModel.getCellList()
        )

    }

}