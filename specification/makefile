#**********************************************************************
#*  PROJEKT:
#*    makefile fuer latex-Dokumente
#*    Datum: 06.2018
#*
#********************************************************************** 

NAME      = project
TEXFILES  = $(NAME) chapter/exam chapter/game chapter/git chapter/help chapter/implementation chapter/organization chapter/project-submission
SUBDIRS   = chapter

#**********************************************************************

.SUFFIXES: .tex .pdf

SWITCH    = --halt-on-error
PDFLATEX  = pdflatex ${SWITCH}


.tex.pdf:
	$(PDFLATEX) $<

#*********************************************************************

.PHONY: all pdf subdirs $(SUBDIRS) clean


all: pdf

pdf: subdirs $(NAME).pdf 

#---------------------------------------------------------------------
# depends on
#   TEXFILES.tex
$(NAME).pdf: $(addsuffix .tex, $(TEXFILES))

#---------------------------------------------------------------------
subdirs: $(SUBDIRS)

# only clean is passed to makefiles in subdirs 
ifeq ($(MAKECMDGOALS),clean)
$(SUBDIRS):
	$(MAKE) -C $@ clean
else
$(SUBDIRS):
	$(MAKE) -C $@
endif
#---------------------------------------------------------------------


clean: subdirs
	rm -f *.pdf
	rm -f *.ps
	rm -f *.dvi
	rm -f *.pdftex
	rm -f *.pdftex_t
	rm -f *.pstex
	rm -f *.pstex_t
	rm -f *.log
	rm -f *.aux
	rm -f *.lof
	rm -f *.toc
	rm -f *.bbl
	rm -f *.blg
	rm -f *.cb
	rm -f *.idx
	rm -f *.ilg
	rm -f *.ind
	rm -f *.lot
	rm -f *.out
	rm -f *.snm
	rm -f *.nav
	rm -f *.vrb
	rm -f *.bak
	rm -f *.soc
	rm -f *~
