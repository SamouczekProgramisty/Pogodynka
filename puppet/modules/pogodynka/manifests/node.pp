class pogodynka::node {
  notify {
    "it works":
  }

  java::oracle {
    'jdk8':
      ensure  => 'present',
      version => '6',
      java_se => 'jdk';
  }
  
  tomcat::install {
    '/opt/tomcat8.5':
      source_url => 'http://mirror.23media.de/apache/tomcat/tomcat-8/v8.5.14/bin/apache-tomcat-8.5.14.tar.gz';
  }

  tomcat::instance {
    'tomcat8.5-production':
      catalina_home => '/opt/tomcat8.5',
      catalina_base => '/opt/tomcat8.5/production';
  }

  tomcat::config::server {
    'tomcat8.5-production':
      catalina_base => '/opt/tomcat8.5/production',
      port => '80';
  }
}

