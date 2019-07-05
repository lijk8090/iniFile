package cn.com.infosec;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

public class IniFile {

	private String filename;
	private List<Object> list = new ArrayList<Object>();

	public IniFile() {
		super();
	}

	public IniFile(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return this.filename;
	}

	public List<Object> getList() {
		return this.list;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	// public void setList(List<Object> list) {
	// this.list = list;
	// }

	public List<Object> readIniFile(String filename) throws Exception {
		Ini ini = new Ini(new FileReader(filename != null ? filename : this.filename));

		for (String sectionName : ini.keySet()) {
			IniSection iniSection = new IniSection(sectionName);

			Section section = ini.get(sectionName);
			for (String keyName : section.keySet()) {
				String valueName = section.get(keyName);

				IniKeyValue iniKeyValue = new IniKeyValue(keyName, valueName);
				iniSection.getList().add(iniKeyValue);
			}

			this.list.add(iniSection);
		}

		return this.list;
	}

	public String getIniFile(String section, String key) throws Exception {

		for (Object sectionValue : this.list) {
			IniSection iniSection = (IniSection) sectionValue;

			for (Object keyValue : iniSection.getList()) {
				IniKeyValue iniKeyValue = (IniKeyValue) keyValue;

				if (section.equals(iniSection.getSection()) && key.equals(iniKeyValue.getKey())) {
					return iniKeyValue.getValue();
				}
			}
		}

		return null;
	}

	public void setIniFile(String section, String key, String value) throws Exception {

		for (Object sectionValue : this.list) {
			IniSection iniSection = (IniSection) sectionValue;

			for (Object keyValue : iniSection.getList()) {
				IniKeyValue iniKeyValue = (IniKeyValue) keyValue;

				if (section.equals(iniSection.getSection()) && key.equals(iniKeyValue.getKey())) {
					iniKeyValue.setValue(value);
				}
			}
		}
	}

	public void writeIniFile(String filename) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename != null ? filename : this.filename));

		for (Object sectionValue : this.list) {
			IniSection iniSection = (IniSection) sectionValue;
			writer.write("[" + iniSection.getSection() + "]" + "\n");

			for (Object keyValue : iniSection.getList()) {
				IniKeyValue iniKeyValue = (IniKeyValue) keyValue;
				writer.write(iniKeyValue.getKey() + " = " + iniKeyValue.getValue() + "\n");
			}

			writer.write("\n");
		}

		writer.close();
	}

}
