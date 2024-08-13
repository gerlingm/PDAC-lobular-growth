// 1. Set the channel names
setChannelNames(
     'DAPI',
     'Opal 570_p53',
     'Opal 690_CD74',
     'Opal 480_ASMA',
     'Opal 620_Vim',
     'Opal 780_IL-6',
     'Opal 520_NGFR',
     'Sample AF'
)

// 15 µm layering: 
runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons":15.0,"lineCap":"ROUND","removeInterior":false,"constrainToParent":true}')

// Helpful commands during creation of layers:
duplicateSelectedAnnotations()
mergeSelectedAnnotations()

// Cell detection 3i
setImageType('FLUORESCENCE');
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage": "DAPI",  "requestedPixelSizeMicrons": 0.4,  "backgroundRadiusMicrons": 6.0,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 1.8,  "minAreaMicrons": 10.0,  "maxAreaMicrons": 200.0,  "threshold": 40.0,  "watershedPostProcess": true,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

// Cleanup a. For stroma. Threshold out the cells that are too round, to increase chance of having fibroblasts and not immune /other cell types.
def toDelete = getDetectionObjects().findAll {measurement(it, 'Nucleus: Circularity') > 0.8}
removeObjects(toDelete, true)

// Cleanup b. For lobes
def toDelete = getDetectionObjects().findAll {measurement(it, 'Nucleus: Circularity') > 0.6}
removeObjects(toDelete, true)

//Run the composite classifier. Both with p53 positivity(nuclear) and all the stroma stains (cytoplasmic)
runObjectClassifier("Stroma_tumor_composite")

