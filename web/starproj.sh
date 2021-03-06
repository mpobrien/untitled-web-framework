#!/bin/sh

global_settings_dir=/home/mike/projects/untitled-web-framework/globalsettings
projectname=$1
echo $1

mkdir -p $projectname/lib
mkdir -p $projectname/conf
mkdir -p $projectname/tools
mkdir -p $projectname/src/$projectname/ctrlrs
mkdir -p $projectname/src/$projectname/modules
mkdir -p $projectname/src/$projectname/models
mkdir -p $projectname/WebContent/WEB-INF/
mkdir -p $projectname/WebContent/static
mkdir -p $projectname/templates
cp $global_settings_dir/lib/*.jar $projectname/lib
cp $global_settings_dir/build.xml $projectname
cp $global_settings_dir/log4j.properties $projectname/conf
cp $global_settings_dir/settings.properties $projectname/conf

cp $global_settings_dir/HomeController.java $projectname/src/$projectname/ctrlrs
#TODO /modules is a bad place for this
cp $global_settings_dir/MyServletContextListener.java $projectname/src/$projectname/modules
cp $global_settings_dir/MainServletModule.java $projectname/src/$projectname/modules
cp $global_settings_dir/web.xml $projectname/WebContent/WEB-INF
cp $global_settings_dir/admin.sh $projectname/
cp $global_settings_dir/index.html $projectname/templates

sed -i "s/!!!PROJECT_NAME!!!/$projectname/g" $projectname/build.xml 
sed -i "s/!!!PROJECT_NAME!!!/$projectname/g" $projectname/src/$projectname/ctrlrs/HomeController.java
sed -i "s/!!!PROJECT_NAME!!!/$projectname/g" $projectname/src/$projectname/modules/MyServletContextListener.java
sed -i "s/!!!PROJECT_NAME!!!/$projectname/g" $projectname/src/$projectname/modules/MainServletModule.java
sed -i "s/!!!PROJECT_NAME!!!/$projectname/g" $projectname/WebContent/WEB-INF/web.xml
sed -i "s/!!!PROJECT_NAME!!!/$projectname/g" $projectname/admin.sh



