class pogodynka::tomcat (
  $code_dir = $::pogodynka::params::code_dir,
  $datavault_version = $::pogodynka::params::datavault_version
) inherits ::pogodynka::params {

  $war_file = "${code_dir}/datavault/build/libs/datavault-${datavault_version}.war"
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

  tomcat::setenv::entry {
    'POGODYNKA_USER_PASSWORD':
      config_file => "${catalina_base}/bin/setenv.sh",
      value       => hiera('password_postgresql_pogodynka_user');
  }

  exec {
    'build_war':
      command => "${code_dir}/gradlew -b ${code_dir}/datavault/datavault.gradle war",
      creates => $war_file;
  }

  tomcat::war {
    'datavault.war':
      war_source    => $war_file,
      catalina_base => $catalina_base,
      require       => Exec['build_war'];
  }
}
