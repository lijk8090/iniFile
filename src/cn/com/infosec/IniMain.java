package cn.com.infosec;

public class IniMain {

	public static void main(String[] args) throws Exception {
		String filename = "src/cfg/config.ini";
		String filename1 = "src/cfg/config1.ini";
		String section = "log";
		String key = "level";
		String value = "debug";

		IniFile iniFile = new IniFile(filename);

		iniFile.readIniFile(filename);

		System.out.println(iniFile.getIniKeyValue(section, key));
		iniFile.setIniKeyValue(section, key, value);
		System.out.println(iniFile.getIniKeyValue(section, key));

		iniFile.writeIniFile(filename1);
	}

}
