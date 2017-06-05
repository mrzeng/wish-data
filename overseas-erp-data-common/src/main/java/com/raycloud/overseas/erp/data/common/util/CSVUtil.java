package com.raycloud.overseas.erp.data.common.util;

import com.csvreader.CsvReader;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenglingfang on 16/12/21.
 */
public class CSVUtil {

    public static List<Object[]> readeCSV(String fileName){

        String path = HttpClientUtil.getFilePath(fileName);

        try {
            CsvReader reader = new CsvReader(path, ',', Charset.forName("UTF-8"));
            reader.readHeaders();
            List<Object[]> list = new ArrayList<Object[]>();
            while (reader.readRecord()) {
                list.add(reader.getValues());
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        String url = "";
        try {
            List<Object []> resultList = readeCSV(url);
            if (resultList != null && resultList.size() > 0){
                System.out.println(resultList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
