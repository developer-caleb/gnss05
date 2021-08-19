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

package kr.loplab.gnss05.activities.workmanager;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kr.loplab.gnss05.R;
import kr.loplab.gnss05.tableview.TableViewModel;
import kr.loplab.gnss05.tableview.model.Cell;
import kr.loplab.gnss05.tableview.model.ColumnHeader;
import kr.loplab.gnss05.tableview.model.RowHeader;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class TableWorkerViewModel extends TableViewModel{
    public static int selectedIndex = -1;

    // Columns indexes
    public static final int MOOD_COLUMN_INDEX = 5;
    public static final int GENDER_COLUMN_INDEX = 8;

    // Constant values for icons
    public static final int SAD = 1;
    public static final int HAPPY = 2;
    public static final int BOY = 1;
    public static final int GIRL = 2;

    // Constant size for dummy data sets
    private static final int COLUMN_SIZE = 3;
    private static final int ROW_SIZE = 5;
    private ArrayList<String> customFileFormat = new ArrayList<>(Arrays.asList("번호", "형식명", "확장명", "형식설명"));


    // Drawables
    @DrawableRes
    private final int mBoyDrawable;
    @DrawableRes
    private final int mGirlDrawable;
    @DrawableRes
    private final int mHappyDrawable;
    @DrawableRes
    private final int mSadDrawable;

    public TableWorkerViewModel() {
        // initialize drawables
        mBoyDrawable = R.drawable.ic_male;
        mGirlDrawable = R.drawable.ic_female;
        mHappyDrawable = R.drawable.ic_happy;
        mSadDrawable = R.drawable.ic_sad;
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
            String title = "column " + i;
            /*int nRandom = new Random().nextInt();
            if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
                title = "large column ㅣ너미ㅏ너ㅏ나나나나 " + i;
            }*/
            switch (i){
                case 0 : title  = "형식명"; break;
                case 1 : title  = "확장명"; break;
                case 2 : title  = "형식설명"; break;

            }

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
                Object text = "cell " + j + " " + i;

                final int random = new Random().nextInt();
                if (j == 0) {
                    //text = i;
                    text= "형식명";
                } else if (j == 1) {
                    text= "csv";
                    //text = random;
                } else if (j == 2) {
                    text= "[이름], [코드], [이름], [코드], [이름], [코드], [이름], [코드], [이름], [코드], [이름], [코드],  ";
                    //text = random;
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

    @DrawableRes
    public int getDrawable(int value, boolean isGender) {
        if (isGender) {
            return value == BOY ? mBoyDrawable : mGirlDrawable;
        } else {
            return value == SAD ? mSadDrawable : mHappyDrawable;
        }
    }

    @NonNull
    public List<List<Cell>> getCellList() {
        return getCellListForSortingTest();
    }

    @NonNull
    public List<RowHeader> getRowHeaderList() {
        return getSimpleRowHeaderList();
    }

    @NonNull
    public List<ColumnHeader> getColumnHeaderList() {
        return getRandomColumnHeaderList();
    }
}
