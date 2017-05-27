class pogodynka::misc {
  class {
    'timezone':
      timezone => 'UTC';
  }

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
