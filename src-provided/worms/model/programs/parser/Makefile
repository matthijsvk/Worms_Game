
ifndef JAVA
  JAVA   = /usr/bin/java
endif

ifndef JAVAC
  JAVAC  = /usr/bin/javac
endif

ifndef CLASSPATH
  CLASSPATH = ""
endif


ANTLRPWD = $(shell pwd)
ANTLRJAR = antlr-4.2.2-complete.jar

SETENV   = export \
             CLASSPATH="./:${CLASSPATH}:${ANTLRPWD}/${ANTLRJAR}:$$CLASSPATH"

ANTLR4   = ${SETENV}; ${JAVA} -jar ${ANTLRPWD}/${ANTLRJAR}
GRUN     = ${SETENV}; ${JAVA} org.antlr.v4.runtime.misc.TestRig


all: antlr test_lexer test_api test_listener antlrclasspath

antlr:
	curl http://www.antlr.org/download/${ANTLRJAR} \
          -o ${ANTLRJAR}
	${ANTLR4}
	touch antlr


antlrclasspath: antlr
	echo "${ANTLRPWD}/${ANTLRJAR}" >antlrclasspath


WormsParser.java: WormsParser.g4 antlr
	${ANTLR4} WormsParser.g4


WormsParser.class: WormsParser.java
	${SETENV}; ${JAVAC} \
          WormsParserParser*.java WormsParserLexer*.java \
          WormsParserListener.java WormsParserBaseListener.java


test_lexer: WormsParser.class
	cd ../../../../; \
          ${GRUN} worms.model.programs.parser.WormsParser \
          eval -tree <../programs/syntax_test.txt
	cd ../../../../; \
          ${GRUN} worms.model.programs.parser.WormsParser \
          eval -tree <../programs/program.txt
	cd ../../../../; \
          ${GRUN} worms.model.programs.parser.WormsParser \
          eval -tree <../programs/program_simple.txt


test_lexer_gui: test_lexer
	cd ../../../../; \
          ${GRUN} worms.model.programs.parser.WormsParser \
          eval -gui <../programs/syntax_test.txt


Processor.class: Processor.java WormsParser.class
	${SETENV}; ${JAVAC} Processor.java \
          WormsParserParser*.java WormsParserLexer*.java \
          WormsParserListener.java WormsParserBaseListener.java


Listener.class: Listener.java WormsParserMyListener.java WormsParser.class
	${SETENV}; ${JAVAC} Listener.java \
          WormsParserParser*.java WormsParserLexer*.java \
          ../ProgramFactory.java DummyProgramFactoryImpl.java ../ProgramParser.java \
          WormsParserListener.java WormsParserMyListener.java


test_api: Processor.class
	cd ../../../../; ${SETENV}; \
          ${JAVA} worms.model.programs.parser.Processor \
          ../programs/syntax_test.txt
	cd ../../../../; ${SETENV}; \
          ${JAVA} worms.model.programs.parser.Processor \
          ../programs/program.txt
	cd ../../../../; ${SETENV}; \
          ${JAVA} worms.model.programs.parser.Processor \
          ../programs/program_simple.txt


test_listener: Listener.class
	cd ../../../../; ${SETENV}; \
          ${JAVA} worms.model.programs.parser.Listener \
          ../programs/syntax_test.txt
	cd ../../../../; ${SETENV}; \
          ${JAVA} worms.model.programs.parser.Listener \
          ../programs/program.txt
	cd ../../../../; ${SETENV}; \
          ${JAVA} worms.model.programs.parser.Listener \
          ../programs/program_simple.txt


clean:
	rm -f *.class
	rm -f WormsParserBaseListener.java
	rm -f WormsParserLexer.*
	rm -f WormsParserListener.java WormsParserParser.java WormsParser.tokens
	rm -f antlrclasspath


distclean: clean
	rm -f antlr antlr-4.*.jar

cleanforzip:
	rm -f *.class antlr antlrclasspath antlr*.jar
