package kr.loplab.gnss05.activities.workmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.filter.Filter
import com.evrencoskun.tableview.pagination.Pagination
import com.evrencoskun.tableview.pagination.Pagination.OnTableViewPageTurnedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.DefaultHeader
import kr.loplab.gnss05.GlobalApplication
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.UserFormatMake
import kr.loplab.gnss05.common.Define.WORKERS_DB
import kr.loplab.gnss05.databinding.ActivityStopSurveyBinding
import kr.loplab.gnss05.databinding.ActivityWorkManagerBinding
import kr.loplab.gnss05.tableview.TableMainActivity
import kr.loplab.gnss05.tableview.TableViewAdapter
import kr.loplab.gnss05.tableview.TableViewListener
import kr.loplab.gnss05.tableview.TableViewModel

class WorkManagerActivity : ActivityBase<ActivityWorkManagerBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_work_manager
    lateinit var db : AppDatabase


    private var moodFilter: Spinner? = null
    private  var genderFilter:android.widget.Spinner? = null
    private var previousButton: ImageButton? = null
    private  var nextButton:android.widget.ImageButton? = null
    private var tablePaginationDetails: TextView? = null
    private lateinit var mTableView: TableView
    lateinit var tableViewModel: TableViewModel
    private var mTableFilter // This is used for filtering the table.
            : Filter? = null
    private var mPagination // This is used for paginating the table.
            : Pagination? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun init() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, WORKERS_DB)
            //.allowMainThreadQueries() //메인쓰레드에서 작동시킬 때 사용
            .build()
        }

    override fun initListener() {
        viewBinding.header01.setOnBackButtonClickListener { onBackPressed();}
        viewBinding.btAdd.setOnClickListener {
            intent = Intent(this, WorkerActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btEdit.setOnClickListener {
            intent = Intent(this, WorkerActivity::class.java)
            startActivity(intent);
        }
        viewBinding.btDelete.setOnClickListener {  }
        viewBinding.btConfirm.setOnClickListener {  }
    }

    override fun initDatabinding() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewBinding.dbText.text = db.workerDao().all.toString()
        }
        // Let's get TableView
        mTableView = findViewById(R.id.tableview2)

        initializeTableView()
    }

     fun initializeTableView() {
        // Create TableView View model class  to group view models of TableView
        tableViewModel = TableWorkerViewModel()

        // Create TableView Adapter
        val tableViewAdapter = TableViewAdapter(tableViewModel)
        mTableView.setAdapter(tableViewAdapter)
        mTableView.setTableViewListener(TableViewListener(mTableView))

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        tableViewAdapter.setAllItems(
            tableViewModel.getColumnHeaderList(), tableViewModel
                .getRowHeaderList(), tableViewModel.getCellList()
        )

    }







}