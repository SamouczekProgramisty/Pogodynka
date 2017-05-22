class pogodynka::misc {
  user {
    'pogodynka':
      managehome => true
  }

  file {
    '/var/log/pogodynka':
      ensure => 'directory',
      owner  => 'pogodynka',
      group  => 'pogodynka',
      mode   => '0775'
  }
}
