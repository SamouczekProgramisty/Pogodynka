class pogodynka::tomcat {
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
}
