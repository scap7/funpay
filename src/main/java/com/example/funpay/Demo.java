package com.example.funpay;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@interface TestAnno{
  String task() default "no task-string passed";
  int testVal() default 2345;
}

@TestAnno(task="so a different task", testVal=666)
class Cat {
  private int age ;
  private String name = "Ram";

  public void setName(String name){
    this.name = name;
  }
}



class Demo{

  public static void main(String[] args){
    Cat cat = new Cat();
    Arrays.stream(cat.getClass().getDeclaredFields()).forEach(e -> System.out.println(e));
    int[] test = {1,6, 9, 89, 70, 57};

    Arrays.stream(test).filter((e) -> { return (e % 2) == 0;}).forEach(System.out::println);



    TestAnno testAnno = cat.getClass().getAnnotation(TestAnno.class);

    System.out.println(testAnno.testVal());
    System.out.println(testAnno.task());
  }
}



