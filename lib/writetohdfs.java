import org.hipi.image.FloatImage;
import org.apache.hadoop.io.IntWritable;
import javax.imageio.ImageIO;
import java.io.*;

/*
 * Contains function useful for writing output to HDFS 
 **/

public static void Write_To_Hdfs(FloatImage value,Context context_output)
{
	
	/*
	 * Converts floatImage to text and write it to the hdfs file system
	 * 
	 * @params FloatImage values : hadoop's output FloatImage to be written to hdfs
	   @params Context context_output :The context of HDFS where the output is to be written
	 * 
	 */

	CharSequence cSeq;
	//object to store the image data
	StringBuilder finaldata=new StringBuilder();;	
	
	int temp;
	String str;

	//height of image
	int h=value.getHeight();

	//width of image
	int w=value.getWidth();

	//the pointer of image data		
	float[] array = value.getData();

	temp = h;
	str = String.valueOf(temp); 
	cSeq=str;
	finaldata.append(cSeq);
	cSeq="\n";
	finaldata.append(cSeq);
	temp = w;
	str = String.valueOf(temp); 
	cSeq=str;
	finaldata.append(cSeq);
	cSeq="\n";
	finaldata.append(cSeq);    

	// Traverse image pixel data in raster-scan order and write to HDFS
	for (int j = 0; j < h; j++)
	{
		for (int i = 0; i < w; i++) 
		{
			
			if(value.getNumBands() == 1)
			{
				temp=(int)(array[j*w + i]);
				str = String.valueOf(temp);      
	
				cSeq=str;
				finaldata.append(cSeq);
				
				cSeq=" ";
				finaldata.append(cSeq);
				 	
			}
			if(value.getNumBands() == 3)
			{
				temp=(int)(array[(j*w + i)*3+0]);
				str = String.valueOf(temp);      
	
				cSeq=str;
				finaldata.append(cSeq);
				
				cSeq=" ";
				finaldata.append(cSeq);
				temp=(int)(array[(j*w + i)*3+1]);
				str = String.valueOf(temp);      
	
				cSeq=str;
				finaldata.append(cSeq);
	
				cSeq=" ";
				finaldata.append(cSeq);
				temp=(int)(array[(j*w + i)*3+2]);
				str = String.valueOf(temp);      
	
				cSeq=str;
				finaldata.append(cSeq);
	
				cSeq=" ";
				finaldata.append(cSeq);
			}	
		}
	}
	cSeq="\n";
	finaldata.append(cSeq);
	// Emit output of job which will be written to HDFS
	context.write(null, finaldata);
}
