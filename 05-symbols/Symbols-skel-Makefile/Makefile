Test.class: Test.java CPLangParser.class CPLangLexer.class
	javac -cp .:antlr-4.11.1-complete.jar Test.java

CPLangParser.class: CPLangParser.java
	javac -cp .:antlr-4.11.1-complete.jar CPLangParser.java

CPLangLexer.class: CPLangLexer.java
	javac -cp .:antlr-4.11.1-complete.jar CPLangLexer.java

CPLangLexer.java: CPLangLexer.g4
	java -jar antlr-4.11.1-complete.jar -visitor -listener CPLangLexer.g4

CPLangParser.java: CPLangParser.g4 CPLangLexer.java
	java -jar antlr-4.11.1-complete.jar -visitor -listener CPLangParser.g4

clean:
	rm -f *.class CPLang*.java *.interp *.tokens

run: Test.class
	java -cp .:antlr-4.11.1-complete.jar Test
