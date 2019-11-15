# Web site for GeoAPI

This branch contains the HTML pages for http://www.geoapi.org/ web site.
The pages are generated by the commands shown below.


## Prerequites

* Git
* [Python 3](https://www.python.org/)
* [Sphinx](http://www.sphinx-doc.org/) for Python 3


### Checkout branches

Following commands need to be executed only once
(directory names and locations can be different if desired):

```
git clone https://github.com/opengeospatial/geoapi master
cd master
git worktree add ../3.1.x 3.1.x
git worktree add ../site gh-pages
```


## Generating the web site

Execute the following command in the directory containing this `gh-pages` branch:

```
export GEOAPI_SITE=`pwd`
```

Execute the following commands in the directory containing the GeoAPI 3.1.x branch:

```
mvn clean install javadoc:javadoc
```

Execute the following commands in the directory containing the GeoAPI master branch:

```
sphinx-build-3 -b html src/site/sphinx $GEOAPI_SITE/snapshot/python
```


### Manual modifications

Move Javadoc to `snapshot` directory for making clear that they are not yet approved.
In addition of following commands, search and replace all occurrences of `apidocs` by
`../snapshot/javadoc` in URLs of the `geoapi` subdirectory.

```
cd $GEOAPI_SITE
mv geoapi/apidocs/* snapshot/javadoc/
rmdir geoapi/apidocs
```

In Python documentation, remove the underscore in `_static` and `_images` directory names.
This will again require manual replacements of those words in the URL of all HTML pages.

Finally verify if there is some old files that should be removed.
The following example list files older than 10 days.
Be careful to not delete files that are not generated by above Maven commands,
in particular all the files in the directories for older versions.

```
find -type f -mtime +10
find -type d -empty -delete
```

Review differences:

```
git diff --ignore-all-space
```

If satisfying, commit and push.
