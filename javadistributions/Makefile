build:
	@rm -rf ./src
	@rm -rf ./classes
	@rm -rf ./javadistributions.jar
	cp -r /home/nico/NetBeansProjects/NetLogo/src .
	cp -r /home/nico/NetBeansProjects/NetLogo/build/classes .
	jar cvfm javadistributions.jar manifest.txt -C classes .
