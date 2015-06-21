package ru.evo.calc;


import org.hibernate.loader.collection.SubselectOneToManyLoader;

import java.util.Arrays;

import static java.lang.Math.*;

/**
 * Created by Dima on 16.05.2015.
 */
public class TestMain {

    public static void main(String[] args) {
//        Integer[] m = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        Integer[] m = {0, 1};

//        Integer[] m = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int value = 10;

//        Integer mi = 0;
//        while (mi != value) {
//            int round = round(m.length / 2);
//            if (m[round] == value)
//                mi = value;
//            else if (m[round] > value) {
//                m = Arrays.copyOfRange(m, 0, round);
//            } else if (m[round] < value) {
//                m = Arrays.copyOfRange(m, round, m.length);
//            }
//        }

        int low = 0;
        int high = m.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int mVal = m[mid];
            if (mVal > value)
                high = mid - 1;
            else if (mVal < value)
                low = mid + 1;
            else {
                System.out.println(mVal);
                break;
            }
        }


    }
}
