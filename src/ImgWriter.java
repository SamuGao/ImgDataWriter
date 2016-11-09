import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImgWriter {
	private static boolean getIsReShowMenu(BufferedReader cmdInput){
    	System.out.println("是否重新输入文件(Y/N)");
    	String choice = "";
    	boolean isShowMenu = false;
    	try {
			choice = cmdInput.readLine();
			
	    	if("Y".equals(choice.toUpperCase())){
	    		isShowMenu = true;
	    	}else{
	    		isShowMenu = false;
	    	}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("系统输入异常");
			isShowMenu = false;
		}
    	
    	return isShowMenu;
	}
	
	private static void writeImgFile(File source, String saveFile, int imgKind) throws IOException{
		byte[] data = null;
		File outFile = new File(saveFile);
		if(!outFile.exists()){
			outFile.createNewFile();
		}
		int imgLen = 0;
		switch(imgKind){
		case 1:
			imgLen = 1474560;
			data = new byte[imgLen];
			break;
		case 2:
		default:
			break;
		}
		if(source.length() < 1474560){
			for(long i = source.length(); i < 1474560; ++i){
				data[(int)i] = 0x00;
			}
		}
		
		DataInputStream inputStream = null;
		DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(outFile));

		inputStream = new DataInputStream(new FileInputStream(source));

		inputStream.read(data, 0, (int)source.length());
		
		outputStream.write(data, 0, imgLen);
		
		if(inputStream != null){
			inputStream.close();
		}
		
		if(outputStream != null){
			outputStream.close();
		}
	}
	
	public static void  main(String[] args){
		InputStreamReader cmdInput = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(cmdInput);
		File sourceFile = null;
		boolean isShowMenu = true;
		String filePath = "";
		String savePath = "";
		String imgKind = "";
		String choice = "";
		while(isShowMenu){
			isShowMenu = false;
			System.out.println("请输入需要写入软盘的文件：");
		    try {
				filePath = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("读取源文件路径异常");
			}
		    
		    sourceFile = new File(filePath);
		    if(!sourceFile.exists()){
		    	System.out.println("源文件不存在");
		    	
		    	isShowMenu = getIsReShowMenu(in);
		    	continue;
		    }else{
		    	System.out.println("请选择写入软盘规格:1-1.44MB");
		    	try {
					choice = in.readLine();
					if(choice == null || "".equals(choice)){
						choice = "1";
					}
				} catch (IOException e) {
					e.printStackTrace();
					choice = "1";
				}
		    	
		    	imgKind = choice;
		    	
		    	System.out.println("请输入软盘保存路径");
		    	try {
					savePath = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
			    	System.out.println("输入保存路径异常");
			    	isShowMenu = getIsReShowMenu(in);
			    	continue;
				}
		    	
		    	try {
					writeImgFile(sourceFile, savePath, Integer.parseInt(imgKind));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	isShowMenu = getIsReShowMenu(in);
		    }
		}
	}
}
