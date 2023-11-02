ANTLR4=java -jar /usr/local/lib/antlr4.jar
CLASSPATH="/usr/local/lib/antlr4.jar:."

Test.class: Test.java CPLangParser.class CPLangLexer.class
	javac -cp $(CLASSPATH) Test.java

CPLangParser.class: CPLangParser.java
	javac -cp $(CLASSPATH) CPLangParser.java

CPLangLexer.class: CPLangLexer.java
	javac -cp $(CLASSPATH) CPLangLexer.java

CPLangLexer.java: CPLangLexer.g4
	$(ANTLR4) CPLangLexer.g4

CPLangParser.java: CPLangParser.g4 CPLangLexer.java
	$(ANTLR4) -visitor -no-listener CPLangParser.g4

clean:
	rm -f *.class CPLang*.java *.interp *.tokens

run: Test.class
	java -cp $(CLASSPATH) Test
