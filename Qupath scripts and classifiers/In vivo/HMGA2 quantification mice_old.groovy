// Quantification of HMGA2 signal of tumor cells in mice within manually drawn annotations

// Set channel names
setChannelNames('DAPI', 'HMGA2')

// Cell detection
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage":"DAPI","requestedPixelSizeMicrons":0.4,"backgroundRadiusMicrons":6.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":2.0,"minAreaMicrons":10.0,"maxAreaMicrons":500.0,"threshold":100.0,"watershedPostProcess":true,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

// 7a. Classification threshold on selected annotation for + or - staning of HMGA2
def hierarchy = getCurrentHierarchy()
def parent = getSelectedObject()
def cellObjects = hierarchy.getObjectsForROI(null, parent.getROI())
    .findAll { it.isDetection() }

positive = getPathClass('HMGA2: Positive')
negative = getPathClass('HMGA2: Negative')

// Set the color manually, using a packed RGB value
Pcolor = getColorRGB(250, 0, 210)
Ncolor = getColorRGB(128, 128, 128)
positive.setColor(Pcolor)
negative.setColor(Ncolor)

for (cell in cellObjects) {
    ch1 = measurement(cell, 'Nucleus: HMGA2 mean')
    if (ch1 > 280)
        cell.setPathClass(positive) 
    else
        cell.setPathClass(negative)
}
fireHierarchyUpdate()