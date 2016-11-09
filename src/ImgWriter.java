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
    	System.out.println("�Ƿ����������ļ�(Y/N)");
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
			System.out.println("ϵͳ�����쳣");
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
			System.out.println("��������Ҫд�����̵��ļ���");
		    try {
				filePath = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("��ȡԴ�ļ�·���쳣");
			}
		    
		    sourceFile = new File(filePath);
		    if(!sourceFile.exists()){
		    	System.out.println("Դ�ļ�������");
		    	
		    	isShowMenu = getIsReShowMenu(in);
		    	continue;
		    }else{
		    	System.out.println("��ѡ��д�����̹��:1-1.44MB");
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
		    	
		    	System.out.println("���������̱���·��");
		    	try {
					savePath = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
			    	System.out.println("���뱣��·���쳣");
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
