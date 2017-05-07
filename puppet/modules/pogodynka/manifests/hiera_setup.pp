class pogodynka::hiera_setup {
  file {
    '/etc/puppet/hiera.yaml':
      source => 'puppet:///modules/pogodynka/hiera.yaml';
  }
}
