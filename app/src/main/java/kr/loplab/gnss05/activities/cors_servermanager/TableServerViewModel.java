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

package kr.loplab.gnss05.activities.cors_servermanager;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.loplab.gnss05.activities.workmanager.Worker;
import kr.loplab.gnss05.tableview.TableViewModel;
import kr.loplab.gnss05.tableview.model.Cell;
import kr.loplab.gnss05.tableview.model.ColumnHeader;
import kr.loplab.gnss05.tableview.model.RowHeader;

public class TableServerViewModel extends TableViewModel {
    String TAG = TableServerViewModel.class.getSimpleName();
    // Constant size for dummy data sets
    private static int COLUMN_SIZE = 5;
    private static int ROW_SIZE = 5;
    private ArrayList<String> customFileFormat = new ArrayList<>(Arrays.asList("번호", "이름", "IP", "포트","사용자","암호"));
    List<Worker> workerslist =null;

     TableServerViewModel(){}
     TableServerViewModel(List<Worker> workerslist){
         ROW_SIZE = workerslist.size();
        this.workerslist = workerslist;
    }

    @Override
    public void removePosition(int position){
        Log.d(TAG, "removePosition: override.. 삭제");
        if(position>= workerslist.size()||position<0) return;
        if(workerslist!=null && workerslist.size()>0){
            workerslist.remove(position);
            ROW_SIZE = workerslist.size();
        }else{return;}
    }


    @NonNull
    private List<RowHeader> getSimpleRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), //"row " +
                   String.valueOf(i+1)
            );
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<ColumnHeader> getRandomColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < COLUMN_SIZE; i++) {
            String title = customFileFormat.get(i + 1);
            ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
            list.add(header);
        }
        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < ROW_SIZE; i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < COLUMN_SIZE; j++) {
                Object text = "";//"cell " + j + " " + i;
                //final int random = new Random().nextInt();
                if (j == 0) {
                    if(workerslist!=null && workerslist.size()!=0){
                        text= workerslist.get(i).getWorker();
                    }else text= "";
                } else if (j == 1) {
                    if(workerslist!=null && workerslist.size()!=0){
                        text= workerslist.get(i).getName();
                    }else text= "";
                } else if (j == 2) {
                    if(workerslist!=null && workerslist.size()!=0){
                        text= workerslist.get(i).getUser();
                    }else text= "";
                }else if (j == 3) {
                    if(workerslist!=null && workerslist.size()!=0){
                        text= workerslist.get(i).getPassword();
                    }else text= "";
                }
                // Create dummy id.
                String id = j + "-" + i;
                Cell cell;
                cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }
        return list;
    }


    @NonNull
    public List<List<Cell>> getCellList() { return getCellListForSortingTest(); }

    @NonNull
    public List<RowHeader> getRowHeaderList() { return getSimpleRowHeaderList(); }

    @NonNull
    public List<ColumnHeader> getColumnHeaderList() { return getRandomColumnHeaderList(); }
}
