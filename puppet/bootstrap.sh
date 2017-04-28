#!/bin/bash

CONFIGURATION_DIR=/opt/configuration/pogodynka

apt-get upgrade
apt-get install puppet libssl-dev git -y

git clone https://github.com/SamouczekProgramisty/Pogodynka.git $CONFIGURATION_DIR

puppet apply --modulepath=$CONFIGURATION_DIR/puppet -e "include pogodynka::node"
