import org.hipi.image.FloatImage;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.*;

/*
 * Contains function useful for Sharpening of given FloatImage
**/
public static FloatImage sharpening(FloatImage value) 
{
	/*
	 * Applies sharpening effect on the floatimage
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * Return value FloatImage : Modified FloatImage which is the output to be returned
	 * 
	 */
	
	// Get dimensions of image
	int w = value.getWidth();
	int h = value.getHeight();

	// Get pointer to image data
	float[] valData = value.getData();

	// Initialize w*h*3 element float array to hold modified pixel value
	float[] filtered = new float[w*h*3];

	float sumr,sumg,sumb;	

	// Traverse image pixel data in raster-scan order and update
	for (int j = 0; j < h; j++) 
	{
		for (int i = 0; i < w; i++)
		{
			sumr=0;
			sumg=0;
			sumb=0;
			for(int k=j-(size/2);k<=j+(size/2);k++)
			{
				for(int l=i-(size/2);l<=i+(size/2);l++)
				{
					if(k>=0 && k<h && l>=0 && l<w)
					{
						sumr = sumr + (valData[(k*w + l)*3+0]*255);
						sumg = sumg + (valData[(k*w + l)*3+1]*255);
						sumb = sumb + (valData[(k*w + l)*3+2]*255);
					}        
				}
			}

			sumr = sumr - valData[(j*w+i)*3+0];
			sumg = sumg - valData[(j*w+i)*3+1];
			sumb = sumb - valData[(j*w+i)*3+2];

			filtered[(j*w+i)*3+0] = valData[(j*w+i)*3+0] + ((8*valData[(j*w+i)*3+0] - sumr)/(size*size));
			if(filtered[(j*w+i)*3+0]>255)
			{
				filtered[(j*w+i)*3+0]=255;	
			}
			filtered[(j*w+i)*3+1] = valData[(j*w+i)*3+1] + ((8*valData[(j*w+i)*3+1] - sumg)/(size*size));
			if(filtered[(j*w+i)*3+1]>255)
			{
				filtered[(j*w+i)*3+1]=255;	
			}   
			filtered[(j*w+i)*3+2] = valData[(j*w+i)*3+2] + ((8*valData[(j*w+i)*3+2] - sumb)/(size*size));
			if(filtered[(j*w+i)*3+2]>255)
			{
				filtered[(j*w+i)*3+2]=255;	
			}
		}
	}				
	//Converting the filtered array of modified image back to FloatImage 	
	FloatImage result = new FloatImage(w,h, 3,filtered);
	return(result);

}
