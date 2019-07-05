package cn.com.infosec;

import java.util.ArrayList;
import java.util.List;

public class IniSection {

	private List<Object> list = new ArrayList<Object>();
	private String section;

	public IniSection() {
		super();
	}

	public IniSection(String section) {
		this.section = section;
	}

	public String getSection() {
		return this.section;
	}

	public List<Object> getList() {
		return this.list;
	}

	public void setSection(String section) {
		this.section = section;
	}

	// public void setList(List<Object> list) {
	// this.list = list;
	// }
}
