package kr.loplab.gnss05.activities.cors_servermanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.evrencoskun.tableview.TableView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define
import kr.loplab.gnss05.databinding.ActivityCorsServerManagerBinding
import kr.loplab.gnss05.tableview.TableViewAdapter
import kr.loplab.gnss05.tableview.TableViewListener
import kr.loplab.gnss05.tableview.TableViewModel

class CORSServerManagerActivity : ActivityBase<ActivityCorsServerManagerBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_cors_server_manager
    lateinit var db : AppDatabase2
    private lateinit var mTableView: TableView
    lateinit var tableWorkerViewModel: TableViewModel
    lateinit var tableViewAdapter : TableViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        TableViewModel.selectedIndex = -1
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        db = Room.databaseBuilder(this, AppDatabase2::class.java, Define.WORKERS_DB)
            .allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용
            .fallbackToDestructiveMigration()
            .build()
        mTableView = findViewById(R.id.tableview2)
    }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btAdd.setOnClickListener {
            intent = Intent(this, ServerAddressAddActivity::class.java)
            ActivityCompat.startActivityForResult(this, intent,
                Define.REQUEST_WORKER_MANAGE_ADD, null)
        }
        viewBinding.btEdit.setOnClickListener {
            if(TableViewModel.selectedIndex<=-1 || TableViewModel.selectedIndex>=tableWorkerViewModel.rowHeaderList.size){
                showToast("수정할 행을 선택해주세요."); return@setOnClickListener
            }
            Log.d(TAG, "initListener: edit ${TableViewModel.selectedIndex}")
            intent = Intent(this, ServerAddressAddActivity::class.java)
            intent.putExtra("selectPosition", TableViewModel.selectedIndex);
            ActivityCompat.startActivityForResult(this, intent,
                Define.REQUEST_WORKER_MANAGE_EDIT, null)
            /*intent = Intent(this, WorkerActivity::class.java)
            startActivity(intent);*/
        }
        viewBinding.btDelete.setOnClickListener {
            // mTableView.
            if(TableViewModel.selectedIndex<=-1 || TableViewModel.selectedIndex>= tableWorkerViewModel.rowHeaderList.size){
                showToast("삭제할 행을 선택해주세요."); return@setOnClickListener
            }
            Log.d(TAG, "initListener: delete ${TableViewModel.selectedIndex}")

            lifecycleScope.launch(Dispatchers.IO) {
                db.serverDao().delete(db.serverDao().all[TableViewModel.selectedIndex])
            }
            tableWorkerViewModel.removePosition(TableViewModel.selectedIndex)
            tableViewAdapter.setAllItems(
                tableWorkerViewModel.getColumnHeaderList(), tableWorkerViewModel
                    .getRowHeaderList(), tableWorkerViewModel.getCellList()
            )
            mTableView.selectedRow = 0
            TableViewModel.selectedIndex = 0

        }
        viewBinding.btConfirm.setOnClickListener {

        }
    }

    override fun initDatabinding() {
            var  serverlist : List<Server> = db.serverDao().all
            initializeTableView(serverlist)
    }
    fun initializeTableView(serverlist : List<Server>) {
        // Create TableView View model class  to group view models of TableView
        tableWorkerViewModel = TableServerViewModel(serverlist)

        // Create TableView Adapter
        tableViewAdapter = TableViewAdapter(tableWorkerViewModel)
        mTableView.setAdapter(tableViewAdapter)
        mTableView.setTableViewListener(TableViewListener(mTableView))

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        tableViewAdapter.setAllItems(
            tableWorkerViewModel.getColumnHeaderList(), tableWorkerViewModel
                .getRowHeaderList(), tableWorkerViewModel.getCellList()
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode== RESULT_OK && requestCode == Define.REQUEST_WORKER_MANAGE_ADD)
        {
            Log.d(TAG, "onActivityResult: 축하합니다_추가")
            //lifecycleScope.launch(Dispatchers.IO) { }
            var  serverlist : List<Server> = db.serverDao().all
            tableWorkerViewModel = TableServerViewModel(serverlist)
            refresh()
        }

        if(resultCode== RESULT_OK && requestCode == Define.REQUEST_WORKER_MANAGE_EDIT)
        {
            Log.d(TAG, "onActivityResult: 축하합니다_편집")
            //lifecycleScope.launch(Dispatchers.IO) { }
            var  serverlist : List<Server> = db.serverDao().all
            tableWorkerViewModel = TableServerViewModel(serverlist)
            refresh()
        }
    }

    fun refresh(){
        tableViewAdapter.setAllItems(
            tableWorkerViewModel.getColumnHeaderList(), tableWorkerViewModel
                .getRowHeaderList(), tableWorkerViewModel.getCellList()
        )
    }
}