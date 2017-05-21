class pogodynka::database {
  package {
    'locales-all':
      ensure => 'latest';
  } ->
  class {
    'postgresql::server':
      listen_addresses  => 'localhost',
      locale            => 'pl_PL.UTF-8',
      log_line_prefix   => '%t [%p-%l] %q%u@%d ',
      postgres_password => hiera('password_postgresql_postgres')
  }

  postgresql::server::db {
    'pogodynka_db':
      user     => 'pogodynka_admin',
      password => postgresql_password('pogodynka_admin', hiera('password_postgresql_pogodynka_admin')),
  }

  postgresql::server::role {
    'pogodynka_user':
      password_hash => postgresql_password('pogodynka_user', hiera('password_postgresql_pogodynka_user'));
  }

  postgresql::server::database_grant {
    'pogodynka_db_connect':
      privilege => 'CONNECT',
      db        => 'pogodynka_db',
      role      => 'pogodynka_user';
  }
}
