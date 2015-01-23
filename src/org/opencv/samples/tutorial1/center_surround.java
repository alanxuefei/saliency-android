package org.opencv.samples.tutorial1;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class center_surround {
	public static Mat main(Mat center, Mat surround, int img_type) {

        int center_width = center.cols();
        int center_height = center.rows();
        int surround_width = surround.cols();
        //int surround_height = surround.rows();
    
        Mat surround_resize = surround.clone();
        Mat out = center.clone();
        /*
        int depth =(int)(Math.log(center_width/surround_width)/Math.log(2));
        for(int depth_index = 0; depth_index < depth; depth_index++){
           Imgproc.resize(surround_resize, surround_resize, new Size(), 2, 2, Imgproc.INTER_AREA);
        }
        */
        Imgproc.resize(surround_resize, surround_resize, new Size(center_width, center_width),0, 0, Imgproc.INTER_AREA);
             
        for (int x = 0; x < center_width; x++) {
            for (int y = 0; y < center_height; y++) {
                if(img_type == 0){//integer
                    int temp_1 = Math.abs((int)center.get(y,x)[0]-(int)surround_resize.get(y,x)[0]);
                    out.put(y,x, temp_1);
                }else if(img_type == 1){//double
                    double temp_1 = Math.abs(center.get(y,x)[0]-surround_resize.get(y,x)[0]);
                    out.put(y,x, temp_1);
                }
            }
        }
        return out;
    }   
}
