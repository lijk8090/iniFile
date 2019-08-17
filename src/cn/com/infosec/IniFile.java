package cn.com.infosec;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
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

	public void setList(List<Object> list) {
		this.list = list;
	}

	public void printIniFile(String filename) throws Exception {
		Ini ini = new Ini(new FileReader(filename != null ? filename : this.filename));

		for (String sectionName : ini.keySet()) {
			System.out.println("[" + sectionName + "]");

			Section section = ini.get(sectionName);
			for (String keyName : section.keySet()) {
				String valueName = section.get(keyName);

				System.out.println(keyName + " = " + valueName.substring(0, valueName.indexOf(";")));
			}
		}
	}

	public List<Object> readIniFile(String filename) throws Exception {
		Ini ini = new Ini(new FileReader(filename != null ? filename : this.filename));

		for (String sectionName : ini.keySet()) {
			IniSection iniSection = new IniSection(sectionName);

			Section section = ini.get(sectionName);
			for (String keyName : section.keySet()) {
				String valueName = section.get(keyName);

				IniKeyValue iniKeyValue = new IniKeyValue(keyName, valueName.substring(0, valueName.indexOf(";")));
				iniSection.getList().add(iniKeyValue);
			}

			this.list.add(iniSection);
		}

		return this.list;
	}

	public String getIniKeyValue(String section, String key) throws Exception {

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

	public boolean setIniKeyValue(String section, String key, String value) throws Exception {

		for (Object sectionValue : this.list) {
			IniSection iniSection = (IniSection) sectionValue;

			for (Object keyValue : iniSection.getList()) {
				IniKeyValue iniKeyValue = (IniKeyValue) keyValue;

				if (section.equals(iniSection.getSection()) && key.equals(iniKeyValue.getKey())) {
					iniKeyValue.setValue(value);
					return true;
				}
			}
		}

		return false;
	}

	public boolean delIniSection(String section) throws Exception {

		Iterator<Object> it = this.list.listIterator();
		while (it.hasNext()) {
			IniSection obj = (IniSection) it.next();

			if (section.equals(obj.getSection())) {
				it.remove();
				return true;
			}
		}

		return false;
	}

	public boolean delIniKeyValue(String section, String key) throws Exception {

		for (Object sectionValue : this.list) {
			IniSection iniSection = (IniSection) sectionValue;

			if (!section.equals(iniSection.getSection())) {
				continue;
			}

			Iterator<Object> it = iniSection.getList().listIterator();
			while (it.hasNext()) {
				IniKeyValue obj = (IniKeyValue) it.next();

				if (key.equals(obj.getKey())) {
					it.remove();
					return true;
				}
			}
		}

		return false;
	}

	public boolean addIniSection(String section) throws Exception {

		for (Object sectionValue : this.list) {
			IniSection obj = (IniSection) sectionValue;

			if (section.equals(obj.getSection())) {
				return false;
			}
		}

		IniSection iniSection = new IniSection(section);
		this.list.add(iniSection);
		return true;
	}

	public boolean addIniKeyValue(String section, String key, String value) throws Exception {

		for (Object sectionValue : this.list) {
			IniSection iniSection = (IniSection) sectionValue;

			if (!section.equals(iniSection.getSection())) {
				continue;
			}

			for (Object keyValue : iniSection.getList()) {
				IniKeyValue obj = (IniKeyValue) keyValue;

				if (key.equals(obj.getKey())) {
					return false;
				}
			}

			IniKeyValue iniKeyValue = new IniKeyValue(key, value);
			iniSection.getList().add(iniKeyValue);
			return true;
		}

		return false;
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
