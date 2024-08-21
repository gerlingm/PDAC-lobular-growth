
// 1a. Set image type and channel names. K19_Amy staining
setImageType('FLUORESCENCE');
setChannelNames(
     'DAPI',
     'AF488_K19',
     'AF647_Amy'
)

// 1b. Set image type and channel names. p53_REG3A staining
setImageType('FLUORESCENCE');
setChannelNames(
     'DAPI',
     'AF488_REG3A',
     'AF647_p53'
)

// Cell detection 3i
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage": "DAPI",  "requestedPixelSizeMicrons": 0.4,  "backgroundRadiusMicrons": 6.0,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 1.8,  "minAreaMicrons": 10.0,  "maxAreaMicrons": 200.0,  "threshold": 40.0,  "watershedPostProcess": true,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

// Remove all cell detections for current selection
def removal = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, getSelectedObject().getROI())
    .findAll { it.isDetection() }

removeObjects(removal, true)



