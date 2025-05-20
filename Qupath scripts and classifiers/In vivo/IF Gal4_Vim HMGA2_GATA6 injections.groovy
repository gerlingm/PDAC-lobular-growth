// A. For images stained with Gal4_Vim
// 1. Set the channel names and image type
setImageType('FLUORESCENCE');
setChannelNames(
     'DAPI',
     'Galectin-4',
     'Vimentin'
)

// 2. Cell detection
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage":"DAPI","requestedPixelSizeMicrons":-0.5,"backgroundRadiusMicrons":2.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":2.1,"minAreaMicrons":8.0,"maxAreaMicrons":100.0,"threshold":30.0,"watershedPostProcess":true,"cellExpansionMicrons":2.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')


// 3. Classification threshold on selected annotation for + or - staning for Gal4 and Vimentin
def hierarchy = getCurrentHierarchy()
def parent = getSelectedObject()
def cellObjects = hierarchy.getObjectsForROI(null, parent.getROI())
    .findAll { it.isDetection() }

coexp = getPathClass('Gal4_pos-Vim_pos')
negative = getPathClass('Gal4_neg-Vim_neg')
Gal4_pos = getPathClass('Gal4_pos-Vim_neg')
Vim_pos = getPathClass('Gal4_neg-Vim_pos')

for (cell in cellObjects) {
    ch1 = measurement(cell, 'Cell: Galectin-4 mean')
    ch2 = measurement(cell, 'Cytoplasm: Vimentin mean')
    if (ch1 > 350 & ch2 > 260)
        cell.setPathClass(coexp)        
    else if (ch1 > 350)
        cell.setPathClass(Gal4_pos) 
    else if (ch2 > 260)
        cell.setPathClass(Vim_pos) 
    else
        cell.setPathClass(negative)
}
fireHierarchyUpdate()

// B. Images stained with HMGA2_GATA6

// 1. Set the channel names and image type for HMGA2 GATA6 stains
setImageType('FLUORESCENCE');
setChannelNames(
     'DAPI',
     'HMGA2',
     'GATA6'
)

// 2. Cell detection (same)
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage":"DAPI","requestedPixelSizeMicrons":-0.5,"backgroundRadiusMicrons":2.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":2.1,"minAreaMicrons":8.0,"maxAreaMicrons":100.0,"threshold":30.0,"watershedPostProcess":true,"cellExpansionMicrons":2.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

// 3. Classification threshold on selected annotation for + or - staning for HMGA2 and GATA6
def hierarchy = getCurrentHierarchy()
def parent = getSelectedObject()
def cellObjects = hierarchy.getObjectsForROI(null, parent.getROI())
    .findAll { it.isDetection() }

coexp = getPathClass('HMGA2_pos-GATA6_pos')
negative = getPathClass('HMGA2_neg-GATA6_neg')
HMGA2_pos = getPathClass('HMGA2_pos-GATA6_neg')
GATA6_pos = getPathClass('HMGA2_neg-GATA6_pos')

for (cell in cellObjects) {
    ch1 = measurement(cell, 'Nucleus: HMGA2 mean')
    ch2 = measurement(cell, 'Nucleus: GATA6 mean')
    if (ch1 > 140 & ch2 > 540)
        cell.setPathClass(coexp)        
    else if (ch1 > 140)
        cell.setPathClass(HMGA2_pos) 
    else if (ch2 > 540)
        cell.setPathClass(GATA6_pos) 
    else
        cell.setPathClass(negative)
}
fireHierarchyUpdate()


// Extra: Remove all cell detections for current selection
def removal = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, getSelectedObject().getROI())
    .findAll { it.isDetection() }

removeObjects(removal, true)


// Same as what Annika used for the HMGA2

// Cell detection
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage":"DAPI","requestedPixelSizeMicrons":0.4,"backgroundRadiusMicrons":6.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":2.0,"minAreaMicrons":10.0,"maxAreaMicrons":500.0,"threshold":100.0,"watershedPostProcess":true,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

// Classification threshold on selected annotation for GATA6
def hierarchy = getCurrentHierarchy()
def parent = getSelectedObject()
def cellObjects = hierarchy.getObjectsForROI(null, parent.getROI())
    .findAll { it.isDetection() }

positive = getPathClass('GATA6: Positive')
negative = getPathClass('GATA6: Negative')

for (cell in cellObjects) {
    ch1 = measurement(cell, 'Nucleus: GATA6 mean')
    if (ch1 > 400)
        cell.setPathClass(positive) 
    else
        cell.setPathClass(negative)
}

// Classification threshold on selected annotation for Gal4
def hierarchy = getCurrentHierarchy()
def parent = getSelectedObject()
def cellObjects = hierarchy.getObjectsForROI(null, parent.getROI())
    .findAll { it.isDetection() }

positive = getPathClass('Gal4: Positive')
negative = getPathClass('Gal4: Negative')

for (cell in cellObjects) {
    ch1 = measurement(cell, 'Cell: Galectin-4 mean')
    if (ch1 > 250)
        cell.setPathClass(positive) 
    else
        cell.setPathClass(negative)
}
fireHierarchyUpdate()

