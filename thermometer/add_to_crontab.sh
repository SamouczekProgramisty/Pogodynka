#!/bin/bash

# don't worry that's a test URL I wouldn't use it "in production" ;)
healthcheck=https://hchk.io/89941a75-5e1a-4b0b-a864-59d584e579a8
command="java -jar /opt/pogodynka/thermometer-1.0-SNAPSHOT.jar && curl -fsS --retry 3 ${healthcheck}"

# check if command is in crontab, return value of script is stored in $?
crontab -l | grep "$command" > /dev/null

# when $? != 0 (-ne) that means that command wasn't added to crontab
if [ $? -ne 0 ]
then
  # list current crontab content and new line, set all this as new crontab
  (crontab -l; echo "*/5 * * * * ${command}") | crontab -
fi