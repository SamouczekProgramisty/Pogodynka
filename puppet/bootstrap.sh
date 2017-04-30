#!/bin/bash

# wget https://raw.githubusercontent.com/SamouczekProgramisty/Pogodynka/master/puppet/bootstrap.sh
# chmod +x bootstrap.sh
# ./bootstrap.sh

CONFIGURATION_DIR=/opt/configuration/pogodynka

apt-get update
apt-get install puppet libssl-dev git vim -y

git clone https://github.com/SamouczekProgramisty/Pogodynka.git $CONFIGURATION_DIR
cd $CONFIGURATION_DIR
git submodule init
git submodule update

puppet apply --modulepath=$CONFIGURATION_DIR/puppet/modules/ $CONFIGURATION_DIR/puppet/manifests/site.pp
