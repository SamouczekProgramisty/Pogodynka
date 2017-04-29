#!/bin/bash

CONFIGURATION_DIR=/opt/configuration/pogodynka

apt-get update
apt-get install puppet libssl-dev git -y

git clone https://github.com/SamouczekProgramisty/Pogodynka.git $CONFIGURATION_DIR

cd $CONFIGURATION_DIR/puppet
puppet apply --modulepath=$CONFIGURATION_DIR/puppet/modules/ manifests/site.pp
