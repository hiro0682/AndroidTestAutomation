package AndroidTest;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImgConv {  
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        String path_in = "C:/Users/hoge/Lenna.bmp";
        String path_out = "C:/Users/hoge/Lenna_gray.bmp";

        Mat mat_src = new Mat();
        Mat mat_dst = new Mat();

        mat_src = Imgcodecs.imread(path_in);                          // 入力画像の読み込み
        Imgproc.cvtColor(mat_src, mat_dst, Imgproc.COLOR_BGR2GRAY); // カラー画像をグレー画像に変換
        Imgcodecs.imwrite(path_out, mat_dst);                         // 出力画像を保存
    }

}