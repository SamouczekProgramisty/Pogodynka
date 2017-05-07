class pogodynka::node {
  include apt
  include pogodynka::java
  include firewall
  include pogodynka::firewall::pre
  include pogodynka::firewall::post

  Firewall {
    before  => Class['pogodynka::firewall::post'],
    require => Class['pogodynka::firewall::pre']
  }

  firewall {
    '099 forward port 80 to 8080':
      table       => 'nat',
      chain       => 'PREROUTING',
      proto       => 'tcp',
      dport       => '80',
      jump        => 'REDIRECT',
      toports     => '8080'
  }

  package {
    'ntp':
      ensure => 'latest';
  }

  $catalina_home = '/opt/tomcat8.5'
  $catalina_base = "${catalina_home}/production"

  tomcat::install {
    '/opt/tomcat8.5':
      source_url => 'http://mirror.23media.de/apache/tomcat/tomcat-8/v8.5.14/bin/apache-tomcat-8.5.14.tar.gz';
  }

  tomcat::instance {
    'tomcat8.5-production':
      catalina_home => $catalina_home,
      catalina_base => $catalina_base;
  }

  # tomcat::config::server {
  #   'tomcat8.5-production':
  #     catalina_base => $catalina_base,
  #     port => '8080';
  # }

  # tomcat::config::server::connector {
  #   'tomcat8.5-production':
  #     catalina_base         => $catalina_base,
  #     port                  => '80',
  #     protocol              => 'HTTP/1.1',
  #     additional_attributes => {
  #       'redirectPort' => '443'
  #     },
  #     connector_ensure => 'present';
  # }

	include 'postgresql::server'

	postgresql::server::db {
		'pogodynka_db':
			user     => 'pogodynka',
			password => postgresql_password('pogodynka', hiera('password_postgresql_pogodynka')),
	}
}
