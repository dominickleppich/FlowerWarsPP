#**********************************************************************
#*  sub makefile for pdflatex projects
#*
#*  brosenne@cs.uni-goettingen.de
#*  09.2009
#*
#********************************************************************** 


#---------------------------------------------------------------------
#
# if called as top level makefile then call make in parent directory
#
ifeq ($(MAKELEVEL),0)

.PHONY: all

all:
	$(MAKE) -C .. all

%::
	$(MAKE) -C .. $(MAKECMDGOALS)



#---------------------------------------------------------------------
#
# not called as  top level makefile
#
else

.SUFFIXES: .fig .pdftex .pdftex_t .pstex .pstex_t

FIG2DEV   = fig2dev

.fig.pdftex_t:
	$(FIG2DEV) -L pdftex < $< > $*.pdftex
	$(FIG2DEV) -L pdftex_t -p $*.pdftex < $< > $@

.fig.pstex_t:
	$(FIG2DEV) -L pstex < $< > $*.pstex
	$(FIG2DEV) -L pstex_t -p $*.pstex < $< > $@


#*********************************************************************

.PHONY: all clean

all: $(patsubst %.fig,%.pdftex_t,$(wildcard *.fig))

clean:
	rm -f *.pdftex
	rm -f *.pdftex_t
	rm -f *.pstex
	rm -f *.pstex_t
	rm -f *.bak
	rm -f *~

endif