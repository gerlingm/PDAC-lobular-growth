// 1a. Set the channel names and image type
setImageType('FLUORESCENCE');
setChannelNames(
     'Hoechst',
     'Hmga2',
     'tdTomato',
     'GATA6'
)

// 1b. Set the channel names and image type
// for the images taken without the tomato channel. In essence only for the conditioned medium images from plate 2.
setImageType('FLUORESCENCE');
setChannelNames(
     'Hoechst',
     'Hmga2',
     'GATA6'
)

// Cell detection "culture"
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage": "Hoechst",  "requestedPixelSizeMicrons": 0.4,  "backgroundRadiusMicrons": 6.0,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 1.8,  "minAreaMicrons": 30,  "maxAreaMicrons": 1500.0,  "threshold": 50.0,  "watershedPostProcess": true,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');






// Extra 1: remove (all!) detections to restart
clearDetections();

// Extra 2: Remove cell detections within specified annotation
def removal = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, getSelectedObject().getROI())
    .findAll { it.isDetection() }

removeObjects(removal, true)



