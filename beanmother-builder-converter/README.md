# Beanmother Builder Converter

This module, help to use Beanmother where no public constructor it's available, specialy where [builder factory pattern](https://en.wikipedia.org/wiki/Builder_pattern) apply, in some "flavor" like [GRPC](https://github.com/grpc/grpc-java) or [Lombok](https://projectlombok.org/features/Builder).

## Configuration

Create fixture `.yml` file in `test/resources/fixtures` as a convention.

```yaml
# test/resources/fixtures/builder.yml

pattern-builder:
  _initBuilder: newBuilder
  _finishBuilder: build
  _targetClass: io.beanmother.grpc.GrpcBuilderClass$BuilderPC
  attr1: 1

pattern-builder-lombok:
  _initBuilder: builder
  _finishBuilder: build
  _targetClass: io.beanmother.lombok.BuilderClass
  prop1: 1
  prop2: 2

pattern-builder-wikipedia:
  _construct:
  _finishBuilder: build
  _targetClass: io.beanmother.wikipedia.CarBuilderImpl
  wheels: 4
  color: Red  
```

The fixture describe how to build the class, through the builder pattern.

There is 3 (maybe more) kind of "builder pattern":

1º The "grpc style"
```java
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
```

2º The "lombok style"
```java
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
```

3º The ["wikipedia style"](https://en.wikipedia.org/wiki/Builder_pattern#Java)
```java
public class Car {

    private int wheels;
    private String color;

    public Car() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public int getWheels() {
        return wheels;
    }

    public void setWheels(final int wheels) {
        this.wheels = wheels;
    }

}

interface CarBuilder {
    Car build();

    CarBuilder setColor(final String color);

    CarBuilder setWheels(final int wheels);
}

public class CarBuilderImpl implements CarBuilder {
    private Car car;

    public CarBuilderImpl() {
        car = new Car();
    }

    @Override
    public Car build() {
        return car;
    }

    @Override
    public CarBuilder setColor(final String color) {
        car.setColor(color);
        return this;
    }

    @Override
    public CarBuilder setWheels(final int wheels) {
        car.setWheels(wheels);
        return this;
    }
}

public class CarBuildDirector {
    private CarBuilder builder;

    public CarBuildDirector(final CarBuilder builder) {
        this.builder = builder;
    }

    public Car construct() {
        return builder.setWheels(4)
                      .setColor("Red")
                      .build();
    }

    public static void main(final String[] arguments) {
        final CarBuilder builder = new CarBuilderImpl();

        final CarBuildDirector carBuildDirector = new CarBuildDirector(builder);

        System.out.println(carBuildDirector.construct());
    }
}
```

The paramas of the fixture are:
* _initBuilder: The method we need to call over the targetClass (grpc: GrpcBuilderClass.newBuilder() or lombok BuilderClass.builder()).
* _construct: indicate tha "instead" of call initBuilder we build the target call calling its default constructor. In the wikipedia example, we can access a constructor, but we build fluent way the result.
* _finishBuilder: The method we need to call to generate the instanced target class.
* _targetClass: Define the target class.

The example of use, throught the test is:

```java

BuilderObjectMother objectMother = BuilderObjectMother.getInstance();

@Before
public void setup(){
      objectMother.addFixtureLocation("testmodel_fixtures");
}
	
@Test
public void testBuilderAndAttr() {
  GrpcBuilderClass obj = objectMother.bear("pattern-builder", GrpcBuilderClass.class);
  assertTrue("1".equals(((GrpcBuilderClass)obj).getAttr1()));
}

@Test
public void testBuilderAndAttr() {
  BuilderClass obj = objectMother.bear("pattern-builder-lombok", BuilderClass.class);
  assertTrue("1".equals(((BuilderClass)obj).getProp1()));
  assertTrue("2".equals(((BuilderClass)obj).getProp2()));
}

@Test
public void testBuilderAndAttr() {
  Car obj = objectMother.bear("pattern-builder-wikipedia", Car.class);
  assertTrue("Red".equals(obj.getColor()));
  assertTrue(4==obj.getWheels());
}
```

And that's all!