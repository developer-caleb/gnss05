package kr.loplab.gnss05.activities.export

import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import kr.loplab.gnss02.ActivityBase
import kr.loplab.gnss05.adapter.FiledirectoryRecyclerViewAdapter
import kr.loplab.gnss05.R
import kr.loplab.gnss05.databinding.ActivityFileExportBinding
import kr.loplab.gnss05.enums.Directorytype
import java.io.File

class FileExportActivity : ActivityBase<ActivityFileExportBinding>(),
    FiledirectoryRecyclerViewAdapter.RecyclerItemClickListener {
    override val layoutResourceId: Int
        get() = R.layout.activity_file_export
        var backdirectory : String = "";
        //
    //private var items : ArrayList<String> = ArrayList();
    var data = ArrayList<Array<String>>()
    private var rootPath = "";
    private var rootPathname = "내장 메모리";
    private var nextPath = "";
    private var prevPath = "";
    private var currentPath = "";
    private lateinit var  messageView: TextView;
    private lateinit var adapter : FiledirectoryRecyclerViewAdapter
    private var initialdata = ArrayList<Array<String>>()
    //

    override fun onBackPressed() {
        currentPath = viewBinding.strDirectory.text.toString().replace( rootPathname, rootPath);
        prevPath(currentPath)
    //super.onBackPressed()
    }

    override fun init() {
        //initialdata.add(arrayOf( "기본 저장 디렉토리로 이동", "0"), )
        initialdata.add(arrayOf( "프로그램 폴더로 이동", Directorytype.storagedirectory.name, "rightarrow"))
        initialdata.add(arrayOf( "다운로드 폴더로 이동", Directorytype.downloadfolders.name, "rightarrow"))
        initialdata.add(arrayOf("최상위 폴더로 이동", Directorytype.rootdirectory.name, "rightarrow"))
        //initialdata.add(arrayOf( "SD카드 루트 디렉토리로 이동", "SDCARD!"))
        initialdata.add(arrayOf( "돌아가기", Directorytype.goBack.name, "back"))
        adapter  = FiledirectoryRecyclerViewAdapter(this, data)
        adapter.setClickListener(this)
        viewBinding.recyclerview.adapter = adapter
        rootPath = Environment.getExternalStorageDirectory().absolutePath;
        var result : Boolean = initdirectory(rootPath);








    }

    override fun initListener() {
        viewBinding.header03.setOnBackButtonClickListener {
            //onBackPressed()
            println("hello")
            super.onBackPressed()
        }
        viewBinding.folderIcon.text = "\uD83D\uDDC2    "
        viewBinding.layoutFilenameInput.setOnClickListener { requestETfocus(viewBinding.etFilenameInput) }


    }

    override fun initDatabinding() {

    }

    override fun onItemClick(view: View?, position: Int) {

        var sdPath = ""

        Log.d(TAG, "onItemClick: sdpath : $sdPath")
        Log.d(TAG, "onItemClick: 절대경로 : ${Environment.getExternalStorageDirectory().absolutePath}")
        Log.d(TAG, "onItemClick: 상대경로 : ${Environment.getExternalStorageDirectory().canonicalPath}")
        Log.d(TAG, "onItemClick: 다운로드 경로1 : ${Environment.getExternalStorageDirectory().absolutePath}")
        Log.d(TAG, "onItemClick: 다운로드 경로2 : ${Environment.getDataDirectory().absolutePath}")
        Log.d(TAG, "onItemClick: 다운로드 경로3 : ${Environment.getRootDirectory().absolutePath}")

        Log.d(TAG, "Recyclerview onItemClick: $position 클릭했음")

          Log.d("KJH_TEST",  "$position : " + data[position].toString());
                currentPath = viewBinding.strDirectory.text.toString().replace( rootPathname, rootPath);
                var path : String= data[position][1].toString();
          Log.d(TAG, "path check $path ")
          Log.d(TAG, "path check root path : $rootPath ")
                 when (path) {
                     "0"-> initdirectory(Environment.getExternalStorageDirectory().absolutePath)
                     Directorytype.rootdirectory.name-> initdirectory(rootPath)
                     Directorytype.downloadfolders.name-> initdirectory(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath)
                     Directorytype.SDCARDfolder.name-> initdirectory(rootPath)
                     Directorytype.goBack.name -> prevPath(path)
                     Directorytype.storagedirectory.name -> {
                         var  gnssfolder = File(Environment.getExternalStorageDirectory().absolutePath +"/GNSS/EXPORT")
                         if (!gnssfolder.exists()) {
                             Log.d(TAG, "onItemClick: Directory Does Not Exist, Create It! /GNSS")
                             //Toast.makeText(MainActivity.this, "Directory Does Not Exist, Create It", Toast.LENGTH_SHORT).show();
                             var success = true
                             success = gnssfolder.mkdirs();
                         }
                         initdirectory(Environment.getExternalStorageDirectory().absolutePath +"/GNSS/EXPORT")

                     }
                     else ->  nextPath(path);
                 }
    }

    fun initdirectory(rootPath : String) :Boolean   {


       /* var fileRoot: File =  File(rootPath);
        //디렉토리가 아님
        if(!fileRoot.isDirectory())        {
            showToast("Not Directory");
            return false;
        }


        //파일 리스트 가져오기 ->//파일 리스트가 없음


        var fileList  = fileRoot.list();
        if ( fileList == null )        {
            showToast("Could not find List");
            return false;
        }

        // 아이템 리스트 전부 삭제
        data.clear();
        data.addAll(initialdata)
        fileList.forEachIndexed { index, string ->  data.add(arrayOf(fileList[index].toString(), fileList[index].toString()));}*/
        processpath(rootPath)
        return true;
    }


    fun nextPath(str: String) {
        prevPath = currentPath

        // 현재 경로에서 / 와 다음 경로 붙이기
        nextPath = "$currentPath/$str"

        processpath(nextPath)

    }

    fun prevPath(str: String?) {
        nextPath = currentPath
        prevPath = currentPath

        if(currentPath == rootPath){
            showToast("최상위 폴더입니다")
            return;}
        // 마지막 / 의 위치 찾기
        val lastSlashPosition = prevPath.lastIndexOf("/")

        // 처음부터 마지막 / 까지의 문자열 가져오기
        prevPath = prevPath.substring(0, lastSlashPosition)



        processpath(prevPath)

    }
    fun processpath(path: String){
        viewBinding.strDirectory.text = path.replace(rootPath , rootPathname)
        val file = File(path)
        if (!file.isDirectory) {
            //TODO: "processpath: csv 파일 클릭처리 할 것!"
            Log.d(TAG, "processpath: csv 파일 클릭처리 할 것!")
            return
        }

        val fileList = file.listFiles()
        data.clear()
        data.addAll(initialdata)
        fileList.forEachIndexed { index, filess ->

           // data.add(arrayOf(fileList[index].toString(), fileList[index].toString()));}

            Log.d(TAG, "processpath: $index : ${filess.isDirectory} 와 ${filess.isFile} 와 ${filess.extension.lowercase()=="csv"}  : ${filess.name}")
            when {
                filess.isDirectory -> {
                    data.add(arrayOf(filess.name, filess.name, "folder"));
                }
                filess.extension.lowercase()=="csv" -> {
                    print("file.extension -> ${file.extension}")
                    data.add(arrayOf(filess.name, filess.name, ".csv"));
                }
                else -> {
                   /* print("file.extension -> ${file.extension}")
                    data.add(arrayOf(filess.name, filess.name, ".csv"));*/
                }
            }
            }
        adapter.notifyDataSetChanged()
    }
}


