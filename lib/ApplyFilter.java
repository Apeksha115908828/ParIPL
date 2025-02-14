import org.hipi.image.FloatImage;
import java.awt.*;
import java.io.IOException;
import java.io.*;

/*
 * @author
 * 
 * */


/*
 * Contains function useful for dealing with filtering of floatimage
 **/
public static FloatImage ApplyFilter(FloatImage value,float filter[],int size) 
{

	/*
	 * Applies general filtering effect to the given floatimage
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * int size : size of filter matrix
	 * float filter[] : filter matrix to be applied on floatimage 
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
					sumr=sumr+(valData[(k*w + l)*3+0]*255)*filter[(k-(j-(size/2)))*size+(l-(i-(size/2)))];
					sumg=sumg+(valData[(k*w + l)*3+1]*255)*filter[(k-(j-(size/2)))*size+(l-(i-(size/2)))];
					sumb=sumb+(valData[(k*w + l)*3+2]*255)*filter[(k-(j-(size/2)))*size+(l-(i-(size/2)))];
				    }        
				}
			}
			filtered[(j*w+i)*3+0]=(int)(sumr/(size*size));
			if(filtered[(j*w+i)*3+0]<0)
			{
				filtered[(j*w+i)*3+0]=0;	
			}
			filtered[(j*w+i)*3+1]=(int)(sumg/(size*size));
			if(filtered[(j*w+i)*3+1]<0)
			{
				filtered[(j*w+i)*3+1]=0;	
			}        
			filtered[(j*w+i)*3+2]=(int)(sumb/(size*size));
			if(filtered[(j*w+i)*3+2]<0)
			{
				filtered[(j*w+i)*3+2]=0;	
			}
		}
	}

	// Converting the filtered array back to floatimage
	//FloatImage result : Modified FloatImage where the output has to be returned
	FloatImage result = new FloatImage(w,h, 3,filtered);
	return result;

	
}

