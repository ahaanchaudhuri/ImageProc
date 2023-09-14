NEW README:

We were able to implement the image mosaicing, but because the providers load function only seems to work with the image of the crab in the res folder, when running the Jar, you must load in the crab image.
We did implement the script command for the mosaicking but the providers did not configure their main class to include script commands (as they incdicated in their questionarre) so it does not work on the JAR.

For implementing the mosaicing we did the following:
1) Created the posn class which has a x and y param (this is used for the "x" and "y" in the array of pixels.
2.) Created a Seed class which represents any singular seed. The seed has an array list of the positions of the other pixels which are in its "cluster". (A pixel being in the cluster means that the seed was the closest seed to the pixel)
		-The cluster is set to empty and is populated in the Mosaic class.
3.) we implemented the Transform interface with a Mosaic class. This class takes care of all the logic behind mosaicing an Image. It is in line with the design of the other classes that implemented transform (brighten flip etc.)
4.) we has to add a mosaic method to the model which delegates to our mosaic class. Again this is in line with the other functions.
5.) We updated the GUI View to add a new button for Mosaicing.
6.) we updated the GUI controller and added a new case for the case that the Mosaic button was pressed.
7.) We updated the PPMController (this seems to be the controller for text command) and added the case that Mosaic was scripted.

NOTE-- We went into depth on this in the code review but there are many functionality issues with the code including that the load function does not seem to work with anything other that the Crab image in the res folder.
		This is the image that we used for the 2 mosaiced images. If the rest of he program functioned as it was supposed to, essentially our mosaic function works perfectly.

NOTE2: crab2 is a mosaic of crab with 8000 seeds, crab500seeds.png is mosaic with 500 seeds. These are both located in the res folder.