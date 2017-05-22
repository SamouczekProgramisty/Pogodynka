node 'debian.p5.tiktalik.io' {
  include pogodynka::hiera_setup
  include pogodynka::node_datavault
}

node 'raspberrypi' {
  include pogodynka::hiera_setup
  include pogodynka::node_thermometer
}
