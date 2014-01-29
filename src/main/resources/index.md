makesite
========

This product allows pages to be written in handlebars/markdown format, and
hosted on Google App Engine.

maven
-----
This application was written using Netbeans with Maven, such that clicking Run
Project, in Netbeans causes all the files in the src/main/resources folder to 
be translated and transferred to target/website.

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