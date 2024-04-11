FROM openjdk:17-alpine
ENTRYPOINT ["java"]
COPY /build/libs/platform_sale_java_spring_project_new.jar /platform_sale_java_spring_project_new.jar
CMD ["-jar", "/platform_sale_java_spring_project_new.jar"]
