package cn.com.infosec;

public class IniMain {

	public static void main(String[] args) throws Exception {
		String filename = "src/cfg/config.ini";
		String section = "log";
		String key = "level";
		String value = "debug";

		IniFile iniFile = new IniFile(filename);

		iniFile.readIniFile(null);

		System.out.println(iniFile.getIniFile(section, key));
		iniFile.setIniFile(section, key, value);
		System.out.println(iniFile.getIniFile(section, key));

		iniFile.writeIniFile(null);
	}

}
