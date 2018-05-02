# Beanmother Grpc Converter

This module, help to use beanmother where no public constructor it's enable, special where [builder factory pattern](https://en.wikipedia.org/wiki/Builder_pattern) apply.

## Example

Create fixture `.yml` file in `test/resources/fixtures` as a convention.

```yaml
# test/resources/fixtures/builder.yml

pattern-builder-grpc:
  _initBuilder: newBuilder
  _finishBuilder: build
  _targetClass: io.beanmother.grpc.util.PatternBuilderClass$BuilderPC
  attr1: 1

pattern-builder-lombok:
  _initBuilder: builder
  _finishBuilder: build
  _targetClass: io.beanmother.grpc.util.BuilderClass
  prop1: 1
  prop2: 2

pattern-builder-wikipedia:
  _construct:
  _finishBuilder: build
  _targetClass: io.beanmother.wikipedia.CarBuilderImpl
  wheels: 4
  color: Red  
```

The fixture describe how to build the class, through the builder.

There is 3 (maybe more) kind of "builder pattern":

1º The "grpc style"
2º The "lombok style"
3º The "wikipedia style"

The paramas are:
_initBuilder:
_finishBuilder:
_targetClass

and:

_construct




 indicate the method that initialize the class to build. In the example class PatternBuilderClass it's BuilderPC.newBuilder. In the 
[java wikipedia example](https://en.wikipedia.org/wiki/Builder_pattern#Java) ...


```java

ObjectMother objectMother = ObjectMother.getInstance();

@Test
public void testBuilderAndAttr() {
    PatternBuilderClass obj = objectMother.bear("pattern-builder", PatternBuilderClass.class);
}

@Test
public void testBuilderAndAttr() {
    BuilderClass obj = objectMother.bear("pattern-builder", BuilderClass.class);
}


```

And just create!


