makesite
========

This product allows pages to be written in handlebars/markdown format, and
hosted on Google App Engine.

maven
-----
This application was written using Netbeans with Maven, such that clicking Run
Project, in Netbeans causes all the files in the src/main/resources folder to 
be translated and transferred to target/website.  Maven outputs an executable 
jar that can be used elsewhere, with or without command line options.

command line options for source and destination directories
-----------------------------------------------------------
By default (when outside of the maven environment) makesite reads and writes 
the current directory.  Specifying a different directory on the command line 
changes the source directory.  -d directory can be used to change the 
destination directory.

creating pages
--------------
Simple create a file under src/main/resources with the appropriate file
extension and it will be translated to an HTML file and stored in a file with
a path name prefixed with target/website/... where the remainder of the name
matches the path under src/main/resources but with an .html file extension.

source file formats
-------------------

### Markdown .md .markdown
Files with extensions .md or .markdown are translated using Markdown.  The page
title is taken from the file name, or if the filename is 'index' the title is
taken from the name of the containing directory.  In the title all occurrences
of '-' are converted to spaces.

watch
-----
Starting makesite with the --watch option causes it to stay running after it's 
scanned all the directories, and then redo the publish process whenever files 
change in the source directory tree.

