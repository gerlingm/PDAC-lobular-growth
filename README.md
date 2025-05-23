Tested with R 4.1.1 and R 4.1.2 for Mac but should run without problems on other R versions and OS. Each script was re-run and updated on macOS 15.4.1 with R version 4.4.1. 

To run a script, please download the script file stored in a folder, and the related read in-file. All quantitative data, the read in-files,  are in the folder "source data files to read in" with two exceptions:
 - The data for "ML tumor interactions and cell-based NGFR intensity" is located in the /data folder within.
 - Due to size limitations, source data for the scRNAseq analysis can't be uploaded to Github (also true for output data that is generated by the script). We here provide a html and .Rmd script, and refer to the given repositories for access of the scRNA seq data.

The aim of this computational pipeline is to combine and explore pathological annotations and associated data from pancreatic cancer.

This readme file and the repository will be updated constantly and finalized upon acceptance of an associated scientific manuscript, which will present the expected output. 
Expect install time and run time is 4 hours, excluding installing dependencies. To install R, please follow the guides provided by The Comprehensive R Archive Network (CRAN) which suits your location and operating system: https://cran.r-project.org/mirrors.html. The download and basic usage of Rstudio is described at: https://rstudio-education.github.io/hopr/starting.html 

For scRNA-seq analysis, the estimated run time may longer, in particular if packages need updating or installing.

To install all packages needed for all scripts to run, please run the separate script called "Install all required R libraries".
