build:
	javac -cp src src/Main.java	
	
clean:
	find -name '*.class' | xargs rm -f
	
doc:
	find -name "*.java" > sources
	javadoc @sources -d docs
	rm sources
