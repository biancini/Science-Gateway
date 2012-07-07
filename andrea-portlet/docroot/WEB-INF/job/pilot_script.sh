#!/bin/sh 
#
# mi-custom - portlet pilot script
#
# Hostname Grid job can be considered the equivalent of the 'hello world' code
# of computer programming languages.
#
# The following script does:
#   o The hostname
#   o The execution start/end dates
#   o Listing of the worker node' $HOME directory
#   o Listing of the worker node' $PWD current directory
#   o Shows the input file
#   o Simulates the creation of an output file  
# 
# It is kindly suggested to keep all informative echoes
# below; they will help developers and system administrators
# to identify possible troubles
#
# Author: riccardo.bruno@ct.infn.it
#

#
# Multi-infrastructure job submission needs
# to build some environment variables
# if the application needs a sw directory
# set and uncomment the SW_NAME value
# then enable code lines related to PATH and
# LD_LIBRARY_PATH settings 
# hostname example does not require to define
# library and path directories
#
#SW_NAME="MyAppDir" # Place here the software dir name and uncomment it
VO_NAME=$(voms-proxy-info -vo)
VO_VARNAME=$(echo $VO_NAME | sed s/"\."/"_"/g | sed s/"-"/"_"/g | awk '{ print toupper($1) }')
VO_SWPATH_NAME="VO_"$VO_VARNAME"_SW_DIR"
VO_SWPATH_CONTENT=$(echo $VO_SWPATH_NAME | awk '{ cmd=sprintf("echo $%s",$1); system(cmd); }')

echo "Multi infrastructure variables:"
echo "-------------------------------"
echo "VO_NAME          : "$VO_NAME
echo "VO_VARNAME       : "$VO_VARNAME
echo "VO_SWPATH_NAME   : "$VO_SWPATH_NAME
echo "VO_SWPATH_CONTENT: "$VO_SWPATH_CONTENT
#
# Assign PATH and LD_LIBRARY_PATH
#
# You may assign VO specific values uncommenting the
# lines below
#
#case $VO_NAME in
#    'prod.vo.eu-eela.eu')
#    export PATH=$PATH:$VO_SWPATH_CONTENT/$SW_NAME/bin
#    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$VO_SWPATH_CONTENT/$SW_NAME/lib
#    ;;
#    'cometa')
#    export PATH=$PATH:$VO_SWPATH_CONTENT/$SW_NAME/bin
#    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$VO_SWPATH_CONTENT/$SW_NAME/lib    
#    ;;
#    'eumed')
#    export PATH=$PATH:$VO_SWPATH_CONTENT/$SW_NAME/bin
#    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$VO_SWPATH_CONTENT/$SW_NAME/lib
#    ;;
#    *)
#    echo "ERROR: Unsupported VO '"$VO_NAME"'"
#    exit 1
#esac
#
# Otherwise use a common setting
#
#export PATH=$PATH:$VO_SWPATH_CONTENT/$SW_NAME/bin
#export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$VO_SWPATH_CONTENT/$SW_NAME/lib
#echo
#echo "PATH and LD_LIBRARY_PATH:"
#echo "-------------------------"
#echo "PATH: "$PATH
#echo "LD_LIBRARY_PATH: "$LD_LIBRARY_PATH
# Check if the software directory exists
#echo
#echo "Software directory : '"$VO_SWPATH_CONTENT/$SW_NAME"'"
#echo "------------------"
#ls -ld $VO_SWPATH_CONTENT/$SW_NAME
#echo

# Get the hostname
HOSTNAME=$(hostname -f)

# In order to avoid concurrent accesses to files, the 
# portlet uses filename prefixes like
# <timestamp>_<username>_filename
# for this reason the file must be located before to use it
INFILE=$(ls -1 | grep input_file.txt)

echo "--------------------------------------------------"
echo "Job landed on: '"$HOSTNAME"'"
echo "--------------------------------------------------"
echo "Job execution starts on: '"$(date)"'"

echo "---[WN HOME directory]----------------------------"
ls -l $HOME

echo "---[WN Working directory]-------------------------"
ls -l $(pwd)

echo "---[Macro file]---------------------------------"
cat $INFILE
echo

#
# Following statement simulates a produced job file
#
OUTFILE=hostname_output.txt
echo "--------------------------------------------------"  > $OUTFILE
echo "Job landed on: '"$HOSTNAME"'"                       >> $OUTFILE
echo "infile:  '"$INFILE"'"                               >> $OUTFILE
echo "outfile: '"$OUTFILE"'"                              >> $OUTFILE
echo "--------------------------------------------------" >> $OUTFILE
echo ""                                                   >> $OUTFILE

# Producing an output file 
cat $INFILE >> $OUTFILE

#
# At the end of the script file it's a good practice to 
# collect all generated job files into a single tar.gz file
# the generated archive may include the input files as well
#
tar cvfz mi-custom-Files.tar.gz $INFILE $OUTFILE

