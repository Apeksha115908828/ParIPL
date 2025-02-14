import org.hipi.image.FloatImage;
import java.awt.*;
import java.util.Arrays;
import javax.swing.JFrame;
import java.io.IOException;
import java.io.*;

/*
 * Contains function useful to blur the image by Gaussian filtering
 **/


public float gauss_value(int x,int y,int sigma)
{
	/*
	*Finds gauss functions value for  given x,y 
	*	
	*@params int x : x value in gauss function
	* int y : y value in gauss function
	* float sigma : standard deviation of Gaussian Blur 	
	*
	*/
	float gvalue=(float)((float)1.0/((2*Math.PI*((float)(sigma*sigma))))*(float)Math.exp((((x*x)+(y*y)))/(2*sigma*sigma)));
	return(gvalue);
}


public static FloatImage GaussianBlur(FloatImage value,int radius,float sigma) 
{
	/*
	 * Applies Gaussian filter i.e. Gaussian blur to the given FloatImage
	 * 
	 * @params FloatImage value : Input Image in FloatImage format
	 * int radius : Gaussian Radius for the gauss filter
	 * float sigma : standard deviation of Gaussian Blur
	 * Return value FloatImage : Modified FloatImage which is the output to be returned
	 * 
	 */
	CharSequence cSeq;
	
	// Get dimensions of image
	int w = value.getWidth();
	int h = value.getHeight();

	// Get pointer to image data
	float[] valData = value.getData();

	// Initialize w*h*3 element float array to hold modified pixel value
	float[] filtered = new float[w*h*3];

	float sumr,sumg,sumb;

	//calculate the gaussian filter of given radius 
	//array to store gaussian filter
	float filter[] = new float[size*size];
	int size = (radius*2)+1;
	float sum=(float)0.0;
	for(int i=-radius;i<=radius;i++)
	{
		for(int j=radius;j>=-radius;j--)
		{
			filter[(radius+i)*(2*radius + 1)+(radius-j)]=gauss_value(j,i,sigma);
			sum=sum+filter[(radius+i)*(2*radius + 1)+(radius-j)];
		}
	}
	
	for(int i=0;i<(2*radius + 1);i++)
	{
		for(int j=0;j<(2*radius + 1);j++)
		{	
			filter[i*(2*radius + 1)+j]=(float)(filter[i*(2*radius + 1)+j]/sum);
		}
	}

	

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
			filtered[(j*w+i)*3+0]=(int)(sumr);
			filtered[(j*w+i)*3+1]=(int)(sumg);
			filtered[(j*w+i)*3+2]=(int)(sumb);
		}
	}

	//Converting the filtered array of modified image back to FloatImage 
    	FloatImage result = new FloatImage(w,h,3,filtered);
	return(result);
	
}
