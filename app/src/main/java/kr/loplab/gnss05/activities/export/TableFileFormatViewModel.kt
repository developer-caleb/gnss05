/*
 * MIT License
 *
 * Copyright (c) 2021 Evren Coşkun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package kr.loplab.gnss05.activities.export

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kr.loplab.gnss05.tableview.TableViewModel
import kr.loplab.gnss05.activities.export.TableFileFormatViewModel
import kr.loplab.gnss05.activities.export.FileFormat
import kr.loplab.gnss05.common.JsonUtils.Companion.jsonarrayToList
import kr.loplab.gnss05.common.OptionList.Companion.SEPERATOR_LIST
import kr.loplab.gnss05.common.OptionList.Companion.SEPERATOR_LIST_OUTPUT
import kr.loplab.gnss05.tableview.model.Cell
import kr.loplab.gnss05.tableview.model.ColumnHeader
import kr.loplab.gnss05.tableview.model.RowHeader
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class TableFileFormatViewModel : TableViewModel {
    var TAG = TableFileFormatViewModel::class.java.simpleName
    private val customFileFormat = ArrayList(Arrays.asList("번호", "형식명", "확장명", "형식설명"))
    var fileformatslist: MutableList<FileFormat>? = null

    internal constructor() {}
    internal constructor(fileformatslist: MutableList<FileFormat>) {
        ROW_SIZE = fileformatslist.size
        this.fileformatslist = fileformatslist
    }

    override fun removePosition(position: Int) {
        Log.d(TAG, "removePosition: override.. 삭제")
        if (position >= fileformatslist!!.size || position < 0) return
        if (fileformatslist != null && fileformatslist!!.size > 0) {
            fileformatslist!!.removeAt(position)
            ROW_SIZE = fileformatslist!!.size
        } else {
            return
        }
    }

    private val simpleRowHeaderList: List<RowHeader>
        private get() {
            val list: MutableList<RowHeader> = ArrayList()
            for (i in 0 until ROW_SIZE) {
                val header = RowHeader(i.toString(), (i + 1).toString())
                list.add(header)
            }
            return list
        }

    /**
     * This is a dummy model list test some cases.
     */
    private val randomColumnHeaderList: List<ColumnHeader>
        private get() {
            val list: MutableList<ColumnHeader> = ArrayList()
            for (i in 0 until COLUMN_SIZE) {
                val title = customFileFormat[i + 1]
                val header = ColumnHeader(i.toString(), title)
                list.add(header)
            }
            return list
        }//"cell " + j + " " + i;
    //final int random = new Random().nextInt();
    // Create dummy id.
    /**
     * This is a dummy model list test some cases.
     */
    private val cellListForSortingTest: List<List<Cell>>
        private get() {
            val list: MutableList<List<Cell>> = ArrayList()
            for (i in 0 until ROW_SIZE) {
                val cellList: MutableList<Cell> = ArrayList()
                for (j in 0 until COLUMN_SIZE) {
                    var text: Any = "" //"cell " + j + " " + i;
                    //final int random = new Random().nextInt();
                    if (j == 0) {
                        text = if (fileformatslist != null && fileformatslist!!.size != 0) {
                            fileformatslist!![i].formatName
                        } else ""
                    } else if (j == 1) {
                        text = if (fileformatslist != null && fileformatslist!!.size != 0) {
                            fileformatslist!![i].extensionName
                        } else ""
                    } else if (j == 2) {
                        text = if (fileformatslist != null && fileformatslist!!.size != 0) {
                           // fileformatslist!![i].formatDescription
                                jsonarrayToList(fileformatslist!![i].formatDescription , fileformatslist!![i].seperator)
                        } else ""
                    }
                    // Create dummy id.
                    val id = "$j-$i"
                    var cell: Cell
                    cell = Cell(id, text)
                    cellList.add(cell)
                }
                list.add(cellList)
            }
            return list
        }

    override fun getCellList(): List<List<Cell>> {
        return cellListForSortingTest
    }

    override fun getRowHeaderList(): List<RowHeader> {
        return simpleRowHeaderList
    }

    override fun getColumnHeaderList(): List<ColumnHeader> {
        return randomColumnHeaderList
    }

    companion object {
        // Constant size for dummy data sets
        private const val COLUMN_SIZE = 3
        private var ROW_SIZE = 5
    }


}