package com.smart.drools;

public class I {
	protected int c;
	private float v;
	protected String s;//样本来源
	protected char m;//测试方法
	protected char t;//样本类型
	protected String u;//计量单位
	
	public I() {}
	
	public I(String testId, String type, String unit, String value) {
		int code;
		code = Integer.parseInt(testId);
		this.setC(code);
		this.setM(' ');
		this.setS(type);
		this.setT(' ');
		this.setU(unit);
		float fvalue = 0;
		try {
			if (value.contains("> ")) {
				fvalue = Float.parseFloat(value.replace("> ", ""));
				this.setV(fvalue);
			} else if (value.contains(">")) {
				fvalue = Float.parseFloat(value.replace(">", ""));
				this.setV(fvalue);
			} else if (value.contains("< ")) {
				fvalue = Float.parseFloat(value.replace("< ", ""));
				this.setV(fvalue);
			} else if (value.contains("<")) {
				fvalue = Float.parseFloat(value.replace("<", ""));
				this.setV(fvalue);
			} else {
				fvalue = Float.parseFloat(value);
				this.setV(fvalue);
			}
		} catch (NumberFormatException e) {
			if (value.contains("阴性") || value.contains("正常") || value.equals("-")) {
				this.setU("-");
				this.setV(1);
			}
			if (value.contains("弱阳性")) {
				this.setU("±");
				this.setV(1);
			}
			if (value.contains("强阳性")) {
				this.setU("+");
				this.setV(3);
			}
			if (value.contains("阳性")) {
				this.setU("+");
				this.setV(1);
			}
			if (value.contains("+")) {
				this.setU("+");
				int count = 0;
				for (int i = 0; i < value.length(); i++) {
					if (value.charAt(i) == '+') {
						count++;
					}
				}
				this.setV(count);
			}
			this.setV(0);
		}
	}

	public I(SI si) {
		this.setC(si.getC());
		this.setM(' ');
		this.setS(si.getS());
		this.setT(' ');
		this.setU(si.getU());
		this.setV(si.getV());
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public float getV() {
		return v;
	}

	public void setV(float v) {
		this.v = v;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public char getM() {
		return m;
	}

	public void setM(char m) {
		this.m = m;
	}

	public char getT() {
		return t;
	}

	public void setT(char t) {
		this.t = t;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}
}
