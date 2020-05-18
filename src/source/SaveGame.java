package source;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class SaveGame {
	public static void saveStatus(Adventure adv,String filePath) throws JsonIOException, IOException {
		Gson gson = new Gson();
		FileWriter fw = new FileWriter(filePath);
		gson.toJson(adv, fw);
		fw.close();
		//System.out.println(gson.toJson(adv));
	
		
	}
}
