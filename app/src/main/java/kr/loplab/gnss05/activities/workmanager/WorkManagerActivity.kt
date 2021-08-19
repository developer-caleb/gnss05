package kr.loplab.gnss05.activities.workmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.filter.Filter
import com.evrencoskun.tableview.pagination.Pagination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.common.Define.WORKERS_DB
import kr.loplab.gnss05.databinding.ActivityWorkManagerBinding
import kr.loplab.gnss05.tableview.TableViewAdapter
import kr.loplab.gnss05.tableview.TableViewListener
import kr.loplab.gnss05.tableview.TableViewModel

class WorkManagerActivity : ActivityBase<ActivityWorkManagerBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_work_manager
    lateinit var db : AppDatabase
    private lateinit var mTableView: TableView
    lateinit var tableWorkerViewModel: TableViewModel
    lateinit var tableViewAdapter : TableViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, WORKERS_DB)
            //.allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용
            .build()
        mTableView = findViewById(R.id.tableview2)
        }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btAdd.setOnClickListener {
            intent = Intent(this, WorkerActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btEdit.setOnClickListener {
            Log.d(TAG, "initListener: edit ${TableViewModel.selectedIndex}")
            /*intent = Intent(this, WorkerActivity::class.java)
            startActivity(intent);*/
        }
        viewBinding.btDelete.setOnClickListener {
           // mTableView.
        }
        viewBinding.btConfirm.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()
        //initDatabinding()
    }
    override fun initDatabinding() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewBinding.dbText.text = db.workerDao().all.toString()
            var  workerlist : List<Worker> = db.workerDao().all
            initializeTableView(workerlist)
        }
        // Let's get TableView

    }

     fun initializeTableView(workerlist : List<Worker>) {
        // Create TableView View model class  to group view models of TableView
        tableWorkerViewModel = TableWorkerViewModel(workerlist)

        // Create TableView Adapter
        val tableViewAdapter = TableViewAdapter(tableWorkerViewModel)
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







}