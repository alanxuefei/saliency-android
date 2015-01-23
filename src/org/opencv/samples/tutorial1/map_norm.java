package org.opencv.samples.tutorial1;
//import static java.lang.Integer.max;
import static java.lang.Math.abs;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;
import org.opencv.imgproc.Imgproc;
import static org.opencv.core.CvType.CV_16UC1;

public class map_norm {

	
	public static Mat main(Mat in) {
        int norm_max_value = 100;
        int local_size = 30;//16;
        Mat out = in.clone();
        out.convertTo(out, CV_16UC1);
        Core.normalize(in, out, 0, norm_max_value, Core.NORM_MINMAX);
        //imwrite("norm_in"+in.rows()+".bmp", in);
        //imwrite("norm_out_0_"+out.rows()+".bmp", out);
        int width = out.cols();
        int height = out.rows();
        int block_hori_number = width/local_size;
        int block_vertical_number = height/local_size;
        int sum_max = 0;
        int ave_max = 0;
        int max_number = 0;
        int norm_max_value_3;
        for(int block_hori_index = 0; block_hori_index < block_hori_number; block_hori_index++){
            for(int block_vertical_index = 0; block_vertical_index < block_vertical_number; block_vertical_index++){
                int max = 0;
                // to calculate local maxima
                for(int x = 0; x < local_size; x++){
                    for(int y = 0; y < local_size; y++){
                        int y_location = y + local_size * block_vertical_index;
                        int x_location = x + local_size * block_hori_index;
                        if(max < out.get(y_location,x_location)[0]){
                           max = (int)out.get(y_location,x_location)[0];
                       }
                    }
                }
                if(max!=norm_max_value){
                    sum_max +=max;
                    max_number++;
                }
                //System.out.println("\nmax: "+ max);
            }
        }
        if(max_number != 0){
            ave_max = sum_max/max_number;
        }
        //System.out.println("\nave_max: "+ ave_max);
        int coeff = Math.max(1, (norm_max_value - ave_max)*(norm_max_value - ave_max)>>8);
        norm_max_value_3 = coeff*norm_max_value;
        if(norm_max_value_3 > 0xFFFF){
            norm_max_value_3 = 0xFFFF;
        }
        //System.out.println("\ntemp1: "+ (norm_max_value - ave_max)+" , "+out.rows());
        //System.out.println("\norm_max_value: "+ norm_max_value_3+" , "+out.rows());
        Core.normalize(out, out, 0, norm_max_value_3, Core.NORM_MINMAX);
        
        //imwrite("norm_out_1_"+out.rows()+".bmp", out);
        return out;
    } 
}
