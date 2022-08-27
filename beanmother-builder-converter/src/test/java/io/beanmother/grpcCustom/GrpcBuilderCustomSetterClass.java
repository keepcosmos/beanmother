package io.beanmother.grpcCustom;

public final class GrpcBuilderCustomSetterClass {

	private String attr1;

	public static BuilderPC newBuilder() {
		return new BuilderPC();
	}

	public static final class BuilderPC {

		private static GrpcBuilderCustomSetterClass pbc;

		private BuilderPC() {
			pbc = new GrpcBuilderCustomSetterClass();
		}

		public BuilderPC withAttr1(String value) {
			pbc.attr1 = value;
			return this;
		}

		public GrpcBuilderCustomSetterClass build() {
			return pbc;
		}
	}

	private GrpcBuilderCustomSetterClass() {
		attr1 = "";
	}

	public String getAttr1() {
		return attr1;
	}

	public static void main(String[] args) {
		GrpcBuilderCustomSetterClass pbc = GrpcBuilderCustomSetterClass.newBuilder().withAttr1("attr1").build();
		System.out.println(pbc.getAttr1());
	}
	
}

