# Name: Ruben Calderon
# CruzID: 1698961
# Class: 12B/M
# Date: May 19, 2019
# Description: Compiles and archives Simulation.java into executable JAR file
# File Name: Makefile

JAVAC      = javac 
MAINCLASS  = Simulation
JAVASRC    = $(wildcard *.java)
SOURCES    = $(JAVASRC) Makefile README
CLASSES    = $(patsubst %.java, %.class, $(JAVASRC))
JARCLASSES = $(patsubst %.class, %*.class, $(CLASSES))
JARFILE    = $(MAINCLASS)
SUBMIT     = submit cmps012b-pt.s19 pa4

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(JARCLASSES)
	chmod +x $(JARFILE)
	rm Manifest

%.class: %.java
	$(JAVAC) $<

clean:
	rm *.class $(JARFILE)

submit:
	$(SUBMIT) $(SOURCES)

check:
	ls  /afs/cats.ucsc.edu/class/cmps012b-pt.s19/pa4/rucalder
