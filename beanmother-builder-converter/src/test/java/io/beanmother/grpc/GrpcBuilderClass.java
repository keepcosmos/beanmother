package io.beanmother.grpc;

public final class GrpcBuilderClass {

	private String attr1;

	public static BuilderPC newBuilder() {
		return new BuilderPC();
	}

	public static final class BuilderPC {

		private static GrpcBuilderClass pbc;

		private BuilderPC() {
			pbc = new GrpcBuilderClass();
		}

		public BuilderPC setAttr1(String value) {
			pbc.attr1 = value;
			return this;
		}

		public GrpcBuilderClass build() {
			return pbc;
		}
	}

	private GrpcBuilderClass() {
		attr1 = "";
	}

	public String getAttr1() {
		return attr1;
	}

	public static void main(String[] args) {
		GrpcBuilderClass pbc = GrpcBuilderClass.newBuilder().setAttr1("attr1").build();
		System.out.println(pbc.getAttr1());
	}
	
}

