// Useful script snippets used in Cell neighbourhood analysis - NGFR intensity within 50 µm of acinar or ADM cells.

// Select annotations by its class (change accordingly; acinar or ADM), Make cell detection within (corresponding to 3e), select cells and measure intensity from the 50 µm expanded area of each detected cell.
selectObjectsByClassification("acinar");
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.3,  "backgroundRadiusMicrons": 4.3,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 2.1,  "minAreaMicrons": 26.0,  "maxAreaMicrons": 500.0,  "threshold": 0.035,  "maxBackground": 2.6,  "watershedPostProcess": true,  "excludeDAB": false,  "cellExpansionMicrons": 7.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}')
selectCells();
runPlugin('qupath.lib.algorithms.IntensityFeaturesPlugin', '{"pixelSizeMicrons":0.1,"region":"CIRCLE","tileSizeMicrons":50.0,"colorOD":false,"colorStain1":false,"colorStain2":true,"colorStain3":true,"colorRed":false,"colorGreen":false,"colorBlue":false,"colorHue":false,"colorSaturation":false,"colorBrightness":false,"doMean":true,"doStdDev":true,"doMinMax":true,"doMedian":true,"doHaralick":false,"haralickDistance":1,"haralickBins":32}')
detectionToAnnotationDistances(false)


// 3. Classify the selected objects (cell detections) according to parental ROI
// Select first the parental ROI in question

tumorAnnotations = getAnnotationObjects().findAll{it.getPathClass() == getPathClass("stroma 6")} //get the path class of parental ROI
tumorAnnotations.each{anno->
    tumorCells = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, anno.getROI())
}

tumorCells.each{cell->
    getCurrentHierarchy().getSelectionModel().setSelectedObject(cell, true);
}

def newPathClass = getPathClass("stroma 6") // Here change to the correct classification!
getSelectedObjects().forEach {
    it.setPathClass(newPathClass)
}
print "Done!"

// 6a. Remove all cell detections for current selection
def removal = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, getSelectedObject().getROI())
    .findAll { it.isDetection() }

removeObjects(removal, true)
