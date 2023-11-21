bin/Test.class: Test.java bin/CPLangParser.class bin/CPLangLexer.class
	javac -cp .:antlr-4.11.1-complete.jar -d bin Test.java

bin/CPLangParser.class: CPLangParser.java
	javac -cp .:antlr-4.11.1-complete.jar -d bin CPLangParser.java

bin/CPLangLexer.class: CPLangLexer.java
	javac -cp .:antlr-4.11.1-complete.jar -d bin CPLangLexer.java

CPLangLexer.java: CPLangLexer.g4
	java -jar antlr-4.11.1-complete.jar -visitor -listener CPLangLexer.g4

CPLangParser.java: CPLangParser.g4 CPLangLexer.java
	java -jar antlr-4.11.1-complete.jar -visitor -listener CPLangParser.g4

clean:
	rm -rf bin/* CPLang*.java *.interp *.tokens

run: bin/Test.class
	java -cp bin:antlr-4.11.1-complete.jar Test
