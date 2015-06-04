import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author zhangle
 *
 */
public class VideoFormatChange {
	/**
	 *  运行命令
	 * @param command
	 */
	public boolean runCmd(String command){
		try
			{          
		  		Runtime rt = Runtime.getRuntime();
		  		Process proc = rt.exec(command);
		        InputStream stderr = proc.getErrorStream();
		        InputStreamReader isr = new InputStreamReader(stderr);
		        BufferedReader br = new BufferedReader(isr);
		        String line = null;
		        System.out.println("<ERROR>");
		        while ( (line = br.readLine()) != null) {
		        	System.out.println(line);
		        }
		        System.out.println("</ERROR>");
		        int exitVal = proc.waitFor();
		        System.out.println("Process exitValue: " + exitVal);
		        return true;
		  	} catch (Throwable t) {
		    	t.printStackTrace();
		    	return false;
		    }
	}
	
	/**
	 * 视频转码
	 * @param infile 输入文件(包括完整路径)
	 * @param outfile 输出文件
	 * @return
	 */
	public boolean transfer(String infile,String outfile){
		String avitoflv = "ffmpeg -i "+infile+" -ar 22050 -ab 56 -f flv -y -s 320x240 "+outfile;
		String flvto3gp = "ffmpeg -i " + infile + " -ar 8000 -ac 1 -acodec amr_nb -vcodec h263 -s 176x144 -r 12 -b 30 -ab 12 " + outfile;
		String avito3gp = "ffmpeg -i " + infile + " -ar 8000 -ac 1 -acodec amr_nb -vcodec h263 -s 176x144 -r 12 -b 30 -ab 12 " + outfile;
		String mp4toflv = "ffmpeg -i "+infile+" -y -ab 32 -ar 22050 -qscale 10 -s 640*480 -r 15 "+ outfile;
		String mp4toavi = "ffmpeg -i " + infile +" "+ outfile;
		//avi -> jpg
		String avitojpg = "ffmpeg -i " + infile + " -y -f image2 -ss 00:00:10 -t 00:00:01 -s 350x240 " + outfile;
		return runCmd(mp4toavi);
	}
	
	/**
	 * main
	 * @param args
	 */
	 public static void main(String args[])
	 {
		 VideoFormatChange vfc = new VideoFormatChange();
		 String infile = "/home/zhangle/test.mp4";
		 String outfile = "/home/zhangle/test.avi";
		 //转码
		 if(vfc.transfer(infile,outfile)){
			 System.out.println(" the transfer is finished! ");
		 }else{
			 System.out.println(" the transfer is unfinished! ");
		 }
	  }
}
