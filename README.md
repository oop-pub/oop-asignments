# Homeworks

This repository contains (and is updated each university year):

 * content of each homework with detailed explanations of the assignments and automated checkers
 * media, such as photos or diagrams, that are used to better portrait examples as well as sources (where need-be - e.g. the UML diagrams can be exported to XML from [draw.io](https://www.draw.io/))
 * skeletons for homeworks which require a starting point

## Repository folder structure

The structure for a current homework is as follows:
```
teme
|
|	--->	homework*X*-name  [1]
|		|	--->	checker  [2]
|		|	--->	media [3]
|		|	--->	skel  [4]
|		|		|	---> image-source [5]
|		|	homework*X*-name  [6]
|	--->	...
|	--->	...
```

The structure for an old or deprecated homework is as follows:
```
old
|	--->	university_year [7]
|		|	--->	old_homework*X*-name  [1]
|		|		|	--->	checker  [2]
|		|		|	--->	media	[3]
|		|		|	--->	skel	[4]
|		|		|		|	---> image-source [5]
|		|		|	old_homework*X*-name  [6]
|		|	--->	...
|		|	--->	...
|	--->	...
|	--->	...
```

* **[1]** - folder with the homework name and contents (where *X* is the homework number)
* **[2]** - folder with the checker [if need-be]
* **[3]** - folder with images and diagrams [if need-be]
* **[4]** - folder with the skeleton code [if need-be]
* **[5]** - folder with sources of the images/diagrams [if need-be]
* **[6]** - file with homework details and exercises - docuwiki code (**must** be the same as the folder name)
* **[7]** - folder with the university year in which the homework was due

## Contributing

If you are interested in fixing issues, detailing laboratory content or just want to lend a helping hand,
please see the document [How to Contribute](CONTRIBUTING.md), which covers the following:

* [Coding Guidelines](CONTRIBUTING.md#coding-guidelines)
* [Submitting pull requests](CONTRIBUTING.md#pull-requests)

This organization has adopted the [Ethics regulation](http://wiki.cs.pub.ro/_media/studenti/licenta/regulament-comisie-de-etica-upb.pdf) and the [Code of Conduct for Students from the Faculty of Automation and Computers](http://wiki.cs.pub.ro/_media/studenti/licenta/cod-conduita-studenti-acs-.pdf). Failure to comply with or violate the rules set out in any of these documents will result in the sanctions imposed by UPB's internal regulations, such as reprimand, written warning, or expulsion from the University POLITEHNICA of Bucharest.

## Feedback

* Ask a question on [the Moodle instance of our university](http://cs.curs.pub.ro/).
* Request a new feature on [GitHub](CONTRIBUTING.md).
* File a bug in [GitHub Issues](https://github.com/oop-pub/teme/issues).

## Related repositories

* [Laboratories](https://github.com/oop-pub/laboratoare) - content related to laboratories
* [Wiki](https://github.com/oop-pub/wiki) - content from the [docuwiki instance](http://elf.cs.pub.ro/poo/)

## License

Licensed under the [MIT](LICENSE) License.


