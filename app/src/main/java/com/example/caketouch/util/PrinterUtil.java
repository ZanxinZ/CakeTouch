package com.example.caketouch.util;

import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.caketouch.food_for_serve.DrinkOrdered;
import com.example.caketouch.table.Stuff;
import com.example.caketouch.table.StuffSize;
import com.example.caketouch.table.Table;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


public class PrinterUtil {

    private OutputStreamWriter mWriter = null;
    private OutputStream mOutputStream = null;

    public final static int WIDTH_PIXEL = 384;
    public final static int IMAGE_SIZE = 240;

    /**
     * 初始化Pos实例
     *
     * @param encoding 编码
     * @throws IOException
     */
    public PrinterUtil(OutputStream outputStream, String encoding) throws IOException {
        mWriter = new OutputStreamWriter(outputStream, encoding);
        mOutputStream = outputStream;
        initPrinter();
    }

    public void print(byte[] bs) throws IOException {
        mOutputStream.write(bs);
    }

    public void printRawBytes(byte[] bytes) throws IOException {
        mOutputStream.write(bytes);
        mOutputStream.flush();
    }

    /**
     * 初始化打印机
     *
     * @throws IOException
     */
    public void initPrinter() throws IOException {
        mWriter.write(0x1B);
        mWriter.write(0x40);
        mWriter.flush();
    }


    /**
     * 打印换行
     *
     * @return length 需要打印的空行数
     * @throws IOException
     */
    public void printLine(int lineNum) throws IOException {
        for (int i = 0; i < lineNum; i++) {
            mWriter.write("\n");
        }
        mWriter.flush();
    }

    /**
     * 打印换行(只换一行)
     *
     * @throws IOException
     */
    public void printLine() throws IOException {
        printLine(1);
    }

