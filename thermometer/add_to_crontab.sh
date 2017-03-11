#!/bin/bash

# don't worry those are test settings, all it will be different on "production" environment ;)
username=dummyUsername
password=dummyPassword
datasink=http://www.samouczekprogramisty.pl/getrealaddress
healthcheck=https://hchk.io/89941a75-5e1a-4b0b-a864-59d584e579a8

measure_temperature="java -jar /opt/pogodynka/thermometer-1.0-SNAPSHOT.jar ${username} ${password} ${datasink} && curl -fsS --retry 3 ${healthcheck}"

# check if command is in crontab, return value of script is stored in $?
crontab -l | grep "${measure_temperature}" > /dev/null

# when $? != 0 (-ne) that means that command wasn't added to crontab
if [ $? -ne 0 ]
then
  # list current crontab content and new line, set all this as new crontab
  (crontab -l; echo "*/5 * * * * ${measure_temperature}") | crontab -
fi


rm_logs="find /var/log/pogodynka/*.log -mtime +7 -exec rm {} \;"
crontab -l | grep "/var/log/pogodynka/\*.log" > /dev/null
if [ $? -ne 0 ]
then
  (crontab -l; echo "1 0 * * * ${rm_logs}") | crontab -
fi