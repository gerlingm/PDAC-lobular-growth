// 1. Set staining vectors
setImageType('BRIGHTFIELD_OTHER');
setColorDeconvolutionStains('{"Name" : "HE-DAB-AP", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049 ", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759 ", "Stain 3" : "AP", "Values 3" : "0.13596 0.83776 0.52885 ", "Background" : " 255 255 255 "}');

// 1 b. Set staining vectors
setImageType('BRIGHTFIELD_OTHER');
setColorDeconvolutionStains('{"Name" : "HE-DAB-AP", "Stain 1" : "Hematoxylin", "Values 1" : "0.455 0.759 0.466", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759 ", "Stain 3" : "AP", "Values 3" : "0.183 0.797 0.576", "Background" : " 235 232 240 "}');

// 1 c. Set staining vectors, PKR-58 Muc5
setImageType('BRIGHTFIELD_OTHER');
setColorDeconvolutionStains('{"Name" : "HE-DAB-AP", "Stain 1" : "Hematoxylin", "Values 1" : "0.478 0.65 0.591", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759 ", "Stain 3" : "AP", "Values 3" : "0.183 0.797 0.576", "Background" : " 235 232 240 "}');



// 3. Cell detection

//: 3c Default
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.4,  "backgroundRadiusMicrons": 6.0,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 2,  "minAreaMicrons": 10.0,  "maxAreaMicrons": 500.0,  "threshold": 0.06,  "maxBackground": 2.5,  "watershedPostProcess": true,  "excludeDAB": true,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

//: 3d
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.3,  "backgroundRadiusMicrons": 4.3,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 2.1,  "minAreaMicrons": 10.0,  "maxAreaMicrons": 500.0,  "threshold": 0.04,  "maxBackground": 2.6,  "watershedPostProcess": true,  "excludeDAB": false,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

//: 3e
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.3,  "backgroundRadiusMicrons": 4.3,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 2.1,  "minAreaMicrons": 26.0,  "maxAreaMicrons": 500.0,  "threshold": 0.035,  "maxBackground": 2.6,  "watershedPostProcess": true,  "excludeDAB": false,  "cellExpansionMicrons": 7.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

//: 3f 
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.3,  "backgroundRadiusMicrons": 4.3,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 2.1,  "minAreaMicrons": 26.0,  "maxAreaMicrons": 500.0,  "threshold": 0.038,  "maxBackground": 2.6,  "watershedPostProcess": true,  "excludeDAB": true,  "cellExpansionMicrons": 7.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

//: 3g
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.3,  "backgroundRadiusMicrons": 4.1,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 2.1,  "minAreaMicrons": 25.0,  "maxAreaMicrons": 550.0,  "threshold": 0.007,  "maxBackground": 3,  "watershedPostProcess": true,  "excludeDAB": false,  "cellExpansionMicrons": 6.8,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

//: 3h
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.4,  "backgroundRadiusMicrons": 6.0,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 2,  "minAreaMicrons": 10.0,  "maxAreaMicrons": 500.0,  "threshold": 0.025,  "maxBackground": 2.5,  "watershedPostProcess": true,  "excludeDAB": true,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');


// 7a. Classification threshold on selected annotation for + or - staning (DAB or AP)
def hierarchy = getCurrentHierarchy()
def parent = getSelectedObject()
def cellObjects = hierarchy.getObjectsForROI(null, parent.getROI())
    .findAll { it.isDetection() }

positive = getPathClass('Tumor: Positive')
negative = getPathClass('Tumor: Negative')

for (cell in cellObjects) {
    ch1 = measurement(cell, 'Nucleus: DAB OD mean')
    if (ch1 > 0.32)
        cell.setPathClass(positive) 
    else
        cell.setPathClass(negative)
}
fireHierarchyUpdate()