    /**
     * 打印空白(一个Tab的位置，约4个汉字)
     *
     * @param length 需要打印空白的长度,
     * @throws IOException
     */
    public void printTabSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            mWriter.write("\t");
        }
        mWriter.flush();
    }

    /**
     * 绝对打印位置
     *
     * @return
     * @throws IOException
     */
    public byte[] setLocation(int offset) throws IOException {
        byte[] bs = new byte[4];
        bs[0] = 0x1B;
        bs[1] = 0x24;
        bs[2] = (byte) (offset % 256);
        bs[3] = (byte) (offset / 256);
        return bs;
    }

    public byte[] getGbk(String stText) throws IOException {
        byte[] returnText = stText.getBytes("GBK"); // 必须放在try内才可以
        return returnText;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getStringPixLength(String str) {
        int pixLength = 0;
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN) {
                pixLength += 24;
            } else {
                pixLength += 12;
            }
        }
        return pixLength;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getOffset(String str) {
        return WIDTH_PIXEL - getStringPixLength(str);
    }

    /**
     * 打印文字
     *
     * @param text
     * @throws IOException
     */
    public void printText(String text) throws IOException {
        mWriter.write(text);
        mWriter.flush();
    }

    /**
     * 对齐0:左对齐，1：居中，2：右对齐
     */
    public void printAlignment(int alignment) throws IOException {
        mWriter.write(0x1b);
        mWriter.write(0x61);
        mWriter.write(alignment);
    }

    public void printLargeText(String text) throws IOException {

        mWriter.write(0x1b);
        mWriter.write(0x21);
        mWriter.write(48);

        mWriter.write(text);

        mWriter.write(0x1b);
        mWriter.write(0x21);
        mWriter.write(0);

        mWriter.flush();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void printTwoColumn(String title, String content) throws IOException {
        int iNum = 0;
        byte[] byteBuffer = new byte[100];
        byte[] tmp;

        tmp = getGbk(title);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = setLocation(getOffset(content));
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(content);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);

        print(byteBuffer);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void printThreeColumn(String left, String middle, String right) throws IOException {
        int iNum = 0;
        byte[] byteBuffer = new byte[200];
        byte[] tmp = new byte[0];

        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(left);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        int pixLength = getStringPixLength(left) % WIDTH_PIXEL;
        if (pixLength > WIDTH_PIXEL / 2 || pixLength == 0) {
            middle = "\n\t\t" + middle;
        }

        tmp = setLocation(192);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(middle);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = setLocation(getOffset(right));
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);
        iNum += tmp.length;

        tmp = getGbk(right);
        System.arraycopy(tmp, 0, byteBuffer, iNum, tmp.length);

        print(byteBuffer);
    }

    public void printDashLine() throws IOException {
        printText("--------------------------------");
    }
    public void printTypeDashLine() throws IOException {
        printText("------");
    }

    public void printBitmap(Bitmap bmp) throws IOException {
        bmp = compressPic(bmp);
        byte[] bmpByteArray = draw2PxPoint(bmp);
        printRawBytes(bmpByteArray);
    }

    /*************************************************************************
     * 假设一个360*360的图片，分辨率设为24, 共分15行打印 每一行,是一个 360 * 24 的点阵,y轴有24个点,存储在3个byte里面。
     * 即每个byte存储8个像素点信息。因为只有黑白两色，所以对应为1的位是黑色，对应为0的位是白色
     **************************************************************************/
    private byte[] draw2PxPoint(Bitmap bmp) {
        //先设置一个足够大的size，最后在用数组拷贝复制到一个精确大小的byte数组中
        int size = bmp.getWidth() * bmp.getHeight() / 8 + 1000;
        byte[] tmp = new byte[size];
        int k = 0;
        // 设置行距为0
        tmp[k++] = 0x1B;
        tmp[k++] = 0x33;
        tmp[k++] = 0x00;
        // 居中打印
        tmp[k++] = 0x1B;
        tmp[k++] = 0x61;
        tmp[k++] = 1;
        for (int j = 0; j < bmp.getHeight() / 24f; j++) {
            tmp[k++] = 0x1B;
            tmp[k++] = 0x2A;// 0x1B 2A 表示图片打印指令
            tmp[k++] = 33; // m=33时，选择24点密度打印
            tmp[k++] = (byte) (bmp.getWidth() % 256); // nL
            tmp[k++] = (byte) (bmp.getWidth() / 256); // nH
            for (int i = 0; i < bmp.getWidth(); i++) {
                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(i, j * 24 + m * 8 + n, bmp);
                        tmp[k] += tmp[k] + b;
                    }
                    k++;
                }
            }
            tmp[k++] = 10;// 换行
        }
        // 恢复默认行距
        tmp[k++] = 0x1B;
        tmp[k++] = 0x32;

        byte[] result = new byte[k];
        System.arraycopy(tmp, 0, result, 0, k);

        return result;
    }

    /**
     * 图片二值化，黑色是1，白色是0
     *
     * @param x   横坐标
     * @param y   纵坐标
     * @param bit 位图
     * @return
     */
    private byte px2Byte(int x, int y, Bitmap bit) {
        if (x < bit.getWidth() && y < bit.getHeight()) {
            byte b;
            int pixel = bit.getPixel(x, y);
            int red = (pixel & 0x00ff0000) >> 16; // 取高两位
            int green = (pixel & 0x0000ff00) >> 8; // 取中两位
            int blue = pixel & 0x000000ff; // 取低两位
            int gray = RGB2Gray(red, green, blue);
            if (gray < 128) {
                b = 1;
            } else {
                b = 0;
            }
            return b;
        }
        return 0;
    }

    /**
     * 图片灰度的转化
     */
    private int RGB2Gray(int r, int g, int b) {
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b); // 灰度转化公式
        return gray;
    }

    /**
     * 对图片进行压缩（去除透明度）
     *
     * @param bitmapOrg
     */
    private Bitmap compressPic(Bitmap bitmapOrg) {
        // 获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        // 定义预转换成的图片的宽度和高度
        int newWidth = IMAGE_SIZE;
        int newHeight = IMAGE_SIZE;
        Bitmap targetBmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(targetBmp);
        targetCanvas.drawColor(0xffffffff);
        targetCanvas.drawBitmap(bitmapOrg, new Rect(0, 0, width, height), new Rect(0, 0, newWidth, newHeight), null);
        return targetBmp;
    }

    private void printQRCode(String text) throws IOException {

//        mWriter.write(0x1b);
//        mWriter.write(0x61);
//        mWriter.write(0x01);

        char[] code = {0x1D, 0x28, 0x6B, 0x03, 0x00, 0x31, 0x51, 0x30};
        mWriter.write(code);
        mWriter.flush();
        char[] s = {0xE4,0xBD,0xA0,0xE5,0xA5,0xBD,0xEF ,0xBC ,0x8C ,0x57 ,0x65,0x6C,0x63,0x6F ,0x6D,0x65};
        //E4 BD A0 E5 A5 ,0xBD ,0xEF ,0xBC ,0x8C ,0x57 ,0x65,0x6C,0x63,0x6F ,0x6D,0x65
        for (int i = 0; i < s.length; i++) {
            mWriter.write(s[i]);
        }


        mWriter.flush();
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void printOrder(BluetoothSocket bluetoothSocket, Bitmap alipay, Bitmap wechat, Table table) {

        try {
            PrinterUtil pUtil = new PrinterUtil(bluetoothSocket.getOutputStream(), "GBK");
            // 店铺名 居中 放大
            pUtil.printAlignment(1);
            pUtil.printLargeText("烧烤园 " + table.getTableNo() + "号");
            pUtil.printLine();
            pUtil.printAlignment(0);
            pUtil.printLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pUtil.printTwoColumn("时间:", sdf.format(new Date()));
            pUtil.printLine();

            pUtil.printTwoColumn("人数:", table.getPeople() + "人");
            pUtil.printLine();

            // 分隔线
            pUtil.printDashLine();
            pUtil.printLine();

            //打印商品列表
            pUtil.printText("商品");
            pUtil.printTabSpace(2);
            pUtil.printText("数量");
            pUtil.printTabSpace(1);
            pUtil.printText("    小计");
            pUtil.printLine();

//            pUtil.printThreeColumn("iphone6", "1", "4999.00");
//            pUtil.printThreeColumn("测试一个超长名字的产品看看打印出来会怎么样哈哈哈哈哈哈", "1", "4999.00");

            //对
            Iterator<Map.Entry<Long, Stuff>> foodEntryIt = table.getOrder().ordered.entrySet().iterator();


            ArrayList<String[]>[] foodsToPrint = new ArrayList[6];
            for (int i = 0; i < 6; i++) {
                foodsToPrint[i] = new ArrayList<>();
            }
            while (foodEntryIt.hasNext()){
                Map.Entry<Long, Stuff> entry = foodEntryIt.next();
                String[] strs = new String[3];

                if (entry.getValue().getStuffSize() == StuffSize.normal) {
                    strs[0] = entry.getValue().getName() + "";
                }else if(entry.getValue().getStuffSize() == StuffSize.small){
                    strs[0] = entry.getValue().getName() + "(小)";
                }
                strs[1] = "1" + entry.getValue().getUnitName();
                strs[2] = entry.getValue().getPrice() + "";
                foodsToPrint[entry.getValue().getDishType().code].add(strs);
            }

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < foodsToPrint[i].size(); j++) {
                    pUtil.printThreeColumn(foodsToPrint[i].get(j)[0], foodsToPrint[i].get(j)[1], foodsToPrint[i].get(j)[2]);
                    pUtil.printLine();
                }
                if(foodsToPrint[i].size()!=0){
                    pUtil.printTypeDashLine();
                    pUtil.printLine();
                }
            }


            Iterator<Map.Entry<Long, DrinkOrdered>> drinkOrderedEntryIt = table.getOrder().drinkOrderedMap.entrySet().iterator();
            while (drinkOrderedEntryIt.hasNext()){
                Map.Entry<Long, DrinkOrdered> entry = drinkOrderedEntryIt.next();
                pUtil.printThreeColumn(entry.getValue().getName(), entry.getValue().getCount() + entry.getValue().getDishUnit() + "", entry.getValue().getMoney()*entry.getValue().getCount() + "");
                pUtil.printLine();
            }

            pUtil.printDashLine();
            pUtil.printLine();

            pUtil.printTwoColumn("订单金额:", table.getOrder().getTotal()+ "");
            pUtil.printLine();

            pUtil.printDashLine();

            pUtil.printAlignment(1);
            pUtil.printText("支付宝支付");
            pUtil.printLine();
            pUtil.printBitmap(alipay);

            pUtil.printLine();

            pUtil.printAlignment(1);
            pUtil.printText("微信支付");
            pUtil.printLine();
            pUtil.printBitmap(wechat);



            pUtil.printLine(4);

        } catch (IOException e) {

        }
    }
}
