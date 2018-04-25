package io.beanmother.grpc.util;

public final class PatternBuilderClass {

	private String attr1;

	public static BuilderPC newBuilder() {
		return new BuilderPC();
	}

	public static final class BuilderPC {

		private static PatternBuilderClass pbc;

		private BuilderPC() {
			pbc = new PatternBuilderClass();
		}

		public BuilderPC setAttr1(String value) {
			pbc.attr1 = value;
			return this;
		}

		public PatternBuilderClass build() {
			return pbc;
		}
	}

	private PatternBuilderClass() {
		attr1 = "";
	}

	public String getAttr1() {
		return attr1;
	}

	public static void main(String[] args) {
		PatternBuilderClass pbc = PatternBuilderClass.newBuilder().setAttr1("attr1").build();
		System.out.println(pbc.getAttr1());
	}
	
}

