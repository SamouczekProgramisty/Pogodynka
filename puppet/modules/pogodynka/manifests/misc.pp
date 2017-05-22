class pogodynka::misc {
  user {
    'pogodynka':
      ensure     => 'present',
      managehome => true;
  }

  file {
    '/var/log/pogodynka':
      ensure  => 'directory',
      owner   => 'pogodynka',
      group   => 'pogodynka',
      mode    => '0775',
      require => User['pogodynka'];
  }
}
