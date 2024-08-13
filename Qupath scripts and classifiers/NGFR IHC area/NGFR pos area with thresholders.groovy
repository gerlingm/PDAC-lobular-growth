// Quantification of NGFR+ area in lobules with four different classes

// Set staining vectors (Correpsonding to option 1b in IHC spatially dependent heterogeneity)
setImageType('BRIGHTFIELD_OTHER');
setColorDeconvolutionStains('{"Name" : "HE-DAB-AP", "Stain 1" : "Hematoxylin", "Values 1" : "0.455 0.759 0.466 ", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759 ", "Stain 3" : "AP", "Values 3" : "0.183 0.797 0.576", "Background" : " 235 232 240 "}');

// Measure NGFR+ area with defined classifiers. Run with classifiers optimised to the each tumor ID, modify classifier names below accordingly.
selectObjectsByClassification("Unaffected");
addPixelClassifierMeasurements("ngfr_thresh_0.43_pkr22", "ngfr_thresh_0.43_pkr22")
selectObjectsByClassification("Pancreatitis");
addPixelClassifierMeasurements("ngfr_thresh_0.43_pkr22", "ngfr_thresh_0.43_pkr22")
selectObjectsByClassification("Invasion");
addPixelClassifierMeasurements("ngfr_thresh_0.43_pkr22", "ngfr_thresh_0.43_pkr22")
selectObjectsByClassification("LateInvasion");
addPixelClassifierMeasurements("ngfr_thresh_0.49_pkr2", "ngfr_thresh_0.49_pkr2")

print "Done!"