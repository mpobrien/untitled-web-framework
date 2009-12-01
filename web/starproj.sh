#!/bin/sh

global_settings_dir=/home/mike/projects/globalsettings
projectname=$1
echo $1

mkdir -p $projectname/lib
mkdir -p $projectname/conf
mkdir -p $projectname/tools
mkdir -p $projectname/src
mkdir -p $projectname/WebContent/WEB-INF/
cp $global_settings_dir/lib/*.jar $projectname/lib
cp $global_settings_dir/build.xml $projectname
cp $global_settings_dir/log4j.properties $projectname/conf
cp $global_settings_dir/settings.properties $projectname

sed -i 's/!!!PROJECT_NAME!!!/testproject/g' $projectname/build.xml 



