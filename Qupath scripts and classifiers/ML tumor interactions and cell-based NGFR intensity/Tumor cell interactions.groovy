// 1 Subtract all the annotation in hierarchy on their parental annotation and keep the resulting annotations.
import qupath.lib.roi.*
import qupath.lib.objects.*

classToSubtract = null
    
def topLevel = getObjects{return it.getLevel()==1 && it.isAnnotation()}
println(topLevel)
for (parent in topLevel){

    def total = []
    def polygons = []
    subtractions = parent.getChildObjects().findAll{it.isAnnotation() }
    println(subtractions)
    for (subtractyBit in subtractions){
        if (subtractyBit instanceof AreaROI){
           subtractionROIs = RoiTools.splitAreaToPolygons(subtractyBit.getROI())
           total.addAll(subtractionROIs[1])
        } else {total.addAll(subtractyBit.getROI())}              
                
    }     
    if (parent instanceof AreaROI){
        polygons = RoiTools.splitAreaToPolygons(parent.getROI())
        total.addAll(polygons[0])
    } else { polygons[1] = parent.getROI()}

            
    def newPolygons = polygons[1].collect {
    updated = it
    for (hole in total)
         updated = RoiTools.combineROIs(updated, hole, RoiTools.CombineOp.SUBTRACT)
         return updated
    }
                // Remove original annotation, add new ones
    annotations = newPolygons.collect {new PathAnnotationObject(updated, parent.getPathClass())}


    addObjects(annotations)

//    removeObjects(subtractions, true)
    removeObject(parent, true)
}
print "done"


// 2a. Set staining vectors before cell detection (Corresponding to option 1b in IHC-heterogeneity analysis)
setImageType('BRIGHTFIELD_OTHER');
setColorDeconvolutionStains('{"Name" : "HE-DAB-AP", "Stain 1" : "Hematoxylin", "Values 1" : "0.455 0.759 0.466 ", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759 ", "Stain 3" : "AP", "Values 3" : "0.183 0.797 0.576", "Background" : " 235 232 240 "}');

//: 2b Run cell detection on tumor annotations (Corresponding to Cell detection option 3e in IHC-heterogeneity analysis)
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield": "Hematoxylin OD",  "requestedPixelSizeMicrons": 0.3,  "backgroundRadiusMicrons": 4.3,  "medianRadiusMicrons": 0.0,  "sigmaMicrons": 2.1,  "minAreaMicrons": 26.0,  "maxAreaMicrons": 500.0,  "threshold": 0.035,  "maxBackground": 2.6,  "watershedPostProcess": true,  "excludeDAB": false,  "cellExpansionMicrons": 7.0,  "includeNuclei": true,  "smoothBoundaries": true,  "makeMeasurements": true}');

// 3. Classify the selected objects (cell detections) according to parental ROI, which enable tracing of the ROI origin in subsequent data analysis. 
// Select first the parental ROI in question

tumorAnnotations = getAnnotationObjects().findAll{it.getPathClass() == getPathClass("stroma 4")} //get the path class of parental ROI
tumorAnnotations.each{anno->
    tumorCells = getCurrentHierarchy().getObjectsForROI(qupath.lib.objects.PathDetectionObject, anno.getROI())
}

tumorCells.each{cell->
    getCurrentHierarchy().getSelectionModel().setSelectedObject(cell, true);
}

def newPathClass = getPathClass("stroma 4") // Here change to the correct classification!
getSelectedObjects().forEach {
    it.setPathClass(newPathClass)
}
print "Done!"