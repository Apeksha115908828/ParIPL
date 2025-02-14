import org.hipi.image.FloatImage;
import java.awt.*;
import java.io.IOException;
import java.io.*;

/*
 * @author
 * 
 * */


/*
 * Contains function useful for dealing with Mean filtering of floatimage
 **/
public static FloatImage MeanFilter(FloatImage value,int size) 
{

	/*
	 * Applies mean filtering i.e blurring effect to the given floatimage
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * int size : size of mean filter matrix
	 */
		
	// Defining a matrix of mean filter according to given size
	float[] filter = new float[size*size];
	for(int i=0;i<size*size;i++)
	{
		filter[i]=(float)(1.0/(size*size));
	}
	
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
			for(k=j-(size/2);k<=j+(size/2);k++)
			{
				for(l=i-(size/2);l<=i+(size/2);l++)
				{
				    if(k>=0 && k<h && l>=0 && l<w)
				    {
					sumr=sumr+((valData[(k*w + l)*3+0]*255)*filter[(k-(j-(size/2)))*size+(l-(i-(size/2)))]);
					sumg=sumg+((valData[(k*w + l)*3+1]*255)*filter[(k-(j-(size/2)))*size+(l-(i-(size/2)))]);
					sumb=sumb+((valData[(k*w + l)*3+2]*255)*filter[(k-(j-(size/2)))*size+(l-(i-(size/2)))]);
				    }        
				}
			}
			filtered[(j*w+i)*3+0]=sumr;
			filtered[(j*w+i)*3+1]=sumg;       
			filtered[(j*w+i)*3+2]=sumb;

		}
	}
	// Converting the filtered array back to floatimage
	//FloatImage finaldata : Modified FloatImage where the output has to be returned
	FloatImage result = new FloatImage(w,h, 3,filtered);
	return result;	
}
