//1. Set image type and Cell detection "3i"
setImageType('FLUORESCENCE');
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage": "DAPI (C1)",  "requestedPixelSizeMicrons": 0.4,  "backgroundRadiusMicrons": 6.0,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 1.8,  "minAreaMicrons": 10.0,  "maxAreaMicrons": 200.0,  "threshold": 40.0,  "watershedPostProcess": true,  "cellExpansionMicrons": 5.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

//2. Classify cells as PanCK+ or -
def hierarchy = getCurrentHierarchy()
def parent = getSelectedObject()
def cellObjects = hierarchy.getObjectsForROI(null, parent.getROI())
    .findAll { it.isDetection() }

positive = getPathClass('PanCK_pos')
negative = getPathClass('PanCK_neg')

for (cell in cellObjects) {
    ch1 = measurement(cell, 'Cell: PanCK (C7) mean') // may need to change between removing and keeing the (C7) part
    if (ch1 > 40)
        cell.setPathClass(positive) 
    else
        cell.setPathClass(negative)
}
fireHierarchyUpdate()

//3. Remove PanCK+ cells 
def removal = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, getSelectedObject().getROI())
    .findAll { it.isDetection() &&  it.getPathClass().toString().contains("PanCK_pos")}

removeObjects(removal, true)

//4. Remove cells with low nuclear circularity
def toDelete = getDetectionObjects().findAll {measurement(it, 'Nucleus: Circularity') < 0.54}
removeObjects(toDelete, true)

//5. Run the composite classifier 
runObjectClassifier("Immune composite classifier_cutoffs45")



// Extra - not run: Remove all cell detections for current selection
def removal = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, getSelectedObject().getROI())
    .findAll { it.isDetection() }

removeObjects(removal, true)
