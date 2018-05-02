package io.beanmother.lombok;

public class BuilderClass {

	private String prop1;
	private String prop2;

	public static BuilderClass builder() {
		return new BuilderClass();
	}

	public BuilderClass build() {
		return this;
	}
	
	public BuilderClass prop1(String prop) {
		this.prop1 = prop;
		return this;
	}

	public BuilderClass prop2(String prop) {
		this.prop2 = prop;
		return this;
	}

	public String getProp1() {
		return this.prop1;
	}

	public String getProp2() {
		return this.prop2;
	}

	public static void main(String[] args) {
		BuilderClass bc = BuilderClass.builder().prop1("prop1").build();
		System.out.println(bc.getProp1());		
	}


}

