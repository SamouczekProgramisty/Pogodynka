class pogodynka::tomcat {

  $code_dir = "/opt/configuration/pogodynka"
  $war_file = "${code_dir}/datavault/build/libs/datavault-1.0-SNAPSHOT.war"
  $catalina_home = '/opt/tomcat8.5'

  tomcat::install {
    '/opt/tomcat8.5':
      source_url => 'http://mirror.23media.de/apache/tomcat/tomcat-8/v8.5.14/bin/apache-tomcat-8.5.14.tar.gz';
  }

  tomcat::instance {
    'tomcat8.5-production':
      catalina_home => $catalina_home,
      catalina_base => $catalina_home;
  }

  exec {
    'build_war':
      command => "${code_dir}/gradlew -b ${code_dir}/datavault/datavault.gradle war",
      creates => $war_file;
  }

  file {
    "${catalina_home}/webapps/datavault.war":
      source  => "file://${war_file}",
      require => [Exec['build_war'], Tomcat::Instance['tomcat8.5-production']],
      group   => 'tomcat',
      owner   => 'tomcat';
  }
}
