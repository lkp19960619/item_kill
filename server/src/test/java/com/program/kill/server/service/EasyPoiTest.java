package com.program.kill.server.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.program.kill.model.entity.Item;
import com.program.kill.model.mapper.ItemMapper;
import com.program.kill.server.MainApplication;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: 李开鹏
 * @Date: 2020/5/3 9:28
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class EasyPoiTest {

    @Autowired(required = false)
    private ItemMapper itemMapper;

    //TODO：easyPoi导出测试
    @Test
    public void test1() throws Exception {
        List<Item> items = itemMapper.selectAll();
        ExportParams exportParams = new ExportParams("商品信息", "item");
        /**
         * easypoi可以自适应两种类型
         * exportExcel  参数1 导出参数对象  参数2 被导出实体类对象    参数3 被导出的数据
         */
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Item.class, items);

        //写出
        workbook.write(new FileOutputStream("D://easyPoi.xlsx"));
    }

    //TODO：easyPoi导入测试
    @Test
    public void test2() throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        /**
         * arg1:文件输入流
         * arg2:导入实体类对象
         * arg3:导入参数对象
         */
        List<Item> list = ExcelImportUtil.importExcel(new FileInputStream("D://easyPoi.xlsx"), Item.class, importParams);
        for (Item item : list) {
            System.out.println(item);
        }
    }

    //TODO：poi test
    @Test
    public void test3() throws Exception {
        //常见文件对象
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        //通过文件对象创建表对象
        HSSFSheet sheet = hssfWorkbook.createSheet();

        //获取行对象 下标从0开始
        HSSFRow row = sheet.createRow(2);

        //获取单元格 下标从0开始
        HSSFCell cell = row.createCell(2);

        //在单元格中写入数据
        cell.setCellValue("李开鹏");

        //保存在磁盘中
        hssfWorkbook.write(new FileOutputStream("D://info.xls"));
    }

    @Test
    public void test4() throws Exception {
        /**
         * 标题上的数据
         */
        String[] titles = {"主键","商品名称","商品编号","商品单价","购买时间","商品状态","商品上架时间","更新时间"};
        //创建文件对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建表对象
        HSSFSheet sheet = workbook.createSheet("item-info");
        //获取行对象
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i <titles.length ; i++) {
            //获取单元格
            HSSFCell cell = row.createCell(i);
            //往单元格中写入数据
            cell.setCellValue(titles[i]);
        }

        /**
         * 数据库中的数据
         */
        //查询数据
        List<Item> items = itemMapper.selectAll();
        //获取行对象
        for (int i = 0; i <items.size() ; i++) {
            Item item = items.get(i);
            //创建行
            HSSFRow row1 = sheet.createRow(i + 1);
            //获取单元格并往单元格中写入数据
            /*
            row1.createCell(0).setCellValue(item.getId());
            row1.createCell(1).setCellValue(item.getName());
            row1.createCell(2).setCellValue(item.getCode());
            row1.createCell(3).setCellValue(item.getStock());
            row1.createCell(4).setCellValue(item.getPurchaseTime());
            row1.createCell(5).setCellValue(item.getIsActive());
            row1.createCell(6).setCellValue(item.getCreateTime());
//            row1.createCell(7).setCellValue(items.get(i).getUpdateTime());
*/
//            遍历创建单元格，有多少个单元格根据实体类属性决定
            //获取item类的对象
            Class<? extends Item> itemClass = item.getClass();
            //通过类的对象
            Field[] fields = itemClass.getDeclaredFields();
            for (int j = 0; j <fields.length ; j++) {
                HSSFCell cell = row1.createCell(j);
                //赋值，通过属性对象拿到属性值
                Field field = fields[j];
                //设置可以访问私有变量
                field.setAccessible(true);
                String value = field.get(item).toString();
                cell.setCellValue(value);
            }
        }
        //保存到磁盘中
        workbook.write(new FileOutputStream("D://item-info.xls"));
    }
}
